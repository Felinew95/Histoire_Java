package tests.unit.managers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import moteur.donnees.Evenement;
import moteur.donnees.Politique;
import moteur.traitement.management.managers.politique.PolitiqueManager;

/**
 * Suite de tests unitaires pour {@link PolitiqueManager}.
 * 
 * <p>
 * Cette classe vérifie l'évolution de la stabilité politique, les changements de régimes 
 * et les bascules d'orientations diplomatiques ou militaires en fonction des événements 
 * historiques (Guerre, Diplomatie, Religion).
 * </p>
 *  
 * @author Tauseef
 * @author Alexandre 
 * 
 * @version 1.1
 */
public class TestPolitiqueManager {

    /** 
     * Objet de données représentant la politique d'une civilisation. 
     */
    private Politique politique;

    /**
     * Initialisation avant chaque test.
     * <p>Prépare une politique stable (50%) avec des orientations par défaut.</p>
     */
    @Before
    public void prepare() {
        politique = new Politique("Monarchie", 50f, "Neutre", "Mixte", "Équilibrée");
    }

    /**
     * Vérifie que la stabilité reste dans les bornes autorisées [0, 100] sans événement perturbateur.
     */
    @Test
    public void testUpdatePolitique_sansEvenement_stabiliteResteDansBornes() {
        PolitiqueManager.updatePolitique(politique, null);
        float stabilite = politique.getStabilite();
        assertTrue("La stabilité doit rester >= 0", stabilite >= 0f);
        assertTrue("La stabilité doit rester <= 100", stabilite <= 100f);
    }

    /**
     * Vérifie que la stabilité a tendance à s'améliorer naturellement en période de calme.
     */
    @Test
    public void testUpdatePolitique_sansEvenement_stabiliteAugmente() {
        float avant = politique.getStabilite();
        PolitiqueManager.updatePolitique(politique, null);
        assertTrue("Sans événement, la stabilité doit légèrement augmenter ou rester stable", 
                   politique.getStabilite() >= avant);
    }

    /**
     * Vérifie qu'un événement de type 'Guerre' impacte négativement la stabilité.
     */
    @Test
    public void testUpdatePolitique_guerre_diminueStabilite() {
        // Ordre constructeur : nom, debut, fin, region, narration, type
        Evenement guerre = new Evenement("Grande Armée", 866, 866, null, "Invasion.", "Guerre");
        float avant = politique.getStabilite();
        PolitiqueManager.updatePolitique(politique, guerre);
        assertTrue("Un événement Guerre doit diminuer la stabilité", politique.getStabilite() < avant);
    }

    /**
     * Vérifie que l'orientation militaire bascule en 'Offensive' lors d'un conflit.
     */
    @Test
    public void testUpdatePolitique_guerre_orientationMilitaireOffensive() {
        Evenement guerre = new Evenement("Grande Armée", 866, 866, null, "Invasion.", "Guerre");
        PolitiqueManager.updatePolitique(politique, guerre);
        assertEquals("Une guerre doit passer l'orientation militaire en Offensive",
                "Offensive", politique.getPolitiqueMilitaire());
    }

    /**
     * Vérifie que la politique diplomatique devient expansionniste en cas de guerre.
     */
    @Test
    public void testUpdatePolitique_guerre_orientationDiplomatiqueExpansionniste() {
        Evenement guerre = new Evenement("Grande Armée", 866, 866, null, "Invasion.", "Guerre");
        PolitiqueManager.updatePolitique(politique, guerre);
        assertEquals("Une guerre doit passer la diplomatie en Expansionniste",
                "Expansionniste", politique.getPolitiqueDiplomatique());
    }

    /**
     * Vérifie qu'un traité diplomatique améliore la stabilité du pays.
     */
    @Test
    public void testUpdatePolitique_diplomatie_augmenteStabilite() {
        Evenement diplo = new Evenement("Traité de paix", 850, 850, null, "Accord diplomatique.", "Diplomatie");
        float avant = politique.getStabilite();
        PolitiqueManager.updatePolitique(politique, diplo);
        assertTrue("Un événement Diplomatie doit augmenter la stabilité", politique.getStabilite() > avant);
    }

    /**
     * Vérifie que l'orientation diplomatique devient 'Pacifique' après un traité.
     */
    @Test
    public void testUpdatePolitique_diplomatie_orientationPacifique() {
        Evenement diplo = new Evenement("Traité de paix", 850, 850, null, "Accord diplomatique.", "Diplomatie");
        PolitiqueManager.updatePolitique(politique, diplo);
        assertEquals("Une diplomatie doit passer l'orientation en Pacifique",
                "Pacifique", politique.getPolitiqueDiplomatique());
    }

    /**
     * Vérifie le changement de régime politique vers une 'Monarchie chrétienne' sous l'influence religieuse.
     */
    @Test
    public void testUpdatePolitique_religion_avecStabiliteElevee_changementRegime() {
        politique.setStabilite(60f);
        Evenement religion = new Evenement("Conversion", 960, 960, null, "Christianisation.", "Religion");
        PolitiqueManager.updatePolitique(politique, religion);
        assertEquals("Avec stabilité >= 50 et événement Religion, le régime doit évoluer",
                "Monarchie chrétienne", politique.getTypeRegime());
    }

    /**
     * Vérifie que le moteur de calcul plafonne la stabilité à 100%.
     */
    @Test
    public void testUpdatePolitique_stabiliteNeDepassePas100() {
        politique.setStabilite(99f);
        Evenement diplo = new Evenement("Traité", 850, 850, null, "Accord.", "Diplomatie");
        for (int i = 0; i < 10; i++) {
            PolitiqueManager.updatePolitique(politique, diplo);
        }
        assertTrue("La stabilité ne doit jamais dépasser 100", politique.getStabilite() <= 100f);
    }

    /**
     * Vérifie que le moteur de calcul plancher la stabilité à 0%.
     */
    @Test
    public void testUpdatePolitique_stabiliteNeDescendPasEn_dessousDe0() {
        politique.setStabilite(1f);
        // Utilisation d'un type d'événement qui fait baisser la stabilité (Guerre ou Politique)
        Evenement guerre = new Evenement("Guerre totale", 866, 866, null, "Invasion.", "Guerre");
        for (int i = 0; i < 20; i++) {
            PolitiqueManager.updatePolitique(politique, guerre);
        }
        assertTrue("La stabilité ne doit jamais descendre en dessous de 0", politique.getStabilite() >= 0f);
    }

    /**
     * Vérifie que la description textuelle de la politique n'est jamais nulle.
     */
    @Test
    public void testGetEtatPolitiqueGlobal_nonNull() {
        String etat = PolitiqueManager.getEtatPolitiqueGlobal(politique);
        assertNotNull("La description de l'état politique ne doit pas être nulle", etat);
    }

    /**
     * Vérifie que la description textuelle contient des informations (non vide).
     */
    @Test
    public void testGetEtatPolitiqueGlobal_nonVide() {
        String etat = PolitiqueManager.getEtatPolitiqueGlobal(politique);
        assertFalse("La description de l'état politique ne doit pas être vide", etat.isEmpty());
    }
    
}