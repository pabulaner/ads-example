package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.util.Color;

public class JfxRenderUtil {

    public static Color fromJfx(javafx.scene.paint.Color color) {
        return Color.fromRgba(
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                (int) (color.getOpacity() * 255));
    }

    public static javafx.scene.paint.Color toJfx(Color color) {
        return javafx.scene.paint.Color.rgb(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                color.getAlpha() / 255.0);
    }
}
