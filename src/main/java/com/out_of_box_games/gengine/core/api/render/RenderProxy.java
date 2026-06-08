package com.out_of_box_games.gengine.core.api.render;

import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.math.Transform;
import com.out_of_box_games.gengine.util.math.Vector2;

public interface RenderProxy {

    boolean isVisible();

    void setVisible(boolean visible);

    int getLayer();

    void setLayer(int layer);

    Transform getTransform();

    void setTransform(Transform transform);

    Vector2 getPivot();

    void setPivot(Vector2 pivot);

    Color getFill();

    void setFill(Color fill);

    Color getStroke();

    void setStroke(Color stroke);

    float getLineWidth();

    void setLineWidth(float lineWidth);
}
