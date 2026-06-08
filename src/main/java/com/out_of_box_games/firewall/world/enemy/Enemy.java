package com.out_of_box_games.firewall.world.enemy;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.data.EnemyRegistry;
import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.data.enemy.EnemyData;
import com.out_of_box_games.firewall.data.enemy.EnemyStaticData;
import com.out_of_box_games.firewall.world.Layers;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.firewall.world.map.MapPathFollowComponent;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.World;
import com.out_of_box_games.gengine.world.component.SpriteComponent;

import java.util.function.Consumer;

public class Enemy extends Actor implements Persist<EnemyData> {

    private EnemyType type;

    private final MapPathFollowComponent pathFollowComponent;

    private final SpriteComponent spriteComponent;

    private final EnemyTextComponent textComponent;

    private final EnemyHealthComponent healthComponent;

    private final EnemyTimeoutComponent timeoutComponent;

    private final EnemyChildrenComponent childrenComponent;

    private final Event<Void> onDone;

    public Enemy() {
        pathFollowComponent = addComponent(new MapPathFollowComponent());
        spriteComponent = addComponent(new SpriteComponent());
        textComponent = addComponent(new EnemyTextComponent());
        healthComponent = addComponent(new EnemyHealthComponent());
        timeoutComponent = addComponent(new EnemyTimeoutComponent());
        childrenComponent = addComponent(new EnemyChildrenComponent(healthComponent));
        onDone = new Event<>();

        spriteComponent.setScale(new Vector2(0.1f));
        spriteComponent.setLayer(Layers.ENEMY);
        spriteComponent.setFill(GameConfig.PRIMARY_COLOR);
        textComponent.setTranslation(Vector2.down().mul(40.0f));
        healthComponent.onValueMin().addListener(onDone::invoke);
        onDone.addListener(ignore -> destroy());

        spriteComponent.attachTo(pathFollowComponent);
        textComponent.attachTo(pathFollowComponent);
        setRoot(pathFollowComponent);
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();

        if (type == EnemyType.AD) {
            GameModeBase gameMode = getWorld().getGameMode();
            gameMode.showAd();

            getWorld().getActors(Enemy.class)
                    .stream()
                    .filter(enemy -> enemy != this && enemy.type == EnemyType.AD)
                    .forEach(getWorld()::removeActor);
        }
    }

    @Override
    public void load(EnemyData data) {
        GameModeBase gameMode = getWorld().getGameMode();
        EnemyRegistry registry = EnemyRegistry.getInstance();
        EnemyStaticData staticData = registry.get(data.getType());

        type = data.getType();
        pathFollowComponent.setProgress(data.getProgress());
        pathFollowComponent.setTarget(data.getEnd());
        pathFollowComponent.setSpeed(staticData.getSpeed() * gameMode.getEnemyBaseSpeed());
        spriteComponent.setTexture(staticData.getTexture());
        textComponent.setPercentage(staticData.isPercentage());
        healthComponent.setMax(data.getMaxHealth());
        healthComponent.setValue(data.getHealth());
        childrenComponent.setChildren(data.getChildren());
    }

    @Override
    public EnemyData save() {
        return new EnemyData()
                .setType(type)
                .setProgress(pathFollowComponent.getProgress())
                .setEnd(pathFollowComponent.getTarget())
                .setMaxHealth(healthComponent.getMax())
                .setHealth(healthComponent.getValue())
                .setChildren(childrenComponent.getChildren());
    }

    public static void forEach(Map map, Consumer<Enemy> consumer) {
        map.getPaths().forEach(path -> path.getActors().forEach(actor -> {
            Enemy enemy = (Enemy) actor;
            consumer.accept(enemy);
        }));
    }

    public boolean isDone() {
        return healthComponent.getValue() <= 0.0f;
    }

    public EnemyType getType() {
        return type;
    }

    public MapPathFollowComponent getPathFollowComponent() {
        return pathFollowComponent;
    }

    public SpriteComponent getSpriteComponent() {
        return spriteComponent;
    }

    public EnemyTextComponent getTextComponent() {
        return textComponent;
    }

    public EnemyHealthComponent getHealthComponent() {
        return healthComponent;
    }

    public EnemyTimeoutComponent getTimeoutComponent() {
        return timeoutComponent;
    }

    public EnemyChildrenComponent getChildrenComponent() {
        return childrenComponent;
    }

    public Event<Void> onDone() {
        return onDone;
    }
}
