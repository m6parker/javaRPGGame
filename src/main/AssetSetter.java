package main;

import Item.Worm;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setItem(){
        // using the item list from the GamePanel, add default items
        gp.items[0] = new Worm();
        gp.items[0].worldX = 5 * gp.tileSize;
        gp.items[0].worldY = 10 * gp.tileSize;

        gp.items[1] = new Worm();
        gp.items[1].worldX = 10 * gp.tileSize;
        gp.items[1].worldY = 8 * gp.tileSize;


    }
}
