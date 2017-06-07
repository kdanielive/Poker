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
   /** lowest casino limit */
   private final int LOWBAR = 1000;
   /** second lowest casino limit */
   private final int MEDIUMBAR = 5000;
   /** highest casino limit */
   private final int HIGHBAR = 10000;
   /** x-coord of entry text */
   private final int ENTRY_X = 1050;
   /** y-coord of entry text */
   private final int ENTRY_Y = 145;
   /** height of entry text */
   private final int ENTRY_HEIGHT = 45;
   /** x-coord of entry  box */
   private final int ENTRYBOX_X = 1045;
   /** y-coord of entry  box */
   private final int ENTRYBOX_Y = 125;
   /** width of entry  box */
   private final int ENTRYBOX_WIDTH = 53;
   /** height of entry  box */
   private final int ENTRYBOX_HEIGHT = 23;
   /** spacing between entry  boxes */
   private final int ENTRYBOX_SPACING = 45;
   /** x-coord of finance text */
   private final int FINANCE_X1 = 950;
   /** y-coord of finance text */
   private final int FINANCE_Y = 100;
   /** font size 1 */
   private final int FONT_SIZE1 = 20;
   /** second x-coord of finance text */
   private final int FINANCE_X2 = 1030;
   /** font size 2 */
   private final int FONT_SIZE2 = 23;
   private final int CASINO_X = 650;
   private final int CASINO_Y = 145;
   private final int CASINO_H = 45;
   private final int ITINERARY_X = 800;
   private final int ITINERARY_Y = 115;
   /** font size 3 */
   private final int FONT_SIZE3 = 35;
   private final int NAME_X = 125;
   private final int NAME_Y = 30;
   private final int CARD_X = 1070;
   private final int CARD_Y = 540;
   private final int CARD_W = 130;
   private final int CARD_H = 260;
   private final int NOTE_X = 100;
   private final int NOTE_Y = 60;
   private final int JKCARD_Y = 500;
   private final int JKCARD_X1 = 650;
   private final int JKCARD_X2 = 850;
   private final int LIST_X = 630;
   private final int LIST_Y = 70;
   private final int INSTRUCTION_X = 260;
   private final int INSTRUCTION_Y = 100;
   private final int MSG_X = 150;
   private final int MSG_Y = 130;
   private final int MSG_H = 30;
   
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
      g2.setFont(new Font("Times New Roman", Font.BOLD, FONT_SIZE3));
      g2.setColor(Color.RED);
      g2.drawString(myApp.getUser().getName(),NAME_X, NAME_Y);
      
      g2.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE1));
      g2.setColor(Color.BLACK);
   }
   
   /**
   draws buffered images
   @param g2 graphics object
   */
   public void drawImages(Graphics2D g2)
   {
      g2.drawImage(woodBackgroundImage, 0, 0, null);
      g2.drawImage(exitCardImage, CARD_X, CARD_Y, null);
      g2.drawImage(vintageNoteImage, NOTE_X, NOTE_Y, null);
      g2.drawImage(joker1Image, JKCARD_X1, JKCARD_Y, null);
      g2.drawImage(joker2Image, JKCARD_X2, JKCARD_Y, null);
      g2.drawImage(casinoListImage, LIST_X, LIST_Y, null);
   }
   
   /**
   writes instructions and story of the game
   @param g2 graphics object
   */
   public void writeStory(Graphics2D g2)
   {
      g2.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE2));
      g2.setColor(Color.WHITE);
      g2.drawString("Instructions", INSTRUCTION_X, INSTRUCTION_Y);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE1));
      g2.drawString("Never forget.", MSG_X, MSG_Y);
      g2.drawString("Your father died at THE CASINO", MSG_X, MSG_Y + MSG_H);
      g2.drawString("Retrace his steps", MSG_X, MSG_Y + MSG_H * 2);
      g2.drawString("Earn money in these casinos.", MSG_X, MSG_Y + MSG_H * 3);
      g2.drawString("They all play limit Texas Holdem,", MSG_X, MSG_Y + MSG_H * 4);
      g2.drawString(" as is the rule in this area.", MSG_X, MSG_Y + MSG_H * 5);
      g2.drawString("Minimum bet for pre-flop and flop are 20,", MSG_X, MSG_Y + MSG_H * 6);
      g2.drawString("while it's 40 for turn and river rounds.", MSG_X, MSG_Y + MSG_H * 7);
      g2.drawString("You'll find some casinos very selective...", MSG_X, MSG_Y + MSG_H * 8);
      g2.drawString("You'll need more money for such casinos.", MSG_X, MSG_Y + MSG_H * 9);
      g2.drawString("Oh, and you know what the custom is. ", MSG_X, MSG_Y + MSG_H * 10);
      g2.drawString("Run out of money, you get killed.", MSG_X, MSG_Y + MSG_H * 11);
      g2.drawString("", MSG_X, MSG_Y + MSG_H * 12);
      g2.drawString("", MSG_X, MSG_Y + MSG_H * 13);
      g2.drawString("", MSG_X, MSG_Y + MSG_H * 14);
      g2.drawString("To THE CASINO then...", MSG_X, MSG_Y + MSG_H * 15); 
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
         
         Rectangle2D.Double exitBox = new Rectangle2D.Double(CARD_X, CARD_Y, CARD_W, CARD_H);
         Rectangle2D.Double[] entryBoxes = new Rectangle2D.Double[7];
         for(int idx = 0; idx < 7; idx++)
         {
            Rectangle2D.Double tempBox = new Rectangle2D.Double(
               ENTRYBOX_X, ENTRYBOX_Y + idx * ENTRY_HEIGHT,
               ENTRYBOX_WIDTH, ENTRYBOX_HEIGHT);
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