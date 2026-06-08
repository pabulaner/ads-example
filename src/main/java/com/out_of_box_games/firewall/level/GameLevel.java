package com.out_of_box_games.firewall.level;

import com.out_of_box_games.firewall.ui.game.GameUI;
import com.out_of_box_games.firewall.world.game.EndlessGameMode;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.firewall.world.game.PlayerStateBase;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.Level;
import com.out_of_box_games.gengine.world.actor.GameMode;
import com.out_of_box_games.gengine.world.actor.GameState;
import com.out_of_box_games.gengine.world.actor.PlayerState;
import com.out_of_box_games.gengine.world.actor.UI;
import com.out_of_box_games.gengine.world.component.CameraComponent;

import java.util.List;

public class GameLevel extends Level {

    private final int level;

    public GameLevel(int level) {
        this.level = level;
    }

    @Override
    public GameMode getGameMode() {
        GameModeBase gameMode = new EndlessGameMode();
        gameMode.setLevel(level);

        return gameMode;
    }

    @Override
    public GameState getGameState() {
        return new GameStateBase();
    }

    @Override
    public PlayerState getPlayerState() {
        return new PlayerStateBase();
    }

    @Override
    public UI getUI() {
        return new GameUI();
    }

    @Override
    public List<Actor> getActors() {
        Actor camera = new Actor();
        camera.setRoot(camera.addComponent(new CameraComponent()));

        return List.of(camera);
    }
}
