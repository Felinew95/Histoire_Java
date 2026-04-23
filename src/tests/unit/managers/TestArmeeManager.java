package tests.unit.managers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import moteur.donnees.Armee;
import moteur.donnees.Evenement;
import moteur.donnees.Kersir;
import moteur.traitement.management.managers.armee.ArmeeManager;

/**
 * Tests unitaires pour {@link ArmeeManager}.
 * 
 * <p>
 * Cette classe vérifie la logique de mise à jour des effectifs militaires (guerriers, navires),
 * la progression des techniques et la cohérence de l'état de santé/moral de l'armée.
 * </p>
 * 
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 1.1
 */
public class TestArmeeManager {

    /** 
     * Instance d'armée utilisée pour les tests. 
     */
    private Armee armee;

    /**
     * Initialisation avant chaque test.
     * <p>Crée une armée standard avec 5000 guerriers, 500 navires et un moral à 50%.</p>
     */
    @Before
    public void prepare() {
        armee = new Armee(new Kersir("Bjorn", 790, 850), 5_000, 500, 10, 50f);
    }

    /**
     * Vérifie que l'armée n'est pas nulle après une mise à jour.
     */
    @Test
    public void testUpdateArmee_armeeNonNull_apresUpdate() {
        ArmeeManager.updateArmee(793, armee, null, "Viking");
        assertNotNull("L'armée ne doit pas être nulle après update", armee);
    }

    /**
     * Vérifie la robustesse du manager si une armée nulle est passée en paramètre.
     */
    @Test
    public void testUpdateArmee_armeNull_nestPasErreur() {
        ArmeeManager.updateArmee(793, null, null, "Viking");
    }

    /**
     * Vérifie que le nombre de guerriers ne devient jamais négatif lors d'une évolution naturelle.
     */
    @Test
    public void testUpdateArmee_guerriersNonNegatifs_sansEvenement() {
        for (int i = 0; i < 50; i++) {
            ArmeeManager.updateArmee(793 + i, armee, null, "Viking");
        }
        assertTrue("Le nombre de guerriers ne doit pas être négatif", armee.getNombreGuerriers() >= 0);
    }

    /**
     * Vérifie que même lors d'une guerre intense (simulation de 50 tours), 
     * le nombre de guerriers reste cohérent (>= 0).
     */
    @Test
    public void testUpdateArmee_guerriersNonNegatifs_evenementGuerre() {
        // Correction de l'ordre des paramètres : nom, debut, fin, region, narration, type
        Evenement guerre = new Evenement("Grande Armée", 866, 866, null, "Invasion massive.", "Guerre");
        for (int i = 0; i < 50; i++) {
            ArmeeManager.updateArmee(866, armee, guerre, "Viking");
        }
        assertTrue("Les guerriers ne doivent pas être négatifs en guerre", armee.getNombreGuerriers() >= 0);
    }

    /**
     * Vérifie que le recrutement ne permet pas de dépasser le plafond de 1 000 000 de soldats.
     */
    @Test
    public void testUpdateArmee_guerriersBorneMax() {
        armee.setNombreGuerriers(999_999);
        ArmeeManager.updateArmee(900, armee, null, "Viking");
        assertTrue("Le nombre de guerriers ne doit pas dépasser 1 000 000", armee.getNombreGuerriers() <= 1_000_000);
    }

    /**
     * Vérifie que la flotte navale reste positive sur le long terme.
     */
    @Test
    public void testUpdateArmee_naviresNonNegatifs_sansEvenement() {
        for (int i = 0; i < 50; i++) {
            ArmeeManager.updateArmee(793 + i, armee, null, "Viking");
        }
        assertTrue("Le nombre de navires ne doit pas être négatif", armee.getNombreNavires() >= 0);
    }

    /**
     * Vérifie que les événements d'exploration favorisent le maintien ou l'expansion de la flotte.
     */
    @Test
    public void testUpdateArmee_exploration_augmenteNavires() {
        int avant = armee.getNombreNavires();
        Evenement exploration = new Evenement("Vinland", 1000, 1000, null, "Exploration.", "Exploration");
        ArmeeManager.updateArmee(1000, armee, exploration, "Viking");
        assertTrue("L'exploration doit augmenter ou maintenir les navires", armee.getNombreNavires() >= avant);
    }

    /**
     * Vérifie que les techniques militaires restent dans la plage autorisée [0, 50].
     */
    @Test
    public void testUpdateArmee_techniquesMilitairesDansBornes() {
        Evenement diplomatie = new Evenement("Paix", 850, 850, null, "Accord.", "Diplomatie");
        for (int i = 0; i < 20; i++) {
            ArmeeManager.updateArmee(850, armee, diplomatie, "Viking");
        }
        assertTrue("Les techniques militaires doivent être >= 0", armee.getTechniquesMilitaire() >= 0);
        assertTrue("Les techniques militaires ne doivent pas dépasser 50", armee.getTechniquesMilitaire() <= 50);
    }

    /**
     * Vérifie que l'état global (moral/santé) reste un pourcentage entre 0 et 100.
     */
    @Test
    public void testUpdateArmee_etatEntre0Et100() {
        ArmeeManager.updateArmee(793, armee, null, "Viking");
        assertTrue("L'état doit être >= 0", armee.getEtat() >= 0f);
        assertTrue("L'état doit être <= 100", armee.getEtat() <= 100f);
    }

    /**
     * Vérifie le libellé de l'état pour un moral haut.
     */
    @Test
    public void testGetDescriptionEtat_enPleineForme() {
        armee.setEtat(80f);
        assertEquals("En pleine forme", ArmeeManager.getDescriptionEtat(armee));
    }

    /**
     * Vérifie le libellé de l'état pour un moral moyen.
     */
    @Test
    public void testGetDescriptionEtat_operationnelle() {
        armee.setEtat(60f);
        assertEquals("Opérationnelle", ArmeeManager.getDescriptionEtat(armee));
    }

    /**
     * Vérifie le libellé de l'état pour un moral bas.
     */
    @Test
    public void testGetDescriptionEtat_affaiblie() {
        armee.setEtat(35f);
        assertEquals("Affaiblie", ArmeeManager.getDescriptionEtat(armee));
    }

    /**
     * Vérifie le libellé de l'état pour un moral critique.
     */
    @Test
    public void testGetDescriptionEtat_demoralisee() {
        armee.setEtat(10f);
        assertEquals("Démoralisée", ArmeeManager.getDescriptionEtat(armee));
    }

    /**
     * Vérifie que la description retournée n'est jamais nulle.
     */
    @Test
    public void testGetDescriptionEtat_nonNull() {
        ArmeeManager.updateArmee(793, armee, null, "Viking");
        assertNotNull("La description de l'état ne doit pas être nulle", ArmeeManager.getDescriptionEtat(armee));
    }
}