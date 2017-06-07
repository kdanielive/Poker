import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

/**
class for the starting main screen of the program
@author Daniel Kim
@version 04/11/2017
*/
public class MainScreen extends JPanel
{
   /** background image */
   private BufferedImage backgroundImage;
   /** turning card image */
   private BufferedImage nextCardImage;
   /** PokerApp object that controls the screens and part of the program */
   private PokerApp myApp;
   
   /**
   default constructor of MainScreen class
   @param app the PokerApp object using the screen
   */
   public MainScreen(PokerApp app)
   {
      myApp = app;
      try
      {
         InputStream is = getClass().getResourceAsStream("MainBackground.jpg");
         backgroundImage = ImageIO.read(is);
         is = getClass().getResourceAsStream("nextCard.png");
         nextCardImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
   
      addMouseListener(new MouseHandler());
      requestFocusInWindow();
   }
   
   /**
   draws the major components of the screen
   @param g graphics object
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawImage(backgroundImage, 0, 0, null);
      
      g2.setColor(Color.RED);
      Stroke oldStroke = g2.getStroke();
      g2.setStroke(new BasicStroke(2));      
      g2.drawRect(570, 340, 330, 60);
      //g2.drawRect(570, 380, 350, 50);
      
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 35));
      g2.setColor(Color.WHITE);
      g2.drawString("Start Game", 630, 380);
      //g2.drawString("Instructions", 630, 420);
   }
   
   /**
   handles mouse clicks
   */
   private class MouseHandler implements MouseListener
   {
      /**
      handles mouse button presses
      @param e info about the mouse event
      */
      public void mousePressed(MouseEvent e)
      {
         int clickX = e.getX();
         int clickY = e.getY();
         
         Rectangle2D.Double button1 = new Rectangle2D.Double(570, 340, 350, 50);
         Rectangle2D.Double button2 = new Rectangle2D.Double(570, 380, 350, 50);
         
         if(button1.contains(clickX, clickY))
         {
            myApp.switchScreen("CharacterScreen");
         }
      }
      
      /**
      handles mouse button releases
      @param e info about the mouse event
      */
      public void mouseReleased(MouseEvent e) { }
      
      /**
      handles mouse button clicks
      @param e info about the mouse event
      */
      public void mouseClicked(MouseEvent e) { }
      
      /**
      handles mouse button enters
      @param e info about the mouse event
      */
      public void mouseEntered(MouseEvent e) { }
      
      /**
      handles mouse button exits
      @param e info about the mouse event
      */
      public void mouseExited(MouseEvent e) { }
   }
   

}