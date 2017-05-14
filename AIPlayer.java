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
   private int betAmount;
   private boolean wouldBet;
   
   public AIPlayer()
   {
      super();
      type = "Easy";
      betAmount = 0;
      wouldBet = false;
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
   public void easyMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards)
   {
      Random randomer = new Random();
      int mindState = randomer.nextInt(5);
      if (mindState == 4)
      {
         betAmount = this.getFinance() / 10;
         wouldBet = true;
      }
      else
      {  
         for(PokerCard card : holeCards)
         {
            int num = card.getNumber();
            String suit = card.getSuit();
            for(PokerCard communCard : communityCards)
            {
               if(communCard.getNumber() == num)   {  wouldBet = true; }
               else if(communCard.getSuit().equals(suit))   {  wouldBet = true; }
            }
         }
      }
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
   public void playMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards)
   {
      if(type.equals("Easy"))   {  easyMove(communityCards, holeCards);   }
      else if(type.equals("Medium"))  {  mediumMove(communityCards, holeCards); }
      else if(type.equals("Hard"))  {  hardMove(communityCards, holeCards);   }
      else  {  bossMove(communityCards, holeCards);   }
   }
}