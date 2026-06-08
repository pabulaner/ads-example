package com.out_of_box_games.firewall.ui.control;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.world.Layers;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.math.RectangleShape;
import com.out_of_box_games.gengine.util.math.Shape;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.component.AreaComponent;
import com.out_of_box_games.gengine.world.component.ShapeComponent;
import com.out_of_box_games.gengine.world.component.TextComponent;

public class Button extends Actor {

    private Runnable onClick;

    private final TextComponent textComponent;

    private final ShapeComponent shapeComponent;

    private final AreaComponent areaComponent;

    public Button() {
        onClick = null;
        textComponent = addComponent(new TextComponent());
        shapeComponent = addComponent(new ShapeComponent());
        areaComponent = addComponent(new AreaComponent());

        textComponent.setLayer(Layers.UI);
        textComponent.setFill(GameConfig.PRIMARY_COLOR);
        textComponent.setFont(GameConfig.FONT);
        shapeComponent.setLayer(Layers.UI);
        shapeComponent.setFill(Color.TRANSPARENT);
        shapeComponent.setStroke(Color.TRANSPARENT);
        shapeComponent.setLineWidth(Map.LINE_WIDTH);
        areaComponent.setClickable(true);
        areaComponent.onClick().addListener(ignore -> {
            if (onClick != null) {
                onClick.run();
            }
        });

        shapeComponent.attachTo(textComponent);
        areaComponent.attachTo(textComponent);
        setRoot(textComponent);
    }

    public static Button createDefault(String text, Vector2 size, Runnable onClick) {
        Button button = new Button();

        button.onClick = onClick;
        button.textComponent.setText(text);

        return button;
    }

    public void setText(String text) {
        textComponent.setText(text);
    }

    public void setSize(Vector2 size) {
        Shape shape = new RectangleShape(size);

        shapeComponent.setShape(shape);
        areaComponent.setShape(shape);
    }

    public void setFill(boolean enabled) {
        shapeComponent.setFill(enabled
                ? GameConfig.PRIMARY_COLOR
                : Color.TRANSPARENT);
    }

    public void setStroke(boolean enabled) {
        shapeComponent.setStroke(enabled
                ? GameConfig.PRIMARY_COLOR
                : Color.TRANSPARENT);
    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public TextComponent getTextComponent() {
        return textComponent;
    }

    public ShapeComponent getShapeComponent() {
        return shapeComponent;
    }

    public AreaComponent getAreaComponent() {
        return areaComponent;
    }
}
