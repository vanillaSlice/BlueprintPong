package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.Difficulty;
import lowe.mike.blueprintpong.GamePreferences;

/**
 * {@code ScreenUtils} provides useful helper methods that are
 * repeatedly used in the game {@link Screen} classes.
 * <p>
 * Instances of {@code ScreenUtils} cannot be created.
 *
 * @author Mike Lowe
 */
final class ScreenUtils {

    private static final Color PRIMARY_TEXT_BUTTON_FONT_COLOUR = Color.WHITE;
    private static final Color SECONDARY_TEXT_BUTTON_FONT_COLOUR = Color.BLACK;

    // don't want instances
    private ScreenUtils() {
    }

    /**
     * Creates a {@link Label} with the given {@link BitmapFont} and text.
     *
     * @param font the {@link BitmapFont}
     * @param text text to initialise the {@link Label} with
     * @return the {@link Label}
     */
    static Label createLabel(BitmapFont font, String text) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        Label label = new Label(text, style);
        label.setAlignment(Align.center);
        return label;
    }

    /**
     * Creates a {@link TextButton} with the given text.
     *
     * @param assets {@link Assets} needed to create {@link TextButton}
     * @param text   text to initialise the {@link TextButton} with
     * @return the {@link TextButton}
     */
    static TextButton createTextButton(Assets assets, String text) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.fontColor = PRIMARY_TEXT_BUTTON_FONT_COLOUR;
        style.downFontColor = SECONDARY_TEXT_BUTTON_FONT_COLOUR;
        style.overFontColor = style.downFontColor;
        style.checkedFontColor = style.downFontColor;
        style.up = getTextureRegionDrawable(assets.getButtonUpTexture());
        style.down = getTextureRegionDrawable(assets.getButtonDownTexture());
        style.over = style.down;
        style.checked = style.down;
        style.font = assets.getMediumFont();
        TextButton textButton = new TextButton(text, style);
        float padding = (textButton.getHeight() - style.font.getCapHeight()) / 2;
        textButton.padLeft(padding).padRight(padding).align(Align.center);
        return textButton;
    }

    private static TextureRegionDrawable getTextureRegionDrawable(Texture texture) {
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    /**
     * Creates a {@link ButtonGroup} of difficulty {@link TextButton}s.
     *
     * @param assets {@link Assets} needed to create {@link TextButton}s
     * @return a {@link ButtonGroup} of difficulty {@link TextButton}s
     */
    static ButtonGroup<TextButton> createDifficultyButtonGroup(Assets assets) {
        Array<TextButton> buttons = new Array<TextButton>(TextButton.class);

        Difficulty currentDifficulty = GamePreferences.getDifficulty();
        for (Difficulty difficulty : Difficulty.values()) {
            TextButton button = createTextButton(assets, difficulty.toString());
            if (difficulty == currentDifficulty) {
                button.setChecked(true);
            }
            addDifficultyButtonListener(button, difficulty);
            buttons.add(button);
        }

        ButtonGroup<TextButton> buttonGroup = new ButtonGroup<TextButton>(buttons.toArray());
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setMaxCheckCount(1);
        return buttonGroup;
    }

    private static void addDifficultyButtonListener(final TextButton button,
                                                    final Difficulty difficulty) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    GamePreferences.setDifficulty(difficulty);
                }
            }

        });
    }

}