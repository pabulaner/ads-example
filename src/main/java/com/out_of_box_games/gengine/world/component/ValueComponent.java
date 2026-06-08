package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.world.Component;

public abstract class ValueComponent<TValue extends Number & Comparable<TValue>> extends Component {

    private TValue min;

    private TValue max;

    private TValue value;

    private final Event<TValue> onValueChange;

    private final Event<Void> onValueMin;

    private final Event<Void> onValueMax;

    public ValueComponent(TValue zero) {
        min = zero;
        max = zero;
        value = zero;
        onValueChange = new Event<>();
        onValueMin = new Event<>();
        onValueMax = new Event<>();
    }

    private TValue clamp(TValue value) {
        if (min.compareTo(value) > 0) return min;
        if (max.compareTo(value) < 0) return max;

        return value;
    }

    public TValue getMin() {
        return min;
    }

    public void setMin(TValue min) {
        this.min = min;

        if (min.compareTo(value) > 0) {
            setValue(min);
        }
    }

    public TValue getMax() {
        return max;
    }

    public void setMax(TValue max) {
        this.max = max;

        if (max.compareTo(value) < 0) {
            setValue(max);
        }
    }

    public TValue getValue() {
        return value;
    }

    public void setValue(TValue value) {
        this.value = clamp(value);
        onValueChange.invoke(this.value);

        if (this.value.compareTo(min) == 0) onValueMin.invoke();
        if (this.value.compareTo(max) == 0) onValueMax.invoke();
    }

    public Event<TValue> onValueChange() {
        return onValueChange;
    }

    public Event<Void> onValueMin() {
        return onValueMin;
    }

    public Event<Void> onValueMax() {
        return onValueMax;
    }
}
