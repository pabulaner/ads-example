package com.out_of_box_games.gengine.core.api.render;

import com.out_of_box_games.gengine.EngineSystem;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.util.math.Vector2Int;

public interface RenderSystem extends EngineSystem<RenderProxy> {

    Vector2Int getSize();

    Color getColor();

    void setColor(Color color);

    Event<Vector2Int> onResize();
}
