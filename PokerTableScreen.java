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
import java.util.Timer;
import java.util.TimerTask;
/**
the screen where the poker game is played
@author Daniel Kim
@version 06/01/2017
*/
public class PokerTableScreen extends JPanel
{  
   /** phase number of flop */
   public static final int FLOP = 3;
   /** phase number of turn */
   public static final int TURN = 4;
   /** phase number of river */
   public static final int RIVER = 5;
   
   /** image of poker table */
   private BufferedImage pokerTableImage;
   
   /** PokerApp object the controls the screen */
   private PokerApp myApp;
   
   /** marks how many rounds have passed */
   private int round = 0;
   
   /** first button */
   private JButton button1;
   /** second button */
   private JButton button2;
   /** third button */
   private JButton button3;
   /** end button */
   private JButton endButton;
   /** start button */
   private JButton startButton;
   
   /** initial console message */
   private String consoleMessage = "Welcome. You will be playing structured limit Texas Holdem.";
   /**  second line of initial console message */
   private String consoleOptionalMsg = "There will be blind bets.";
   
   /** number of players participated in round */
   private int expCounter = 0;
   /** counter for various situations */
   private int freeCounter = 0;
   /** string holding variable for various situations */
   private String freeString;
   /** increments with change in freeString variable */
   private int freeStringCount = 0;
   /** index of whose turn it is */
   private int turnIndex = 0;
   /** index of every player who folded */
   private ArrayList<Integer> foldedIndex = new ArrayList<Integer>();
   /** array keeping track of everyone's betted amount */
   private ArrayList<Integer> betAmtArray= new ArrayList<Integer>();
   /** minimum required bet */
   private int minBetAmt;
   /** timer object for running game smoothly */
   private Timer myTimer;
   
   /** PokerGame object that holds all essential data */
   private PokerGame game = new PokerGame();
   
