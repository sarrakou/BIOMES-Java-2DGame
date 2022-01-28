
import java.awt.Color;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */ 

/**
 *
 * @author sarra
 */

public class javaProject {
    
    public static void main(String[] args) {
      MainMenu mframe = new MainMenu();
        
        mframe.setSize(700, 700); 
        mframe.setLocationRelativeTo(null); /* to start in the middle instead of the left corner */
        
        mframe.setResizable(false);
        mframe.setTitle("BIOMES Platform Game");
        mframe.setVisible(true); 
        
        mframe.setDefaultCloseOperation(EXIT_ON_CLOSE); /* to end program after closing */
    }
    
    
}
