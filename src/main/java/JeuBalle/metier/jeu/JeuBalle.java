package JeuBalle.metier.jeu;

import JeuBalle.metier.GestionFichier;
import JeuBalle.metier.balle.Balle;

import java.util.ArrayList;

/**
 * la classe jeu Balle est un jeu avec une balle qui rebondit
 * 
 * @author vthomas
 * 
 */

public class JeuBalle implements Jeu 
{
	public static double taille_balleJ1 = 10;
	public static double taille_balleJ2 = 10;
	public static double taille_balle   = 10;
	
	private int width;
	private int height;
	
	/**
	 * balle contenue dans le jeu
	 */
	private Balle balle; //j1
	private Balle balleJ2;
	
	
	private ArrayList<Balle> tabBalleCoul;
	
	private boolean gameFinish;
	
	private int joueurGagne;
	private int niveaux;
	private int highScore;
	
	private long time;
	
	public JeuBalle( int[] tabDimensions ) 
	{		
		//on construit une balle
		this.balle        = new Balle( tabDimensions[0] );
		this.tabBalleCoul = new ArrayList<Balle>();
		
		this.time        = 0;
		this.joueurGagne = 0;
		this.gameFinish  = false;
		this.niveaux     = 1;
		this.highScore   = GestionFichier.lireScore();
		
		for( int cpt = 0; cpt < this.niveaux; cpt++ )
			this.tabBalleCoul.add( new Balle( (int) (Math.random()*400), 20, this.niveaux, tabDimensions[0] ) );
		
		this.width  = tabDimensions[0];
		this.height = tabDimensions[1];
	}

	/**
	 * mettre a jour le modele du monde consiste a faire evoluer la balle
	 * 
	 * @param dt
	 *            le pas de temps d'evolution
	 */
	public void update(double dt, boolean bBalleMove) 
	{		
		if ( bBalleMove )
		{
			this.balle  .evoluer(dt, JeuBalle.taille_balleJ2);
			this.balleJ2.evoluer(dt, JeuBalle.taille_balleJ1);
		}
		
		while ( this.niveaux - this.tabBalleCoul.size() > 0 )
			this.tabBalleCoul.add( new Balle( (int) (Math.random()*this.width), 10, this.niveaux, this.width ) );
		
		for( int cpt = 0; cpt < this.tabBalleCoul.size(); cpt++ )
		{			
			if ( this.tabBalleCoul.get(cpt).y < 522 )
				this.tabBalleCoul.get(cpt).evoluerTomber(dt);
			else
				this.tabBalleCoul.remove(cpt);
		}
		
		for( int cpt = 0; cpt < this.tabBalleCoul.size(); cpt++ )
		{			
			if ( this.tabBalleCoul.get(cpt).x+(JeuBalle.taille_balle/2) >= this.balle.x-(JeuBalle.taille_balleJ1/2) && this.tabBalleCoul.get(cpt).x-(JeuBalle.taille_balle/2) <= this.balle.x+(JeuBalle.taille_balleJ1/2))
			{
				if ( this.tabBalleCoul.get(cpt).y+(JeuBalle.taille_balle/2) >= this.height - 52 - this.balle.y-(JeuBalle.taille_balleJ1/2) && this.tabBalleCoul.get(cpt).y-(JeuBalle.taille_balle/2) <= this.height - 52 - this.balle.y+(JeuBalle.taille_balleJ1/2) )
				{
					if( this.tabBalleCoul.get(cpt).isMalus() && this.taille_balleJ1 < 20 )
					{
						JeuBalle.taille_balleJ1++;
					}
					else if ( this.tabBalleCoul.get(cpt).isBonus() && this.taille_balleJ1 > 5 )
					{
						JeuBalle.taille_balleJ1--;
					}
					else
					{
						this.gameFinish  = true;
						this.joueurGagne = 2; // joueur 1 = perdu
						
						if ( this.niveaux > this.highScore )
							GestionFichier.enregistrerScore(this.niveaux);
					}
				}
			}
			
			if( this.balleJ2 != null )
				if ( this.tabBalleCoul.get(cpt).x+(JeuBalle.taille_balle/2) >= this.balleJ2.x-(JeuBalle.taille_balleJ2/2) && this.tabBalleCoul.get(cpt).x-(JeuBalle.taille_balle/2) <= this.balleJ2.x+(JeuBalle.taille_balleJ2/2))
				{
					if ( this.tabBalleCoul.get(cpt).y+(JeuBalle.taille_balle/2) >= this.height - 52 - this.balleJ2.y-(JeuBalle.taille_balleJ2/2) && this.tabBalleCoul.get(cpt).y-(JeuBalle.taille_balle/2) <= this.height - 52 - this.balleJ2.y+(JeuBalle.taille_balleJ2/2) )
					{
						if( this.tabBalleCoul.get(cpt).isMalus() && this.taille_balleJ2 < 20 )
						{
							JeuBalle.taille_balleJ2++;
						}
						else if ( this.tabBalleCoul.get(cpt).isBonus() && this.taille_balleJ2 > 5 )
						{
							JeuBalle.taille_balleJ2--;
						}
						else
						{
							this.gameFinish  = true;
							this.joueurGagne = 1; // joueur 2 = perdu
							
							if ( this.niveaux > this.highScore )
								GestionFichier.enregistrerScore(this.niveaux);
						}
					}
				}
		}
	}
	
	public static double getTailleBalle( char c)
	{
		return c == '2' ? JeuBalle.taille_balleJ2 : JeuBalle.taille_balleJ1;
	}
	
	public void setTime(long time)
	{
		this.time = time;
	}
	
	public long getLunchTime()
	{
		return System.nanoTime() - this.time;
	}
	
	public int getJoueurGagne()
	{
		return this.joueurGagne;
	}
	
	public void niveauxSup()
	{ 
		this.niveaux++;
	}
	
	public int getHighScore()
	{
		return this.highScore;
	}
	
	public int getNiveaux()
	{
		return this.niveaux;
	}
	
	public boolean getFinish()
	{
		return this.gameFinish;
	}

	public double getBallePersoCoordonnees( char c, char j )
	{
		Balle temp = j == '2' ? this.balleJ2 : this.balle;
		
		if ( temp == null ) return -1.0;
				
		switch (c)
		{
			case 'x': return temp.x;
			case 'y': return temp.y;
		}
		
		return 0.0;
	}

	public Balle[] getTabBalle()
	{
		return this.tabBalleCoul.toArray(  new Balle[this.tabBalleCoul.size()] );
	}

	public void evoluerBallePerso( double d, String string, char joueur )
	{
		if ( !this.gameFinish )
		{
			if( joueur == '1' )
				this.balle.evoluer(d, string, this.balleJ2, JeuBalle.taille_balleJ1);
			else if ( joueur == '2' && this.balleJ2 != null )
				this.balleJ2.evoluer(d, string, this.balle, JeuBalle.taille_balleJ2);
		}
	}

	public void initJ2()
	{
		this.balleJ2 = new Balle(300, 0, this.width );		
	}
}