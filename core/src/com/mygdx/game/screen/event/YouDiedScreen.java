package com.mygdx.game.screen.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.MyPreferences;

public class YouDiedScreen extends AbstractScreen {

    private MyPreferences myPreferences;
    private Stage stage;
    private ImageButton youDiedButton;
    private TextureRegion youDiedTex;

    private float width = 800f;
    private float height = 600f;

    public YouDiedScreen(SpaceInvaderApp game) {
        super(game);
        youDiedTex = GameScreen.youDiedImage;
        GameScreen.NUMBER_OF_BULLETS = 1;

        myPreferences = new MyPreferences();
        myPreferences.updatePoints();

        Timer.instance().clear();


        stage = new Stage(new ScreenViewport());


        Drawable youDied = new TextureRegionDrawable(youDiedTex);
        youDiedButton = new ImageButton(youDied);
        youDiedButton.setSize(width, height);
        youDiedButton.setPosition(Constants.WIDTH / 2f - (width / 2f), Constants.HEIGHT / 2f - (height / 2f));


        SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.01f), Actions.fadeIn(0.9f));
        youDiedButton.addAction(Actions.repeat(1, flicker));

        stage.addActor(youDiedButton);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
