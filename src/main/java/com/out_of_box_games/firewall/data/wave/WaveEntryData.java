package com.out_of_box_games.firewall.data.wave;

import com.out_of_box_games.firewall.data.enemy.EnemyData;
import com.out_of_box_games.gengine.data.Data;

public class WaveEntryData implements Data {

    private int begin;

    private EnemyData enemy;

    private float offset;

    public int getBegin() {
        return begin;
    }

    public WaveEntryData setBegin(int begin) {
        this.begin = begin;
        return this;
    }

    public EnemyData getEnemy() {
        return enemy;
    }

    public WaveEntryData setEnemy(EnemyData enemy) {
        this.enemy = enemy;
        return this;
    }

    public float getOffset() {
        return offset;
    }

    public WaveEntryData setOffset(float offset) {
        this.offset = offset;
        return this;
    }
}
