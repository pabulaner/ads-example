package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.firewall.world.enemy.Enemy;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.gengine.world.World;

public class MapEndNode extends MapPathNode {

    protected MapEndNode(int index) {
        super("OUT", index);
    }

    @Override
    public void handle(MapPathFollowComponent follow) {
        // TODO: fix this dependency, map should not depend on enemy
        Enemy enemy = follow.getActor();
        World world = enemy.getWorld();
        GameModeBase gameMode = world.getGameMode();
        GameStateBase gameState = world.getGameState();

        if (!enemy.isDone()) {
            gameState.setHealth(gameState.getHealth() - gameMode.getEnemyDamage());
        }

        follow.getActor().destroy();
    }
}
