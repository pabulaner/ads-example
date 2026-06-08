package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.firewall.ui.game.GameUI;
import com.out_of_box_games.firewall.ui.game.TowerBuildMenu;
import com.out_of_box_games.firewall.ui.game.TowerUpgradeMenu;
import com.out_of_box_games.firewall.util.BinaryUtil;
import com.out_of_box_games.firewall.world.cpu.Cpu;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.PlayerStateBase;
import com.out_of_box_games.firewall.world.tower.Tower;

import java.util.function.Consumer;

public class MapPlatform extends MapNode {

    private PlayerStateBase playerState;

    private Tower tower;

    private int usage;

    private final Consumer<Void> listener;

    public MapPlatform() {
        usage = 0;
        listener = ignore -> {
            GameModeBase gameMode = getWorld().getGameMode();
            Cpu cpu = gameMode.getCpu();
            usage = 0;

            if (tower != null) {
                usage = tower.getData().getType() == TowerType.CPU
                        ? (int) cpu.getTotalUsagePercentRandomized()
                        : (int) gameMode.getCpu().getUsagePercentRandomized(tower.getId());
            }
        };

        getNameComponent().setFill(GameConfig.SECONDARY_COLOR);
        getBinaryComponent().setFill(GameConfig.SECONDARY_COLOR);
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        playerState = getWorld().getPlayerState();
        playerState.onUpdateUsage().addListener(listener);
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();
        playerState.onUpdateUsage().removeListener(listener);
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        updateText();
    }

    @Override
    protected void onClick() {
        super.onClick();

        GameUI ui = getWorld().getUI();
        ui.showMenu(tower == null
                ? new TowerBuildMenu(this)
                : new TowerUpgradeMenu(this));
    }

    private void updateText() {
        String text = switch (playerState.getTowerDisplayMode()) {
            case LEVEL -> BinaryUtil.toString(tower != null
                    ? tower.getData().getLevel()
                    : 0);
            case USAGE -> usage + "%";
        };

        getBinaryComponent().setText(text);
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        if (this.tower != null) {
            this.tower.destroy();

            getShapeComponent().setFill(GameConfig.SECONDARY_COLOR);
            getNameComponent().setText("");
            getBinaryComponent().setText("");
        }

        if (tower != null) {
            TowerData data = tower.getData();
            Consumer<Void> listener = ignore -> getBinaryComponent().setText(BinaryUtil.toString(data.getLevel()));

            tower.getRoot().attachTo(getRoot());
            tower.onUpgrade().addListener(listener);

            getShapeComponent().setFill(GameConfig.PRIMARY_COLOR);
            getNameComponent().setText(data.getType().toString());

            listener.accept(null);
        }

        this.tower = tower;
    }
}
