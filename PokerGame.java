import java.util.*;
/**
plays the poker game
@author Daniel Kim
@version 04/11/2017
*/
public class PokerGame
{
   private ArrayList<Player> players = new ArrayList<Player>();
   /** the deck of poker cards being used in game */
   private  Deck deck;
   /** the betting money on table */
   private int moneyOnTable;
   /** the phase of the game */
   public static int phase;
   /** the sub-phase of the game */
   public static String subPhase;
   /** the communicty cards of the game */
   public static ArrayList<PokerCard> communityCards;
   public static final int MINBET = 20;
   private int[] betters = new int[3];
   
   private boolean[] commCardFlipBool;
   private boolean receivedHoleCard = false;
   
   private int lastBetAmount;
   private int userDesiredAmt = 0;
   private int round = 0;
   
   private CompareHandler comparier = new CompareHandler();
   
   public static final int PLAYER_NUM = 5;
   public static final int HAND_VOLUME = 5;
   
   private boolean[] checkList =  new boolean[5];
   private boolean[] foldList = new boolean[5];
   private boolean[] turnList = new boolean[5];
   
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
   
   public int getPhase() {  return phase;   }
   
   public void setPhase(int num) {  phase = num;   }
   
   public void incrementPhase(){  phase++;  }
   
   public void setSubPhase(String msg)  {  subPhase = msg;  }
   
   public String getSubPhase()  {  return subPhase;   }
   
   public void addToPot(int amt) {  moneyOnTable += amt; }
   
   public void emptyPot()  {  moneyOnTable = 0; }
   
   public int getPot()  {  return moneyOnTable; }
   
   public int[] getBetters()  {  return betters;   }
   
   public ArrayList<PokerCard> getCommunCards() {  return communityCards;  }
   
   public void shuffleDeck()  {  deck.mix(); }
   
   public ArrayList<Player> getPlayerList()
   {
      return players;
   }
   
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
   
   public void listPlayers(Player player, AIPlayer p1, AIPlayer p2,
       AIPlayer p3, AIPlayer p4)
   {
      players.set(0, player);
      players.set(1, p1);
      players.set(2, p2);
      players.set(3, p3);
      players.set(4, p4);
   }
   
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
   
   public static void main(String[] args)
   {
      
      Deck deck1 = new Deck();
      for(int idx = 0; idx < Deck.cardNum; idx++)
      {
         System.out.println(deck1.drawCard().getName());
      }
      
      PokerGame game = new PokerGame();
      game.setButton();
   }
}