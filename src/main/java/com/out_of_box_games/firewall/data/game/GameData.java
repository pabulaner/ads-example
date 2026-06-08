package com.out_of_box_games.firewall.data.game;

import com.out_of_box_games.firewall.data.cpu.CpuData;
import com.out_of_box_games.firewall.data.map.MapData;
import com.out_of_box_games.firewall.data.wave.WaveData;
import com.out_of_box_games.gengine.data.Data;

public class GameData implements Data {

    private float health;

    private float cash;

    private CpuData cpu;

    private MapData map;

    private WaveData wave;

    public float getHealth() {
        return health;
    }

    public GameData setHealth(float health) {
        this.health = health;
        return this;
    }

    public float getCash() {
        return cash;
    }

    public GameData setCash(float cash) {
        this.cash = cash;
        return this;
    }

    public CpuData getCpu() {
        return cpu;
    }

    public GameData setCpu(CpuData cpu) {
        this.cpu = cpu;
        return this;
    }

    public MapData getMap() {
        return map;
    }

    public GameData setMap(MapData map) {
        this.map = map;
        return this;
    }

    public WaveData getWave() {
        return wave;
    }

    public GameData setWave(WaveData wave) {
        this.wave = wave;
        return this;
    }
}
