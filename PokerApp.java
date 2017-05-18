import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;

public class PokerApp
{
   /** width of the game window */
   public static final int WINDOW_WIDTH = 1200;
   
   /** height of the game window */
   public static final int WINDOW_HEIGHT = 800;

   /** the app window */
   private JFrame myApp;
   
   /** JPanel for the PokerApp */
   private JPanel myAppPanel;
      
   private GameScreen myGameScreen;
   
   public static void main(String[] args)
   {
      PokerApp theApp = new PokerApp();
      theApp.run();
   }
   
   public void run()
   {
      myGameScreen = new GameScreen(this);

      myApp = new JFrame();
      myApp.add(myGameScreen);
      
      myAppPanel = new JPanel(new CardLayout());
      myAppPanel.add(myGameScreen, "PokerTable");
      myApp.add(myAppPanel);
            
      myApp.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myApp.setVisible(true);
   }
}