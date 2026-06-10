package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.render.ShapeRenderProxy;
import com.out_of_box_games.gengine.util.math.Shape;
import com.out_of_box_games.gengine.util.math.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.util.List;

public class JfxShapeRenderProxy extends JfxRenderProxy<Polyline> implements ShapeRenderProxy {

    private Shape shape;

    private boolean cyclic;

    public JfxShapeRenderProxy() {
        super(new Polyline());
    }

    @Override
    public void update() {
        super.update();

        Polyline node = getNode();

        node.setStrokeLineCap(StrokeLineCap.ROUND);
        node.setStrokeLineJoin(StrokeLineJoin.ROUND);
        node.setFill(getFillRaw());
        node.setStroke(getStrokeRaw());
        node.setStrokeWidth(getLineWidth());

        List<Double> points = getNode().getPoints();
        points.clear();

        if (shape != null && shape.getPoints().length > 0) {
            for (Vector2 point : shape.getPoints()) {
                points.add((double) point.x);
                points.add((double) point.y);
            }

            if (cyclic) {
                Vector2 point = shape.getPoints()[0];

                points.add((double) point.x);
                points.add((double) point.y);
            }
        }
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public void setShape(Shape shape) {
        this.shape = shape;
        update();
    }

    @Override
    public boolean isCyclic() {
        return cyclic;
    }

    @Override
    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
        update();
    }
}
