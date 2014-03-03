package whackamole;

//class import
import java.awt.*;

//class for the Mole
public class Mole {

    //variables
    public Image mole;
    private Shape shape;
    private boolean alive;
    private int x, y;

    //accessor methods
    public Shape getShape() {return shape;}
    public boolean isAlive() {return alive;}
    public int getX() {return x;}
    public int getY() {return y;}

    //mutator and helper methods
    public void setShape(Shape shape) {this.shape = shape;}
    public void setAlive(boolean alive) {this.alive = alive;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void incY(double i) {this.y += i;}
    
    //default constructor
    Mole(){
        mole = null;
        alive = false;
    }

    //set image method
    public void setImage(Image image)
    {
        mole = image;
    }

    //return image method
    public Image returnImage(){
        return mole;
    }
}