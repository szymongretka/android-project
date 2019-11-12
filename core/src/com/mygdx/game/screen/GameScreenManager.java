package com.mygdx.game.screen;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.menu.MainMenuScreen;

import java.util.HashMap;
import java.util.Map;

public class GameScreenManager {

    public final MyGdxGame game;
    private Map<GameState, AbstractScreen> gameScreens;

    public GameScreenManager(final MyGdxGame game) {
        this.game = game;
        initScreens();
        setScreen(GameState.MENU);
    }

    private void initScreens() {
        gameScreens = new HashMap<GameState, AbstractScreen>();
        gameScreens.put(GameState.MENU, new MainMenuScreen(game));

    }

    public void setScreen(GameState screen) {
        game.setScreen(gameScreens.get(screen));
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if(screen != null) {
                screen.dispose();
            }
        }
    }

}
