package moteur.traitement.management.managers.evenement;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import log.LoggerUtility;

import moteur.donnees.Civilisation;
import moteur.donnees.Evenement;

/**
 * Gestionnaire des {@link Evenement} historiques de la simulation.
 *
 * <p>
 * Cette classe conserve une liste des événements historiques,
 * permet de les ajouter, vérifier leur existence, et déclencher
 * ceux qui sont pertinents pour une {@link Civilisation} donnée
 * à une année précise.
 * </p>
 *
 * <p>Utilise {@link Logger} pour consigner le déclenchement des événements.</p>
 *
 * @author Alexandre
 * @version 1.1
 *
 * @see Evenement
 * @see Civilisation
 */
public class EvenementHistoriqueManager implements Iterable<Evenement> {

	/**
	 * Liste des événements historiques
	 */
	private final ArrayList<Evenement> evenementsHistoriques = new ArrayList<>();

	/**
	 * Logger pour consigner les événements déclenchés
	 */
	private static final Logger logger = LoggerUtility.getLogger(EvenementHistoriqueManager.class, "html");

	/**
	 * Constructeur par défaut.
	 * Initialise un gestionnaire vide d'événements historiques.
	 */
	public EvenementHistoriqueManager() {
		super();
	}

	/**
	 * Retourne le nombre d'événements historiques présents dans le gestionnaire.
	 *
	 * @return nombre d'événements historiques
	 */
	public int getNbEvenementsHistoriques() {
		return evenementsHistoriques.size();
	}

	/**
	 * Vérifie si un événement historique donné est déjà présent.
	 *
	 * @param evenement événement à vérifier
	 * @return {@code true} si l'événement est présent, {@code false} sinon
	 */
	public boolean contientEvenementHistorique(Evenement evenement) {
		return evenementsHistoriques.contains(evenement);
	}

	/**
	 * Ajoute un événement historique au gestionnaire.
	 *
	 * <p>
	 * Ne fait rien si l'événement est {@code null} ou déjà présent.
	 * </p>
	 *
	 * @param evenement événement à ajouter
	 */
	public void ajouterEvenementHistorique(Evenement evenement) {
		if (evenement != null && !contientEvenementHistorique(evenement)) {
			evenementsHistoriques.add(evenement);
		}
	}

	/**
	 * Vérifie tous les événements historiques et déclenche ceux qui
	 * sont pertinents pour la civilisation à l'année donnée.
	 *
	 * @param anneeActuelle année courante de la simulation
	 * @param civilisation civilisation concernée par les événements
	 */
	public void verifierEvenementsHistoriques(int anneeActuelle, Civilisation civilisation) {
		for (Evenement evenement : evenementsHistoriques) {
			verifierEvenement(anneeActuelle, civilisation, evenement);
		}
	}

	/**
	 * Vérifie un événement spécifique et le déclenche si l'année
	 * correspond à son année de début.
	 *
	 * @param anneeActuelle année courante de la simulation
	 * @param civilisation civilisation concernée
	 * @param evenement événement à vérifier
	 */
	private void verifierEvenement(int anneeActuelle, Civilisation civilisation, Evenement evenement) {
		int anneeDebutEvenement = evenement.getAnneeDebut();
		if (anneeActuelle == anneeDebutEvenement) {
			civilisation.ajouterEvenement(evenement);
			logger.info("Événement déclenché en " + anneeActuelle + " : \""
					+ evenement.getNom() + "\" (type=" + evenement.getType()
					+ ", région=" + evenement.getRegion().getNom() + ")");
		}
	}

	/**
	 * Retourne un itérateur sur la liste des événements historiques.
	 *
	 * @return {@link Iterator} des {@link Evenement} historiques
	 */
	@Override
	public Iterator<Evenement> iterator() {
		return evenementsHistoriques.iterator();
	}

}