package com.mygdx.game.screen;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.menu.LoadingScreen;
import com.mygdx.game.screen.menu.MainMenuScreen;

import java.util.HashMap;
import java.util.Map;

public class GameScreenManager {

    public final MyGdxGame game;
    private Map<GameState, AbstractScreen> gameScreens;
    private LoadingScreen loadingScreen;
    //private MainMenuScreen mainMenuScreen;

    private GameState activeScreen;

    public GameScreenManager(final MyGdxGame game) {
        this.game = game;
        this.loadingScreen = new LoadingScreen(game);
        initScreens(loadingScreen);
        setScreen(GameState.LOADINGSCREEN, loadingScreen);


        /*this.mainMenuScreen = new MainMenuScreen(game);
        initScreens(mainMenuScreen);
        setScreen(GameState.MENU, mainMenuScreen);*/
    }

    private void initScreens(LoadingScreen loadingScreen) {
        gameScreens = new HashMap<>();
        gameScreens.put(GameState.LOADINGSCREEN, loadingScreen);

        //gameScreens.put(GameState.MENU, mainMenuScreen);
    }

    public void setScreen(GameState gameStateScreen, AbstractScreen screen) {
        if (gameScreens.containsValue(gameStateScreen))
            game.setScreen(gameScreens.get(gameStateScreen));
        else {
            gameScreens.put(gameStateScreen, screen);
            game.setScreen(gameScreens.get(gameStateScreen));
        }
        activeScreen = gameStateScreen;
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if(screen != null) {
                screen.dispose();
            }
        }
    }

    public GameState getActiveScreen() {
        return activeScreen;
    }

    public void setActiveScreen(GameState gameState) {
        this.activeScreen = gameState;
    }
}
