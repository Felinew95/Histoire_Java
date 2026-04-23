package tests.unit.factory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import config.SimConfig;
import moteur.carte.Carte;
import moteur.donnees.Civilisation;
import moteur.traitement.management.factory.CivilisationFactory;
import moteur.traitement.management.managers.civilisation.CivilisationManager;

import java.util.List;

/**
 * Classe de test unitaire pour la classe {@link CivilisationFactory}.
 *
 * <p>
 * Elle vérifie :
 * <ul>
 *     <li>Que chaque civilisation construite n'est pas nulle</li>
 *     <li>Que les noms des civilisations sont corrects</li>
 *     <li>Que les relations diplomatiques sont bien initialisées</li>
 *     <li>Que les managers adverses sont correctement construits</li>
 * </ul>
 * </p>
 *
 * @author Tauseef
 * @version 1.0
 */
public class TestCivilisationFactory {

    private static final int ANNEE_DEBUT = 793;

    // Civilisations construites
    private Civilisation vikings;
    private Civilisation angloSaxons;
    private Civilisation francs;
    private Civilisation irlandais;

    // Managers adverses
    private List<CivilisationManager> managersAdverses;

    /**
     * Initialise les civilisations et les managers avant chaque test.
     */
    @Before
    public void prepare() {
        Carte carte = new Carte(SimConfig.NOMBRE_LIGNES, SimConfig.NOMBRE_COLONNES);

        vikings = CivilisationFactory.buildVikings(ANNEE_DEBUT);

        List<CivilisationManager> managers =CivilisationFactory.buildSimulationManagersAdverses(ANNEE_DEBUT, carte);
        managersAdverses = managers;

        angloSaxons = managers.get(0).getCivilisation();
        francs      = managers.get(1).getCivilisation();
        irlandais   = managers.get(2).getCivilisation();
    }

    @Test
    public void testVikings_nonNull() {
        assertNotNull("La civilisation Viking ne doit pas être nulle", vikings);
    }

    @Test
    public void testAngloSaxons_nonNull() {
        assertNotNull("La civilisation Anglo-Saxons ne doit pas être nulle", angloSaxons);
    }

    @Test
    public void testFrancs_nonNull() {
        assertNotNull("La civilisation Francs ne doit pas être nulle", francs);
    }

    @Test
    public void testIrlandais_nonNull() {
        assertNotNull("La civilisation Irlandais ne doit pas être nulle", irlandais);
    }

    @Test
    public void testAngloSaxons_nomCorrect() {
        assertEquals("Le nom doit être 'Anglo-Saxons'", "Anglo-Saxons", angloSaxons.getNom());
    }

    @Test
    public void testFrancs_nomCorrect() {
        assertEquals("Le nom doit être 'Francs'", "Francs", francs.getNom());
    }

    @Test
    public void testIrlandais_nomCorrect() {
        assertEquals("Le nom doit être 'Irlandais'", "Irlandais", irlandais.getNom());
    }

    @Test
    public void testVikings_aDesRelations() {
        assertTrue("Les Vikings doivent avoir au moins une relation",vikings.getRelations().size() > 0);
    }

    @Test
    public void testVikings_troisRelations() {
        assertEquals("Les Vikings doivent avoir exactement 3 relations",3, vikings.getRelations().size());
    }

    @Test
    public void testAngloSaxons_aDesRelations() {
        assertTrue("Les Anglo-Saxons doivent avoir au moins une relation",angloSaxons.getRelations().size() > 0);
    }

    @Test
    public void testFrancs_aDesRelations() {
        assertTrue("Les Francs doivent avoir au moins une relation",francs.getRelations().size() > 0);
    }

    @Test
    public void testIrlandais_aDesRelations() {
        assertTrue("Les Irlandais doivent avoir au moins une relation",irlandais.getRelations().size() > 0);
    }

    @Test
    public void testManagersAdverses_nonNull() {
        assertNotNull("La liste des managers adverses ne doit pas être nulle", managersAdverses);
    }

    @Test
    public void testManagersAdverses_troisManagers() {
        assertEquals("Il doit y avoir exactement 3 managers adverses",3, managersAdverses.size());
    }

    @Test
    public void testManagerAngloSaxons_nonNull() {
        assertNotNull("Le manager Anglo-Saxons ne doit pas être nul", managersAdverses.get(0));
    }

    @Test
    public void testManagerFrancs_nonNull() {
        assertNotNull("Le manager Francs ne doit pas être nul", managersAdverses.get(1));
    }

    @Test
    public void testManagerIrlandais_nonNull() {
        assertNotNull("Le manager Irlandais ne doit pas être nul", managersAdverses.get(2));
    }

    @Test
    public void testGetCivilisationsAdverses_nonNull() {
        List<Civilisation> civs = CivilisationFactory.getCivilisationsAdverses(managersAdverses);
        assertNotNull("La liste des civilisations adverses ne doit pas être nulle", civs);
    }

    @Test
    public void testGetCivilisationsAdverses_troisCivs() {
        List<Civilisation> civs = CivilisationFactory.getCivilisationsAdverses(managersAdverses);
        assertEquals("getCivilisationsAdverses doit retourner 3 civilisations",3, civs.size());
    }

    @Test
    public void testGetCivilisationsAdverses_aucuneNull() {
        List<Civilisation> civs = CivilisationFactory.getCivilisationsAdverses(managersAdverses);
        for (Civilisation civ : civs) {
            assertNotNull("Aucune civilisation adverse ne doit être nulle", civ);
        }
    }
}