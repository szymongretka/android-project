package com.mygdx.game.screen.event;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.util.MyPreferences;

public class YouDiedScreen extends AbstractScreen {

    private BitmapFont font;
    private MyPreferences myPreferences;

    public YouDiedScreen(SpaceInvaderApp game) {
        super(game);

        myPreferences = new MyPreferences();
        myPreferences.updatePoints();

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

        font.draw(game.batch, "You Died...", 20, 20);

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
