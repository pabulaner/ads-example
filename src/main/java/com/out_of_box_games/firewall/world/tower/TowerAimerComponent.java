package com.out_of_box_games.firewall.world.tower;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.data.tower.TowerStaticData;
import com.out_of_box_games.firewall.world.enemy.Enemy;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.map.MapPathFollowComponent;
import com.out_of_box_games.firewall.world.projectile.ProjectileMovementComponent;
import com.out_of_box_games.gengine.util.math.MathUtil;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.Component;
import com.out_of_box_games.gengine.world.component.PathComponent;

public class TowerAimerComponent extends Component {

    private Enemy last;

    public TowerAimerComponent() {
        last = null;
    }

    public Enemy find() {
        Tower tower = getActor();

        if (last != null && isValid(last)) {
            return last;
        } else {
            last = null;
        }

        Enemy[] result = { null };

        Enemy.forEach(tower.getMap(), enemy -> {
            if (result[0] == null && isValid(enemy)) {
                result[0] = enemy;
            }
        });

        last = result[0];
        return last;
    }

    public Vector2 target(Enemy enemy) {
        float distance = MathUtil.sqrt(getDistanceSquared(enemy));
        float time = distance / ProjectileMovementComponent.SPEED;

        MapPathFollowComponent follow = enemy.getPathFollowComponent();
        PathComponent path = follow.getParent();

        if (path == null) {
            return null;
        }

        Vector2 result = path.sample(follow.getProgress() + follow.getSpeed() * time);
        return path.getGlobalTransform().applyTo(result);
    }

    private boolean isValid(Enemy enemy) {
        if (enemy.isDone()) {
            return false;
        }

        GameModeBase gameMode = getActor().getWorld().getGameMode();
        Tower tower = getActor();
        TowerStaticData data = tower.getStaticData();
        float rangeSquared = MathUtil.pow(gameMode.getTowerRange(data.getType(), tower.getData().getLevel()), 2.0f);

        EnemyType enemyType = enemy.getType();
        float distanceSquared = getDistanceSquared(enemy);

        return Tower.canTarget(data.getType(), enemyType) && distanceSquared <= rangeSquared;
    }

    private float getDistanceSquared(Actor actor) {
        Vector2 tower = getActor().getRoot().getGlobalTranslation();
        Vector2 enemy = actor.getRoot().getGlobalTranslation();

        return enemy.sub(tower).lengthSquared();
    }
}
