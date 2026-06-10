package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.core.api.render.TextureRenderProxy;
import com.out_of_box_games.gengine.core.jfx.assets.JfxTexture;
import com.out_of_box_games.gengine.util.math.Transform;
import javafx.scene.canvas.GraphicsContext;
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

        getNode().setImage(texture != null
                ? texture.getImage()
                : null);
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
