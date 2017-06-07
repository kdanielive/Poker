import java.util.*;

/**
class that handles all comparisons between cards and hand ranks
@author Daniel Kim
@version 06/01/2017
*/
public class CompareHandler
{  
   /**
   creates a dictionary of cards in hand
   @param hand the cards used
   @return returns an organized dictionary of all cards in hand
   */
   public int[] createDictionary(ArrayList<PokerCard> hand)
   {
      int[] dictionary = new int[13];
      
      for(PokerCard card : hand)
      {
         dictionary[card.getNumber() - 1] += 1;
      }
      
      return dictionary;
   }
   
   /**
   checks for one pair
   @param hand the used cards
   @return returns whether or not the hand has one pair
   */
   public boolean checkForOnePair(ArrayList<PokerCard> hand)
   {
      int[] dictionary = createDictionary(hand);
      
      for(int number : dictionary)
      {
         if(number == 2)   {  return true;   }
      }
      
      return false;
   }
   
   /**
   checks for two pair
   @param hand the used cards
   @return returns whether of not the hand has two pair
   */
   public boolean checkForTwoPair(ArrayList<PokerCard> hand)
   {
      int[] dictionary = createDictionary(hand);
      
      int pairCount = 0;
      
      for(int number : dictionary)
      {
         if(number == 2)   {  pairCount++;   }
      }

      if(pairCount == 2)   {  return true;   }

      return false;
   }
   
   /**
   checks for three of a kind
   @param hand the used cards
   @return returns whether of not the hand has three of a kind
   */
   public boolean checkForThreeOfAKind(ArrayList<PokerCard> hand)
   {
      int[] dictionary = createDictionary(hand);
      
      for(int number : dictionary)
      {
         if(number == 3)   {  return true;   }
      }
      return false;
   }
   
   /**
   checks for straight
   @param hand the used cards
   @return returns whether of not the hand has a straight
   */
   public boolean checkForStraight(ArrayList<PokerCard> hand)
   {
      insertionSort(hand);
      
      boolean isStraight = true;
      int straightTracker = 0;

      for(int idx = 1; idx < hand.size(); idx++)
      {
         if(hand.get(idx).getNumber() == hand.get(idx - 1).getNumber() + 1)   
         {  
            straightTracker++;
            if(straightTracker == 4)
            {
               return true;
            }
         }
         else  {  straightTracker = 0; }
      }
      
      return false;
   }
   
   /**
   checks for flush
   @param hand the used cards
   @return returns whether of not the hand has flush
   */
   public boolean checkForFlush(ArrayList<PokerCard> hand)
   {
      boolean isFlush = true;
      for(PokerCard card : hand)
      {
         if(card.getSuit().equals(hand.get(0).getSuit()) == false)
         {
            isFlush = false;
         }
      }
      return isFlush;
   }
   
   /**
   checks for full house
   @param hand the used cards
   @return returns whether of not the hand has full house
   */
   public boolean checkForFullHouse(ArrayList<PokerCard> hand)
   {
      int[] dictionary = createDictionary(hand);
      int pairCount = 0;
      int tripleCount = 0;
      for(int count : dictionary)
      {
         if(count == 2) {  pairCount++;   }
         else if(count == 3)  {  tripleCount++;   }
      }
      
      if(pairCount == 1 && tripleCount == 1)   {  return true;   }
      else  {  return false;  }
   }
   
   /**
   checks for four of a kind
   @param hand the used cards
   @return returns whether of not the hand has four of a kind
   */
   public boolean checkForFourOfAKind(ArrayList<PokerCard> hand)
   { 
      int[] dictionary = createDictionary(hand);
      
      for(int number : dictionary)
      {
         if(number == 4)   {  return true;   }
      }
      return false;
   }
   
   /**
   checks for straight flush
   @param hand the used cards
   @return returns whether of not the hand has straight flush
   */
   public boolean checkForStraightFlush(ArrayList<PokerCard> hand)
   {
      boolean isStraightFlush = false;
      if(checkForStraight(hand) == true && checkForFlush(hand) == true)
      {
         isStraightFlush = true;
      }
      
      return isStraightFlush;
   }
   
