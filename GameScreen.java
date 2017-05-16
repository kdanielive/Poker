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

public class GameScreen extends JPanel
{
   /** array of players in the game */
   private Player[] players;
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
      button1 = "Check";
      button2 = "Bet";
      button3 = "End Game";
      
      game = new PokerGame();
      game.listPlayers(user, new AIPlayer(), new AIPlayer(), new AIPlayer(), new AIPlayer());
      players = game.getPlayerList();
      for(Player player : players)
      {
         System.out.println(player.getName());
      }
   }
   
   /**
   draws the panel
   @param g the graphics handler
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawImage(pokerTableImage, 250, 200, null);
      
      players[1].drawMe(g2, 200, 100);
      players[2].drawMe(g2, 875, 100);
      players[3].drawMe(g2, 200, 500);
      players[4].drawMe(g2, 875, 500);
      players[0].drawMe(g2, 400, 600);
      
      g2.setColor(Color.LIGHT_GRAY);
      g2.draw3DRect(900, 650, 100, 150, true);
      g2.draw3DRect(1000, 650, 100, 150, true);
      g2.draw3DRect(1100, 650, 100, 150, true);
      g2.setColor(Color.BLACK);
      g2.drawString(button1, 930, 710);
      g2.drawString(button2, 1025, 710);
      g2.drawString(button3, 1120, 710);
      
      drawHoleCards(g);
   }
   
   public void drawHoleCards(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      PokerCard[] holeCards = new PokerCard[2];
      holeCards = players[0].getHoleCards();
      
      g2.drawImage(holeCards[0].getImage(), 500, 570, null);
      g2.drawImage(holeCards[1].getImage(), 600, 570, null);
   }
}