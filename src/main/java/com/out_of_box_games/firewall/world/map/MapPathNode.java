package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.firewall.util.BinaryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MapPathNode extends MapNode {

    private final int index;

    private final List<MapPath> paths;

    private final Map<Integer, MapPath> routes;

    protected MapPathNode(String name, int index) {
        this.index = index;
        this.paths = new ArrayList<>();
        this.routes = new HashMap<>();

        getNameComponent().setText(name);
        getBinaryComponent().setText(BinaryUtil.toString(index));
    }

    public abstract void handle(MapPathFollowComponent follow);

    public int getIndex() {
        return index;
    }

    public void addPath(MapPath path) {
        paths.add(path);
    }

    public List<MapPath> getPaths() {
        return paths;
    }

    public MapPath getRoute(int id) {
        return routes.get(id);
    }

    public void setRoute(int id, MapPath path) {
        routes.put(id, path);
    }
}
