package JeuBalle.metier.balle;

public class Balle 
{
	/**
	 * position de la balle
	 */
	public double x, y;

	/**
	 * vitesse de la balle
	 */
	public double vx, vy;

	/**
	 * acceleration de la balle
	 */
	public double ax, ay;
	
	private char dir;
	private char coul;
	private boolean malus;
	private boolean bonus;
	
	private int rigthWall;

	private Balle autreJ;

	/**
	 * constructeur qui initialise les attributs
	 */
	public Balle( int rigthWall ) 
	{
		// position initiale
		x = 0;
		y = 0;
		
		// vitesse initiale (en pixel/secondes)
		vx = 100;
		vy = 400;
		
		// acceleration initiale (en pixel/s carre)
		ax = 0;
		ay = -800;
		
		dir  = 'd';
		coul = 'b';
		
		this.malus = false;
		this.bonus = false;
		
		this.rigthWall = rigthWall;
	}
	
	public Balle( int x, int y, int rigthWall )
	{
		this( rigthWall );
		
		this.x = x;
		this.y = y;
	}
	
	public Balle( int x, int y, int level, int rigthWall )
	{
		this(x, y, rigthWall);
		
		this.vy = (int) (Math.random()* (200 + level*10) );
		
		if( level >= 1 && level <= 3 )
			coul = 'b';
		else if ( level > 3 && level <= 6 )
			coul = ((int) (Math.random() * 101) ) <= 80 ? 'b': 'v';
		else if ( level > 6 && level <= 10 )
		{
			int tmp = (int) (Math.random()*101);
			
			coul = tmp <= 70 ? 'b': tmp <= 90 ? 'v' : 'o';
		}
		else if ( level > 10 && level <= 15 )
		{
			int tmp = (int) (Math.random()*101);
			
			if ( tmp <= 50 )
				coul = 'b';
			else if( tmp <= 80 )
				coul = 'v';
			else if ( tmp <= 95 )
				coul = 'o';
			else
				coul = 'r';
		}
		else if ( level > 15 && level <= 20 )
		{
			int tmp = (int) (Math.random()*101);
			
			if ( tmp <= 40 )
				coul = 'b';
			else if( tmp <= 60 )
				coul = 'v';
			else if ( tmp <= 85 )
				coul = 'o';
			else if ( tmp <= 95 )
				coul = 'r';
			else
				coul = 'n';
		}
		else if ( level > 20 )
		{
			int tmp = (int) (Math.random()*101);
			
			if ( tmp <= 20 )
				coul = 'b';
			else if( tmp <= 50 )
				coul = 'v';
			else if ( tmp <= 80 )
				coul = 'o';
			else if ( tmp <= 90 )
				coul = 'r';
			else if ( tmp <= 99 )
				coul = 'n';
			else
				coul = 'w';
		}
		
		if( level > 10 && ((int) (Math.random()*101)) > 98 )
		{
			this.malus = true;
			this.coul  = 'm';
		}
		
		if( level > 11 && ((int) (Math.random()*150)) > 148 )
		{
			this.bonus = true;
			this.coul  = 'g';
		}
		
	}
	
	public boolean isMalus()
	{
		return this.malus;
	}
	
	public boolean isBonus()
	{
		return this.bonus;
	}

	/**
	 * se charge de mettre � jour avec les lois de la physique
	 * 
	 * @param dt
	 *            pas de temps
	 */
	public void evoluer(double dt, double tailleBalleActuel) 
	{
//		x = x + vx * dt;
//		y = y + vy * dt;
//		vx = vx + ax * dt;
//		vy = vy + ay * dt;
//
//		// si on touche la sol, on rebondit
		if (y < 0)
			vy = -vy;
//		
//		if ( y > 600 )
//			vy = -vy;
//		
//		//si on touche un des bords verticaux
//		if ( x > (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth()-10 )
//			vx = -vx;
//		
//		if ( x < 0 )
//			vx = -vx;
		double xTemp = x;
		
		if ( dir == 'd' )
		{
			x = x + vx * dt;
		}
		
		if ( dir == 'q' )
		{
			x = x - vx * dt;
		}
		
//		if ( dir == 'z' )
//		{
//			x = x + vx * dt;
//			y = y + vy * dt;
//			vx = vx + ax * dt;
//			vy = vy + ay * dt;
//		}
		
		if( this.autreJ != null )
			if( this.x >= this.autreJ.x-(tailleBalleActuel/1.3) &&  this.x <= this.autreJ.x+(tailleBalleActuel/1.3) )
				this.x = xTemp;
		
		x = x <   0 ?   0 : x;
		x = x > this.rigthWall ? this.rigthWall : x;
		
		vy = vy > 600 ? 600 : vy;
	}
	
	public void evoluer( double dt, String dir, Balle autreJ, double tailleBalleActuel )
	{
		this.autreJ = autreJ;
		this.dir    = dir.toLowerCase().charAt(0);
		
		this.evoluer(dt, tailleBalleActuel);
	}

	public void evoluerTomber( double dt ) 
	{
		int temp = 0;
		
		switch ( coul )
		{
			case 'g': temp =  10;break;
			case 'v': temp =  40;break;
			case 'o': temp =  80;break;
			case 'r': temp = 120;break;
			case 'n': temp = 160;break;
			case 'w': temp = 200;break;
			case 'm': temp = 500;break;
		}
		
		y  =  y + vy * dt;
		vy = vy + (100+temp) * dt;
		
	}

	/**
	 * @return the coul
	 */
	public char getCoul()
	{
		return coul;
	}

}

