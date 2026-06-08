package com.out_of_box_games.gengine.core.api.render;

import com.out_of_box_games.gengine.util.math.Shape;

public interface ShapeRenderProxy extends RenderProxy {

    Shape getShape();

    void setShape(Shape shape);

    boolean isCyclic();

    void setCyclic(boolean cyclic);
}
