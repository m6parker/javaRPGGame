package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX;
    public int worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    // the part of the entity that is solid
    // ex. player's feet, not whole tile of player
    public Rectangle solidArea;
    public boolean collisionOn = false;

}
