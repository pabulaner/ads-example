package com.out_of_box_games.firewall.world.game;

import com.gluonhq.attach.util.Platform;
import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.util.SaveGame;

import java.util.ArrayList;
import java.util.List;

public class EditionGameMode extends GameModeBase {

    public EditionGameMode() {
        super(SaveGame.Type.EDITION, null);

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
