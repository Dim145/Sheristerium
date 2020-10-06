package JeuBalle.metier.jeu;

/**
 * interface qui définit un jeu
 * 
 * @author Dimitri Dubois
 * 
 */

public interface Jeu
{
	/**
	 * la methode en charge de la mise a jour des donnees
	 * 
	 * @param dt
	 *            pas de temps
	 */
	public abstract void update(double dt, boolean bBalleMove);

	/**
	 * la methode en charge de l'affichage
	 */
//	public abstract void render();
	
	public abstract boolean getFinish();

	public abstract int getNiveaux();
	
	public abstract void niveauxSup();
	
	public void setTime(long time);
}
