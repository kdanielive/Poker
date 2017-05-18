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
   
   private boolean drawBetInstructBool = false;
   private boolean drawRaiseInstructBool = false;
   private boolean drawVictoryBool = false;
   private boolean drawLossBool = false;
   
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
      
      user.setName("Danny");
      
      game = new PokerGame();
      game.listPlayers(user, new AIPlayer(), new AIPlayer(), new AIPlayer(), new AIPlayer());

      addMouseListener(new MouseHandler());
      addKeyListener(new MyKeyListener());
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
      if(drawBetInstructBool == true)
      {
         drawBetInstruct(g);
      }
      if(drawRaiseInstructBool == true)
      {
         drawRaiseInstruct(g);
      }
      if(drawVictoryBool == true)
      {
         drawVictoryInstruct(g);
      }
      if(drawLossBool == true)
      {
         drawLossInstruct(g);
      }
      drawBettingSituation(g);
   }
   
   public void drawBettingSituation(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawString("Last Bet: " + (PokerGame.lastBetAmount 
         - PokerGame.players[PokerGame.turnId % 5].getBettedAmount()), 50, 750);
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
            g2.drawString(holeCards[0].getName(), 500, 730);
            g2.drawString(holeCards[1].getName(), 600, 730);
         }
      }
   }
   
   public void drawCommunityCards(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      ArrayList<PokerCard> communityCards = PokerGame.communityCards;
      
      if(PokerGame.round == 1)
      {
         for(int idx = 0; idx < 3; idx++)
         {
            PokerGame.commCardFlipBool[idx] = true;
         }
      }
      else if(PokerGame.round == 2)
      {
         for(int idx = 0; idx < 4; idx++)
         {
            PokerGame.commCardFlipBool[idx] = true;
         }
      }
      else if(PokerGame.round == 3)
      {
         for(int idx = 0; idx < 5; idx++)
         {
            PokerGame.commCardFlipBool[idx] = true;
         }
      }     
      for(int idx = 0; idx < 5; idx++)
      {
         if(PokerGame.commCardFlipBool[idx] == true)
         {
            g2.drawImage(communityCards.get(idx).getImage(), 350 + idx * 100, 300, null);
            g2.setColor(Color.RED);
            g2.drawString(communityCards.get(idx).getName(), 350 + idx * 100, 460);
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
      
      if(PokerGame.turn != 0)
      {
         try
         {
            dealerButton = ImageIO.read(new File("./DealerButton.png"));
            smallBlindButton = ImageIO.read(new File("./SmallBlindButton.png"));
            bigBlindButton = ImageIO.read(new File("./BigBlindButton.png"));
            
            BufferedImage[] buttonImages = {dealerButton, smallBlindButton, bigBlindButton};
            
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
   }
   
   public void drawBetInstruct(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.drawString("Press B after entering your desired betting amount through keyboard", 50, 50);
   }
   
   public void drawRaiseInstruct(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.drawString("Press B after entering your desired betting amount through keyboard", 50, 50);
      g2.drawString("Your desired betting amount must be higher than the Last Bet amount.", 50, 70);
   }
   
   public void drawVictoryInstruct(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.drawString("Congratulations! You won this round!", 50, 50);
      g2.drawString("Click on Proceed, then Fold to give another round a go.", 50, 70);
   }
   
   public void drawLossInstruct(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.drawString("Too bad! You lose this round!", 50, 50);
      g2.drawString("Click on Proceed, then Fold to give another round a go.", 50, 70);   
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
         PokerGame.players[PokerGame.betters[1]].setFirstBetterBool(true);
         PokerGame.turn++;
         button1 = "Go";
         button2 = "Through";
         button3 = "Blind";
         drawVictoryBool = false;
         repaint();
      }
      else if(button1.equals("Go"))
      {
         PokerGame.players[PokerGame.betters[1]].bet(5, false);
         PokerGame.players[PokerGame.betters[2]].bet(10, false);
         PokerGame.players[PokerGame.betters[1]].setBettedAmount(0);
         PokerGame.players[PokerGame.betters[2]].setBettedAmount(0);

         button1 = "Proceed";
         button2 = "";
         button3 = "";
         repaint();
      }
      else if(button1.equals("Proceed"))
      {
         PokerGame.turn++;
         boolean allCheck = false;
         drawLossBool = false;
         int count = 0;
         while(allCheck == false)
         {
            System.out.println("yo");
            if(PokerGame.turnId % 5 == 0)
            {
               // Remember to disable this and implement it in other methods!
               System.out.println("equals Danny");
               PokerGame.turnId++;
               userMoveTurn();
               System.out.println(PokerGame.checkList[0]);
               if(PokerGame.checkList[0] == false)  {  break;   }
               else  {  ;  }
            }
            else
            {
               PokerGame.players[PokerGame.turnId % 5].playMove();
               PokerGame.turnId++;
            }
            
            allCheck = true;
            for(boolean checker : PokerGame.checkList)
            {
               if(checker == false)
               {
                  allCheck = false;
               }
            }
         }
         
         allCheck = true;
         for(boolean checker : PokerGame.checkList)
         {
            if(checker == false)
            {
               allCheck = false;
            }
         }
         
         if(allCheck == true && PokerGame.round != 3)
         {
            finishRound();
         }
         
         if(allCheck == true && PokerGame.round == 3)
         {
            commenceShowDown();
         }
         
         repaint();
      }
      
      else if(button1.equals("Bet"))
      {
         drawBetInstructBool = true;
         PokerGame.players[0].setFirstBetterBool(false);
         repaint();
      }
      else if(button1.equals("Check"))
      {
         PokerGame.checkList[0] = true;
         button1 = "Proceed";
         button2 = "";
         button3 = "";
         repaint();
      }
      else if(button1.equals("Call"))
      {
         PokerGame.players[0].bet(PokerGame.lastBetAmount - PokerGame.players[0].getBettedAmount(), false);
         button1 = "Proceed";
         button2 = "";
         button3 = "";
         repaint();
      }
   }
   
   public void buttonTwoPressed()
   {
      if(button2.equals("Through"))
      {
         PokerGame.players[PokerGame.betters[1]].bet(5, false);
         PokerGame.players[PokerGame.betters[2]].bet(10, false);
         PokerGame.players[PokerGame.betters[1]].setBettedAmount(0);
         PokerGame.players[PokerGame.betters[2]].setBettedAmount(0);
         button1 = "Start";
         button2 = "";
         button3 = "";
         repaint();
      }
      
      else if(button2.equals("Raise"))
      {
         drawRaiseInstructBool = true;
         repaint();
      }
   }
   
   public void buttonThreePressed()
   {
      if(button3.equals("Blind"))
      {
         PokerGame.players[PokerGame.betters[1]].bet(5, false);
         PokerGame.players[PokerGame.betters[2]].bet(10, false);
         PokerGame.players[PokerGame.betters[1]].setBettedAmount(0);
         PokerGame.players[PokerGame.betters[2]].setBettedAmount(0);
         button1 = "Start";
         button2 = "";
         button3 = "";
         repaint();
      }
      else if(button3.equals("Fold"))
      {
         resetGame();
         button1 = "Start Game";
         button2 = "";
         button3 = "End Game";
         repaint();
      }
   }
   
   public void userMoveTurn()
   {
      if(PokerGame.players[0].getFirstBetterBool() == true)
      {
         button1 = "Bet";
         button2 = "";
         button3 = "Fold";
      }
      else if(PokerGame.lastBetAmount - PokerGame.players[0].getBettedAmount() == 0)
      {
         button1 = "Check";
         button2 = "Raise";
         button3 = "Fold";
      }
      else
      {
         button1 = "Call";
         button2 = "Raise";
         button3 = "Fold";
      }
   }
   
   
   private class MyKeyListener implements KeyListener
   {
      public void keyPressed(KeyEvent e)
      {
         int code = e.getKeyCode();
         
         if(code == KeyEvent.VK_1)
         {
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 1;
         }
         if(code == KeyEvent.VK_2)
         {            
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 2;
         }
         if(code == KeyEvent.VK_3)
         {            
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 3;
         }
         if(code == KeyEvent.VK_4)
         {            
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 4;
         }
         if(code == KeyEvent.VK_5)
         {            
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 5;
         }
         if(code == KeyEvent.VK_6)
         {            
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 6;
         }
         if(code == KeyEvent.VK_7)
         {
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 7;
         }
         if(code == KeyEvent.VK_8)
         {
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 8;
         }
         if(code == KeyEvent.VK_9)
         {
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10 + 9;
         }
         if(code == KeyEvent.VK_0)
         {
            PokerGame.userDesiredAmt = PokerGame.userDesiredAmt * 10;
         }
         if(code == KeyEvent.VK_B)
         {
            PokerGame.players[0].bet(PokerGame.userDesiredAmt, true);
            PokerGame.userDesiredAmt = 0;
            button1 = "Proceed";
            button2 = "";
            button3 = "";
            drawBetInstructBool = false;
            drawRaiseInstructBool = false;
            
            if(PokerGame.round == 0)   
            {   
               PokerGame.checkList[0] = true;  
            }
            
            repaint();
         }
      }
      
      public void keyReleased(KeyEvent e)
      {
      
      }
      
      public void keyTyped(KeyEvent e)
      {
      
      } 
   }
   
   public void commenceShowDown()
   {
      CompareHandler comparier = new CompareHandler(PokerGame.communityCards);
      ArrayList<Integer> numArray = new ArrayList<Integer>();
      for(Player ppl : PokerGame.players)
      {
         ArrayList<PokerCard> showDownHand = new ArrayList<PokerCard>();
         for(PokerCard card : PokerGame.communityCards)
         {
            showDownHand.add(card);
         }
         for(PokerCard card : ppl.getHoleCards())
         {
            showDownHand.add(card);
         }
         numArray.add(comparier.checkCompo(showDownHand));
      }
      CompareHandler.intInsertionSort(numArray);
      ArrayList<PokerCard> showDownHand = new ArrayList<PokerCard>();
      for(PokerCard card : PokerGame.communityCards)
      {
         showDownHand.add(card);
      }
      for(PokerCard card : PokerGame.players[0].getHoleCards())
      {
         showDownHand.add(card);
      }
      if(numArray.get(4) == comparier.checkCompo(showDownHand))
      {
         victory();
      }
      else
      {
         loss();
      }
   }
   
   public void finishRound()
   {
      PokerGame.lastBetAmount = 0;
      for(Player player : PokerGame.players)
      {
         player.setBettedAmount(0);
      }
      game.setBetter();
      for(int idx = 0; idx < 5; idx++)
      {
         PokerGame.checkList[idx] = false;
      }
      for(int idx = 0; idx < 5; idx++)
      {
         PokerGame.commCardFlipBool[idx] = false;
      }
      PokerGame.round++;
      
      button1 = "Proceed";
      button2 = "";
      button3 = "";
   }
   
   public void resetGame()
   {
      finishRound();
      PokerGame.deck = new Deck();
      PokerGame.moneyOnTable = 0;
      PokerGame.turn = 0;
      PokerGame.lastBetAmount = 0;
      PokerGame.turnId = 0;
      PokerGame.round = 0;
      game.distributeCards();
      game.setBetter();
      for(int idx = 0; idx < 5; idx++)
      {
         PokerGame.checkList[idx] = false;
      }
      for(Player player : PokerGame.players)
      {
         player.setBettedAmount(0);
      }
   }
   
   
   public void victory()
   {
      PokerGame.players[0].modifyFinance(PokerGame.players[0].getFinance() + PokerGame.moneyOnTable);
      resetGame();
      drawVictoryBool = true;
      repaint();
   }
   
   public void loss()
   {
      resetGame();
      drawLossBool = true;
      repaint();
   }
}  