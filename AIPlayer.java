import java.util.*;
/**
a child class of Player that contains behaviors of AIPlayers
@author Daniel Kim
@version 06/01/2017
*/
public class AIPlayer extends Player
{
   /** constant that marks a milestone in hand comparison */
   public static final int COMPO_MARK = 5;

   /** string that tells the type of AIPlayer */
   private String type;
   
   /** the cards that the AI knows on the table */
   private ArrayList<PokerCard> knownCards = new ArrayList<PokerCard>();
   
   /** how many times the AI betted */
   private int betCount = 0;
   
   /** an incrementing count used for more sophisticated AI */
   private ArrayList<Integer> stackCount;
   
   /** description of the move the AI just did */
   private String moveDescript;
   
   /** the amount of bet the AI just did */
   private int justBetAmt;
   
   /** the amount of raised bet the AI just did */
   private int justRaisedAmt;
   
   /**
   AIPlayer constructor
   */
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
   
   /**
   sets a new move description
   @param move the move description
   */
   public void setMoveDescript(String move)  {  moveDescript = move; }
   
   /**
   returns the current move description
   @return the AI's current move description
   */
   public String getMoveDescript()  {  return moveDescript; }
   
   /**
   sets the amount of money just bet
   @param amt amount of money
   */
   public void setJustBetAmt(int amt)   {  justBetAmt = amt;   }
   
   /**
   gets the amount of money just bet
   @return amount of money just bet
   */
   public int getJustBetAmt() {  return justBetAmt;   }
   
   /**
   sets the amount of money just raised
   @param amt amount of money just raised
   */
   public void setJustRaisedAmt(int amt)   { justRaisedAmt = amt; }
   
   /**
   gets the amount of moeny just raised
   @return amount of money just raised
   */
   public int getJustRaisedAmt() {  return justRaisedAmt;  }
   
   /**
   records the move, amount of money just bet, and the amount of money just raised
   @param descript move description
   @param amt1 amount of money just bet
   @param amt2 amount of money just raised
   */
   public void setTrinity(String descript, int amt1, int amt2)
   {
      moveDescript = descript;
      justBetAmt = amt1;
      justRaisedAmt = amt2;
   }
   
   /**
   sets the hardness of the AI
   @param mode how sophisticated the AI is
   */
   public void setMode(String mode)
   {
      type = mode;
   }
   
   /**
   gets how many bets the AI made
   @return count of how many bets AI made
   */
   public int getBetCount()
   {
      return betCount;
   }
   
   /**
   sets the bet count of AI
   @param num the number of bet count
   */
   public void setBetCount(int num)
   {
      betCount = num;
   }
   
   /** 
   the gameplay of an easy AI
   @param game the PokerGame object storing all game data
   @return decision of AI
   */
   public String easyMove(PokerGame game)
   {
      CompareHandler comparier = new CompareHandler();
      int compoNum = comparier.checkCompo(knownCards);
      int highNum = comparier.getHighCard(knownCards);
      System.out.println(this.getName() + " : " + compoNum);
      if(game.getPhase() < COMPO_MARK - 1)
      {
         if(compoNum == COMPO_MARK) {  return "low"; }
         else if(compoNum > 0 || highNum > COMPO_MARK) {  return "high"; }
         else  {  return "flat"; }
      }
      else
      {
         if(compoNum == COMPO_MARK) {  return "low"; }
         else if (compoNum > 1)  {  return "high"; }
         else  {  return "flat";  }
      }
   }
   
   /**
   sets the cards that the AI can currently know at this phase of the game
   @param phase the phase of the game
   @param game the PokerGame object storing all game data
   */
   public void setKnownCards(int phase, PokerGame game)
   {
      knownCards.clear();
      for(PokerCard card : this.getHoleCards())
      {
         knownCards.add(card);
      }
      if(phase == PokerTableScreen.FLOP) 
      {
         for(int idx = 0; idx < PokerTableScreen.FLOP; idx++)
         {
            knownCards.add(game.getCommunCards().get(idx));  
         }
      }
      if(phase == PokerTableScreen.TURN) {  knownCards.add(game.getCommunCards().get(3)); };
      if(phase == PokerTableScreen.RIVER) {  knownCards.add(game.getCommunCards().get(4)); };
   }
   
   /** 
   the gameplay of a medium AI
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   @param game the PokerGame object storing all game data
   @return decision of AI
   */
   public String mediumMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards, PokerGame game)
   {
      CompareHandler comparier = new CompareHandler();
      int compoNum = comparier.checkCompo(knownCards);
      int highNum = comparier.getHighCard(knownCards);
      System.out.println(this.getName() + " : " + compoNum);
      
      if(game.getPhase() < PokerTableScreen.FLOP - 1)
      {
         if(compoNum == COMPO_MARK) {  return "low"; }
         else if(compoNum > 0 || highNum > COMPO_MARK) {  return "high"; }
         else  {  return "flat"; }
      }
      else if(game.getPhase() < PokerTableScreen.TURN)
      {
         if(highNum + compoNum > COMPO_MARK - 2) {  return "flat"; }
         else if(stackCount.size() < COMPO_MARK - 2)   {  return "low";  }
         else  {  return "high"; }
      }
      else
      {
         if(compoNum == COMPO_MARK) {  return "low"; }
         else if (compoNum > 1)  {  return "high"; }
         else  {  return "flat";  }
      }  
   }
   
   /** 
   the gameplay of a hard AI
   @param communityCards the community cards of the game
   @param holeCards the hole cards of the player
   @param game the PokerGame object storing all game data
   @return decision of AI
   */
   public String hardMove(ArrayList<PokerCard> communityCards, 
      PokerCard[] holeCards, PokerGame game)
   {
      CompareHandler comparier = new CompareHandler();
      int compoNum = comparier.checkCompo(knownCards);
      int highNum = comparier.getHighCard(knownCards);
      int myStackCount = 0;
      for(int num : stackCount)
      {
         if(num > COMPO_MARK - 3) {  myStackCount++;   }
      }
      System.out.println(this.getName() + " : " + compoNum);
      if(myStackCount > COMPO_MARK + 2) {  return "low";  }
      else if(myStackCount > COMPO_MARK - 2)  {  return "sky";  }
      else if(compoNum == COMPO_MARK && highNum == COMPO_MARK - 1) {  return "flat"; }
      else if(compoNum > 0 || highNum > COMPO_MARK) {  return "high"; }
      else
      {
         if(compoNum == COMPO_MARK) {  return "low"; }
         else if (compoNum > 1)  {  return "high"; }
         else  {  return "flat";  }
      }
      
   }
}