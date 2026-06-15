package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.render.RenderProxy;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.math.Transform;
import com.out_of_box_games.gengine.util.math.Vector2;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;

public abstract class JfxRenderProxy<TNode extends Node> implements RenderProxy {

    private final TNode node;

    private boolean visible;

    private int layer;

    private Transform transform;

    private Vector2 pivot;

    private javafx.scene.paint.Color fill;

    private javafx.scene.paint.Color stroke;

    private float lineWidth;

    public JfxRenderProxy(TNode node) {
        this.node = node;
    }

    public void update() {
        if (transform == null) {
            return;
        }

        Vector2 translation = transform.getTranslation();
        Vector2 scale = transform.getScale();

        node.setVisible(visible);
        node.setViewOrder(-layer);
        node.setTranslateX(translation.x);
        node.setTranslateY(translation.y);
        node.setRotate(transform.getRotation());
        node.setScaleX(scale.x);
        node.setScaleY(scale.y);
    }

    public TNode getNode() {
        return node;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        update();
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
        update();
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public void setTransform(Transform transform) {
        this.transform = transform;
        update();
    }

    @Override
    public Vector2 getPivot() {
        return pivot;
    }

    @Override
    public void setPivot(Vector2 pivot) {
        this.pivot = pivot;
        update();
    }

    @Override
    public Color getFill() {
        return JfxRenderUtil.fromJfx(fill);
    }

    protected javafx.scene.paint.Color getFillRaw() {
        return fill;
    }

    @Override
    public void setFill(Color fill) {
        this.fill = JfxRenderUtil.toJfx(fill);
        update();
    }

    @Override
    public Color getStroke() {
        return JfxRenderUtil.fromJfx(stroke);
    }

    protected javafx.scene.paint.Color getStrokeRaw() {
        return stroke;
    }

    @Override
    public void setStroke(Color stroke) {
        this.stroke = JfxRenderUtil.toJfx(stroke);
        update();
    }

    @Override
    public float getLineWidth() {
        return lineWidth;
    }

    @Override
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        update();
    }
}
