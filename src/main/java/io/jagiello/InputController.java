package io.jagiello;

public class InputController {
    public static int KEY_A = 97;
    public static int KEY_D = 100;
    public static int KEY_W = 119;
    public static int KEY_S = 115;

    public InputController() {
    }

    public static void movePlayer(int key, Player player, Map map) {
        if (key == KEY_A) {
            player.turnLeft();
        }

        if (key == KEY_D) {
            player.turnRight();
        }

        if (key == KEY_W) {
            player.moveForward(map.getMap());
        }

        if (key == KEY_S) {
            player.moveBackward(map.getMap());
        }

    }
}
