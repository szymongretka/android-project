package com.mygdx.game.screen.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.screen.menu.MainMenuScreen;
import com.mygdx.game.util.Constants;

public class PauseScreen extends AbstractScreen {

    private Stage stage;
    private Table table;

    private ImageButton resumeButton;
    private ImageButton mainMenuButton;

    private TextureAtlas textureAtlas;
    private Texture background;
    private TextureRegion resumeTextureUp;
    private TextureRegion resumeTextureDown;
    private TextureRegion mainMenuTextureUp;
    private TextureRegion mainMenuTextureDown;



    public PauseScreen(final SpaceInvaderApp game) {
        super(game);

        if(game.assets.manager.isFinished()){
            loadAssets();
        }
        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        initButtons();


        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                //game.gameScreenManager.setActiveScreen(GameState.LEVEL1); //TODO //////////////////
                Timer.instance().start();
                game.gameScreenManager.setScreen(GameState.LEVEL1, GameScreen.class);
            }
        });

        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                //game.gameScreenManager.setActiveScreen(GameState.MENU);
                Timer.instance().clear();
                game.gameScreenManager.clearGameStateMap();
                game.gameScreenManager.setScreen(GameState.MENU, MainMenuScreen.class);

            }
        });



        table.padTop(Constants.HEIGHT/3f);
        table.add(resumeButton).height(200f).width(500f).padBottom(60);
        table.row();
        table.add(mainMenuButton).height(200f).width(500f).padBottom(60);
        table.row();

        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        stage.getBatch().end();

        stage.draw();

    }

    private void initButtons() {
        Drawable resumeUp = new TextureRegionDrawable(resumeTextureUp);
        Drawable resumeDown = new TextureRegionDrawable(resumeTextureDown);
        resumeButton = new ImageButton(resumeUp, resumeDown);

        Drawable menuUp = new TextureRegionDrawable(mainMenuTextureUp);
        Drawable menuDown = new TextureRegionDrawable(mainMenuTextureDown);
        mainMenuButton = new ImageButton(menuUp, menuDown);
    }


    @Override
    public void update(float delta) {

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

    private void loadAssets() {
        textureAtlas = game.assets.manager.get("packedImages/playerAndEnemies.atlas", TextureAtlas.class);

        background = game.assets.manager.get("background/menu_background.png", Texture.class);

        resumeTextureUp = new TextureRegion(textureAtlas.findRegion("menu/pause_buttons/RESUME"));
        resumeTextureDown = new TextureRegion(textureAtlas.findRegion("menu/pause_buttons/RESUMEk"));

        mainMenuTextureUp = new TextureRegion(textureAtlas.findRegion("menu/pause_buttons/Menu"));
        mainMenuTextureDown = new TextureRegion(textureAtlas.findRegion("menu/pause_buttons/Menuk"));
    }

}
