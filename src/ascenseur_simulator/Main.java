package ascenseur_simulator;



import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.UIManager;


public class Main
{
    private final JFrame frame;
    private final JPanel panel;
    private final JButton btnStart;
    private final JButton btnStop;
    private Simulator simulation;
   
    
    public Main()
    {
        this.btnStart = new JButton();
		this.btnStop = new JButton();
		simulation = new Simulator();
		simulation.setBounds(0, 0, 750, 500);
		
		simulation.start();
		ImageIcon icon = new ImageIcon("/Users/pro/eclipse-workspace/ascenseur_simulator/src/ascenseur_simulator/img/enter.png"); 
		JLabel enter = new JLabel(icon); 
		enter.setBounds(650, 505, 85, 40);
		
		ImageIcon icon2 = new ImageIcon("/Users/pro/eclipse-workspace/ascenseur_simulator/src/ascenseur_simulator/img/exit.png"); 
		JLabel exit = new JLabel(icon2); 
		exit.setBounds(0, 505, 85, 40);
		
		
		JLabel desc = new JLabel("BEN ABDELLAH ANAS");
		JLabel desc2 = new JLabel("CHAMLALI ZAKARIA"); 
		desc.setBounds(320, 472, 180, 85);
		desc2.setBounds(320, 490, 180, 85);
		
      
        panel = new JPanel(null);
        panel.setBackground(UIManager.getColor("Focus.color"));
        panel.setPreferredSize(new Dimension(750, 550));
        panel.add(simulation);
        panel.add(enter);
        panel.add(exit);
        panel.add(desc);
        panel.add(desc2);
     
    
         
       
        frame = new JFrame("Simulation de 2 Ascenceurs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        
      
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args)
    {
    	// TODO Auto-generated method stub
        Main main = new Main();
    }
}

		
