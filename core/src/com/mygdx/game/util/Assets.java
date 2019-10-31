package com.mygdx.game.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    public final AssetManager manager = new AssetManager();

    public void load() {
        manager.load("spaceship.png", Texture.class);
        manager.load("droplet.png", Texture.class);
        manager.load("sfx-laser.wav", Sound.class);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