   /**
   gets the highest card in hand
   @param the used cards
   @return the number of highest card in hand
   */
   public int getHighCard(ArrayList<PokerCard> hand)
   {
      int[] dictionary = createDictionary(hand);
      for(int idx = 12; idx >= 0; idx--)
      {
         if(dictionary[idx] != 0)   {  return idx + 1;   }
      }
      return 0;
   }
   
   /**
   gets the highest compo of given cards
   @param cards given cards
   @return returns the int signifying the most valuable compo
   */
   public int checkCompo(ArrayList<PokerCard> cards)
   {
      if(checkForStraightFlush(cards))  {  return 8;   }
      else if(checkForFourOfAKind(cards))  {  return 7;   }
      else if(checkForFullHouse(cards)) {  return 6;   }
      else if(checkForFlush(cards))  {  return 5;   }
      else if(checkForStraight(cards))  {  return 4;   }
      else if(checkForThreeOfAKind(cards)) {  return 3;   }
      else if(checkForTwoPair(cards))   {  return 2;   }
      else if(checkForOnePair(cards))   {  return 1;   }
      else  {  return 0;   }
   }
   
   /**
   sorts the hand by card number
   @param list the used cards
   */
   public void insertionSort(ArrayList<PokerCard> list)
   {
      int n = list.size();
      
      for(int swapIdx = 1; swapIdx < n; swapIdx++)
      {
         int idx = swapIdx;
         PokerCard idxVal = list.get(swapIdx);
         int idxValNum = list.get(swapIdx).getNumber();
         while(idx > 0 && list.get(idx - 1).getNumber() > idxValNum)
         {
            list.set(idx, list.get(idx - 1));
            idx--;
         }
         list.set(idx, idxVal);
      }
   }
   
   /**
   gets the name of the biggest compo
   @param hand the used cards
   @return name of the biggest compo
   */
   public String compoName(ArrayList<PokerCard> hand)
   {
      int index = checkCompo(hand);
      switch(index)
      {
         case 1: return "One Pair";
         case 2: return "Two Pair";
         case 3: return "Three of a Kind";
         case 4: return "Straight";
         case 5: return "Flush";
         case 6: return "Full House";
         case 7: return "Four of a Kind";
         case 8: return "Straight Flush";
         default : return "Full House";
      }
   }
   
   /**
   insertion sort of integers
   @param list list of integers
   */
   public void intInsertionSort(ArrayList<Integer> list)
   {
      int n = list.size();
      
      for(int swapIdx = 1; swapIdx < n; swapIdx++)
      {
         int idx = swapIdx;
         int idxVal = list.get(swapIdx);
         while(idx > 0 && list.get(idx - 1) > idxVal)
         {
            list.set(idx, list.get(idx - 1));
            idx--;
         }
         list.set(idx, idxVal);
      }
   }
   
   /**
   compares the hands of two players and decides the winner
   @param player1 the first player of hand comparison
   @param player2 the second player of hand comparison
   @param game the PokerGame object used for storing data
   */
   public Player compareHands(Player player1, Player player2, PokerGame game)
   {
      player1.setShowDownHand(game);
      player2.setShowDownHand(game);
      int p1Point = checkCompo(player1.getShowDownHand());
      int p2Point = checkCompo(player2.getShowDownHand());
      
      if(p1Point > p2Point)   {  return player1;   }
      else if(p1Point == p2Point)
      {
         if(p1Point == AIPlayer.COMPO_MARK)  {  return null;   }
         else
         {
            int p1Num = getHighCard(player1.getShowDownHand());
            int p2Num = getHighCard(player2.getShowDownHand());
            if(p1Num > p2Num) {  return player1;   }
            else  {  return player2;   }
         }
      }
      else  {  return player2;   }
   }
}