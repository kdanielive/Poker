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
   
   private BufferedImage pokerTableImage;
      
   private PokerApp myApp;
   
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
   }
   
   /**
   draws the panel
   @param g the graphics handler
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      g2.drawImage(pokerTableImage, 250, 200, null);
      Player[] players = PokerGame.players;
      Player player1 = players[0];
      //player1.getName(); why doens't this work?
      
      (new Player()).drawMe(g2, 200, 100);
      (new Player()).drawMe(g2, 875, 100);
      (new Player()).drawMe(g2, 200, 500);
      (new Player()).drawMe(g2, 875, 500);
      PokerGame.user.drawMe(g2, 525, 600);
      
      g2.setColor(Color.LIGHT_GRAY);
      g2.draw3DRect(900, 650, 100, 150, true);
      g2.draw3DRect(1000, 650, 100, 150, true);
      g2.draw3DRect(1100, 650, 100, 150, true);
   }
}