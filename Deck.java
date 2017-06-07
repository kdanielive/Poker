import java.util.*;
/**
class for a deck of poker cards
@author Daniel Kim
@version 04/11/2017
*/
public class Deck
{
   /** number of cards with the same suit in a deck */
   public static final int numPerSuit = 13;
   /** total number of cards in deck */
   public static final int cardNum = 52;
   
   /** the deck of poker cards */
   private PokerCard[] pokerDeck;
   /** the index of the topmost card in deck */
   private int lastCardIdx;

   /**
   default constructor of Deck class
   */
   public Deck()
   {
      pokerDeck = new PokerCard[cardNum];
      lastCardIdx = cardNum - 1;
      
      for(int idx = 1; idx <= numPerSuit; idx++)
      {
         pokerDeck[idx - 1] = new PokerCard("Spade", idx);
      }
      for(int idx = numPerSuit + 1; idx <= 2 * numPerSuit ; idx++)
      {
         pokerDeck[idx - 1] = new PokerCard("Heart", idx - numPerSuit);
      }
      for(int idx = 2 * numPerSuit + 1; idx <= 3 * numPerSuit; idx++)
      {
         pokerDeck[idx - 1] = new PokerCard("Club", idx - 2 * numPerSuit);
      }
      for(int idx = 3 * numPerSuit + 1; idx <= 4 * numPerSuit; idx++)
      {
         pokerDeck[idx - 1] = new PokerCard("Diamond", idx - 3 * numPerSuit);
      }
      
      mix();
   }

   /** 
   mixes the order of cards in deck randomly 
   */
   public void mix()
   {
      lastCardIdx = cardNum - 1;
      int length = pokerDeck.length;
      PokerCard[] newDeck = new PokerCard[length];
      int count = 0;
      Random randomer = new Random();
      
      while(count < length)
      {
         int randIdx = randomer.nextInt(length);
         if(pokerDeck[randIdx] != null)
         {
            newDeck[count] = pokerDeck[randIdx];
            pokerDeck[randIdx] = null;
            count++;
         }
      }
      
      pokerDeck = newDeck;
   }
   
   /** 
   returns the topmost card in deck 
   @return the topmost card in deck
   */
   public PokerCard drawCard()
   {
      lastCardIdx--;
      return pokerDeck[lastCardIdx + 1];
   }
   
   /**
   burns the topmost card in deck
   */
   public void burn()
   {
      lastCardIdx--;
   }
   
   /**
   gets the total number of cards left in the deck
   @return the number of cards left in the deck
   */
   public int getCardNum()
   {
      return lastCardIdx + 1;
   }
}