import java.util.*;
/**
plays the poker game
@author Daniel Kim
@version 04/11/2017
*/
public class PokerGame
{
   private Player[] players = new Player[5];
   /** the deck of poker cards being used in game */
   private  Deck deck;
   /** the betting money on table */
   private int moneyOnTable;
   /** the number of turns passed in the game */
   private int turn;
   /** the communicty cards of the game */
   public ArrayList<PokerCard> communityCards;
   
   private boolean[] commCardFlipBool;
   private boolean receivedHoleCard = false;
   
   private int lastBetAmount;
   private int userDesiredAmt = 0;
   private int round = 0;
   
   private CompareHandler comparier = new CompareHandler(communityCards);
   
   public static final int PLAYER_NUM = 5;
   public static final int HAND_VOLUME = 5;
   
   private int[] betters = new int[3];
   private boolean[] checkList =  new boolean[5];
   private boolean[] foldList = new boolean[5];
   private boolean[] turnList = new boolean[5];
   
   public PokerGame()
   {
      deck = new Deck();
      moneyOnTable = 0;
      turn = 0;
      communityCards = new ArrayList<PokerCard>();
      
      commCardFlipBool = new boolean[5];
      for(Player player : players)
      {
         player = new Player();
      }
      
      setBetter();
      
      for(int idx = 0; idx < 5; idx++)
      {
         communityCards.add(new PokerCard());
         commCardFlipBool[idx] = false;
      }
      
   }
   
   /**
   plays the poker game
   */
   public void playGame()
   {
   
   }
   
   /**
   ends the poker game
   */
   public void endGame()
   {
   
   }
   
   /**
   compares the hands of two players and decides the winner
   @param player1 the first player of hand comparison
   @param player2 the second player of hand comparison
   
   public Player compareHands(Player player1, Player player2)
   {
      return comparier.compareHands(player1, player2);
   }
   */
   
   public void check()
   {
      System.out.print("Nothing happens");
   }
   
   public void call(Player player)
   {
      moneyOnTable += lastBetAmount;
      player.modifyFinance(player.getFinance() - lastBetAmount);
   }
   
   public void listPlayers(Player player, AIPlayer p1, AIPlayer p2,
       AIPlayer p3, AIPlayer p4)
   {
      players[0] = player;
      players[1] = p1;
      players[2] = p2;
      players[3] = p3;
      players[4] = p4;
      p1.setId(1);
      p2.setId(2);
      p3.setId(3);
      p4.setId(4);
   }
   
   public void setBetter()
   {
      if(turn == 0)
      {
      
         Random randomer = new Random();
         int randIdx = randomer.nextInt(players.length);

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
   
   public void distributeCards()
   {
      ArrayList<PokerCard> tempCardArray = new ArrayList<PokerCard>();
      
      for(int idx = 0; idx < PLAYER_NUM * 2; idx++)
      {
         tempCardArray.add(deck.drawCard());
      }
      for(int idx = 0; idx < PLAYER_NUM; idx++)
      {
         players[idx].setHoleCards(tempCardArray.get(idx),
            tempCardArray.get(idx + PLAYER_NUM));
      }
      for(int idx = 0; idx < 5; idx++)
      {
         communityCards.set(idx, deck.drawCard());
      }
      receivedHoleCard = true;                                            
   }
   
   public void finishRound()
   {
      turn++;
      lastBetAmount = 0;
      moneyOnTable = 0;
      communityCards = new ArrayList<PokerCard>();
      deck = new Deck();
   }
   
   public int[] getBetters()
   {
      return betters;
   }
   
   public Player[] getPlayerList()
   {
      return players;
   }
   
   public int getTurn()
   {
      return turn;
   }
   
   public static void main(String[] args)
   {
      
      Deck deck1 = new Deck();
      for(int idx = 0; idx < Deck.cardNum; idx++)
      {
         System.out.println(deck1.drawCard().getName());
      }
      
      PokerGame game = new PokerGame();
      game.setBetter();
   }
}