package com.out_of_box_games.firewall.world.wave;

import com.out_of_box_games.firewall.data.enemy.EnemyData;
import com.out_of_box_games.firewall.data.wave.WaveData;
import com.out_of_box_games.firewall.data.wave.WaveEntryData;
import com.out_of_box_games.firewall.world.enemy.Enemy;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.firewall.world.map.MapBeginNode;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.component.TimerComponent;

import java.util.ArrayList;
import java.util.List;

public class WaveManager extends Actor implements Persist<WaveData> {

    private int index;

    private final List<WaveEntryData> entries;

    private final Map map;

    private final TimerComponent timerComponent;

    private final Event<Void> onDone;

    private final Event<Void> onEnemySpawn;

    public WaveManager(Map map) {
        this.index = 0;
        this.entries = new ArrayList<>();
        this.map = map;
        this.timerComponent = addComponent(new TimerComponent());
        this.onEnemySpawn = new Event<>();
        this.onDone = new Event<>();

        timerComponent.onTimeout().addListener(ignored -> spawnEnemy());
    }

    @Override
    public void load(WaveData data) {
        index = data.getIndex() >= 0
                ? data.getIndex()
                : index + 1;

        entries.clear();
        entries.addAll(data.getEntries());

        if (!entries.isEmpty()) {
            spawnEnemy();
        }
    }

    @Override
    public WaveData save() {
        return new WaveData()
                .setIndex(index)
                .setEntries(entries);
    }

    public void start(List<WaveEntryData> entries) {
        load(new WaveData()
                .setIndex(-1)
                .setEntries(entries));
    }

    private void spawnEnemy() {
        if (entries.isEmpty()) {
            onDone.invoke();
            return;
        }

        WaveEntryData entry = entries.remove(0);
        EnemyData data = entry.getEnemy();

        if (data != null) {
            Enemy enemy = Persist.create(new Enemy(), data, getWorld());
            MapBeginNode begin = map.getNodes(MapBeginNode.class).get(entry.getBegin());

            begin.handle(enemy.getPathFollowComponent());
        }

        timerComponent.start(entry.getOffset());
    }

    public Event<Void> onEnemySpawn() {
        return onEnemySpawn;
    }

    public Event<Void> onDone() {
        return onDone;
    }
}
