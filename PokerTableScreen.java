import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.Object;
//limit holdem
public class PokerTableScreen extends JPanel
{  
   private BufferedImage pokerTableImage;
      
   private PokerApp myApp;
   
   private JButton button1;
   private JButton button2;
   private JButton button3;
   private JButton turnButton;
   
   private String consoleMessage;
   private String consoleOptionalMsg;
   
   private int freeCounter = 0;
   private String freeString;
   private int turnIndex = 0;
   private ArrayList<Integer> foldedIndex = new ArrayList<Integer>();
   private ArrayList<Integer> betAmtArray= new ArrayList<Integer>();
   private int minBetAmt;
   
   public static PokerGame game = new PokerGame();
   
   public PokerTableScreen(PokerApp app)
   {
      myApp = app;
      
      this.setLayout(null);
      
      for(int idx = 0; idx < 5; idx++) {  betAmtArray.add(0);  }
      
      button1 = new JButton("Press");
      button2 = new JButton("Card");
      button3 = new JButton("on Right");
      JButton[] buttons = {button1, button2, button3};
      turnButton = new JButton("");
      
      try
      {
         InputStream is = getClass().getResourceAsStream("PokerDesk.jpg");
         pokerTableImage = ImageIO.read(is);
         Image img = ImageIO.read(getClass().getResource("nextCard.png"));
         turnButton.setIcon(new ImageIcon(img));
      }
      catch(IOException ioe)
      {
      
      }

      for(int idx = 0; idx < 3; idx++)
      {
         this.add(buttons[idx]);
         buttons[idx].setSize(150, 40);
         buttons[idx].setLocation(920, 660 + idx * 40);
      }
      
      this.add(turnButton);
      turnButton.setSize(128, 186);
      turnButton.setLocation(1070, 590);
      
      button1.addActionListener(new Button1Listener());
      button2.addActionListener(new Button2Listener());
      button3.addActionListener(new Button3Listener());
      turnButton.addActionListener(new TurnButtonListener());
      
      game.listPlayers(PokerApp.user, PokerApp.player2, PokerApp.player3, 
         PokerApp.player4, PokerApp.player5);
      /*for(Player player : game.getPlayerList())
      {
         player.declareGame(game);
      }*/
      
      consoleMessage = "Welcome. Test yourself against these masters in Poker. Press the card"
         + " with arrow to proceed.";
      consoleOptionalMsg = "You will be playing structured limit Texas Holdem. There will be blind bets.";

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
      
      game.getPlayerList().get(2).drawMe(g2, 200, 100);
      game.getPlayerList().get(3).drawMe(g2, 875, 100);
      game.getPlayerList().get(1).drawMe(g2, 200, 500);
      game.getPlayerList().get(4).drawMe(g2, 875, 500);
      game.getPlayerList().get(0).drawMe(g2, 375, 600);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      
      g2.setColor(Color.WHITE);
      g2.fillRect(0, 0, 800, 90);
      g2.setColor(Color.BLACK);
      g2.drawRect(0, 0, 800, 90);
      g2.drawString(consoleMessage, 20, 30);
      g2.drawString(consoleOptionalMsg, 20, 50);
      
      g2.drawString(PokerApp.player3.getName(), 150, 200);
      g2.drawString(PokerApp.player4.getName(), 1025, 200);
      g2.drawString(PokerApp.player2.getName(), 150, 600);
      g2.drawString(PokerApp.player5.getName(), 1025, 550);
      g2.drawString(PokerApp.user.getName(), 350, 750);
      drawHoleCards(g);
      drawCommunityCards(g);
      drawMoneyOnTable(g);
      drawButtons(g);
      //drawMyFinance(g);
   }
   
   public void bet(int raiseAmt)
   {
      minBetAmt += raiseAmt;
      game.getPlayerList().get(turnIndex).minusFinance(minBetAmt - betAmtArray.get(turnIndex));
      game.addToPot(minBetAmt - betAmtArray.get(turnIndex));
      betAmtArray.set(turnIndex, minBetAmt);
      System.out.println(betAmtArray);
   }
   
