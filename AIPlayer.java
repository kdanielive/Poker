import java.util.*;
/**
a child class of Player that contains behaviors of AIPlayers
@author Daniel Kim
@version 04/11/2017
*/
public class AIPlayer extends Player
{
   /** string that tells the type of AIPlayer */
   private String type;
   private int firstBetAmount;
   private boolean wouldBet;
   private int Id;
   
   public AIPlayer()
   {
      super();
      type = "Easy";
      firstBetAmount = 0;
      wouldBet = false;
      Id = 0;
   }
   
   public void setId(int num)
   {
      Id = num;
   }
   
   public int getId()
   {
      return Id;
   }
   
   public void setMode(String mode)
   {
      type = mode;
   }
   
   /**
   @param myIcon the icon of the player
   @param myName the name of the player
   @param myFinance the starting finance of the player
   @param difficulty the difficulty rating of the player
   */
   public AIPlayer(String myIcon, String myName, int myFinance,
                   String difficulty)
   {
      super(myIcon, myName, myFinance);
      type = difficulty;
   }
   
   /** 
   the gameplay of an easy AI
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   */
   public void easyMove()
   {

   }
   
   /** 
   the gameplay of a medium AI
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   */
   public void mediumMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards)
   {
      
   }
   
   /** 
   the gameplay of a hard AI
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   */
   public void hardMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards)
   {
      
   }

   /** 
   the gameplay of a boss AI
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   */
   public void bossMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards)
   {
      
   }
   
   /**
   the gameplay of the AI depending on its type 
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   */
   public void playMove()
   {
      if(type.equals("Easy"))   {  easyMove();   }
   }
}