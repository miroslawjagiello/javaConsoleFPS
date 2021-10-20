package io.jagiello;

import static io.jagiello.Map.MAP_HEIGHT;
import static io.jagiello.Map.MAP_WIDTH;

public class Screen {

    public static int SCREEN_WIDTH = 120;
    public static int SCREEN_HEIGHT = 40;

    private StringBuffer screen;

    public Screen() {
        this.screen = new StringBuffer();
        for (int i = 0; i < SCREEN_HEIGHT * SCREEN_WIDTH; i++) {
            screen.append(' ');
        }
        screen.append('\n');
    }

    public void setPixel(int x, int y, char _char){
        screen.setCharAt(x + y * SCREEN_WIDTH, _char);
    }

    public void displayMap(Map map, Player player){
        for(int nx = 0; nx < MAP_WIDTH; nx++){
            for(int ny = 0; ny < MAP_HEIGHT; ny++){
                screen.setCharAt((ny+1)*SCREEN_WIDTH + nx, map.getMap().charAt(ny* MAP_WIDTH + nx));
            }
        }
        screen.setCharAt(((int) player.getY() + 1) * SCREEN_WIDTH + (int)player.getX(), 'P');
    }

    public void prepareScreen(){
        for (int i = 1; i < SCREEN_HEIGHT * SCREEN_WIDTH; i++) {
            if (i % SCREEN_WIDTH == 0) {
                screen.setCharAt(i, '\n');
            }
        }
    }

    public StringBuffer getScreen() {
        return screen;
    }
}
