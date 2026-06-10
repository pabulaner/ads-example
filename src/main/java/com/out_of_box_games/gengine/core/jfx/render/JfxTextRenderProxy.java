package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.assets.Font;
import com.out_of_box_games.gengine.core.api.render.TextRenderProxy;
import com.out_of_box_games.gengine.core.jfx.assets.JfxFont;
import com.out_of_box_games.gengine.util.Align;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class JfxTextRenderProxy extends JfxRenderProxy<Text> implements TextRenderProxy {

    private String text;

    private JfxFont font;

    private Align align;

    public JfxTextRenderProxy() {
        super(new Text());
    }

    @Override
    public void update() {
        super.update();

        Text node = getNode();

        node.setFill(getFillRaw());
        node.setStroke(getStrokeRaw());
        node.setText(text);
        node.setFont(font != null
                ? font.getFont()
                : javafx.scene.text.Font.getDefault());
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
