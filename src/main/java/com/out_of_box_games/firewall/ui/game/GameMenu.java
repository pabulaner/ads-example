package com.out_of_box_games.firewall.ui.game;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.ui.control.Button;
import com.out_of_box_games.firewall.world.Layers;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.gengine.util.math.RectangleShape;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.component.ShapeComponent;

public class GameMenu extends Actor {

    private static final Vector2 BOTTOM_CENTER = new Vector2(0.0f, 260.0f);

    private static final float BOTTOM_SPACING = 70.0f;

    private int bottomButtonCount;

    private final ShapeComponent shapeComponent;

    public GameMenu() {
        bottomButtonCount = 0;
        shapeComponent = addComponent(new ShapeComponent());

        shapeComponent.setLayer(Layers.UI);
        shapeComponent.setShape(new RectangleShape(new Vector2(1000.0f, 600.0f)));
        shapeComponent.setFill(GameConfig.SECONDARY_COLOR);
        shapeComponent.setStroke(GameConfig.PRIMARY_COLOR);
        shapeComponent.setLineWidth(Map.LINE_WIDTH);

        setRoot(shapeComponent);
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();
        createCloseButton();
    }

    protected Button addBottomButton(String text, Runnable onClick) {
        Button button = new Button();
        button.getRoot().translate(BOTTOM_CENTER.copy().add(
                Vector2.up().mul(BOTTOM_SPACING * bottomButtonCount++)));
        button.setText(text);
        button.setStroke(true);
        button.setSize(new Vector2(280.0f, 50.0f));
        button.setOnClick(onClick);

        getWorld().addActor(button);
        button.getRoot().attachTo(getRoot());

        return button;
    }

    protected void createCloseButton() {
        addBottomButton("Close", () -> {
            GameUI ui = getWorld().getUI();
            ui.hideMenu();
        });
    }
}
