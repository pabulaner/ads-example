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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JfxRenderSystem implements RenderSystem {

    private final GraphicsContext ctx;

    private final ClassFactory<Void, RenderProxy> factories;

    private final List<JfxRenderProxy> proxies;

    private javafx.scene.paint.Color color;

    private final Event<Vector2Int> onResize;

    public JfxRenderSystem(GraphicsContext ctx) {
        this.ctx = ctx;
        this.factories = new ClassFactory<>();
        this.proxies = new ArrayList<>();
        this.color = javafx.scene.paint.Color.WHITE;
        this.onResize = new Event<>();

        factories.addFactory(CameraRenderProxy.class, () -> new JfxCameraRenderProxy(this));
        factories.addFactory(TextureRenderProxy.class, JfxTextureRenderProxy::new);
        factories.addFactory(ShapeRenderProxy.class, JfxShapeRenderProxy::new);
        factories.addFactory(TextRenderProxy.class, JfxTextRenderProxy::new);

        ctx.getCanvas().widthProperty().addListener(ignored -> onResize.invoke(getSize()));
        ctx.getCanvas().heightProperty().addListener(ignored -> onResize.invoke(getSize()));
    }

    @Override
    public void update(float delta) {
        Vector2Int size = getSize();
        ctx.setFill(color);
        ctx.fillRect(0, 0, size.x, size.y);
        ctx.save();

        proxies.sort(Comparator.comparingInt(JfxRenderProxy::getLayer));
        proxies.stream()
                .filter(JfxRenderProxy::isVisible)
                .forEach(proxy -> proxy.render(ctx));

        ctx.restore();
    }

    @Override
    public <TSubComponent extends RenderProxy> TSubComponent create(Class<? extends TSubComponent> componentClass) {
        return factories.create(componentClass);
    }

    @Override
    public void add(RenderProxy proxy) {
        proxies.add((JfxRenderProxy) proxy);
    }

    @Override
    public void remove(RenderProxy proxy) {
        proxies.remove((JfxRenderProxy) proxy);
    }

    @Override
    public Vector2Int getSize() {
        Canvas canvas = ctx.getCanvas();
        return new Vector2Int(
                (int) canvas.getWidth(),
                (int) canvas.getHeight());
    }

    @Override
    public Color getColor() {
        return JfxRenderUtil.fromJfx(color);
    }

    @Override
    public void setColor(Color color) {
        this.color = JfxRenderUtil.toJfx(color);
    }

    @Override
    public Event<Vector2Int> onResize() {
        return onResize;
    }
}
