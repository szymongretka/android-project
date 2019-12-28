package com.mygdx.game.screen.event;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screen.AbstractScreen;

public class YouWinScreen extends AbstractScreen {

    private BitmapFont font;

    public YouWinScreen(MyGdxGame game) {
        super(game);

        font = new BitmapFont();
        font.setColor(Color.RED);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        super.render(delta);


        game.batch.begin();

        font.draw(game.batch, "You WIn!", 20, 20);

        game.batch.end();
    }

    @Override
    public void show() {

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
