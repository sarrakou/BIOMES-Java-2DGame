


import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sarra
 */
public class GamePanel  extends javax.swing.JPanel implements ActionListener {

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sarra
 */
    Player player;
    ArrayList<wall> walls = new ArrayList<>();
    Timer gameTimer;
    int cameraX;
    int offset;
   
    AudioInputStream ais;
    Clip clip;
    AudioInputStream aisP ;
    Clip ST;
    
    int score;
    
    Rectangle PauseRect;
    Rectangle MusicRect;
    
    
    
    public GamePanel() throws IOException, UnsupportedAudioFileException, LineUnavailableException, FontFormatException {
        
        PauseRect = new Rectangle(600,48,48,48);
        MusicRect = new Rectangle(550,48,48,48);
        
        setLayout(null);
        JLabel background;
        ImageIcon img = new ImageIcon("src\\\\assets\\\\gameBG1.png");
        
        background = new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0, 700, 700);
        add(background);
        
        player = new Player(400,300,this);
        reset();
        
        gameTimer = new Timer();
        score = -100;
        gameTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                
                 try {
                    player.set();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(walls.get(walls.size() - 1).x < 800){
                    offset += 700;
                    try {
                        makeWalls(offset);
                    } catch (IOException ex) {
                        Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    score += 100;
                } 
                
              for (wall wall:walls) {
                  wall.set(cameraX);
              } 
            
              //to delete old walls after making new walls (save CPU memory)
              for(int i=0;i<walls.size();i++){
                  if (walls.get(i).x < -800){
                      walls.remove(i);
                  }
              }
              
              repaint(); 
            }
            
        }, 0, 17); /* 1000/60 = 16.67 */
            
        File file = new File("src\\\\wav\\\\Soundtrack.wav");
        aisP = AudioSystem.getAudioInputStream(file);
        ST = AudioSystem.getClip();
        ST.open(aisP);
        ST.start();
    
    }
    
    
 
      
    
    public void reset() throws IOException {
        player.x= 100;
        player.y = 150;
        cameraX = 150;
        
        player.xspeed = 0;
        player.yspeed= 0;
        walls.clear();
        offset = -150;
        try {
            makeWalls(offset);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        score = -100;
    }
    
    
    public void makeWalls(int offset) throws IOException{
        int s=50;
        Random rand = new Random();
        int index = rand.nextInt(8);
        switch (index){
            case 0 -> {
                
                for (int i=0;i<14;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\sand.png" )),(offset+i*50),600,s,s));
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\sand.png" )),(offset+i*50),550,s,s));
                } 
                
               walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\cactus.png" )),(offset+50+150),500,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\cactus.png" )),(offset+50+500),500,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\cactus.png" )),(offset+50+500),450,s,s));

                
            }
            case 1 -> {
                
                for (int i=1;i<5;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\wood.png" )),(offset+i*50),550,s,s));
                } 
                for (int i=9;i<13;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\wood.png" )),(offset+i*50),550,s,s));
                } 
             
                
            }
            case 2 -> {
                for (int i=2;i<5;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\wood.png" )),(offset+i*50),550,s,s));
                } 
                for (int i=9;i<11;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\wood.png" )),(offset+i*50),550,s,s));
                } 
                
                
            }
            case 3 -> {
                
                for (int i=0;i<3 ;i++){
                    walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+i*50),550,s,s));
                }
                for (int i=3;i<5 ;i++){
                    walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+i*50),550,s,s));
                }
                
                for (int i=2;i<5 ;i++){
                    walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+450+i*50),550,s,s));
                }
                
                for (int i=0;i<2 ;i++){
                    walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+450+i*50),550,s,s));
                }
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),offset+150,500,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),offset+200,500,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),offset+200,450,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+200),400,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+500),500,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+450),500,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+450),450,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+450),400,s,s));
                
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset),600,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+50),600,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+100),600,s,s));
                
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+500),600,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+550),600,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+600),600,s,s));                
                
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\thorns.png" )),(offset+50),500,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\thorns.png" )),(offset+50+500),500,s,s));
            
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+150),600,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+200),600,s,s));
                 walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+450),600,s,s));
                walls.add(new wall(3,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+650),600,s,s));
            }
            case 4 -> {
                for (int i=0;i<14;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),600,s,s));
                }                 
                for (int i=0;i<14;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),550,s,s));
                } 
                for (int i=4;i<9;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),500,s,s));
                } 
                for (int i=6;i<8;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),450,s,s));
                } 
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\icicle.png" )),(offset+450),500,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\icicle.png" )),(offset+250),450,s,s));
               
            }
            case 5 -> {
                for (int i=0;i<14;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\sand.png" )),(offset+i*50),600,s,s));
                } 
                for (int i=0;i<14;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\sand.png" )),(offset+i*50),550,s,s));
                } 
                for (int i=4;i<8;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\sand.png" )),(offset+i*50),500,s,s));
                } 
                for (int i=6;i<8;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\sand.png" )),(offset+i*50),450,s,s));
                } 
                for (int i=6;i<8;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\sand.png" )),(offset+i*50),400,s,s));
                } 
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\cactus.png" )),(offset+450),450,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\cactus.png" )),(offset+450),500,s,s));
                
            }
            case 6 -> {
                for (int i=0;i<14;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),600,s,s));
                } 
                for (int i=0;i<14;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),550,s,s));
                } 
                for (int i=1;i<8;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),500,s,s));
                }
                for (int i=2;i<8;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),450,s,s));
                }
                for (int i=6;i<8;i++){
                    walls.add(new wall(8,ImageIO.read( new File("src\\\\assets\\\\snow.png" )),(offset+i*50),400,s,s));
                }
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\icicle.png" )),(offset+200),400,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\icicle.png" )),(offset+250),400,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\icicle.png" )),(offset+400),500,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\icicle.png" )),(offset+450),500,s,s));
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\icicle.png" )),(offset+500),500,s,s));
               
                }
            case 7 -> {
                
                walls.add(new wall(0,ImageIO.read( new File("src\\\\assets\\\\thorns.png" )),offset+50,500,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset),550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),offset+50,550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),offset+100,550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset),600,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),offset+50,600,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),offset+100,600,s,s));
                
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),offset+200,450,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+250),450,s,s)); 
                
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),offset+200,500,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+250),500,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),offset+200,550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+250),550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),offset+200,600,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+250),600,s,s));
                
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+350),350,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+400),350,s,s));
                
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+350),400,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+400),400,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+350),450,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+400),450,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+350),500,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+400),500,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+350),550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+400),550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+350),600,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+400),600,s,s));
                
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+500),450,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+550),450,s,s));
                
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+500),500,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+550),500,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+500),550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+550),550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+500),600,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+550),600,s,s));
                
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\grass.png" )),(offset+650),550,s,s));
                walls.add(new wall(7,ImageIO.read( new File("src\\\\assets\\\\dirt.png" )),(offset+650),600,s,s));
                
            }
        }
        
    }
    
    
    @Override
    public void paint(Graphics g) {
        
        super.paint(g);
        
        Graphics2D gtd = (Graphics2D) g; 
        
        //walls
        for(int i=0;i<walls.size();i++) {  
            try {
                walls.get(i).draw(gtd);
            } catch (IOException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        //player
        try { 
            player.draw(gtd);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //the pause box
        try {
            gtd.drawImage(ImageIO.read( new File("src\\\\assets\\\\pause.png" )),600,48,null);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //the music box
        try {
            gtd.drawImage(ImageIO.read( new File("src\\\\assets\\\\music.png" )),540,48,null);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

  void keyPressed(KeyEvent e) throws IOException {
        switch(e.getKeyChar()) {
            case 'q' -> player.keyLeft = true;
            case 'z' ->{
                player.keyUp = true;
                 //jump sound 
                    File file = new File("src\\\\wav\\\\jump.wav");
                    try {
                        ais = AudioSystem.getAudioInputStream(file);
                        clip = AudioSystem.getClip();
                        clip.open(ais);
                        clip.start();
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                        Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                    } 
            }
            case 'd' -> player.keyRight = true;
            case 's' -> player.keyDown = true;
            case 'r' -> reset();
            case 'p' -> pause();
        }      
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("\n Thank you for playing");
            System.exit(0);
        }
    } 

  public void pause() {
      pauseFrame jf= new pauseFrame();
            jf.show();
            jf.setTitle("PAUSE MENU");
            
        /*    
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } 
        */         
}

    void keyReleased(KeyEvent e) {
       switch(e.getKeyChar()) {
            case 'q' -> player.keyLeft = false;
            case 'z' -> player.keyUp = false;
            case 'd' -> player.keyRight = false;
            case 's' -> player.keyDown = false;
        }
    }
    
      @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    
    void mouseClicked(MouseEvent e) throws IOException {
        if (PauseRect.contains(new Point(e.getPoint().x, e.getPoint().y - 27))) {/*because jframe's mouse click can sometimes have bugs*/ 
            /* create the pause menu jframe */ 
        pause();
        } 
        
        if (MusicRect.contains(new Point(e.getPoint().x, e.getPoint().y - 27))) {/*because jframe's mouse click can sometimes have bugs*/ 
            /* create the pause menu jframe */ 
        if (ST.isRunning()){
            ST.stop();
        }
        else {
            ST.start();
        }
        }
        
    
        } 

    public static void sleep(int i) throws InterruptedException{
        Thread.sleep(i);
    }
}
    


