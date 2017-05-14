/**
class for a poker card
@author Daniel Kim
@version 04/11/2017
*/
public class PokerCard
{
   /** the suit of the card */
   private String suit;
   /** the number of the card */
   private int number;
   /** the suit and number of the card */
   private String name;
   
   public PokerCard()
   {
      suit = "Unmarked";
      number = 0;
      name = suit + " " + name;
   }
   
   /**
   @param mySuit the suit of the card
   @param myNumber the number of the card
   */
   public PokerCard(String mySuit, int myNumber)
   {
      suit = mySuit;
      number = myNumber;
      name = mySuit + " " + myNumber;
   }
   
   /**
   gets the number of the card
   @return number of card
   */
   public int getNumber()
   {
      return number;
   }
   
   /**
   gets the suit of the card
   @return suit of card
   */
   public String getSuit()
   {
      return suit;
   }
   
   /**
   gets the name of the card
   @return name of card
   */
   public String getName()
   {
      return name;
   }
   
   public static void main(String[] args)
   {
      PokerCard card = new PokerCard();
      PokerCard card2 = new PokerCard("Clover", 12);
      System.out.println(card.getName());
      System.out.println(card2.getName());
   }
}