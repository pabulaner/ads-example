package com.out_of_box_games.gengine.core.api.render;

import com.out_of_box_games.gengine.core.api.assets.Font;
import com.out_of_box_games.gengine.util.Align;

public interface TextRenderProxy extends RenderProxy {

    String getText();

    void setText(String text);

    Font getFont();

    void setFont(Font font);

    Align getAlign();

    void setAlign(Align align);
}
