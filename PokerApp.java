import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;

/**
class that manages all screens and basic functionalities of game
@author Daniel Kim
@version 04/11/2017
*/
public class PokerApp
{
   /** width of the game window */
   public static final int WINDOW_WIDTH = 1200;
   /** height of the game window */
   public static final int WINDOW_HEIGHT = 750;
   /** the starting finance of AI */
   public static final int AI_MONEY = 1000000;

   /** the app window */
   private JFrame myApp;
   
   /** JPanel for the PokerApp */
   private JPanel myAppPanel;
   /** the PokerTableScreen object where game is played */
   private PokerTableScreen myTableScreen;
   /** the CharacterCreationScreen object where user character is created */
   private CharacterCreationScreen myCharacterScreen;
   /** the MainScreen object which is the first main page of program */
   private MainScreen myMainScreen;
   /** the LobbyScreen object which is the waiting page in between games */
   private LobbyScreen myLobbyScreen;
   /** PokerGame object that holds all important data */
   private PokerGame game;
   
   /** user of the game */
   private Player user = new Player();
   /** unmodified AI one */
   private AIPlayer player2 = new AIPlayer("teardrop", "Bill", AI_MONEY, "Easy");
   /** unmodified AI two */
   private AIPlayer player3 = new AIPlayer("eyeball", "Jack", AI_MONEY, "Easy");
   /** unmodified AI three*/
   private AIPlayer player4 = new AIPlayer("jokerhat", "Jason", AI_MONEY, "Easy");
   /** unmodified AI four */
   private AIPlayer player5 = new AIPlayer("death", "Lohan", AI_MONEY, "Easy");
   
   /**
   main method that runs the program
   @param args String[] args
   */
   public static void main(String[] args)
   {
      PokerApp theApp = new PokerApp();
      theApp.run();
   }
   
   /**
   gets the user of the game
   @return returns the user
   */
   public Player getUser() {  return user;   }
   
   /**
   gets the first AI
   @return returns first AI
   */
   public AIPlayer getAI1()   {  return player2;   }
   
   /**
   gets the second AI
   @return returns second AI
   */
   public AIPlayer getAI2()   {  return player3;   }
   
   /**
   gets the third AI
   @return returns third AI
   */
   public AIPlayer getAI3()   {  return player4;   }
   
   /**
   gets the fourth AI
   @return returns fourth AI
   */
   public AIPlayer getAI4()   {  return player5;   }
   
   /**
   resets the user
   */
   public void resetUser() {  user = new Player(); }
   
   /**
   method that manages all the screens and runs the program
   */
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
   
   /**
   switches between screens
   @param whichScreen indicates which screen to switch to
   */
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