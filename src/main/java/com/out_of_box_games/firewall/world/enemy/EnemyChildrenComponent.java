package com.out_of_box_games.firewall.world.enemy;

import com.out_of_box_games.firewall.data.enemy.EnemyData;
import com.out_of_box_games.firewall.world.map.MapPathFollowComponent;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.world.Component;

import java.util.List;

public class EnemyChildrenComponent extends Component {

    private static final float OFFSET = 16.0f;

    private List<EnemyData> children;

    public EnemyChildrenComponent(EnemyHealthComponent healthComponent) {
        children = List.of();
        healthComponent.onValueMin().addListener(ignore -> spawnChildren());
    }

    private void spawnChildren() {
        Enemy enemy = getActor();
        MapPathFollowComponent follow = enemy.getPathFollowComponent();

        float length = OFFSET * (children.size() - 1);
        float[] progress = { follow.getProgress() - length * 0.5f };

        children.forEach(child -> {
            Persist.create(new Enemy(), child.setProgress(progress[0]), getActor().getWorld())
                    .getRoot()
                    .attachTo(follow.getParent());

            progress[0] += OFFSET;
        });
    }

    public List<EnemyData> getChildren() {
        return children;
    }

    public void setChildren(List<EnemyData> children) {
        this.children = children;
    }
}
