package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.render.ShapeRenderProxy;
import com.out_of_box_games.gengine.util.math.Shape;
import com.out_of_box_games.gengine.util.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineJoin;

public class JfxShapeRenderProxy extends JfxRenderProxy implements ShapeRenderProxy {

    private Shape shape;

    private boolean cyclic;

    @Override
    public void render(GraphicsContext ctx) {
        if (shape == null) {
            return;
        }

        ctx.save();
        prepareCtx(ctx);

        Vector2[] points = shape.getPoints();
        double[] x = new double[points.length];
        double[] y = new double[points.length];

        for (int i = 0; i < points.length; i++) {
            x[i] = points[i].x;
            y[i] = points[i].y;
        }

        ctx.fillPolygon(x, y, points.length);
        ctx.setLineJoin(StrokeLineJoin.ROUND);

        if (cyclic) {
            ctx.strokePolygon(x, y, points.length);
        } else {
            ctx.strokePolyline(x, y, points.length);
        }

        ctx.restore();
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public boolean isCyclic() {
        return cyclic;
    }

    @Override
    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
    }
}
