package tests.unit.managers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import moteur.donnees.Evenement;
import moteur.donnees.Population;
import moteur.donnees.Region;
import moteur.traitement.management.factory.SimFactory;
import moteur.traitement.management.managers.population.PopulationManager;

/**
 * Suite de tests unitaires pour {@link PopulationManager}.
 * <p>
 * Cette classe valide la dynamique démographique de la simulation, notamment :
 * <ul>
 * <li>La robustesse des effectifs (pas de population négative).</li>
 * <li>Le maintien des taux de natalité et de mortalité dans des seuils réalistes.</li>
 * <li>La cohérence de l'évolution de l'âge moyen de la population.</li>
 * <li>L'impact des crises (Guerre, Raids) et de la stabilité (Commerce) sur les statistiques vitales.</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 1.1
 */
public class TestPopulationManager {

    /** 
     * Instance de population utilisée pour les tests. ù
     */
    private Population population;
    
    /** 
     * Région de référence pour les événements de test. 
     */
    private Region region;

    /**
     * Initialisation avant chaque test.
     * 
     * <p>
     * Crée une population de 10 000 habitants avec un âge moyen de 30 ans 
     * et des taux de naissance/décès standards via la {@link SimFactory}.
     * </p>
     */
    @Before
    public void prepare() {
        population = SimFactory.buildPopulation(10_000, 30f, "HOMME");
        population.setTauxNaissance(0.015f);
        population.setTauxDeces(0.003f);
        region = SimFactory.buildRegion("Scandinavie", "Ragnar");
    }

    /**
     * Vérifie que la population ne chute pas sous zéro après une longue période d'évolution naturelle.
     */
    @Test
    public void testUpdatePopulation_populationNonNegative_sansEvenement() {
        for (int i = 0; i < 100; i++) {
            PopulationManager.updatePopulation(population, null);
        }
        assertTrue("La population ne doit jamais être négative", population.getNbHabitants() >= 0);
    }

    /**
     * Vérifie que même un conflit prolongé (Guerre) ne produit pas une population négative.
     */
    @Test
    public void testUpdatePopulation_populationNonNegative_evenementGuerre() {
        Evenement guerre = new Evenement("Grande Armée", 866, 866, region, "Invasion.", "Guerre");
        for (int i = 0; i < 50; i++) {
            PopulationManager.updatePopulation(population, guerre);
        }
        assertTrue("La population ne doit jamais être négative même en guerre",
                population.getNbHabitants() >= 0);
    }

    /**
     * Vérifie que le taux de naissance reste dans les bornes physiologiques définies par le moteur.
     */
    @Test
    public void testUpdatePopulation_tauxNaissanceDansBornes_sansEvenement() {
        for (int i = 0; i < 20; i++) {
            PopulationManager.updatePopulation(population, null);
        }
        float taux = population.getTauxNaissance();
        assertTrue("Le taux de naissance doit être >= 0.002", taux >= 0.002f);
        assertTrue("Le taux de naissance doit être <= 0.025", taux <= 0.025f);
    }

    /**
     * Vérifie que le taux de décès reste dans les bornes structurelles du moteur.
     */
    @Test
    public void testUpdatePopulation_tauxDecesDansBornes_sansEvenement() {
        for (int i = 0; i < 20; i++) {
            PopulationManager.updatePopulation(population, null);
        }
        float taux = population.getTauxDeces();
        assertTrue("Le taux de décès doit être >= 0.0005", taux >= 0.0005f);
        assertTrue("Le taux de décès doit être <= 0.008", taux <= 0.008f);
    }

    /**
     * Vérifie que les bornes du taux de naissance sont respectées même en période de guerre.
     */
    @Test
    public void testUpdatePopulation_tauxNaissanceDansBornes_guerre() {
        Evenement guerre = new Evenement("Bataille", 866, 866, region, "Combat.", "Guerre");
        for (int i = 0; i < 20; i++) {
            PopulationManager.updatePopulation(population, guerre);
        }
        float taux = population.getTauxNaissance();
        assertTrue("Le taux de naissance doit rester >= 0.002 en guerre", taux >= 0.002f);
        assertTrue("Le taux de naissance doit rester <= 0.025 en guerre", taux <= 0.025f);
    }

    /**
     * Vérifie que l'âge moyen de la population n'atteint pas des valeurs aberrantes.
     */
    @Test
    public void testUpdatePopulation_ageMoyenDansBornes_sansEvenement() {
        for (int i = 0; i < 20; i++) {
            PopulationManager.updatePopulation(population, null);
        }
        float age = population.getAgeMoyen();
        assertTrue("L'âge moyen doit être >= 15", age >= 15f);
        assertTrue("L'âge moyen doit être <= 60", age <= 60f);
    }

    /**
     * S'assure que le champ sexeMajoritaire reste renseigné après un événement.
     */
    @Test
    public void testUpdatePopulation_sexeMajoritaireNonNull_avecEvenement() {
        Evenement evenement = new Evenement("Raid", 795, 795, region, "Raids côtiers.", "Raid");
        PopulationManager.updatePopulation(population, evenement);
        assertNotNull("Le sexe majoritaire ne doit pas être nul", population.getSexeMajoritaire());
    }

    /**
     * Vérifie qu'un événement pacifique comme le commerce n'altère pas le sexe majoritaire.
     */
    @Test
    public void testUpdatePopulation_sexeMajoritaireValide_commerce() {
        Evenement commerce = new Evenement("Commerce baltique", 900, 950, region, "Échanges.", "Commerce");
        PopulationManager.updatePopulation(population, commerce);
        assertEquals("Un événement Commerce doit laisser le sexe majoritaire à HOMME",
                "HOMME", population.getSexeMajoritaire());
    }

    /**
     * Vérifie la corrélation entre un événement de type 'Guerre' et l'augmentation de la mortalité.
     */
    @Test
    public void testUpdatePopulation_guerre_augmenteTauxDeces() {
        float tauxInitial = 0.003f;
        population.setTauxDeces(tauxInitial);
        Evenement guerre = new Evenement("Grande Armée", 866, 866, region, "Invasion.", "Guerre");
        PopulationManager.updatePopulation(population, guerre);
        assertTrue("Un événement Guerre doit augmenter le taux de décès",
                population.getTauxDeces() > tauxInitial);
    }
}