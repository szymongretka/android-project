package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.GameScreenManager;
import com.mygdx.game.util.Assets;

public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	//Managers
    public Assets assets;
    public GameScreenManager gameScreenManager;

    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        assets = new Assets();
        gameScreenManager = new GameScreenManager(this);
    }

	public void render() {
		super.render();
	}

	public void dispose() {
        gameScreenManager.dispose();
        batch.dispose();
        font.dispose();
        assets.dispose();

	}



}