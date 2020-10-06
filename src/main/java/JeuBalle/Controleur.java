package JeuBalle;

import JeuBalle.ihm.FirstFrame;
import JeuBalle.metier.Moteur;
import JeuBalle.metier.balle.Balle;
import JeuBalle.metier.jeu.Jeu;
import JeuBalle.metier.jeu.JeuBalle;
import music.SoundMp3;

public class Controleur implements Runnable
{
	private Jeu j;
	
	private final Thread     tIhm;
	private final FirstFrame ihm;
	
	private Thread tMoteur;
	private final Thread tSon;
	
	private boolean bLoseSoundLaunch;
	private boolean bMusicOn;
	private boolean bPause;

	public Controleur() throws Exception
	{
		this.j = new JeuBalle( FirstFrame.getDimensions() );
		
		this.ihm  = new FirstFrame(this);
		this.tIhm = new Thread( this.ihm );

		this.tMoteur = new Thread( new Moteur(j, this) );
		this.tSon    = new Thread( new SoundMp3("/Transforyou_couper.mp3", true) );
		
		this.bLoseSoundLaunch = false;
		this.bMusicOn         = true;
		this.bPause           = false;
	}
	
	public void pausResume()
	{
		if(!this.bPause) 
		{
			this.tMoteur.suspend();
			
			if( this.bMusicOn )
				this.tSon.suspend();
		}
		else  
		{
			this.tMoteur.resume();
			
			if( this.bMusicOn && !this.getFinish() )
				this.tSon.resume();
		}
		
		this.bPause = !this.bPause;
	}
	
	public boolean isbPause()
	{
		return this.bPause;
	}
	
	public void runEngine()
	{
		this.tMoteur.start();
		this.tSon.start();
		
		if( !this.bMusicOn )
			this.tSon.suspend();
	}
	
	public void relancerPartie()
	{
		if( ((JeuBalle) this.j).getBallePersoCoordonnees('x', '2') != -1 )
		{
			this.j = new JeuBalle( FirstFrame.getDimensions() );
			this.initJ2();
		}
		else
		{
			this.j = new JeuBalle( FirstFrame.getDimensions() );
		}
		
		this.tMoteur = new Thread( new Moteur(j, this) );
		this.bLoseSoundLaunch = false;
		this.bPause           = false;
		
		this.ihm.reset();
		tMoteur.start();
		
		if( this.bMusicOn )
			this.tSon.resume();
	}

	public void evoluerBallePerso( double d, String string, char joueur )
	{
		( (JeuBalle) j ).evoluerBallePerso( d, string, joueur );
	}
	
	public long getLunchTime()
	{
		return ((JeuBalle) this.j).getLunchTime();
	}
	
	public int getJoueurGagne()
	{
		return ((JeuBalle) this.j).getJoueurGagne();
	}

	public double getBallePersoCoordonnees( char c, char joueur )
	{
		return ( (JeuBalle) j ).getBallePersoCoordonnees( c, joueur );
	}

	public Balle[] getTabBalle()
	{
		return ( (JeuBalle) j ).getTabBalle();
	}

	public int getNiveaux()
	{
		return this.j.getNiveaux();
	}
	
	public int getHighScore()
	{
		return ((JeuBalle) j).getHighScore();
	}
	
	public void maj()
	{
		this.ihm.run();
	}
	
	public static void main( String[] arg ) throws Exception
	{
		new Thread ( new Controleur() ).start();
	}

	public boolean getFinish()
	{
		if( this.j.getFinish() && !this.bLoseSoundLaunch && this.bMusicOn )
		{
			this.tSon.suspend();
			
			Thread temp = null;
			try
			{
				temp = new Thread( new SoundMp3("/j_perdu2.mp3", false) );
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			assert temp != null;
			temp.start();
			
			this.bLoseSoundLaunch = true;
		}
		
		return this.j.getFinish();
	}

	@Override
	public void run()
	{
		this.tIhm.start();
	}

	public void initJ2()
	{
		((JeuBalle) j).initJ2();
	}

	public void switchMusic()
	{
		if( this.bMusicOn )
		{
			this.tSon.suspend();
		}
		else
		{
			if( !this.getFinish())
				this.tSon.resume();
		}
		
		this.bMusicOn = !this.bMusicOn;
		
	}
	
	public boolean getMusicOn()
	{
		return this.bMusicOn;
	}

	public double getTailleBalle( char c )
	{
		return JeuBalle.getTailleBalle(c);
	}

}
