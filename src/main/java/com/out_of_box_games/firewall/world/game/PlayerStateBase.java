package com.out_of_box_games.firewall.world.game;

import com.out_of_box_games.firewall.data.EnemyDisplayMode;
import com.out_of_box_games.firewall.data.TowerDisplayMode;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.world.actor.PlayerState;

public class PlayerStateBase extends PlayerState {

    private TowerDisplayMode towerDisplayMode;

    private EnemyDisplayMode enemyDisplayMode;

    private final Event<Void> onModeChange;

    private final Event<Void> onUpdateUsage;

    public PlayerStateBase() {
        towerDisplayMode = TowerDisplayMode.LEVEL;
        enemyDisplayMode = EnemyDisplayMode.HEALTH;
        onModeChange = new Event<>();
        onUpdateUsage = new Event<>();
    }

    public TowerDisplayMode getTowerDisplayMode() {
        return towerDisplayMode;
    }

    public void setTowerDisplayMode(TowerDisplayMode towerDisplayMode) {
        this.towerDisplayMode = towerDisplayMode;
        onModeChange.invoke();
    }

    public EnemyDisplayMode getEnemyDisplayMode() {
        return enemyDisplayMode;
    }

    public void setEnemyDisplayMode(EnemyDisplayMode enemyDisplayMode) {
        this.enemyDisplayMode = enemyDisplayMode;
        onModeChange.invoke();
    }

    public Event<Void> onModeChange() {
        return onModeChange;
    }

    public Event<Void> onUpdateUsage() {
        return onUpdateUsage;
    }
}
