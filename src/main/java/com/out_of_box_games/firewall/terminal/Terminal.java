package com.out_of_box_games.firewall.terminal;

import com.out_of_box_games.firewall.GameConfig;
import com.out_of_box_games.gengine.core.jfx.JfxApplication;
import com.out_of_box_games.gengine.core.jfx.render.JfxRenderUtil;
import com.out_of_box_games.gengine.world.actor.UI;
import com.out_of_box_games.gengine.world.component.TimerComponent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Terminal extends UI {

    private static final float INSETS = 200.0f;

    private static final float REVEAL_TIMER = 0.05f;

    private final StyleClassedTextArea area;

    private List<Line> lines;

    private String raw;

    private int index;

    private int count;

    private final Map<Integer, Integer> buttons;

    private final TimerComponent timerComponent;

    public Terminal() {
        area = new StyleClassedTextArea();
        lines = List.of();
        raw = "";
        index = 0;
        count = 0;
        buttons = new HashMap<>();
        timerComponent = addComponent(new TimerComponent());

        area.setEditable(false);
        area.setWrapText(true);
        area.setCursor(Cursor.DEFAULT);
        area.setBackground(Background.fill(JfxRenderUtil.toJfx(GameConfig.SECONDARY_COLOR)));
        area.setPadding(new Insets(INSETS));
        area.getStylesheets().add(getClass().getResource("/css/terminal.css").toExternalForm());

        timerComponent.onTimeout().addListener(ignore -> {
            typeChar();

            if (isTypingFinished()) {
                onTypingFinished();
            } else {
                restartTimer();
            }
        });

        area.setOnKeyPressed(this::onKeyPressed);
        area.setOnMouseMoved(this::onMouseMoved);
        area.addEventFilter(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
        area.addEventFilter(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);
        area.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);

        Pane root = JfxApplication.getRoot();

        area.prefWidthProperty().bind(root.widthProperty());
        area.prefHeightProperty().bind(root.heightProperty());
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        JfxApplication.getRoot().getChildren().add(area);
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();

        JfxApplication.getRoot().getChildren().remove(area);
    }

    public void show(List<Line> lines) {
        area.clear();

        this.lines = lines;
        this.raw = lines.stream()
                .map(Line::getText)
                .collect(Collectors.joining("\n"));
        this.index = 0;
        this.count = (int) lines.stream()
                .filter(ButtonLine.class::isInstance)
                .count();

        restartTimer();
    }

    private void draw() {
        int[] begin = {0};
        int[] index = {0};

        buttons.clear();

        lines.forEach(line -> {
            int end = begin[0] + line.getText().length() + 1;

            if (line instanceof TextLine) {
                area.setStyle(begin[0], end, Collections.singleton("default"));
            } else if (line instanceof ButtonLine) {
                for (int i = begin[0]; i < begin[0] + end; i++) {
                    buttons.put(i, index[0]);
                }

                area.setStyle(begin[0], end, Collections.singleton(index[0] == this.index
                    ? "highlight"
                    : "default"));

                index[0] += 1;
            }

            begin[0] = end;
        });
    }

    private void onKeyPressed(KeyEvent event) {
        if (!isTypingFinished()) {
            typeAll();
            return;
        }

        switch (event.getCode()) {
            case ENTER -> getButton(index).getAction().run();
            case UP -> {
                index = (index + count - 1) % count;
                draw();
            }
            case DOWN -> {
                index = (index + 1) % count;
                draw();
            }
        }
    }

    private void onMouseMoved(MouseEvent event) {
        if (!isTypingFinished()) {
            return;
        }

        int button = getButtonIndex(event);

        if (button >= 0) {
            index = button;
            draw();
        }
    }

    private void onMousePressed(MouseEvent event) {
        event.consume();

        if (!isTypingFinished()) {
            return;
        }

        int button = getButtonIndex(event);

        if (button >= 0) {
            index = button;
            draw();
        }
    }

    private void onMouseReleased(MouseEvent event) {
        event.consume();

        if (!isTypingFinished()) {
            typeAll();
            return;
        }

        int button = getButtonIndex(event);

        if (button >= 0) {
            getButton(button).getAction().run();
        }
    }

    private void onTypingFinished() {
        draw();
    }

    private void restartTimer() {
        timerComponent.start(REVEAL_TIMER);
    }

    private void typeChar() {
        if (isTypingFinished()) {
            onTypingFinished();
            return;
        }

        String value = String.valueOf(raw.charAt(area.getLength()));
        area.append(value, "default");
    }

    private void typeAll() {
        while (!isTypingFinished()) {
            typeChar();
        }
    }

    private boolean isTypingFinished() {
        return raw.length() <= area.getLength();
    }

    private ButtonLine getButton(int index) {
        return lines.stream()
                .filter(ButtonLine.class::isInstance)
                .map(ButtonLine.class::cast)
                .skip(index)
                .findFirst()
                .orElse(null);
    }

    private int getButtonIndex(MouseEvent event) {
        int index = area.hit(event.getX(), event.getY()).getInsertionIndex();
        Integer result = buttons.get(index);

        return result != null
                ? result
                : -1;
    }
}
