package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.core.api.render.RenderProxy;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.math.Transform;
import com.out_of_box_games.gengine.util.math.Vector2;

public abstract class RenderComponent<TProxy extends RenderProxy> extends TransformComponent {

    private final TProxy proxy;

    public RenderComponent(Class<TProxy> proxyClass) {
        proxy = Engine.get()
                .getRenderSystem()
                .create(proxyClass);

        proxy.setVisible(true);
        proxy.setLayer(0);
        proxy.setTransform(Transform.identity());
        proxy.setPivot(Vector2.zero());
        proxy.setFill(Color.WHITE);
        proxy.setStroke(Color.TRANSPARENT);
        proxy.setLineWidth(0.0f);
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();
        Engine.get()
                .getRenderSystem()
                .add(proxy);
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();
        Engine.get()
                .getRenderSystem()
                .remove(proxy);
    }

    @Override
    protected void invalidate() {
        super.invalidate();
        proxy.setTransform(getGlobalTransform());
    }

    protected TProxy getProxy() {
        return proxy;
    }

    public boolean isVisible() {
        return proxy.isVisible();
    }

    public void setVisible(boolean visible) {
        proxy.setVisible(visible);
    }

    public int getLayer() {
        return proxy.getLayer();
    }

    public void setLayer(int layer) {
        proxy.setLayer(layer);
    }

    public Vector2 getPivot() {
        return proxy.getPivot();
    }

    public void setPivot(Vector2 pivot) {
        proxy.setPivot(pivot);
    }

    public Color getFill() {
        return proxy.getFill();
    }

    public void setFill(Color fill) {
        proxy.setFill(fill);
    }

    public Color getStroke() {
        return proxy.getStroke();
    }

    public void setStroke(Color stroke) {
        proxy.setStroke(stroke);
    }

    public float getLineWidth() {
        return proxy.getLineWidth();
    }

    public void setLineWidth(float lineWidth) {
        proxy.setLineWidth(lineWidth);
    }
}
