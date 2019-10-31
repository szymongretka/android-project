package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.menu.MainMenuScreen;
import com.mygdx.game.util.Assets;

public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
    public Assets assets;

    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        assets = new Assets();
        this.setScreen(new MainMenuScreen(this));
    }

	public void render() {
		super.render();
	}

	public void dispose() {
        batch.dispose();
        font.dispose();
        assets.dispose();
	}



}