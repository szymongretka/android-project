package com.mygdx.game.menu;

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
import com.mygdx.game.screen.GameScreen;

public class LevelScreen extends AbstractScreen {

    private Stage stage;
    private Table table;
    private Skin skin;
    private TextButton level1Button;
    private TextButton level2Button;
    private TextButton level3Button;
    private TextButton backButton;


    public LevelScreen(final MyGdxGame game) {
        super(game);

        game.assets.load();
        game.assets.manager.finishLoading();

        if(game.assets.manager.isFinished()){
            loadAssets();
        }

        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        level1Button = new TextButton("Level 1", skin);
        level2Button = new TextButton("Level 2",skin);
        level3Button = new TextButton("Level 3",skin);

        //level2Button.setDisabled(true);
        //level3Button.setDisabled(true);

        level1Button.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.LEVEL1);
                game.gameScreenManager.setScreen(GameState.LEVEL1, new GameScreen(game));
            }
        });



        table.padTop(30);
        table.add(level1Button).height(200f).width(500f).padBottom(60);
        table.row();
        table.add(level2Button).height(200f).width(500f).padBottom(60);
        table.row();
        table.add(level3Button).height(200f).width(500f);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

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
        //skin.dispose();
    }

    private void loadAssets() {
        skin = game.assets.manager.get("data/uiskin.json", Skin.class);//game.assets.manager.get("uiskin.json", Skin.class);
    }

}
