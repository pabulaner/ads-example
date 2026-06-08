package com.out_of_box_games.firewall.world.enemy;

import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.gengine.world.component.ValueComponent;

public class EnemyHealthComponent extends ValueComponent<Float> {

    public EnemyHealthComponent() {
        super(0.0f);

        onValueMin().addListener(ignore -> {
            Enemy enemy = getActor();
            GameModeBase gameMode = enemy.getWorld().getGameMode();
            GameStateBase gameState = enemy.getWorld().getGameState();
            float cash = gameMode.getEnemyReward(enemy.getType(), getMax(), gameState.getWave());

            gameState.setCash(gameState.getCash() + cash);
        });
    }

    public void heal(float amount) {
        setValue(getValue() + amount);
    }

    public void damage(float amount) {
        setValue(getValue() - amount);
    }
}
