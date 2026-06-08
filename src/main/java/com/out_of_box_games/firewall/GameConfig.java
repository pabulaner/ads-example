package com.out_of_box_games.firewall;

import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.core.api.assets.Font;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.math.Vector2;

public class GameConfig {

    public static final Vector2 SIZE = new Vector2(1920.0f, 1080.0f);

    public static final Color PRIMARY_COLOR = Color.fromRgba(0x00D6FFFF);

    public static final Color SECONDARY_COLOR = Color.BLACK;

    public static final Font FONT = Engine.get()
            .getAssetLoader()
            .load(Font.class, "/fonts/RobotoMonoBold.ttf", 24.0f);

    public static final Font TITLE_FONT = Engine.get()
            .getAssetLoader()
            .load(Font.class, "/fonts/RobotoMonoBold.ttf", 72.0f);
}
