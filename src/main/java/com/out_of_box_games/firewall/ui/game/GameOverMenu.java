package com.out_of_box_games.firewall.ui.game;

import com.out_of_box_games.firewall.level.GameLevel;
import com.out_of_box_games.firewall.level.TerminalLevel;
import com.out_of_box_games.firewall.ui.control.Button;
import com.out_of_box_games.firewall.world.game.GameModeBase;
import com.out_of_box_games.firewall.world.game.GameStateBase;

public class GameOverMenu extends GameMenu {

    @Override
    protected void onAddToWorld() {
        super.onAddToWorld();

        GameStateBase gameState = getWorld().getGameState();
        Button gameOver = new Button();

        gameOver.setText("Game Over (" + gameState.getWave() + ")");

        getWorld().addActor(gameOver);
        gameOver.getRoot().attachTo(getRoot());
        
        addBottomButton("Exit", () -> getWorld().loadLevel(new TerminalLevel()));
    }

    @Override
    protected void createCloseButton() {
        GameModeBase gameMode = getWorld().getGameMode();
        addBottomButton("Restart", () -> getWorld().loadLevel(new GameLevel(gameMode.getLevel())));
    }
}
