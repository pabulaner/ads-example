package com.out_of_box_games.gengine.core.api.render;

import com.out_of_box_games.gengine.core.api.assets.Texture;

public interface TextureRenderProxy extends RenderProxy {

    Texture getTexture();

    void setTexture(Texture texture);
}
