import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Abstract class for various items that will appear in the world
 */
public abstract class Item extends Actor
{
    // X and Y positon of an object
    private int x;
    private int y;
    
    // Constructor
    // param - x and y position of this object
    public Item(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Act - do whatever the items wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.

    }    

    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public boolean intersecting( java.lang.Class<?> cls )
    {
        if( this.isTouching( cls ) )
        {
            return true;
        }
        return false;
    }
}