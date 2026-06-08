package com.out_of_box_games.firewall.ui.menu;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.level.TerminalLevel;
import com.out_of_box_games.firewall.ui.control.Button;
import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import com.out_of_box_games.gengine.world.actor.UI;
import com.out_of_box_games.gengine.world.component.CameraComponent;
import com.out_of_box_games.gengine.world.component.SpriteComponent;
import com.out_of_box_games.gengine.world.component.TextComponent;

import java.util.function.Consumer;

public class MenuUI extends UI {

    private final Consumer<Vector2Int> onResizeListener;

    private final SpriteComponent spriteComponent;

    private final TextComponent textComponent;

    public MenuUI() {
        onResizeListener = this::onResize;
        spriteComponent = addComponent(new SpriteComponent());
        textComponent = addComponent(new TextComponent());

        spriteComponent.setTexture(Engine.get().getAssetLoader().load(Texture.class, "/title.png"));
        spriteComponent.setTranslation(Vector2.up().mul(270.0f));
        spriteComponent.setScale(new Vector2(0.3f));
        textComponent.setFont(GameConfig.FONT);
        textComponent.setText("Version: 1.0");
        textComponent.setFill(GameConfig.PRIMARY_COLOR);
        textComponent.setTranslation(Vector2.down().mul(270.0f));

        textComponent.attachTo(getRoot());
        spriteComponent.attachTo(getRoot());
    }


    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();
        Engine.get()
                .getRenderSystem()
                .onResize()
                .addListener(onResizeListener);

        addButton("Play", 0, () -> getWorld().loadLevel(new TerminalLevel()));
        addButton("Exit", 1, () -> Engine.get()
                .getPlatformSystem()
                .exit());
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();
        Engine.get()
                .getRenderSystem()
                .onResize()
                .removeListener(onResizeListener);
    }

    private void addButton(String text, int index, Runnable onClick) {
        Button button = getWorld().addActor(new Button());
        button.setText(text);
        button.setSize(new Vector2(400.0f, 70.0f));
        button.setStroke(true);
        button.setOnClick(onClick);
        button.getRoot().setTranslation(new Vector2(0, -50.0f)
                .add(Vector2.down().mul(index * 100.0f)));

        button.getRoot().attachTo(getRoot());
    }

    private void onResize(Vector2Int size) {
        getRoot().setTranslation(getWorld().getComponents(CameraComponent.class)
                .get(0)
                .getSize()
                .mul(0.5f));
    }
}
