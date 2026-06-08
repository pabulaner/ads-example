package com.out_of_box_games.firewall.world.cpu;

import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.data.cpu.CpuData;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.firewall.world.tower.Tower;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.util.collection.SafeCollection;
import com.out_of_box_games.gengine.util.math.MathUtil;
import com.out_of_box_games.gengine.util.math.RandomUtil;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.actor.DataActor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cpu extends Actor implements Persist<CpuData> {

    public static final int USAGE_STEPS = 8;

    public static final float MAX_USAGE = 100.0f;

    private static final float RANDOM = 4.0f;

    private final Map<TowerType, Integer> points;

    private final Collection<Task> tasks;

    private final Event<Void> onPointsChange;

    public Cpu() {
        points = new HashMap<>();
        tasks = new SafeCollection<>();
        onPointsChange = new Event<>();

        for (TowerType type : TowerType.values()) {
            points.put(type, 1);
        }
    }

    @Override
    public void load(CpuData data) {
        if (data.getPoints() != null) {
            points.clear();
            points.putAll(data.getPoints());
        }
    }

    @Override
    public CpuData save() {
        return new CpuData().setPoints(points);
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        float usage = 0.0f;

        for (Iterator<Task> it = tasks.iterator(); it.hasNext(); ) {
            Task task = it.next();
            float free = getTotalUsagePercent() * getCores() - usage;
            float time = delta;

            float taskUsage = task.getUsage();
            float taskTime = task.getTime();

            if (free <= 0.0f) {
                return;
            } else if (free < taskUsage) {
                time *= Math.min(free, MAX_USAGE) / taskUsage;
            }

            runTask(task);

            usage += taskUsage;
            taskTime -= time;

            if (taskTime <= 0.0f) {
                task.getOnEnd().run();
                it.remove();
            } else {
                task.setTime(taskTime);
            }
        }
    }

    private void runTask(Task task) {
        Runnable onBegin = task.getOnBegin();

        if (onBegin != null) {
            onBegin.run();
            task.setOnBegin(null);
        }
    }

    public void addTask(Task task) {
        if (isFree()) {
            runTask(task);
        }

        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public int getFreePoints() {
        GameModeBase gameMode = getWorld().getGameMode();
        int level = getWorld().getActors(Tower.class)
                .stream()
                .map(DataActor::getData)
                .filter(value -> value.getType() == TowerType.CPU)
                .map(TowerData::getLevel)
                .findFirst()
                .orElse(0);

        int total = gameMode.getCpuBasePoints() + gameMode.getCpuPointsPerLevel() * level;
        int used = points.values()
                .stream()
                .map(value -> value - 1)
                .reduce(0, Integer::sum);

        return total - used;
    }

    public int getPoints(TowerType type) {
        return points.get(type);
    }

    public void setPoints(TowerType type, int usage) {
        points.put(type, usage);
        onPointsChange.invoke();
    }

    public float getUsagePercent(TowerType type) {
        return 100.0f * (points.get(type) + 1) / USAGE_STEPS;
    }

    public float getUsagePercentRandomized(int id) {
        float usage = 0.0f;

        for (Task task : tasks) {
            float taskUsage = task.getUsage();
            float free = getTotalUsagePercent() * getCores() - usage;

            if (task.getId() == id) {
                float result = Math.min(taskUsage, free);

                if (result > 0.0f) {
                    result += RandomUtil.randomFloat(-RANDOM, RANDOM);
                    result = MathUtil.clamp(result, 0.0f, MAX_USAGE);
                }

                return result;
            } else {
                usage += taskUsage;
            }
        }

        return 0.0f;
    }

    public float getTotalUsagePercent() {
        return MathUtil.min(tasks.stream()
                .map(Task::getUsage)
                .reduce(Float::sum)
                .orElse(0.0f), getMaxUsagePercent()) / getCores();
    }

    public float getTotalUsagePercentRandomized() {
        float usage = getTotalUsagePercent();

        if (usage > 0.0f) {
            usage += RandomUtil.randomFloat(-RANDOM, RANDOM);
            usage = MathUtil.clamp(usage, 0.0f, MAX_USAGE);
        }

        return usage;
    }

    public float getMaxUsagePercent() {
        return getCores() * MAX_USAGE;
    }

    public boolean isFree() {
        return getTotalUsagePercent() < getMaxUsagePercent();
    }

    private int getCores() {
        GameStateBase gameState = getWorld().getGameState();
        return gameState.getCores();
    }

    public Event<Void> onPointsChange() {
        return onPointsChange;
    }
}
