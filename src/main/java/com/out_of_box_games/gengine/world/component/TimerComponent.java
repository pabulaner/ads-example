package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.world.Component;

public class TimerComponent extends Component {

    private float time;

    private boolean active;

    private final Event<Void> onTimeout;

    public TimerComponent() {
        time = 0.0f;
        active = false;
        onTimeout = new Event<>();
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);

        if (active) {
            time -= delta;

            if (time <= 0.0f) {
                active = false;
                onTimeout.invoke();
            }
        }
    }

    public void start(float time) {
        this.time = time;
        this.active = true;
    }

    public void stop() {
        active = false;
    }

    public float getTime() {
        return time;
    }

    public boolean isActive() {
        return active;
    }

    public Event<Void> onTimeout() {
        return onTimeout;
    }
}
