



import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sarra
 */
public class keyChecker extends KeyAdapter {
    GamePanel panel;
    
    public keyChecker(GamePanel panel) {
        this.panel = panel;
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        try {
            panel.keyPressed(e);
        } catch (IOException ex) {
            Logger.getLogger(keyChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        panel.keyReleased(e);
    }
}
