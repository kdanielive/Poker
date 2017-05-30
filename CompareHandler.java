import java.util.*;

public class CompareHandler
{
   public CompareHandler()
   {
   
   }

   public int[] createDictionary(ArrayList<PokerCard> hand)
   {
      int[] dictionary = new int[13];
      
      for(PokerCard card : hand)
      {
         dictionary[card.getNumber() - 1] += 1;
      }
      
      return dictionary;
   }
         
   public boolean checkForOnePair(ArrayList<PokerCard> hand)
   {
      int[] dictionary = createDictionary(hand);
      
      for(int number : dictionary)
      {
         if(number == 2)   {  return true;   }
      }
      
      return false;
   }
   
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
   
   public boolean checkForThreeOfAKind(ArrayList<PokerCard> hand)
   {
      int[] dictionary = createDictionary(hand);
      
      for(int number : dictionary)
      {
         if(number == 3)   {  return true;   }
      }
      return false;
   }
   
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
   
   public boolean checkForFourOfAKind(ArrayList<PokerCard> hand)
   { 
      int[] dictionary = createDictionary(hand);
      
      for(int number : dictionary)
      {
         if(number == 4)   {  return true;   }
      }
      return false;
   }
   
   //ask about this
   public boolean checkForStraightFlush(ArrayList<PokerCard> hand)
   {
      boolean isStraightFlush = false;
      if(checkForStraight(hand) == true && checkForFlush(hand) == true)
      {
         isStraightFlush = true;
      }
      
      return isStraightFlush;
   }
   
   public int getHighCard(ArrayList<PokerCard> hand)
   {
      int[] dictionary = createDictionary(hand);
      for(int idx = 12; idx >= 0; idx--)
      {
         if(dictionary[idx] != 0)   {  return idx + 1;   }
      }
      return 0;
   }
   
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
   
   public static void insertionSort(ArrayList<PokerCard> list)
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
   
   public static void intInsertionSort(ArrayList<Integer> list)
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
   */
   public Player compareHands(Player player1, Player player2)
   {
      player1.setShowDownHand();
      player2.setShowDownHand();
      int p1Point = checkCompo(player1.getShowDownHand());
      int p2Point = checkCompo(player2.getShowDownHand());
      
      if(p1Point > p2Point)   {  return player1;   }
      else  {  return player2;   }
   }

   public static void main(String[] args)
   {      
   
   }
}