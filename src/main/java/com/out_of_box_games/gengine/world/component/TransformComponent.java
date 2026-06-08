package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.util.collection.SafeCollection;
import com.out_of_box_games.gengine.util.math.Transform;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.Component;

import java.util.Collection;

public class TransformComponent extends Component {

    private Transform local;

    private Transform global;

    private TransformComponent parent;

    private final Collection<TransformComponent> children;

    public TransformComponent() {
        local = new Transform();
        global = null;
        parent = null;
        children = new SafeCollection<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detach();

        children.forEach(child -> {
            Actor actor = child.getActor();

            if (actor != getActor()) {
                actor.destroy();
            }
        });
    }

    public void attachTo(TransformComponent newParent) {
        if (newParent.getActor() != getActor() && !isActorRoot()) {
            throw new RuntimeException();
        }

        if (parent != null) {
            detach();
        }

        parent = newParent;
        parent.children.add(this);

        invalidate();
    }

    public void detach() {
        if (parent == null) {
            return;
        }

        parent.children.remove(this);
        parent = null;

        invalidate();
    }

    protected void invalidate() {
        global = null;

        for (TransformComponent child : children) {
            child.invalidate();
        }
    }

    public void translate(Vector2 translation) {
        setTranslation(getTranslation().add(translation));
    }

    public void rotate(float rotation) {
        setRotation(getRotation() + rotation);
    }

    public void scale(Vector2 scale) {
        setScale(getScale().mul(scale));
    }

    public Transform getTransform() {
        return local.copy();
    }

    public void setTransform(Transform transform) {
        local = transform;
        invalidate();
    }

    public Vector2 getTranslation() {
        return local.getTranslation().copy();
    }

    public void setTranslation(Vector2 translation) {
        local.setTranslation(translation);
        invalidate();
    }

    public float getRotation() {
        return local.getRotation();
    }

    public void setRotation(float rotation) {
        local.setRotation(rotation);
        invalidate();
    }

    public Vector2 getScale() {
        return local.getScale().copy();
    }

    public void setScale(Vector2 scale) {
        local.setScale(scale);
        invalidate();
    }

    public Transform getGlobalTransform() {
        if (global == null) {
            global = local.copy();

            if (parent != null) {
                parent.getGlobalTransform().applyTo(global);
            }
        }

        return global;
    }

    public Vector2 getGlobalTranslation() {
        return getGlobalTransform().getTranslation().copy();
    }

    public float getGlobalRotation() {
        return getGlobalTransform().getRotation();
    }

    public Vector2 getGlobalScale() {
        return getGlobalTransform().getScale().copy();
    }

    public boolean isActorRoot() {
        Actor actor = getActor();
        return actor != null && actor.getRoot() == this;
    }

    @SuppressWarnings("unchecked")
    public <TRoot extends TransformComponent> TRoot getRoot() {
        if (parent != null) {
            return parent.getRoot();
        }

        return (TRoot) this;
    }

    @SuppressWarnings("unchecked")
    public <TParent extends TransformComponent> TParent getParent() {
        return (TParent) parent;
    }

    public Collection<TransformComponent> getChildren() {
        return children;
    }
}
