package com.out_of_box_games.gengine.world;

import com.out_of_box_games.gengine.world.actor.GameMode;
import com.out_of_box_games.gengine.world.actor.GameState;
import com.out_of_box_games.gengine.world.actor.PlayerState;
import com.out_of_box_games.gengine.world.actor.UI;

import java.util.List;

public class Level {

    public GameMode getGameMode() {
        return new GameMode();
    }

    public GameState getGameState() {
        return new GameState();
    }

    public PlayerState getPlayerState() {
        return new PlayerState();
    }

    public UI getUI() {
        return new UI();
    }

    public List<Actor> getActors() {
        return List.of();
    }
}
