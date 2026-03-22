package Item;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Worm extends Item{
    public Worm(){
        name = "worm";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Items/worm.png")));
        }catch(IOException e ){
            e.printStackTrace();
        }
    }
}
