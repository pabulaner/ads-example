package com.out_of_box_games.gengine;

import com.out_of_box_games.gengine.core.api.assets.AssetLoader;
import com.out_of_box_games.gengine.core.api.input.InputSystem;
import com.out_of_box_games.gengine.core.api.platform.PlatformSystem;
import com.out_of_box_games.gengine.core.api.render.RenderSystem;
import com.out_of_box_games.gengine.world.World;

public class Engine {

    private static Engine instance;

    private final PlatformSystem platformSystem;

    private final AssetLoader assetLoader;

    private final InputSystem inputSystem;

    private final RenderSystem renderSystem;

    private final World world;

    private Engine(EngineConfig config) {
        platformSystem = config.getPlatformSystem();
        assetLoader = config.getAssetLoader();
        inputSystem = config.getInputSystem();
        renderSystem = config.getRenderSystem();
        world = new World();
    }

    public static Engine init(EngineConfig config) {
        if (instance != null) {
            throw new IllegalStateException("Engine is already initialized");
        }

        instance = new Engine(config);
        return instance;
    }

    public static Engine get() {
        if (instance == null) {
            throw new IllegalStateException("Engine is not initialized");
        }

        return instance;
    }

    public void update(float delta) {
        inputSystem.update(delta);
        world.update(delta);
        renderSystem.update(delta);
    }

    public PlatformSystem getPlatformSystem() {
        return platformSystem;
    }

    public AssetLoader getAssetLoader() {
        return assetLoader;
    }

    public InputSystem getInputSystem() {
        return inputSystem;
    }

    public RenderSystem getRenderSystem() {
        return renderSystem;
    }

    public World getWorld() {
        return world;
    }
}
