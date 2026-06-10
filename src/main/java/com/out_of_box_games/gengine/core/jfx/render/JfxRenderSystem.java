package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.render.CameraRenderProxy;
import com.out_of_box_games.gengine.core.api.render.RenderProxy;
import com.out_of_box_games.gengine.core.api.render.RenderSystem;
import com.out_of_box_games.gengine.core.api.render.ShapeRenderProxy;
import com.out_of_box_games.gengine.core.api.render.TextRenderProxy;
import com.out_of_box_games.gengine.core.api.render.TextureRenderProxy;
import com.out_of_box_games.gengine.util.ClassFactory;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JfxRenderSystem implements RenderSystem {

    private final Group group;

    private final ClassFactory<Void, RenderProxy> factories;

    private final List<JfxRenderProxy<?>> proxies;

    private javafx.scene.paint.Color color;

    private final Event<Vector2Int> onResize;

    public JfxRenderSystem(Group group) {
        this.group = group;
        this.factories = new ClassFactory<>();
        this.proxies = new ArrayList<>();
        this.color = javafx.scene.paint.Color.WHITE;
        this.onResize = new Event<>();

        factories.addFactory(CameraRenderProxy.class, () -> new JfxCameraRenderProxy(this));
        factories.addFactory(TextureRenderProxy.class, JfxTextureRenderProxy::new);
        factories.addFactory(ShapeRenderProxy.class, JfxShapeRenderProxy::new);
        factories.addFactory(TextRenderProxy.class, JfxTextRenderProxy::new);

//        group.widthProperty().addListener(ignored -> onResize.invoke(getSize()));
//        group.heightProperty().addListener(ignored -> onResize.invoke(getSize()));
    }

    @Override
    public void update(float delta) {
        proxies.sort(Comparator.comparingInt(JfxRenderProxy::getLayer));
        proxies.stream()
                .filter(JfxRenderProxy::isVisible)
                .forEach(JfxRenderProxy::update);
    }

    @Override
    public <TSubComponent extends RenderProxy> TSubComponent create(Class<? extends TSubComponent> componentClass) {
        return factories.create(componentClass);
    }

    @Override
    public void add(RenderProxy proxy) {
        JfxRenderProxy<?> jfxProxy = (JfxRenderProxy<?>) proxy;

        proxies.add(jfxProxy);
        group.getChildren().add(jfxProxy.getNode());
    }

    @Override
    public void remove(RenderProxy proxy) {
        JfxRenderProxy<?> jfxProxy = (JfxRenderProxy<?>) proxy;

        group.getChildren().remove(jfxProxy.getNode());
        proxies.remove(jfxProxy);
    }

    @Override
    public Vector2Int getSize() {
        Window window = group.getScene().getWindow();

        return new Vector2Int(
                (int) window.getWidth(),
                (int) window.getHeight());
    }

    @Override
    public Color getColor() {
        return JfxRenderUtil.fromJfx(color);
    }

    @Override
    public void setColor(Color color) {
        this.color = JfxRenderUtil.toJfx(color);

        Pane pane = (Pane) group.getParent();
        pane.setBackground(Background.fill(this.color));
    }

    @Override
    public Event<Vector2Int> onResize() {
        return onResize;
    }
}
