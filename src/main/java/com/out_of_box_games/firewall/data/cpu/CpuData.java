package com.out_of_box_games.firewall.data.cpu;

import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.gengine.data.Data;

import java.util.Map;

public class CpuData implements Data {

    private Map<TowerType, Integer> points;

    public Map<TowerType, Integer> getPoints() {
        return points;
    }

    public CpuData setPoints(Map<TowerType, Integer> points) {
        this.points = points;
        return this;
    }
}
