/**
stores a single sequence of colors in Mastermind
@author Daniel Kim
@version 1/26/2016
*/
public class MasterChain
{
   /** strings to represent all peg colors */
   public static final String[] COLORS = {"R", "Y", "G"};
   /** peg color for a guess that is exactly right */
   public static final String EXACT = "W";
   /** peg color for a guess that is right color but wrong location */
   public static final String CLOSE = "B";
   /** peg color for a guess that is not right in any way */
   public static final String NONE = "N";
   /** length of all chains */
   public static final int CHAIN_LENGTH = 5;
   /** list of pegs in the chain */
   private String[] myColors = new String[CHAIN_LENGTH];
   
   public MasterChain()
   {
      for(int idx = 0; idx < CHAIN_LENGTH; idx++)
      {
         int randNum = (int)(Math.random() * 3);
         myColors[idx] = COLORS[randNum];
      }
   }
   
   /**
   @param colors the colors of the chain
   */
   public MasterChain(String[] colors)
   {
      myColors = colors;
   }
   
   /**
   returns string-based representation
   @return list of pegs in myColors
   */
   public String toString()
   {
      String print = "";
      
      for(int idx = 0; idx < CHAIN_LENGTH; idx++)
      {
         print = print + myColors[idx] + " ";
      }
      
      return print;
   }
   
   /**
   gets a copy of the current chain
   @return  a copy of the current chain
   */
   public MasterChain clone()
   {
      MasterChain cloned = new MasterChain();
      for(int idx = 0 ; idx < CHAIN_LENGTH ; idx++)
      {
         cloned.myColors[idx] = getPeg(idx);
      }
      
      return cloned;
   }
   
   /**
   gets a single peg from a chain, or null if idx is invalid
   @param idx the position of the peg, 0 - (CHAIN_LENGTH - 1)
   @return value of myColors[idx]
   */
   public String getPeg(int idx)
   {
      if(idx >= 0 && idx < CHAIN_LENGTH)
      {
         return myColors[idx];
      }
      else
      {
         return null;
      }
   }
   
   /**
   modifies the value of a peg
   @param idx the position of the peg
   @param color the new peg color
   @return true if successful, else false
   */
   public boolean setPeg(int idx, String color)
   {
      if(0 <= idx && idx < 5)
      {
         myColors[idx] = color;
         return true;
      }
      return false;
   }
   
   /**
   gets the number of black or white pegs
   @param guess the MasterChain guess
   @return number of white or black pegs
   */
   public int getBWPegNum(MasterChain guess)
   {
      int blackNum = 0;
      int greenNumGuess = 0;
      int yellowNumGuess = 0;
      int redNumGuess = 0;
      int greenNumAns = 0;
      int yellowNumAns = 0;
      int redNumAns = 0;
      
      for(int idx = 0; idx < CHAIN_LENGTH; idx++)
      {
         if(guess.getPeg(idx).equals("G"))   {  greenNumGuess++;  }
         else if(guess.getPeg(idx).equals("Y"))   {  yellowNumGuess++; }
         else if(guess.getPeg(idx).equals("R"))   {  redNumGuess++; }
      }
      for(int idx = 0; idx < CHAIN_LENGTH; idx++)
      {
         if(this.getPeg(idx).equals("G"))   {  greenNumAns++;  }
         else if(this.getPeg(idx).equals("Y"))   {  yellowNumAns++; }
         else if(this.getPeg(idx).equals("R"))   {  redNumAns++; }
      }
      if(greenNumGuess <= greenNumAns) {  blackNum += greenNumGuess; }
      else if (greenNumGuess > greenNumAns)   {  blackNum += greenNumAns;   }
      
      if(yellowNumGuess <= yellowNumAns) {  blackNum += yellowNumGuess; }
      else if (yellowNumGuess > yellowNumAns)   {  blackNum += yellowNumAns;   }
      
      if(redNumGuess <= redNumAns) {  blackNum += redNumGuess; }
      else if (redNumGuess > redNumAns)   {  blackNum += redNumAns;   }   
      
      return blackNum;
   }
   
 
   /**
   gets a chain w/ black and white pegs (signifying a correct guess)
   @param guess the MasterChain guess
   @return a chain w/ one black peg for each exact match and one white peg for
      each out-of-position match
   */
   public MasterChain getFeedback(MasterChain guess)
   {
      int blackNum = getBWPegNum(guess);
      int whiteNum = 0;
      
      for(int idx = 0; idx < CHAIN_LENGTH; idx++)
      {
         if(guess.getPeg(idx).equals(this.getPeg(idx))) {
            whiteNum++;
         }
      }
      
      blackNum = blackNum - whiteNum;
   
      MasterChain result = new MasterChain();
      
      for(int idx = 0; idx< CHAIN_LENGTH; idx++)
      {
         if(idx < whiteNum)   {  result.myColors[idx] = EXACT;   }
         else if(idx < (whiteNum + blackNum))  {  result.myColors[idx] = CLOSE;   }
         else {   result.myColors[idx] = NONE;   }
      }
      
      return result;
   }
   
   /**
   gets number of exact pegs
   @return number of "B" in a feedback chain
   */
   public int getNumCorrect()
   {
      int numCorrect = 0;
      
      for (String flag : myColors)
      {
         if(flag == EXACT) {  numCorrect++;  }
      }
      
      return numCorrect;     
   }
    
   public static void main(String[] args)
   {
      String[] ansStr = {"G", "R", "R", "Y", "Y"};
      String[] guesStr = {"Y", "R", "R", "G", "Y"};
   
      MasterChain answer = new MasterChain(ansStr);
      MasterChain guess = new MasterChain(guesStr);
      
      MasterChain feedback = answer.getFeedback(guess);
      
      System.out.println(answer);
      System.out.println(guess);
      System.out.println(feedback);
      System.out.println(feedback.getNumCorrect());
   }

}