   /**
   default constructor of PokerTableScreen class
   @param app PokerApp object that controls the screen
   */
   public PokerTableScreen(PokerApp app)
   {
      myApp = app;
      
      this.setLayout(null);
      
      for(int idx = 0; idx < 5; idx++) {  betAmtArray.add(0);  }
      addComponents();
      
      try
      {
         InputStream is = getClass().getResourceAsStream("PokerDesk.jpg");
         pokerTableImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
      game.listPlayers(myApp.getUser(), myApp.getAI1(), myApp.getAI2(), 
         myApp.getAI3(), myApp.getAI4());
      
      requestFocusInWindow();
   }
   
   /**
   gets the PokerGame object
   @return the PokerGame object
   */
   public PokerGame getPokerGame()
   {
      return game;
   }
   
   /**
   method to initialize the screen when entered
   */
   public void screenInitCheck()
   {
      game = new PokerGame();
      button1.setText("Read");
      button2.setText("The");
      button3.setText("Console");
      game.listPlayers(myApp.getUser(), myApp.getAI1(), myApp.getAI2(), 
         myApp.getAI3(), myApp.getAI4());
      consoleMessage = "Welcome. You will be playing structured limit Texas Holdem.";
      consoleOptionalMsg = "There will be blind bets.";
   }
   
   /**
   helper method that adds various components of the screen like buttons
   */
   public void addComponents()
   {
      button1 = new JButton("Read");
      button2 = new JButton("The");
      button3 = new JButton("Console");
      JButton[] buttons = {button1, button2, button3};
      endButton = new JButton("Exit"); 
      startButton = new JButton("Start");
      
      for(int idx = 0; idx < 3; idx++)
      {
         this.add(buttons[idx]);
         buttons[idx].setSize(150, 40);
         buttons[idx].setLocation(1050, 610 + idx * 40);
      }
      this.add(startButton);
      startButton.setSize(200, 40);
      startButton.setLocation(580, 50);
      this.add(endButton);
      endButton.setSize(200, 40);
      endButton.setLocation(580, 10); 
      
      button1.addActionListener(new Button1Listener());
      button2.addActionListener(new Button2Listener());
      button3.addActionListener(new Button3Listener());
      startButton.addActionListener(new TurnButtonListener());
      endButton.addActionListener(new RunListener());
   }
   
   /**
   helper method that draws players on screen
   @param g2 graphics object
   */
   public void drawPlayers(Graphics2D g2)
   {
      game.getPlayerList().get(2).drawMe(g2, 200, 100);
      game.getPlayerList().get(3).drawMe(g2, 875, 100);
      game.getPlayerList().get(1).drawMe(g2, 200, 500);
      game.getPlayerList().get(4).drawMe(g2, 875, 500);
      game.getPlayerList().get(0).drawMe(g2, 375, 600);
   }
   
   /**
   helper method that draws the console on screen
   @param g2 graphics object
   */
   public void drawConsole(Graphics2D g2)
   {
      g2.setColor(Color.WHITE);
      g2.fillRect(0, 0, 800, 90);
      g2.setColor(Color.BLACK);
      g2.drawRect(0, 0, 800, 90);
      g2.drawString(consoleMessage, 20, 30);
      g2.drawString(consoleOptionalMsg, 20, 70);
   }
   
   /**
   draws the name of all players
   @param g2 graphics object
   */
   public void drawNames(Graphics2D g2)
   {
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      g2.drawString(myApp.getAI2().getName(), 150, 200);
      g2.drawString(myApp.getAI3().getName(), 1025, 200);
      g2.drawString(myApp.getAI1().getName(), 150, 600);
      g2.drawString(myApp.getAI4().getName(), 975, 525);
      g2.drawString(myApp.getUser().getName(), 300, 710);
   }
   
   /**
   draws the financial situation on screen
   @param g2 graphics object
   */
   public void drawFinance(Graphics2D g2)
   {  
      g2.setColor(Color.BLACK);
      g2.drawString("My Finance: ", 950, 30);
      g2.drawString("" + game.getPlayerList().get(0).getFinance(), 1070, 30);
   }
   
   /**
   draws what first AI did last turn
   @param g2 graphics object
   */
   public void drawAI1Status(Graphics2D g2)
   {
      AIPlayer subject = (AIPlayer) (game.getPlayerList().get(1));
      if(subject.getMoveDescript() != null)
      {
         g2.drawString(subject.getMoveDescript(), 150, 640);
      }
      if(subject.getJustBetAmt() != 0)
      {
         g2.drawString("Bet: " + subject.getJustBetAmt(), 150, 660);
      }
      if(subject.getJustRaisedAmt() != 0)
      {
         g2.drawString("Raised: " + subject.getJustRaisedAmt(), 150, 680);
      }
   }
   
   /**
   draws what second AI did last turn
   @param g2 graphics object
   */
   public void drawAI2Status(Graphics2D g2)
   {
      AIPlayer subject = (AIPlayer) (game.getPlayerList().get(2));
      if(subject.getMoveDescript() != null)
      {
         g2.drawString(subject.getMoveDescript(), 160, 260);
      }
      if(subject.getJustBetAmt() != 0)
      {
         g2.drawString("Bet: " + subject.getJustBetAmt(), 160, 280);
      }
      if(subject.getJustRaisedAmt() != 0)
      {
         g2.drawString("Raised: " + subject.getJustRaisedAmt(), 160, 300);
      }
   }
   
   /**
   draws what third AI did last turn
   @param g2 graphics object
   */
   public void drawAI3Status(Graphics2D g2)
   {
      AIPlayer subject = (AIPlayer) (game.getPlayerList().get(3));
      if(subject.getMoveDescript() != null)
      {
         g2.drawString(subject.getMoveDescript(), 1000, 240);
      }
      if(subject.getJustBetAmt() != 0)
      {
         g2.drawString("Bet: " + subject.getJustBetAmt(), 1000, 260);
      }
      if(subject.getJustRaisedAmt() != 0)
      {
         g2.drawString("Raised: " + subject.getJustRaisedAmt(), 1000, 280);
      }
   }
   
   /**
   draws what fourth AI did last turn
   @param g2 graphics object
   */
   public void drawAI4Status(Graphics2D g2)
   {
      AIPlayer subject = (AIPlayer) (game.getPlayerList().get(4));
      if(subject.getMoveDescript() != null)
      {
         g2.drawString(subject.getMoveDescript(), 1050, 500);
      }
      if(subject.getJustBetAmt() != 0)
      {
         g2.drawString("Bet: " + subject.getJustBetAmt(), 1050, 520);
      }
      if(subject.getJustRaisedAmt() != 0)
      {
         g2.drawString("Raised: " + subject.getJustRaisedAmt(), 1050, 540);
      }
   }
   
   /**
   draws what first AI did last turn
   @param g2 graphics object
   */
   public void drawAIStatus(Graphics2D g2)
   {
      drawAI1Status(g2);
      drawAI2Status(g2);
      drawAI3Status(g2);
      drawAI4Status(g2);
   }
   
   /**
   initializes all AI status records
   */
   public void initAIStatus()
   {
      for(int idx = 1; idx < 5; idx++)
      {
         AIPlayer subject = (AIPlayer) (game.getPlayerList().get(idx));
         subject.setTrinity(null, 0, 0);
      }
   }

   /**
   draws the panel
   @param g the graphics handler
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.setFont(new Font("Times New Roman", Font.BOLD, 20));
      g2.drawImage(pokerTableImage, 250, 200, null);
      drawPlayers(g2);
      drawConsole(g2);
      drawNames(g2);
      drawHoleCards(g);
      drawCommunityCards(g);
      drawMoneyOnTable(g);
      drawButtons(g);
      drawFinance(g2);
      g2.setFont(new Font("Times New Roman", Font.BOLD, 20));
      drawAIStatus(g2);
   }
   
   /**
   method for managaing betting activities of players
   @param raiseAmt amount of money raised, not bet
   */
   public void bet(int raiseAmt)
   {
      if(turnIndex != 0)
      {
         AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
         if(raiseAmt == 0)
         {
            subject.setTrinity("Call", minBetAmt - betAmtArray.get(turnIndex), 0);
         }
         else
         {
            subject.setTrinity("Raise", 0, raiseAmt);
         }
      }
      
      if(turnIndex == 0)
      {
         System.out.println("" + minBetAmt + " - " + betAmtArray.get(turnIndex));
      }
      minBetAmt += raiseAmt;
      game.getPlayerList().get(turnIndex).minusFinance(minBetAmt - betAmtArray.get(turnIndex));
      game.addToPot(minBetAmt - betAmtArray.get(turnIndex));
      betAmtArray.set(turnIndex, minBetAmt);
   }
   
   /**
   checks if the round has come to an end
   @return returns whether the round has ended
   */
   public boolean checkRoundFinished()
   {
      if(expCounter < 5)   {  return false;  }
      if(expCounter == 12) {  return true;   }
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
      if(compareSubject.size() == 1)   
      {
         game.setPhase(-2);
         return true;
      }
      for(int idx = 1; idx < compareSubject.size(); idx++)
      {
         if(compareSubject.get(idx) != compareSubject.get(idx - 1))
         {
            return false;
         }
      }
      return true;
   }
   
   /**
   initializes the turn index
   */
   public void initializeTurnIndex()
   {
      if(game.getPlayerList().get(game.getBetters()[0]).equals(myApp.getAI1()))
      {
         turnIndex = 1;
      }
      else if(game.getPlayerList().get(game.getBetters()[0]).equals(myApp.getAI2()))
      {
         turnIndex = 2;
      }
      else if(game.getPlayerList().get(game.getBetters()[0]).equals(myApp.getAI3()))
      {
         turnIndex = 3;
      }
      else if(game.getPlayerList().get(game.getBetters()[0]).equals(myApp.getAI4()))
      {
         turnIndex = 4;
      }
      else
      {
         turnIndex = 0;
      }
   }
   
   /**
   helper method to set the button text
   @param one text for first button
   @param two text for second button
   @param three text for third button
   */
   public void setButtonStrings(String one, String two, String three)
   {
      button1.setText(one);
      button2.setText(two);
      button3.setText(three);
   }
   
   /**
   draws money in pot
   @param g graphics object
   */
   public void drawMoneyOnTable(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      if(game.getPhase() > 0)
      {
         g2.setColor(Color.GREEN);
         g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
         g2.drawString("Amount in Pot: " + game.getPot(), 520, 535);
      }
   }
   
   /**
   sets the known cards of all AI
   */
   public void setAIKnownCards()
   {
      myApp.getAI1().setKnownCards(game.getPhase(), game);
      myApp.getAI2().setKnownCards(game.getPhase(), game);
      myApp.getAI3().setKnownCards(game.getPhase(), game);
      myApp.getAI4().setKnownCards(game.getPhase(), game);
      
      for(int idx : foldedIndex)
      {
         AIPlayer temp = (AIPlayer)(game.getPlayerList().get(idx));
         temp.setKnownCards(0, game);
      }
   }
   
   /**
   draws buttons on screen
   @param g graphics object
   */
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
               if(game.getBetters()[idx] == 0)
               {
                  g2.drawImage(buttonImages[idx], 300, 600, null);
               }
               else if(game.getBetters()[idx] == 1)
               {
                  g2.drawImage(buttonImages[idx], 100, 500, null);
               }
               else if(game.getBetters()[idx] == 2)
               {
                  g2.drawImage(buttonImages[idx], 100, 100, null);
               }
               else if(game.getBetters()[idx] == 3)
               {
                  g2.drawImage(buttonImages[idx], 975, 75, null);
               }
               else if(game.getBetters()[idx] == 4)
               {
                  g2.drawImage(buttonImages[idx], 975, 425, null);
               }
            }
         } 
         catch(IOException ioe)  {  }   
      }
   }

   /**
   draws the hold cards of the player
   @param g graphics object
   */
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
         }
      }
   }
   
   /**
   draws the community cards on screen
   @param g graphics object
   */
   public void drawCommunityCards(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      ArrayList<PokerCard> communityCards = game.getCommunCards();
      Boolean[] commCardFlipBool = {false, false, false, false, false};
      
      if(game.getPhase() == 3)
      {
         for(int idx = 0; idx < 3; idx++) {  commCardFlipBool[idx] = true; }
      }
      else if(game.getPhase() == 4)
      {
         for(int idx = 0; idx < 4; idx++) {  commCardFlipBool[idx] = true; }
      }
      else if(game.getPhase() == 5)
      {
         for(int idx = 0; idx < 5; idx++) {  commCardFlipBool[idx] = true; }
      }     
      for(int idx = 0; idx < 5; idx++)
      {
         if(commCardFlipBool[idx] == true)
         {
            g2.drawImage(communityCards.get(idx).getImage(), 350 + idx * 100, 300, null);
            g2.setColor(Color.GREEN);
         }
      }
   }
   
   /**
   initializes all variables of the class object
   */
   public void clearData()
   {
      freeCounter = 0;
      expCounter = 0;
      turnIndex = 0;
      round++;
      for(int idx = 0; idx < 5; idx++) {  betAmtArray.set(idx, 0);  }
      minBetAmt = 20;
      game.setPhase(-10);
      game.setSubPhase("End is beginning");
      foldedIndex.clear();
      game.emptyPot();
      consoleOptionalMsg = "Another Round?";
      startButton.setVisible(true);
      initAIStatus();
      if(myTimer != null)  {  myTimer.cancel(); }
   }
   
   /**
   ends the game
   */
   public void endGame()
   {
      if(foldedIndex.size() == 4)  
      {  
         myApp.getUser().plusFinance(game.getPot());
         repaint();  
      }
      else
      {
         CompareHandler comparier = new CompareHandler();
         Player victor = new Player();
         int length = foldedIndex.size();
         int adder = 0;
         ArrayList<Player> contenders = new ArrayList<Player>();
         for(Player player : game.getPlayerList()) {  contenders.add(player); }
         while(length > 0)
         {
            contenders.remove(foldedIndex.remove(0) - adder);
            adder++;
            length--;
         } 
         for(int idx = 1; idx < contenders.size(); idx++)
         {
            victor = comparier.compareHands(contenders.get(idx), contenders.get(idx - 1), game);
         }
         if(victor == null)   {  consoleMessage = "It's a draw!"; }
         consoleMessage = victor.getName() + " wins! Congratulations, " + victor.getName() + " !";
         if(victor.equals(myApp.getUser())) {  myApp.getUser().plusFinance(game.getPot()); }
         repaint();
      }
      clearData();
   }
      
   /**
   helper method to help reset the game before ending
   */
   public void endInitHelper()
   {
      clearData();
      round = 0;
      JFrame frame = new JFrame("Message Box");
      JOptionPane.showConfirmDialog(frame, "Enough Poker for today. Get some rest first.", "Time Out", -1);
      myApp.switchScreen("Lobby");
   }
   
   /**
   helper method to help prepare game phase before flop
   */
   public void flopEntraHelper()
   {
      setAIKnownCards();
      foldedIndex.clear();
      initializeTurnIndex();
      turnIndex = (turnIndex + 1) % 5;
      for(int idx = 0; idx < 5; idx++) {  betAmtArray.set(idx, 0);  }
      minBetAmt = 20;
      game.setSubPhase("SecondR");
      repaint();
   }
   
   /**
   helper method to help prepare game phase before turn
   */
   public void turnEntraHelper()
   {
      setAIKnownCards();
      foldedIndex.clear();
      initializeTurnIndex();
      turnIndex = (turnIndex + 2) % 5;
      for(int idx = 0; idx < 5; idx++) {  betAmtArray.set(idx, 0);   }
      minBetAmt = 40;
      game.setSubPhase("ThirdR");
      repaint();
   }
   
   /**
   helper method to help prepare game phase before river
   */
   public void riverEntraHelper()
   {
      setAIKnownCards();
      foldedIndex.clear();
      initializeTurnIndex();
      turnIndex = (turnIndex + 3) % 5;
      for(int idx = 0; idx < 5; idx++) {  betAmtArray.set(idx, 0);   }
      minBetAmt = 40;
      game.setSubPhase("FourthR");
      repaint();
   }
   
   /**
   helps the prepare game phase before blind
   */
   public void gameEntraHelper()
   {
      game.setRound(round);
      game.setButton();
      game.shuffleDeck();
      initializeTurnIndex();
      turnIndex = (turnIndex + 1) % 5;
      game.incrementPhase();
      game.setSubPhase("Blind");
      consoleOptionalMsg = "";
      consoleMessage = "Dealer set.";
      repaint();
   }
   
   /**
   helps prepare gamephase before pre-flop betting round
   */
   public void afterBlindsHelper()
   {
      game.incrementPhase();
      game.setSubPhase("firstR");
      freeCounter = 0;
      game.distributeCards();
      setAIKnownCards();
      consoleMessage = "Hole Cards received.";
      repaint();
   }
   
   /**
   executes the small blind bet
   */
   public void smallBlindHelper()
   {
      game.addToPot(PokerGame.MINBET / 2);
      if(turnIndex == 0)   {  myApp.getUser().minusFinance(PokerGame.MINBET / 2);   }
      consoleMessage = game.getPlayerList().get(turnIndex).getName() + " paid the small blind.";
      freeCounter++;
      betAmtArray.set(turnIndex, PokerGame.MINBET / 2);
      turnIndex = (turnIndex + 1) % 5;
      expCounter++;
      repaint();
   }
   
   /**
   executes the big blind bet
   */
   public void bigBlindHelper()
   {
      game.addToPot(PokerGame.MINBET);
      consoleMessage = game.getPlayerList().get(turnIndex).getName() + " paid the big blind.";
      freeCounter++;
      if(turnIndex == 0)   {  myApp.getUser().minusFinance(PokerGame.MINBET);   }
      betAmtArray.set(turnIndex, PokerGame.MINBET);
      minBetAmt = PokerGame.MINBET;
      turnIndex = (turnIndex + 1) % 5;
      expCounter++;
      repaint();
   }
   
   /**
   handles all cases excluding blinds, beginning of game, and end of game
   */
   public void processTurn()
   {
      if(game.getPhase() == -10 && game.getSubPhase().equals("End is beginning") && round == 5)
      {
         endInitHelper();
      }
      else if(game.getPhase() == -10 && game.getSubPhase().equals("End is beginning") && round != 5)
      {
         game.setPhase(0);
      }
      else if(game.getPhase() == 6)
      {
         endGame();
      }
      else if(game.getPhase() == -1 && game.getSubPhase().equals("CommunCards"))
      {
         consoleMessage = "Everyone Folded. You won!";
         endGame();
      }
      else if(game.getPhase() == 3 && game.getSubPhase().equals("CommunCards"))
      {
         flopEntraHelper();
      }
      else if(game.getPhase() == 4 && game.getSubPhase().equals("CommunCards"))
      {
         turnEntraHelper();
      }
      else if(game.getPhase() == 5 && game.getSubPhase().equals("CommunCards"))
      {
         riverEntraHelper();
      }
      else if(game.getPhase() == 0) {  gameEntraHelper();   }
      else if(game.getPhase() == 1 && game.getSubPhase().equals("Blind"))
      {
         if(freeCounter == 2) {  afterBlindsHelper(); }
         else if(freeCounter == 0)  {  smallBlindHelper();  }
         else if(freeCounter == 1)  {  bigBlindHelper(); }
      }
      else  {  manageMainTurn(game.getPhase()); }
   }
   
   /**
   prepares game before the start of next betting round
   */
   public void initNextTurn()
   {
      game.incrementPhase();
      game.setSubPhase("CommunCards");
      if(game.getPhase() == 3)  {  consoleMessage = "Flop";   }
      else if(game.getPhase() == 4)  {  consoleMessage = "Turn";   }
      else if(game.getPhase() == 5)   {  consoleMessage = "River";  }
      else if(game.getPhase() == 6)
      {  
         consoleMessage = "Showdown!";
         game.setSubPhase("Final");
      }
      freeCounter = 0;
      expCounter = 0;
      initAIStatus();
      repaint();
   }
   
   /**
   helps prepare game before the user makes the first bet of the betting round
   */
   public void userInitBetHelper()
   {
      setButtonStrings("Check", "Call", "Fold");
      consoleMessage = "Your turn";
      if(freeStringCount == 0)
      {
         freeString = game.getSubPhase();
         freeStringCount++;
      }
      game.setSubPhase("user");
      repaint();
   }
   
   /**
   helps prepare game before the user makes any bet of the betting round
   */
   public void userTurnHelper()
   {
      setButtonStrings("Call", "Raise", "Fold");
      consoleMessage = "Your turn";
      freeString = game.getSubPhase();
      if(freeStringCount == 0)
      {
         freeString = game.getSubPhase();
         freeStringCount++;
      }
      game.setSubPhase("user");
      repaint();
   }

   /**
   helps prepare game before AI makes the first bet of the betting round
   */
   public void aiInitBetHelper()
   {
      AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
      if( subject.easyMove(game).equals("high"))
      {
         bet(0);
         consoleMessage = subject.getName() + " called";
         incrementCounters();
      }
      else if( subject.easyMove(game).equals("flat"))
      {
         consoleMessage = subject.getName() + " checked";
         subject.setTrinity("Check", 0, 0);
         incrementCounters();
      }
      else
      {
         consoleMessage = subject.getName() + " folded";
         subject.setTrinity("Fold", 0, 0);
         foldedIndex.add(turnIndex);
         System.out.println(foldedIndex);
         incrementCounters();
      }
   }
   
   /**
   helps prepare game before the AI plays any turn in the betting round
   */
   public void aiTurnHelper()
   {
      AIPlayer subject = (AIPlayer) game.getPlayerList().get(turnIndex);
      if( subject.easyMove(game).equals("high"))
      {
         if(freeCounter < 4)
         {
            bet(20);
            consoleMessage = subject.getName() + " raised by 20";
         }
         else
         {  
            bet(0);
            consoleMessage = subject.getName() + " called.";
         }
         incrementCounters();
      }
      else if( subject.easyMove(game).equals("flat"))
      {
         consoleMessage = subject.getName() + " called";
         bet(0);
         incrementCounters();
      }
      else
      {
         consoleMessage = subject.getName() + " folded";
         subject.setTrinity("Fold", 0, 0);
         foldedIndex.add(turnIndex);
         System.out.println(foldedIndex);
         incrementCounters();
      }
   }
   
   /**
   controls all betting rounds of the poker game
   @param phase phase of the game
   */
   public void manageMainTurn(int phase)
   {
      if(checkRoundFinished())
      {
         initNextTurn();
      }
      else if(turnIndex == 0 && freeCounter == 0)
      {
         userInitBetHelper();
      }
      else if(turnIndex == 0)
      {
         userTurnHelper();
      }
      else if(freeCounter == 0)
      {
         aiInitBetHelper();
      }
      else
      {
         aiTurnHelper();
      }
   }
   
   /**
   increments all essential counters of game
   */
   public void incrementCounters()
   {
      turnIndex = (turnIndex + 1) % 5;
      freeCounter++;
      expCounter++;
      repaint();
   }
   
   /**
   inner class that handles the timer task
   */
   private class UpdateTask extends TimerTask
   {
      /**
      runs the poker game according to the timer
      */
      public void run()
      {  
         processTurn();
         repaint();
      }
   }
   
   /**
   listener for the start button
   @author Daniel Kim
   @version 06/01/2017
   */
   private class TurnButtonListener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {
         myTimer = new Timer();
         myTimer.scheduleAtFixedRate(new UpdateTask(), 0, 2000);  
         startButton.setVisible(false);
      }
   }
   
   /**
   inner class for the first button action listener
   */
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
            freeStringCount = 0;
            freeCounter++;
            expCounter++;
            repaint();
         }
         else if(button1.getText().equals("Check") && game.getSubPhase().equals("user"))
         {
            consoleMessage = "You checked";
            turnIndex = (turnIndex + 1) % 5;
            game.setSubPhase(freeString);
            freeStringCount = 0;
            freeCounter++;
            expCounter++;
            repaint();
         }
      }
   }
   
   /**
   inner class for the second button action listener
   */
   private class Button2Listener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {
         if(button2.getText().equals("Call") && game.getSubPhase().equals("user"))
         {
            consoleMessage = "You called";
            bet(0);
            turnIndex = (turnIndex + 1) % 5;
            game.setSubPhase(freeString);
            freeStringCount = 0;
            freeCounter++;
            expCounter++;
            repaint();
         }
         else if(button2.getText().equals("Raise") && game.getSubPhase().equals("user"))
         {
            consoleMessage = "You raised";
            if(game.getPhase() < 5) {  bet(20); }
            else  {  bet(40); }
            turnIndex = (turnIndex + 1) % 5;
            game.setSubPhase(freeString);
            freeStringCount = 0;
            freeCounter++;
            expCounter++;
            repaint();
         }
      }
   }

   /**
   inner class for the third button action listener
   */
   private class Button3Listener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {
         if(button3.getText().equals("Fold") && game.getSubPhase().equals("user"))
         {
            clearData();
            consoleMessage = "You folded...";
            repaint();
         }
      }
   }
   
   /**
   inner class for the end button action listener
   */
   private class RunListener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {
         JFrame frame = new JFrame("Message Box");
         int choice = JOptionPane.showConfirmDialog(frame, "Exit casino?");
         if(choice == 0)
         {
            clearData();
            repaint();
            myApp.switchScreen("Lobby");
         }
      }
   }

}