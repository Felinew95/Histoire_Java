package tests.unit.managers;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import moteur.donnees.Armee;
import moteur.donnees.Civilisation;
import moteur.donnees.Economie;
import moteur.donnees.Evenement;
import moteur.donnees.Kersir;
import moteur.donnees.Konungr;
import moteur.donnees.Politique;
import moteur.donnees.Population;
import moteur.donnees.Religion;
import moteur.traitement.management.managers.evenement.EvenementHistoriqueManager;

/**
 * Suite de tests unitaires pour {@link EvenementHistoriqueManager}.
 * <p>
 * Cette classe valide la gestion du cycle de vie des événements historiques :
 * <ul>
 * <li>L'initialisation correcte du gestionnaire.</li>
 * <li>L'ajout sécurisé (gestion des nuls et des doublons).</li>
 * <li>Le bon fonctionnement de l'itérateur pour le parcours des données.</li>
 * <li>Le déclenchement effectif des événements sur une {@link Civilisation} selon l'année simulée.</li>
 * </ul>
 * </p>
 * 
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 1.1
 */
public class TestEvenementHistoriqueManager {

    /** 
     * Instance du manager d'événements à tester. 
     */
    private EvenementHistoriqueManager manager;
    
    /** 
     * Échantillon d'événement ponctuel. 
     */
    private Evenement evenement793;
    
    /** 
     * Échantillon d'événement sur une période. 
     */
    private Evenement evenement900;
    
    /**
     * Civilisation à tester 
     */
    private Civilisation civilisation;

    /**
     * Prépare l'environnement de test avant chaque méthode.
     * 
     * <p>
     * Initialise le manager et crée deux instances d'{@link Evenement} avec des signatures distinctes.
     * Note : L'ordre des paramètres respecte {@code (nom, debut, fin, region, narration, type)}.
     * </p>
     */
    @Before
    public void prepare() {
        manager = new EvenementHistoriqueManager();

        evenement793 = new Evenement("Raid de Lindisfarne", 793, 793, null, "Un raid viking historique.", "Raid");
        evenement900 = new Evenement("Commerce baltique", 900, 950, null, "Échanges commerciaux.", "Commerce");
        
        Konungr roi = new Konungr("Bjorn", 790, 850);
        Economie eco = new Economie();
        Armee armee = new Armee(new Kersir("Erik", 790, 810), 0, 0, 0, 100f);
        Population pop = new Population(1000, 50f, "HOMME");
        Religion rel = new Religion("Nordique", "Forte", 100f);
        Politique pol = new Politique("Tribal", 50f);

        civilisation = new Civilisation(roi, eco, armee, pop, rel, pol, "Viking", Color.RED, "⚔️");
    }

    /**
     * Vérifie que le manager est bien instancié.
     */
    @Test
    public void testManager_nonNull() {
        assertNotNull("Le manager ne doit pas être nul", manager);
    }

    /**
     * Vérifie que le manager ne contient aucun événement par défaut.
     */
    @Test
    public void testManager_videInitialement() {
        assertEquals("Le manager doit être vide à la création", 0, manager.getNbEvenementsHistoriques());
    }

    /**
     * Vérifie que l'ajout d'un événement incrémente correctement la taille interne.
     */
    @Test
    public void testAjouterEvenement_incrementeCompteur() {
        manager.ajouterEvenementHistorique(evenement793);
        assertEquals("Le manager doit contenir 1 événement après ajout", 1, manager.getNbEvenementsHistoriques());
    }

    /**
     * Vérifie que l'ajout d'une référence nulle est ignoré et ne provoque pas d'erreur.
     */
    @Test
    public void testAjouterEvenement_null_ignore() {
        manager.ajouterEvenementHistorique(null);
        assertEquals("Ajouter null ne doit pas changer le compteur", 0, manager.getNbEvenementsHistoriques());
    }

    /**
     * Vérifie que le manager n'accepte pas deux fois le même événement (unicité).
     */
    @Test
    public void testAjouterEvenement_doublon_ignore() {
        manager.ajouterEvenementHistorique(evenement793);
        manager.ajouterEvenementHistorique(evenement793);
        assertEquals("Un doublon ne doit pas être ajouté", 1, manager.getNbEvenementsHistoriques());
    }

    /**
     * Vérifie l'ajout de plusieurs événements distincts.
     */
    @Test
    public void testAjouterPlusieursEvenements() {
        manager.ajouterEvenementHistorique(evenement793);
        manager.ajouterEvenementHistorique(evenement900);
        assertEquals("Le manager doit contenir 2 événements distincts", 2, manager.getNbEvenementsHistoriques());
    }

    /**
     * Vérifie la capacité du manager à confirmer la présence d'un événement ajouté.
     */
    @Test
    public void testContientEvenement_apresAjout() {
        manager.ajouterEvenementHistorique(evenement793);
        assertTrue("Le manager doit contenir l'événement ajouté", manager.contientEvenementHistorique(evenement793));
    }

    /**
     * Vérifie que la recherche d'un événement non ajouté retourne faux.
     */
    @Test
    public void testContientEvenement_nonAjoute_retourneFalse() {
        assertFalse("Un événement non ajouté ne doit pas être contenu", manager.contientEvenementHistorique(evenement793));
    }

    /**
     * Vérifie que l'implémentation de l'itérateur permet de parcourir l'intégralité des données.
     */
    @Test
    public void testIterateur_parcoursTousLesEvenements() {
        manager.ajouterEvenementHistorique(evenement793);
        manager.ajouterEvenementHistorique(evenement900);

        int count = 0;
        for (Evenement e : manager) {
            assertNotNull("Chaque événement parcouru ne doit pas être nul", e);
            count++;
        }
        assertEquals("L'itérateur doit parcourir tous les événements", 2, count);
    }

    /**
     * Teste la non-activation des événements.
     * <p>Vérifie qu'aucun événement n'est transmis à la civilisation si l'année ne correspond pas.</p>
     */
    @Test
    public void testVerifierEvenements_mauvaiseAnnee_nAjoutePas() {
        manager.ajouterEvenementHistorique(evenement793);
        manager.verifierEvenementsHistoriques(800, civilisation);

        assertEquals("Aucun événement ne doit être ajouté pour une année incorrecte",
                0, civilisation.getNbEvenements());
    }
    
}