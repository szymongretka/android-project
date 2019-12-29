package com.mygdx.game.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.util.Constants;

public class LoadingScreen extends AbstractScreen {

    private ShapeRenderer shapeRenderer;
    private float progress = 0f;


    public LoadingScreen(final SpaceInvaderApp game) {
        super(game);
        this.shapeRenderer = new ShapeRenderer();
        queueAssets();
    }

    @Override
    public void update(float delta) {
        progress = MathUtils.lerp(progress, game.assets.manager.getProgress(), .1f);
        if(game.assets.manager.update() && progress <= game.assets.manager.getProgress() - .001f) {
           // game.gameScreenManager.setActiveScreen(GameState.MENU);
            game.gameScreenManager.setScreen(GameState.MENU, MainMenuScreen.class);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(48, Constants.HEIGHT/2, Constants.WIDTH - 48, 32);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(48, Constants.HEIGHT/2, progress * (Constants.WIDTH - 48), 32);
        shapeRenderer.end();

        game.batch.begin();
        game.font.draw(game.batch, "Loading...", 20, 20);
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

    private void queueAssets() {
        game.assets.load();
    }

}
