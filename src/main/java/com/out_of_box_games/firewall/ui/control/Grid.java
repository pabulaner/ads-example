package com.out_of_box_games.firewall.ui.control;

import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.component.TransformComponent;

import java.util.ArrayList;
import java.util.List;

public class Grid extends Actor {

    private Vector2 spacing;

    private int columns;

    private final List<Actor> children;

    public Grid() {
        spacing = Vector2.zero();
        columns = 1;
        children = new ArrayList<>();

        setRoot(addComponent(new TransformComponent()));
    }

    public void add(Actor actor) {
        Vector2Int cell = new Vector2Int(children.size() % columns, children.size() / columns);
        actor.getRoot().setTranslation(cell.toVector2().mul(spacing));
        getWorld().addActor(actor);
        actor.getRoot().attachTo(getRoot());

        children.add(actor);
    }

    public Vector2 getSpacing() {
        return spacing;
    }

    public void setSpacing(Vector2 spacing) {
        this.spacing = spacing;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Actor> getChildren() {
        return children;
    }
}
