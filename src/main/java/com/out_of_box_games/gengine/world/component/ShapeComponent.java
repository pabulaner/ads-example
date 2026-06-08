package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.core.api.render.ShapeRenderProxy;
import com.out_of_box_games.gengine.util.math.Shape;

public class ShapeComponent extends RenderComponent<ShapeRenderProxy> {

    public ShapeComponent() {
        super(ShapeRenderProxy.class);
        ShapeRenderProxy proxy = getProxy();

        proxy.setShape(null);
        proxy.setCyclic(true);
    }

    public Shape getShape() {
        return getProxy().getShape();
    }

    public void setShape(Shape shape) {
        getProxy().setShape(shape);
    }

    public boolean isCyclic() {
        return getProxy().isCyclic();
    }

    public void setCyclic(boolean cyclic) {
        getProxy().setCyclic(cyclic);
    }
}
