package com.mygdx.game.screen;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.menu.LoadingScreen;
import com.mygdx.game.screen.menu.MainMenuScreen;
import com.mygdx.game.screen.pause.PauseScreen;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class GameScreenManager<T extends AbstractScreen> {

    public final MyGdxGame game;
    private Map<GameState, AbstractScreen> gameScreens;

    private GameState activeScreen;

    public GameScreenManager(final MyGdxGame game) {
        this.game = game;
        initScreens();
        setScreen(GameState.LOADINGSCREEN, (Class<T>) LoadingScreen.class);
    }

    private void initScreens() {
        gameScreens = new HashMap<>();
        gameScreens.put(GameState.LOADINGSCREEN, new LoadingScreen(game));
    }

    public void setScreen(GameState gameStateScreen, Class<T> clazz) {
        try {
            gameScreens.putIfAbsent(gameStateScreen, clazz.getConstructor(game.getClass()).newInstance(game));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        game.setScreen(gameScreens.get(gameStateScreen));
    }



    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if(screen != null) {
                screen.dispose();
            }
        }
    }

    public AbstractScreen getGameScreen(GameState state) {
        return gameScreens.get(state);
    }

    public GameState getActiveScreen() {
        return activeScreen;
    }

    public void setActiveScreen(GameState gameState) {
        this.activeScreen = gameState;
    }

    public void clearGameStateMap() {
        this.gameScreens.clear();
    }

}
