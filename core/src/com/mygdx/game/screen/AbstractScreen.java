package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.mygdx.game.MyGdxGame;

public abstract class AbstractScreen implements Screen {

    protected final MyGdxGame game;

    public AbstractScreen(final MyGdxGame game) {
        this.game = game;
    }

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }



}
