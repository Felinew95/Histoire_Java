package moteur.traitement.management.simulation;

import moteur.donnees.Evenement;
import moteur.traitement.management.managers.civilisation.CivilisationManager;
import config.SimConfig;

import java.util.List;

/**
 * Moteur principal de la simulation gérant l'avancement temporel et l'ordre des mises à jour.
 * * <p>Cette classe fait office de chef d'orchestre : elle déclenche les tours de jeu (rounds) 
 * pour l'ensemble des civilisations présentes et notifie l'interface utilisateur des 
 * changements d'état via l'interface {@link SimulationListener}.</p>
 */
public class SimulationEngine {

    /** Gestionnaire de la civilisation contrôlée par l'utilisateur ou considérée comme principale. */
    private final CivilisationManager civPrincipale;

    /** Liste des gestionnaires représentant les civilisations concurrentes ou adverses. */
    private final List<CivilisationManager> civAdverses;

    /** Écouteur permettant de lier la logique métier aux mises à jour de l'interface graphique. */
    private final SimulationListener listener;

    /**
     * Initialise une nouvelle instance du moteur de simulation.
     * * @param civPrincipale Le gestionnaire de la civilisation principale.
     * @param civAdverses   La liste des gestionnaires pour les civilisations adverses.
     * @param listener      L'instance du listener pour les retours visuels (peut être null).
     */
    public SimulationEngine(CivilisationManager civPrincipale, List<CivilisationManager> civAdverses, SimulationListener listener) {
        this.civPrincipale = civPrincipale;
        this.civAdverses = civAdverses;
        this.listener = listener;
    }
    
    public CivilisationManager getCivPrincipale() {
		return civPrincipale;
	}

	/**
     * Exécute la logique d'une année de simulation (un tour) pour toutes les entités.
     * * <p>Le processus suit l'ordre suivant :
     * <ol>
     * <li>Mise à jour de la civilisation principale.</li>
     * <li>Mise à jour séquentielle de chaque civilisation adverse.</li>
     * <li>Notification du listener pour mettre à jour le temps, les statistiques, la narration et la carte.</li>
     * <li>Vérification de la condition de fin de simulation.</li>
     * </ol>
     * </p>
     */
    public void nextTurn() {
        if (!estTerminee()) {
            civPrincipale.nextRound();
            
            for (CivilisationManager simAdverse : civAdverses) {
                simAdverse.nextRound();
            }

            if (listener != null) {
                int annee = civPrincipale.getAnneeActuelle();
                
               
                listener.onMiseAJourTemps(annee);
                
              
                listener.onMiseAJourStatistiques();
                
                
                int nbEvents = civPrincipale.getCivilisation().getNbEvenements();
                Evenement dernierEvent = (nbEvents > 0) ? civPrincipale.getCivilisation().getEvenement(nbEvents - 1) : null;
                
                listener.onMiseAJourNarrationEtCarte(annee, dernierEvent);
            }
        }
        
   
        if (estTerminee() && listener != null) {
            listener.onSimulationTerminee();
        }
    }

    /**
     * Vérifie si la simulation a atteint ou dépassé l'année de fin configurée.
     * * @return {@code true} si l'année actuelle est supérieure ou égale à l'année de fin 
     * définie dans {@link SimConfig#ANNEE_FIN_SIM}, sinon {@code false}.
     */
    public boolean estTerminee() {
        return civPrincipale.getAnneeActuelle() >= SimConfig.ANNEE_FIN_SIM;
    }
}