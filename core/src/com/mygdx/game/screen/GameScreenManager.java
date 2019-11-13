package com.mygdx.game.screen;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.menu.MainMenuScreen;

import java.util.HashMap;
import java.util.Map;

public class GameScreenManager {

    public final MyGdxGame game;
    private Map<GameState, AbstractScreen> gameScreens;
    private MainMenuScreen mainMenuScreen;

    public GameScreenManager(final MyGdxGame game) {
        this.game = game;
        this.mainMenuScreen = new MainMenuScreen(game);
        initScreens(mainMenuScreen);
        setScreen(GameState.MENU, mainMenuScreen);
    }

    private void initScreens(MainMenuScreen mainMenuScreen) {
        gameScreens = new HashMap<GameState, AbstractScreen>();
        gameScreens.put(GameState.MENU, mainMenuScreen);

    }

    public void setScreen(GameState gameStateScreen, AbstractScreen screen) {
        if (gameScreens.containsValue(gameStateScreen))
            game.setScreen(gameScreens.get(gameStateScreen));
        else {
            gameScreens.put(gameStateScreen, screen);
            game.setScreen(gameScreens.get(gameStateScreen));
        }
        //game.setScreen(gameScreens.get(gameStateScreen));
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if(screen != null) {
                screen.dispose();
            }
        }
    }

}
