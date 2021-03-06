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
   /** image of the card */
   private BufferedImage myImage;
   
   /**
   default constructor of PokerCard class
   */
   public PokerCard()
   {
      suit = "Clover";
      number = 9;
      name = suit + " " + name;
      try
      {
         myImage = ImageIO.read(new File("./PokerCardImages/" + number + suit + ".png"));
      }
      catch(IOException ioe)
      {
      
      }
   }
   
   /**
   constructor of PokerCard class specifying suit and number
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
         myImage = ImageIO.read(new File("./PokerCardImages/" + number + suit + ".png"));

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
   
   /**
   gets the image of the card
   @return image of card
   */
   public BufferedImage getImage()
   {
      return myImage;
   } 
}