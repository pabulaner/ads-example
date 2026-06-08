package com.out_of_box_games.firewall.ui.game;

import com.out_of_box_games.firewall.data.TowerRegistry;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.firewall.data.tower.TowerStaticData;
import com.out_of_box_games.firewall.ui.control.Button;
import com.out_of_box_games.firewall.ui.control.Grid;
import com.out_of_box_games.firewall.util.BinaryUtil;
import com.out_of_box_games.firewall.util.MemoryUtil;
import com.out_of_box_games.firewall.util.TypeUtil;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.map.MapPlatform;
import com.out_of_box_games.gengine.util.Align;
import com.out_of_box_games.gengine.util.math.Vector2;

public class TowerMenu extends GameMenu {

    private final MapPlatform platform;

    private final boolean showStats;

    private Grid grid;

    private Button name;

    private Button damage;

    private Button reload;

    private Button range;

    private Button usage;

    private Button level;

    public TowerMenu(MapPlatform platform, boolean showStats) {
        this.platform = platform;
        this.grid = null;
        this.showStats = showStats;
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();
        grid = new Grid();
        grid.getRoot().setTranslation(new Vector2(180.0f, -200.0f));
        grid.setColumns(2);
        grid.setSpacing(new Vector2(20.0f, 45.0f));

        getWorld().addActor(grid);
        grid.getRoot().attachTo(getRoot());

        if (showStats) {
            name = addStat("Name");
            damage = addStat("Damage");
            reload = addStat("Reload");
            range = addStat("Range");
            usage = addStat("Usage");
            level = addStat("Level");
        }
    }

    private Button addStat(String name) {
        Button left = new Button();
        Button right = new Button();
        left.setText(name + ":");
        left.getTextComponent().setAlign(Align.CENTER_RIGHT);
        right.getTextComponent().setAlign(Align.CENTER_LEFT);

        grid.add(left);
        grid.add(right);

        return right;
    }

    protected void updateStats(TowerData data) {
        if (!showStats) {
            return;
        }

        GameModeBase gameMode = getWorld().getGameMode();
        TowerRegistry registry = TowerRegistry.getInstance();
        TowerType type = data.getType();
        TowerStaticData staticData = registry.get(type);
        int level = data.getLevel();
        float usage = gameMode.getCpu().getUsagePercent(type);

        this.name.setText(staticData.getName());
        this.damage.setText(MemoryUtil.toString(gameMode.getTowerDamage(type, level, usage, TypeUtil.toEnemyType(type))));
        this.reload.setText(String.format("%.2fs", gameMode.getTowerReload(type, level, usage)));
        this.range.setText(String.format("%.2f", gameMode.getTowerRange(type, level)));
        this.usage.setText(usage + "%");
        this.level.setText(BinaryUtil.toString(level));
    }

    protected MapPlatform getPlatform() {
        return platform;
    }
}
