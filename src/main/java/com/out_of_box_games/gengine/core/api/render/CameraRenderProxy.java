package com.out_of_box_games.gengine.core.api.render;

import com.out_of_box_games.gengine.util.math.Vector2;

public interface CameraRenderProxy extends RenderProxy {

    Vector2 getPrefSize();

    void setPrefSize(Vector2 prefSize);

    Vector2 getSize();
}
