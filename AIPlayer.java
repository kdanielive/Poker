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
      Random randomer = new Random();
      int mindState = randomer.nextInt(5);
      if (mindState == 4)
      {
         firstBetAmount = 50;
         wouldBet = true;
      }
      else
      {  
         /*
         ArrayList<PokerCard> showDownCards = new ArrayList<PokerCard>();
         for(PokerCard card : this.getHoleCards())
         {
            showDownCards.add(card);
         }
         for(PokerCard card : PokerGame.communityCards)
         {
            showDownCards.add(card);
         }
         CompareHandler comparier = new CompareHandler(showDownCards);
         int compoNum = comparier.checkCompo(showDownCards);
         */
         int compoNum = 1;
         if(compoNum > 0)
         {
            wouldBet = true;
            firstBetAmount = 30;
         }
         
         else
         {
            if(this.getBettedAmount() > this.getFinance() / 3)
            {
               wouldBet = false;
            }
            else  
            {  
               wouldBet = true;  
               firstBetAmount = 20;
            }
         }
      }
      
      if(PokerGame.lastBetAmount - this.getBettedAmount() == 0 && getFirstBetterBool() == false)
      {
         PokerGame.checkList[Id] = true;
         wouldBet = false;
      }
      
      if(wouldBet == true && this.getFirstBetterBool() == false) 
      {  
         System.out.print("" + Id + "normal bet : " + PokerGame.lastBetAmount + ", " + this.getBettedAmount());
         bet(PokerGame.lastBetAmount - this.getBettedAmount(), false);
         System.out.println(", " + getBettedAmount());
      }
      else if(wouldBet == true)
      {
         System.out.println("" + Id + "first bet : " + firstBetAmount + ", " + PokerGame.lastBetAmount + ", " + getBettedAmount());
         bet(firstBetAmount, true);
         setFirstBetterBool(false);
      }
      else
      {
         PokerGame.checkList[Id] = true;
         System.out.println("lmao");
         for(boolean la : PokerGame.checkList)
         {
            System.out.print(la);
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
   public void playMove()
   {
      if(type.equals("Easy"))   {  easyMove();   }
   }
}