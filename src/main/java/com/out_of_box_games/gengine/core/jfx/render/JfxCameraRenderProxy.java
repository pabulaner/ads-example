package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.render.CameraRenderProxy;
import com.out_of_box_games.gengine.util.math.Transform;
import com.out_of_box_games.gengine.util.math.Vector2;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

public class JfxCameraRenderProxy extends JfxRenderProxy<Node> implements CameraRenderProxy {

    private final JfxRenderSystem system;

    private Vector2 prefSize;

    public JfxCameraRenderProxy(JfxRenderSystem system) {
        super(new Group());

        this.system = system;
        getNode().parentProperty().addListener((observable, oldValue, newValue) -> update());
    }

    @Override
    public void update() {
        Node node = getNode().getParent();
        Transform transform = getTransform();

        if (node == null || transform == null) {
            return;
        }

        Vector2 translation = transform.getTranslation();
        Vector2 scale = system.getSize()
                .toVector2()
                .div(getSize());

        node.setTranslateX(-translation.x);
        node.setTranslateY(-translation.y);
        node.setRotate(-transform.getRotation());
        node.setScaleX(scale.x);
        node.setScaleY(scale.y);
    }

    @Override
    public Vector2 getPrefSize() {
        return prefSize;
    }

    @Override
    public void setPrefSize(Vector2 prefSize) {
        this.prefSize = prefSize;
        update();
    }

    @Override
    public Vector2 getSize() {
        Vector2 screen = system.getSize().toVector2();

        if (screen.equals(Vector2.zero())) {
            screen = Vector2.one();
        }

        float screenRatio = screen.x / screen.y;
        float prefRatio = prefSize.x / prefSize.y;

        return prefRatio < screenRatio
                ? new Vector2(screenRatio * prefSize.y, prefSize.y)
                : new Vector2(prefSize.x, prefSize.x / screenRatio);
    }
}
