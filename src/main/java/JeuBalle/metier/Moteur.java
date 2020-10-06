package JeuBalle.metier;

import JeuBalle.Controleur;
import JeuBalle.metier.jeu.Jeu;

public class Moteur implements Runnable
{
	/**
	 * le jeu a lancer
	 */
	private Jeu jeu;
	private Controleur ctrl;
	private int nbRepet;

	/**
	 * constructeur de moteur
	 * 
	 * @param j
	 *            le jeu a executer
	 */
	public Moteur(Jeu j, Controleur ctrl) 
	{
		this.jeu = j;
		this.ctrl = ctrl;
		this.nbRepet = 0;
	}

	/**
	 * lance le jeu pendant un certain temps
	 * 
	 * @param nbIter
	 *            le nombre d'iterations
	 * @param dureeBoucleMs
	 *            la duree d'une iteration en mSecondes
	 */
	public int lancerBoucle(int nbIter, long dureeBoucleMs) 
	{
		for (int i = 0; i < nbIter; i++) 
		{
			long dateDebut = System.nanoTime();
			
			double dt = (1.0 * dureeBoucleMs) / 1000;
			jeu.update(dt, false);
			this.ctrl.maj();

			// apres le render en nanos
			long dateFin = System.nanoTime();

			// duree en nanos
			long dureeEcoulee = dateFin - dateDebut;
			long dureeNs = dureeBoucleMs * 1000000L;
			long dureeRestante = dureeNs - dureeEcoulee;
//			System.out.println("doit attendre" + dureeRestante / 1000L);

			this.attendre(dureeRestante);
			
			if ( this.jeu.getFinish() ) return 1;
		}
		
		return 0;
	}

	/**
	 * on attend la duree restante
	 * 
	 * @param dureeRestante
	 *            duree a attendre
	 * 
	 * @throws AssertionError
	 *             en cas de soucis de temps
	 */
	private void attendre(long dureeRestante) throws AssertionError 
	{
		// attend dans le vide
		long debutAttente = System.nanoTime();
		
//		if (dureeRestante < 0)
//			throw new AssertionError("pas assez de vitesse");
		
		// on attend le temps qu'il faut
		while (System.nanoTime() - debutAttente < dureeRestante) {}
	}
	
	public void setNbRep( int nbRep )
	{
		this.nbRepet = nbRep;
	}

	@Override
	public void run()
	{
		this.jeu.setTime( System.nanoTime() );
		while( !this.jeu.getFinish() && this.jeu.getNiveaux() < 100 )
		{
			this.lancerBoucle( 50 + this.jeu.getNiveaux()*15, (long) 60 );
			this.jeu.niveauxSup();
		}
	}
}
