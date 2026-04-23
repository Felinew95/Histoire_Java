package moteur.traitement.management.simulation;

import moteur.donnees.Evenement;

/**
 * Interface de rappel (callback) permettant au moteur de simulation de communiquer 
 * avec l'interface utilisateur (IHM).
 * 
 * <p>
 * Elle définit les événements majeurs du cycle de vie d'une simulation auxquels 
 * l'interface graphique doit réagir pour rester synchronisée.
 * </p>
 * 
 * @author Tauseef
 * @version 2.1
 */
public interface SimulationListener {

    /**
     * Appelée à chaque progression chronologique de la simulation.
     * Permet de mettre à jour l'affichage de l'horloge ou du calendrier.
     * 
     * @param annee L'année actuelle atteinte par la simulation.
     */
    void onMiseAJourTemps(int annee);

    /**
     * Appelée lorsque les données chiffrées (indicateurs, ressources, scores) 
     * ont été recalculées et doivent être rafraîchies à l'écran.
     */
    void onMiseAJourStatistiques();

    /**
     * Appelée lorsqu'un événement spécifique survient. 
     * Déclenche la mise à jour du journal de narration et les modifications visuelles sur la carte.
     * 
     * @param annee     L'année à laquelle l'événement se produit.
     * @param evenement L'objet contenant les détails de l'événement déclenché.
     */
    void onMiseAJourNarrationEtCarte(int annee, Evenement evenement);

    /**
     * Appelée lorsque la simulation atteint son terme (fin du scénario, victoire ou défaite).
     * Utilisée pour afficher l'écran de fin ou bloquer les interactions.
     */
    void onSimulationTerminee();
    
}