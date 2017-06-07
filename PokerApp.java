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
   
   public static final int AI_MONEY = 1000000;

   /** the app window */
   private JFrame myApp;
   
   /** JPanel for the PokerApp */
   private JPanel myAppPanel;
      
   private PokerTableScreen myTableScreen;
   private CharacterCreationScreen myCharacterScreen;
   private MainScreen myMainScreen;
   private LobbyScreen myLobbyScreen;
   private PokerGame game;
   
   private Player user = new Player();
   private AIPlayer player2 = new AIPlayer("teardrop", "Bill", AI_MONEY, "Easy");
   private AIPlayer player3 = new AIPlayer("eyeball", "Jack", AI_MONEY, "Easy");
   private AIPlayer player4 = new AIPlayer("jokerhat", "Jason", AI_MONEY, "Easy");
   private AIPlayer player5 = new AIPlayer("death", "Lohan", AI_MONEY, "Easy");
   
   public static void main(String[] args)
   {
      PokerApp theApp = new PokerApp();
      theApp.run();
   }
   
   public Player getUser() {  return user;   }
   public AIPlayer getAI1()   {  return player2;   }
   public AIPlayer getAI2()   {  return player3;   }
   public AIPlayer getAI3()   {  return player4;   }
   public AIPlayer getAI4()   {  return player5;   }
   public void resetUser() {  user = new Player(); }
   
   public void run()
   {
      myApp = new JFrame();
      myApp.setResizable(false);
      
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
         myTableScreen.screenInitCheck();
         myTableScreen.requestFocusInWindow();
      }
      else if(whichScreen == "CharacterScreen")
      {
         myCharacterScreen.screenInitCheck();
         myCharacterScreen.requestFocusInWindow();
      }
      else if(whichScreen == "Main")
      {
         myMainScreen.requestFocusInWindow();
      }
      else if(whichScreen == "Lobby")
      {
         myLobbyScreen.screenInitCheck();
         myLobbyScreen.requestFocusInWindow();
      }
   }
}