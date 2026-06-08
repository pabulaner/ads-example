package com.out_of_box_games.firewall.terminal;

public class ButtonLine extends Line {

    private final Runnable action;

    public ButtonLine(String text, Runnable action) {
        super(text);

        this.action = action;
    }

    public Runnable getAction() {
        return action;
    }
}
