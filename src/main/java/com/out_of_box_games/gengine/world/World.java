package com.out_of_box_games.gengine.world;

import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.core.api.render.RenderSystem;
import com.out_of_box_games.gengine.util.collection.SafeCollection;
import com.out_of_box_games.gengine.world.actor.GameMode;
import com.out_of_box_games.gengine.world.actor.GameState;
import com.out_of_box_games.gengine.world.actor.PlayerState;
import com.out_of_box_games.gengine.world.actor.UI;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class World {

    private GameMode gameMode;

    private GameState gameState;

    private PlayerState playerState;

    private UI ui;

    private final Collection<Actor> actors;

    private final Collection<Actor> destroyed;

    private boolean pause;

    private float timeDilation;

    public World() {
        gameMode = null;
        gameState = null;
        ui = null;
        actors = new SafeCollection<>();
        destroyed = new SafeCollection<>();
        pause = false;
        timeDilation = 1.0f;
    }

    public void loadLevel(Level level) {
        loadLevel(level, true);
    }

    public void loadLevel(Level level, boolean replace) {
        if (replace) {
            pause = false;
            timeDilation = 1.0f;

            if (gameMode != null) {
                gameMode.endPlay();
            }

            while (!actors.isEmpty()) {
                actors.forEach(this::removeActor);
                cleanActors();
            }

            gameMode = addActor(level.getGameMode());
            gameState = addActor(level.getGameState());
            playerState = addActor(level.getPlayerState());
            ui = addActor(level.getUI());

            gameMode.beginPlay();
        }

        level.getActors().forEach(this::addActor);

        // TODO: remove this as it fixes only a bug
        RenderSystem render = Engine.get().getRenderSystem();
        render.onResize().invoke(render.getSize());
    }

    public void update(float delta) {
        if (pause) {
            return;
        }

        actors.forEach(actor -> actor.update(delta * timeDilation));
        cleanActors();
    }

    public <TActor extends Actor> TActor addActor(TActor actor) {
        if (actor.getWorld() != null) {
            actor.getWorld().removeActor(actor, false);
        }

        actors.add(actor);
        actor.addToWorld(this);

        return actor;
    }

    public <TActor extends Actor> Collection<TActor> addActors(Collection<TActor> actors) {
        actors.forEach(this::addActor);
        return actors;
    }

    public <TActor extends Actor> TActor removeActor(TActor actor) {
        return removeActor(actor, true);
    }

    public <TActor extends Actor> TActor removeActor(TActor actor, boolean destroy) {
        if (actor.getWorld() != this) {
            return actor;
        }

        if (destroy) {
            if (!destroyed.contains(actor)) {
                destroyed.add(actor);
            }
        } else {
            actor.removeFromWorld();
            actors.remove(actor);
        }

        return actor;
    }

    public <TActor extends Actor> Collection<TActor> removeActors(Collection<TActor> actors) {
        return removeActors(actors, true);
    }

    public <TActor extends Actor> Collection<TActor> removeActors(Collection<TActor> actors, boolean destroy) {
        actors.forEach(actor -> removeActor(actor, destroy));
        return actors;
    }

    private void cleanActors() {
        for (Iterator<Actor> it = destroyed.iterator(); it.hasNext(); ) {
            Actor actor = it.next();

            actor.removeFromWorld();
            actors.remove(actor);

            actor.destroy();
            it.remove();
        }
    }

    @SuppressWarnings("unchecked")
    public <TGameMode extends GameMode> TGameMode getGameMode() {
        return (TGameMode) gameMode;
    }

    @SuppressWarnings("unchecked")
    public <TGameState extends GameState> TGameState getGameState() {
        return (TGameState) gameState;
    }

    @SuppressWarnings("unchecked")
    public <TPlayerState extends PlayerState> TPlayerState getPlayerState() {
        return (TPlayerState) playerState;
    }

    @SuppressWarnings("unchecked")
    public <TUI extends UI> TUI getUI() {
        return (TUI) ui;
    }

    public <TActor extends Actor> TActor getActor(Class<TActor> actorClass) {
        return getActors(actorClass).stream()
                .findFirst()
                .orElse(null);
    }

    public <TActor extends Actor> List<TActor> getActors(Class<TActor> actorClass) {
        return actors.stream()
                .filter(actorClass::isInstance)
                .map(actorClass::cast)
                .toList();
    }

    public <TComponent extends Component> List<TComponent> getComponents(Class<TComponent> componentClass) {
        return actors.stream()
                .flatMap(actor -> actor.getComponents(componentClass).stream())
                .toList();
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public float getTimeDilation() {
        return timeDilation;
    }

    public void setTimeDilation(float timeDilation) {
        this.timeDilation = timeDilation;
    }
}
