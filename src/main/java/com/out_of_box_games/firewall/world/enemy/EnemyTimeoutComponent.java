package com.out_of_box_games.firewall.world.enemy;

import com.out_of_box_games.gengine.world.component.ValueComponent;

public class EnemyTimeoutComponent extends ValueComponent<Float> {

    public EnemyTimeoutComponent() {
        super(0.0f);
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        setValue(getValue() - delta);
    }
}
