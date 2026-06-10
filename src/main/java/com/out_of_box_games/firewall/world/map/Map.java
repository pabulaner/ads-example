package com.out_of_box_games.firewall.world.map;

import com.out_of_box_games.firewall.data.MapRegistry;
import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.firewall.data.enemy.EnemyData;
import com.out_of_box_games.firewall.data.map.MapData;
import com.out_of_box_games.firewall.data.map.MapStaticData;
import com.out_of_box_games.firewall.data.tower.TowerData;
import com.out_of_box_games.firewall.world.enemy.Enemy;
import com.out_of_box_games.firewall.world.projectile.Projectile;
import com.out_of_box_games.firewall.world.tower.Tower;
import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.data.Persist;
import com.out_of_box_games.gengine.util.collection.Pair;
import com.out_of_box_games.gengine.util.math.RandomUtil;
import com.out_of_box_games.gengine.util.math.Vector2;
import com.out_of_box_games.gengine.util.math.Vector2Int;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.World;
import com.out_of_box_games.gengine.world.component.CameraComponent;
import com.out_of_box_games.gengine.world.component.TransformComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Map extends Actor implements Persist<MapData> {

    public static final Vector2 TILE_SIZE = new Vector2(100.0f);

    public static final Vector2 NODE_SIZE = TILE_SIZE.copy().mul(0.75f);

    public static final float LINE_WIDTH = 5.0f;

    private int type;

    private Vector2Int size;

    private final List<MapPathNode> nodes;

    private final List<MapPath> paths;

    private final List<MapPlatform> platforms;

    private final List<Projectile> projectiles;

    private final Consumer<Vector2Int> onResize;

    public Map() {
        type = 0;
        size = Vector2Int.zero();
        nodes = new ArrayList<>();
        paths = new ArrayList<>();
        platforms = new ArrayList<>();
        projectiles = new ArrayList<>();
        onResize = this::onResize;

        setRoot(addComponent(new TransformComponent()));
    }

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();
        Engine.get()
                .getRenderSystem()
                .onResize()
                .addListener(onResize);
    }

    @Override
    protected void onRemoveFromWorld() {
        super.onRemoveFromWorld();
        Engine.get()
                .getRenderSystem()
                .onResize()
                .removeListener(onResize);
    }

    private void onResize(Vector2Int ignored) {
        getRoot().setTranslation(getWorld().getComponents(CameraComponent.class)
                .get(0)
                .getSize()
                .mul(0.5f)
                .sub(size.toVector2()
                        .mul(TILE_SIZE)
                        .mul(0.5f)));

        System.out.println(getRoot().getTranslation());
    }

    @Override
    public void load(MapData data) {
        World world = getWorld();
        MapRegistry registry = MapRegistry.getInstance();
        MapStaticData staticData = registry.get(data.getType());

        world.removeActors(nodes).clear();
        world.removeActors(paths).clear();
        world.removeActors(platforms).clear();
        world.removeActors(projectiles).clear();

        int[] beginIndex = { 0 };
        int[] intersectionIndex = { 0 };
        int[] endIndex = { 0 };

        type = data.getType();
        size = staticData.getSize();
        nodes.addAll(createNodes(() -> new MapBeginNode(beginIndex[0]++), staticData.getBegins()));
        nodes.addAll(createNodes(() -> new MapIntersectionNode(intersectionIndex[0]++), staticData.getIntersections()));
        nodes.addAll(createNodes(() -> new MapEndNode(endIndex[0]++), staticData.getEnds()));
        paths.addAll(staticData.getPaths().stream()
                .map(MapPath::createBidirectional)
                .flatMap(Pair::stream)
                .peek(path -> {
                    getWorld().addActor(path);
                    path.getRoot().attachTo(getRoot());
                })
                .toList());
        platforms.addAll(createNodes(MapPlatform::new, staticData.getPlatforms()));

        List<TowerData> towers = data.getTowers();
        List<List<EnemyData>> enemies = data.getEnemies();

        if (towers != null) {
            for (int i = 0; i < towers.size(); i++) {
                TowerData tower = towers.get(i);

                if (tower != null)  {
                    platforms.get(i).setTower(Persist.create(new Tower(), tower, getWorld()));
                }
            }
        } else {
            MapPlatform platform = RandomUtil.getRandom(platforms);
            Tower tower = Persist.create(new Tower(), new TowerData()
                    .setType(TowerType.CPU)
                    .setLevel(0), getWorld());

            platform.setTower(tower);
        }

        if (enemies != null) {
            for (int i = 0; i < enemies.size(); i++) {
                for (EnemyData enemy : enemies.get(i)) {
                    Persist.create(new Enemy(), enemy, getWorld())
                            .getRoot()
                            .attachTo(paths.get(i).getPathComponent());
                }
            }
        }

        MapConnector.createConnections(nodes, getNodes(MapBeginNode.class), getNodes(MapEndNode.class), paths);
    }

    @Override
    public MapData save() {
//        List<Projectile> projectiles = getWorld().getActors(Projectile.class);
        List<TowerData> towers = platforms.stream()
                .map(MapPlatform::getTower)
                .map(tower -> tower != null ? tower.save() : null)
                .toList();
//        List<List<EnemyData>> enemies = paths.stream()
//                .map(path -> path.getActors()
//                        .stream()
//                        .map(Enemy.class::cast)
//                        .map(Enemy::save)
//                        .toList())
//                .toList();

        return new MapData()
                .setType(type)
                .setTowers(towers)
                .setEnemies(List.of())
                .setProjectiles(List.of());
    }

    private <TMapNode extends MapNode> List<TMapNode> createNodes(Supplier<TMapNode> supplier, List<Vector2Int> points) {
        return points.stream()
                .map(point -> {
                    TMapNode node = supplier.get();
                    getWorld().addActor(node);

                    node.getRoot().setTranslation(toTranslation(point));
                    node.getRoot().attachTo(getRoot());

                    return node;
                })
                .toList();
    }

    public static Vector2 toTranslation(Vector2Int position) {
        return position.toVector2()
                .mul(TILE_SIZE)
                .add(TILE_SIZE.copy().mul(0.5f));
    }

    public int getId(Tower tower) {
        for (int i = 0; i < platforms.size(); i++) {
            if (platforms.get(i).getTower() == tower) {
                return i;
            }
        }

        return -1;
    }

    public Vector2Int getSize() {
        return size;
    }

    public <TMapPathNode extends MapPathNode> List<TMapPathNode> getNodes(Class<TMapPathNode> nodeClass) {
        return nodes.stream()
                .filter(nodeClass::isInstance)
                .map(nodeClass::cast)
                .toList();
    }

    public List<MapPath> getPaths() {
        return paths;
    }

    public List<MapPlatform> getPlatforms() {
        return platforms;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }
}
