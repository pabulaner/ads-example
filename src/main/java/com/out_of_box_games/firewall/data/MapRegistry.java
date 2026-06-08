package com.out_of_box_games.firewall.data;

import com.out_of_box_games.firewall.data.map.MapStaticData;
import com.out_of_box_games.gengine.data.Registry;
import com.out_of_box_games.gengine.util.math.Vector2Int;

import java.util.List;

public class MapRegistry extends Registry<Integer, MapStaticData> {

    private static MapRegistry instance;

    private MapRegistry() {
        super(
                new MapStaticData()
                        .setType(1)
                        .setSize(new Vector2Int(8))
                        .setBegins(List.of(new Vector2Int(5, 0)))
                        .setIntersections(List.of())
                        .setEnds(List.of(new Vector2Int(0, 4)))
                        .setPaths(List.of(List.of(
                                new Vector2Int(5, 0),
                                new Vector2Int(5, 1),
                                new Vector2Int(7, 1),
                                new Vector2Int(7, 3),
                                new Vector2Int(5, 3),
                                new Vector2Int(5),
                                new Vector2Int(7, 5),
                                new Vector2Int(7),
                                new Vector2Int(0, 7),
                                new Vector2Int(0, 6),
                                new Vector2Int(3, 6),
                                new Vector2Int(3, 2),
                                new Vector2Int(1, 2),
                                new Vector2Int(1, 4),
                                new Vector2Int(0, 4))))
                        .setPlatforms(List.of(
                                new Vector2Int(1),
                                new Vector2Int(2, 1),
                                new Vector2Int(2, 3),
                                new Vector2Int(2, 4),
                                new Vector2Int(2, 5),
                                new Vector2Int(3, 1),
                                new Vector2Int(4),
                                new Vector2Int(4, 1),
                                new Vector2Int(4, 2),
                                new Vector2Int(4, 3),
                                new Vector2Int(4, 5),
                                new Vector2Int(4, 6),
                                new Vector2Int(5, 2),
                                new Vector2Int(5, 6),
                                new Vector2Int(6, 2),
                                new Vector2Int(6, 4),
                                new Vector2Int(6),
                                new Vector2Int(7, 4))),
                new MapStaticData()
                        .setType(2)
                        .setSize(new Vector2Int(9, 5))
                        .setBegins(List.of(
                                new Vector2Int(0, 1),
                                new Vector2Int(0, 3)))
                        .setIntersections(List.of(
                                new Vector2Int(4, 0),
                                new Vector2Int(4)))
                        .setEnds(List.of(
                                new Vector2Int(8, 1),
                                new Vector2Int(8, 3)))
                        .setPaths(List.of(
                                List.of(
                                        new Vector2Int(0, 1),
                                        new Vector2Int(0),
                                        new Vector2Int(4, 0)),
                                List.of(
                                        new Vector2Int(0, 3),
                                        new Vector2Int(0, 4),
                                        new Vector2Int(4)),
                                List.of(
                                        new Vector2Int(4, 0),
                                        new Vector2Int(8, 0),
                                        new Vector2Int(8, 1)),
                                List.of(
                                        new Vector2Int(4),
                                        new Vector2Int(8, 4),
                                        new Vector2Int(8, 3)),
                                List.of(
                                        new Vector2Int(4, 0),
                                        new Vector2Int(4))))
                        .setPlatforms(List.of(
                                new Vector2Int(1),
                                new Vector2Int(1, 2),
                                new Vector2Int(1, 3),
                                new Vector2Int(2, 1),
                                new Vector2Int(2, 3),
                                new Vector2Int(3, 1),
                                new Vector2Int(3, 2),
                                new Vector2Int(3),
                                new Vector2Int(5, 1),
                                new Vector2Int(5, 2),
                                new Vector2Int(5, 3),
                                new Vector2Int(6, 1),
                                new Vector2Int(6, 3),
                                new Vector2Int(7, 1),
                                new Vector2Int(7, 2),
                                new Vector2Int(7, 3))),
                new MapStaticData()
                        .setType(3)
                        .setSize(new Vector2Int(12, 7))
                        .setBegins(List.of(new Vector2Int(0, 3)))
                        .setIntersections(List.of(
                                new Vector2Int(9, 3),
                                new Vector2Int(11, 3)))
                        .setEnds(List.of(
                                new Vector2Int(0, 2),
                                new Vector2Int(0, 4),
                                new Vector2Int(4, 0),
                                new Vector2Int(4, 6)))
                        .setPaths(List.of(
                                List.of(
                                        new Vector2Int(0, 3),
                                        new Vector2Int(9, 3)),
                                List.of(
                                        new Vector2Int(9, 3),
                                        new Vector2Int(11, 3)),
                                List.of(
                                        new Vector2Int(9, 3),
                                        new Vector2Int(9, 1),
                                        new Vector2Int(0, 1),
                                        new Vector2Int(0, 2)),
                                List.of(
                                        new Vector2Int(9, 3),
                                        new Vector2Int(9, 5),
                                        new Vector2Int(0, 5),
                                        new Vector2Int(0, 4)),
                                List.of(
                                        new Vector2Int(11, 3),
                                        new Vector2Int(11, 0),
                                        new Vector2Int(4, 0)),
                                List.of(
                                        new Vector2Int(11, 3),
                                        new Vector2Int(11, 6),
                                        new Vector2Int(4, 6))))
                        .setPlatforms(List.of(
                                new Vector2Int(1, 2),
                                new Vector2Int(2),
                                new Vector2Int(3, 2),
                                new Vector2Int(4, 2),
                                new Vector2Int(5, 2),
                                new Vector2Int(6, 2),
                                new Vector2Int(7, 2),
                                new Vector2Int(8, 2),
                                new Vector2Int(1, 4),
                                new Vector2Int(2, 4),
                                new Vector2Int(3, 4),
                                new Vector2Int(4),
                                new Vector2Int(5, 4),
                                new Vector2Int(6, 4),
                                new Vector2Int(7, 4),
                                new Vector2Int(8, 4),
                                new Vector2Int(10, 1),
                                new Vector2Int(10, 2),
                                new Vector2Int(10, 4),
                                new Vector2Int(10, 5))),
                new MapStaticData()
                        .setType(4)
                        .setSize(new Vector2Int(13, 7))
                        .setBegins(List.of(
                                new Vector2Int(0, 0),
                                new Vector2Int(0, 2),
                                new Vector2Int(0, 4),
                                new Vector2Int(0, 6)))
                        .setIntersections(List.of(
                                new Vector2Int(3, 0),
                                new Vector2Int(3, 2),
                                new Vector2Int(6, 4),
                                new Vector2Int(6),
                                new Vector2Int(9, 2),
                                new Vector2Int(9, 4)))
                        .setEnds(List.of(
                                new Vector2Int(12, 0),
                                new Vector2Int(12, 2),
                                new Vector2Int(12, 4),
                                new Vector2Int(12, 6)))
                        .setPaths(List.of(
                                List.of(
                                        new Vector2Int(0),
                                        new Vector2Int(3, 0)),
                                List.of(
                                        new Vector2Int(3, 0),
                                        new Vector2Int(12, 0)),
                                List.of(
                                        new Vector2Int(0, 2),
                                        new Vector2Int(3, 2)),
                                List.of(
                                        new Vector2Int(3, 2),
                                        new Vector2Int(9, 2)),
                                List.of(
                                        new Vector2Int(9, 2),
                                        new Vector2Int(12, 2)),
                                List.of(
                                        new Vector2Int(0, 4),
                                        new Vector2Int(6, 4)),
                                List.of(
                                        new Vector2Int(6, 4),
                                        new Vector2Int(9, 4)),
                                List.of(
                                        new Vector2Int(9, 4),
                                        new Vector2Int(12, 4)),
                                List.of(
                                        new Vector2Int(0, 6),
                                        new Vector2Int(6)),
                                List.of(
                                        new Vector2Int(6),
                                        new Vector2Int(12, 6)),
                                List.of(
                                        new Vector2Int(3, 0),
                                        new Vector2Int(3, 2)),
                                List.of(
                                        new Vector2Int(6, 4),
                                        new Vector2Int(6)),
                                List.of(
                                        new Vector2Int(9, 2),
                                        new Vector2Int(9, 4))
                        ))
                        .setPlatforms(List.of(
                                new Vector2Int(1),
                                new Vector2Int(2, 1),
                                new Vector2Int(4, 1),
                                new Vector2Int(5, 1),
                                new Vector2Int(7, 1),
                                new Vector2Int(8, 1),
                                new Vector2Int(10, 1),
                                new Vector2Int(11, 1),
                                new Vector2Int(1, 3),
                                new Vector2Int(2, 3),
                                new Vector2Int(4, 3),
                                new Vector2Int(5, 3),
                                new Vector2Int(7, 3),
                                new Vector2Int(8, 3),
                                new Vector2Int(10, 3),
                                new Vector2Int(11, 3),
                                new Vector2Int(1, 5),
                                new Vector2Int(2, 5),
                                new Vector2Int(4, 5),
                                new Vector2Int(5),
                                new Vector2Int(7, 5),
                                new Vector2Int(8, 5),
                                new Vector2Int(10, 5),
                                new Vector2Int(11, 5))));
    }

    public static MapRegistry getInstance() {
        if (instance == null) {
            instance = new MapRegistry();
        }

        return instance;
    }
}
