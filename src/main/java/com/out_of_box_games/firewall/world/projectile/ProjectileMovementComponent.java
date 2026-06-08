package com.out_of_box_games.firewall.world.projectile;

import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Component;

public class ProjectileMovementComponent extends Component {

    public static final float SPEED = 450.0f;

    private float distance;

    private Vector2 direction;

    public ProjectileMovementComponent() {
        distance = 0.0f;
        direction = Vector2.zero();
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);

        float length = delta * SPEED;
        distance -= length;

        if (distance <= 0.0f) {
            getActor().destroy();
            return;
        }

        getActor().getRoot().translate(direction.copy().mul(length));
    }

    public float getDistance() {
        return distance;
    }

    public ProjectileMovementComponent setDistance(float distance) {
        this.distance = distance;
        return this;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public ProjectileMovementComponent setDirection(Vector2 direction) {
        this.direction = direction;
        return this;
    }
}
