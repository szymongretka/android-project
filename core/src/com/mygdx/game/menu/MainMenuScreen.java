package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screen.GameScreen;


public class MainMenuScreen implements Screen {

    private final MyGdxGame game;
    //private OrthographicCamera camera;

    private Stage stage;
    private Table table;
    private Skin skin;
    private TextButton startButton;
    private TextButton quitButton;
    private TextButton settingsButton;
    private TextButton openBrowserButton;


    public MainMenuScreen(final MyGdxGame game) {
        this.game = game;

        game.assets.load();
        game.assets.manager.finishLoading();

        if(game.assets.manager.isFinished()){
            loadAssets();
        }

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, 480, 800);

        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        startButton = new TextButton("Start!", skin);
        openBrowserButton = new TextButton("Check Stats",skin);
        quitButton = new TextButton("Quit Game",skin);


        startButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });

        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
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


        table.padTop(30);
        table.add(startButton).height(200f).width(500f).padBottom(30);
        table.row();
        table.add(openBrowserButton).height(200f).width(500f).padBottom(30);
        table.row();
        table.add(quitButton).height(200f).width(500f);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

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
        skin.dispose();
    }

    private void loadAssets() {
        skin = game.assets.manager.get("data/uiskin.json", Skin.class);//game.assets.manager.get("uiskin.json", Skin.class);
    }


}