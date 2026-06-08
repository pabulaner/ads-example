package com.out_of_box_games.gengine.data;

import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.World;

import java.util.List;

public interface Persist<TData extends Data> {

    static <TActor extends Actor & Persist<TData>, TData extends Data> TActor create(TActor actor, TData data) {
        return create(actor, data, (Actor) null);
    }

    static <TActor extends Actor & Persist<TData>, TData extends Data> TActor create(TActor actor, TData data, Actor parent) {
        if (parent != null) {
            if (parent.getWorld() != null) {
                parent.getWorld().addActor(actor);
            }

            actor.getRoot().attachTo(parent.getRoot());
        }

        actor.load(data);
        return actor;
    }

    static <TActor extends Actor & Persist<TData>, TData extends Data> TActor create(TActor actor, TData data, World world) {
        world.addActor(actor);
        actor.load(data);
        
        return actor;
    }

    static <TActor extends Actor & Persist<TData>, TData extends Data> List<TActor> load(TActor actor, List<TData> data) {
        return load(actor, data, null);
    }

    static <TActor extends Actor & Persist<TData>, TData extends Data> List<TActor> load(TActor actor, List<TData> data, Actor parent) {
        return data.stream()
                .map(value -> create(actor, value, parent))
                .toList();
    }

    static <TActor extends Actor & Persist<TData>, TData extends Data> List<TData> save(List<TActor> actors) {
        return actors.stream()
                .map(Persist::save)
                .toList();
    }

    void load(TData data);

    TData save();
}
