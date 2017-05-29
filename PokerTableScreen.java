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
   
   private PokerGame game = new PokerGame();
   
   public PokerTableScreen(PokerApp app)
   {
      myApp = app;
      
      this.setLayout(null);
      
      button1 = new JButton("Press to");
      button2 = new JButton("Start");
      button3 = new JButton("Game");
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
      for(Player player : game.getPlayerList())
      {
         player.declareGame(game);
      }
      
      consoleMessage = "Welcome. Test yourself against these masters in Poker. "
         + "Win money, get money, or lose all money and die.";

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
      
      game.getPlayerList()[2].drawMe(g2, 200, 100);
      game.getPlayerList()[3].drawMe(g2, 875, 100);
      game.getPlayerList()[1].drawMe(g2, 200, 500);
      game.getPlayerList()[4].drawMe(g2, 875, 500);
      game.getPlayerList()[0].drawMe(g2, 400, 600);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
      
      g2.setColor(Color.WHITE);
      g2.fillRect(0, 0, 800, 90);
      g2.setColor(Color.BLACK);
      g2.drawRect(0, 0, 800, 90);
      g2.drawString(consoleMessage, 0, 0);

      //drawHoleCards(g);
      //drawCommunityCards(g);
      //drawMoneyOnTable(g);
      //drawButtons(g);
      //drawMyFinance(g);
   }
   
   public void drawButtons(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      BufferedImage dealerButton;
      BufferedImage smallBlindButton;
      BufferedImage bigBlindButton;
      
      if(game.getTurn() != 0)
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

   /*
   public void drawMyFinance(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.BLACK);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
      g2.drawString("Finance: " + game.players[0].getFinance(), 1000, 50);
   }
   
   public void drawMoneyOnTable(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.setColor(Color.WHITE);
      g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
      g2.drawString("Total: " + game.moneyOnTable, 540, 535);
   }

   
   public void drawCommunityCards(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      ArrayList<PokerCard> communityCards = game.communityCards;
      
      if(game.round == 1)
      {
         for(int idx = 0; idx < 3; idx++)
         {
            game.commCardFlipBool[idx] = true;
         }
      }
      else if(game.round == 2)
      {
         for(int idx = 0; idx < 4; idx++)
         {
            game.commCardFlipBool[idx] = true;
         }
      }
      else if(game.round == 3)
      {
         for(int idx = 0; idx < 5; idx++)
         {
            game.commCardFlipBool[idx] = true;
         }
      }     
      for(int idx = 0; idx < 5; idx++)
      {
         if(game.commCardFlipBool[idx] == true)
         {
            g2.drawImage(communityCards.get(idx).getImage(), 350 + idx * 100, 300, null);
            g2.setColor(Color.RED);
            g2.drawString(communityCards.get(idx).getName(), 350 + idx * 100, 460);
         }
      }
   }

   public void drawHoleCards(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      PokerCard[] holeCards = new PokerCard[2];
      holeCards = game.players[0].getHoleCards();
      
      if(game.receivedHoleCard == true)
      {
         if(game.turn == 1)
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
   
   private class TurnButtonListener implements ActionListener
   {
      /**
      what to do when action is recognized
      @param e an action event
      */
      public void actionPerformed(ActionEvent e)
      {

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