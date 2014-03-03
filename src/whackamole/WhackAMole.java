package whackamole;

//class imports
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;
import java.net.*;
import javax.imageio.ImageIO;

//applet class
public class WhackAMole extends Applet implements Runnable, MouseListener {
    
    //gaming thread
    Thread gameloop;

    //backbuffer
    BufferedImage backbuffer;

    //graphics engine
    Graphics2D g2d;

    //image variables for background and temp holder for mole class array
    private Image background;
    private Image temp;

    //array of Mole class
    Mole[] mole = new Mole[5];

    //create a random number generator
    Random rand = new Random();

    //timer event
    long start = System.currentTimeMillis();
    long time = System.currentTimeMillis();

    //speed for timer
    long speed = 1000;

    //score variable
    int score = 0;
    int timePassed = 0;

    //set font
    Font font = new Font("Arial", Font.PLAIN, 36);

    //URL method for retrieving file path for mole image
    private URL getURL(String filename)
    {
        URL url = null;
        try
        {
            url = this.getClass().getResource(filename);
        }
        catch(Exception e){}
        return url;
    }
    
    //initialize applet
    public void init()
    {
        try
        {
            //set background image
            background = ImageIO.read( WhackAMole.class.getResource( "/textures/background.png" ) );

            //set mole images
            for(int i = 0; i < 5; i++)
            {
                mole[i] = new Mole();

                temp = getImage(getURL("/textures/mole.png"));
                mole[i].setImage(temp);
            }

            //set locations for the moles
            mole[0].setX(85);
            mole[0].setY(360);
            mole[1].setX(300);
            mole[1].setY(360);
            mole[2].setX(515);
            mole[2].setY(360);
            mole[3].setX(190);
            mole[3].setY(300);
            mole[4].setX(415);
            mole[4].setY(300);

            //set up the backbuffer
            backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
            g2d = backbuffer.createGraphics();

            //add the mouse listener to the applet
            addMouseListener(this);

            //set the size of the applet
            resize(640,480);
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    //update method for the applet
    public void update(Graphics g)
    {
        //draw background and mole
        drawBackground();
        drawMole();
        
        //draw score
        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString("Score: " + score, 50, 50);
        g2d.drawString("Time: " + timePassed, 400, 50);

        //repaint the applet window
        paint(g);
    }

    //check if mole is alive and draw the mole
    public void drawMole()
    {
        for(int i = 0; i < 5; i++)
        {
            if(mole[i].isAlive() == true)
            {
                g2d.drawImage(mole[i].returnImage(), mole[i].getX(), mole[i].getY(), this);
            }
        }
    }

    //draw the background for the game
    public void drawBackground()
    {
        g2d.drawImage(background, 0, 0, this);
    }

    //paint method
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g.drawImage(backbuffer, 0, 0, this);
    }

    //thread start method
    public void start()
    {
        gameloop = new Thread(this);
        gameloop.start();
    }

    //thread run method
    public void run()
    {
        //run thread t
        Thread t = Thread.currentThread();
        
        //update game and sleep the thread for 20
        while(t == gameloop)
        {
            try
            {
                gameUpdate();
                
                Thread.sleep(20);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            repaint();

            //use the system time to pop a mole every 500
            if(start + 500 < System.currentTimeMillis())
            {

                //reset moles
                for(int i = 0; i < 5; i++)
                {
                    mole[i].setAlive(false);
                }

                //pop a random mole
                mole[rand.nextInt(5)].setAlive(true);
                start = start + speed;
            }

            //use the system time to use the timer
            if(time + 1000 < System.currentTimeMillis())
            {
                timePassed = timePassed + 1;
                time = time + speed;
            }
        }
    }
    
    //thread stop method
    public void stop()
    {
        gameloop = null;
    }

    //game update thread
    public void gameUpdate()
    {

    }

    //mouse listening methods
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e){

        //get the x and y coords of the mouse clicked
        int x, y;

        x = e.getX();
        y = e.getY();
        
        //kill mole if it is alive and clicked on
        for(int i = 0; i < 5; i++)
        {
            if(x >= mole[i].getX() && x <= mole[i].getX() + mole[i].returnImage().getWidth(this)
            && y >= mole[i].getY() && y <= mole[i].getY() + mole[i].returnImage().getHeight(this)
            && mole[i].isAlive() == true)
            {
                mole[i].setAlive(false);

                //increase score
                score = score + 1;
            }
        }
    }
}