package com.mygdx.game.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    public final AssetManager manager = new AssetManager();

    public void load() {

        //TODO pack all textures into atlas

        //music & sounds
        manager.load("music/level1Music.wav", Music.class);
        manager.load("music/sfx-laser.wav", Sound.class);
        manager.load("music/score.wav", Sound.class);

        //menu
        manager.load("menu/pause.png", Texture.class);

        //waves
        manager.load("rawImages/waves/wave1.png", Texture.class);
        manager.load("rawImages/waves/wave2.png", Texture.class);
        manager.load("rawImages/waves/wave3.png", Texture.class);
        manager.load("rawImages/waves/wave4.png", Texture.class);
        manager.load("rawImages/waves/wave5.png", Texture.class);
        manager.load("rawImages/waves/wave6.png", Texture.class);

        //items
        manager.load("revert.png", Texture.class);
        manager.load("coin.png", Texture.class);
        manager.load("shield.png", Texture.class);

        //skins
        manager.load("data/uiskin.json", Skin.class);

        //Particle effects
        manager.load("effects/explosion.flame", ParticleEffect.class);
        manager.load("effects/engine2.flame", ParticleEffect.class);

        //backgrounds
        manager.load("background/lvl1.jpg", Texture.class);

        //atlas
        manager.load("packedImages/playerAndEnemies.atlas", TextureAtlas.class);

        //bullets
        manager.load("bullet.png", Texture.class);
        manager.load("bullet2.png", Texture.class);
        manager.load("bullet3.png", Texture.class);

    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
