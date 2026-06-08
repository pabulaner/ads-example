package com.out_of_box_games.gengine.world;

public class Component {

    private Actor actor;

    public Component() {
        actor = null;
    }

    public final void destroy() {
        if (actor != null) {
            actor.removeComponent(this);
        } else {
            onDestroy();
        }
    }

    protected void onDestroy() {
        // empty
    }

    void addToActor(Actor actor) {
        this.actor = actor;
        onAddToActor();

        if (actor.getWorld() != null) {
            onAddToWorld();
        }
    }

    protected void onAddToActor() {
        // empty
    }

    void removeFromActor() {
        if (actor.getWorld() != null) {
            onRemoveFromWorld();
        }

        onRemoveFromActor();
        actor = null;
    }

    protected void onRemoveFromActor() {
        // empty
    }

    protected void onAddToWorld() {
        // empty
    }

    protected void onRemoveFromWorld() {
        // empty
    }

    protected void onBeginPlay() {
        // empty0
    }

    protected void onUpdate(float delta) {
        // empty
    }

    @SuppressWarnings("unchecked")
    public <TActor extends Actor> TActor getActor() {
        return (TActor) actor;
    }
}
