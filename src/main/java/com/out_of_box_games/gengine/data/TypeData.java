package com.out_of_box_games.gengine.data;

public class TypeData<TSelf, TType> implements Data {

    private TType type;

    @SuppressWarnings("unchecked")
    protected TSelf self() {
        return (TSelf) this;
    }

    public TType getType() {
        return type;
    }

    public TSelf setType(TType type) {
        this.type = type;
        return self();
    }
}
