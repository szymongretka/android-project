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

        //music & sounds
        manager.load("music/level1Music.wav", Music.class);
        manager.load("music/sfx-laser.wav", Sound.class);
        manager.load("music/score.wav", Sound.class);

        //skin
        manager.load("skin/uiskin.json", Skin.class);

        //items
        manager.load("revert.png", Texture.class);
        manager.load("coin.png", Texture.class);
        manager.load("shield.png", Texture.class);

        //Particle effects
        manager.load("effects/explosion.flame", ParticleEffect.class);
        manager.load("effects/engine2.flame", ParticleEffect.class);

        //backgrounds
        manager.load("background/lvl1.jpg", Texture.class);
        manager.load("background/menu_background.png", Texture.class);

        //atlas
        manager.load("packedImages/playerAndEnemies.atlas", TextureAtlas.class);


    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
