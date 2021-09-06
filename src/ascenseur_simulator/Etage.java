package ascenseur_simulator;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List; 

public class Etage
{
    private int x1;
    private int x2;
    private int x3;
    private int x4;
    private int y;
    private int l; 
    private int floor;
    private List<Personne> passengers;
    private List<Personne> departing;
    
    public Etage(int x1, int x2, int x3, int x4, int y, int l, int floor)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.y = y;
        this.l = l;
        this.floor = floor;
        this.passengers = Collections.synchronizedList(new ArrayList());
        this.departing = Collections.synchronizedList(new ArrayList());
    }
    
    public void draw(Graphics g)
    {
    	
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setStroke(new BasicStroke(3));
   
       

        
        
        g.drawString("" + (floor), 690, y - 45);
        Graphics2D g3 = (Graphics2D) g;
        g3.setColor(Color.LIGHT_GRAY);
        
        g3.fillRect(50, y, 100, 100);
        g3.fillRect(200, y, 100, 100);
        g3.setColor(Color.BLACK);
     	g.drawLine(0, y, x1, y);
        g.drawLine(x2, y, x3, y);
        g.drawLine(x4, y, l, y);
        g.drawLine(x1, y, x2, y);
        g.drawLine(x3, y, x4, y);
       
       
    
        
        for (int i = 0, j = 400; i < passengers.size(); i++, j += 50)
        {
            passengers.get(i).setDestPosX(j);
            passengers.get(i).draw(g);
            
        }
        
        Iterator iterator = departing.iterator();
        
        while (iterator.hasNext())
        {
        	Personne temp = (Personne) iterator.next();
            
            temp.setDestPosX(775);
            temp.setDirection(Personne.Mode.RIGHT);
            temp.draw(g);
            
            if (temp.getX() == temp.getDestPosX())
            {
                iterator.remove();
            }
        }
    }
    
    public void addPassenger(Personne passenger)
    {
        int k = (!passengers.isEmpty()) ? passengers.get(passengers.size() - 1).getX() + 750 : 750;
        
        passenger.setX(k);
        passengers.add(passenger);
    }
    
    public int getFloor()
    {
        return this.floor;
    }
    
    public List<Personne> getPassengers()
    {
        return this.passengers;
    }
    
    public List<Personne> getDeparting()
    {
        return this.departing;
    }
}