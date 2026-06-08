package com.out_of_box_games.firewall.data.tower;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.gengine.data.TypeData;

import java.util.List;

public class TowerStaticData extends TypeData<TowerStaticData, TowerType> {

    private String name;

    private List<EnemyType> weakAgainst;

    private List<EnemyType> strongAgainst;

    private float cost;

    private float damage;
    
    private float reload;

    private float range;

    public String getName() {
        return name;
    }

    public TowerStaticData setName(String name) {
        this.name = name;
        return this;
    }

    public List<EnemyType> getWeakAgainst() {
        return weakAgainst;
    }

    public TowerStaticData setWeakAgainst(List<EnemyType> weakAgainst) {
        this.weakAgainst = weakAgainst;
        return this;
    }

    public List<EnemyType> getStrongAgainst() {
        return strongAgainst;
    }

    public TowerStaticData setStrongAgainst(List<EnemyType> strongAgainst) {
        this.strongAgainst = strongAgainst;
        return this;
    }

    public float getCost() {
        return cost;
    }

    public TowerStaticData setCost(float cost) {
        this.cost = cost;
        return this;
    }

    public float getDamage() {
        return damage;
    }

    public TowerStaticData setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public float getReload() {
        return reload;
    }

    public TowerStaticData setReload(float reload) {
        this.reload = reload;
        return this;
    }

    public float getRange() {
        return range;
    }

    public TowerStaticData setRange(float range) {
        this.range = range;
        return this;
    }
}
