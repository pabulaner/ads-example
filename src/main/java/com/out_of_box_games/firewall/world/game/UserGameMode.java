package com.out_of_box_games.firewall.world.game;

import com.out_of_box_games.firewall.util.SaveGame;
import com.out_of_box_games.gengine.util.Event;

public class UserGameMode extends GameModeBase {

    private boolean done;

    private final Event<Void> onWaveFinish;

    public UserGameMode(String user) {
        super(SaveGame.Type.USER, user);

        done = true;
        onWaveFinish = new Event<>();

        setUnlockedEnemies(wave -> getUserManager().getInfo().enemies());
    }

    @Override
    protected void onUpdate(float delta) {
        boolean tmp = super.isDone();

        if (nextWave && tmp && !done) {
            onWaveFinish.invoke();
        }

        done = tmp;
        super.onUpdate(delta);
    }

    @Override
    public String getDomain() {
        return getUserManager().getUrl();
    }

    @Override
    public boolean isDone() {
        return super.isDone() && getUserManager().isDone();
    }

    public Event<Void> onWaveFinish() {
        return onWaveFinish;
    }
}
