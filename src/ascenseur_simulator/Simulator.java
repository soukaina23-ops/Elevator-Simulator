package ascenseur_simulator;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.Timer;
import javax.swing.JTextField;

public class Simulator extends JPanel
{
    private Timer timer;
    private boolean isRunning;
    private int counter;
    private int timeElapsedInSecs;
    
    private Ascenseur elevator1;
    private Ascenseur elevator2;
 
    private List<Etage> floors;
    
    private Random random;
    
    
    
    
    public Simulator(int refreshRate)
    {
        this.isRunning = false;
        this.counter = 0;
        this.timeElapsedInSecs = 0;
        
        elevator1 = new Ascenseur(50, 400);
        elevator2 = new Ascenseur(200, 400);
        
        

        floors = Collections.synchronizedList(new ArrayList());
        
        for (int i = 0; i < 6; i++)
        {
            floors.add(new Etage(100, 200, 250, 350, 100 * (5 - i), 750, i));
            
        }
        
        random = new Random();
        
        timer = new Timer(refreshRate, (e) -> {
            
            counter += refreshRate;
            
            if (counter / 1000 == 1)
            {
                counter = 0;
                ++timeElapsedInSecs;
                
                if (timeElapsedInSecs % 5 == 0)
                {
                    spawn();
                }
            }
            
            if (isRunning)
            {
                repaint();
            }
        });
        
        timer.start();
    }
    
    public Simulator()
    {
    	this(10);
    	
        
    }
    
    public void start()
    {
        if (!isRunning)
        {
            isRunning = true;
        }
    }
    
    public void stop()
    {
        if (isRunning)
        {
            isRunning = false;
        }
    }
    
    public void spawn()
    {
        int numOfPassenger = random.nextInt(3) + 1;

        for (int i = 0; i < numOfPassenger; i++)
        {
            int srcFloor = random.nextInt(5);
            int destFloor;

            do
            {
                destFloor = random.nextInt(5);
            }
            while (destFloor == srcFloor);

            int weight = random.nextInt((120 - 20) )+1 + 20;

            floors.get(srcFloor).addPassenger(new Personne(0, 100 * (5 - srcFloor) - 50, weight, srcFloor, destFloor));
        }
    }
    
    public void maneuver(Ascenseur elevator)
    {
       
        if (elevator.n1)
        {
      
            for (Etage floor : floors)
            {
                if (floor.getPassengers().isEmpty())
                {
                    continue;
                }
                
                if (floor.getPassengers().get(0).getX() == floor.getPassengers().get(0).getDestPosX())
                {
                    elevator.setMode(Ascenseur.Mode.UP);
                    elevator.n1 = false;
                    elevator.n2 = true;
                    
                    break;
                }
            }
        }
        
        if (elevator.n2)
        {
        
            if (elevator.getY() % 100 == 0)
            {
                boolean alightCheck = false;
                
                for (Personne passenger : elevator.getPassengers())
                {
                    if (passenger.getDestinationFloor() == elevator.getFloor())
                    {
                        alightCheck = true;
                    }
                }
                
                if (floors.get(elevator.getFloor()).getPassengers().isEmpty() && !alightCheck)
                {
                    elevator.setMode(elevator.getDirection() == Ascenseur.Mode.UP ? Ascenseur.Mode.UP : Ascenseur.Mode.DOWN);
                }
                else
                {
                    elevator.setMode(Ascenseur.Mode.OPEN);
                    elevator.n2 = false;
                    elevator.n3 = true;
                }
            }
        }
        
        if (elevator.isOpen() && elevator.n3)
        {
           
            
            elevator.alight(floors.get(elevator.getFloor()).getDeparting());
            elevator.board(floors.get(elevator.getFloor()).getPassengers());

            if (!elevator.getPassengers().isEmpty())
            {
                elevator.n3 = false;
                elevator.n4 = true;
            }
            else
            {
                elevator.n3 = false;
                elevator.n7 = true;
            }
        }
        
        if (elevator.n7)
        {
            elevator.setMode(Ascenseur.Mode.CLOSE);
            
            if (elevator.isClose())
            {
                elevator.n7 = false;
                elevator.n1 = true;
            }
        }
        
        if (elevator.n4)
        {
            
            List<Personne> temp = elevator.getPassengers();
            
            if (!temp.isEmpty())
            {
                if (temp.get(temp.size() - 1).getX() == temp.get(temp.size() - 1).getDestPosX())
                {
                    elevator.setMode(Ascenseur.Mode.CLOSE);
                    elevator.n4 = false;
                    elevator.n5 = true;
                }
                else 
                {
                    Iterator i = temp.iterator();
                    
                    while (i.hasNext())
                    {
                        Personne p = (Personne) i.next();
                        
                        if (p.getX() < elevator.getX())
                        {
                            i.remove();
                        }   
                    }
                }
            }
            else
            {
                elevator.n4 = false;
                elevator.n7 = true;
            }
        }
        
        if (elevator.isClose() && elevator.n5)
        {
          

            elevator.setMode(elevator.getDirection() == Ascenseur.Mode.UP ? Ascenseur.Mode.UP : Ascenseur.Mode.DOWN);
            
            elevator.n5 = false;
            elevator.n6 = true;
        }
        
        if (elevator.n6)
        {
            
            elevator.n6 = false;
            elevator.n2 = true;
           
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        this.setBackground(Color.YELLOW);
        
      
       
       
       
        for (Etage floor : floors)
        {
        	
      
            
            floor.draw(g);
        
         
        }
        
        elevator1.draw(g);
        elevator2.draw(g);
        
        maneuver(elevator1);
        maneuver(elevator2);
    }
}
