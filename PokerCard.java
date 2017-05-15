import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

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
   private BufferedImage myImage;
   
   public PokerCard()
   {
      suit = "Unmarked";
      number = 0;
      name = suit + " " + name;
      try
      {
         InputStream is = new FileInputStream("./PokerCardImages/12Clover.png");
         myImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
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
      try
      {
         InputStream is = new FileInputStream("./PokerCardImages/" + number + suit + ".png");
         myImage = ImageIO.read(is);
      }
      catch(IOException ioe)
      {
      
      }
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