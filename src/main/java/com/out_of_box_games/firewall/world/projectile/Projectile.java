package com.out_of_box_games.firewall.world.projectile;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.data.projectile.ProjectileData;
import com.out_of_box_games.firewall.world.Layers;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.util.math.CircleShape;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.component.ShapeComponent;

public class Projectile extends Actor implements Persist<ProjectileData> {

    private final ShapeComponent shapeComponent;

    private final ProjectileMovementComponent movementComponent;

    private final ProjectileCollisionComponent collisionComponent;

    public Projectile() {
        shapeComponent = addComponent(new ShapeComponent());
        movementComponent = addComponent(new ProjectileMovementComponent());
        collisionComponent = addComponent(new ProjectileCollisionComponent());

        shapeComponent.setLayer(Layers.PROJECTILE);
        shapeComponent.setFill(GameConfig.PRIMARY_COLOR);
        shapeComponent.setStroke(GameConfig.SECONDARY_COLOR);
        shapeComponent.setLineWidth(Map.LINE_WIDTH);
        shapeComponent.setShape(new CircleShape(8.0f, 12));

        setRoot(shapeComponent);
    }


    @Override
    public void load(ProjectileData data) {
        GameModeBase gameMode = getWorld().getGameMode();

        movementComponent.setDistance(data.getDistance());
        movementComponent.setDirection(data.getDirection());
        collisionComponent.setMap(gameMode.getMap());
        collisionComponent.setType(data.getType());
        collisionComponent.setLevel(data.getLevel());
        collisionComponent.setUsage(data.getUsage());
        getRoot().setTranslation(data.getTranslation());
    }

    @Override
    public ProjectileData save() {
        return new ProjectileData()
                .setDistance(movementComponent.getDistance())
                .setDirection(movementComponent.getDirection())
                .setType(collisionComponent.getType())
                .setLevel(collisionComponent.getLevel())
                .setUsage(collisionComponent.getUsage());
    }
}
