package com.out_of_box_games.firewall.world.game;

import com.gluonhq.attach.util.Platform;
import com.out_of_box_games.firewall.data.EnemyType;

import java.util.ArrayList;
import java.util.List;

public class EndlessGameMode extends GameModeBase {

    public EndlessGameMode() {
        setCpuBasePoints(6);
        setCpuPointsPerLevel(2);
        setReward(64.0f);
        setRewardMultiplier(1.25f);
        setTowerBaseCost(512.0f);
        setTowerCostMultiplier(2.5f);
        setTowerSellMultiplier(0.65f);
        setTowerBaseDamage(1000.0f);
        setTowerDamageMultiplier(2.8f);
        setTowerDamageUsageMultiplier(2.5f);
        setTowerWeakAgainstMultiplier(0.20f);
        setTowerBaseReload(1.0f);
        setTowerReloadUsageMultiplier(3.0f);
        setTowerReloadMultiplier(0.97f);
        setTowerBaseRange(300.0f);
        setTowerRangeMultiplier(1.04f);
        setEnemyBaseHealth(1000.0f);
        setEnemyHealthMultiplier(1.35f);
        setEnemyBaseSpeed(55.0f);
        setEnemyDamage(4.0f);
        setUnlockedEnemies(wave -> {
            List<EnemyType> result = new ArrayList<>();
            result.add(EnemyType.TXT);

            if (wave >= 4) result.add(EnemyType.SRC);
            if (wave >= 8) result.add(EnemyType.BIN);
            if (wave >= 12) result.add(EnemyType.IMG);
            if (wave >= 15) result.add(EnemyType.AUD);
            if (wave >= 20) result.add(EnemyType.VID);
            if (wave >= 24) result.add(EnemyType.ENC);
            if (wave >= 27) result.add(EnemyType.ZIP);

            if (wave >= 6 && canShowAd() && (Platform.isAndroid() || Platform.isIOS())) {
                result.add(EnemyType.AD);
            }

            return result;
        });
    }
}
