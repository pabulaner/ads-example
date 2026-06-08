package com.out_of_box_games.firewall.ui.game;

import com.gluonhq.attach.util.Platform;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.firewall.ui.control.Button;
import com.out_of_box_games.firewall.ui.control.Grid;
import com.out_of_box_games.firewall.util.MemoryUtil;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.firewall.world.map.MapPlatform;
import com.out_of_box_games.firewall.world.tower.Tower;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.World;

import java.util.ArrayList;
import java.util.List;

public class TowerBuildMenu extends TowerMenu {

    private Grid grid;

    public TowerBuildMenu(MapPlatform platform) {
        super(platform, true);
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        grid = new Grid();
        grid.setColumns(3);
        grid.setSpacing(new Vector2(120.0f, 60.0f));
        getWorld().addActor(grid);
        grid.getRoot().setTranslation(new Vector2(-340.0f, -200.0f));
        grid.getRoot().attachTo(getRoot());

        World world = getWorld();
        GameModeBase gameMode = world.getGameMode();
        GameStateBase gameState = world.getGameState();
        TowerType[] selected = { null };

        Button build = addBottomButton("", () -> {
            float cost = gameMode.getTowerCost(selected[0], 0);
            float cash = gameState.getCash();

            if (cost > cash) {
                return;
            }

            gameState.setCash(cash - cost);

            GameUI ui = world.getUI();
            Tower tower = Persist.create(new Tower(), new TowerData()
                    .setType(selected[0])
                    .setLevel(0), world);

            world.addActor(tower);
            getPlatform().setTower(tower);

            ui.hideMenu();
        });

        List<TowerType> types = new ArrayList<>(List.of(
                TowerType.TXT,
                TowerType.SRC,
                TowerType.BIN,
                TowerType.IMG,
                TowerType.AUD,
                TowerType.VID,
                TowerType.ENC,
                TowerType.ZIP
        ));

        if (Platform.isAndroid() || Platform.isIOS()) {
            types.add(TowerType.AD);
        }

        for (TowerType type : types) {
            float cost = gameMode.getTowerCost(type, 0);
            String text = "Build (" + MemoryUtil.toString(cost) + ")";
            Button button = new Button();
            TowerData data = new TowerData().setType(type);

            if (selected[0] == null) {
                build.setText(text);
                button.setStroke(true);

                selected[0] = type;
                updateStats(data);
            }

            button.setText(type.toString());
            button.setSize(new Vector2(90.0f, 45.0f));
            button.setOnClick(() -> {
                grid.getChildren().forEach(child -> ((Button) child).setStroke(false));
                build.setText(text);
                button.setStroke(true);

                selected[0] = type;
                updateStats(data);
            });

            grid.add(button);
        }
    }
}
