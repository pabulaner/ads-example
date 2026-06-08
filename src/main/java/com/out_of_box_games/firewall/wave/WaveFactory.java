package com.out_of_box_games.firewall.wave;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.data.enemy.EnemyData;
import com.out_of_box_games.firewall.data.wave.WaveEntryData;
import com.out_of_box_games.gengine.util.math.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class WaveFactory {

    private static final float MAX_DEPTH = 2;

    private static final int ENC_COUNT = 1;

    private static final float ENC_FACTOR = 0.8f;

    private static final int ZIP_COUNT = 4;

    private static final float ZIP_FACTOR = 1.4f;

    private final WaveContext ctx;

    public WaveFactory(WaveContext ctx) {
        this.ctx = ctx;
    }

    public List<WaveEntryData> create() {
        return create(0);
    }

    public List<WaveEntryData> create(int depth) {
        int budget = ctx.getBudget();
        List<EnemyType> enemies = ctx.getEnemies();
        List<WaveEntryData> result = new ArrayList<>();

        while (budget > 0) {
            int begin = RandomUtil.randomInt(0, ctx.getBegins());
            int end = RandomUtil.randomInt(0, ctx.getEnds());
            EnemyType type = RandomUtil.getRandom(enemies);
            WavePart part = WavePart.getRandom(budget);
            budget -= part.getBudget();

            for (int i = 0; i < part.getCount(); i++) {
                float health = part.getHealth() * ctx.getHealth(type);
                EnemyData data = createEnemy(type, health, end, 0);

                result.add(new WaveEntryData()
                        .setBegin(begin)
                        .setEnemy(data)
                        .setOffset(part.getOffset()));
            }
        }

        return result;
    }

    private EnemyData createEnemy(EnemyType type, float health, int end, int depth) {
        List<EnemyData> children = new ArrayList<>();
        EnemyData enemy = new EnemyData()
                .setType(type)
                .setEnd(end)
                .setMaxHealth(health)
                .setHealth(health)
                .setProgress(0.0f)
                .setChildren(children);

        int count = switch (type) {
            case ENC -> ENC_COUNT;
            case ZIP -> ZIP_COUNT;
            default -> 0;
        };

        float factor = switch (type) {
            case ENC -> ENC_FACTOR;
            case ZIP -> ZIP_FACTOR;
            default -> 0.0f;
        };

        for (int i = 0; i < count; i++) {
            EnemyType other = RandomUtil.getRandom(ctx.getEnemies()
                    .stream()
                    .filter(value -> depth < MAX_DEPTH || (value != type && value != EnemyType.ENC && value != EnemyType.ZIP))
                    .toList());

            children.add(createEnemy(other, health * factor / count, end, depth + 1));
        }

        return enemy;
    }
}
