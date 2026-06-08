package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.world.Layers;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.collection.Pair;
import com.out_of_box_games.gengine.util.math.PolygonShape;
import com.out_of_box_games.gengine.util.math.Shape;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.Component;
import com.out_of_box_games.gengine.world.component.PathComponent;
import com.out_of_box_games.gengine.world.component.ShapeComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapPath extends Actor {

    private MapPath other;

    private MapPathNode end;

    private final PathComponent pathComponent;

    private final ShapeComponent lineComponent;

    public MapPath() {
        other = null;
        end = null;
        pathComponent = addComponent(new PathComponent());
        lineComponent = addComponent(new ShapeComponent());

        lineComponent.setLayer(Layers.PATH);
        lineComponent.setFill(Color.TRANSPARENT);
        lineComponent.setStroke(GameConfig.PRIMARY_COLOR);
        lineComponent.setLineWidth(Map.LINE_WIDTH);
        lineComponent.setCyclic(false);

        pathComponent.attachTo(lineComponent);
        setRoot(lineComponent);
    }

    public static Pair<MapPath, MapPath> createBidirectional(List<Vector2Int> points) {
        List<Vector2> first = points.stream()
                .map(Map::toTranslation)
                .toList();

        List<Vector2> second = new ArrayList<>(first);
        Collections.reverse(second);

        Pair<MapPath, MapPath> result = new Pair<>(create(first), create(second));
        result.getFirst().setOther(result.getSecond());
        result.getSecond().setOther(result.getFirst());

        return result;
    }

    private static MapPath create(List<Vector2> points) {
        MapPath path = new MapPath();
        Shape shape = new PolygonShape(points.toArray(new Vector2[0]));

        path.pathComponent.setPoints(points);
        path.lineComponent.setShape(shape);

        return path;
    }

    public List<Actor> getActors() {
        return pathComponent.getChildren()
                .stream()
                .map(Component::<Actor>getActor)
                .toList();
    }

    public MapPath getOther() {
        return other;
    }

    public MapPath setOther(MapPath other) {
        this.other = other;
        return this;
    }

    public MapPathNode getEnd() {
        return end;
    }

    public void setEnd(MapPathNode end) {
        this.end = end;
    }

    public PathComponent getPathComponent() {
        return pathComponent;
    }

    public ShapeComponent getLineComponent() {
        return lineComponent;
    }
}
