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
   private BufferedImage backgroundImage;
   private BufferedImage nextCardImage;
   private PokerApp myApp;
   
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
      
      Stroke oldStroke = g2.getStroke();
      g2.setStroke(new BasicStroke(2));      
      g2.drawRect(570, 300, 350, 50);
      g2.drawRect(570, 380, 350, 50);
      
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 35));
      g2.setColor(Color.WHITE);
      g2.drawString("Start Game", 630, 340);
      g2.drawString("Instructions", 630, 420);
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
         
         Rectangle2D.Double button1 = new Rectangle2D.Double(570, 300, 350, 50);
         Rectangle2D.Double button2 = new Rectangle2D.Double(570, 380, 350, 50);
         
         if(button1.contains(clickX, clickY))
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