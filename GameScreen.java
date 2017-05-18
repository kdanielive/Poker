import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class GameScreen extends JPanel
{
   /** array of players in the game */
   private Player user = new Player();
   
   private BufferedImage pokerTableImage;
      
   private PokerApp myApp;
   
   private String button1;
   private String button2;
   private String button3;
   
   private PokerGame game;
   
   public GameScreen(PokerApp app)
   {
      myApp = app; 
      
      try
      {
         InputStream is = getClass().getResourceAsStream("PokerDesk.jpg");
         pokerTableImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
      button1 = "Start Game";
      button2 = "";
      button3 = "End Game";
      
      game = new PokerGame();
      game.listPlayers(user, new AIPlayer(), new AIPlayer(), new AIPlayer(), new AIPlayer());

      addMouseListener(new MouseHandler());
      requestFocusInWindow();
   }
   
   /**
   draws the panel
   @param g the graphics handler
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawImage(pokerTableImage, 250, 200, null);
      
      PokerGame.players[2].drawMe(g2, 200, 100);
      PokerGame.players[3].drawMe(g2, 875, 100);
      PokerGame.players[1].drawMe(g2, 200, 500);
      PokerGame.players[4].drawMe(g2, 875, 500);
      PokerGame.players[0].drawMe(g2, 400, 600);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      g2.drawString("You", 400, 720);
      
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
      g2.setColor(Color.LIGHT_GRAY);
      g2.draw3DRect(900, 650, 100, 150, true);
      g2.draw3DRect(1000, 650, 100, 150, true);
      g2.draw3DRect(1100, 650, 100, 150, true);
      g2.setColor(Color.BLACK);
      g2.drawString(button1, 930, 710);
      g2.drawString(button2, 1025, 710);
      g2.drawString(button3, 1120, 710);
      
      drawHoleCards(g);
      drawCommunityCards(g);
      drawMoneyOnTable(g);
      drawButtons(g);
      drawMyFinance(g);
   }
   
   public void drawHoleCards(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      PokerCard[] holeCards = new PokerCard[2];
      holeCards = PokerGame.players[0].getHoleCards();
      
      if(PokerGame.receivedHoleCard == true)
      {
         if(PokerGame.turn == 1)
         {
            try
            {
               BufferedImage backSideImage = ImageIO.read(new File("./PokerCardImages/BackSide.png"));
               g2.drawImage(backSideImage, 500, 570, null);
               g2.drawImage(backSideImage, 600, 570, null);
            }
            catch(IOException ioe)  {  }
         }
         else
         {
            g2.drawImage(holeCards[0].getImage(), 500, 570, null);
            g2.drawImage(holeCards[1].getImage(), 600, 570, null);
            System.out.println(holeCards[0].getName());
            System.out.println(holeCards[1].getName());
         }
      }
   }
   
   public void drawCommunityCards(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      ArrayList<PokerCard> communityCards = PokerGame.communityCards;
      
      for(int idx = 0; idx < 5; idx++)
      {
         if(PokerGame.commCardFlipBool[idx] == true)
         {
            g2.drawImage(communityCards.get(idx).getImage(), 350 + idx * 100, 300, null);
         }
      }
   }
   
   public void drawMoneyOnTable(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.setColor(Color.WHITE);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
      g2.drawString("Total: " + PokerGame.moneyOnTable, 540, 535);
   }
   
   public void drawMyFinance(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.BLACK);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
      g2.drawString("Finance: " + PokerGame.players[0].getFinance(), 1000, 50);
   }
   
   public void drawButtons(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      BufferedImage dealerButton;
      BufferedImage smallBlindButton;
      BufferedImage bigBlindButton;
      
      try
      {
         dealerButton = ImageIO.read(new File("./DealerButton.png"));
         smallBlindButton = ImageIO.read(new File("./SmallBlindButton.png"));
         bigBlindButton = ImageIO.read(new File("./BigBlindButton.png"));
         
         BufferedImage[] buttonImages = {dealerButton, smallBlindButton, bigBlindButton};
         
         for(int num : PokerGame.betters)
         {
            System.out.println(num);
         }
         for(int idx = 0; idx < 3; idx++)
         {
            switch(PokerGame.betters[idx])
            {
               case 0:
                  g2.drawImage(buttonImages[idx], 300, 600, null);
                  break;
               case 1:
                  g2.drawImage(buttonImages[idx], 100, 500, null);
                  break;
               case 2:
                  g2.drawImage(buttonImages[idx], 100, 100, null);
                  break;
               case 3:
                  g2.drawImage(buttonImages[idx], 975, 100, null);
                  break;
               case 4:
                  g2.drawImage(buttonImages[idx], 975, 500, null);
                  break;
               default:
                  System.out.println("Wrong dude");
                  break;
            }
         }
      }
      catch(IOException ioe)  {  }   
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
         
         Rectangle2D.Double[] buttons = new Rectangle2D.Double[3];
         buttons[0] = new Rectangle2D.Double(900, 650, 100, 150);
         buttons[1] = new Rectangle2D.Double(1000, 650, 100, 150);
         buttons[2] = new Rectangle2D.Double(1100, 650, 100, 150);
         
         for(int idx = 0; idx < 3; idx++)
         {
            if(buttons[idx].contains(clickX, clickY) && idx == 0)
            {
               buttonOnePressed();
               System.out.println("yeah");
            }
            else if (buttons[idx].contains(clickX, clickY) && idx == 1)
            {
               buttonTwoPressed();
            }
            else if (buttons[idx].contains(clickX, clickY) && idx == 2)
            {
               buttonThreePressed();
            }
         }
      }
      
      public void mouseReleased(MouseEvent e) { }
      
      public void mouseClicked(MouseEvent e) { }
      
      public void mouseEntered(MouseEvent e) { }
      
      public void mouseExited(MouseEvent e) { }
   }
   
   public void buttonOnePressed()
   {
      if(button1.equals("Start Game"))
      {
         game.distributeCards();
         game.setBetter();
         PokerGame.turn++;
         button1 = "Proceed";
         button2 = "Through";
         button3 = "Blind";
         repaint();
      }
      else if(button1.equals("Proceed"))
      {
         PokerGame.players[PokerGame.betters[1]].bet(5);
         PokerGame.players[PokerGame.betters[2]].bet(10);
         repaint();
      }
   }
   
   public void buttonTwoPressed()
   {
      if(button1.equals("Through"))
      {
         PokerGame.players[PokerGame.betters[1]].bet(5);
         PokerGame.players[PokerGame.betters[2]].bet(10);
         repaint();
      }
   
   }
   
   public void buttonThreePressed()
   {
      if(button1.equals("Blind"))
      {
         PokerGame.players[PokerGame.betters[1]].bet(5);
         PokerGame.players[PokerGame.betters[2]].bet(10);
         repaint();
      }
   
   }

}  