




import java.awt.Color;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sarra
 */
public class mainFrame extends javax.swing.JFrame{
    int score;
    
    
    public mainFrame() throws IOException, UnsupportedAudioFileException, LineUnavailableException, FontFormatException{ 
        GamePanel panel = new GamePanel();
        panel.setLocation(0,0);
        panel.setBackground(Color.lightGray);
        panel.setSize(this.getSize());
        panel.setVisible(true);
        this.add(panel); 
        score = panel.score;
        addKeyListener(new keyChecker(panel));
        addMouseListener(new MouseChecker(panel)); 

    } 
    
}
