package tile;

import main.GamePanel;

import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;


public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    private Tile[] tileList;
    public String jsonPath = "/maps/map2.json";

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
//        loadMap("/maps/map2.json");
        loadMapFromJson();
    }

    public void getTileImage(){
        // get the tile types from the external tile config file
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream("/tiles/config.json")) {
            if (is == null) {
                throw new RuntimeException("Could not find tiles.json in resources!");
            }

            List<Map<String, Object>> tilesConfig = mapper.readValue(
                    is,
                    mapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );

            // Initialize tile array based on the number of tiles in the config
            tile = new Tile[tilesConfig.size()];

            for (Map<String, Object> config : tilesConfig) {
                int id = ((Number) config.get("id")).intValue();
                String imagePath = "/tiles/" + config.get("image");
                boolean collision = (boolean) config.get("collision");

                tile[id] = new Tile();
                tile[id].image = ImageIO.read(getClass().getResourceAsStream(imagePath));
                tile[id].collision = collision;
            }
        } catch (Exception e) {
            System.err.println("Failed to load tiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadMapFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream(jsonPath)) {
            if (is == null) {
                throw new RuntimeException("Could not find map JSON: " + jsonPath);
            }

            // Parse the entire map structure
            Map<String, Object> mapData = mapper.readValue(is, Map.class);

            // Extract the first layer
            List<Map<String, Object>> layers = (List<Map<String, Object>>) mapData.get("layers");
            if (layers.isEmpty()) {
                throw new RuntimeException("No layers found in the map!");
            }

            Map<String, Object> firstLayer = layers.get(0);
            List<Integer> layerDataList = (List<Integer>) firstLayer.get("data");

            // Fill the mapTileNum array
            for (int y = 0; y < gp.maxWorldRow; y++) {
                for (int x = 0; x < gp.maxWorldCol; x++) {
                    int index = y * gp.maxWorldCol + x;
                    mapTileNum[x][y] = layerDataList.get(index);
                }
            }

        } catch (Exception e) {
            System.err.println("Failed to load map: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
//        g2.drawImage(tile[0].image, 0,0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 48,0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 96,0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[1].image, 48,48, gp.tileSize, gp.tileSize, null);
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            // world(x,y) is the location on the map
            // screen(x,y) is the position in the window
            // the screen position is determined based on the players location in the world
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // only draw the tiles that are visible to the player
            // don't load tiles that are outside the boundaries
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
