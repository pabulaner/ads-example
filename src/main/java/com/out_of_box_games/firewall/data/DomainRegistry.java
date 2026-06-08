package com.out_of_box_games.firewall.data;

import com.out_of_box_games.firewall.data.domain.DomainStaticData;
import com.out_of_box_games.gengine.data.Registry;

import java.util.List;

public class DomainRegistry extends Registry<String, DomainStaticData> {

    private static DomainRegistry instance;

    private DomainRegistry() {
        // txt and src enemies are added automatically as well as enc and zip
        super(
//                new DomainStaticData()
//                        .setType("epicgames.com")
//                        .setEnemies(List.of(EnemyType.BIN, EnemyType.IMG, EnemyType.VID)),
//                new DomainStaticData()
//                        .setType("youtube.com")
//                        .setEnemies(List.of(EnemyType.AUD, EnemyType.VID, EnemyType.AD)),
//                new DomainStaticData()
//                        .setType("spotify.com")
//                        .setEnemies(List.of(EnemyType.IMG, EnemyType.AUD, EnemyType.AD)),
//                new DomainStaticData()
//                        .setType("steampowered.com")
//                        .setEnemies(List.of(EnemyType.BIN, EnemyType.IMG, EnemyType.VID)),
//                new DomainStaticData()
//                        .setType("wikipedia.org")
//                        .setEnemies(List.of(EnemyType.IMG, EnemyType.AUD, EnemyType.VID)),
                new DomainStaticData()
                        .setType("out-of-box-games.com")
                        .setEnemies(List.of(EnemyType.IMG)));
    }

    public static DomainRegistry getInstance() {
        if (instance == null) {
            instance = new DomainRegistry();
        }

        return instance;
    }
}
