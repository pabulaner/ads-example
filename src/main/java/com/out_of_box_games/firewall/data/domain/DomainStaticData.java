package com.out_of_box_games.firewall.data.domain;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.gengine.data.TypeData;

import java.util.List;

public class DomainStaticData extends TypeData<DomainStaticData, String> {

    private List<EnemyType> enemies;

    public List<EnemyType> getEnemies() {
        return enemies;
    }

    public DomainStaticData setEnemies(List<EnemyType> enemies) {
        this.enemies = enemies;
        return this;
    }
}
