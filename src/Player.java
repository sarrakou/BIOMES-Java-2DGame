import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sarra
 */
public class Player {
    
    int x;
    int y;
    int width;
    int height;
    double xspeed; 
    double yspeed;
    
    AudioInputStream ais;
    Clip clip;
    
    private BufferedImage img = null;
    String name;
    
    Rectangle hitBox;
    
    boolean keyLeft;
    boolean keyRight;
    boolean keyUp;
    boolean keyDown;
    
    game_over GO;
    JFrame parent;
    GamePanel panel; 
    int score;
    
    EnterName nickname;
    
    Font pixelmix;
       
    public Player(int x, int y, GamePanel panel) throws UnsupportedAudioFileException, LineUnavailableException,IOException, FontFormatException{
        //I created the object nickname to acces to the name written by the user later on
        try {
            this.nickname = new EnterName();
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.panel = panel;
        //X position
        this.x = x;
        //Y position
        this.y = y;
        
        //dimensions of the player
        width = 50;
        height = 100;
        
        //used later on to check for collisions
        hitBox = new Rectangle(x,y,width,height);
    
        //death sound 
            File file = new File("src\\\\wav\\\\death.wav");
            try {
                ais = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(ais);
               
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
                GO = new game_over();
        GO.setLocationRelativeTo(null);
        GO.setResizable(false);
        GO.setVisible(true);
        GO.setTitle("GAME OVER");

    }
    
    public void set() throws IOException, InterruptedException{
        
        
        if (keyLeft == keyRight) {
            xspeed *= 0.8;
        }
        //to slow speed if moving left
        else if (keyLeft && !keyRight) {
            xspeed --;
        } 
        //to speed up if moving right
        else if (keyRight && !keyLeft) {
            xspeed ++;
        }  
        
        //to stop when the player isn't pressing the right key
        if (xspeed > 0 && xspeed < 0.75) {
            xspeed = 0;
        } 
        
        //to stop when the player isn't pressing the left key
        if (xspeed < 0 && xspeed > -0.75) {
            xspeed = 0;
        }
        //maximum speed
        if (xspeed >7) {
            xspeed = 7;
        }
        
        //minimum speed
        if (xspeed < -7) {
            xspeed = -7;
        }
        
        
        if (keyUp) {
            
            //to go up
            hitBox.y ++; 
            
            //to go back down if touching a wall after jumping
            for (wall wall: panel.walls) {
                if(wall.hitBox.intersects(hitBox)){
                    yspeed = -13;
                }
            } 
            
            //gravity (go back down)
            hitBox.y --;
            
        } 
        
        //overall speed
        yspeed += 0.6;
        
        
        //Horizontal Collision 
        hitBox.x += xspeed;
        
    for(int i=0;i<panel.walls.size();i++){
        //if not touching an obstacle
        if (panel.walls.get(i).id != 0){ 
            //if touching a wall
            if (hitBox.intersects(panel.walls.get(i).hitBox)) {
            hitBox.x -= xspeed;
            //while the player is NOT touching a wall
            while (!panel.walls.get(i).hitBox.intersects(hitBox)) { 
            hitBox.x += Math.signum(xspeed); // if xspeed> 0 math.signum = 1 , if xspeed <0 math.signum = -1
            } 
            
            //while the player IS touching a wall
            hitBox.x -= Math.signum(xspeed);
            //stop the camera movement
            panel.cameraX += x - hitBox.x;
            //stop the player
            xspeed = 0;
            hitBox.x = x;
                }
            }
        //if touching an obstacle
        else {
             if (hitBox.intersects(panel.walls.get(i).hitBox)) {
                 //death sound 
                clip.start();
                panel.sleep(1000);
                //close the game
                parent = (JFrame) panel.getTopLevelAncestor();             
                parent.dispose();
                //stop music
                panel.ST.stop();
             }
        }
    }
        
        //Vertical Collision (same as horizontal)
        hitBox.y += yspeed;
        for(int i=0;i<panel.walls.size();i++){
            //if not touching an obstacle
            if (panel.walls.get(i).id != 0){
            if (hitBox.intersects(panel.walls.get(i).hitBox)) {
                hitBox.y -= yspeed;
                while (!panel.walls.get(i).hitBox.intersects(hitBox)) {
                    hitBox.y += Math.signum(yspeed); // if yspeed> 0 math.signum = 1 , if yspeed <0 math.signum = -1
                }
                hitBox.y -= Math.signum(yspeed);
                yspeed = 0;
                y = hitBox.y;
            }
            } 
            //if touching an obstacle
            else {
                 if (hitBox.intersects(panel.walls.get(i).hitBox)) {
                    //death sound 
                     clip.start();
                    panel.sleep(1000);
                    //close the game
                    parent = (JFrame) panel.getTopLevelAncestor();
                    parent.dispose();
                    //stop music
                    panel.ST.stop();
   
                 }
            }
        }
        
        
        
        panel.cameraX -= xspeed;
        y += yspeed;
        
        /* so that the hitbox follows the player */
        hitBox.x = x;
        hitBox.y = y; 
        
        //Death code
        if (y > 600) {
            //death sound 
            clip.start();
            panel.sleep(1000);
            //close the game
            parent = (JFrame) panel.getTopLevelAncestor();
            parent.dispose();
            //stop music
            panel.ST.stop();

            /*
            GO = new game_over();
            GO.setLocationRelativeTo(null); //to start in the middle instead of the left corner
            GO.setResizable(false);
            GO.setVisible(true);  
            y=0; */
           
        }
        
        
    }
    
    public void draw(Graphics2D gtd) throws SQLException, IOException{
        //the player
        img = ImageIO.read( new File("src\\\\assets\\\\pngcharacter.png" ));
        gtd.drawImage(img,x,y, null);
        //downloading the font and setting it
        try {
            pixelmix = Font.createFont(Font.TRUETYPE_FONT, new File("src\\\\fonts\\\\pixelmix_bold.ttf")).deriveFont(25f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("src\\\\fonts\\\\pixelmix_bold.ttf")));
        } catch(IOException | FontFormatException e){
            
        }
        gtd.setFont(pixelmix);
        
        //getting the current player s username to add it on top of the player
       try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample","app","app");
            Statement stmt = con.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM APP.CURRENT_PLAYER");
            if (rs.next()) {
            name = rs.getString("username");
            } 
            else {
                System.out.print("db empty");
            }
            } catch (SQLException ex) {
            Logger.getLogger(EnterName.class.getName()).log(Level.SEVERE, null, ex);
            } 
    
        //drawing the username on top of the player
        gtd.drawString(name,x-30,y);
        //score printing
        gtd.drawString(" Score "+Integer.toString(panel.score), 50, 100);
        
        //to use it in the update
        score = panel.score;
         
        //to update the score while the user is playing
         try {
               Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample","app","app");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE APP.SCOREBOARD SET score="+score+" WHERE USERNAME='"+name+"'");
            } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(EnterName.class.getName()).log(Level.SEVERE, null, ex);
            } 
         
    }
}
