package com.out_of_box_games.firewall.ui.game;

import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.firewall.ui.control.Button;
import com.out_of_box_games.firewall.ui.control.CpuPointsBarGroup;
import com.out_of_box_games.firewall.util.MemoryUtil;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.firewall.world.map.MapPlatform;
import com.out_of_box_games.firewall.world.tower.Tower;
import com.out_of_box_games.gengine.util.math.Vector2;

public class TowerUpgradeMenu extends TowerMenu {

    private static final float USAGE_STEP = 4.0f;

    private TowerData tower;

    private Button upgrade;

    private Button sell;

    public TowerUpgradeMenu(MapPlatform platform) {
        super(platform, platform.getTower().getData().getType() != TowerType.CPU);
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        GameModeBase gameMode = getWorld().getGameMode();
        GameStateBase gameState = getWorld().getGameState();
        tower = getPlatform().getTower().getData();
        updateStats(tower);

        Button type = new Button();
        type.getRoot().setTranslation(new Vector2(-250.0f, -125.0f));
        type.setText(tower.getType().toString());

        if (tower.getType() == TowerType.CPU) {
            CpuPointsBarGroup group = getWorld().addActor(new CpuPointsBarGroup(1));
            group.getRoot().setTranslation(new Vector2(220.0f, -230.0f));
            group.getTextComponent().setTranslation(new Vector2(-470.0f, 150.0f));
            group.getRoot().attachTo(getRoot());
        }

        getWorld().addActor(type);
        type.getRoot().attachTo(getRoot());

        sell = addBottomButton("", () -> {
            if (tower.getType() == TowerType.CPU) {
                return;
            }

            GameUI ui = getWorld().getUI();
            gameState.setCash(gameState.getCash() + gameMode.getTowerSell(tower.getType(), tower.getLevel()));

            getPlatform().setTower(null);
            ui.hideMenu();
        });

        upgrade = addBottomButton("", () -> {
            float cost = gameMode.getTowerCost(tower.getType(), tower.getLevel() + 1);
            float cash = gameState.getCash();

            if (cost > cash || tower.getLevel() >= Tower.MAX_LEVEL) {
                return;
            }

            gameState.setCash(cash - cost);
            getPlatform().getTower().upgrade();

            updateStats(tower);
            updateButtons();

            gameMode.getCpu().onPointsChange().invoke();
        });

        updateButtons();
    }

    private void updateButtons() {
        GameModeBase gameMode = getWorld().getGameMode();

        float upgradeCost = gameMode.getTowerCost(tower.getType(), tower.getLevel() + 1);
        float sellCost = gameMode.getTowerSell(tower.getType(), tower.getLevel());
        String sellStr = tower.getType() != TowerType.CPU
                ? MemoryUtil.toString(sellCost)
                : "-";

        upgrade.setText("Upgrade (" + MemoryUtil.toString(upgradeCost) + ")");
        sell.setText("Sell (" + sellStr + ")");

        if (tower.getLevel() >= Tower.MAX_LEVEL) {
            upgrade.setText("");
            upgrade.setStroke(false);
        }
    }
}
