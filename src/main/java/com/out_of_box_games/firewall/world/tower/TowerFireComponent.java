package com.out_of_box_games.firewall.world.tower;

import com.out_of_box_games.firewall.data.projectile.ProjectileData;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.firewall.world.cpu.Cpu;
import com.out_of_box_games.firewall.world.cpu.Task;
import com.out_of_box_games.firewall.world.enemy.Enemy;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.projectile.Projectile;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Component;

public class TowerFireComponent extends Component {

    private Task task;

    private final TowerAimerComponent aimerComponent;

    public TowerFireComponent(TowerAimerComponent aimerComponent) {
        this.task = null;
        this.aimerComponent = aimerComponent;
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();

        if (task != null) {
            GameModeBase gameMode = getActor().getWorld().getGameMode();
            gameMode.getCpu().removeTask(task);
        }
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);

        if (task != null) {
            return;
        }

        Enemy target = aimerComponent.find();

        if (target != null) {
            GameModeBase gameMode = getActor().getWorld().getGameMode();
            Tower tower = getActor();
            TowerData data = tower.getData();
            Cpu cpu = gameMode.getCpu();
            float usage = cpu.getUsagePercent(data.getType());

            task = new Task()
                    .setId(tower.getId())
                    .setUsage(usage)
                    .setTime(gameMode.getTowerReload(data.getType(), data.getLevel(), usage))
                    .setOnBegin(() -> fire(target))
                    .setOnEnd(() -> task = null);

            cpu.addTask(task);
        }
    }

    private void fire(Enemy enemy) {
        GameModeBase gameMode = getActor().getWorld().getGameMode();

        if (enemy.isDestroyed() || enemy != aimerComponent.find()) {
            gameMode.getCpu().removeTask(task);
            task = null;
        }

        Tower tower = getActor();
        TowerData data = tower.getData();
        Vector2 origin = tower.getRoot().getGlobalTranslation();
        Vector2 target = aimerComponent.target(enemy);

        if (target == null) {
            return;
        }

        Persist.create(new Projectile(), new ProjectileData()
                .setType(data.getType())
                .setLevel(data.getLevel())
                .setUsage(gameMode.getCpu().getUsagePercent(data.getType()))
                .setDistance(gameMode.getTowerRange(data.getType(), data.getLevel()))
                .setDirection(target.sub(origin).normalize())
                .setTranslation(origin), tower.getWorld());
    }
}
