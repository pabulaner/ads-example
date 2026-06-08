package com.out_of_box_games.firewall.world.enemy;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.firewall.util.BinaryUtil;
import com.out_of_box_games.firewall.util.MemoryUtil;
import com.out_of_box_games.firewall.world.game.PlayerStateBase;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.gengine.util.math.MathUtil;
import com.out_of_box_games.gengine.world.component.TextComponent;

import java.util.function.Consumer;

public class EnemyTextComponent extends TextComponent {

    private PlayerStateBase playerState;

    private boolean percentage;

    private final Consumer<Void> listener;

    public EnemyTextComponent() {
        setFill(GameConfig.PRIMARY_COLOR);
        setStroke(GameConfig.SECONDARY_COLOR);
        setLineWidth(Map.LINE_WIDTH);
        setFont(GameConfig.FONT);

        listener = ignore -> updateText();
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        Enemy enemy = getActor();
        enemy.getHealthComponent()
                .onValueChange()
                .addListener(ignore -> updateText());
        enemy.getTimeoutComponent()
                .onValueChange()
                .addListener(ignore -> updateText());

        playerState = getActor().getWorld().getPlayerState();
        playerState.onModeChange().addListener(listener);

        updateText();
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();
        playerState.onModeChange().removeListener(listener);
    }

    private void updateText() {
        Enemy enemy = getActor();

        setText(switch (playerState.getEnemyDisplayMode()) {
            case HEALTH -> {
                float health = enemy.getHealthComponent().getValue();
                float maxHealth = enemy.getHealthComponent().getMax();
                String display = percentage
                        ? (int) MathUtil.ceil(health / maxHealth * 100.0f) + "%"
                        : MemoryUtil.toString(health);

                yield health > 0.0f
                        ? display
                        : "";
            }
            case TIMEOUT -> (int) MathUtil.ceil(enemy.getTimeoutComponent().getValue()) + "ms";
            case DESTINATION -> BinaryUtil.toString(enemy.getPathFollowComponent().getTarget());
        });
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }
}
