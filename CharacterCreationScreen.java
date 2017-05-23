import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

public class CharacterCreationScreen extends JPanel
{
   BufferedImage icon1;
   BufferedImage icon2;
   BufferedImage icon3;
   BufferedImage icon4;
     
   public CharacterCreationScreen()
   {   
      try
      {
         InputStream is = getClass().getResourceAsStream("./IconImages/death.png");
         icon1 = ImageIO.read(is);
         is = getClass().getResourceAsStream("./IconImages/eyeball.png");
         icon2 = ImageIO.read(is);
         is = getClass().getResourceAsStream("./IconImages/jokerhat.png");
         icon3 = ImageIO.read(is);
         is = getClass().getResourceAsStream("./IconImages/teardrop.png");
         icon4 = ImageIO.read(is);
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
      
      g2.drawString("Enter Your Name: ", 100, 100);
      g2.drawString("Choose Your Icon: ", 100, 250);
      g2.drawImage(icon1, 150, 300, null);
      g2.drawImage(icon2, 300, 300, null);
      g2.drawImage(icon3, 450, 300, null);
      g2.drawImage(icon4, 600, 300, null);
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
         
         Rectangle2D.Double[] icons = new Rectangle2D.Double[4];
         icons[0] = new Rectangle2D.Double(150, 300, 128, 128);
         icons[1] = new Rectangle2D.Double(300, 300, 128, 128);
         icons[2] = new Rectangle2D.Double(450, 300, 128, 128);
         icons[3] = new Rectangle2D.Double(600, 300, 128, 128);
         
         for(int idx = 0; idx < 4; idx++)
         {
            if(icons[idx].contains(clickX, clickY) && idx == 0)
            {
               System.out.println("1");
               PokerApp.user.setIcon("death");
            }
            else if (icons[idx].contains(clickX, clickY) && idx == 1)
            {
               System.out.println("2");
               PokerApp.user.setIcon("eyeball");
            }
            else if (icons[idx].contains(clickX, clickY) && idx == 2)
            {
               System.out.println("3");
               PokerApp.user.setIcon("jokerhat");
            }
            else if (icons[idx].contains(clickX, clickY) && idx == 3)
            {
               System.out.println("4");
               PokerApp.user.setIcon("teardrop");
            }
         }
      }
      
      public void mouseReleased(MouseEvent e) { }
      
      public void mouseClicked(MouseEvent e) { }
      
      public void mouseEntered(MouseEvent e) { }
      
      public void mouseExited(MouseEvent e) { }
   }
   

}