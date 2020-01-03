package com.mygdx.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
import com.mygdx.game.game_object.player.ship.BasicShip;
import com.mygdx.game.game_object.player.ship.BigShip;
import com.mygdx.game.game_object.player.ship.Ship;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.MyPreferences;


public class SpaceshipScreen extends AbstractScreen {

    private Stage stage;
    private ImageButton backButton;
    private ImageButton attachmentsButton;
    private ImageButton arrowForwardButton;
    private ImageButton arrowBackwardButton;
    private ImageButton saveButton;
    private ImageButton selectButton;
    private ImageButton buyButton;

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

    private TextureRegion saveTextureUp;
    private TextureRegion saveTextureDown;

    private TextureRegion selectTextureUp;
    private TextureRegion selectTextureDown;

    private TextureRegion buyTextureUp;
    private TextureRegion buyTextureDown;

    private Table table;

    private float shipWidth = 400f, shipHeight = 400f;
    private Integer shipNumber = 0;

    private Ship[] shipArray;
    private Integer playerMoney;
    private MyPreferences preferences;
    private Skin skin;
    private Label playerMoneyLabel;
    private Label shipPriceLabel;

    public SpaceshipScreen(final SpaceInvaderApp game) {
        super(game);


        if(game.assets.manager.isFinished()){
            loadAssets();
        }

        preferences = game.myPreferences;
        playerMoney = preferences.getPoints();
        shipArray = preferences.getShipArray();
        for (Ship ship : shipArray) {
            if(ship instanceof BasicShip)
                ship.setTextureRegion(basicShipTex);
            else if(ship instanceof BigShip)
                ship.setTextureRegion(bigShipTex);
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
                game.gameScreenManager.setActiveScreen(GameState.MENU);
                game.gameScreenManager.setScreen(GameState.MENU, MainMenuScreen.class);
            }
        });

        arrowForwardButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                if(shipNumber < shipArray.length - 1)
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

        attachmentsButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                //todo
            }
        });

        selectButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                preferences.setActiveShip(shipArray[shipNumber]);
            }
        });


        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                //TODO
            }
        });

        buyButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                playerMoney = playerMoney - shipArray[shipNumber].getPrice();
                shipArray[shipNumber].setAvailable(true);
                preferences.buyShip(shipArray[shipNumber].getClass());
                preferences.setNewAmountOfPoints(playerMoney);
            }
        });


        playerMoneyLabel = new Label("Money: " + playerMoney, skin);
        shipPriceLabel = new Label("Cost: " + shipArray[shipNumber].getPrice() , skin);
        skin.getFont("default-font").getData().setScale(2f);

        table.add(playerMoneyLabel).padBottom(100f).colspan(2).center();
        table.row();
        table.add(attachmentsButton).colspan(2).center().height(200f).width(350f).padBottom(800f);
        table.row();
        table.add(shipPriceLabel).padBottom(100f).colspan(2).center();
        table.row();
        table.add(selectButton).height(200f).width(350f);
        table.add(buyButton).height(200f).width(350f);
        table.row();
        table.add(saveButton).colspan(2).center().height(200f).width(350f);

        stage.addActor(table);
        stage.addActor(backButton);
        stage.addActor(arrowBackwardButton);
        stage.addActor(arrowForwardButton);

    }

    @Override
    public void update(float delta) {
        Ship ship = shipArray[shipNumber];
        if(ship.getPrice() > playerMoney || ship.isAvailable()) { //ship is not available to buy if player doesn't have enough money or already bought it
            buyButton.setTouchable(Touchable.disabled);
        } else {
            buyButton.setTouchable(Touchable.enabled);
        }
        if(!ship.isAvailable()) {
            selectButton.setTouchable(Touchable.disabled);
        } else {
            selectButton.setTouchable(Touchable.enabled);
        }
        shipPriceLabel.setText("Cost: " + shipArray[shipNumber].getPrice());
        playerMoneyLabel.setText("Money: " + playerMoney);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(shipArray[shipNumber].getTextureRegion(), Constants.WIDTH/2f - (shipWidth/2f),
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

        buyTextureUp = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/BUY"));
        buyTextureDown = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/BUYk"));

        selectTextureUp = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/SELECT"));
        selectTextureDown = new TextureRegion(textureAtlas.findRegion("menu/customize_spaceship/SELECTk"));

        saveTextureUp = new TextureRegion(textureAtlas.findRegion("menu/SAVE"));
        saveTextureDown = new TextureRegion(textureAtlas.findRegion("menu/SAVEk"));

        skin = game.assets.manager.get("skin/uiskin.json", Skin.class);
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

        Drawable saveUp = new TextureRegionDrawable(saveTextureUp);
        Drawable saveDown = new TextureRegionDrawable(saveTextureDown);
        saveButton = new ImageButton(saveUp, saveDown);

        Drawable selectUp = new TextureRegionDrawable(selectTextureUp);
        Drawable selectDown = new TextureRegionDrawable(selectTextureDown);
        selectButton = new ImageButton(selectUp, selectDown);

        Drawable buyUp = new TextureRegionDrawable(buyTextureUp);
        Drawable buyDown = new TextureRegionDrawable(buyTextureDown);
        buyButton = new ImageButton(buyUp, buyDown);

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
//        stage.dispose();
    }
}
