package com.out_of_box_games.firewall;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.OutputStream;
import java.io.PrintStream;

public class DebugLabel extends TextArea {

    private static final double WIDTH = 256.0f;

    private static final double HEIGHT = 256.0f;

    public DebugLabel() {
        setMaxWidth(WIDTH);
        setMaxHeight(HEIGHT);

        System.setOut(create(System.out));
        System.setErr(create(System.err));
    }

    private PrintStream create(PrintStream old) {
        return new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                setText(getText() + (char) b);

                if (getText().chars().filter(c -> c == '\n').count() > 8) {
                    setText("");
                }

                Platform.runLater(() -> toFront());
                old.write(b);
            }
        });
    }
}
