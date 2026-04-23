package tests.unit.managers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import moteur.donnees.Evenement;
import moteur.donnees.Politique;
import moteur.donnees.Region;
import moteur.donnees.Relation;
import moteur.traitement.management.factory.SimFactory;
import moteur.traitement.management.managers.politique.RelationManager;

import java.util.ArrayList;

/**
 * Suite de tests unitaires pour {@link RelationManager}.
 * 
 * <p>
 * Cette classe valide la gestion des interactions diplomatiques, notamment :
 * <ul>
 * 		<li>Le maintien des niveaux de relation dans les bornes [0, 100].</li>
 * 		<li>L'impact ciblé des événements historiques sur des civilisations spécifiques (ex: Northumbrie, Francs).</li>
 * 		<li>La traduction qualitative des scores (Alliance, Neutre, Hostile).</li>
 * 		<li>Les algorithmes de recherche de la meilleure et de la pire relation.</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 1.1
 */
public class TestRelationManager {

    /** 
     * Liste des relations diplomatiques de la civilisation testée. 
     */
    private ArrayList<Relation> relations;
    
    /** 
     * État politique de la civilisation. 
     */
    private Politique politique;
    
    /** 
     * Objets de relation pour les tests ciblés. 
     */
    private Relation northumbrie;
    private Relation irlande;
    private Relation francs;
    
    /** 
     * Région de référence pour les événements. 
     */
    private Region region;

    /**
     * Initialisation avant chaque test.
     * 
     * <p>
     * Prépare une région, un régime politique et une liste de trois relations 
     * initialisées à un niveau neutre (50).
     * </p>
     */
    @Before
    public void prepare() {
        region = SimFactory.buildRegion("Scandinavie", "Ragnar");
        politique = SimFactory.buildPolitique("Monarchie viking", 50f);

        northumbrie = new Relation("Northumbrie", 50f);
        irlande     = new Relation("Irlande", 50f);
        francs      = new Relation("Royaume des Francs", 50f);

        relations = new ArrayList<>();
        relations.add(northumbrie);
        relations.add(irlande);
        relations.add(francs);
    }

    /**
     * Vérifie que même une guerre totale ne fait pas descendre le niveau de relation sous 0.
     */
    @Test
    public void testUpdateRelations_niveauResteDansBornes_guerre() {
        Evenement guerre = new Evenement("Grande Armée païenne", 866, 866, region, "Invasion totale.", "Guerre");
        RelationManager.updateRelations(relations, politique, guerre);

        for (Relation r : relations) {
            assertTrue("Le niveau doit être >= 0", r.getNiveau() >= 0f);
            assertTrue("Le niveau doit être <= 100", r.getNiveau() <= 100f);
        }
    }

    /**
     * Vérifie la stabilité des niveaux de relation en l'absence d'événement.
     */
    @Test
    public void testUpdateRelations_niveauResteDansBornes_sansEvenement() {
        RelationManager.updateRelations(relations, politique, null);

        for (Relation r : relations) {
            assertTrue("Le niveau doit être >= 0", r.getNiveau() >= 0f);
            assertTrue("Le niveau doit être <= 100", r.getNiveau() <= 100f);
        }
    }

    /**
     * Vérifie que le raid de Lindisfarne impacte spécifiquement et négativement la Northumbrie.
     */
    @Test
    public void testUpdateRelations_raidLindisfarne_diminueNorthumbrie() {
        northumbrie.setNiveau(80f);
        Evenement raid = new Evenement("Raid de Lindisfarne", 793, 793, region, "Premier raid.", "Raid");
        RelationManager.updateRelations(relations, politique, raid);
        assertTrue("Un raid sur Lindisfarne doit fortement diminuer la relation avec la Northumbrie",
                northumbrie.getNiveau() < 80f);
    }

    /**
     * Vérifie que la fondation de la Normandie améliore les relations avec les Francs.
     */
    @Test
    public void testUpdateRelations_fondationNormandie_augmenteFrancs() {
        francs.setNiveau(30f);
        Evenement normandie = new Evenement("Fondation de la Normandie", 911, 911, region, "Alliance avec les Francs.", "Diplomatie");
        RelationManager.updateRelations(relations, politique, normandie);
        assertTrue("La fondation de la Normandie doit améliorer la relation avec les Francs",
                francs.getNiveau() > 30f);
    }

