package com.out_of_box_games.gengine.core.jfx.platform;

import com.out_of_box_games.gengine.core.api.platform.PlatformSystem;
import com.out_of_box_games.gengine.util.Event;
import javafx.application.Platform;

public class JfxPlatformSystem implements PlatformSystem {

    @Override
    public void exit() {
        Platform.exit();
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public OS getOS() {
        return null;
    }

    @Override
    public Event<Void> onExit() {
        return null;
    }
}
