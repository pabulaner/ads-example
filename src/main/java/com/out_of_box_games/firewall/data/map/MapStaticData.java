package com.out_of_box_games.firewall.data.map;

import com.out_of_box_games.gengine.data.TypeData;
import com.out_of_box_games.gengine.util.math.Vector2Int;

import java.util.List;

public class MapStaticData extends TypeData<MapStaticData, Integer> {

    private Vector2Int size;

    private List<Vector2Int> begins;

    private List<Vector2Int> intersections;

    private List<Vector2Int> ends;

    private List<List<Vector2Int>> paths;

    private List<Vector2Int> platforms;

    public Vector2Int getSize() {
        return size;
    }

    public MapStaticData setSize(Vector2Int size) {
        this.size = size;
        return this;
    }

    public List<Vector2Int> getBegins() {
        return begins;
    }

    public MapStaticData setBegins(List<Vector2Int> begins) {
        this.begins = begins;
        return this;
    }

    public List<Vector2Int> getIntersections() {
        return intersections;
    }

    public MapStaticData setIntersections(List<Vector2Int> intersections) {
        this.intersections = intersections;
        return this;
    }

    public List<Vector2Int> getEnds() {
        return ends;
    }

    public MapStaticData setEnds(List<Vector2Int> ends) {
        this.ends = ends;
        return this;
    }

    public List<List<Vector2Int>> getPaths() {
        return paths;
    }

    public MapStaticData setPaths(List<List<Vector2Int>> paths) {
        this.paths = paths;
        return this;
    }

    public List<Vector2Int> getPlatforms() {
        return platforms;
    }

    public MapStaticData setPlatforms(List<Vector2Int> platforms) {
        this.platforms = platforms;
        return this;
    }
}
