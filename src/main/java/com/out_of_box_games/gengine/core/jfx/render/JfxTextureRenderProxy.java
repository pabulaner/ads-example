package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.core.api.render.TextureRenderProxy;
import com.out_of_box_games.gengine.core.jfx.assets.JfxTexture;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JfxTextureRenderProxy extends JfxRenderProxy<ImageView> implements TextureRenderProxy {

    private JfxTexture texture;

    public JfxTextureRenderProxy() {
        super(new ImageView());
    }

    @Override
    public void update() {
        super.update();

        ImageView node = getNode();

        if (texture != null) {
            Image image = texture.getImage();

            node.setTranslateX(node.getTranslateX() - 0.5 * image.getWidth());
            node.setTranslateY(node.getTranslateY() - 0.5 * image.getHeight());
            node.setImage(image);
        } else {
            node.setImage(null);
        }
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture = (JfxTexture) texture;
        update();
    }
}
