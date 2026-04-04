package tests.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import moteur.carte.Continent;
import moteur.traitement.builders.CarteBuilder;

/**
 * Classe de test unitaire pour la classe {@link CarteBuilder}.
 *
 * <p>
 * Elle vérifie :
 * <ul>
 *     <li>Que chaque continent construit n'est pas nul</li>
 *     <li>Que chaque continent possède au moins un bloc</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public class TestCarteBuilder {

    // Attributs représentant les continents construits
    private Continent lindsfarne;
    private Continent europe;
    private Continent islande;
    private Continent grandeBretagne;
    private Continent normandie;
    private Continent scandinavie;
    private Continent vinland;

    /**
     * Initialise les continents avant chaque test.
     */
    @Before
    public void prepare() {
        lindsfarne = CarteBuilder.buildLindsfarne();
        europe = CarteBuilder.buildEurope();
        islande = CarteBuilder.buildIslande();
        grandeBretagne = CarteBuilder.buildGrandeBretagne();
        normandie = CarteBuilder.buildNormandie();
        scandinavie = CarteBuilder.buildScandinave();
        vinland = CarteBuilder.buildVinland();
    }

    // =======================
    // Tests de non-nullité
    // =======================

    @Test
    public void testLindsfarne_nonNull() {
        assertNotNull("Lindsfarne devrait être non nul", lindsfarne);
    }

    @Test
    public void testEurope_nonNull() {
        assertNotNull("Europe devrait être non nul", europe);
    }

    @Test
    public void testIslande_nonNull() {
        assertNotNull("Islande devrait être non nul", islande);
    }

    @Test
    public void testGrandeBretagne_nonNull() {
        assertNotNull("Grande Bretagne devrait être non nul", grandeBretagne);
    }

    @Test
    public void testNormandie_nonNull() {
        assertNotNull("Normandie devrait être non nul", normandie);
    }

    @Test
    public void testScandinavie_nonNull() {
        assertNotNull("Scandinavie devrait être non nul", scandinavie);
    }

    @Test
    public void testVinland_nonNull() {
        assertNotNull("Vinland devrait être non nul", vinland);
    }

    // =======================
    // Tests de blocs
    // =======================

    @Test
    public void testLindsfarne_aDesBlocs() {
        assertTrue("Lindsfarne doit avoir au moins un bloc", lindsfarne.getNbBlocs() > 0);
    }

    @Test
    public void testEurope_aDesBlocs() {
        assertTrue("Europe doit avoir au moins un bloc", europe.getNbBlocs() > 0);
    }

    @Test
    public void testIslande_aDesBlocs() {
        assertTrue("Islande doit avoir au moins un bloc", islande.getNbBlocs() > 0);
    }

    @Test
    public void testGrandeBretagne_aDesBlocs() {
        assertTrue("Grande Bretagne doit avoir au moins un bloc", grandeBretagne.getNbBlocs() > 0);
    }

    @Test
    public void testNormandie_aDesBlocs() {
        assertTrue("Normandie doit avoir au moins un bloc", normandie.getNbBlocs() > 0);
    }

    @Test
    public void testScandinavie_aDesBlocs() {
        assertTrue("Scandinavie doit avoir au moins un bloc", scandinavie.getNbBlocs() > 0);
    }

    @Test
    public void testVinland_aDesBlocs() {
        assertTrue("Vinland doit avoir au moins un bloc", vinland.getNbBlocs() > 0);
    }

}