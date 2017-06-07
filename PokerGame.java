import java.util.*;
/**
plays the poker game
@author Daniel Kim
@version 04/11/2017
*/
public class PokerGame
{
   /** total number of players */
   public static final int PLAYER_NUM = 5;
   /** total number of cards in hand */
   public static final int HAND_VOLUME = 5;

   private ArrayList<Player> players = new ArrayList<Player>();
   /** the deck of poker cards being used in game */
   private  Deck deck;
   /** the betting money on table */
   private int moneyOnTable;
   /** the phase of the game */
   private int phase;
   /** the sub-phase of the game */
   private String subPhase;
   /** the communicty cards of the game */
   private ArrayList<PokerCard> communityCards;
   /** minimum bet of flop and turn betting rounds */
   public static final int MINBET = 20;
   /** array of dealer, small blind, and big blind */
   private int[] betters = new int[3];
   
   /** array indicating which community cards are opened */
   private boolean[] commCardFlipBool;
   /** whether hole cards have been handed out */
   private boolean receivedHoleCard = false;
   
   /** the last betted amount in game */
   private int lastBetAmount;
   /** the desired amount to bet by the user */
   private int userDesiredAmt = 0;
   /** the current round number */
   private int round = 0;
   
   /** compares two hands of poker cards by rank */
   private CompareHandler comparier = new CompareHandler();
   
   /** list of everyone who checked */
   private boolean[] checkList =  new boolean[5];
   /** list of everyone who folded */
   private boolean[] foldList = new boolean[5];
   /** list of everyone's turn */
   private boolean[] turnList = new boolean[5];
   
   /**
   default constructor of PokerGame class
   */
   public PokerGame()
   {
      deck = new Deck();
      moneyOnTable = 0;
      phase = 0;
      communityCards = new ArrayList<PokerCard>();
      
      commCardFlipBool = new boolean[5];
      for(int idx = 0; idx < 5; idx++)
      {
         players.add(new Player());
      }
      
      setButton();
      
      for(int idx = 0; idx < 5; idx++)
      {
         communityCards.add(new PokerCard());
         commCardFlipBool[idx] = false;
      }
   }
   
   /**
   gets the phase of the game
   @return phase phase of game
   */
   public int getPhase() {  return phase;   }
   
   /**
   sets the phase of the game
   @param num phase of game
   */
   public void setPhase(int num) {  phase = num;   }
   
   /**
   increments the phase of game by one
   */
   public void incrementPhase(){  phase++;  }
   
   /**
   sets the sub-phase of the game
   @param msg sub-phase of game
   */
   public void setSubPhase(String msg)  {  subPhase = msg;  }
   
   /**
   gets the sub-phase of the game
   @return sub-phase of game
   */
   public String getSubPhase()  {  return subPhase;   }
   
   /**
   adds an amount of money to pot
   @param amt amount of money to add to pot
   */
   public void addToPot(int amt) {  moneyOnTable += amt; }
   
   /**
   empties the pot
   */
   public void emptyPot()  {  moneyOnTable = 0; }
   
   /**
   gets the money in pot
   @return amount of money in pot
   */
   public int getPot()  {  return moneyOnTable; }
   
   /**
   gets the array consisting of dealer, small blind, and big blind
   @return array of dealer, small blind, and big blind
   */
   public int[] getBetters()  {  return betters;   }
   
   /**
   gets the community cards of the game
   @return community cards of game
   */
   public ArrayList<PokerCard> getCommunCards() {  return communityCards;  }
   
   /**
   shuffles the deck
   */
   public void shuffleDeck()  {  deck.mix(); }
   
   /**
   sets the round number
   @param num number to set the round by
   */
   public void setRound(int num) {  round = num;   }
   
   /**
   gets list of all participating players
   @return array of all players
   */
   public ArrayList<Player> getPlayerList()
   {
      return players;
   }
   
   /**
   distributes hole cards to players in game
   */
   public void distributeCards()
   {
      ArrayList<PokerCard> tempCardArray = new ArrayList<PokerCard>();
      
      for(int idx = 0; idx < PLAYER_NUM * 2; idx++)
      {
         tempCardArray.add(deck.drawCard());
      }
      for(int idx = 0; idx < PLAYER_NUM; idx++)
      {
         players.get(idx).setHoleCards(tempCardArray.get(idx),
            tempCardArray.get(idx + PLAYER_NUM));
      }
      for(int idx = 0; idx < 5; idx++)
      {
         communityCards.set(idx, deck.drawCard());
      }                                         
   }
   
   /**
   initializes array of participating players
   @param player user
   @param p1 first AI
   @param p2 second AI
   @param p3 third AI
   @param p4 fourth AI
   */
   public void listPlayers(Player player, AIPlayer p1, AIPlayer p2,
       AIPlayer p3, AIPlayer p4)
   {
      players.set(0, player);
      players.set(1, p1);
      players.set(2, p2);
      players.set(3, p3);
      players.set(4, p4);
   }
   
   /**
   decides who gets the button, and decides dealer, small blind, and big blind
   */
   public void setButton()
   {
      if(round == 0)
      {
         Random randomer = new Random();
         int randIdx = randomer.nextInt(players.size());

         betters[0] = randIdx;
         betters[1] = (randIdx + 1) % 5;
         betters[2] = (randIdx + 2) % 5;
      }
      else
      {
         int buttonIdx = (betters[0] + 1) % 5;
         
         betters[0] = buttonIdx;
         betters[1] = (buttonIdx + 1) % 5;
         betters[2] = (buttonIdx + 2) % 5;
      }
   }
}