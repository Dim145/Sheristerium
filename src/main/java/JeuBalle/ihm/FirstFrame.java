package JeuBalle.ihm;

import JeuBalle.Controleur;
import JeuBalle.ihm.panels.PanelAffichage;
import JeuBalle.metier.balle.Balle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FirstFrame extends JFrame implements ActionListener, Runnable
{
	private static final long serialVersionUID = 1633631400631595979L;
	
	private Controleur     ctrl;
	private PanelAffichage panelAffichage;
	private MenusBar       menu;
	
	private JButton bRetry;
	private JButton bStart;
	private JButton b2Joueur;
	private JButton bMute;
	private JButton bPause;

	private boolean canRun;
	
	public FirstFrame( Controleur ctrl )
	{
		this.ctrl   = ctrl;
		
		this.setTitle("SPHERISTERIUM");
		this.setIconImage( new ImageIcon(this.getClass().getResource("/logoV1.png")).getImage() );
		
		this.panelAffichage = new PanelAffichage(this);
		this.bRetry         = new JButton( new ImageIcon( Toolkit.getDefaultToolkit().getImage( this.getClass().getResource("/retry.png") ) ) );
		this.bMute          = new JButton( new ImageIcon( Toolkit.getDefaultToolkit().getImage( this.getClass().getResource("/muteOff.png") ) ) );
		this.bPause         = new JButton( new ImageIcon( Toolkit.getDefaultToolkit().getImage( this.getClass().getResource("/pause.png") ) ) );
		this.bStart         = new JButton("Solo");
		this.b2Joueur       = new JButton("Multi");
		this.menu           = new MenusBar(this);
		this.canRun         = false;
		
		this.setContentPane( panelAffichage );
		this.addKeyListener( new keyListener() );
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.bRetry.addActionListener(this);
		this.bRetry.setVisible(false);
		this.bRetry.setBounds(190, 300, 30, 30);
		this.bRetry.setFocusable(false);
		
		this.bStart.addActionListener(this);
		this.bStart.setBounds(155, 240, 100, 30);
		this.bStart.setFocusable(false);
		
		this.b2Joueur.addActionListener(this);
		this.b2Joueur.setFocusable(false);
		this.b2Joueur.setBounds(155, 290, 100, 30);
		
		this.bMute.addActionListener(this);
		this.bMute.setFocusable(false);
		this.bMute.setBounds(179, this.getHeight()- 80, 22, 22);
		
		this.bPause.addActionListener(this);
		this.bPause.setVisible(false);
		this.bPause.setFocusable(false);
		this.bPause.setBounds(201, this.getHeight()-80, 22, 22);
		
		this.setLayout(null);
		this.add(this.bRetry );
		this.add(this.bStart);
		this.add(this.b2Joueur);
		this.add(this.bMute);
		this.add(this.bPause);
		
		this.setJMenuBar(this.menu);
	}
	
	
	private class keyListener extends KeyAdapter
	{
		private boolean[] keyJ1;
		private boolean[] keyJ2;
		
		public keyListener()
		{
			super();
			
			this.keyJ1 = new boolean[] { false, false };
			this.keyJ2 = new boolean[] { false, false };
		}
		
		public void keyPressed(KeyEvent e) 
		{			
			if ( canRun && !FirstFrame.this.ctrl.isbPause() )
			{
				if( e.getKeyCode() == e.VK_RIGHT ) 
					this.keyJ1[1] = true;
					
				if (e.getKeyChar() == 'd' )
					this.keyJ2[1] = true;
				
				if ( e.getKeyCode() == e.VK_LEFT )
					this.keyJ1[0] = true;
					
				if (e.getKeyChar() == 'q' )
					this.keyJ2[0] = true;
					
				this.maj();
			}
		}
		
		public void keyReleased( KeyEvent e )
		{
			if ( canRun && !FirstFrame.this.ctrl.isbPause() )
			{
				if( e.getKeyCode() == e.VK_RIGHT ) 
					this.keyJ1[1] = false;
					
				if (e.getKeyChar() == 'd' )
					this.keyJ2[1] = false;
				
				if ( e.getKeyCode() == e.VK_LEFT )
					this.keyJ1[0] = false;
					
				if (e.getKeyChar() == 'q' )
					this.keyJ2[0] = false;
					
				this.maj();
			}
		}
		
		private void maj()
		{
			if( this.keyJ1[0] )
				ctrl.evoluerBallePerso( (1.3*60)/1000, "q", '1' );
			if( this.keyJ1[1] )
				ctrl.evoluerBallePerso( (1.3*60)/1000, "d", '1' );
			
			if( this.keyJ2[0] )
				ctrl.evoluerBallePerso( (1.3*60)/1000, "q", '2' );
			if( this.keyJ2[1] )
				ctrl.evoluerBallePerso( (1.3*60)/1000, "d", '2' );
		}
		
	}

	public double getBallePersoCoordonnees( char c, char j )
	{
		return this.ctrl.getBallePersoCoordonnees( c, j );
	}

	public Balle[] getTabBalle()
	{
		return this.ctrl.getTabBalle();
	}

	public int getNiveaux()
	{
		return this.ctrl.getNiveaux();
	}
	
	public int getHighScore()
	{
		return this.ctrl.getHighScore();
	}
	
	public void reset()
	{
		this.bRetry.setVisible(false);
		this.bPause.setVisible(true);
	}
	
	public void run()
	{	
		this.afficher(true);
		this.panelAffichage.repaint();
		this.repaint();
	}

	public boolean getFinish()
	{
		return this.ctrl.getFinish();
	}
	
	public void displayRetryButton()
	{
		this.bRetry.setVisible(true);
		this.bPause.setVisible(false);
	}
	
	public void afficher( boolean on )
	{
		this.setVisible(on);
	}

	@Override
	public void actionPerformed( ActionEvent arg0 )
	{
		Object temp = arg0.getSource();
		
		if ( temp == this.bRetry )
			this.ctrl.relancerPartie();
		
		if ( temp == this.bStart || temp == this.b2Joueur )
		{
			if( temp == this.b2Joueur )
				this.ctrl.initJ2();
			
			this.bStart.setVisible(false);
			this.b2Joueur.setVisible(false);
			this.bPause.setVisible(true);
			this.canRun = true;
			this.ctrl.runEngine();
		}
		
		if( temp == this.bMute )
		{
			this.ctrl.switchMusic();
			
			if( !this.ctrl.getMusicOn() )
				this.bMute.setIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( this.getClass().getResource("/muteOn.png") ) ) );
			else
				this.bMute.setIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( this.getClass().getResource("/muteOff.png") ) ) );
		}
		
		if( temp == this.bPause && this.canRun )
		{			
			if( this.ctrl.isbPause() && !this.getFinish() )
				this.bPause.setIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( this.getClass().getResource("/pause.png") ) ) );
			else if( !this.getFinish() )
				this.bPause.setIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( this.getClass().getResource("/play.png") ) ) );
		
			this.repaint();
			
			this.pauseResume();
		}
	}
	
	public void pauseResume()
	{
		this.ctrl.pausResume();
	}
	
	public long getLunchTime()
	{
		return this.ctrl.getLunchTime();
	}
	
	public void evoluerBallePerso( double d, String string, char joueur )
	{
		this.ctrl.evoluerBallePerso( (1.3*60)/1000, string, joueur );
	}
	
	public int getJoueurGagne()
	{
		return this.ctrl.getJoueurGagne();
	}
	
	public boolean canRun()
	{
		return this.canRun;
	}

	public double getTailleBalle( char c )
	{
		return this.ctrl.getTailleBalle(c);
	}
	
	public static int[] getDimensions()
	{
		return new int[] { PanelAffichage.WIDTH, PanelAffichage.HEIGTH };
	}
}
