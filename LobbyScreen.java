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

public class LobbyScreen extends JPanel
{
   private PokerApp myApp;
   
   BufferedImage woodBackgroundImage;
   BufferedImage exitCardImage;
   BufferedImage vintageNoteImage;
   BufferedImage joker1Image;
   BufferedImage joker2Image;
   BufferedImage casinoListImage;
   
   boolean[] authorizedList = {false, false, false, false, false, false, false};
   
   public LobbyScreen(PokerApp app)
   {
      myApp = app;
      
      authorizedList[0] = true;
      authorizedList[1] = true;
      if(PokerApp.user.getFinance() > 1000)
      {
         authorizedList[2] = true;
         authorizedList[3] = true;
      }
      if(PokerApp.user.getFinance() > 5000)
      {
         authorizedList[4] = true;
         authorizedList[5] = true;
      }
      if(PokerApp.user.getFinance() > 10000) {  authorizedList[6] = true;  }
      
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
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      drawImages(g2);
      drawComponents(g2);
      
      AttributedString as1 = new AttributedString("Enter");
      as1.addAttribute(TextAttribute.STRIKETHROUGH, 
         TextAttribute.STRIKETHROUGH_ON, 0, 4);
      Font font = new Font("Times New Roman", Font.PLAIN, 20);
      as1.addAttribute(TextAttribute.FONT, font);
      
      for(int idx = 0; idx < 7; idx++)
      {
         if(authorizedList[idx] == true)
         {
            g2.drawString("Enter", 1050, 145 + idx * 45);
            g2.drawRect(1045, 125 + idx * 45, 53, 23);
         }
         else
         {
            g2.setColor(Color.RED);
            g2.drawString(as1.getIterator(), 1050, 145 + idx * 45);
            g2.drawRect(1045, 125 + idx * 45, 53, 23);
            g2.setColor(Color.BLACK);
         }
      }
      writeStory(g2);
   }
   
   public void drawComponents(Graphics2D g2)
   {
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      g2.drawString("Finance: ", 950, 100);
      g2.drawString("" + PokerApp.user.getFinance(), 1030, 100);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 23));
      g2.drawString("Itinerary", 800, 115);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      g2.drawString("Gaunt Casino", 650, 145);
      g2.drawString("Lotus Casino", 650, 190);
      g2.drawString("Hallowed Casino", 650, 235);
      g2.drawString("Mourn Casino", 650, 280);
      g2.drawString("Low Casino", 650, 325);
      g2.drawString("Royale Casino", 650, 370);
      g2.drawString("The Casino", 650, 415);
      
      PokerApp.user.drawMe(g2, 0, 0);
      g2.setFont(new Font("Times New Roman", Font.BOLD, 35));
      g2.setColor(Color.RED);
      g2.drawString(PokerApp.user.getName(),125, 30);
      
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      g2.setColor(Color.BLACK);
   }
   
   public void drawImages(Graphics2D g2)
   {
      g2.drawImage(woodBackgroundImage, 0, 0, null);
      g2.drawImage(exitCardImage, 1070, 540, null);
      g2.drawImage(vintageNoteImage, 100, 60, null);
      g2.drawImage(joker1Image, 650, 500, null);
      g2.drawImage(joker2Image, 850, 500, null);
      g2.drawImage(casinoListImage, 630, 70, null);
   }
   
   public void writeStory(Graphics2D g2)
   {
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 23));
      g2.setColor(Color.WHITE);
      g2.drawString("Instructions", 260, 100);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      g2.drawString("So this is the story.", 150, 130);
      g2.drawString("We hold your son in ransom. ", 150, 160);
      g2.drawString("And we require money.", 150, 190);
      g2.drawString("Like, a lot of money.", 150, 220);
      g2.drawString("We know that you're a miserable loser", 150, 250);
      g2.drawString("but luckily, see the casinos nearby?", 150, 280);
      g2.drawString("They all play limit Texas Holdem,", 150, 310);
      g2.drawString(" as is the rule in this area.", 150, 340);
      g2.drawString("Minimum bet for pre-flop and flop are 20,", 150, 370);
      g2.drawString("while it's 40 for turn and river rounds.", 150, 400);
      g2.drawString("You'll find some casinos very selective...", 150, 430);
      g2.drawString("You'll need more money to go into such casinos.", 150, 460);
      g2.drawString("Oh, and you know what the custom is. ", 150, 490);
      g2.drawString("Run out of money, you get killed.", 150, 520);
      g2.drawString("See you at THE CASINO then...", 150, 550);
      g2.drawString("if you can make if that far.", 150, 580); 
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
            JFrame frame = new JFrame("Message Box");
            int choice = JOptionPane.showConfirmDialog(frame, "Exit game?");
            if(choice == 0)   
            {  
               myApp.switchScreen("Main");
               PokerTableScreen.game = new PokerGame();
            }
         }
         
         for(int idx = 0; idx < 7; idx++)
         {
            if(entryBoxes[idx].contains(clickX, clickY) && idx < 2 
               && authorizedList[idx] == true)
            {
               myApp.switchScreen("PokerTable");
            }
            else if (entryBoxes[idx].contains(clickX, clickY) && idx < 4
               && authorizedList[idx] == true)
            {
               myApp.player2.setMode("Medium");
               myApp.player3.setMode("Hard");
               myApp.player4.setMode("Medium");
               myApp.switchScreen("PokerTable");
            }
            else if (entryBoxes[idx].contains(clickX, clickY) && idx < 6
               && authorizedList[idx] == true)
            {
               myApp.player2.setMode("Medium");
               myApp.player3.setMode("Medium");
               myApp.player4.setMode("Hard");
               myApp.player5.setMode("Hard");
               myApp.switchScreen("PokerTable");
            }
            else if (entryBoxes[idx].contains(clickX, clickY) && idx == 7
               && authorizedList[idx] == true)
            {
               myApp.player2.setMode("Hard");
               myApp.player3.setMode("Hard");
               myApp.player4.setMode("Hard");
               myApp.player5.setMode("Hard");
               myApp.switchScreen("PokerTable");
            }
         }
      }
      
      public void mouseReleased(MouseEvent e) { }
      
      public void mouseClicked(MouseEvent e) { }
      
      public void mouseEntered(MouseEvent e) { }
      
      public void mouseExited(MouseEvent e) { }
   }
}