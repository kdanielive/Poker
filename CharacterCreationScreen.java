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
the screen where the user's character is created
@author Daniel Kim
@version 06/01/2017
*/
public class CharacterCreationScreen extends JPanel
{
   private final int LABEL_X = 100;
   private final int NAME_Y = 100;
   private final int NAME_BUTTON_X = 240;
   private final int NAME_RECT_X = 300;
   private final int NAME_RECT_Y = 38;
   private final int ICON_LABEL_Y = 250;
   private final int ICON_X = 150;
   private final int ICON_Y = 300;
   private final int ICON_LENGTH = 150;
   private final int THICK_STROKE = 2;
   private final int CARD_Y = 540;
   private final int CARD_X = 1070;
   private final int ICON_DIM = 128;
   
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
   
   private boolean nameBool = false;
   
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
   
   public void screenInitCheck()
   {
      nameBool = false;
      nameButton = "Click!";
      icon1Selected = false;
      icon2Selected = false;
      icon3Selected = false;
      icon4Selected = false;
   }
   
   public void drawIcons(Graphics2D g2)
   {
      int height = icon1.getHeight();
      int width = icon1.getWidth();
      
      if(icon1Selected == true)  
      {  
         g2.drawRect(ICON_X, ICON_Y, width, height);  
      }
      else if(icon2Selected == true)   
      { 
         g2.drawRect(ICON_X + ICON_LENGTH, ICON_Y, width, height);   
      }
      else if(icon3Selected == true)   
      { 
         g2.drawRect(ICON_X + ICON_LENGTH * 2, ICON_Y, width, height);   
      }
      else if(icon4Selected == true)   
      { 
         g2.drawRect(ICON_X + ICON_LENGTH * 3, ICON_Y, width, height);   
      }
   }
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawImage(backgroundImage, 0, 0, null);
      
      g2.drawString("Enter Your Name: ", LABEL_X, NAME_Y);
      g2.drawRect(NAME_BUTTON_X, NAME_Y, NAME_RECT_X, NAME_RECT_Y);
      if(nameButton != null)  
      {  
         g2.drawString(nameButton, NAME_BUTTON_X, NAME_Y);   
      }
      g2.drawString("Choose Your Icon: ", LABEL_X, ICON_LABEL_Y);
      g2.drawImage(icon1, ICON_X, ICON_Y, null);
      g2.drawImage(icon2, ICON_X + ICON_LENGTH, ICON_Y, null);
      g2.drawImage(icon3, ICON_X + ICON_LENGTH * 2, ICON_Y, null);
      g2.drawImage(icon4, ICON_X + ICON_LENGTH * 3, ICON_Y, null);
      g2.drawImage(nextCardImage, CARD_X, CARD_Y, null);
     
      g2.setStroke(new BasicStroke(THICK_STROKE));
      
      drawIcons(g2);
   }
   
   public Rectangle2D.Double[] setIcons()
   {
      Rectangle2D.Double[] icons = new Rectangle2D.Double[4];
      icons[0] = new Rectangle2D.Double(ICON_X, 
         ICON_Y, ICON_DIM, ICON_DIM);
      icons[1] = new Rectangle2D.Double(ICON_X 
         + ICON_LENGTH, ICON_Y, ICON_DIM, ICON_DIM);
      icons[2] = new Rectangle2D.Double(ICON_X 
         + ICON_LENGTH * 2, ICON_Y, ICON_DIM, ICON_DIM);
      icons[3] = new Rectangle2D.Double(ICON_X 
         + ICON_LENGTH * 2, ICON_Y, ICON_DIM, ICON_DIM);
      return icons;
   }
   
   public void mousePressedHelper(String string)
   {
      myApp.getUser().setIcon(string);
      repaint();
   }
   
   public void nameBoxHelper()
   {
      JFrame frame = new JFrame("Message Box");
      nameButton = JOptionPane.showInputDialog(frame, 
         "Enter you name", "Your Name?", JOptionPane.WARNING_MESSAGE);
      repaint();
      myApp.getUser().setName(nameButton);
      if(nameButton != null)  {  nameBool = true;  }
   }
   
   public void iconSelectedHelper(int num)
   {
      if(num == 0)
      {
         icon1Selected = true;
         icon2Selected = false;
         icon3Selected = false;
         icon4Selected = false;
      }
      else if(num == 1)
      {
         icon1Selected = false;
         icon2Selected = true;
         icon3Selected = false;
         icon4Selected = false;
      }
      else if(num == 2)
      {
         icon1Selected = false;
         icon2Selected = false;
         icon3Selected = true;
         icon4Selected = false;
      }
      else if(num == 3)
      {
         icon1Selected = false;
         icon2Selected = false;
         icon3Selected = false;
         icon4Selected = true;
      }
   }
   
   public void nextBoxHelper()
   {
      if(nameBool)   {  myApp.switchScreen("Lobby");  }
      else  
      {    
         JFrame frame = new JFrame("Message Box");
         JOptionPane.showConfirmDialog(frame, "Enter your name first!", "Name not entered", -1);
      }
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
         Rectangle2D.Double[] icons = setIcons();  
         Rectangle2D.Double nameBox = new Rectangle2D.Double(220, 80, 300, 35);
         Rectangle2D.Double nextBox = new Rectangle2D.Double(1070, 540, 130, 260);
         if(nameBox.contains(clickX, clickY))
         {
            nameBoxHelper();
         }
         if(nextBox.contains(clickX, clickY))
         {
            nextBoxHelper();
         }
         if(icons[0].contains(clickX, clickY))
         {
            iconSelectedHelper(0);
            mousePressedHelper("death");
         }
         else if (icons[1].contains(clickX, clickY))
         {
            iconSelectedHelper(1);
            mousePressedHelper("eyeball");
         }
         else if (icons[2].contains(clickX, clickY))
         {
            iconSelectedHelper(2);
            mousePressedHelper("jokerhat");
         }
         else if (icons[3].contains(clickX, clickY))
         {
            iconSelectedHelper(3);
            mousePressedHelper("teardrop");
         }
      }
      public void mouseReleased(MouseEvent e) { }
      public void mouseClicked(MouseEvent e) { }
      public void mouseEntered(MouseEvent e) { }
      public void mouseExited(MouseEvent e) { }
   }
}