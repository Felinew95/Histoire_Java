package moteur.traitement.management;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import log.LoggerUtility;

import moteur.donnees.Civilisation;
import moteur.donnees.Evenement;

/**
 * Classe qui gère les événements historiques
 * 
 * @author Alexandre
 * @version 1.0
 */
public class EvenementHistoriqueManager implements Iterable<Evenement> {

	// Attributs 
	private final ArrayList<Evenement> evenementsHistoriques = new ArrayList<Evenement>();
	private static Logger logger = LoggerUtility.getLogger(EvenementHistoriqueManager.class, "html");
	
	/**
	 * Constructeur de la classe EvenementHistoriqueManager
	 */
	public EvenementHistoriqueManager() {
		super();
	}

	/**
	 * Getter de evenementsHistoriques
	 * 
	 * @return La liste des événements historiques 
	 */
	public ArrayList<Evenement> getEvenementsHistoriques() {
		return new ArrayList<Evenement>(evenementsHistoriques);
	}
	
	/**
	 * Méthode qui calcule le nombre d'événements historiques
	 * 
	 * @return Le nombre d'événements historiques
	 */
	public int getNbEvenementsHistoriques() {
		return evenementsHistoriques.size();
	}
	
	/**
	 * Méthode qui vérifie si un événement historique existe 
	 * 
	 * @param evenement : Une événement 
	 * @return true si un événement historique existe, false sinon
	 */
	public boolean contientEvenementHistorique(Evenement evenement) {
		return evenementsHistoriques.contains(evenement);
	}
	
	/**
	 * Méthode qui ajoute un événement historique 
	 * 
	 * @param evenement : Un événement 
	 */
	public void ajouterEvenementHistorique(Evenement evenement) {
		if (evenement != null && !contientEvenementHistorique(evenement)) {
			evenementsHistoriques.add(evenement);
		}
	}
	
	/**
	 * Méthode qui supprime un événement historique
	 * 
	 * @param evenement : Un événement 
	 */
	public void supprimerEvenementHistorique(Evenement evenement) {
		evenementsHistoriques.remove(evenement);
	}
	
	/**
	 * Méthode qui vérifie si les événements peuvent être déclenchés 
	 * 
	 * @param anneeActuelle : Année actuelle de la simulation 
	 * @param civilisation : La civilisation 
	 */
	public void verifierEvenementsHistoriques(int anneeActuelle, Civilisation civilisation) {
		for (Evenement evenement : evenementsHistoriques) {
			verifierEvenement(anneeActuelle, civilisation, evenement);
		}
	}

	/**
	 * Méthode qui vérifie si un événement peut être déclenché
	 *
	 * @param anneeActuelle : Année actuelle de la simulation 
	 * @param civilisation : La civilisation
	 * @param evenement : Un événement 
	 */
	private void verifierEvenement(int anneeActuelle, Civilisation civilisation, Evenement evenement) {
		int anneeDebutEvenement = evenement.getAnneeDebut();
		if (anneeActuelle == anneeDebutEvenement) {
			civilisation.ajouterEvenement(evenement);
			logger.info("Événement déclenché en " + anneeActuelle + " : \"" + evenement.getNom() + "\" (type=" + evenement.getType() + ", région=" + evenement.getRegion().getNom() + ")");
		}
	}

	@Override
	public Iterator<Evenement> iterator() {
		return evenementsHistoriques.iterator();
	}
	
}