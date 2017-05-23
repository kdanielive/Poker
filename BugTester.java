import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class BugTester extends JPanel
{
   Deck deck = new Deck();
   
   public BugTester()
   {
      
   }
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      for(int row = 0; row < 4; row++)
      {
         for(int col = 0; col < 14; col++)
         {
            PokerCard card = deck.drawCard();
            g2.drawImage(card.getImage(), col * 100, row * 200, null);
            col++;
            System.out.println(card.getName());
         }
         row++;
      }
   }  
   
   public static void run()
   {
      BugTester myScreen = new BugTester();
      
      JFrame myApp = new JFrame();
      myApp.add(myScreen);
      
      myScreen.setFocusable(true);
      myScreen.requestFocusInWindow();
            
      myApp.setSize(1200, 800);
      myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myApp.setVisible(true);
   }
   
   public static void main(String[] args)
   {
      run();
   }
}