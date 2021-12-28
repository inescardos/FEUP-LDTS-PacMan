package model.Maps.Builders;

import model.Elements.Pacman;
import model.Elements.Wall;
import model.Maps.Map;
import model.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MapReader implements MapBuilder {
    private int width, height;

    @Override
    public Map createMap(String mapName) throws IOException {
        Map m = new Map(width, height);
        String rootPath = new File(System.getProperty("user.dir")).getPath();
        String mapLocation = rootPath + "//src//main//resources//" + mapName;

        FileReader fr = new FileReader(new File(mapLocation));
        BufferedReader br = new BufferedReader(fr);


        // READS WIDTH AND HEIGHT
        width = Integer.parseInt(br.readLine());
        height = Integer.parseInt(br.readLine());

        m.setWalls(readWalls(br));

        // arranjar maneira melhor aqui. Não existe o .reset...
        fr = new FileReader(new File(mapLocation));
        br = new BufferedReader(fr);
        width = Integer.parseInt(br.readLine());
        height = Integer.parseInt(br.readLine());

        m.setPacman(readPacman(br));
        return m;
    }

    private List<Wall> readWalls(BufferedReader br) throws IOException {
        List<Wall> walls = new ArrayList<Wall>();
        for (int i = 0; i < height; i++) {
            String line = br.readLine();
            for (int j = 0; j < width; j++) {
                if (line.charAt(j) == '#') {
                    Wall wall = new Wall(new Position(i * 8, j * 12), 12, 8);
                    walls.add(wall);
                }
            }
        }

        return walls;
    }

    private Pacman readPacman(BufferedReader br) throws IOException {
        Pacman pacman = null;
        for (int i = 0; i < height; i++) {
            String line = br.readLine();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'c') {
                    pacman = new Pacman(new Position(i * 8 + 1, j * 12 - 1));
                    break;
                }
            }
        }

        return pacman;
    }


}

