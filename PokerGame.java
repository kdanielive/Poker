import java.util.*;
/**
plays the poker game
@author Daniel Kim
@version 04/11/2017
*/
public class PokerGame
{
   public static Player[] players = new Player[5];
   /** the deck of poker cards being used in game */
   public static Deck deck;
   /** the betting money on table */
   public static int moneyOnTable;
   /** the number of turns passed in the game */
   public static int turn;
   /** the communicty cards of the game */
   public static ArrayList<PokerCard> communityCards;
   
   public static boolean[] commCardFlipBool;
   
   public static int lastBetAmount;
   
   private CompareHandler comparier = new CompareHandler(communityCards);
   
   public static final int PLAYER_NUM = 5;
   public static final int HAND_VOLUME = 5;
   
   public static Player[] betters;
   
   public PokerGame()
   {
      deck = new Deck();
      moneyOnTable = 0;
      turn = 0;
      communityCards = new ArrayList<PokerCard>();
      betters = new Player[3];
      commCardFlipBool = new boolean[5];
      
      for(Player player : players)
      {
         player = new Player();
      }
      
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
   public void bet(Player player, int amount)
   {
      moneyOnTable += amount;
      lastBetAmount = amount;
      player.modifyFinance(player.getFinance() - amount);
   }
   
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
   }
   
   public void setBetter()
   {
      if(turn == 0)
      {
         Random randomer = new Random();
         int randIdx = randomer.nextInt(players.length);

         betters[0] = players[randIdx];
         betters[1] = players[(randIdx + 1) % 5];
         betters[2] = players[(randIdx + 2) % 5];
      }
      else
      {
         int buttonIdx = 0;
         for(int idx = 0; idx < players.length; idx++)
         {
            if(players[idx] == betters[0])
            {
               buttonIdx = idx;
            }
         }
         
         betters[0] = players[buttonIdx];
         betters[1] = players[(buttonIdx + 1) % 5];
         betters[2] = players[(buttonIdx + 2) % 5];
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
   }
   
   public void finishRound()
   {
      turn++;
      lastBetAmount = 0;
      moneyOnTable = 0;
      communityCards = new ArrayList<PokerCard>();
      deck = new Deck();
   }
   
   public Player[] getBetters()
   {
      return betters;
   }
   
   public Player[] getPlayerList()
   {
      return players;
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