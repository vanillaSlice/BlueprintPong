package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

import lowe.mike.blueprintpong.Difficulty;
import lowe.mike.blueprintpong.GamePreferences;

/**
 * {@code ScreenUtils} provides useful helper methods that are
 * repeatedly used in the game {@link Screen} classes.
 *
 * @author Mike Lowe
 */
public final class ScreenUtils {

    // don't want instances
    private ScreenUtils() {
    }

    /**
     * Creates a {@link ButtonGroup} of difficulty {@link TextButton}s.
     *
     * @param screen {@link BaseScreen} to create {@link ButtonGroup} for
     * @return a {@link ButtonGroup} of difficulty {@link TextButton}s
     */
    public static ButtonGroup<TextButton> initialiseDifficultyButtonGroup(
            BaseScreen screen) {
        Array<TextButton> buttons = new Array<TextButton>(TextButton.class);

        Difficulty currentDifficulty = GamePreferences.getDifficulty();
        for (Difficulty difficulty : Difficulty.values()) {
            TextButton button = screen.createTextButton(difficulty.toString());
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
