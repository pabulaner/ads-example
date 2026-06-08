package com.out_of_box_games.firewall.data;

import com.out_of_box_games.firewall.data.tower.TowerStaticData;
import com.out_of_box_games.gengine.data.Registry;

import java.util.List;

public class TowerRegistry extends Registry<TowerType, TowerStaticData> {

    private static TowerRegistry instance;

    private TowerRegistry() {
        super(
                new TowerStaticData()
                        .setType(TowerType.TXT)
                        .setName("text")
                        .setStrongAgainst(List.of(EnemyType.TXT))
                        .setWeakAgainst(List.of(EnemyType.SRC))
                        .setCost(1.0f)
                        .setDamage(0.3f)
                        .setReload(1.0f)
                        .setRange(1.0f),
                new TowerStaticData()
                        .setType(TowerType.SRC)
                        .setName("source")
                        .setStrongAgainst(List.of(EnemyType.SRC))
                        .setWeakAgainst(List.of(EnemyType.TXT))
                        .setCost(1.5f)
                        .setDamage(0.25f)
                        .setReload(1.2f)
                        .setRange(1.0f),
                new TowerStaticData()
                        .setType(TowerType.BIN)
                        .setName("binary")
                        .setStrongAgainst(List.of(EnemyType.BIN))
                        .setWeakAgainst(List.of(
                                EnemyType.IMG,
                                EnemyType.AUD,
                                EnemyType.VID))
                        .setCost(1.8f)
                        .setDamage(0.15f)
                        .setReload(1.5f)
                        .setRange(1.0f),
                new TowerStaticData()
                        .setType(TowerType.IMG)
                        .setName("image")
                        .setStrongAgainst(List.of(EnemyType.IMG))
                        .setWeakAgainst(List.of(EnemyType.VID))
                        .setCost(1.6f)
                        .setDamage(0.35f)
                        .setReload(0.75f)
                        .setRange(1.0f),
                new TowerStaticData()
                        .setType(TowerType.AUD)
                        .setName("audio")
                        .setStrongAgainst(List.of(EnemyType.AUD))
                        .setWeakAgainst(List.of(EnemyType.VID))
                        .setCost(1.7f)
                        .setDamage(0.20f)
                        .setReload(1.1f)
                        .setRange(1.0f),
                new TowerStaticData()
                        .setType(TowerType.VID)
                        .setName("video")
                        .setStrongAgainst(List.of(EnemyType.VID))
                        .setWeakAgainst(List.of(
                                EnemyType.IMG,
                                EnemyType.AUD))
                        .setCost(2.0f)
                        .setDamage(0.45f)
                        .setReload(2.5f)
                        .setRange(1.0f),
                new TowerStaticData()
                        .setType(TowerType.ENC)
                        .setName("encrypted")
                        .setStrongAgainst(List.of(EnemyType.ENC))
                        .setWeakAgainst(List.of())
                        .setCost(2.5f)
                        .setDamage(0.35f)
                        .setReload(1.3f)
                        .setRange(1.5f),
                new TowerStaticData()
                        .setType(TowerType.ZIP)
                        .setName("archive")
                        .setStrongAgainst(List.of(EnemyType.ZIP))
                        .setWeakAgainst(List.of())
                        .setCost(3.0f)
                        .setDamage(0.4f)
                        .setReload(1.5f)
                        .setRange(1.5f),
                new TowerStaticData()
                        .setType(TowerType.ALL)
                        .setName("all")
                        .setStrongAgainst(List.of())
                        .setWeakAgainst(List.of(
                                EnemyType.TXT,
                                EnemyType.SRC,
                                EnemyType.BIN,
                                EnemyType.IMG,
                                EnemyType.AUD,
                                EnemyType.VID))
                        .setCost(3.5f)
                        .setDamage(0.05f)
                        .setReload(0.9f)
                        .setRange(0.75f),
                new TowerStaticData()
                        .setType(TowerType.AD)
                        .setName("ad")
                        .setStrongAgainst(List.of(EnemyType.AD))
                        .setWeakAgainst(List.of())
                        .setCost(5.0f)
                        .setDamage(0.25f)
                        .setReload(1.4f)
                        .setRange(0.5f),
                new TowerStaticData()
                        .setType(TowerType.CPU)
                        .setName("cpu")
                        .setStrongAgainst(List.of())
                        .setWeakAgainst(List.of())
                        .setCost(5.0f)
                        .setDamage(0.0f)
                        .setReload(0.0f)
                        .setRange(0.0f),
                new TowerStaticData()
                        .setType(TowerType.CLN)
                        .setName("clean")
                        .setStrongAgainst(List.of())
                        .setWeakAgainst(List.of())
                        .setCost(12.0f)
                        .setDamage(0.01f)
                        .setReload(60.0f)
                        .setRange(0.0f));
    }

    public static TowerRegistry getInstance() {
        if (instance == null) {
            instance = new TowerRegistry();
        }

        return instance;
    }
}
