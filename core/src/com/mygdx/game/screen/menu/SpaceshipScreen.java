package com.mygdx.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.game_object.player.ship.BasicShip;
import com.mygdx.game.game_object.player.ship.BigShip;
import com.mygdx.game.game_object.player.ship.Ship;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.util.Constants;

import java.util.HashMap;
import java.util.Map;


public class SpaceshipScreen extends AbstractScreen {

    private Stage stage;
    private ImageButton backButton;
    private ImageButton attachmentsButton;
    private ImageButton arrowForwardButton;
    private ImageButton arrowBackwardButton;

    private TextureAtlas textureAtlas;
    private TextureRegion backTextureUp;
    private TextureRegion backTextureDown;

    private TextureRegion basicShipTex;
    private TextureRegion bigShipTex;

    private TextureRegion attachmentsTextureUp;
    private TextureRegion attachmentsTextureDown;

    private TextureRegion arrowRightTextureUp;
    private TextureRegion arrowRightTextureDown;
    private TextureRegion arrowLeftTextureUp;
    private TextureRegion arrowLeftTextureDown;

    private Table table;

    private float shipWidth = 400f, shipHeight = 400f;
    private Integer shipNumber = 0;

    private BigShip bigShip;
    private BasicShip basicShip;

    private Map<Integer, Ship> map = new HashMap<>();


    public SpaceshipScreen(final SpaceInvaderApp game) {
        super(game);


        if(game.assets.manager.isFinished()){
            loadAssets();
        }

        basicShip = new BasicShip(basicShipTex);
        bigShip = new BigShip(bigShipTex);

        map.put(0, basicShip);
        map.put(1, bigShip);

        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.setDebug(true);

        initButtons();

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                game.gameScreenManager.setActiveScreen(GameState.MENU);
                game.gameScreenManager.setScreen(GameState.MENU, MainMenuScreen.class);
            }
        });

        arrowForwardButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                if(shipNumber < map.size() - 1)
                    shipNumber++;
            }
        });

        arrowBackwardButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                if(shipNumber > 0)
                    shipNumber--;
            }
        });



        table.padTop(100f);
        table.add(attachmentsButton).height(200f).width(350f).padBottom(400f);
        table.row();


        stage.addActor(table);
        stage.addActor(backButton);
        stage.addActor(arrowBackwardButton);
        stage.addActor(arrowForwardButton);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();

        stage.getBatch().draw(map.get(shipNumber).getTextureRegion(), Constants.WIDTH/2f - (shipWidth/2f),
                Constants.HEIGHT/2f - (shipHeight/2f), shipWidth, shipHeight);
        stage.getBatch().end();

        stage.draw();

    }

    private void loadAssets() {
        textureAtlas = game.assets.manager.get("packedImages/playerAndEnemies.atlas", TextureAtlas.class);
        backTextureUp = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/strzalka"));
        backTextureDown = new TextureRegion(textureAtlas.findRegion("menu/level_buttons/strzalka1"));

        attachmentsTextureUp = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/att"));
        attachmentsTextureDown = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/attk"));

        arrowRightTextureUp = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/arrow"));
        arrowRightTextureDown = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/arrowk"));

        arrowLeftTextureUp = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/arrowLeft"));
        arrowLeftTextureDown = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/arrowLeftk"));


        basicShipTex = new TextureRegion(textureAtlas.findRegion("basicPlayerSpaceship"), 5 * 80, 0, 80, 127);
        bigShipTex = new TextureRegion(textureAtlas.findRegion("basicPlayerShipHIT"), 0, 0, 80, 127);

    }

    private void initButtons() {
        Drawable backUp = new TextureRegionDrawable(backTextureUp);
        Drawable backDown = new TextureRegionDrawable(backTextureDown);
        backButton = new ImageButton(backUp, backDown);

        backButton.setSize(120f, 120f);
        backButton.setPosition(0, Constants.HEIGHT - 120f);

        Drawable attUp = new TextureRegionDrawable(attachmentsTextureUp);
        Drawable attDown = new TextureRegionDrawable(attachmentsTextureDown);
        attachmentsButton = new ImageButton(attUp, attDown);

        Drawable arrUp = new TextureRegionDrawable(arrowLeftTextureUp);
        Drawable arrDown = new TextureRegionDrawable(arrowLeftTextureDown);
        arrowBackwardButton = new ImageButton(arrUp, arrDown);

        arrowBackwardButton.setSize(200f, 200f);
        arrowBackwardButton.setPosition(Constants.WIDTH/2f - (shipWidth/2f) - arrowBackwardButton.getWidth(),
                Constants.HEIGHT/2f - (shipHeight/2f));


        Drawable arrForwardUp = new TextureRegionDrawable(arrowRightTextureUp);
        Drawable arrForwardDown = new TextureRegionDrawable(arrowRightTextureDown);
        arrowForwardButton = new ImageButton(arrForwardUp, arrForwardDown);

        arrowForwardButton.setSize(200f, 200f);
        arrowForwardButton.setPosition(Constants.WIDTH/2f + (shipWidth/2f),
                Constants.HEIGHT/2f - (shipHeight/2f));


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