   public boolean checkRoundFinished()
   {
      if(freeCounter < (6 - foldedIndex.size()))   {  return false;  }
   
      ArrayList<Integer> tempSubject = new ArrayList<Integer>();
      for(int num : betAmtArray)
      {
         tempSubject.add(num);
      }
      for(int num : foldedIndex)
      {
         tempSubject.set(num, -1);
      }
      ArrayList<Integer> compareSubject = new ArrayList<Integer>();
      for(int num: tempSubject)
      {
         if(num != -1)  {  compareSubject.add(num);   }
      }
      
      if(compareSubject.size() == 1)   {
         game.setPhase(-2);
         return true; 
      }
      
      for(int idx = 1; idx < compareSubject.size(); idx++)
      {
         if(compareSubject.get(idx) != compareSubject.get(idx - 1))
         {
            System.out.println("no");
            return false;
         }
      }
      System.out.println("yes");
      return true;
   }
   
   public void initializeTurnIndex()
   {
      if(game.getPlayerList().get(game.getBetters()[0]).equals(PokerApp.player2))
      {
         turnIndex = 1;
      }
      else if(game.getPlayerList().get(game.getBetters()[0]).equals(PokerApp.player3))
      {
         turnIndex = 2;
      }
      else if(game.getPlayerList().get(game.getBetters()[0]).equals(PokerApp.player4))
      {
         turnIndex = 3;
      }
      else if(game.getPlayerList().get(game.getBetters()[0]).equals(PokerApp.player5))
      {
         turnIndex = 4;
      }
      else
      {
         turnIndex = 0;
      }
   }
   
   public void setButtonStrings(String one, String two, String three)
   {
      button1.setText(one);
      button2.setText(two);
      button3.setText(three);
   }
   
   public void drawMoneyOnTable(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      if(game.getPhase() > 0)
      {
         g2.setColor(Color.BLACK);
         g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
         g2.drawString("Amount in Pot: " + game.getPot(), 20, 70);
      }
   }
   
   public void setAIKnownCards()
   {
      PokerApp.player2.setKnownCards(game.getPhase());
      PokerApp.player3.setKnownCards(game.getPhase());
      PokerApp.player4.setKnownCards(game.getPhase());
      PokerApp.player5.setKnownCards(game.getPhase());
      
      for(int idx : foldedIndex)
      {
         AIPlayer temp = (AIPlayer)(game.getPlayerList().get(idx));
         temp.setKnownCards(0);
      }
   }
   
