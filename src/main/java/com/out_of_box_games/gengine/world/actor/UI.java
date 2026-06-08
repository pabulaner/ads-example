package com.out_of_box_games.gengine.world.actor;

import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.component.TransformComponent;

public class UI extends Actor {

    public UI() {
        setRoot(addComponent(new TransformComponent()));
    }
}
