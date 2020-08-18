import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
//import java.awt.Color;

/**
 * Class for the score counter
 */
public class Counter extends Actor
{
    private int score;
    
    /**
     * constructor that makes the a new score board.
     */
    public Counter()
    {
        // void method that creates constructor
        updateImage();
        score = 0;
    }
    
    public Counter( int i )
    {
        updateImage();
        score = i;
    }

    public void addScore() 
    {
        // score is increased by one.
        score ++;
        // vpiid method that makes a new image to replace an old one.
        updateImage();
    }    
    
    public void updateImage() 
    {
        // resets the image to a new one each time it is called upon. 
        //new score is also implemented with each update
        setImage(new GreenfootImage("Score: " + score, 40, Color.WHITE, Color.BLACK));
    }  
    
    
    
    public void setScore( int newScore )
    {
        score = newScore;
    }
    
    public int getScore()
    {
        return score;
    }
}