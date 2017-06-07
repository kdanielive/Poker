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
   /** x-coord of text box */
   private final int BOX_X = 570;
   /** y-coord of text box */
   private final int BOX_Y = 340;
   /** width of text box */
   private final int BOX_W = 330;
   /** height of text box */
   private final int BOX_H = 60;
   /** x-coord of text */
   private final int TEXT_X = 630;
   /** y-coord of text */
   private final int TEXT_Y = 380;
   /** stroke thickness */
   private final int STROKE = 3;

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
      
      g2.setColor(Color.WHITE);
      Stroke oldStroke = g2.getStroke();
      g2.setStroke(new BasicStroke(STROKE));      
      g2.drawRect(BOX_X, BOX_Y, BOX_W, BOX_H);
      
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 35));
      g2.setColor(Color.WHITE);
      g2.drawString("Start Game", TEXT_X, TEXT_Y);
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