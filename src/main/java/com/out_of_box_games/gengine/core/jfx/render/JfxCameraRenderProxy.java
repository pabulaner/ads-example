package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.render.CameraRenderProxy;
import com.out_of_box_games.gengine.util.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class JfxCameraRenderProxy extends JfxRenderProxy implements CameraRenderProxy {

    private final JfxRenderSystem system;

    private Vector2 prefSize;

    public JfxCameraRenderProxy(JfxRenderSystem system) {
        this.system = system;
    }

    @Override
    public void render(GraphicsContext ctx) {
        Vector2 screen = system.getSize().toVector2();

        getTransform().setScale(screen.div(getSize()));
        prepareCtx(ctx);
    }

    @Override
    public Vector2 getPrefSize() {
        return prefSize;
    }

    @Override
    public void setPrefSize(Vector2 prefSize) {
        this.prefSize = prefSize;
    }

    @Override
    public Vector2 getSize() {
        Vector2 screen = system.getSize().toVector2();
        float screenRatio = screen.x / screen.y;
        float prefRatio = prefSize.x / prefSize.y;

        return prefRatio < screenRatio
                ? new Vector2(screenRatio * prefSize.y, prefSize.y)
                : new Vector2(prefSize.x, prefSize.x / screenRatio);
    }
}
