package ascenseur_simulator;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Ascenseur
{
    private int xAxis;
    private int yAxis;
    private int width;
    private int height;
    private int doorWidth;
    private int floor; 

    private boolean isOpen;
    private boolean isClose;
    
    private Mode mode;
    private Mode direction;
    private List<Personne> passengers;

    public boolean n1; 
    public boolean n2;
    public boolean n3;
    public boolean n4;
    public boolean n5;
    public boolean n6;
    public boolean n7;
    
    public Ascenseur(int xAxis, int yAxis)
    {
        this(xAxis, yAxis, 100, 100, 0);
    }
    
    public Ascenseur(int xAxis, int yAxis, int width, int height, int floor)
    {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.width = width;
        this.height = height;
        this.doorWidth = width / 2;
        this.floor = floor;
        this.isOpen = false;
        this.isClose = true;
        this.mode = Mode.WAIT;
        this.direction = Mode.UP;
        
        // Thread Safe, synchronizedList : return a synchronized List
        this.passengers = Collections.synchronizedList(new ArrayList());
        
        n1 = true;
        n2 = false;
        n3 = false;
        n4 = false;
        n5 = false;
        n6 = false;
        n7 = false;
    }
    
    public void draw(Graphics g)
    {
        for (Personne passenger : passengers)
        {
            passenger.draw(g);
        }
        
        
        g.setColor(Color.GRAY);
        g.fillRect(xAxis, yAxis, doorWidth, height);
        g.fillRect(xAxis + width - doorWidth, yAxis, doorWidth, height);
        g.setColor(Color.BLACK);
        g.drawRect(xAxis, yAxis, doorWidth, height);
        g.drawRect(xAxis + width - doorWidth, yAxis, doorWidth, height);
        step();
    }
    
    public void step()
    {
        switch (mode)
        {
            case UP:
                
                --yAxis;
                
                for (Personne passenger : passengers)
                {
                    passenger.setY(passenger.getY() - 1);
                }

                if (yAxis % 100 == 0)
                {
                    ++floor;
                    
                    if (floor == 4)
                    {
                        direction = Mode.DOWN;
                    }
                }

                break;
                
            case DOWN:
                
                ++yAxis;
                
                for (Personne passenger : passengers)
                {
                    passenger.setY(passenger.getY() + 1);
                }

                if (yAxis % 100 == 0)
                {
                    --floor;
                    
                    if (floor == 0)
                    {
                        direction = Mode.UP;
                    }
                }
                
                break;
                
            case OPEN:
                
                if (doorWidth > 0)
                {
                    --doorWidth;
                }
                else if (doorWidth == 0)
                {
                    isOpen = true;
                    isClose = false;
                }
                
                break;
                
            case CLOSE:
                
                if (doorWidth < width / 2)
                {
                    ++doorWidth;
                }
                else if (doorWidth == width / 2)
                {
                    isOpen = false;
                    isClose = true;
                }
                
                break;
                
            default:
                
                break;
        }
    }
    
    public void alight(List<Personne> passengers)
    {
        Iterator iterator = this.passengers.iterator();
        
        while (iterator.hasNext())
        {
        	Personne temp = (Personne) iterator.next();
            
            if (temp.getDestinationFloor() == floor)
            {
                passengers.add(temp);
                iterator.remove();
            }
        }
    }
    
    public void board(List<Personne> passengers)
    {
        int totalWeight = 0;
        
        for (Personne passenger : passengers)
        {
            totalWeight += passenger.getWeight();
        }
        
        while (this.passengers.size() < 6 && totalWeight < 450 && !passengers.isEmpty() )
        {
            this.passengers.add(passengers.get(0));
            passengers.remove(0);
        }
        
        int x = 100 / (this.passengers.size() + 1);
        
        for (int i = 0, j = xAxis - 5 + x; i < this.passengers.size(); i++, j += x)
        {
            this.passengers.get(i).setDestPosX(j);
        }
    }
    
    public boolean isOpen()
    {
        return this.isOpen;
    }
    
    public boolean isClose()
    {
        return this.isClose;
    }
    
    public void setMode(Mode mode)
    {
        this.mode = mode;
    }
    
    public void setDirection(Mode direction)
    {
        this.direction = direction;
    }
    
    public int getFloor()
    {
        return this.floor;
    }
    
    public List<Personne> getPassengers()
    {
        return this.passengers;
    }
    
    public Mode getMode()
    {
        return this.mode;
    }
    
    public Mode getDirection()
    {
        return this.direction;
    }
    
    public int getX()
    {
        return this.xAxis;
    }
    
    public int getY()
    {
        return this.yAxis;
    }
    
    public enum Mode {UP, DOWN, OPEN, CLOSE, WAIT};
}