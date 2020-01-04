package com.mygdx.game.game_object.player;

import com.mygdx.game.game_object.player.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String nickname;
    private List<Ship> ships = new ArrayList<>();

    public Player(String nickname, List<Ship> ships) {
        this.nickname = nickname;
        this.ships = ships;
    }

}
