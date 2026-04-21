package tests.unit.managers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import moteur.donnees.Evenement;
import moteur.donnees.Region;
import moteur.donnees.Religion;
import moteur.traitement.management.factory.SimFactory;
import moteur.traitement.management.managers.politique.ReligionManager;

/**
 * Suite de tests unitaires pour {@link ReligionManager}.
 * 
 * <p>
 * Cette classe valide la gestion de la ferveur et de l'influence religieuse, notamment :
 * <ul>
 * 		<li>Le maintien de l'influence dans les bornes de sécurité [5, 100].</li>
 * 		<li>L'impact des conflits (Guerre/Raid) qui renforcent l'influence nordique.</li>
 * 		<li>La mécanique de conversion religieuse vers le Christianisme lorsque l'influence païenne devient trop faible.</li>
 * 		<li>L'intégrité des données descriptives (nom, croyance) après mise à jour.</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 1.1
 */
public class TestReligionManager {

    /** 
     * Instance de religion utilisée pour les tests. 
     */
    private Religion religion;
    
    /** 
     * Région de référence pour le contexte des événements. 
     */
    private Region region;

    /**
     * Prépare l'environnement de test avant chaque méthode.
     * 
     * <p>
     * Initialise une religion de type "Paganisme nordique" avec une influence neutre de 50.
     * </p>
     */
    @Before
    public void prepare() {
        // signature buildReligion : nom, croyance, influence
        religion = SimFactory.buildReligion("Paganisme nordique", "Paganisme nordique", 50f);
        region = SimFactory.buildRegion("Scandinavie", "Ragnar");
    }

    /**
     * Vérifie que l'influence reste stable et bornée lors d'une évolution naturelle sans événement.
     */
    @Test
    public void testUpdateReligion_influenceResteDansBornes_sansEvenement() {
        for (int i = 0; i < 20; i++) {
            ReligionManager.updateReligion(religion, null);
        }
        assertTrue("L'influence doit rester >= 5", religion.getInfluence() >= 5f);
        assertTrue("L'influence doit rester <= 100", religion.getInfluence() <= 100f);
    }

    /**
     * Vérifie que le moteur de calcul empêche l'influence de descendre sous le seuil critique de 5%.
     */
    @Test
    public void testUpdateReligion_influenceNePasDescendreSous5() {
        religion.setInfluence(5f);
        Evenement evenement = new Evenement("Conversion au christianisme", 960, 960, region, "Christianisation.", "Religion");
        for (int i = 0; i < 10; i++) {
            ReligionManager.updateReligion(religion, evenement);
        }
        assertTrue("L'influence ne doit pas descendre sous 5", religion.getInfluence() >= 5f);
    }

    /**
     * Vérifie que l'influence est plafonnée à 100%, même en cas de succès militaires répétés.
     */
    @Test
    public void testUpdateReligion_influenceNeDepassePas100() {
        religion.setInfluence(99f);
        Evenement guerre = new Evenement("Grande Armée", 866, 866, region, "Invasion.", "Guerre");
        for (int i = 0; i < 10; i++) {
            ReligionManager.updateReligion(religion, guerre);
        }
        assertTrue("L'influence ne doit pas dépasser 100", religion.getInfluence() <= 100f);
    }

    /**
     * Vérifie qu'un état de guerre renforce l'influence de la religion nordique (ferveur guerrière).
     */
    @Test
    public void testUpdateReligion_guerre_augmenteInfluence() {
        float avant = religion.getInfluence();
        Evenement guerre = new Evenement("Raid de Lindisfarne", 793, 793, region, "Raid.", "Guerre");
        ReligionManager.updateReligion(religion, guerre);
        assertTrue("Un événement Guerre doit augmenter l'influence nordique",
                religion.getInfluence() >= avant);
    }

    /**
     * Vérifie que les raids victorieux boostent l'influence religieuse locale.
     */
    @Test
    public void testUpdateReligion_raid_augmenteInfluence() {
        float avant = religion.getInfluence();
        Evenement raid = new Evenement("Raid côtes irlandaises", 795, 795, region, "Raids côtiers.", "Raid");
        ReligionManager.updateReligion(religion, raid);
        assertTrue("Un événement Raid doit augmenter l'influence nordique",
                religion.getInfluence() >= avant);
    }

    /**
     * Vérifie que les rites nordiques anciens (avant 960) consomment de l'influence 
     * ou marquent une transition dans le moteur.
     */
    @Test
    public void testUpdateReligion_religion_preChristianisation_diminueInfluence() {
        religion.setInfluence(50f);
        Evenement evenement = new Evenement("Rite nordique", 800, 800, region, "Rite.", "Religion");
        ReligionManager.updateReligion(religion, evenement);
        assertTrue("Un événement Religion avant 960 doit diminuer l'influence nordique",
                religion.getInfluence() <= 50f);
    }

    /**
     * Teste la mécanique de bascule de croyance.
     * 
     * <p>
     * Si l'influence tombe sous les 20% lors d'un événement religieux historique, 
     * la civilisation doit adopter le Christianisme.
     * </p>
     */
    @Test
    public void testUpdateReligion_religion_sousSeuilInfluence_changeCroyance() {
        religion.setInfluence(15f);
        Evenement evenement = new Evenement("Conversion au christianisme", 960, 960, region, "Christianisation.", "Religion");
        ReligionManager.updateReligion(religion, evenement);
        assertEquals("Sous le seuil d'influence avec événement Religion, la croyance doit devenir Christianisme",
                "Christianisme", religion.getCroyance());
    }

    /**
     * Vérifie que le nom de la religion reste intègre après une mise à jour.
     */
    @Test
    public void testReligion_nomNonNull_apresUpdate() {
        ReligionManager.updateReligion(religion, null);
        assertNotNull("Le nom de la religion ne doit pas être nul", religion.getNom());
    }

    /**
     * Vérifie que le champ croyance est toujours renseigné.
     */
    @Test
    public void testReligion_croyanceNonNull_apresUpdate() {
        ReligionManager.updateReligion(religion, null);
        assertNotNull("La croyance ne doit pas être nulle", religion.getCroyance());
    }

    /**
     * Vérifie que le nom de la religion n'est pas effacé (chaîne vide).
     */
    @Test
    public void testReligion_nomNonVide_apresUpdate() {
        ReligionManager.updateReligion(religion, null);
        assertFalse("Le nom de la religion ne doit pas être vide", religion.getNom().isEmpty());
    }
    
}