   public void drawButtons(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      BufferedImage dealerButton;
      BufferedImage smallBlindButton;
      BufferedImage bigBlindButton;
      
      if(game.getPhase() != 0)
      {
         try
         {
            dealerButton = ImageIO.read(new File("./DealerIcon.png"));
            smallBlindButton = ImageIO.read(new File("./SmallBlindIcon.png"));
            bigBlindButton = ImageIO.read(new File("./BigBlindIcon.png"));
            
            BufferedImage[] buttonImages = {dealerButton, smallBlindButton, bigBlindButton};
            
            for(int idx = 0; idx < 3; idx++)
            {
               switch(game.getBetters()[idx])
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
                     g2.drawImage(buttonImages[idx], 975, 75, null);
                     break;
                  case 4:
                     g2.drawImage(buttonImages[idx], 975, 450, null);
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

   public void drawHoleCards(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      PokerCard[] holeCards = game.getPlayerList().get(0).getHoleCards();
      
      if(game.getPhase() > 0)
      {
         if(game.getPhase() < 2)
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
      
      ArrayList<PokerCard> communityCards = game.getCommunCards();
      Boolean[] commCardFlipBool = {false, false, false, false, false};
      
      if(game.phase == 3)
      {
         for(int idx = 0; idx < 3; idx++)
         {
            commCardFlipBool[idx] = true;
         }
      }
      else if(game.phase == 4)
      {
         for(int idx = 0; idx < 4; idx++)
         {
            commCardFlipBool[idx] = true;
         }
      }
      else if(game.phase == 5)
      {
         for(int idx = 0; idx < 5; idx++)
         {
            commCardFlipBool[idx] = true;
         }
      }     
      for(int idx = 0; idx < 5; idx++)
      {
         if(commCardFlipBool[idx] == true)
         {
            g2.drawImage(communityCards.get(idx).getImage(), 350 + idx * 100, 300, null);
            g2.setColor(Color.RED);
            g2.drawString(communityCards.get(idx).getName(), 350 + idx * 100, 460);
         }
      }
   }
   /*
   public void drawMyFinance(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.BLACK);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
      g2.drawString("Finance: " + game.players[0].getFinance(), 1000, 50);
   }
   */
      
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
         
      }
      
      public void mouseReleased(MouseEvent e) { }
      
      public void mouseClicked(MouseEvent e) { }
      
      public void mouseEntered(MouseEvent e) { }
      
      public void mouseExited(MouseEvent e) { }
   }

   private class MyKeyListener implements KeyListener
   {
      public void keyPressed(KeyEvent e)
      {
         int code = e.getKeyCode();
         
         if(code == KeyEvent.VK_1) ;

      }
      
      public void keyReleased(KeyEvent e)
      {
      
      }
      
      public void keyTyped(KeyEvent e)
      {
      
      } 
   }
   
   public void endGame()
   {
      freeCounter = 0;
      turnIndex = 0;
      for(int idx = 0; idx < 5; idx++) {  betAmtArray.set(idx, 0);  }
      minBetAmt = 20;
   }
   
   private class TurnButtonListener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {
         if(game.getPhase() == 2 && game.getSubPhase().equals("CommunCards"))
         {
            consoleMessage = "Everyone Folded. You won!";
         }
         else if(game.getPhase() == 3 && game.getSubPhase().equals("CommunCards"))
         {
            initializeTurnIndex();
            turnIndex = (turnIndex + 2) % 5;
            for(int idx = 0; idx < 5; idx++) {  betAmtArray.set(idx, 0);  }
            minBetAmt = 20;
            game.setSubPhase("SecondR");
            repaint();
         }
         else if(game.getPhase() == 4 && game.getSubPhase().equals("CommunCards"))
         {
            initializeTurnIndex();
            turnIndex = (turnIndex + 3) % 5;
            for(int idx = 0; idx < 5; idx++) {  betAmtArray.set(idx, 0);   }
            minBetAmt = 40;
            game.setSubPhase("ThirdR");
            repaint();
         }
         else if(game.getPhase() == 5 && game.getSubPhase().equals("CommunCards"))
         {
            initializeTurnIndex();
            turnIndex = (turnIndex + 3) % 5;
            for(int idx = 0; idx < 5; idx++) {  betAmtArray.set(idx, 0);   }
            minBetAmt = 40;
            game.setSubPhase("FourthR");
            repaint();
         }
         //00000000
         else if(game.getPhase() == 0)
         {
            game.setButton();
            initializeTurnIndex();
            turnIndex = (turnIndex + 1) % 5;
            game.incrementPhase();
            game.setSubPhase("Blind");
            consoleOptionalMsg = "";
            consoleMessage = "Dealer set.";
            repaint();
         }
         //11111111
         else if(game.getPhase() == 1 && game.getSubPhase().equals("Blind"))
         {
            if(freeCounter == 2)
            {
               game.incrementPhase();
               game.setSubPhase("firstR");
               freeCounter = 0;
               game.distributeCards();
               setAIKnownCards();
               consoleMessage = "Hole Cards received.";
               repaint();
            }
            else if(freeCounter == 0)
            {
               game.addToPot(PokerGame.MINBET / 2);
               if(turnIndex == 0)   {  PokerApp.user.minusFinance(PokerGame.MINBET / 2);   }
               consoleMessage = game.getPlayerList().get(turnIndex).getName() + " paid the small blind.";
               freeCounter++;
               betAmtArray.set(turnIndex, PokerGame.MINBET / 2);
               turnIndex = (turnIndex + 1) % 5;
               repaint();
            }
            else if(freeCounter == 1)
            {
               game.addToPot(PokerGame.MINBET);
               consoleMessage = game.getPlayerList().get(turnIndex).getName() + " paid the big blind.";
               freeCounter++;
               if(turnIndex == 0)   {  PokerApp.user.minusFinance(PokerGame.MINBET);   }
               betAmtArray.set(turnIndex, PokerGame.MINBET);
               minBetAmt = PokerGame.MINBET;
               turnIndex = (turnIndex + 1) % 5;
               repaint();
            }
         }
         //222222222222
         else if(game.getPhase() == 2 && game.getSubPhase().equals("firstR"))
         {
            if(checkRoundFinished())
            {
               game.incrementPhase();
               game.setSubPhase("CommunCards");
               consoleMessage = "Flop";
               freeCounter = 0;
               setAIKnownCards();
               repaint();
            }
            else if(turnIndex == 0 && freeCounter == 0)
            {
               setButtonStrings("Check", "Call", "Fold");
               consoleMessage = "Your turn";
               freeString = game.getSubPhase();
               game.setSubPhase("user");
               repaint();
            }
            else if(turnIndex == 0)
            {
               setButtonStrings("Call", "Raise", "Fold");
               consoleMessage = "Your turn";
               freeString = game.getSubPhase();
               game.setSubPhase("user");
               repaint();
            }
            else if(freeCounter == 0)
            {
               AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
               if( subject.easyMove().equals("high"))
               {
                  bet(0);
                  consoleMessage = subject.getName() + " called";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else if( subject.easyMove().equals("flat"))
               {
                  consoleMessage = subject.getName() + " checked";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else
               {
                  consoleMessage = subject.getName() + " folded";
                  foldedIndex.add(turnIndex);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
            }
            else
            {
               AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
               if( subject.easyMove().equals("high"))
               {
                  if(freeCounter < 5)  { bet(20);  }
                  else  {  bet(0);  }
                  consoleMessage = subject.getName() + " raised by 20";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else if( subject.easyMove().equals("flat"))
               {
                  consoleMessage = subject.getName() + " called";
                  bet(0);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else
               {
                  consoleMessage = subject.getName() + " folded";
                  foldedIndex.add(turnIndex);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
            }
         }
         //333333333333333333
         else if(game.getPhase() == 3 && game.getSubPhase().equals("SecondR"))
         {
            if(checkRoundFinished())
            {
               game.incrementPhase();
               game.setSubPhase("CommunCards");
               consoleMessage = "Turn";
               freeCounter = 0;
               setAIKnownCards();
               repaint();
            }
            else if(turnIndex == 0 && freeCounter == 0)
            {
               setButtonStrings("Check", "Call", "Fold");
               consoleMessage = "Your turn";
               freeString = game.getSubPhase();
               game.setSubPhase("user");
               repaint();
            }
            else if(turnIndex == 0)
            {
               setButtonStrings("Call", "Raise", "Fold");
               consoleMessage = "Your turn";
               freeString = game.getSubPhase();
               game.setSubPhase("user");
               repaint();
            }
            else if(freeCounter == 0)
            {
               AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
               System.out.println(subject.getName());
               if( subject.easyMove().equals("high"))
               {
                  bet(0);
                  consoleMessage = subject.getName() + " called";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else if( subject.easyMove().equals("flat"))
               {
                  consoleMessage = subject.getName() + " checked";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else
               {
                  consoleMessage = subject.getName() + " folded";
                  foldedIndex.add(turnIndex);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
            }
            else
            {
               AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
               if( subject.easyMove().equals("high"))
               {
                  bet(20);
                  consoleMessage = subject.getName() + " raised by 20";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else if( subject.easyMove().equals("flat"))
               {
                  consoleMessage = subject.getName() + " called";
                  bet(0);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else
               {
                  consoleMessage = subject.getName() + " folded";
                  foldedIndex.add(turnIndex);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
            }
         }
         //44444444444
         else if(game.getPhase() == 4 && game.getSubPhase().equals("ThirdR"))
         {
            if(checkRoundFinished())
            {
               game.incrementPhase();
               game.setSubPhase("CommunCards");
               consoleMessage = "River";
               freeCounter = 0;
               setAIKnownCards();
               repaint();
            }
            else if(turnIndex == 0 && freeCounter == 0)
            {
               setButtonStrings("Check", "Call", "Fold");
               consoleMessage = "Your turn";
               freeString = game.getSubPhase();
               game.setSubPhase("user");
               repaint();
            }
            else if(turnIndex == 0)
            {
               setButtonStrings("Call", "Raise", "Fold");
               consoleMessage = "Your turn";
               freeString = game.getSubPhase();
               game.setSubPhase("user");
               repaint();
            }
            else if(freeCounter == 0)
            {
               AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
               if( subject.easyMove().equals("high"))
               {
                  bet(0);
                  consoleMessage = subject.getName() + " called";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else if( subject.easyMove().equals("flat"))
               {
                  consoleMessage = subject.getName() + " checked";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else
               {
                  consoleMessage = subject.getName() + " folded";
                  foldedIndex.add(turnIndex);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
            }
            else
            {
               AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
               if( subject.easyMove().equals("high"))
               {
                  if(freeCounter < 5)  { bet(20);  }
                  else  {  bet(0);  }
                  consoleMessage = subject.getName() + " raised by 20";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else if( subject.easyMove().equals("flat"))
               {
                  consoleMessage = subject.getName() + " called";
                  bet(0);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else
               {
                  consoleMessage = subject.getName() + " folded";
                  foldedIndex.add(turnIndex);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
            }
         }
         else if(game.getPhase() == 5 && game.getSubPhase().equals("FourthR"))
         {
            if(checkRoundFinished())
            {
               game.incrementPhase();
               game.setSubPhase("Final");
               consoleMessage = "ShowDown!";
               freeCounter = 0;
               setAIKnownCards();
               repaint();
            }
            else if(turnIndex == 0 && freeCounter == 0)
            {
               setButtonStrings("Check", "Call", "Fold");
               consoleMessage = "Your turn";
               freeString = game.getSubPhase();
               game.setSubPhase("user");
               repaint();
            }
            else if(turnIndex == 0)
            {
               setButtonStrings("Call", "Raise", "Fold");
               consoleMessage = "Your turn";
               freeString = game.getSubPhase();
               game.setSubPhase("user");
               repaint();
            }
            else if(freeCounter == 0)
            {
               AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
               if( subject.easyMove().equals("high"))
               {
                  bet(0);
                  consoleMessage = subject.getName() + " called";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else if( subject.easyMove().equals("flat"))
               {
                  consoleMessage = subject.getName() + " checked";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else
               {
                  consoleMessage = subject.getName() + " folded";
                  foldedIndex.add(turnIndex);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
            }
            else
            {
               AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
               if( subject.easyMove().equals("high"))
               {
                  if(freeCounter < 5)  { bet(20);  }
                  else  {  bet(0);  }
                  consoleMessage = subject.getName() + " raised by 20";
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else if( subject.easyMove().equals("flat"))
               {
                  consoleMessage = subject.getName() + " called";
                  bet(0);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
               else
               {
                  consoleMessage = subject.getName() + " folded";
                  foldedIndex.add(turnIndex);
                  turnIndex = (turnIndex + 1) % 5;
                  freeCounter++;
                  repaint();
               }
            }
         }
      }
   }
   
   private class Button1Listener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {
         if(button1.getText().equals("Call") && game.getSubPhase().equals("user"))
         {
            consoleMessage = "You called";
            bet(0);
            turnIndex = (turnIndex + 1) % 5;
            game.setSubPhase(freeString);
            freeCounter++;
            repaint();
         }
         else if(button1.getText().equals("Check") && game.getSubPhase().equals("user"))
         {
            consoleMessage = "You checked";
            turnIndex = (turnIndex + 1) % 5;
            game.setSubPhase(freeString);
            freeCounter++;
            repaint();
         }
      }
   }

   private class Button2Listener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {
         
      }
   }

   private class Button3Listener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {

      }
   }
}