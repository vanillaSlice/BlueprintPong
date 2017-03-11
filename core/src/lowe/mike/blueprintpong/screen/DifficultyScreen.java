package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lowe.mike.blueprintpong.Assets;

/**
 * Difficulty screen to show just before the player enters
 * the game.
 *
 * @author Mike Lowe
 */
final class DifficultyScreen extends BaseScreen {

    private static final String DIFFICULTY_LABEL_TEXT = "Select Difficulty";
    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String PLAY_BUTTON_TEXT = "Play";

    private final Label difficultyLabel;
    private final ButtonGroup<TextButton> difficultyButtonGroup;
    private final Button backButton;
    private final TextButton playButton;

    /**
     * Creates a new {@code DifficultyScreen} given {@link Assets}, a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     */
    DifficultyScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        super(assets, spriteBatch, screenManager);
        this.difficultyLabel = ScreenUtils.createLabel(this.assets.getLargeFont(), DIFFICULTY_LABEL_TEXT);
        this.difficultyButtonGroup = ScreenUtils.createDifficultyButtonGroup(this.assets);
        this.backButton = initialiseBackButton();
        this.playButton = initialisePlayButton();
        this.stage.addActor(getMenuTable());
    }

    private TextButton initialiseBackButton() {
        TextButton button = ScreenUtils.createTextButton(assets, BACK_BUTTON_TEXT);
        addBackButtonListener(button);
        return button;
    }

    private void addBackButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    screenManager.switchToPreviousScreen();
                }
            }

        });
    }

    private TextButton initialisePlayButton() {
        TextButton button = ScreenUtils.createTextButton(assets, PLAY_BUTTON_TEXT);
        addPlayButtonListener(button);
        return button;
    }

    private void addPlayButtonListener(final TextButton button) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    switchToGameScreen();
                }
            }

        });
    }

    private void switchToGameScreen() {
        // dispose this screen because we won't be able to return to it
        // from the next screen
        screenManager.disposeAndClearAllScreens();
        screenManager.setScreen(new GameScreen(assets, spriteBatch, screenManager));
    }

    private Table getMenuTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.row();
        table.add(difficultyLabel).expandX();
        table.row().padBottom(COMPONENT_SPACING);
        addDifficultyButtonGroup(table);
        table.row().padTop(COMPONENT_SPACING);
        addBackAndPlayButtons(table);
        return table;
    }

    private void addDifficultyButtonGroup(Table table) {
        HorizontalGroup group = new HorizontalGroup();
        group.space(COMPONENT_SPACING);
        for (TextButton button : difficultyButtonGroup.getButtons()) {
            group.addActor(button);
        }
        table.add(group).expandX();
    }

    private void addBackAndPlayButtons(Table table) {
        HorizontalGroup group = new HorizontalGroup();
        group.space(COMPONENT_SPACING);
        group.addActor(backButton);
        group.addActor(playButton);
        table.add(group).expandX();
    }

}