import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Super-class for the classes that represents a snake
 */
public abstract class Snake extends Actor
{
    // X and Y position of an object
    private int x;
    private int y;
    /**
     * Act - do whatever the Snake wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    // Snake Constructor
    // param - x and y position of this object
    public Snake( int x, int y )
    {
        this.x = x;
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setXY( int x, int y )
    {
        this.x = x;
        this.y = y;
    }
    
    //checks if this object is intersecting with an instance of the parameter class
    public boolean intersecting( java.lang.Class<?> cls )
    {
        if( this.isTouching(cls) )
        {
            return true;
        }
        return false;
    }
    
    //gets the instance of the parameter class that this object is intersecting with
    public Actor getIntersectingObject( java.lang.Class<?> cls )
    {
        return this.getOneIntersectingObject( cls );
    }
}
