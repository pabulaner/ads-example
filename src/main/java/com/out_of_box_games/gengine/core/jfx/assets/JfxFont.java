package com.out_of_box_games.gengine.core.jfx.assets;

import com.out_of_box_games.gengine.core.api.assets.Font;

public class JfxFont implements Font {

    private final javafx.scene.text.Font font;

    public JfxFont(JfxAssetData data) {
        this.font = javafx.scene.text.Font.loadFont(
                getClass().getResourceAsStream(data.getPath()),
                data.<Float>getParam(0));
    }

    @Override
    public String getFamily() {
        return font.getFamily();
    }

    @Override
    public float getSize() {
        return (float) font.getSize();
    }

    public javafx.scene.text.Font getFont() {
        return font;
    }
}
