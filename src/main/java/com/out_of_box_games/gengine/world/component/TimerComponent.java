package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.world.Component;
import com.sun.security.jgss.GSSUtil;

public class TimerComponent extends Component {

    private static int nextId = 0;

    private float time;

    private boolean active;

    private final Event<Void> onTimeout;

    private final int id;

    public TimerComponent() {
        time = 0.0f;
        active = false;
        onTimeout = new Event<>();
        id = nextId++;
    }

    private float counter;

    @Override
    protected void onUpdate(float delta) {
        counter += delta;
        super.onUpdate(delta);

        if (counter >= 1.0f) {
            counter = 0.0f;
            System.out.println("Update " + id + ": " + time + ", " + active);
        }

        if (active) {
            time -= delta;

            if (time <= 0.0f) {
                active = false;
                onTimeout.invoke();

                System.out.println("Invoke " + id);
            }
        }
    }

    public void start(float time) {
        System.out.println("Start " + id + ": " + time);
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
