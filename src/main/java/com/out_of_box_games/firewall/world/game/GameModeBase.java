package com.out_of_box_games.firewall.world.game;

import com.out_of_box_games.firewall.data.EnemyRegistry;
import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.data.TowerRegistry;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.data.cpu.CpuData;
import com.out_of_box_games.firewall.data.enemy.EnemyStaticData;
import com.out_of_box_games.firewall.data.game.GameData;
import com.out_of_box_games.firewall.data.map.MapData;
import com.out_of_box_games.firewall.data.tower.TowerStaticData;
import com.out_of_box_games.firewall.data.wave.WaveData;
import com.out_of_box_games.firewall.util.SaveGame;
import com.out_of_box_games.firewall.wave.WaveContext;
import com.out_of_box_games.firewall.wave.WaveFactory;
import com.out_of_box_games.firewall.world.cpu.Cpu;
import com.out_of_box_games.firewall.world.enemy.Enemy;
import com.out_of_box_games.firewall.world.map.Map;
import com.out_of_box_games.firewall.world.map.MapBeginNode;
import com.out_of_box_games.firewall.world.map.MapEndNode;
import com.out_of_box_games.firewall.world.wave.WaveManager;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.util.math.MathUtil;
import com.out_of_box_games.gengine.world.World;
import com.out_of_box_games.gengine.world.actor.GameMode;
import com.out_of_box_games.gengine.world.component.TimerComponent;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class GameModeBase extends GameMode {

    private static final float AD_TIMER = 5.0f * 60.0f;

    private final Map map;

    private final Cpu cpu;

    private final WaveManager waveManager;

    private int level;

    private int cpuBasePoints;

    private int cpuPointsPerLevel;

    private float reward;

    private float rewardMultiplier;

    private float towerBaseCost;

    private float towerCostMultiplier;

    private float towerSellMultiplier;

    private float towerBaseDamage;

    private float towerDamageMultiplier;

    private float towerDamageUsageMultiplier;

    private float towerWeakAgainstMultiplier;

    private float towerBaseReload;

    private float towerReloadMultiplier;

    private float towerReloadUsageMultiplier;

    private float towerBaseRange;

    private float towerRangeMultiplier;

    private float enemyBaseHealth;

    private float enemyHealthMultiplier;

    private float enemyBaseSpeed;

    private float enemyRewardMultiplier;

    private float enemyDamage;

    private Function<Integer, List<EnemyType>> unlockedEnemies;

    private boolean nextWave;

    private boolean canShowAd;

    private final TimerComponent adTimerComponent;

    private final Event<Void> onNextWave;

    public GameModeBase() {
        map = new Map();
        cpu = new Cpu();
        nextWave = false;
        waveManager = new WaveManager(map);
        canShowAd = true;
        adTimerComponent = addComponent(new TimerComponent());
        onNextWave = new Event<>();
        waveManager.onDone().addListener(ignore -> nextWave = true);

        adTimerComponent.onTimeout().addListener(ignore -> canShowAd = true);

        TimerComponent timer = addComponent(new TimerComponent());
        timer.onTimeout().addListener(ignore -> nextWave = true);
        timer.start(2.0f);
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();
        World world = getWorld();

        world.addActor(map);
        world.addActor(cpu);
        world.addActor(waveManager);
    }

    @Override
    public void beginPlay() {
        super.beginPlay();

        GameStateBase gameState = getWorld().getGameState();

        gameState.load(new GameData()
                .setHealth(64.0f)
                .setCash(100000.0f)
                .setCpu(new CpuData())
                .setMap(new MapData().setType(level))
                .setWave(new WaveData()
                        .setIndex(32)
                        .setEntries(List.of())));

        try {
            // gameState.load(Objects.requireNonNull(SaveGame.load(level)));
        } catch (Exception ignore) {

        }

        cpu.onPointsChange().invoke();
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);

        boolean done = getWorld().getActors(Enemy.class)
                .stream()
                .map(Enemy::isDone)
                .reduce((first, second) -> first && second)
                .orElse(true);

        if (nextWave && done) {
            save();
            startWave();
        }
    }

    public void save() {
//        GameStateBase gameState = getWorld().getGameState();
//        SaveGame.save(level, gameState.save());
    }

    private void startWave() {
        nextWave = false;

        GameStateBase gameState = getWorld().getGameState();
        int wave = gameState.getWave() + 1;

        WaveFactory factory = new WaveFactory(new WaveContext()
                .setBudget(wave)
                .setBegins(map.getNodes(MapBeginNode.class).size())
                .setEnds(map.getNodes(MapEndNode.class).size())
                .setHealth(type -> getEnemyHealth(type, wave))
                .setEnemies(unlockedEnemies.apply(wave)));

        gameState.setWave(wave);
        waveManager.start(factory.create());
        onNextWave.invoke();
    }

    public void showAd() {
        if (canShowAd) {
            canShowAd = false;
            adTimerComponent.start(AD_TIMER);
        }
    }

    public boolean canShowAd() {
        return canShowAd;
    }

    public Map getMap() {
        return map;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public Cpu getCpu() {
        return cpu;
    }

    private float getValue(float value, float multiplier, int exp) {
        return value * MathUtil.pow(multiplier, exp);
    }

    public float getTowerCost(TowerType type, int level) {
        TowerStaticData data = TowerRegistry.getInstance().get(type);
        return getValue(towerBaseCost * data.getCost(), towerCostMultiplier, level);
    }

    public float getTowerSell(TowerType type, int level) {
        float value = 0;

        while (level >= 0) {
            value += getTowerCost(type, level--);
        }

        return value * towerSellMultiplier;
    }

    public float getTowerDamage(TowerType towerType, int level, float usage, EnemyType enemyType) {
        TowerStaticData towerData = TowerRegistry.getInstance().get(towerType);
        EnemyStaticData enemyData = EnemyRegistry.getInstance().get(enemyType);
        float damage = towerBaseDamage;
        float health = 1.0f;
        float multiplier = MathUtil.interpolate(1.0f, towerDamageUsageMultiplier, usage / Cpu.MAX_USAGE);

        if (enemyType != null) {
            health = enemyData.getHealth();
            if (towerData.getWeakAgainst().contains(enemyType)) {
                damage *= towerWeakAgainstMultiplier;
            } else if (!towerData.getStrongAgainst().contains(enemyType)) {
                return 0.0f;
            }
        }

        return getValue(damage * health * towerData.getDamage(), towerDamageMultiplier, level) * multiplier;
    }

    public float getTowerReload(TowerType type, int level, float usage) {
        TowerStaticData data = TowerRegistry.getInstance().get(type);
        float multiplier = MathUtil.interpolate(1.0f, 1.0f / towerReloadUsageMultiplier, usage / Cpu.MAX_USAGE);

        return getValue(towerBaseReload * data.getReload(), towerReloadMultiplier, level) * multiplier;
    }

    public float getTowerRange(TowerType type, int level) {
        TowerStaticData data = TowerRegistry.getInstance().get(type);
        return getValue(towerBaseRange * data.getRange(), towerRangeMultiplier, level);
    }

    public float getEnemyHealth(EnemyType type, int wave) {
        EnemyStaticData data = EnemyRegistry.getInstance().get(type);
        return getValue(enemyBaseHealth * data.getHealth(), enemyHealthMultiplier, wave - 1);
    }

    public float getEnemyReward(EnemyType type, float health, int wave) {
        return getValue(reward * health / getEnemyHealth(type, wave), rewardMultiplier, wave - 1);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCpuBasePoints() {
        return cpuBasePoints;
    }

    protected void setCpuBasePoints(int cpuBasePoints) {
        this.cpuBasePoints = cpuBasePoints;
    }

    public int getCpuPointsPerLevel() {
        return cpuPointsPerLevel;
    }

    protected void setCpuPointsPerLevel(int cpuPointsPerLevel) {
        this.cpuPointsPerLevel = cpuPointsPerLevel;
    }

    public float getReward() {
        return reward;
    }

    protected void setReward(float reward) {
        this.reward = reward;
    }

    public float getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(float rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public float getTowerBaseCost() {
        return towerBaseCost;
    }

    protected void setTowerBaseCost(float towerBaseCost) {
        this.towerBaseCost = towerBaseCost;
    }

    public float getTowerCostMultiplier() {
        return towerCostMultiplier;
    }

    protected void setTowerCostMultiplier(float towerCostMultiplier) {
        this.towerCostMultiplier = towerCostMultiplier;
    }

    public float getTowerSellMultiplier() {
        return towerSellMultiplier;
    }

    protected void setTowerSellMultiplier(float towerSellMultiplier) {
        this.towerSellMultiplier = towerSellMultiplier;
    }

    public float getTowerBaseDamage() {
        return towerBaseDamage;
    }

    protected void setTowerBaseDamage(float towerBaseDamage) {
        this.towerBaseDamage = towerBaseDamage;
    }

    public float getTowerDamageMultiplier() {
        return towerDamageMultiplier;
    }

    protected void setTowerDamageMultiplier(float towerDamageMultiplier) {
        this.towerDamageMultiplier = towerDamageMultiplier;
    }

    public float getTowerDamageUsageMultiplier() {
        return towerDamageUsageMultiplier;
    }

    public void setTowerDamageUsageMultiplier(float towerDamageUsageMultiplier) {
        this.towerDamageUsageMultiplier = towerDamageUsageMultiplier;
    }

    public float getTowerWeakAgainstMultiplier() {
        return towerWeakAgainstMultiplier;
    }

    protected void setTowerWeakAgainstMultiplier(float towerWeakAgainstMultiplier) {
        this.towerWeakAgainstMultiplier = towerWeakAgainstMultiplier;
    }

    public float getTowerBaseReload() {
        return towerBaseReload;
    }

    public float getTowerReloadUsageMultiplier() {
        return towerReloadUsageMultiplier;
    }

    public void setTowerReloadUsageMultiplier(float towerReloadUsageMultiplier) {
        this.towerReloadUsageMultiplier = towerReloadUsageMultiplier;
    }

    protected void setTowerBaseReload(float towerBaseReload) {
        this.towerBaseReload = towerBaseReload;
    }

    public float getTowerReloadMultiplier() {
        return towerReloadMultiplier;
    }

    protected void setTowerReloadMultiplier(float towerReloadMultiplier) {
        this.towerReloadMultiplier = towerReloadMultiplier;
    }

    public float getTowerBaseRange() {
        return towerBaseRange;
    }

    protected void setTowerBaseRange(float towerBaseRange) {
        this.towerBaseRange = towerBaseRange;
    }

    public float getTowerRangeMultiplier() {
        return towerRangeMultiplier;
    }

    protected void setTowerRangeMultiplier(float towerRangeMultiplier) {
        this.towerRangeMultiplier = towerRangeMultiplier;
    }

    public float getEnemyBaseHealth() {
        return enemyBaseHealth;
    }

    protected void setEnemyBaseHealth(float enemyBaseHealth) {
        this.enemyBaseHealth = enemyBaseHealth;
    }

    public float getEnemyHealthMultiplier() {
        return enemyHealthMultiplier;
    }

    protected void setEnemyHealthMultiplier(float enemyHealthMultiplier) {
        this.enemyHealthMultiplier = enemyHealthMultiplier;
    }

    public float getEnemyBaseSpeed() {
        return enemyBaseSpeed;
    }

    protected void setEnemyBaseSpeed(float enemyBaseSpeed) {
        this.enemyBaseSpeed = enemyBaseSpeed;
    }

    public float getEnemyRewardMultiplier() {
        return enemyRewardMultiplier;
    }

    protected void setEnemyRewardMultiplier(float enemyRewardMultiplier) {
        this.enemyRewardMultiplier = enemyRewardMultiplier;
    }

    public float getEnemyDamage() {
        return enemyDamage;
    }

    protected void setEnemyDamage(float enemyDamage) {
        this.enemyDamage = enemyDamage;
    }

    public Function<Integer, List<EnemyType>> getUnlockedEnemies() {
        return unlockedEnemies;
    }

    protected void setUnlockedEnemies(Function<Integer, List<EnemyType>> unlockedEnemies) {
        this.unlockedEnemies = unlockedEnemies;
    }

    public Event<Void> onNextWave() {
        return onNextWave;
    }
}
