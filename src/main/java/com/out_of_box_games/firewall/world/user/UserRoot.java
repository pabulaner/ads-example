package com.out_of_box_games.firewall.world.user;

public record UserRoot(
        String url,
        float cash,
        UserInfo info
) {
    // empty
}
