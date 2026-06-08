package com.out_of_box_games.gengine.core.jfx.render;

import com.out_of_box_games.gengine.core.api.assets.Font;
import com.out_of_box_games.gengine.core.api.render.TextRenderProxy;
import com.out_of_box_games.gengine.core.jfx.assets.JfxFont;
import com.out_of_box_games.gengine.util.Align;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

public class JfxTextRenderProxy extends JfxRenderProxy implements TextRenderProxy {

    private String text;

    private JfxFont font;

    private Align align;

    @Override
    public void render(GraphicsContext ctx) {
        if (text == null || font == null) {
            return;
        }

        ctx.save();
        prepareCtx(ctx);

//        TextAlignment textAlign = switch (align.getValue().x) {
//            case -1 -> TextAlignment.LEFT;
//            case 0 -> TextAlignment.CENTER;
//            case 1 -> TextAlignment.RIGHT;
//            default -> throw new IllegalStateException("Unexpected value: " + align.getValue().x);
//        };
//
//        VPos textBaseline = switch (align.getValue().y) {
//            case -1 -> VPos.TOP;
//            case 0 -> VPos.CENTER;
//            case 1 -> VPos.BOTTOM;
//            default -> throw new IllegalStateException("Unexpected value: " + align.getValue().x);
//        };
//
//        ctx.setFont(font.getFont());
//        ctx.setTextAlign(textAlign);
//        ctx.setTextBaseline(textBaseline);
//        ctx.strokeText(text, 0.0, 0.0);
//        ctx.fillText(text, 0.0, 0.0);

        ctx.restore();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        this.font = (JfxFont) font;
    }

    @Override
    public Align getAlign() {
        return align;
    }

    @Override
    public void setAlign(Align align) {
        this.align = align;
    }
}
