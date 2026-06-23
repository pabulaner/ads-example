package com.out_of_box_games.firewall.world.game;

import com.out_of_box_games.firewall.data.game.GameData;
import com.out_of_box_games.firewall.ui.game.GameOverMenu;
import com.out_of_box_games.firewall.ui.game.GameUI;
import com.out_of_box_games.firewall.util.SaveGame;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.world.actor.GameState;

public class GameStateBase extends GameState implements Persist<GameData> {

    private int wave;

    private float cash;

    private int cores;

    private float usage;

    private float health;

    private final Event<Void> onStatsChange;

    public GameStateBase() {
        wave = 0;
        cash = 1000.0f;
        cores = 1;
        usage = 0.0f;
        health = 100.0f;
        onStatsChange = new Event<>();

        onStatsChange.addListener(ignore -> {
            if (health <= 0.0f) {
                health = 0.0f;

                GameModeBase gameMode = getWorld().getGameMode();
                GameUI ui = getWorld().getUI();

                getWorld().setPause(true);
                ui.hideMenu();
                ui.showMenu(new GameOverMenu());

                SaveGame.remove(gameMode.getLevelType(), gameMode.getLevel());
            }
        });
    }

    @Override
    public void load(GameData data) {
        GameModeBase gameMode = getWorld().getGameMode();

        wave = data.getWave().getIndex();
        cash = data.getCash();
        health = data.getHealth();

        gameMode.getCpu().load(data.getCpu());
        gameMode.getMap().load(data.getMap());
        gameMode.getWaveManager().load(data.getWave());
        gameMode.getUserManager().load(data.getUser());

        onStatsChange.invoke();
    }

    @Override
    public GameData save() {
        GameModeBase gameMode = getWorld().getGameMode();

        return new GameData()
                .setCash(cash)
                .setHealth(health)
                .setCpu(gameMode.getCpu().save())
                .setMap(gameMode.getMap().save())
                .setWave(gameMode.getWaveManager().save())
                .setUser(gameMode.getUserManager().save());
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
        onStatsChange.invoke();
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
        onStatsChange.invoke();
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
        onStatsChange.invoke();
    }

    public float getUsage() {
        return usage;
    }

    public void setUsage(float usage) {
        this.usage = usage;
        onStatsChange.invoke();
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
        onStatsChange.invoke();
    }

    public Event<Void> onStatsChange() {
        return onStatsChange;
    }
}
