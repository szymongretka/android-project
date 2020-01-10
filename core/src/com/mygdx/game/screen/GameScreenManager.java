package com.mygdx.game.screen;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.SpaceInvaderApp;
import com.mygdx.game.enums.GameState;
import com.mygdx.game.screen.event.YouDiedScreen;
import com.mygdx.game.screen.event.YouWinScreen;
import com.mygdx.game.screen.game.GameScreen;
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
    private GameState previousActiveScreen;

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
            /*if(clazz.getClass().equals(GameScreen.class)) {
                gameScreens.put(gameStateScreen, new GameScreen(game));
            } else {*/
                gameScreens.putIfAbsent(gameStateScreen, clazz.getConstructor(game.getClass()).newInstance(game));
            //}
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
        GameScreen.NUMBER_OF_BULLETS = 1;
        this.gameScreens.clear();
    }

    public GameState getPreviousActiveScreen() {
        return previousActiveScreen;
    }

    public void setPreviousActiveScreen(GameState previousActiveScreen) {
        this.previousActiveScreen = previousActiveScreen;
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
                }, 2.5f);

                return true;
            case MessageType.YOU_DIED_SCREEN:
                clearGameStateMap();
                setActiveScreen(GameState.YOU_DIED_SCREEN);
                setScreen(GameState.YOU_DIED_SCREEN, (Class<T>) YouDiedScreen.class);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        setActiveScreen(GameState.LEVELSCREEN);
                        setScreen(GameState.LEVELSCREEN, (Class<T>) LevelScreen.class);
                    }
                }, 2.5f);

                return true;
        }

        return false;
    }
}
