package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.gengine.util.math.Vector2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MapConnector {

    public static void createConnections(List<MapPathNode> nodes, List<MapBeginNode> begins, List<MapEndNode> ends, List<MapPath> paths) {
        paths.forEach(path -> {
            List<Vector2> points = path.getPathComponent().getPoints();
            Vector2 begin = points.get(0);
            Vector2 end = points.get(points.size() - 1);

            nodes.forEach(node -> {
                Vector2 point = node.getRoot().getTranslation();

                if (begin.equals(point)) {
                    node.addPath(path);
                }

                if (end.equals(point)) {
                    path.setEnd(node);
                }
            });
        });

        begins.forEach(begin -> ends.forEach(end -> createPathFinding(begin, end)));
    }

    private static void createPathFinding(MapBeginNode begin, MapEndNode end) {
        Queue<MapPathNode> open = new LinkedList<>();
        Map<MapPathNode, Float> lengths = new HashMap<>();

        open.add(begin);
        lengths.put(begin, 0.0f);

        while (!open.isEmpty()) {
            MapPathNode node = open.poll();
            float length = lengths.get(node);

            node.getPaths().forEach(path -> {
                MapPathNode other = path.getEnd();
                float otherLength = length + path.getPathComponent().length();

                if (!lengths.containsKey(other) || lengths.get(other) > otherLength) {
                    if (!open.contains(other)) {
                        open.add(other);
                    }

                    lengths.put(other, otherLength);
                }
            });
        }

        MapPathNode node = end;

        while (node != begin) {
            MapPath shortest = node.getPaths()
                    .stream()
                    .min(Comparator.comparingDouble(path -> lengths.get(path.getEnd())))
                    .orElseThrow();

            node = shortest.getEnd();
            node.setRoute(end.getIndex(), shortest.getOther());
        }
    }
}
