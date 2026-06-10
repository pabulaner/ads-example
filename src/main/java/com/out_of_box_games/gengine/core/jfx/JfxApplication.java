package com.out_of_box_games.gengine.core.jfx;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.mvc.View;
import com.out_of_box_games.firewall.DebugLabel;
import com.out_of_box_games.firewall.level.GameLevel;
import com.out_of_box_games.firewall.level.TerminalLevel;
import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.EngineConfig;
import com.out_of_box_games.gengine.core.api.assets.AssetLoader;
import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.core.api.input.InputSystem;
import com.out_of_box_games.gengine.core.api.platform.PlatformSystem;
import com.out_of_box_games.gengine.core.api.render.RenderSystem;
import com.out_of_box_games.gengine.core.jfx.assets.JfxAssetLoader;
import com.out_of_box_games.gengine.core.jfx.input.JfxInputSystem;
import com.out_of_box_games.gengine.core.jfx.platform.JfxPlatformSystem;
import com.out_of_box_games.gengine.core.jfx.render.JfxRenderSystem;
import com.out_of_box_games.gengine.util.Color;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.Level;
import com.out_of_box_games.gengine.world.component.CameraComponent;
import com.out_of_box_games.gengine.world.component.SpriteComponent;
import com.out_of_box_games.gengine.world.component.TextComponent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class JfxApplication extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);

    private Engine engine;

    private static Pane root;

    @Override
    public void init() {
        appManager.addViewFactory(AppManager.HOME_VIEW, () -> {
            Group group = new Group();
            Canvas canvas = new Canvas();
            DebugLabel label = new DebugLabel();
            GraphicsContext ctx = canvas.getGraphicsContext2D();

            root = new Pane(group);
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
                    RenderSystem system = new JfxRenderSystem(group);
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

    private Level createTestLevel() {
        return new Level() {
            @Override
            public List<Actor> getActors() {
                return List.of(new Actor() {
                    {
                        CameraComponent camera = addComponent(new CameraComponent());
                        setRoot(camera);
                    }

                    @Override
                    protected void onUpdate(float delta) {
                        super.onUpdate(delta);

                        getRoot().translate(new Vector2(0.0f, -20.0f * delta));
                    }
                }, new Actor() {
                    {
                        SpriteComponent sprite = addComponent(new SpriteComponent());
                        TextComponent text = addComponent(new TextComponent());
                        sprite.setTexture(Engine.get()
                                .getAssetLoader()
                                .load(Texture.class, "icon.png"));

                        text.setText("Hello World!");
                        text.setTranslation(new Vector2(100.0f, 100.0f));

                        setRoot(sprite);
                        text.attachTo(sprite);
                        // sprite.setScale(new Vector2(0.5f, 0.5f));
                    }

                    @Override
                    protected void onUpdate(float delta) {
                        super.onUpdate(delta);
                        getRoot().translate(new Vector2(delta * 5.0f, 0.0f));
                        getRoot().rotate(delta * 0.0f);
                    }
                }, new Actor() {
                    {
                        SpriteComponent sprite = addComponent(new SpriteComponent());
                        sprite.setTexture(Engine.get()
                                .getAssetLoader()
                                .load(Texture.class, "/icon.png"));

                        setRoot(sprite);
                        sprite.setScale(new Vector2(0.5f, 0.5f));
                    }

                    @Override
                    protected void onUpdate(float delta) {
                        super.onUpdate(delta);

                        // getRoot().translate(new Vector2(delta * 50.0f, delta * 20.0f));
                        getRoot().rotate(delta * 5.0f);
                    }
                });
            }
        };
    }

    @Override
    public void start(Stage primaryStage) {
        appManager.start(primaryStage);

//        primaryStage.setScene(scene);
//        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Pane getRoot() {
        return root;
    }
}
