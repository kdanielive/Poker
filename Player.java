import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.geom.*; 

/**
a class for a player in poker game
@author Daniel Kim
@version 04/11/2017
*/
public class Player
{
   /** starting finance of a player */
   private int finance;
   /** the name of icon of a player */
   private String icon;
   /** the name of a player */
   private String name;
   /** the hole cards of a player */
   private PokerCard[] holeCards = new PokerCard[2];
   /** indicator of whether the player is alive or dead */
   private boolean isAlive;
   /** array of card used for Show Down phase of poker game */
   ArrayList<PokerCard> showDownHand = new ArrayList<PokerCard>();
   /** how much the player betted */
   private int bettedAmount = 0;
   /** image of player icon */
   private BufferedImage iconImage;
   
   /**
   default constructor for Player class
   */
   public Player()
   {
      isAlive = true;
      name = "Name";
      icon = "death";
      finance = 1000;
      
      holeCards[0] = new PokerCard();
      holeCards[1] = new PokerCard();
      
      try
      {
         InputStream is = getClass().getResourceAsStream("./IconImages/" + icon + ".png");
         iconImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
   }
   
   /**
   constructor for Player class specifying icon, name, and finance
   @param myIcon the icon of player
   @param myName the name of player
   @param myFinance the finance of player
   */
   public Player(String myIcon, String myName, 
      int myFinance)
   {
      isAlive = true;
      icon = myIcon;
      name = myName;
      finance = myFinance;
      holeCards = new PokerCard[2];

      try
      {
         InputStream is = getClass().getResourceAsStream("./IconImages/" + icon + ".png");
         iconImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
   }
   
   /**
   deducts money from finance
   @param amt deducted amount of money
   */
   public void minusFinance(int amt)
   {
      finance = finance - amt;
   }
   
   /**
   adds money to finance
   @param amt incremented amount of money
   */
   public void plusFinance(int amt)
   {
      finance = finance + amt;
   }
   
   /**
   gets the show down hand
   @return arraylist of show down hand of the player
   */
   public ArrayList<PokerCard> getShowDownHand()
   {
      return showDownHand;
   }
   
   /**
   sets the show down hand
   @param game PokerGame object containing the necessary community cards
   */
   public void setShowDownHand(PokerGame game)
   {
      for(PokerCard card : holeCards)   {  showDownHand.add(card); }
      for(PokerCard card : game.getCommunCards())
      {
         showDownHand.add(card);
      }
   }
   
   /**
   gets the financial situation of the player
   @return the total amount of money the player has
   */
   public int getFinance()
   {
      return finance;
   }
   
   /**
   gets the icon of the player
   @return icon of player
   */
   public String getIcon()
   {
      return icon;
   }
   
   /**
   gets the name of the player
   @return name of player 
   */
   public String getName()
   {
      return name;
   }
   
   /**
   gets the betted amount of money
   @return betted amount of money
   */
   public int getBettedAmount()
   {
      return bettedAmount;
   }
   
   /**
   sets the betted amount of money
   @param amt betted amount of money
   */
   public void setBettedAmount(int amt)
   {
      bettedAmount = amt;
   }
   
   /**
   sets the icon of the player
   @param myIcon icon of player
   */
   public void setIcon(String myIcon)
   {
      icon = myIcon;
      
      try
      {
         InputStream is = getClass().getResourceAsStream("./IconImages/" + icon + ".png");
         iconImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
   }
   
   /**
   sets the new finance of the player
   @param myFinance the new finance of player
   */
   public void modifyFinance(int myFinance)
   {
      finance = myFinance;
   }
   
   /**
   sets the name of the player
   @param myName name of player
   */
   public void setName(String myName)
   {
      name = myName;
   }
   
   /**
   prompts the reader to make a move
   */
   public void playMove()
   {

   }
   
   /** 
   gets the hole cards of the player
   @return the hole cards of player
   */
   public PokerCard[] getHoleCards()
   {
      return holeCards;
   }
   
   /**
   sets the hole cards of the player
   @param card1 first hole card
   @param card2 second hole card
   */
   public void setHoleCards(PokerCard card1, PokerCard card2)
   {
      holeCards[0] = card1;
      holeCards[1] = card2;
   }
   
   /**
   kills the player
   */
   public void die()
   {
      isAlive = false;
   }
   
   /**
   draws the player icon on the screen
   @param g2 the graphics handler
   @param myXval x-coordinate of icon
   @param myYval y-coordinate of icon
   */
   public void drawMe(Graphics2D g2, int myXval, int myYval)
   {
      g2.drawImage(iconImage, myXval, myYval, null);
   }
}