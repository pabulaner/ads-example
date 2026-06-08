package com.out_of_box_games.gengine.core.api.platform;

import com.out_of_box_games.gengine.util.Event;

public interface PlatformSystem {

    enum Type {

        DESKTOP,
        MOBILE,
        WEB
    }

    enum OS {

        LINUX,
        MAC,
        WINDOWS,
        IOS,
        ANDROID
    }

    void exit();

    Type getType();

    OS getOS();

    Event<Void> onExit();
}
