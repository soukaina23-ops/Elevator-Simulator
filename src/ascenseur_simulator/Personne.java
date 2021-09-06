package ascenseur_simulator;


import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Personne
{
    private int xAxis;
    private int yAxis;
    private int width;
    private int height;
    private int weight;
    private int sourceFloor;
    private int destinationFloor;
    private int currentFloor;
    private int destinationPosX;
    private Mode mode;
    private Mode direction;
    
  
  
   
    
    
    public Personne(int xAxis, int yAxis, int weight, int src, int dest)
    {
        this(xAxis, yAxis, 10, 50, weight, src, dest);
    }
    
    public Personne(int xAxis, int yAxis, int width, int height, int weight, int src, int dest)
    {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.sourceFloor = src;
        this.destinationFloor = dest;
        this.currentFloor = src;
        this.destinationPosX = -1;
        this.mode = Mode.WAIT;
        this.direction = Mode.LEFT;
    }
    
    public void draw(Graphics g)
    {
    	  try {
    
    	        File pathToFile = new File("/Users/pro/eclipse-workspace/ascenseur_simulator/src/ascenseur_simulator/img/1.png");
    	        Image img = ImageIO.read(pathToFile);
    	        g.drawImage (img, xAxis, yAxis,width,height, null);
    	        g.drawString("Poid: " + weight, xAxis - 10, yAxis - 31);
    	        g.drawString("Dest: " + (destinationFloor), xAxis - 5, yAxis - 7);
    	        
    	        if (xAxis == destinationPosX)
    	        {
    	            mode = Mode.WAIT;
    	        }
    	        else
    	        {
    	            mode = direction == Mode.LEFT ? Mode.LEFT : Mode.LEFT;
    	        }
    	        
    	        step();
    	    } catch (IOException ex) {
    	        ex.printStackTrace();
    	    }
    	    
    	
      
    }
    
    public void step()
    {
        switch (mode)
        {
            case LEFT:
                --xAxis;
                break;
            case RIGHT:
                ++xAxis;
                break;
            case UP:
                --yAxis;
                break;
            case DOWN:
                ++yAxis;
                break;
            default:
                break;
        }
    }
    
    public void setMode(Mode mode)
    {
        this.mode = mode;
    }
    
    public void setDirection(Mode direction)
    {
        this.direction = direction;
    }
    
    public void setX(int x)
    {
        this.xAxis = x;
    }
    
    public void setY(int y)
    {
        this.yAxis = y;
    }
    
    public void setDestPosX(int x)
    {
        this.destinationPosX = x;
    }
    
    public int getX()
    {
        return this.xAxis;
    }
    
    public int getY()
    {
        return this.yAxis;
    }
    
    public int getDestPosX()
    {
        return this.destinationPosX;
    }
    
    public int getCurrentFloor()
    {
        return this.currentFloor;
    }
    
    public int getSourceFloor()
    {
        return this.sourceFloor;
    }
    
    public int getDestinationFloor()
    {
        return this.destinationFloor;
    }
    
    public int getWeight()
    {
        return this.weight;
    }
    
    public enum Mode {LEFT, RIGHT, UP, DOWN, WAIT};
}