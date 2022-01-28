/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sarra
 */

// This class was created in order to fetch the usernames+scores on the scoreboard

public class Character {
   
     private int score;
     private String username;
     
     public Character(int score,String username){
         this.score = score;
         this.username=username;
         
     }
     
     public int getScore() {
         return score;
     }
     
      public String getUsername() {
         return username;
     }
 
}
