package com.out_of_box_games.firewall.util;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.firewall.data.TowerType;

public class TypeUtil {

    public static EnemyType toEnemyType(TowerType type) {
        return switch (type) {
            case TXT -> EnemyType.TXT;
            case SRC -> EnemyType.SRC;
            case BIN -> EnemyType.BIN;
            case IMG -> EnemyType.IMG;
            case AUD -> EnemyType.AUD;
            case VID -> EnemyType.VID;
            case ENC -> EnemyType.ENC;
            case ZIP -> EnemyType.ZIP;
            default -> null;
        };
    }
}
