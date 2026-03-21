package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        // places the player at the center of the screen
        // gamePanel.screenWidth/2 and gamePanel.screenHeight/2 is the middle pixel
        // (gamePanel.tileSize/2) uses the middle of the sprite character (which is 1 tile large)
        screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        // default player starting position on the map
        worldX = gamePanel.tileSize * 10;
        worldY = gamePanel.tileSize * 10;

        direction = "down"; // starting direction

        // todo: replace with variable later for upgrading player stats
        // ex. speed, stamina, strength, health
        speed = 5;
    }

    public void getPlayerImage(){
        // todo: replace with variable later for upgrading player appearance
        try{
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fish_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fish_down2.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fish_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fish_up2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fish_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fish_left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fish_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fish_right2.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){

        // check if a key is being pressed so the player is not animating while standing still
        if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {

            if (keyHandler.upPressed) {
                direction = "up";
                worldY -= speed;
            } else if (keyHandler.downPressed) {
                direction = "down";
                worldY += speed;
            } else if (keyHandler.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else if (keyHandler.rightPressed) {
                direction = "right";
                worldX += speed;
            }

            // alternate between player images for smooth transitions
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2){
//        g2.setColor(Color.orange);
//        g2.fillRect(x, worldY, gamePanel.tileSize, gamePanel.tileSize);

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
            default:
                image = null;
                break;
        }

        // the player remains at the center of the screen as the background is moved behind it
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
