package com.out_of_box_games.firewall.data.tower;

import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.gengine.data.TypeData;

public class TowerData extends TypeData<TowerData, TowerType> {

    private int level;

    public int getLevel() {
        return level;
    }

    public TowerData setLevel(int level) {
        this.level = level;
        return this;
    }
}
