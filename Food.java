 import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class for a food object
 */
public class Food extends Item
{
    // Constructor
    // param - x and y position of this object
    public Food(int x, int y)
    {
        super(x, y);
        //resizes the object
        GreenfootImage image = getImage();
        image.scale( 16, 19 );
        setImage( image );
    }
    
    /**
     * Act - do whatever the food wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {

    }    
}
