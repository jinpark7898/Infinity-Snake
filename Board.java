import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Class for the game board
 */
public class Board extends World
{
    private ArrayList<Snake> snake; // Arraylist to hold snkae objects
    private Food food; // food object
    private LastTailPosition last; // keeps track of the last position of the snake's tail
    private Counter counter;// score board 
    private Counter highestCounter;
    public static int highest = 0;
    private int numOfRocks; // number of rocks on the board
    private GreenfootSound music; // background music

    // Constructor for objects of class Board
    public Board()
    {    
        super(50, 50, 10); // creates a board with 50 x 50 units ( 1 unit = 10 x 10 pixels )
        Greenfoot.setSpeed(50); // sets the speed for the Greenfoot.delay method
        counter = new Counter();// creates a new counter object for the score board
        highestCounter = new Counter( highest );
        highestCounter.setImage( new GreenfootImage( "Highest Score: " + highestCounter.getScore(), 20, Color.WHITE, Color.BLACK) );
        addObject( highestCounter, 25, 0 );
        addObject(counter, 8, 3); // adds it the to world

        //draws a line to isolate the score board and the actual game board
        for( int i = 0; i < getWidth(); i++ )
        {
            addObject( new StillObject(i, 6), i, 6 );
        }

        //initialization of the snake
        snake = new ArrayList<Snake>();
        //length of the snake
        int snakeLength = 3;

        snake.add( new SnakeHead( 2, getHeight() - 7 ) ); //Head

        while( snake.size() != snakeLength ) //Body
        {
            snake.add( new SnakeBody( 2, getHeight() -7 + snake.size() ) );
        }

        //initializes the tail's last position to be the current position of the tail
        last = new LastTailPosition( snake.get( snake.size() - 1).getX(), snake.get( snake.size() - 1 ).getY() );

        showText( "Press Run To Play", 25, 25 ); // prints a text in the middle of the board

        GreenfootSound[] sound = new GreenfootSound[3];
        sound[0] = new GreenfootSound( "After School Cat Walking.mp3" );
        sound[1] = new GreenfootSound( "Second run.mp3" );
        sound[2] = new GreenfootSound( "Sereno - The Summer Wind Front.mp3" );
        music = sound[ Greenfoot.getRandomNumber(3) ];
        // the world is updated
        updateWorld();
    }

    public void started()
    {
        music.playLoop();
        showText( null, 25, 25); //erases the text in the middle of the board
        createFood(); // creates one Food object on the board, randomly positioned
        create6Rocks(); // creates 6 rocks on the board, randomly positioned
        numOfRocks = 5;
        //boolean varialbe for the status of the program
        boolean running = true;
        //String that holds the direction of the sake; initial direction is "up"
        String lastDirection = "up";

        //while the game is running
        while( running )
        {
            if( snake.get(0).intersecting( Food.class ) )// checks to see if snake intesects with food object
            {
                //if true food object is removed 
                removeObject( snake.get(0).getIntersectingObject( Food.class ) );
                //once removed, a new SnakeBody object is added to the last position of the tail
                snake.add( new SnakeBody( last.getX(), last.getY() ) );
                //then new food is created
                createFood();
                //score board is updated to increase by one.
                counter.addScore();
                didItReach();
                //repaints the world
                repaint();
            } 

            clearWorld(); // remove all the snake parts from the world
            lastDirection = snakeDirection( lastDirection ); //get the user input for direction
            updateWorld(); // add snake parts with new positions to the world 
            repaint(); // update the graphic with any changes made

            //checks if the snake hit the edge of the world, game ends if true
            if( snake.get(0).isAtEdge() )
            {
                running = false;
            }

            //checks if the snake head hit any of its body parts, game ends if true
            if( snake.get(0).intersecting( SnakeBody.class ) )
            {
                running = false;
            }

            //checks if the snake head hit a rock. game ends if true
            if( snake.get(0).intersecting( StillObject.class ) )
            {
                running = false;
            }

            //0.05 sec delay
            try
            {
                Thread.sleep(50);
            }
            catch( InterruptedException e )
            {
                Thread.currentThread().interrupt();
            }
        }
        //if( running == false ), game over
        music.stop();
        Greenfoot.stop();
    }

    //remove every objects representing the snake from the world
    private void clearWorld()
    {
        removeObjects(snake);
    }

    //add objects representing the snake to the world
    private void updateWorld()
    {
        for( int i = 0; i < snake.size(); i++ )
        {
            addObject( snake.get(i), snake.get(i).getX(), snake.get(i).getY() );
        }
    }

