package io.jagiello;

public class Map {
    public static int MAP_WIDTH = 16;
    public static int MAP_HEIGHT = 16;

    private StringBuffer map;

    public Map() {
        this.map = new StringBuffer();
        map.append("#########.......");
        map.append("#...............");
        map.append("#.......########");
        map.append("#..............#");
        map.append("#......##......#");
        map.append("#......##......#");
        map.append("#..............#");
        map.append("###............#");
        map.append("##.............#");
        map.append("#......####..###");
        map.append("#......#.......#");
        map.append("#......#.......#");
        map.append("#..............#");
        map.append("#......#########");
        map.append("#..............#");
        map.append("################");
    }

    public StringBuffer getMap() {
        return map;
    }
}
