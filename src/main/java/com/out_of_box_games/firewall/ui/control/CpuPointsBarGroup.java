package com.out_of_box_games.firewall.ui.control;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.world.Layers;
import com.out_of_box_games.firewall.world.cpu.Cpu;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.component.TextComponent;

import java.util.List;
import java.util.function.Consumer;

public class CpuPointsBarGroup extends Grid {

    private final Consumer<Void> listener;

    private final TextComponent textComponent;

    public CpuPointsBarGroup(int columns) {
        textComponent = addComponent(new TextComponent());
        listener = ignore -> textComponent.setText("Free: " + getCpu().getFreePoints());

        setColumns(columns);
        setSpacing(new Vector2(250.0f, 40.0f));

        textComponent.setLayer(Layers.UI);
        textComponent.setFont(GameConfig.FONT);
        textComponent.setFill(GameConfig.PRIMARY_COLOR);
        textComponent.attachTo(getRoot());
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        List<TowerType> types = List.of(
                TowerType.TXT,
                TowerType.SRC,
                TowerType.BIN,
                TowerType.IMG,
                TowerType.AUD,
                TowerType.VID,
                TowerType.ENC,
                TowerType.ZIP);

        getCpu().onPointsChange().addListener(listener);
        types.forEach(type -> add(new CpuPointsBar(type)));
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();

        getCpu().onPointsChange().removeListener(listener);
    }

    private Cpu getCpu() {
        GameModeBase gameMode = getWorld().getGameMode();
        return gameMode.getCpu();
    }

    public TextComponent getTextComponent() {
        return textComponent;
    }
}
