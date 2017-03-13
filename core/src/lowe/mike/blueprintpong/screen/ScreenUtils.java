package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import lowe.mike.blueprintpong.BlueprintPongGame;
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
    private static final String SETTINGS_BUTTON_TEXT = "Settings";
    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String EXIT_BUTTON_TEXT = "Exit";

    // don't want instances
    private ScreenUtils() {
    }

    /**
     * Scales {@link Actor}s to the correct size for the screen.
     *
     * @param actor the {@link Actor} to scale
     */
    static void scaleActor(Actor actor) {
        actor.setScale(BlueprintPongGame.X_SCALE, BlueprintPongGame.Y_SCALE);
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
        textButton.getLabelCell().padLeft(padding).padRight(padding);
        textButton.align(Align.center);
        textButton.pack();
        return textButton;
    }

    private static TextureRegionDrawable getTextureRegionDrawable(Texture texture) {
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    /**
     * Creates a settings {@link TextButton} that will make {@link SettingsScreen} the current
     * {@link Screen} when pressed.
     *
     * @param assets        {@link Assets} needed to create the {@link TextButton}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     * @return the settings {@link TextButton}
     */
    static TextButton createSettingsButton(final Assets assets,
                                           final SpriteBatch spriteBatch,
                                           final ScreenManager screenManager) {
        final TextButton button = ScreenUtils.createTextButton(assets, SETTINGS_BUTTON_TEXT);
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    screenManager.setScreen(new SettingsScreen(assets, spriteBatch, screenManager));
                }
            }

        });
        return button;
    }

    /**
     * Creates a back {@link TextButton} that will switch to the previous {@link Screen}
     * when pressed.
     *
     * @param assets        {@link Assets} needed to create the {@link TextButton}
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     * @return the back {@link TextButton}
     */
    static TextButton createBackButton(final Assets assets,
                                       final ScreenManager screenManager) {
        final TextButton button = ScreenUtils.createTextButton(assets, BACK_BUTTON_TEXT);
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    screenManager.switchToPreviousScreen();
                }
            }

        });
        return button;
    }

    /**
     * Creates an exit {@link TextButton} that will make {@link MainMenuScreen} the current
     * {@link Screen} when pressed.
     *
     * @param assets        {@link Assets} needed to create the {@link TextButton}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     * @return the settings {@link TextButton}
     */
    static TextButton createExitButton(final Assets assets,
                                       final SpriteBatch spriteBatch,
                                       final ScreenManager screenManager) {
        final TextButton button = ScreenUtils.createTextButton(assets, EXIT_BUTTON_TEXT);
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    screenManager.disposeAndClearAllScreens();
                    screenManager.setScreen(new MainMenuScreen(assets, spriteBatch, screenManager));
                }
            }

        });
        return button;
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

    /**
     * Creates a score {@link Label} for the computer.
     *
     * @param assets {@link Assets} needed to create the {@link Label}
     * @param score  the score
     * @return the computer score {@link Label}
     */
    static Label createComputerScoreLabel(Assets assets, int score) {
        return createScoreLabel(assets, score, .25f);
    }

    /**
     * Creates a score {@link Label} for the player.
     *
     * @param assets {@link Assets} needed to create the {@link Label}
     * @param score  the score
     * @return the player score {@link Label}
     */
    static Label createPlayerScoreLabel(Assets assets, int score) {
        return createScoreLabel(assets, score, .75f);
    }

    private static Label createScoreLabel(Assets assets, int score, float positionFraction) {
        Label label = ScreenUtils.createLabel(assets.getMediumFont(), Integer.toString(score));
        float x = (BlueprintPongGame.VIRTUAL_WIDTH * positionFraction) - (label.getWidth() / 2);
        float y = BlueprintPongGame.VIRTUAL_HEIGHT - label.getHeight() - BaseScreen.COMPONENT_SPACING;
        label.setPosition(x, y);
        return label;
    }

}