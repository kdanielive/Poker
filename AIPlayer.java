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
   private ArrayList<Integer> stackCount;
   
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
      System.out.println(this.getName() + " : " + compoNum);
      if(PokerGame.phase < 4)
      {
         if(compoNum == 5) {  return "low"; }
         else if(compoNum > 0 || highNum > 5) {  return "high"; }
         else  {  return "flat"; }
      }
      else
      {
         if(compoNum == 5) {  return "low"; }
         else if (compoNum > 1)  {  return "high"; }
         else  {  return "flat";  }
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
   public String mediumMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards)
   {
      CompareHandler comparier = new CompareHandler();
      int compoNum = comparier.checkCompo(knownCards);
      int highNum = comparier.getHighCard(knownCards);
      System.out.println(this.getName() + " : " + compoNum);
      
      if(PokerGame.phase < 2)
      {
         if(compoNum == 5) {  return "low"; }
         else if(compoNum > 0 || highNum > 5) {  return "high"; }
         else  {  return "flat"; }
      }
      else if(PokerGame.phase <4)
      {
         if(highNum + compoNum > 3)
         {
            return "flat";
         }
         else if(stackCount.size() < 3)
         {
            return "low";
         }
         else  {  return "high"; }
      }
      else
      {
         if(compoNum == 5) {  return "low"; }
         else if (compoNum > 1)  {  return "high"; }
         else  {  return "flat";  }
      }  
   }
   
   /** 
   the gameplay of a hard AI
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   */
   public String hardMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards)
   {
      CompareHandler comparier = new CompareHandler();
      int compoNum = comparier.checkCompo(knownCards);
      int highNum = comparier.getHighCard(knownCards);
      int myStackCount = 0;
      for(int num : stackCount)
      {
         if(num > 2) {  myStackCount++;   }
      }
      System.out.println(this.getName() + " : " + compoNum);
      if(myStackCount > 7) {  return "low";  }
      else if(myStackCount > 3)  {  return "sky";  }
      else if(compoNum == 5 && highNum == 4) {  return "flat"; }
      else if(compoNum > 0 || highNum > 5) {  return "high"; }
      else
      {
         if(compoNum == 5) {  return "low"; }
         else if (compoNum > 1)  {  return "high"; }
         else  {  return "flat";  }
      }
      
   }
}