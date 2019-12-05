package com.mygdx.game.screen.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.game.GameScreen;

public class PauseScreen extends AbstractScreen {

    private Stage stage;
    private Table table;
    private Skin skin;
    private TextButton resumeButton;
    private TextButton mainMenuButton;

    public PauseScreen(final MyGdxGame game) {
        super(game);

        if(game.assets.manager.isFinished()){
            loadAssets();
        }
        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        resumeButton = new TextButton("Resume", skin);
        mainMenuButton = new TextButton("Main menu",skin);


        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.PLAYSCREEN); //TODO //////////////////
                game.gameScreenManager.setScreen(GameState.PLAYSCREEN,
                        game.gameScreenManager.getGameScreen(GameState.PLAYSCREEN));
            }
        });

        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.MENU);
                game.gameScreenManager.setScreen(GameState.MENU,
                        game.gameScreenManager.getGameScreen(GameState.MENU));
            }
        });



        table.padTop(30);
        table.add(resumeButton).height(200f).width(500f).padBottom(60);
        table.row();
        table.add(mainMenuButton).height(200f).width(500f).padBottom(60);
        table.row();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.draw();

    }


    @Override
    public void update(float delta) {

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

    private void loadAssets() {
        skin = game.assets.manager.get("data/uiskin.json", Skin.class);
    }

}
