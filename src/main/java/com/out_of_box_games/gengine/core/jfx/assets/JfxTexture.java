package com.out_of_box_games.gengine.core.jfx.assets;

import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import javafx.scene.image.Image;

public class JfxTexture extends JfxAsset implements Texture {

    private final Image image;

    public JfxTexture(JfxAssetData data) {
        this.image = new Image(data.getPath());
    }

    @Override
    public Vector2Int getSize() {
        return new Vector2Int((int) image.getWidth(), (int) image.getHeight());
    }

    public Image getImage() {
        return image;
    }
}
