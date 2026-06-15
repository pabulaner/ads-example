package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.assets.Font;
import com.out_of_box_games.gengine.core.api.render.TextRenderProxy;
import com.out_of_box_games.gengine.core.jfx.assets.JfxFont;
import com.out_of_box_games.gengine.util.Align;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class JfxTextRenderProxy extends JfxRenderProxy<StackPane> implements TextRenderProxy {

    private final Text front;

    private final Text back;

    private String text;

    private JfxFont font;

    private Align align;

    public JfxTextRenderProxy() {
        super(new StackPane());

        front = new Text();
        back = new Text();

        front.setTextAlignment(TextAlignment.CENTER);
        front.setTextOrigin(VPos.CENTER);
        back.setTextAlignment(TextAlignment.CENTER);
        back.setTextOrigin(VPos.CENTER);

        getNode().getChildren().addAll(back, front);
    }

    @Override
    public void update() {
        super.update();

        StackPane node = getNode();
        javafx.scene.text.Font fontOrDefault = font != null
                ? font.getFont()
                : javafx.scene.text.Font.getDefault();

        node.setTranslateX(node.getTranslateX() - 0.5 * node.getLayoutBounds().getWidth());
        node.setTranslateY(node.getTranslateY() - 0.5 * node.getLayoutBounds().getHeight());
        front.setFill(getFillRaw());
        front.setText(text);
        front.setFont(fontOrDefault);
        back.setStroke(getStrokeRaw());
        back.setStrokeWidth(getLineWidth());
        back.setText(text);
        back.setFont(fontOrDefault);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        update();
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        this.font = (JfxFont) font;
        update();
    }

    @Override
    public Align getAlign() {
        return align;
    }

    @Override
    public void setAlign(Align align) {
        this.align = align;
        update();
    }
}
