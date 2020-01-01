package com.mygdx.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.IRequestCallback;

import java.util.Map;

public class StatisticsScreen extends AbstractScreen {

    private Stage stage;
    private ImageButton backButton;
    private ImageButton updateStatsButton;

    private TextureAtlas textureAtlas;
    private TextureRegion backTextureUp;
    private TextureRegion backTextureDown;
    private TextureRegion updateTextureUp;
    private TextureRegion updateTextureDown;

    private Map<String, Long> mapOfTopScores;
    private Skin skin;
    private Table table;

    private Label label;
    private Label topTenLabel;


    public StatisticsScreen(final SpaceInvaderApp game) {
        super(game);

        mapOfTopScores = game.getScoreService().getMapOfTopScores();

        if(game.assets.manager.isFinished()){
            loadAssets();
        }

        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        initButtons();


        backButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                dispose();
                game.gameScreenManager.setActiveScreen(GameState.MENU);
                game.gameScreenManager.setScreen(GameState.MENU, MainMenuScreen.class);
            }
        });

        updateStatsButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                game.getScoreService().createUpdateScoreResponse(new IRequestCallback() {
                    @Override
                    public void onSucceed() {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                //TODO
                            }
                        });
                    }

                    @Override
                    public void onError() {
                        //TODO
                    }
                }, game.myPreferences);
            }
        });

        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;

        for (Map.Entry<String, Long> entry : mapOfTopScores.entrySet()) {
            i++;
            stringBuilder.append(i).append(". ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" points").append('\n');
        }


        topTenLabel = new Label("TOP TEN PLAYERS:", skin);
        label = new Label(stringBuilder.toString(), skin);
        skin.getFont("default-font").getData().setScale(3f);

        table.padTop(100f);
        table.add(topTenLabel).padBottom(150f);
        table.row();
        table.add(label);
        table.row();
        table.add(updateStatsButton).height(200f).width(350f);
        table.row();

        stage.addActor(table);
        stage.addActor(backButton);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.draw();

    }

    private void loadAssets() {
        textureAtlas = game.assets.manager.get("packedImages/playerAndEnemies.atlas", TextureAtlas.class);
        backTextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/strzalka"));
        backTextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/strzalka1"));
        skin = game.assets.manager.get("skin/uiskin.json", Skin.class);

        updateTextureUp = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/SEND SCORE"));
        updateTextureDown = new TextureRegion(textureAtlas.findRegion("menu/main_menu_buttons/SEND SCOREk"));

    }

    private void initButtons() {
        Drawable backUp = new TextureRegionDrawable(backTextureUp);
        Drawable backDown = new TextureRegionDrawable(backTextureDown);
        backButton = new ImageButton(backUp, backDown);

        backButton.setSize(120f, 120f);
        backButton.setPosition(0, Constants.HEIGHT - 120f);

        Drawable updateUp = new TextureRegionDrawable(updateTextureUp);
        Drawable updateDown = new TextureRegionDrawable(updateTextureDown);
        updateStatsButton = new ImageButton(updateUp, updateDown);
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
        stage.dispose();
    }
}
