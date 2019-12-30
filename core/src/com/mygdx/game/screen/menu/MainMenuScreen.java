package com.mygdx.game.screen.menu;

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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.util.Constants;


public class MainMenuScreen extends AbstractScreen {

    //private OrthographicCamera camera;

    private Stage stage;
    private Table table;
    private Skin skin;

    private ImageButton startButton;
    private ImageButton quitButton;
    private ImageButton settingsButton;
    private ImageButton openBrowserButton;

    private TextureAtlas textureAtlas;
    private Texture background;

    private TextureRegion startTextureUp;
    private TextureRegion startTextureDown;
    private TextureRegion checkTextureUp;
    private TextureRegion checkTextureDown;
    private TextureRegion quitTextureUp;
    private TextureRegion quitTextureDown;
    private TextureRegion settingsTextureUp;
    private TextureRegion settingsTextureDown;



    public MainMenuScreen(final SpaceInvaderApp game) {
        super(game);

        if(game.assets.manager.isFinished()){
            loadAssets();
        }

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        initButtons();

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setScreen(GameState.LEVELSCREEN, LevelScreen.class);
            }
        });

        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                game.dispose();
                Gdx.app.exit();
            }
        });

        openBrowserButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                String url = "http://192.168.1.107:8080/player/all";
                Gdx.net.openURI(url);
            }
        });

        table.padTop(Constants.HEIGHT/3f);
        table.add(startButton).height(200f).width(500f).padBottom(30);
        table.row();
        table.add(openBrowserButton).height(200f).width(500f).padBottom(30);
        table.row();
        table.add(quitButton).height(200f).width(500f);


        stage.addActor(settingsButton);
        stage.addActor(table);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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

        Drawable startUp = new TextureRegionDrawable(startTextureUp);
        Drawable startDown = new TextureRegionDrawable(startTextureDown);
        startButton = new ImageButton(startUp, startDown);

        Drawable checkUp = new TextureRegionDrawable(checkTextureUp);
        Drawable checkDown = new TextureRegionDrawable(checkTextureDown);
        openBrowserButton = new ImageButton(checkUp, checkDown);

        Drawable quitUp = new TextureRegionDrawable(quitTextureUp);
        Drawable quitDown = new TextureRegionDrawable(quitTextureDown);
        quitButton = new ImageButton(quitUp, quitDown);

        Drawable settingsUp = new TextureRegionDrawable(settingsTextureUp);
        Drawable settingsDown = new TextureRegionDrawable(settingsTextureDown);
        settingsButton = new ImageButton(settingsUp, settingsDown);

        settingsButton.setSize(80f, 80f);
        settingsButton.setPosition(Constants.WIDTH - 80f, Constants.HEIGHT - 80f);


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

        startTextureUp = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/Start"));
        startTextureDown = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/Start1"));

        checkTextureUp = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/STATS"));
        checkTextureDown = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/STATS1"));

        quitTextureUp = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/Quit"));
        quitTextureDown = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/Quit1"));

        settingsTextureUp = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/opcje"));
        settingsTextureDown = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/opcje1"));

    }


}