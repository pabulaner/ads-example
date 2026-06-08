package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.core.api.assets.Font;
import com.out_of_box_games.gengine.core.api.render.TextRenderProxy;
import com.out_of_box_games.gengine.util.Align;

public class TextComponent extends RenderComponent<TextRenderProxy> {

    public TextComponent() {
        super(TextRenderProxy.class);
        TextRenderProxy proxy = getProxy();

        proxy.setText(null);
        proxy.setFont(null);
        proxy.setAlign(Align.CENTER);
    }

    public String getText() {
        return getProxy().getText();
    }

    public void setText(String text) {
        getProxy().setText(text);
    }

    public Font getFont() {
        return getProxy().getFont();
    }

    public void setFont(Font font) {
        getProxy().setFont(font);
    }

    public Align getAlign() {
        return getProxy().getAlign();
    }

    public void setAlign(Align align) {
        getProxy().setAlign(align);
    }
}
