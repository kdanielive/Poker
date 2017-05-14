import java.util.*;

public class CompareHandler
{
   /** the communicty cards of the game */
   private ArrayList<PokerCard> communityCards;
   
   public CompareHandler(ArrayList<PokerCard> myCommunityCards)
   {
      communityCards = myCommunityCards;
   }
   
   /**
   compares the hands of two players and decides the winner
   @param player1 the first player of hand comparison
   @param player2 the second player of hand comparison
   */
   public Player compareHands(Player player1, Player player2)
   {
      int p1Point = checkCompo(player1);
      int p2Point = checkCompo(player2);
      
      if(p1Point > p2Point)   {  return player1;   }
      else  {  return player2;   }
   }

   public int[] createDictionary(ArrayList<PokerCard> hand)
   {
      int[] dictionary = new int[hand.size()];
      
      for(PokerCard card : hand)
      {
         dictionary[card.getNumber() - 1] = dictionary[card.getNumber() - 1] + 1;
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
      int firstIndex = 0;
      while(firstIndex <= hand.size() - PokerGame.HAND_VOLUME)
      {
         isStraight = true;
         for(int idx = firstIndex; idx < idx + PokerGame.HAND_VOLUME; idx++)
         {
            if(hand.get(idx).getNumber() + 1 != hand.get(idx + 1).getNumber())
            {
               isStraight = false;
            }
         }
         if(isStraight == true)   {  return true;   }
         firstIndex++;
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
      int passCount = 0;
      for(int count : dictionary)
      {
         if(count == 2) {  passCount++;   }
         else if(count == 3)  {  passCount++;   }
      }
      
      if(passCount == 2)   {  return true;   }
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
   
   public int checkCompo(Player player)
   {
      if(checkForStraightFlush(player.getShowDownHand()))  {  return 8;   }
      else if(checkForFourOfAKind(player.getShowDownHand()))  {  return 7;   }
      else if(checkForFullHouse(player.getShowDownHand())) {  return 6;   }
      else if(checkForFlush(player.getShowDownHand()))  {  return 5;   }
      else if(checkForStraight(player.getShowDownHand()))  {  return 4;   }
      else if(checkForThreeOfAKind(player.getShowDownHand())) {  return 3;   }
      else if(checkForTwoPair(player.getShowDownHand()))   {  return 2;   }
      else if(checkForOnePair(player.getShowDownHand()))   {  return 1;   }
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

   public static void main(String[] args)
   {      

   }
}