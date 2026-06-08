package com.out_of_box_games.firewall.data.enemy;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.data.TypeData;

public class EnemyStaticData extends TypeData<EnemyStaticData, EnemyType> {

    private Texture texture;

    private float health;

    private boolean percentage;

    private float speed;

    public Texture getTexture() {
        return texture;
    }

    public EnemyStaticData setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public float getHealth() {
        return health;
    }

    public EnemyStaticData setHealth(float health) {
        this.health = health;
        return this;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public EnemyStaticData setPercentage(boolean percentage) {
        this.percentage = percentage;
        return this;
    }

    public float getSpeed() {
        return speed;
    }

    public EnemyStaticData setSpeed(float speed) {
        this.speed = speed;
        return this;
    }
}
