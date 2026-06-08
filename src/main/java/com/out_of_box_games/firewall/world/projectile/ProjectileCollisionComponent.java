package com.out_of_box_games.firewall.world.projectile;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.world.enemy.Enemy;
import com.out_of_box_games.firewall.world.enemy.EnemyHealthComponent;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.firewall.world.tower.Tower;
import com.out_of_box_games.gengine.util.math.MathUtil;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.Component;
import com.out_of_box_games.gengine.world.World;

public class ProjectileCollisionComponent extends Component {

    private static final float RADIUS_SQUARED = MathUtil.pow(30.0f, 2.0f);

    private Map map;

    private TowerType type;

    private int level;

    private float usage;

    public ProjectileCollisionComponent() {
        map = null;
        type = null;
        level = 0;
        usage = 0.0f;
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        boolean[] hit = { false };

        Enemy.forEach(map, enemy -> {
            if (hit[0]) {
                return;
            }

            Actor actor = getActor();
            Vector2 projectileTranslation = actor.getRoot().getGlobalTranslation();
            Vector2 enemyTranslation = enemy.getRoot().getGlobalTranslation();

            EnemyType enemyType = enemy.getType();
            float distanceSquared = enemyTranslation.sub(projectileTranslation).lengthSquared();

            if (Tower.canTarget(type, enemyType) && distanceSquared <= RADIUS_SQUARED && !enemy.isDone()) {
                hit[0] = true;

                World world = actor.getWorld();
                GameModeBase gameMode = world.getGameMode();
                EnemyHealthComponent healthComponent = enemy.getHealthComponent();
                float damage = gameMode.getTowerDamage(type, level, usage, enemyType);

                healthComponent.damage(damage);
                actor.destroy();
            }
        });
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public TowerType getType() {
        return type;
    }

    public void setType(TowerType type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getUsage() {
        return usage;
    }

    public void setUsage(float usage) {
        this.usage = usage;
    }
}
