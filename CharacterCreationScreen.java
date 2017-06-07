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
   private BufferedImage icon1;
   private BufferedImage icon2;
   private BufferedImage icon3;
   private BufferedImage icon4;
   
   private String nameButton;
   
   private BufferedImage backgroundImage;
   private BufferedImage nextCardImage;
   
   private boolean icon1Selected = false;
   private boolean icon2Selected = false;
   private boolean icon3Selected = false;
   private boolean icon4Selected = false;
   
   private PokerApp myApp;
     
   public CharacterCreationScreen(PokerApp app)
   {   
      myApp = app;
      
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
         is = getClass().getResourceAsStream("darkforest.png");
         backgroundImage = ImageIO.read(is);
         is = getClass().getResourceAsStream("nextCard.png");
         nextCardImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
      
      nameButton = "Click!";
      
      addMouseListener(new MouseHandler());
      requestFocusInWindow();
   }
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawImage(backgroundImage, 0, 0, null);
      
      g2.drawString("Enter Your Name: ", 100, 100);
      g2.drawRect(220, 80, 300, 35);
      g2.drawString(nameButton, 240, 100);
      g2.drawString("Choose Your Icon: ", 100, 250);
      g2.drawImage(icon1, 150, 300, null);
      g2.drawImage(icon2, 300, 300, null);
      g2.drawImage(icon3, 450, 300, null);
      g2.drawImage(icon4, 600, 300, null);
      g2.drawImage(nextCardImage, 1070, 540, null);
      
      int height = icon1.getHeight();
      int width = icon1.getWidth();
      g2.setStroke(new BasicStroke(2));
      
      if(icon1Selected == true)  {  g2.drawRect(150, 300, width, height);  }
      else if(icon2Selected == true)   { g2.drawRect(300, 300, width, height);   }
      else if(icon3Selected == true)   { g2.drawRect(450, 300, width, height);   }
      else if(icon4Selected == true)   { g2.drawRect(600, 300, width, height);   }
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
                 
         Rectangle2D.Double nameBox = new Rectangle2D.Double(220, 80, 300, 35);
         Rectangle2D.Double nextBox = new Rectangle2D.Double(1070, 540, 128, 128);
         
         if(nameBox.contains(clickX, clickY))
         {
            JFrame frame = new JFrame("Message Box");
            nameButton = JOptionPane.showInputDialog(frame, 
               "Enter you name", "Your Name?", JOptionPane.WARNING_MESSAGE);
            repaint();
            PokerApp.user.setName(nameButton);
         }
         
         if(nextBox.contains(clickX, clickY))
         {
            myApp.switchScreen("Lobby");
         }
         
         for(int idx = 0; idx < 4; idx++)
         {
            if(icons[idx].contains(clickX, clickY) && idx == 0)
            {
               PokerApp.user.setIcon("death");
               icon1Selected = true;
               repaint();
            }
            else if (icons[idx].contains(clickX, clickY) && idx == 1)
            {
               PokerApp.user.setIcon("eyeball");
               icon2Selected = true;
               repaint();
            }
            else if (icons[idx].contains(clickX, clickY) && idx == 2)
            {
               PokerApp.user.setIcon("jokerhat");
               icon3Selected = true;
               repaint();
            }
            else if (icons[idx].contains(clickX, clickY) && idx == 3)
            {
               PokerApp.user.setIcon("teardrop");
               icon4Selected = true;
               repaint();
            }
         }
      }
      public void mouseReleased(MouseEvent e) { }
      public void mouseClicked(MouseEvent e) { }
      public void mouseEntered(MouseEvent e) { }
      public void mouseExited(MouseEvent e) { }
   }
   

}