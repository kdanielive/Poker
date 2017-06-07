import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.text.AttributedString;
import java.awt.font.TextAttribute;
import java.awt.Font;

/**
class for the lobby screen of the game
@author Daniel Kim
@version 04/11/2017
*/
public class LobbyScreen extends JPanel
{
   private final int LOWBAR = 1000;
   private final int MEDIUMBAR = 5000;
   private final int HIGHBAR = 10000;
   private final int ENTRY_X = 1050;
   private final int ENTRY_Y = 145;
   private final int ENTRY_HEIGHT = 45;
   private final int ENTRYBOX_X = 1045;
   private final int ENTRYBOX_Y = 125;
   private final int ENTRYBOX_WIDTH = 53;
   private final int ENTRYBOX_HEIGHT = 23;
   private final int ENTRYBOX_SPACING = 45;
   private final int FINANCE_X1 = 950;
   private final int FINANCE_Y = 100;
   private final int FONT_SIZE1 = 20;
   private final int FINANCE_X2 = 1030;
   private final int FONT_SIZE2 = 23;
   private final int CASINO_X = 650;
   private final int CASINO_Y = 145;
   private final int CASINO_H = 45;
   private final int ITINERARY_X = 800;
   private final int ITINERARY_Y = 115;
   
   /** PokerApp object that controls the screens and part of the program */
   private PokerApp myApp;
   /** background image */
   private BufferedImage woodBackgroundImage;
   /** exit card image */
   private BufferedImage exitCardImage;
   /** image of a vintage paper */
   private BufferedImage vintageNoteImage;
   /** first joker card image */
   private BufferedImage joker1Image;
   /** second joker card image */
   private BufferedImage joker2Image;
   /** image to contain a list of casinos */
   private BufferedImage casinoListImage;
   
   /** list indicating authorized casinos */
   boolean[] authorizedList = {false, false, false, false, false, false, false};
   
   /**
   default constuctor of LobbyScreen class
   @param app the PokerApp object using the screen
   */
   public LobbyScreen(PokerApp app)
   {
      myApp = app;
      setAuthorizedList();
      try
      {
         InputStream is = getClass().getResourceAsStream("woodBackground.jpg");
         woodBackgroundImage = ImageIO.read(is);
         is = getClass().getResourceAsStream("exitCard.png");
         exitCardImage = ImageIO.read(is);
         is = getClass().getResourceAsStream("vintageNote.jpg");
         vintageNoteImage = ImageIO.read(is);
         is = getClass().getResourceAsStream("joker1.jpg");
         joker1Image = ImageIO.read(is);
         is = getClass().getResourceAsStream("joker2.jpg");
         joker2Image = ImageIO.read(is);
         is = getClass().getResourceAsStream("casinoList.jpg");
         casinoListImage = ImageIO.read(is);
      }
      catch(IOException ioe)  {  }
      addMouseListener(new MouseHandler());
      requestFocusInWindow();
   }
   
   /**
   method to initialize the screen when entered
   */
   public void screenInitCheck()
   {
      setAuthorizedList();
      checkDeadOrAlive();
   }
   
   /**
   sets the authorized list of casinos
   */
   public void setAuthorizedList()
   {
      authorizedList[0] = true;
      authorizedList[1] = true;
      if(myApp.getUser().getFinance() > LOWBAR)
      {
         authorizedList[2] = true;
         authorizedList[3] = true;
      }
      if(myApp.getUser().getFinance() > MEDIUMBAR)
      {
         authorizedList[4] = true;
         authorizedList[5] = true;
      }
      if(myApp.getUser().getFinance() > HIGHBAR) {  authorizedList[6] = true;  }
   }
   
   /**
   checks whether the user is dead or alive
   */
   public void checkDeadOrAlive()
   {
      if(myApp.getUser().getFinance() < 0)
      {
         JFrame frame = new JFrame("Message Box");
         JOptionPane.showConfirmDialog(frame, "No more money, that means you're dead.",
             "You're DEAD", -1);
         myApp.resetUser();
         myApp.switchScreen("Main");
      }
   }
   
