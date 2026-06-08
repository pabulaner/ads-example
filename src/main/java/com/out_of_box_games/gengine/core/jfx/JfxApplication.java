package com.out_of_box_games.gengine.core.jfx;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.mvc.View;
import com.out_of_box_games.firewall.DebugLabel;
import com.out_of_box_games.firewall.level.GameLevel;
import com.out_of_box_games.firewall.level.TerminalLevel;
import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.EngineConfig;
import com.out_of_box_games.gengine.core.api.assets.AssetLoader;
import com.out_of_box_games.gengine.core.api.input.InputSystem;
import com.out_of_box_games.gengine.core.api.platform.PlatformSystem;
import com.out_of_box_games.gengine.core.api.render.RenderSystem;
import com.out_of_box_games.gengine.core.jfx.assets.JfxAssetLoader;
import com.out_of_box_games.gengine.core.jfx.input.JfxInputSystem;
import com.out_of_box_games.gengine.core.jfx.platform.JfxPlatformSystem;
import com.out_of_box_games.gengine.core.jfx.render.JfxRenderSystem;
import com.out_of_box_games.gengine.util.Color;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JfxApplication extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);

    private Engine engine;

    private static StackPane root;

    @Override
    public void init() {
        appManager.addViewFactory(AppManager.HOME_VIEW, () -> {
            Canvas canvas = new Canvas();
            DebugLabel label = new DebugLabel();
            GraphicsContext ctx = canvas.getGraphicsContext2D();

            root = new StackPane(canvas);
            // root.getChildren().add(label);

            engine = Engine.init(new EngineConfig() {
                @Override
                public PlatformSystem getPlatformSystem() {
                    return new JfxPlatformSystem();
                }

                @Override
                public AssetLoader getAssetLoader() {
                    return new JfxAssetLoader();
                }

                @Override
                public InputSystem getInputSystem() {
                    return new JfxInputSystem(canvas);
                }

                @Override
                public RenderSystem getRenderSystem() {
                    RenderSystem system = new JfxRenderSystem(ctx);
                    system.setColor(Color.BLACK);

                    return system;
                }
            });

            // engine.getWorld().loadLevel(new TerminalLevel());

            canvas.widthProperty().bind(root.widthProperty());
            canvas.heightProperty().bind(root.heightProperty());

            return new View(root);
        });
    }

    private void postInit(Scene scene) {
        appManager.getAppBar().setVisible(false);
        engine.getWorld().loadLevel(new GameLevel(1));

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                System.out.println("Update + " + now);
                if (last < 0) {
                    last = now;
                    return;
                }

                float delta = (now - last) / 1_000_000_000.0f;
                last = now;

                engine.update(delta);
            }
        }.start();
    }

    @Override
    public void start(Stage primaryStage) {
        appManager.start(primaryStage);
        System.err.println("[FirewallJfxApplication]: start");

//        primaryStage.setScene(scene);
//        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
//        primaryStage.show();
    }

    public static void main(String[] args) {
        System.err.println("[FirewallJfxApplication]: main");
        launch(args);
    }

    public static StackPane getRoot() {
        return root;
    }
}
