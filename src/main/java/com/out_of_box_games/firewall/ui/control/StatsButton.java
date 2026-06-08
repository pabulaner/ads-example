package com.out_of_box_games.firewall.ui.control;

import com.out_of_box_games.gengine.util.math.Vector2;

public class StatsButton extends Button {

    private String name;

    private String value;

    public StatsButton() {
        this.name = "";
        this.value = "";


        setSize(new Vector2(150.0f, 50.0f));
        updateText();
    }

    public static StatsButton create(String name) {
        StatsButton button = new StatsButton();
        button.name = name;

        return button;
    }

    protected void updateText() {
        setText(name + "\n" + value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateText();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        updateText();
    }
}
