package com.out_of_box_games.firewall.ui.control;

import com.out_of_box_games.gengine.util.Event;

import java.util.List;
import java.util.function.Function;

public class StatsToggleButton<TValue extends Enum<?>> extends StatsButton {

    private int index;

    private final List<TValue> values;

    private final Function<TValue, String> converter;

    private final Event<Void> onSelectionChange;

    @SafeVarargs
    public StatsToggleButton(Function<TValue, String> converter, TValue... values) {
        this.index = 0;
        this.values = List.of(values);
        this.converter = converter;
        this.onSelectionChange = new Event<>();

        setOnClick(() -> {
            index = (index + 1) % values.length;

            updateText();
            onSelectionChange.invoke();
        });

        updateText();
    }

    @Override
    protected void updateText() {
        if (values != null) {
            setText(getName() + "\n< " + converter.apply(values.get(index)) + " >");
        }
    }

    public TValue getSelection() {
        return values.get(index);
    }

    public List<TValue> getValues() {
        return values;
    }

    public Function<TValue, String> getConverter() {
        return converter;
    }

    public Event<Void> onSelectionChange() {
        return onSelectionChange;
    }
}
