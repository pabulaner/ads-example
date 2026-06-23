package com.out_of_box_games.firewall.world.user;

import com.out_of_box_games.firewall.data.game.UserData;
import com.out_of_box_games.firewall.level.TerminalLevel;
import com.out_of_box_games.firewall.terminal.ButtonLine;
import com.out_of_box_games.firewall.terminal.Line;
import com.out_of_box_games.firewall.terminal.Terminal;
import com.out_of_box_games.firewall.terminal.TextLine;
import com.out_of_box_games.firewall.util.SaveGame;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;
import com.out_of_box_games.firewall.world.game.UserGameMode;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.actor.GameMode;

import java.util.ArrayList;
import java.util.List;

public class UserManager extends Actor implements Persist<UserData> {

    private final GameMode gameMode;

    private final String user;

    private String url;

    private int wave;

    private UserInfo info;

    public UserManager(String user, GameMode gameMode) {
        this.user = user;
        this.gameMode = gameMode;
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        if (user != null) {
            ((UserGameMode) gameMode).onWaveFinish().addListener(ignore -> {
                if (url == null) {
                    return;
                }

                wave += 1;

                if (wave >= info.waves()) {
                    url = null;
                    wave = 0;

                    showTerminal();
                }
            });
        }
    }

    @Override
    public void load(UserData data) {
        if (user == null) {
            return;
        }

        if (data != null) {
            url = data.getUrl();
            wave = data.getWave();
            info = new User(user).load(url);
        } else {
            UserRoot root = new User(user).load();
            GameStateBase gameState = getWorld().getGameState();

            url = root.url();
            wave = 0;
            info = root.info();

            gameState.setCash(root.cash());
        }
    }

    @Override
    public UserData save() {
        return new UserData()
                .setUrl(url)
                .setWave(wave);
    }

    private void showTerminal() {
        GameModeBase gameMode = getWorld().getGameMode();
        Terminal terminal = getWorld().addActor(new Terminal());
        List<Line> lines = new ArrayList<>();

        lines.add(new TextLine(info.content() + "\n"));

        for (String choice : info.choices()) {
            lines.add(new ButtonLine(" " + choice + " ", () -> {
                url = choice;
                info = new User(user).load(url);

                terminal.destroy();
            }));
        }

        if (info.choices().isEmpty()) {
            lines.add(new ButtonLine(" Exit ", () -> {
                SaveGame.remove(SaveGame.Type.USER, gameMode.getLevel());
                getWorld().loadLevel(new TerminalLevel());
            }));
        }

        terminal.show(lines);
    }

    public boolean isDone() {
        return url != null;
    }

    public String getUrl() {
        return url;
    }

    public UserInfo getInfo() {
        return info;
    }
}
