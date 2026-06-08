package com.out_of_box_games.firewall.ui.game;

import com.out_of_box_games.firewall.level.TerminalLevel;
import com.out_of_box_games.firewall.ui.control.Button;

public class PauseMenu extends GameMenu {

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        Button button = new Button();
        button.setText("Pause");

        getWorld().addActor(button);
        button.getRoot().attachTo(getRoot());

        addBottomButton("Exit", () -> {
//            GameModeBase gameMode = getWorld().getGameMode();
//
//            gameMode.save();
            getWorld().loadLevel(new TerminalLevel());
        });
    }

    @Override
    protected void createCloseButton() {
        addBottomButton("Close", () -> {
            GameUI ui = getWorld().getUI();

            getWorld().setPause(false);
            ui.hideMenu();
        });
    }
}
