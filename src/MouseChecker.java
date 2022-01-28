


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class MouseChecker implements MouseListener {

    GamePanel panel;
    
    public MouseChecker(GamePanel panel) {
        this.panel = panel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            panel.mouseClicked(e);
        } catch (IOException ex) {
            Logger.getLogger(MouseChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    @Override
    public void mousePressed(MouseEvent e) {
       }

    @Override
    public void mouseReleased(MouseEvent e) {
       }

    @Override
    public void mouseEntered(MouseEvent e) {
       }

    @Override
    public void mouseExited(MouseEvent e) {
       }
    
}
