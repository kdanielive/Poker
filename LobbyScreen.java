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
      if(PokerApp.user.getFinance() > 10000)
      {
         authorizedList[2] = true;
         authorizedList[3] = true;
      }
      if(PokerApp.user.getFinance() > 100000)
      {
         authorizedList[4] = true;
         authorizedList[5] = true;
      }
      if(PokerApp.user.getFinance() > 500000)
      {
         authorizedList[6] = true;
      }
      
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
      catch(IOException ioe)
      {
      
      }
      
      addMouseListener(new MouseHandler());
      requestFocusInWindow();
   }
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawImage(woodBackgroundImage, 0, 0, null);
      g2.drawImage(exitCardImage, 1070, 590, null);
      g2.drawImage(vintageNoteImage, 100, 60, null);
      g2.drawImage(joker1Image, 650, 500, null);
      g2.drawImage(joker2Image, 850, 500, null);
      g2.drawImage(casinoListImage, 630, 70, null);
      
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
      
      AttributedString as1 = new AttributedString("Enter");
      as1.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, 0, 4);
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
      
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 23));
      g2.setColor(Color.WHITE);
      g2.drawString("Transactions", 260, 100);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));

      g2.drawString("Blah", 150, 130);
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
         
         Rectangle2D.Double exitBox = new Rectangle2D.Double(1070, 590, 128, 128);
         
         if(exitBox.contains(clickX, clickY))
         {
            JFrame frame = new JFrame("Message Box");
            int choice = JOptionPane.showConfirmDialog(frame, "Exit game?");
            if(choice == 0)   {  myApp.switchScreen("Main");   }
         }
      }
      
      public void mouseReleased(MouseEvent e) { }
      
      public void mouseClicked(MouseEvent e) { }
      
      public void mouseEntered(MouseEvent e) { }
      
      public void mouseExited(MouseEvent e) { }
   }
}