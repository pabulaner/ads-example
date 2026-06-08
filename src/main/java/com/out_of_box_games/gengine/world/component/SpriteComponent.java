package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.core.api.render.TextureRenderProxy;

public class SpriteComponent extends RenderComponent<TextureRenderProxy> {

    public SpriteComponent() {
        super(TextureRenderProxy.class);
        getProxy().setTexture(null);
    }

    public Texture getTexture() {
        return getProxy().getTexture();
    }

    public void setTexture(Texture texture) {
        getProxy().setTexture(texture);
    }
}
