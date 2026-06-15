package com.out_of_box_games.firewall.ui.game;

import com.out_of_box_games.firewall.data.DomainRegistry;
import com.out_of_box_games.firewall.data.EnemyDisplayMode;
import com.out_of_box_games.firewall.data.SpeedMode;
import com.out_of_box_games.firewall.data.TowerDisplayMode;
import com.out_of_box_games.firewall.ui.control.Button;
import com.out_of_box_games.firewall.ui.control.CpuPointsBarGroup;
import com.out_of_box_games.firewall.ui.control.StatsButton;
import com.out_of_box_games.firewall.ui.control.StatsToggleButton;
import com.out_of_box_games.firewall.util.MemoryUtil;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.firewall.world.game.PlayerStateBase;
import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.core.jfx.JfxApplication;
import com.out_of_box_games.gengine.util.math.MathUtil;
import com.out_of_box_games.gengine.util.math.RandomUtil;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import com.out_of_box_games.gengine.world.World;
import com.out_of_box_games.gengine.world.actor.UI;
import com.out_of_box_games.gengine.world.component.CameraComponent;
import com.out_of_box_games.gengine.world.component.TimerComponent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GameUI extends UI {

    private static final Vector2 TOP_CENTER = new Vector2(0.0f, -485.0f);

    private static final Vector2 BOTTOM_CENTER = new Vector2(0.0f, 460.0f);

    private static final float DOMAIN_SIZE = 150.0f;

    private static final float SPACE = 170.0f;

    private static final float CPU_UPDATE_TIME = 0.75f;

    private GameMenu menu;

    private StatsToggleButton<TowerDisplayMode> tower;

    private StatsToggleButton<EnemyDisplayMode> enemy;

    private StatsToggleButton<SpeedMode> speed;

    private final TimerComponent timerComponent;

    public GameUI() {
        timerComponent = addComponent(new TimerComponent());
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        Button domain = new Button();
        domain.getRoot().setTranslation(TOP_CENTER);
        domain.setText("...");
        domain.setStroke(true);
        domain.setSize(new Vector2(400.0f, 50.0f));
        domain.setOnClick(() -> {
            getWorld().setPause(true);
            showMenu(new PauseMenu());
        });

        getWorld().addActor(domain);
        domain.getRoot().attachTo(getRoot());

        tower = addStatsButton(() -> new StatsToggleButton<>(value -> switch (value) {
            case LEVEL -> "Level";
            case USAGE -> "Usage";
        }, TowerDisplayMode.LEVEL, TowerDisplayMode.USAGE), -4, "Process");

        enemy = addStatsButton(() -> new StatsToggleButton<>(value -> switch (value) {
            case HEALTH -> "Health";
            case TIMEOUT -> "Timeout";
            case DESTINATION -> "Dest";
        }, EnemyDisplayMode.HEALTH, EnemyDisplayMode.DESTINATION), -3, "File");

        speed = addStatsButton(() -> new StatsToggleButton<>(value -> switch (value) {
            case _1X -> "1x";
            case _2X -> "2x";
            case _4X -> "4x";
            case _8X -> "8x";
            case _16X -> "16x";
        }, SpeedMode.values()), -2, "Speed");

        World world = getWorld();
        PlayerStateBase playerState = world.getPlayerState();

        tower.onSelectionChange().addListener(ignore -> playerState.setTowerDisplayMode(tower.getSelection()));
        enemy.onSelectionChange().addListener(ignore -> playerState.setEnemyDisplayMode(enemy.getSelection()));
        speed.onSelectionChange().addListener(ignore -> world.setTimeDilation(switch (speed.getSelection()) {
            case _1X -> 1.0f;
            case _2X -> 2.0f;
            case _4X -> 4.0f;
            case _8X -> 8.0f;
            case _16X -> 16.0f;
        }));

        StatsButton website = addStatsButton(StatsButton::new, -1, "Website");
        StatsButton memory = addStatsButton(StatsButton::new, 1, "Memory");
        StatsButton cores = addStatsButton(StatsButton::new, 2, "Cores");
        StatsButton usage = addStatsButton(StatsButton::new, 3, "Usage");
        StatsButton health = addStatsButton(StatsButton::new, 4, "Health");

        GameModeBase gameMode = world.getGameMode();
        GameStateBase gameState = world.getGameState();
        Consumer<Void> statsListener = ignored -> {
            website.setValue(String.valueOf(gameState.getWave()));
            memory.setValue(MemoryUtil.toString(gameState.getCash()));
            cores.setValue(String.valueOf(gameState.getCores()));
            health.setValue((int) MathUtil.ceil(gameState.getHealth()) + "%");
        };
        Consumer<Void> timerListener = ignore -> {
            usage.setValue((int) Math.ceil(gameMode.getCpu().getTotalUsagePercentRandomized()) + "%");
            playerState.onUpdateUsage().invoke();

            timerComponent.start(CPU_UPDATE_TIME);
        };

        gameState.onStatsChange().addListener(statsListener);
        statsListener.accept(null);

        timerComponent.onTimeout().addListener(timerListener);
        timerListener.accept(null);

        gameMode.onNextWave().addListener(ignore -> {
            DomainRegistry registry = DomainRegistry.getInstance();
            String value = RandomUtil.getRandom(new ArrayList<>(registry.all()));

            domain.setText(value);
        });

        CpuPointsBarGroup group = getWorld().addActor(new CpuPointsBarGroup(4));
        group.getTextComponent().setTranslation(new Vector2(480.0f, -40.0f));
        group.getRoot().setTranslation(BOTTOM_CENTER.copy().add(Vector2.left().mul(480.0f)));
        group.getRoot().attachTo(getRoot());

        JfxApplication.getRoot().getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (menu == null) {
                    getWorld().setPause(true);
                    showMenu(new PauseMenu());
                } else {
                    getWorld().setPause(false);
                    hideMenu();
                }
            }
        });
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();

        JfxApplication.getRoot().getScene().setOnKeyPressed(null);
    }

    private <TStatsButton extends StatsButton> TStatsButton addStatsButton(Supplier<TStatsButton> supplier, int index, String name) {
        TStatsButton button = supplier.get();
        Vector2 translation = TOP_CENTER.copy()
                .add(Vector2.right()
                        .mul(MathUtil.sign(index))
                        .mul(DOMAIN_SIZE))
                .add(Vector2.right().mul(index * SPACE));

        getWorld().addActor(button);

        button.setName(name);
        button.getRoot().setTranslation(translation);
        button.getRoot().attachTo(getRoot());

        return button;
    }

    public void showMenu(GameMenu menu) {
        if (this.menu != null) {
            menu.destroy();
            return;
        }

        this.menu = menu;

        getWorld().addActor(menu);
        menu.getRoot().attachTo(getRoot());
    }

    public void hideMenu() {
        if (menu != null) {
            menu.destroy();
            menu = null;
        }
    }

    public TowerDisplayMode getTowerDisplayMode() {
        return tower.getSelection();
    }

    public EnemyDisplayMode getEnemyDisplayMode() {
        return enemy.getSelection();
    }
}
