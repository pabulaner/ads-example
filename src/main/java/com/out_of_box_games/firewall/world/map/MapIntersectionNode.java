package com.out_of_box_games.firewall.world.map;

public class MapIntersectionNode extends MapPathNode {

    public MapIntersectionNode(int index) {
        super("RTE", index);
    }

    @Override
    public void handle(MapPathFollowComponent follow) {
        follow.attachTo(getRoute(follow.getTarget()).getPathComponent());
    }
}
