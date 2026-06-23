package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.core.api.input.InputListener;
import com.out_of_box_games.gengine.core.api.input.MouseButton;
import com.out_of_box_games.gengine.core.api.render.CameraRenderProxy;
import com.out_of_box_games.gengine.util.math.Collision;
import com.out_of_box_games.gengine.util.math.Shape;
import com.out_of_box_games.gengine.util.math.Transform;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.util.math.Vector2Int;

public class CameraComponent extends RenderComponent<CameraRenderProxy> implements InputListener {

    public CameraComponent() {
        super(CameraRenderProxy.class);

        // render camera first
        getProxy().setLayer(Integer.MIN_VALUE);
        getProxy().setPrefSize(new Vector2(1920.0f, 1080.0f));
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();
        Engine.get()
                .getInputSystem()
                .add(this);
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();
        Engine.get()
                .getInputSystem()
                .remove(this);
    }

    @Override
    public void onMousePress(MouseButton button, Vector2Int point) {
        Vector2 world = screenToWorld(point);

        getActor().getWorld()
                .getComponents(AreaComponent.class)
                .forEach(component -> {
                    Shape shape = component.getShape();
                    Transform transform = component.getGlobalTransform();

                    if (component.isClickable() && shape != null && Collision.test(shape.getPoints(transform), world)) {
                        component.onClick().invoke();
                    }
                });
    }

    public Vector2 screenToWorld(Vector2Int point) {
        Vector2 screenSize = Engine.get()
                .getRenderSystem()
                .getSize()
                .toVector2();
        Vector2 result = point.toVector2()
                .div(screenSize)
                .sub(0.5f)
                .mul(getSize());

        System.out.println(point);

        return getGlobalTransform().inverse().applyTo(result);
    }

    public Vector2 getPrefSize() {
        return getProxy().getPrefSize().copy();
    }

    public void setPrefSize(Vector2 prefSize) {
        getProxy().setPrefSize(prefSize);
    }

    public Vector2 getSize() {
        return getProxy().getSize().copy();
    }
}
