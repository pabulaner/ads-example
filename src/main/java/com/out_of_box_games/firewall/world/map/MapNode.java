package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.world.Layers;
import com.out_of_box_games.gengine.util.math.RectangleShape;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.component.AreaComponent;
import com.out_of_box_games.gengine.world.component.ShapeComponent;
import com.out_of_box_games.gengine.world.component.TextComponent;

public abstract class MapNode extends Actor {

    private final AreaComponent areaComponent;

    private final ShapeComponent shapeComponent;

    private final TextComponent nameComponent;

    private final TextComponent binaryComponent;

    public MapNode() {
        areaComponent = addComponent(new AreaComponent());
        shapeComponent = addComponent(new ShapeComponent());
        nameComponent = addComponent(new TextComponent());
        binaryComponent = addComponent(new TextComponent());

        areaComponent.setShape(new RectangleShape(Map.TILE_SIZE));
        areaComponent.setClickable(true);
        areaComponent.onClick().addListener(ignored -> onClick());
        shapeComponent.setLayer(Layers.NODE);
        shapeComponent.setFill(GameConfig.SECONDARY_COLOR);
        shapeComponent.setStroke(GameConfig.PRIMARY_COLOR);
        shapeComponent.setLineWidth(Map.LINE_WIDTH);
        shapeComponent.setShape(new RectangleShape(Map.NODE_SIZE));
        nameComponent.translate(Vector2.up().mul(15.0f));
        nameComponent.setLayer(Layers.NODE_TEXT);
        nameComponent.setFill(GameConfig.PRIMARY_COLOR);
        nameComponent.setFont(GameConfig.FONT);
        binaryComponent.translate(Vector2.down().mul(15.0f));
        binaryComponent.setLayer(Layers.NODE_TEXT);
        binaryComponent.setFill(GameConfig.PRIMARY_COLOR);
        binaryComponent.setFont(GameConfig.FONT);

        shapeComponent.attachTo(areaComponent);
        nameComponent.attachTo(areaComponent);
        binaryComponent.attachTo(areaComponent);
        setRoot(areaComponent);
    }

    protected void onClick() {
        // empty
    }

    public AreaComponent getAreaComponent() {
        return areaComponent;
    }

    public ShapeComponent getShapeComponent() {
        return shapeComponent;
    }

    public TextComponent getNameComponent() {
        return nameComponent;
    }

    public TextComponent getBinaryComponent() {
        return binaryComponent;
    }
}
