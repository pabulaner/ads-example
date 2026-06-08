package com.out_of_box_games.firewall.data.map;

import com.out_of_box_games.firewall.data.enemy.EnemyData;
import com.out_of_box_games.firewall.data.projectile.ProjectileData;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.gengine.data.TypeData;

import java.util.List;

public class MapData extends TypeData<MapData, Integer> {

    private List<IntersectionData> intersections;

    private List<TowerData> towers;

    private List<List<EnemyData>> enemies;

    private List<ProjectileData> projectiles;

    public List<IntersectionData> getIntersections() {
        return intersections;
    }

    public MapData setIntersections(List<IntersectionData> intersections) {
        this.intersections = intersections;
        return this;
    }

    public List<TowerData> getTowers() {
        return towers;
    }

    public MapData setTowers(List<TowerData> towers) {
        this.towers = towers;
        return this;
    }

    public List<List<EnemyData>> getEnemies() {
        return enemies;
    }

    public MapData setEnemies(List<List<EnemyData>> enemies) {
        this.enemies = enemies;
        return this;
    }

    public List<ProjectileData> getProjectiles() {
        return projectiles;
    }

    public MapData setProjectiles(List<ProjectileData> projectiles) {
        this.projectiles = projectiles;
        return this;
    }
}
