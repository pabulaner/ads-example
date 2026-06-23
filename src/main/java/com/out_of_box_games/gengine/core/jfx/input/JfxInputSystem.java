package com.out_of_box_games.gengine.core.jfx.input;

import com.out_of_box_games.gengine.core.api.input.InputListener;
import com.out_of_box_games.gengine.core.api.input.InputSystem;
import com.out_of_box_games.gengine.core.api.input.MouseButton;
import com.out_of_box_games.gengine.util.collection.SafeCollection;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.Collection;
import java.util.List;

public class JfxInputSystem implements InputSystem {

    private final Collection<InputListener> listeners;

    public JfxInputSystem(Node node) {
        listeners = new SafeCollection<>();

        node.setOnMousePressed(event -> List.copyOf(listeners).forEach(listener ->
                listener.onMousePress(convertMouseButton(event), convertPoint(event))));

        node.setOnMouseReleased(event -> List.copyOf(listeners).forEach(listener ->
                listener.onMouseRelease(convertMouseButton(event), convertPoint(event))));

        node.setOnMouseMoved(event -> List.copyOf(listeners).forEach(listener ->
                listener.onMouseMove(convertPoint(event))));
    }

    @Override
    public void update(float delta) {
        // TODO: forward inputs to the actors and components in this loop
    }

    @Override
    public <TSubComponent extends InputListener> TSubComponent create(Class<? extends TSubComponent> componentClass) {
        return null;
    }

    @Override
    public void add(InputListener listener) {
        listeners.add(listener);
    }

    @Override
    public void remove(InputListener listener) {
        listeners.remove(listener);
    }

    private Vector2Int convertPoint(MouseEvent event) {
        return new Vector2Int(
                (int) event.getSceneX(),
                (int) event.getSceneY());
    }

    private MouseButton convertMouseButton(MouseEvent event) {
        return switch (event.getButton()) {
            case NONE, BACK, FORWARD -> null;
            case PRIMARY -> MouseButton.LEFT;
            case MIDDLE -> MouseButton.WHEEL;
            case SECONDARY -> MouseButton.RIGHT;
        };
    }
}
