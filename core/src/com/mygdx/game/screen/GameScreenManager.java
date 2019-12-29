package com.mygdx.game.screen;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.event.YouWinScreen;
import com.mygdx.game.screen.menu.LevelScreen;
import com.mygdx.game.screen.menu.LoadingScreen;
import com.mygdx.game.util.MessageType;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class GameScreenManager<T extends AbstractScreen> implements Telegraph {

    public final SpaceInvaderApp game;
    private Map<GameState, AbstractScreen> gameScreens;

    private GameState activeScreen;

    public GameScreenManager(final SpaceInvaderApp game) {
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

    @Override
    public boolean handleMessage(Telegram msg) {

        switch (msg.message) {
            case MessageType.YOU_WIN_SCREEN:
                clearGameStateMap();
                setActiveScreen(GameState.YOU_WIN_SCREEN);
                setScreen(GameState.YOU_WIN_SCREEN, (Class<T>) YouWinScreen.class);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        setActiveScreen(GameState.LEVELSCREEN);
                        setScreen(GameState.LEVELSCREEN, (Class<T>) LevelScreen.class);
                    }
                }, 2);

                return true;
            case MessageType.YOU_DIED_SCREEN:

                return true;
        }

        return false;
    }
}
