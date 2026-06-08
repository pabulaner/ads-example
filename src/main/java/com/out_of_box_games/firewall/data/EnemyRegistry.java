package com.out_of_box_games.firewall.data;

import com.out_of_box_games.firewall.data.enemy.EnemyStaticData;
import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.data.Registry;

public class EnemyRegistry extends Registry<EnemyType, EnemyStaticData> {

    private static EnemyRegistry instance;

    private EnemyRegistry() {
        super(
                new EnemyStaticData()
                        .setType(EnemyType.TXT)
                        .setTexture(texture("txt.png"))
                        .setHealth(1.0f)
                        .setPercentage(false)
                        .setSpeed(1.0f),
                new EnemyStaticData()
                        .setType(EnemyType.SRC)
                        .setTexture(texture("src.png"))
                        .setHealth(2.0f)
                        .setPercentage(false)
                        .setSpeed(0.8f),
                new EnemyStaticData()
                        .setType(EnemyType.BIN)
                        .setTexture(texture("bin.png"))
                        .setHealth(1_000.0f)
                        .setPercentage(false)
                        .setSpeed(1.5f),
                new EnemyStaticData()
                        .setType(EnemyType.IMG)
                        .setTexture(texture("img.png"))
                        .setHealth(4_000.0f)
                        .setPercentage(false)
                        .setSpeed(0.8f),
                new EnemyStaticData()
                        .setType(EnemyType.AUD)
                        .setTexture(texture("aud.png"))
                        .setHealth(3_000.0f)
                        .setPercentage(false)
                        .setSpeed(0.9f),
                new EnemyStaticData()
                        .setType(EnemyType.VID)
                        .setTexture(texture("vid.png"))
                        .setHealth(4_000_000.0f)
                        .setPercentage(false)
                        .setSpeed(0.5f),
                new EnemyStaticData()
                        .setType(EnemyType.ENC)
                        .setTexture(texture("enc.png"))
                        .setHealth(1.0f)
                        .setPercentage(true)
                        .setSpeed(0.7f),
                new EnemyStaticData()
                        .setType(EnemyType.ZIP)
                        .setTexture(texture("zip.png"))
                        .setHealth(1.0f)
                        .setPercentage(true)
                        .setSpeed(0.6f),
                new EnemyStaticData()
                        .setType(EnemyType.AD)
                        .setTexture(texture("ad.png"))
                        .setHealth(1.0f)
                        .setPercentage(true)
                        .setSpeed(1.2f));
    }

    public static EnemyRegistry getInstance() {
        if (instance == null) {
            instance = new EnemyRegistry();
        }

        return instance;
    }

    private static Texture texture(String path) {
        return Engine.get()
                .getAssetLoader()
                .load(Texture.class, "/textures/" + path);
    }
}
