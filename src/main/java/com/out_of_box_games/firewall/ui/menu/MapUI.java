package com.out_of_box_games.firewall.ui.menu;

import com.out_of_box_games.firewall.data.game.GameData;
import com.out_of_box_games.firewall.level.GameLevel;
import com.out_of_box_games.firewall.level.TerminalLevel;
import com.out_of_box_games.firewall.ui.control.Button;
import com.out_of_box_games.firewall.util.SaveGame;
import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import com.out_of_box_games.gengine.world.actor.UI;
import com.out_of_box_games.gengine.world.component.CameraComponent;

import java.util.function.Consumer;

public class MapUI extends UI {

    private final Consumer<Vector2Int> onResizeListener;

    private int index;

    public MapUI() {
        onResizeListener = this::onResize;
        index = 0;
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();
        Engine.get()
                .getRenderSystem()
                .onResize()
                .addListener(onResizeListener);

        addLevelButton("The Beginning");
        addLevelButton("The Decision");
        addLevelButton("The Work");
        addLevelButton("The Connection");

        index += 1;

        addButton("Back", () -> getWorld().loadLevel(new TerminalLevel()));
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();
        Engine.get()
                .getRenderSystem()
                .onResize()
                .removeListener(onResizeListener);
    }

    private void addLevelButton(String name) {
        int level = index + 1;
        GameData data = SaveGame.load(level);

        if (data != null) {
            name += " (" + data.getWave().getIndex() + ")";
        }

        addButton(name, () -> getWorld().loadLevel(new GameLevel(level)));
    }

    private void addButton(String text, Runnable onClick) {
        Button button = getWorld().addActor(new Button());
        button.setText(text);
        button.setSize(new Vector2(400.0f, 70.0f));
        button.setStroke(true);
        button.setOnClick(onClick);
        button.getRoot().setTranslation(new Vector2(0, -250.0f)
                .add(Vector2.down().mul(index++ * 100.0f)));

        button.getRoot().attachTo(getRoot());
    }

    private void onResize(Vector2Int size) {
        getRoot().setTranslation(getWorld().getComponents(CameraComponent.class)
                .get(0)
                .getSize()
                .mul(0.5f));
    }
}
