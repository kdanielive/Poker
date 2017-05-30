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
   private ArrayList<PokerCard> knownCards = new ArrayList<PokerCard>();
   private int betCount = 0;  //how many times he betted
   
   PokerGame myGame = new PokerGame();
   
   public AIPlayer()
   {
      super();
      type = "Easy";
      knownCards.clear();
      for(PokerCard card : this.getHoleCards())
      {
         knownCards.add(card);
      }
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
      knownCards.clear();
      for(PokerCard card : this.getHoleCards())
      {
         knownCards.add(card);
      }
   }
   
   public void setMode(String mode)
   {
      type = mode;
   }
   
   public int getBetCount()
   {
      return betCount;
   }
   
   public void setBetCount(int num)
   {
      betCount = num;
   }
   
   /** 
   the gameplay of an easy AI
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   */
   public String easyMove()
   {
      CompareHandler comparier = new CompareHandler();
      int compoNum = comparier.checkCompo(knownCards);
      int highNum = comparier.getHighCard(knownCards);
      if(PokerGame.phase < 3)
      {
         if(compoNum > 2)  {  return "high"; }
         else if(compoNum > 0 || highNum > 7) {  return "flat"; }
         else  {  return "low"; }
      }
      else
      {
         if(compoNum > 3)  {  return "high"; }
         else if (compoNum > 0)  {  return "flat"; }
         else  {  return "low";  }
      }
   }
   
   public void setKnownCards(int phase)
   {
      knownCards.clear();
      for(PokerCard card : this.getHoleCards())
      {
         knownCards.add(card);
      }
      if(phase == 3) 
      {
         for(int idx = 0; idx < 3; idx++)
         {
            knownCards.add(PokerGame.communityCards.get(idx));  
         }
      }
      if(phase == 4) {  knownCards.add(PokerGame.communityCards.get(3)); };
      if(phase == 5) {  knownCards.add(PokerGame.communityCards.get(4)); };
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