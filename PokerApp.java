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
   private CharacterCreationScreen myCharacterScreen;
   private MainScreen myMainScreen;
   
   public static Player user = new Player();
   
   public static void main(String[] args)
   {
      PokerApp theApp = new PokerApp();
      theApp.run();
   }
   
   public void run()
   {
      myGameScreen = new GameScreen(this);
      myCharacterScreen = new CharacterCreationScreen(this);
      myMainScreen = new MainScreen(this);

      myApp = new JFrame();
      myApp.add(myGameScreen);
      myApp.add(myCharacterScreen);
      myApp.add(myMainScreen);
      
      myGameScreen.setFocusable(true);
      myGameScreen.requestFocusInWindow();
      
      myAppPanel = new JPanel(new CardLayout());
      myAppPanel.add(myCharacterScreen, "CharacterScreen");
      myAppPanel.add(myGameScreen, "PokerTable");
      myAppPanel.add(myMainScreen, "Main");
      myApp.add(myAppPanel);
            
      myApp.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myApp.setVisible(true);
   }
   
   public void switchScreen(String whichScreen)
   {
      CardLayout layout = (CardLayout) myAppPanel.getLayout();
      layout.show(myAppPanel, whichScreen);
      
      if(whichScreen == "PokerTable")
      {
         myGameScreen.requestFocusInWindow();
      }
      else if(whichScreen == "CharacterScreen")
      {
         myCharacterScreen.requestFocusInWindow();
      }
      else if(whichScreen == "Main")
      {
         myMainScreen.requestFocusInWindow();
      }
   }
}