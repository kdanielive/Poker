import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

public class MainScreen extends JPanel
{
   BufferedImage backgroundImage;
   BufferedImage nextCardImage;
   PokerApp myApp;
   
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
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawImage(backgroundImage, 0, 0, null);
      g2.drawImage(nextCardImage, 1070, 590, null);
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
         
         Rectangle2D.Double nextBox = new Rectangle2D.Double(1070, 590, 128, 128);
         
         if(nextBox.contains(clickX, clickY))
         {
            myApp.switchScreen("CharacterScreen");
         }
      }
      
      public void mouseReleased(MouseEvent e) { }
      
      public void mouseClicked(MouseEvent e) { }
      
      public void mouseEntered(MouseEvent e) { }
      
      public void mouseExited(MouseEvent e) { }
   }
   

}