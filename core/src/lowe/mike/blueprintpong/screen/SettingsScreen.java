package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.GamePreferences;

/**
 * Settings screen to show settings that the user can change.
 *
 * @author Mike Lowe
 */
final class SettingsScreen extends BaseScreen {

    private static final String SETTINGS_LABEL_TEXT = "Settings";
    private static final String DIFFICULTY_LABEL_TEXT = "Difficulty";
    private static final String SOUNDS_LABEL_TEXT = "Sounds";
    private static final String ON_BUTTON_TEXT = "On";
    private static final String OFF_BUTTON_TEXT = "Off";
    private static final String BACK_BUTTON_TEXT = "Back";

    private final Label settingsLabel;
    private final Label difficultyLabel;
    private final ButtonGroup<TextButton> difficultyButtonGroup;
    private final Label soundsLabel;
    private final ButtonGroup<TextButton> soundsButtonGroup;
    private final Button backButton;

    /**
     * Creates a new {@code SettingsScreen} given {@link Assets}, a {@link SpriteBatch}
     * and a {@link ScreenManager}.
     *
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     */
    SettingsScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
        super(assets, spriteBatch, screenManager);
        this.settingsLabel = createLabel(assets.getLargeFont(), SETTINGS_LABEL_TEXT);
        this.difficultyLabel = createLabel(assets.getMediumFont(), DIFFICULTY_LABEL_TEXT);
        this.difficultyButtonGroup = ScreenUtils.initialiseDifficultyButtonGroup(this);
        this.soundsLabel = createLabel(assets.getMediumFont(), SOUNDS_LABEL_TEXT);
        this.soundsButtonGroup = initialiseSoundsButtonGroup();
        this.backButton = initialiseBackButton();
        this.stage.addActor(getMenuTable());
    }

    private ButtonGroup<TextButton> initialiseSoundsButtonGroup() {
        boolean playSounds = GamePreferences.shouldPlaySounds();
        TextButton onButton = createTextButton(ON_BUTTON_TEXT);
        TextButton offButton = createTextButton(OFF_BUTTON_TEXT);
        if (playSounds) {
            onButton.setChecked(true);
        } else {
            offButton.setChecked(true);
        }
        addSoundButtonListener(onButton, true);
        addSoundButtonListener(offButton, false);

        ButtonGroup<TextButton> buttonGroup = new ButtonGroup<TextButton>(onButton, offButton);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setMaxCheckCount(1);
        return buttonGroup;
    }

    private void addSoundButtonListener(final TextButton button, final boolean playSounds) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (button.isChecked()) {
                    GamePreferences.setPlaySounds(playSounds);
                }
            }

        });
    }

    private TextButton initialiseBackButton() {
        TextButton button = createTextButton(BACK_BUTTON_TEXT);
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

    private Table getMenuTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.row().padBottom(getComponentSpacing());
        int colspan = difficultyButtonGroup.getButtons().size + 1;
        table.add(settingsLabel).expandX().colspan(colspan);
        table.row();
        addButtonGroup(table, difficultyLabel, difficultyButtonGroup);
        table.row().padTop(getComponentSpacing());
        addButtonGroup(table, soundsLabel, soundsButtonGroup);
        table.row().padTop(getComponentSpacing());
        table.add(backButton).expandX().colspan(colspan);
        return table;
    }

    private void addButtonGroup(Table table, Label label, ButtonGroup<TextButton> buttonGroup) {
        table.add(label).expandX().align(Align.center);
        for (TextButton button : buttonGroup.getButtons()) {
            table.add(button).expandX().align(Align.left);
        }
    }

}