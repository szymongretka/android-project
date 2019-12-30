package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.networking.ScoreService;
import com.mygdx.game.networking.ShipService;
import com.mygdx.game.screen.GameScreenManager;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.MyPreferences;

public class SpaceInvaderApp extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	//Managers
    public Assets assets;
    public GameScreenManager gameScreenManager;
    public MessageManager messageManager = MessageManager.getInstance();
    public MyPreferences myPreferences;
    public TextureAtlas textureAtlas;

    //services
    private ShipService shipService;
    private ScoreService scoreService;

    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        assets = new Assets();
        myPreferences = new MyPreferences();
        gameScreenManager = new GameScreenManager(this);

        shipService = new ShipService();
        scoreService = new ScoreService();
    }

	public void render() {
		super.render();
	}

	public void dispose() {
        gameScreenManager.dispose();
        //batch.dispose();
        font.dispose();
        assets.dispose();
	}


    public ShipService getShipService() {
        return shipService;
    }

    public ScoreService getScoreService() {
        return scoreService;
    }
}