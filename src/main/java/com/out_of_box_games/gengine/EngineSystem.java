package com.out_of_box_games.gengine;

public interface EngineSystem<TComponent> {

    void update(float delta);

    <TSubComponent extends TComponent> TSubComponent create(Class<? extends TSubComponent> componentClass);

    void add(TComponent component);

    void remove(TComponent component);
}
