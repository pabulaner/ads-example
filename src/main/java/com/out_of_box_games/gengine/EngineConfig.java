package com.out_of_box_games.gengine;

import com.out_of_box_games.gengine.core.api.assets.AssetLoader;
import com.out_of_box_games.gengine.core.api.input.InputSystem;
import com.out_of_box_games.gengine.core.api.platform.PlatformSystem;
import com.out_of_box_games.gengine.core.api.render.RenderSystem;

public interface EngineConfig {

    PlatformSystem getPlatformSystem();

    AssetLoader getAssetLoader();

    InputSystem getInputSystem();

    RenderSystem getRenderSystem();
}
