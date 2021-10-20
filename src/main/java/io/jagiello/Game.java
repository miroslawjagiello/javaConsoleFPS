package io.jagiello;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Game {
    public static void main(String[] args) throws IOException, InterruptedException {

        Player player = new Player();
        Map map = new Map();
        Screen screen = new Screen();

        GraphicEngine graphicEngine = new GraphicEngine(map, player, screen);

        final int[] keyPressed = {0};
        ConsoleUtils.runKeyListener(keyPressed);

        while (true) {
            InputController.movePlayer(keyPressed[0], player, map);
            keyPressed[0] = 0;

            graphicEngine.makeFrame();

            screen.displayMap(map, player);
            screen.prepareScreen();

            ConsoleUtils.drawScreen(screen);
            TimeUnit.MILLISECONDS.sleep(20);
            ConsoleUtils.clearScreen();
        }
    }
}