   /**
   paints the major components of the screen
   @param g graphics object
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      drawImages(g2);
      drawComponents(g2);
      
      for(int idx = 0; idx < 7; idx++)
      {
         if(authorizedList[idx] == true)
         {
            g2.drawString("Enter", ENTRY_X, ENTRY_Y + idx * ENTRY_HEIGHT);
            g2.drawRect(ENTRYBOX_X, ENTRYBOX_Y + idx * ENTRYBOX_SPACING
               , ENTRYBOX_WIDTH, ENTRYBOX_HEIGHT);
         }
         else
         {
            g2.setColor(Color.RED);
            g2.drawString("Enter", ENTRY_X, ENTRY_Y + idx * ENTRY_HEIGHT);
            g2.drawRect(ENTRYBOX_X, ENTRYBOX_Y + idx * ENTRYBOX_SPACING
               , ENTRYBOX_WIDTH, ENTRYBOX_HEIGHT);
            g2.setColor(Color.BLACK);
         }
      }
      writeStory(g2);
   }
   
   /**
   draws other components of the screen
   @param g2 graphics object
   */
   public void drawComponents(Graphics2D g2)
   {
      g2.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE1));
      g2.drawString("Finance: ", FINANCE_X1, FINANCE_Y);
      g2.drawString("" + myApp.getUser().getFinance(), FINANCE_X2, FINANCE_Y);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE2));
      g2.drawString("Itinerary", ITINERARY_X, ITINERARY_Y);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE1));
      g2.drawString("Gaunt Casino", CASINO_X, CASINO_Y);
      g2.drawString("Lotus Casino", CASINO_X, CASINO_Y + CASINO_H);
      g2.drawString("Hallowed Casino", CASINO_X, CASINO_Y + 2 * CASINO_H);
      g2.drawString("Mourn Casino", CASINO_X, CASINO_Y + 3 * CASINO_H);
      g2.drawString("Low Casino", CASINO_X, CASINO_Y + 4 * CASINO_H);
      g2.drawString("Royale Casino", CASINO_X, CASINO_Y + 5 * CASINO_H);
      g2.drawString("The Casino", CASINO_X, CASINO_Y + 6 * CASINO_H);
      
      myApp.getUser().drawMe(g2, 0, 0);
      g2.setFont(new Font("Times New Roman", Font.BOLD, 35));
      g2.setColor(Color.RED);
      g2.drawString(myApp.getUser().getName(),125, 30);
      
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      g2.setColor(Color.BLACK);
   }
   
   /**
   draws buffered images
   @param g2 graphics object
   */
   public void drawImages(Graphics2D g2)
   {
      g2.drawImage(woodBackgroundImage, 0, 0, null);
      g2.drawImage(exitCardImage, 1070, 540, null);
      g2.drawImage(vintageNoteImage, 100, 60, null);
      g2.drawImage(joker1Image, 650, 500, null);
      g2.drawImage(joker2Image, 850, 500, null);
      g2.drawImage(casinoListImage, 630, 70, null);
   }
   
   /**
   writes instructions and story of the game
   @param g2 graphics object
   */
   public void writeStory(Graphics2D g2)
   {
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 23));
      g2.setColor(Color.WHITE);
      g2.drawString("Instructions", 260, 100);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      g2.drawString("Never forget.", 150, 130);
      g2.drawString("Your father died at THE CASINO", 150, 160);
      g2.drawString("Retrace his steps", 150, 190);
      g2.drawString("Earn money in these casinos.", 150, 220);
      g2.drawString("They all play limit Texas Holdem,", 150, 250);
      g2.drawString(" as is the rule in this area.", 150, 280);
      g2.drawString("Minimum bet for pre-flop and flop are 20,", 150, 310);
      g2.drawString("while it's 40 for turn and river rounds.", 150, 340);
      g2.drawString("You'll find some casinos very selective...", 150, 370);
      g2.drawString("You'll need more money for such casinos.", 150, 400);
      g2.drawString("Oh, and you know what the custom is. ", 150, 430);
      g2.drawString("Run out of money, you get killed.", 150, 460);
      g2.drawString("", 150, 490);
      g2.drawString("", 150, 520);
      g2.drawString("", 150, 550);
      g2.drawString("To THE CASINO then...", 150, 580); 
   }
   
   /**
   helper method that handles exiting to main screen
   */
   public void exitBoxHelper()
   {
      JFrame frame = new JFrame("Message Box");
      int choice = JOptionPane.showConfirmDialog(frame, "Exit game?");
      if(choice == 0)   
      {  
         myApp.switchScreen("Main");
         myApp.resetUser();
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
         
         Rectangle2D.Double exitBox = new Rectangle2D.Double(1070, 540, 130, 260);
         Rectangle2D.Double[] entryBoxes = new Rectangle2D.Double[7];
         for(int idx = 0; idx < 7; idx++)
         {
            Rectangle2D.Double tempBox = new Rectangle2D.Double(1045, 125 + idx * 45, 53, 23);
            entryBoxes[idx] = (tempBox);
         }
         if(exitBox.contains(clickX, clickY))
         {
            exitBoxHelper();
         }
         for(int idx = 0; idx < 7; idx++)
         {
            if(entryBoxes[idx].contains(clickX, clickY) 
               && authorizedList[idx] == true)
            {
               myApp.switchScreen("PokerTable");
            }
            else if(entryBoxes[idx].contains(clickX, clickY))
            {
               JFrame frame = new JFrame("Message Box");
               JOptionPane.showConfirmDialog(frame, "Not enough money", "Lacking Finance", -1);
            }
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