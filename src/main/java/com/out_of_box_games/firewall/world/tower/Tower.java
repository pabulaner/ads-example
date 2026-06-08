package com.out_of_box_games.firewall.world.tower;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.data.TowerRegistry;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.firewall.data.tower.TowerStaticData;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.world.actor.DataActor;
import com.out_of_box_games.gengine.world.component.TransformComponent;

public class Tower extends DataActor<TowerType, TowerData, TowerStaticData> {

    public static final int MAX_LEVEL = 15;

    public static final float BASE_USAGE = 4.0f;

    private Map map;

    private final TowerAimerComponent aimerComponent;

    private final TowerFireComponent fireComponent;

    private final Event<Void> onUpgrade;

    public Tower() {
        super(TowerRegistry.getInstance());

        map = null;
        aimerComponent = addComponent(new TowerAimerComponent());
        fireComponent = addComponent(new TowerFireComponent(aimerComponent));
        onUpgrade = new Event<>();

        setRoot(addComponent(new TransformComponent()));
    }

    @Override
    public void load(TowerData data) {
        super.load(data);

        GameModeBase gameMode = getWorld().getGameMode();
        map = gameMode.getMap();
    }

    @Override
    public TowerData save() {
        return super.save();
    }

    public static boolean canTarget(TowerType towerType, EnemyType enemyType) {
        TowerStaticData data = TowerRegistry.getInstance().get(towerType);
        return data.getStrongAgainst().contains(enemyType) || data.getWeakAgainst().contains(enemyType);
    }

    public void upgrade() {
        TowerData data = getData();
        data.setLevel(data.getLevel() + 1);

        if (data.getType() == TowerType.CPU) {
            GameStateBase gameState = getWorld().getGameState();
            gameState.setCores(data.getLevel() + 1);
        }

        onUpgrade.invoke();
    }

    public int getId() {
        for (int i = 0; i < map.getPlatforms().size(); i++) {
            if (map.getPlatforms().get(i).getTower() == this) {
                return i;
            }
        }

        return -1;
    }

    public Map getMap() {
        return map;
    }

    public Event<Void> onUpgrade() {
        return onUpgrade;
    }
}
