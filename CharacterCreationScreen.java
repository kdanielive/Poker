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
   /** the x-coordinate of two labels  */
   private final int LABEL_X = 100;
   /** the y-coorindate of name label  */
   private final int NAME_Y = 100;
   /** the x-coordinate of name button */
   private final int NAME_BUTTON_X = 240;
   /** the x-coordinate of name text box */
   private final int NAME_RECT_X = 300;
   /** the y-coordinate of name text box */
   private final int NAME_RECT_Y = 38;
   /** the y-coordinate of icon label */
   private final int ICON_LABEL_Y = 250;
   /** the initial x-coordinate of icons */
   private final int ICON_X = 150;
   /** the initial y-coordinate of icons */
   private final int ICON_Y = 300;
   /** the length of the icons */
   private final int ICON_LENGTH = 150;
   /** the stroke thickness for graphics */
   private final int THICK_STROKE = 2;
   /** the y-coordinate of the turning card */
   private final int CARD_Y = 540;
   /** the x-coordinate of the turning card */
   private final int CARD_X = 1070;
   /** the length and width of the icons */
   private final int ICON_DIM = 128;
   
   /** the first icon */
   private BufferedImage icon1;
   /** the second icon */
   private BufferedImage icon2;
   /** the third icon */
   private BufferedImage icon3;
   /** the fourth icon */
   private BufferedImage icon4;
   
   /** the text that appears in name text box */
   private String nameButton;
   
   /** the background image */
   private BufferedImage backgroundImage;
   /** the image for turning card */
   private BufferedImage nextCardImage;

   /** indicates whether icon1 is selected */
   private boolean icon1Selected = false;
   /** indicates whether icon2 is selected */
   private boolean icon2Selected = false;
   /** indicates whether icon3 is selected */
   private boolean icon3Selected = false;
   /** indicates whether icon4 is selected */
   private boolean icon4Selected = false;
   
   /** indicates whether the name is correctly filled in */
   private boolean nameBool = false;
   
   /** PokerApp object that controls the screens and part of the program */
   private PokerApp myApp;
     
   /**
   default constructor of CharacterCreationScreen
   @param app the PokerApp object using the screen
   */
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
   
   /**
   method to initialize the screen when entered
   */
   public void screenInitCheck()
   {
      nameBool = false;
      nameButton = "Click!";
      icon1Selected = false;
      icon2Selected = false;
      icon3Selected = false;
      icon4Selected = false;
   }
   
   /**
   method that draws all the icons 
   @param g2 the graphics object
   */
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
   
   /**
   paints the major components of the screen
   @param g graphics object
   */
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
   
   /**
   draws regions that holds the icons
   @return array holding all the regions that hold the icons
   */
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
   
   /**
   helper method for mousehandler to set the user's icon
   @param string user's new icon
   */
   public void mousePressedHelper(String string)
   {
      myApp.getUser().setIcon(string);
      repaint();
   }
   
   /**
   helper method that handles the JOptionPane for entering name
   */
   public void nameBoxHelper()
   {
      JFrame frame = new JFrame("Message Box");
      nameButton = JOptionPane.showInputDialog(frame, 
         "Enter you name", "Your Name?", JOptionPane.WARNING_MESSAGE);
      repaint();
      myApp.getUser().setName(nameButton);
      if(nameButton != null)  {  nameBool = true;  }
   }
   
   /**
   helper method that helps set the right boolean for iconSelected variables
   @param num index of icon to select
   */
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
   
   /**
   helper method that handles switching screens
   */
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
      handles when mouse button enters
      @param e info about the mouse event
      */
      public void mouseEntered(MouseEvent e) { }
      
      /**
      handles when mouse button exits
      @param e info about the mouse event
      */
      public void mouseExited(MouseEvent e) { }
   }
}