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
   public static final int WINDOW_HEIGHT = 750;

   /** the app window */
   private JFrame myApp;
   
   /** JPanel for the PokerApp */
   private JPanel myAppPanel;
      
   private PokerTableScreen myTableScreen;
   private CharacterCreationScreen myCharacterScreen;
   private MainScreen myMainScreen;
   private LobbyScreen myLobbyScreen;
   
   public static Player user = new Player();
   public static AIPlayer player2 = new AIPlayer("teardrop", "Bill", 10000, "Easy");
   public static AIPlayer player3 = new AIPlayer("eyeball", "Jack", 10000, "Easy");
   public static AIPlayer player4 = new AIPlayer("jokerhat", "Jason", 10000, "Easy");
   public static AIPlayer player5 = new AIPlayer("death", "Lohan", 10000, "Easy");
   
   public static void main(String[] args)
   {
      PokerApp theApp = new PokerApp();
      theApp.run();
   }
   
   
   public void run()
   {
      myApp = new JFrame();
      
      myTableScreen = new PokerTableScreen(this);
      myCharacterScreen = new CharacterCreationScreen(this);
      myMainScreen = new MainScreen(this);
      myLobbyScreen = new LobbyScreen(this);

      myApp.add(myTableScreen);
      myApp.add(myCharacterScreen);
      myApp.add(myMainScreen);
      myApp.add(myLobbyScreen);
      
      myAppPanel = new JPanel(new CardLayout());
      myAppPanel.add(myMainScreen, "Main");
      myAppPanel.add(myCharacterScreen, "CharacterScreen");
      myAppPanel.add(myTableScreen, "PokerTable");
      myAppPanel.add(myLobbyScreen, "Lobby");
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
         myTableScreen.requestFocusInWindow();
      }
      else if(whichScreen == "CharacterScreen")
      {
         myCharacterScreen.requestFocusInWindow();
      }
      else if(whichScreen == "Main")
      {
         myMainScreen.requestFocusInWindow();
      }
      else if(whichScreen == "Lobby")
      {
         myLobbyScreen.requestFocusInWindow();
      }
   }
}