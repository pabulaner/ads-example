package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.render.RenderProxy;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.math.Transform;
import com.out_of_box_games.gengine.util.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;

public abstract class JfxRenderProxy implements RenderProxy {

    private boolean visible;

    private int layer;

    private Transform transform;

    private Vector2 pivot;

    private javafx.scene.paint.Color fill;

    private javafx.scene.paint.Color stroke;

    private float lineWidth;

    public abstract void render(GraphicsContext ctx);

    protected void prepareCtx(GraphicsContext ctx) {
        Transform transform = getTransform();

        ctx.translate(transform.getTranslation().x, transform.getTranslation().y);
        ctx.rotate(transform.getRotation());
        ctx.scale(transform.getScale().x, transform.getScale().y);
        ctx.setFill(fill);
        ctx.setStroke(stroke);
        ctx.setLineWidth(lineWidth);
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    @Override
    public Vector2 getPivot() {
        return pivot;
    }

    @Override
    public void setPivot(Vector2 pivot) {
        this.pivot = pivot;
    }

    @Override
    public Color getFill() {
        return JfxRenderUtil.fromJfx(fill);
    }

    @Override
    public void setFill(Color fill) {
        this.fill = JfxRenderUtil.toJfx(fill);
    }

    @Override
    public Color getStroke() {
        return JfxRenderUtil.fromJfx(stroke);
    }

    @Override
    public void setStroke(Color stroke) {
        this.stroke = JfxRenderUtil.toJfx(stroke);
    }

    @Override
    public float getLineWidth() {
        return lineWidth;
    }

    @Override
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
}
