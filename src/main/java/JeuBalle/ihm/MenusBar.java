package JeuBalle.ihm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenusBar extends JMenuBar
{
	private FirstFrame frame;
	
	private JMenu fichier;
	private JMenu aide;
	
	private JMenuItem[] tabItem;
	
	public MenusBar(FirstFrame frame)
	{
		super();
		
		this.frame = frame;
		
		this.fichier = new JMenu("Fichier");
		this.aide    = new JMenu("Aide");
		
		this.tabItem = new JMenuItem[] { new JMenuItem("Sauvegarder"), new JMenuItem("Ouvrir"),
				                         new JMenuItem("Aide")       , new JMenuItem("A propos")    };
		
		for( int cpt = 0; cpt < this.tabItem.length; cpt++ )
			this.tabItem[cpt].addActionListener(new ActionMenu());
		
		this.fichier.add( this.tabItem[0] );
		this.fichier.add( this.tabItem[1] );
		
		this.aide.add( this.tabItem[2] );
		this.aide.add( this.tabItem[3] );
		
		this.add(this.fichier);
		this.add(this.aide);
	}
	
	private class ActionMenu implements ActionListener
	{

		@Override
		public void actionPerformed( ActionEvent e )
		{
			JMenuItem temp = (JMenuItem) e.getSource();
			
			if( temp == MenusBar.this.tabItem[0] ) System.out.println("sauv");
			if( temp == MenusBar.this.tabItem[1] ) System.out.println("ouvr");
			if( temp == MenusBar.this.tabItem[2] ) new FrameHelp();
			if( temp == MenusBar.this.tabItem[3] ) 
			{
				MenusBar.this.frame.pauseResume();
				new Apropos();
				MenusBar.this.frame.pauseResume();
			}
		}
		
	}
}
