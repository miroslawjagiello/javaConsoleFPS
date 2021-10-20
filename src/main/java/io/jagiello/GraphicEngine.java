package io.jagiello;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

import static io.jagiello.Map.MAP_HEIGHT;
import static io.jagiello.Map.MAP_WIDTH;
import static io.jagiello.Screen.SCREEN_HEIGHT;
import static io.jagiello.Screen.SCREEN_WIDTH;

public class GraphicEngine {

    public static float FIELD_OF_VIEW = (float)Math.PI / 4.0f;
    public static float DEPTH = 16.0f;

    private Map map;
    private Player player;
    private Screen screen;

    public GraphicEngine(Map map, Player player, Screen screen) {
        this.map = map;
        this.player = player;
        this.screen = screen;
    }

    public void makeFrame(){
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            float fRayAngle = (player.getAngle() - FIELD_OF_VIEW / 2.0f) + ((float) x / (float) SCREEN_WIDTH) * FIELD_OF_VIEW;

            float fDistanceToWall = 0;
            boolean bHitWall = false;
            boolean bBoundary = false;

            float fEyeX = (float) Math.sin(fRayAngle);
            float fEyeY = (float) Math.cos(fRayAngle);

            while (!bHitWall && fDistanceToWall < DEPTH) {
                fDistanceToWall += 0.1f;

                int nTestX = (int) (player.getX() + fEyeX * fDistanceToWall);
                int nTestY = (int) (player.getY() + fEyeY * fDistanceToWall);

                // Test if ray is out of bounds
                if (nTestX < 0 || nTestX >= MAP_WIDTH || nTestY < 0 || nTestY >= MAP_HEIGHT) {
                    bHitWall = true;          // Just set distance to maximum depth
                    fDistanceToWall = DEPTH;
                } else {
                    // Ray is inbounds so test to see if the ray cell is a wall block
                    if (map.getMap().charAt(nTestY * MAP_WIDTH + nTestX) == '#') {
                        bHitWall = true;

                        // distance, dot
                        List<Pair<Float,Float>> p = new ArrayList<>();

                        for (int tx = 0; tx < 2; tx++){
                            for(int ty = 0; ty < 2; ty++){
                                float vy = (float)nTestY + ty - player.getY();
                                float vx = (float)nTestX + tx - player.getX();
                                float d = (float)Math.sqrt(vx*vx + vy*vy);
                                float dot = (fEyeX * vx/d) + (fEyeY * vy / d);
                                p.add(new Pair<>(d, dot));
                            }
                        }

                        p.sort((left, right) -> {
                            if( left.getValue0().floatValue() > right.getValue0().floatValue()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        });

                        float fBound = 0.01f;
                        if (Math.acos(p.get(0).getValue1()) < fBound) bBoundary = true;
                        if (Math.acos(p.get(1).getValue1()) < fBound) bBoundary = true;
                        //if (Math.acos(p.get(2).getValue1()) < fBound) bBoundary = true;
                    }
                }
            }

            fillScreen(x, fDistanceToWall, bBoundary);
        }
    }

    private void fillScreen(int x, float distanceToWall, boolean boundary) {
        char nShade = calculateWallShade(distanceToWall, boundary);

        //Calculate distance to ceilling and floor
        int nCeilling = (int) ((float) (SCREEN_HEIGHT / 2.0) - SCREEN_HEIGHT / distanceToWall);
        int nFloor = SCREEN_HEIGHT - nCeilling;

        for (int y = 0; y < SCREEN_HEIGHT; y++) {
            if (y <= nCeilling) {
                screen.setPixel(x, y, ' ');
            } else if (y > nCeilling && y <= nFloor) {
                screen.setPixel(x, y, nShade);
            } else {
                // Shade floor based on distance
                float b = 1.0f - (((float)y - SCREEN_HEIGHT / 2.0f) / ((float) SCREEN_HEIGHT / 2.0f));
                screen.setPixel(x, y, calculateFloorShade(b));
            }
        }
    }

    private char calculateWallShade(float distance, boolean boundary){
        char shade = ' ';

        if (distance <= DEPTH / 4.0f)      shade = 0x2588; // Very close
        else if (distance <= DEPTH / 3.0f) shade = 0x2593;
        else if (distance <= DEPTH / 2.0f) shade = 0x2592;
        else if (distance <= DEPTH)        shade = 0x2591;
        else                               shade = ' ';    // To far away

        if (boundary) shade = ' '; // Black it out

        return shade;
    }

    private char calculateFloorShade(float distance){
        char floorShade = ' ';

        if (distance < 0.25)      floorShade = '#';
        else if (distance < 0.5)  floorShade = 'x';
        else if (distance < 0.75) floorShade = '.';
        else if (distance < 9)    floorShade = '-';
        else                      floorShade = ' ';

        return floorShade;
    }
}
