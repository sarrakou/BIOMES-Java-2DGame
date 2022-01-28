
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sarra
 */
public class wall {
    int x;
    int y;
    int width;
    int height;
    int startX;
    Rectangle hitBox;
    BufferedImage img = null;
    int id;
    
    public wall(int id,BufferedImage img,int x,int y , int width, int height){
        //this.color = color;
        this.img = img;
        this.x =x;
        startX =x;
        this.y=y;
        this.height=height;
        this.width=width;
        this.id = id;
        hitBox = new Rectangle(x,y,width,height);
    }
    
    public void draw(Graphics2D gtd) throws IOException{
        //gtd.setColor(Color.BLACK);
       //gtd.drawRect(x, y, width, height); 
        gtd.drawImage(img, x, y,null);
        //gtd.fillRect(x+1, y+1, width-2, height-2);
    }
    
    
    //set the wall + its hitbox position according to the camera position
    public int set(int cameraX){
        x = startX + cameraX;
        hitBox.x = x;
        
        return x;
    }
}
