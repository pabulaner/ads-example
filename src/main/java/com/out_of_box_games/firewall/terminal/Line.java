package com.out_of_box_games.firewall.terminal;

public abstract class Line {

    private final String text;

    public Line(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
