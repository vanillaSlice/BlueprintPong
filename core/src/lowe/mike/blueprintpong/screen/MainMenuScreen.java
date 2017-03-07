package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * Main menu screen to show when the game is first opened.
 *
 * @author Mike Lowe
 */

final class MainMenuScreen extends BaseScreen {

    // add screen portion fractions

    private static final String PLAY_BUTTON_TEXT = "Play";
    private static final String SETTINGS_BUTTON_TEXT = "Settings";
    private static final float SETTINGS_BUTTON_TOP_PADDING = 10f;

    private final Image background;
    private final Label titleLabel;
    private final Button playButton;
    private final Button settingsButton;

    public MainMenuScreen(SpriteBatch spriteBatch) {
        super(spriteBatch);
        this.background = new Image(Assets.getInstance().getBackgroundTexture());
        this.titleLabel = createLabel(viewport.getScreenWidth() / 6, BlueprintPongGame.TITLE);
        this.playButton = createTextButton(viewport.getScreenWidth() / 10, "Play");
        this.settingsButton = createTextButton(viewport.getScreenWidth() / 10, "Settings");
        this.stage.addActor(this.background);
        this.stage.addActor(getMenuTable());

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        Gdx.input.setInputProcessor(this.stage);

        this.playButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switchToDifficultyScreen();
            }
        });

        this.settingsButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Settings");
            }
        });
    }

    private Table getMenuTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.row();
        table.add(titleLabel).expandX();
        table.row();
        table.add(playButton).expandX();
        table.row();
        table.add(settingsButton).expandX().padTop(SETTINGS_BUTTON_TOP_PADDING);
        return table;
    }

    //add buttons

    private void switchToDifficultyScreen() {
       // ScreenManager.getInstance().removeAndDisposeCurrentScreen();
        ScreenManager.getInstance().setScreen(new DifficultyScreen(spriteBatch, ScreenManager.getInstance()));
    }

    @Override
    public void resizeAssets() {
        background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
    }

}