package com.mygdx.game.screen.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.menu.MainMenuScreen;
import com.mygdx.game.util.Constants;

public class ErrorScreen extends AbstractScreen {

    private Stage stage;
    private ImageButton backButton;

    private TextureAtlas textureAtlas;
    private TextureRegion backTextureUp;
    private TextureRegion backTextureDown;

    private ImageButton errorButton;
    private TextureRegion errorTex;

    private float width = 800f;
    private float height = 600f;

    public ErrorScreen(SpaceInvaderApp game) {
        super(game);

        if(game.assets.manager.isFinished()){
            loadAssets();
        }

        stage = new Stage(new ScreenViewport());

        initButtons();

        Drawable error = new TextureRegionDrawable(errorTex);
        errorButton = new ImageButton(error);
        errorButton.setSize(width, height);
        errorButton.setPosition(Constants.WIDTH / 2f - (width / 2f), Constants.HEIGHT / 2f - (height / 2f));


        SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.01f), Actions.fadeIn(0.9f));
        errorButton.addAction(Actions.repeat(1, flicker));

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                game.gameScreenManager.setActiveScreen(GameState.MENU);
                game.gameScreenManager.setScreen(GameState.MENU, MainMenuScreen.class);
            }
        });

        stage.addActor(errorButton);
        stage.addActor(backButton);

    }

    private void initButtons() {
        Drawable backUp = new TextureRegionDrawable(backTextureUp);
        Drawable backDown = new TextureRegionDrawable(backTextureDown);
        backButton = new ImageButton(backUp, backDown);

        backButton.setSize(120f, 120f);
        backButton.setPosition(0, Constants.HEIGHT - 120f);
    }

    private void loadAssets() {
        textureAtlas = game.assets.manager.get("packedImages/playerAndEnemies.atlas", TextureAtlas.class);
        backTextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/strzalka"));
        backTextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/strzalka1"));
        errorTex = new TextureRegion(textureAtlas.findRegion("menu/event_screen/CONNECTION"));
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
        Gdx.input.setInputProcessor(stage);
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