    /**
     * Vérifie que la conversion religieuse améliore les relations avec les puissances chrétiennes.
     */
    @Test
    public void testUpdateRelations_conversion_augmenteRelationsChretiennes() {
        northumbrie.setNiveau(30f);
        francs.setNiveau(30f);
        Evenement conversion = new Evenement("Conversion au christianisme", 960, 960, region, "Fin du statut païen.", "Religion");
        RelationManager.updateRelations(relations, politique, conversion);
        assertTrue("La conversion doit améliorer la relation avec la Northumbrie",
                northumbrie.getNiveau() > 30f);
        assertTrue("La conversion doit améliorer la relation avec les Francs",
                francs.getNiveau() > 30f);
    }

    /**
     * Vérifie que le manager ne lève pas d'exception si la liste fournie est vide.
     */
    @Test
    public void testUpdateRelations_listeVide_nestPasErreur() {
        RelationManager.updateRelations(new ArrayList<>(), politique, null);
    }

    /**
     * Vérifie la robustesse du manager face à une liste de relations nulle.
     */
    @Test
    public void testUpdateRelations_null_nestPasErreur() {
        RelationManager.updateRelations(null, politique, null);
    }

    /**
     * Vérifie le libellé qualitatif pour un score de 80 (Alliance).
     */
    @Test
    public void testGetDescriptionRelation_alliance() {
        northumbrie.setNiveau(80f);
        assertEquals("Niveau 80 doit être une Alliance", "Alliance", RelationManager.getDescriptionRelation(northumbrie));
    }

    /**
     * Vérifie le libellé qualitatif pour un score de 60 (Neutre).
     */
    @Test
    public void testGetDescriptionRelation_neutre() {
        northumbrie.setNiveau(60f);
        assertEquals("Niveau 60 doit être Neutre", "Neutre", RelationManager.getDescriptionRelation(northumbrie));
    }

    /**
     * Vérifie le libellé qualitatif pour un score de 40 (Tendue).
     */
    @Test
    public void testGetDescriptionRelation_tendue() {
        northumbrie.setNiveau(40f);
        assertEquals("Niveau 40 doit être Tendue", "Tendue", RelationManager.getDescriptionRelation(northumbrie));
    }

    /**
     * Vérifie le libellé qualitatif pour un score de 10 (Hostile).
     */
    @Test
    public void testGetDescriptionRelation_hostile() {
        northumbrie.setNiveau(10f);
        assertEquals("Niveau 10 doit être Hostile", "Hostile", RelationManager.getDescriptionRelation(northumbrie));
    }

    /**
     * Vérifie que la recherche de la meilleure relation retourne bien l'objet avec le score le plus élevé.
     */
    @Test
    public void testGetMeilleureRelation_retourneLaPlusHaute() {
        northumbrie.setNiveau(80f);
        irlande.setNiveau(30f);
        francs.setNiveau(50f);

        Relation meilleure = RelationManager.getMeilleureRelation(relations);
        assertEquals("La meilleure relation doit être la Northumbrie",
                "Northumbrie", meilleure.getNomCivilisation());
    }

    /**
     * Vérifie que la recherche de la pire relation retourne bien l'objet avec le score le plus bas.
     */
    @Test
    public void testGetPireRelation_retourneLaPlusBasse() {
        northumbrie.setNiveau(80f);
        irlande.setNiveau(10f);
        francs.setNiveau(50f);

        Relation pire = RelationManager.getPireRelation(relations);
        assertEquals("La pire relation doit être l'Irlande",
                "Irlande", pire.getNomCivilisation());
    }

    /**
     * Vérifie que la méthode retourne null si aucune relation n'est présente dans la liste.
     */
    @Test
    public void testGetMeilleureRelation_listeVide_retourneNull() {
        assertNull("Avec une liste vide, la meilleure relation doit être null",
                RelationManager.getMeilleureRelation(new ArrayList<>()));
    }

    /**
     * Vérifie que la méthode retourne null pour la pire relation si la liste est vide.
     */
    @Test
    public void testGetPireRelation_listeVide_retourneNull() {
        assertNull("Avec une liste vide, la pire relation doit être null",
                RelationManager.getPireRelation(new ArrayList<>()));
    }
    
}