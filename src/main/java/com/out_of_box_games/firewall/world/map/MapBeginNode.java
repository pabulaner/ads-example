package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.gengine.util.math.RandomUtil;

public class MapBeginNode extends MapPathNode {

    public MapBeginNode(int index) {
        super("IN", index);
    }

    @Override
    public void handle(MapPathFollowComponent follow) {
        follow.attachTo(RandomUtil.getRandom(getPaths()).getPathComponent());
    }
}
