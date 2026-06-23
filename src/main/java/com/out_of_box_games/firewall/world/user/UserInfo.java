package com.out_of_box_games.firewall.world.user;

import com.out_of_box_games.firewall.data.EnemyType;

import java.util.List;

public record UserInfo(
        int waves,
        List<EnemyType> enemies,
        List<String> choices,
        String content)
{
    // empty
}
