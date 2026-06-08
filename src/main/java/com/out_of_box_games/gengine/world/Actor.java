package com.out_of_box_games.gengine.world;

import com.out_of_box_games.gengine.util.collection.SafeCollection;
import com.out_of_box_games.gengine.world.component.TransformComponent;

import java.util.Collection;

public class Actor {

    private World world;

    private boolean destroyed;

    private TransformComponent root;

    private final Collection<Component> components;

    public Actor() {
        world = null;
        destroyed = false;
        root = null;
        components = new SafeCollection<>();
    }

    public void destroy() {
        if (destroyed) {
            return;
        }

        if (world != null) {
            world.removeActor(this);
        } else {
            onDestroy();
            components.forEach(Component::destroy);

            destroyed = true;
        }
    }

    protected void onDestroy() {
        // empty
    }

    void addToWorld(World world) {
        this.world = world;

        components.forEach(Component::onAddToWorld);
        onAddToWorld();
    }

    protected void onAddToWorld() {
        // empty
    }

    void beginPlay() {
        components.forEach(Component::onBeginPlay);
        onBeginPlay();
    }

    protected void onBeginPlay() {
        // empty
    }

    void removeFromWorld() {
        onRemoveFromWorld();
        components.forEach(Component::onRemoveFromWorld);

        world = null;
    }

    protected void onRemoveFromWorld() {
        // empty
    }

    void update(float delta) {
        components.forEach(component -> component.onUpdate(delta));
        onUpdate(delta);
    }

    protected void onUpdate(float delta) {
        // empty
    }

    public <TComponent extends Component> TComponent addComponent(TComponent component) {
        if (component.getActor() != null) {
            component.getActor().removeComponent(component, false);
        }

        components.add(component);
        component.addToActor(this);

        return component;
    }

    public <TComponent extends Component> TComponent removeComponent(TComponent component) {
        return removeComponent(component, true);
    }

    public <TComponent extends Component> TComponent removeComponent(TComponent component, boolean destroy) {
        if (component.getActor() != this) {
            return component;
        }

        component.removeFromActor();
        components.remove(component);

        if (destroy) {
            component.destroy();
        }

        return component;
    }

    public World getWorld() {
        return world;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @SuppressWarnings("unchecked")
    public <TRoot extends TransformComponent> TRoot getRoot() {
        return (TRoot) root;
    }

    public void setRoot(TransformComponent root) {
        if (root.getActor() != this) {
            throw new RuntimeException();
        }

        this.root = root;
    }

    public <TComponent extends Component> TComponent getComponent(Class<TComponent> componentClass) {
        return getComponents(componentClass).stream()
                .findFirst()
                .orElse(null);
    }

    public <TComponent extends Component> Collection<TComponent> getComponents(Class<TComponent> componentClass) {
        return components.stream()
                .filter(componentClass::isInstance)
                .map(componentClass::cast)
                .toList();
    }
}
