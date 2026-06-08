package com.out_of_box_games.gengine.world.actor;

import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.data.Registry;
import com.out_of_box_games.gengine.data.TypeData;
import com.out_of_box_games.gengine.world.Actor;

public abstract class DataActor<TType, TData extends TypeData<TData, TType>, TStaticData extends TypeData<TStaticData, TType>> extends Actor implements Persist<TData> {

    private final Registry<TType, TStaticData> registry;

    private TData data;

    public DataActor(Registry<TType, TStaticData> registry) {
        this.registry = registry;
        this.data = null;
    }

    @Override
    public void load(TData data) {
        this.data = data;
    }

    @Override
    public TData save() {
        return data;
    }

    public TData getData() {
        return data;
    }

    public void setData(TData data) {
        this.data = data;
    }

    public TStaticData getStaticData() {
        return data != null
                ? registry.get(data.getType())
                : null;
    }
}