    //get an user input for the direction and movement of the snake
    private String snakeDirection( String lastDirection )
    {
        String key = Greenfoot.getKey(); //user input from the keyboard
        //if the user didn't press any key, the snake doesn't change its direction and moves one unit
        if( key == null )
        {
            if( lastDirection.equals( "up" ) )
            {
                //adds new SnakeHead to the right position regarding the direction
                //and changes the old head to be a body part
                snake.add(0, new SnakeHead( snake.get(0).getX(), snake.get(0).getY() -1 ) );
                snake.set(1, new SnakeBody( snake.get(1).getX(), snake.get(1).getY() ) );
            }
            else if( lastDirection.equals( "down" ) )
            {
                snake.add(0, new SnakeHead( snake.get(0).getX(), snake.get(0).getY() +1 ) );
                snake.set(1, new SnakeBody( snake.get(1).getX(), snake.get(1).getY() ) );
            }
            else if( lastDirection.equals( "right" ) )
            {
                snake.add(0, new SnakeHead( snake.get(0).getX() +1, snake.get(0).getY() ) );
                snake.set(1, new SnakeBody( snake.get(1).getX(), snake.get(1).getY() ) );
            }
            else //if( lastDirection.equals( "left" ) )
            {
                snake.add(0, new SnakeHead( snake.get(0).getX() -1, snake.get(0).getY() ) );
                snake.set(1, new SnakeBody( snake.get(1).getX(), snake.get(1).getY() ) );
            }
            //changes the tail's last position to be the current position
            last.setXY( snake.get( snake.size() - 1).getX(), snake.get( snake.size() - 1 ).getY() );
            //removes the tail
            snake.remove( snake.size() - 1 );
        }
        //if there was an user input, change the direction of the snake accordingly
        else // if( key != null )
        {
            if( key.equals( "up" ) && !lastDirection.equals("down") )
            {
                snake.add(0, new SnakeHead( snake.get(0).getX(), snake.get(0).getY() -1 ) );
                snake.set(1, new SnakeBody( snake.get(1).getX(), snake.get(1).getY() ) );
                lastDirection = "up";
                last.setXY( snake.get( snake.size() - 1).getX(), snake.get( snake.size() - 1 ).getY() );
                snake.remove( snake.size() - 1 );
            }
            else if( key.equals( "down" ) && !lastDirection.equals("up") )
            {
                snake.add(0, new SnakeHead( snake.get(0).getX(), snake.get(0).getY() +1 ) );
                snake.set(1, new SnakeBody( snake.get(1).getX(), snake.get(1).getY() ) );
                lastDirection = "down";
                last.setXY( snake.get( snake.size() - 1).getX(), snake.get( snake.size() - 1 ).getY() );
                snake.remove( snake.size() - 1 );
            }
            else if( key.equals( "right" ) && !lastDirection.equals("left") )
            {
                snake.add(0, new SnakeHead( snake.get(0).getX() +1, snake.get(0).getY() ) );
                snake.set(1, new SnakeBody( snake.get(1).getX(), snake.get(1).getY() ) );
                lastDirection = "right";
                last.setXY( snake.get( snake.size() - 1).getX(), snake.get( snake.size() - 1 ).getY() );
                snake.remove( snake.size() - 1 );
            }
            else if( key.equals( "left" ) && !lastDirection.equals("right") )
            {
                snake.add(0, new SnakeHead( snake.get(0).getX() -1, snake.get(0).getY() ) );
                snake.set(1, new SnakeBody( snake.get(1).getX(), snake.get(1).getY() ) ); 
                lastDirection = "left";
                last.setXY( snake.get( snake.size() - 1).getX(), snake.get( snake.size() - 1 ).getY() );
                snake.remove( snake.size() - 1 );
            }
        }
        return lastDirection; // returns the new current direction of the snake
    } 

    //creates one Food object and adds it to the world, randomly positioned
    public void createFood()
    {
        while( true )
        {
            food = new Food( Greenfoot.getRandomNumber( getWidth() - 4 ) + 2,
                Greenfoot.getRandomNumber( getHeight() - 8 ) + 7 );
            addObject( food, food.getX(), food.getY() );
            //checks if there is any object already existing in the position
            //if true, remove the food and creates another one
            if( food.intersecting( null ) )
            {
                removeObject( food ); 
            }
            else
            {
                break;
            }
        }
    }

    //creates 6 rock objects and adds it to the world
    public void create6Rocks()
    {
        for( int i = 0; i < 6; i++ )
        {
            while( true )
            {
                StillObject rock = new StillObject( Greenfoot.getRandomNumber( getWidth() - 4 ) + 2,
                        Greenfoot.getRandomNumber( getHeight() - 8 ) + 7 );
                addObject( rock, rock.getX(), rock.getY() );
                if( rock.intersecting( null ) )
                {
                    removeObject( rock );
                }
                else
                {
                    break;
                }
            }
        }
    }

    //add one more rock to the world every time the score is a multiple of 5
    public void didItReach()
    {
        int score = counter.getScore();
        if( score % 5  == 0 && score > 0 )
        { 
            //shows a text
            showText( "Good Job! More Rocks!", 30, 3);
            while( true )
            {
                StillObject rock = new StillObject( Greenfoot.getRandomNumber( getWidth() - 4 ) + 2,
                        Greenfoot.getRandomNumber( getHeight() - 8 ) + 7 );
                addObject( rock, rock.getX(), rock.getY() );
                if( rock.intersecting( null ) )
                {
                    removeObject( rock );
                }
                else
                {
                    break;
                }
            }
        }
        //erases a text
        else if( score % 5 == 1 )
        {
            showText( null, 30 ,3 );
        }
    }

    //when the game is over, the program stops for a moment and restarts
    public void stopped()
    {
        showText( "Game Over", 25, 22 );
        showText( "Final Score: " + counter.getScore(), 25, 26 );
        if( counter.getScore() > highest )
        {
            highest = counter.getScore();
            showText( "NEW RECORD!!!", 25, 30 );
        }
        Greenfoot.delay(150);
        Greenfoot.setWorld( new Board() );
    }
}
