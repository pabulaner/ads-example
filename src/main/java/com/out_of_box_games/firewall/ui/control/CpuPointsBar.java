package com.out_of_box_games.firewall.ui.control;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.world.Layers;
import com.out_of_box_games.firewall.world.cpu.Cpu;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.component.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CpuPointsBar extends Actor {

    private static final Vector2 SIZE = new Vector2(10.0f, 30.0f);

    private static final float OFFSET = 20.0f;

    private final TowerType type;

    private int index;

    private final List<Button> buttons;

    private final Consumer<Void> listener;

    private final TextComponent textComponent;

    public CpuPointsBar(TowerType type) {
        this.type = type;
        this.index = -1;
        this.buttons = new ArrayList<>();
        this.listener = ignore -> select(getCpu().getPoints(type) - 1);
        this.textComponent = addComponent(new TextComponent());

        textComponent.setLayer(Layers.UI);
        textComponent.setFont(GameConfig.FONT);
        textComponent.setText(type.toString());
        textComponent.setFill(GameConfig.PRIMARY_COLOR);

        setRoot(textComponent);
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        for (int i = 0; i < Cpu.USAGE_STEPS; i++) {
            addButton(i);
        }

        getCpu().onPointsChange().addListener(listener);
        select(getCpu().getPoints(type) - 1);
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();

        getCpu().onPointsChange().removeListener(listener);
    }

    private void addButton(int index) {
        Button button = getWorld().addActor(new Button());
        button.setSize(SIZE);
        button.setFill(false);
        button.setStroke(true);
        button.setOnClick(() -> select(index));
        button.getRoot().translate(Vector2.right().mul(OFFSET * index + 50.0f));
        button.getRoot().attachTo(getRoot());
        button.getShapeComponent().setLineWidth(Map.LINE_WIDTH * 0.5f);
        button.getAreaComponent().setScale(new Vector2(1.8f, 1.0f));

        buttons.add(button);
    }

    public void select(int index) {
        if (this.index == index) {
            return;
        }

        this.index = index;
        getCpu().setPoints(type, index + 1);

        int free = getCpu().getFreePoints();

        if (free < 0) {
            index += free;
        }

        this.index = index;

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setFill(i < index + 1);
        }

        getCpu().setPoints(type, index + 1);
    }

    public int getIndex() {
        return index;
    }

    private Cpu getCpu() {
        GameModeBase gameMode = getWorld().getGameMode();
        return gameMode.getCpu();
    }
}
