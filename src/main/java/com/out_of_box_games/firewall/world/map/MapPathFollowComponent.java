package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.gengine.world.component.PathFollowComponent;

public class MapPathFollowComponent extends PathFollowComponent {

    private int target;

    private float speed;

    public MapPathFollowComponent() {
        target = -1;
        speed = 0.0f;
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        setProgress(getProgress() + delta * speed);

        if (getProgressRatio() >= 1.0f) {
            MapPath path = getParent().getActor();

            setProgress(0.0f);
            path.getEnd().handle(this);
        }
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
