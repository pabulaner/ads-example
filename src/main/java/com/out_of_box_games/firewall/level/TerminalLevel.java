package com.out_of_box_games.firewall.level;

import com.out_of_box_games.firewall.data.game.GameData;
import com.out_of_box_games.firewall.terminal.ButtonLine;
import com.out_of_box_games.firewall.terminal.Line;
import com.out_of_box_games.firewall.terminal.Terminal;
import com.out_of_box_games.firewall.terminal.TextLine;
import com.out_of_box_games.firewall.util.SaveGame;
import com.out_of_box_games.gengine.Engine;
import com.out_of_box_games.gengine.world.Actor;
import com.out_of_box_games.gengine.world.Level;
import com.out_of_box_games.gengine.world.actor.UI;
import com.out_of_box_games.gengine.world.component.CameraComponent;

import java.util.ArrayList;
import java.util.List;

public class TerminalLevel extends Level {

    @Override
    public UI getUI() {
        Terminal terminal = new Terminal();

        List<Line> main = new ArrayList<>();
        List<Line> maps = new ArrayList<>();
        List<Line> help = new ArrayList<>();

        main.add(new TextLine("Firewall (version = 1.0)\n"));
        addButton(main, "Boot", () -> terminal.show(maps));
        addButton(main, "Help", () -> terminal.show(help));
        addButton(main, "Exit", () -> Engine.get()
                .getPlatformSystem()
                .exit());

        maps.add(new TextLine("Select Edition\n"));

        String[] names = {
                "Individual",
                "Educational",
                "Company",
                "Enterprise"
        };

        for (int i = 0; i < names.length; i++) {
            String name = names[i];

            int level = i + 1;
            GameData data = SaveGame.load(level);

            if (data != null) {
                name += " (" + data.getWave().getIndex() + ")";
            }

            addButton(maps, name, () -> terminal.getWorld().loadLevel(new GameLevel(level)));
        }

        help.add(new TextLine("""
                # Firewall
                
                Your job is to protect the network from malicious files.
                To do so You may place towers to target different kinds of files.
                
                It might also be necessary to utilize Your CPU in a tactical way.
                This means assigning CPU to towers that need it the most.
                But be careful, as You only have a limited amount of CPU power.
                """));

        addButton(maps, "Back", () -> terminal.show(main));
        addButton(help, "Back", () -> terminal.show(main));

        terminal.show(main);
        return terminal;
    }

    @Override
    public List<Actor> getActors() {
        Actor camera = new Actor();
        camera.setRoot(camera.addComponent(new CameraComponent()));

        return List.of(camera);
    }

    private void addButton(List<Line> lines, String text, Runnable action) {
        lines.add(new ButtonLine(" > " + text + " ", action));
    }
}
