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
import com.mygdx.game.screen.game.GameScreen;
import com.mygdx.game.util.Constants;

public class LevelScreen extends AbstractScreen {

    private Stage stage;
    private Table table;

    private TextureAtlas textureAtlas;
    private Texture background;

    private ImageButton level1Button;
    private ImageButton level2Button;
    private ImageButton level3Button;
    private ImageButton level4Button;
    private ImageButton level5Button;
    private ImageButton backButton;

    private TextureRegion level1TextureUp;
    private TextureRegion level1TextureDown;
    private TextureRegion level2TextureUp;
    private TextureRegion level2TextureDown;
    private TextureRegion level3TextureUp;
    private TextureRegion level3TextureDown;
    private TextureRegion level4TextureUp;
    private TextureRegion level4TextureDown;
    private TextureRegion level5TextureUp;
    private TextureRegion level5TextureDown;
    private TextureRegion backTextureUp;
    private TextureRegion backTextureDown;



    public LevelScreen(final SpaceInvaderApp game) {
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


        level1Button.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.LEVEL1);
                game.gameScreenManager.setScreen(GameState.LEVEL1, GameScreen.class);
            }
        });

        level2Button.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.LEVEL2);
                game.gameScreenManager.setScreen(GameState.LEVEL2, GameScreen.class);
            }
        });
        level3Button.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.LEVEL3);
                game.gameScreenManager.setScreen(GameState.LEVEL3, GameScreen.class);
            }
        });
        level4Button.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.LEVEL4);
                game.gameScreenManager.setScreen(GameState.LEVEL4, GameScreen.class);
            }
        });
        level5Button.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.LEVEL5);
                game.gameScreenManager.setScreen(GameState.LEVEL5, GameScreen.class);
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                game.gameScreenManager.setActiveScreen(GameState.MENU);
                game.gameScreenManager.setScreen(GameState.MENU, MainMenuScreen.class);
            }
        });



        table.padTop(30);
        table.add(level1Button).height(200f).width(500f).padBottom(60);
        table.row();
        table.add(level2Button).height(200f).width(500f).padBottom(60);
        table.row();
        table.add(level3Button).height(200f).width(500f).padBottom(60);
        table.row();
        table.add(level4Button).height(200f).width(500f).padBottom(60);
        table.row();
        table.add(level5Button).height(200f).width(500f);


        stage.addActor(backButton);
        stage.addActor(table);

    }

    @Override
    public void update(float delta) {

    }

    private void initButtons() {
        Drawable level1Up = new TextureRegionDrawable(level1TextureUp);
        Drawable level1Down = new TextureRegionDrawable(level1TextureDown);
        level1Button = new ImageButton(level1Up, level1Down);

        Drawable level2Up = new TextureRegionDrawable(level2TextureUp);
        Drawable level2Down = new TextureRegionDrawable(level2TextureDown);
        level2Button = new ImageButton(level2Up, level2Down);

        Drawable level3Up = new TextureRegionDrawable(level3TextureUp);
        Drawable level3Down = new TextureRegionDrawable(level3TextureDown);
        level3Button = new ImageButton(level3Up, level3Down);

        Drawable level4Up = new TextureRegionDrawable(level4TextureUp);
        Drawable level4Down = new TextureRegionDrawable(level4TextureDown);
        level4Button = new ImageButton(level4Up, level4Down);

        Drawable level5Up = new TextureRegionDrawable(level5TextureUp);
        Drawable level5Down = new TextureRegionDrawable(level5TextureDown);
        level5Button = new ImageButton(level5Up, level5Down);

        Drawable backUp = new TextureRegionDrawable(backTextureUp);
        Drawable backDown = new TextureRegionDrawable(backTextureDown);
        backButton = new ImageButton(backUp, backDown);

        backButton.setSize(120f, 120f);
        backButton.setPosition(0, Constants.HEIGHT - 120f);
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
        //skin.dispose();
    }

    private void loadAssets() {
        textureAtlas = game.assets.manager.get("packedImages/playerAndEnemies.atlas", TextureAtlas.class);

        background = game.assets.manager.get("background/menu_background.png", Texture.class);

        level1TextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level1"));
        level1TextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level1k"));

        level2TextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level2"));
        level2TextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level2k"));

        level3TextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level3"));
        level3TextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level3k"));

        level4TextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level4"));
        level4TextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level4k"));

        level5TextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level5"));
        level5TextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/level5k"));

        backTextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/strzalka"));
        backTextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/strzalka1"));

    }

}
