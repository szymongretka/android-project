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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.util.Constants.BASIC_SHIP;
import static com.mygdx.game.util.Constants.BIG_SHIP;


public class SetNicknameScreen extends AbstractScreen {


    private Stage stage;

    private TextureAtlas textureAtlas;
    private Texture background;
    private TextureRegion saveTextureUp;
    private TextureRegion saveTextureDown;

    private Skin skin;
    private Table table;
    private ImageButton saveButton;
    private TextField nicknameTextField;

    private MyPreferences preferences;

    public SetNicknameScreen(final SpaceInvaderApp game) {
        super(game);

        if (game.assets.manager.isFinished()) {
            loadAssets();
        }

        preferences = game.myPreferences;
        initShipMap();

        stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        Drawable saveUp = new TextureRegionDrawable(saveTextureUp);
        Drawable saveDown = new TextureRegionDrawable(saveTextureDown);
        saveButton = new ImageButton(saveUp, saveDown);

        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                game.myPreferences.setNickname(nicknameTextField.getText());
                game.gameScreenManager.setActiveScreen(GameState.MENU);
                game.gameScreenManager.setScreen(GameState.MENU, MainMenuScreen.class);
            }
        });


        skin.getFont("default-font").getData().setScale(3f);

        nicknameTextField = new TextField("", skin);
        nicknameTextField.setPosition(Constants.WIDTH / 4f, Constants.HEIGHT / 2f);

        table.padTop(Constants.HEIGHT / 4f);
        table.add(nicknameTextField).height(200f).width(Constants.WIDTH / 2f).padBottom(30);
        table.row();
        table.add(saveButton).height(200f).width(500f).padBottom(30);
        table.row();


        stage.addActor(table);

    }

    private void initShipMap() {

        BasicShip basicShip = new BasicShip();
        BigShip bigShip = new BigShip();

        basicShip.setActive(true);
        basicShip.setAvailable(true);

        bigShip.setActive(false);
        bigShip.setAvailable(false);

        preferences.initShipMap(new Ship[]{basicShip, bigShip});
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

    private void loadAssets() {
        textureAtlas = game.assets.manager.get("packedImages/playerAndEnemies.atlas", TextureAtlas.class);
        background = game.assets.manager.get("background/menu_background.png", Texture.class);
        skin = game.assets.manager.get("skin/uiskin.json", Skin.class);

        saveTextureUp = new TextureRegion(textureAtlas.findRegion("menu/SAVE"));
        saveTextureDown = new TextureRegion(textureAtlas.findRegion("menu/SAVEk"));
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
}
