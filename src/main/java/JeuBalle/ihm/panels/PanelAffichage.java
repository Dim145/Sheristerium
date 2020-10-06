package JeuBalle.ihm.panels;

import JeuBalle.ihm.FirstFrame;
import JeuBalle.metier.balle.Balle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PanelAffichage extends JPanel
{
	public static final long serialVersionUID = -5284555765299928380L;
	public static final int  TAILLE_BALLE = 10;
	public static final int  WIDTH  = 400;
	public static final int  HEIGTH = 550;
	
	private FirstFrame frame;
	
	private int timeInSec;

	public PanelAffichage( FirstFrame frame )
	{
		this.frame = frame;
//		this.addKeyListener( new keyListener() );s
		
		this.setPreferredSize( new Dimension( 400, 550 ) );
	}
	
//	private class keyListener extends KeyAdapter
//	{
//		public void keyPressed(KeyEvent e) 
//		{			
//			if ( PanelAffichage.this.frame.canRun() )
//			{
//				if( e.getKeyChar() == 'd' ) 
//					PanelAffichage.this.frame.evoluerBallePerso( (1.3*60)/1000, "d", '2' );
//				else if ( e.getKeyChar() == 'q' )
//					PanelAffichage.this.frame.evoluerBallePerso( (1.3*60)/1000, "q", '2' );
//			}
//		}
//		
//	}
	
	public void paint(Graphics g) 
	{
		// on efface la page
		super.paint(g);
		
		g.drawLine(0, this.getHeight()-(62-TAILLE_BALLE), 400+TAILLE_BALLE, this.getHeight()-(62-TAILLE_BALLE));
		g.drawLine(400+TAILLE_BALLE, 0, 400+TAILLE_BALLE, this.getHeight()-(92-TAILLE_BALLE));
		
		double tailleBalleJ1 = this.frame.getTailleBalle('1');
		double tailleBalleJ2 = this.frame.getTailleBalle('2');
		
		// on inverse axe Y
		double balleX = this.frame.getBallePersoCoordonnees('x', '1');
		double balleY = this.getHeight() - (52+tailleBalleJ1) - this.frame.getBallePersoCoordonnees('y', '1');
		
		Balle[] tabBalleCoul = this.frame.getTabBalle();
		
		g.setColor( Color.BLACK );

		// on affiche la balle J1
//		g.fillOval(balleX, balleY, tailleBalleJ1, tailleBalleJ1);
		( ( Graphics2D ) g).fill( new Ellipse2D.Double(balleX, balleY, tailleBalleJ1, tailleBalleJ1) );
		( ( Graphics2D ) g).drawString("J1", (float) balleX, (float) (balleY-tailleBalleJ1/2));
		
		double balleJ2x = this.frame.getBallePersoCoordonnees('x', '2');
		if( balleJ2x >= 0 )
		{
			double balleJ2y = this.getHeight() - (52+tailleBalleJ2) - this.frame.getBallePersoCoordonnees('y', '2');
			
			g.setColor( new Color( 71, 0, 0 ));
//			g.fillOval(balleJ2x, balleJ2y, tailleBalleJ2, tailleBalleJ2);
			( ( Graphics2D ) g).fill( new Ellipse2D.Double(balleJ2x, balleJ2y, tailleBalleJ2, tailleBalleJ2) );
//			g.drawString("J2", balleJ2x, balleJ2y-5);
			( ( Graphics2D ) g).drawString("J2", (float) balleJ2x, (float) (balleJ2y-tailleBalleJ2/2));
		}
		
		for ( int cpt = 0; cpt < tabBalleCoul.length; cpt++ )
		{
			if ( tabBalleCoul[cpt] == null ) continue;
			
			double balleCoulx = tabBalleCoul[cpt].x;
			double balleCouly = tabBalleCoul[cpt].y;
			
//			System.out.println("balle " + cpt + " x: " + balleCoulx + " y " + balleCouly );
			Color coulTemp = Color.CYAN;
			
			switch( tabBalleCoul[cpt].getCoul() )
			{
				case 'b': coulTemp = Color.BLUE      ;break;
				case 'v': coulTemp = Color.GREEN     ;break;
				case 'o': coulTemp = Color.ORANGE    ;break;
				case 'r': coulTemp = Color.RED       ;break;
				case 'n': coulTemp = Color.BLACK     ;break;
				case 'w': coulTemp = Color.WHITE     ;break;
				case 'm': coulTemp = Color.MAGENTA   ;break;
				case 'g': coulTemp = Color.LIGHT_GRAY;break;
			}
			
			g.setColor( coulTemp );			
			( ( Graphics2D ) g).fill( new Ellipse2D.Double(balleCoulx, balleCouly, TAILLE_BALLE, TAILLE_BALLE) );
		}
		
		if ( this.frame.getFinish() )
		{
			String sJTemp = this.frame.getJoueurGagne() == 1 ? "joueur 1" : "joueur 2";
					
			g.setColor(Color.RED);
			g.drawString("Game Over", 170, 270 );
			
			if( this.frame.getBallePersoCoordonnees('x', '2') >= 0)
			{
				g.setColor( new Color( 2, 114, 27) ); //vert fonce
				g.drawString("Victoire du " + sJTemp, 155, 290);
			}
			
			this.frame.displayRetryButton();
			this.repaint();
		}
		
		if( !this.frame.getFinish() )
			this.timeInSec = (int) (this.frame.getLunchTime()/1000000000);
		
		g.setColor(Color.BLACK);
		
		if( this.timeInSec > 60 )
			g.drawString("time: " + timeInSec/60 + ":" + (this.timeInSec-( 60 * (timeInSec/60))) + " (s)", 150, 10);
		else
			g.drawString("time: " + timeInSec + " (s)", 150, 10);
		
		g.drawString("Niveaux: "   + this.frame.getNiveaux()  ,                  0, 10);
		g.drawString("HighScore: " + this.frame.getHighScore(), this.getWidth()-95, 10);
		
		g.drawString("fl_droite = droite", 5, this.getHeight()-25);
		g.drawString("fl_gauche = gauche", 5, this.getHeight()-10);
		
		if ( this.frame.getBallePersoCoordonnees('x', '2') >= 0 )
		{
			g.drawString("q = gauche", this.getWidth()-75, this.getHeight()-25);
			g.drawString("d = droite", this.getWidth()-75, this.getHeight()-10);
		}
	}
}
