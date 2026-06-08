package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.core.api.render.TextureRenderProxy;
import com.out_of_box_games.gengine.core.jfx.assets.JfxTexture;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class JfxTextureRenderProxy extends JfxRenderProxy implements TextureRenderProxy {

    private JfxTexture texture;

    @Override
    public void render(GraphicsContext ctx) {
        if (texture == null) {
            return;
        }

        Image image = texture.getImage();
        ctx.save();

        prepareCtx(ctx);
        ctx.drawImage(
                texture.getImage(),
                -image.getWidth() * 0.5,
                -image.getHeight() * 0.5);

        ctx.restore();
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture = (JfxTexture) texture;
    }
}
