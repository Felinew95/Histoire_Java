package tests.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import moteur.carte.Continent;
import moteur.traitement.builders.CarteBuilder;

/**
 * Classe de test de la classe CarteBuilder
 * 
 * @author Alexandre 
 * @version 1.0
 */
public class TestCarteBuilder {

	// Attributs 
	private Continent lindsfarne;
    private Continent europe;
    private Continent islande;
    private Continent grandeBretagne;
    private Continent normandie;
    private Continent scandinavie;
    private Continent vinland;
	
    /**
     * Méthode qui créer les valeurs pour les attributs
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
    
    // Tests pour vérifier si les continents existent bien
    
    /**
     * Méthode qui teste si lindfarne existe bien 
     */
    @Test
    public void testLindsfarne_nonNull() {
        assertNotNull(lindsfarne);
    }
 
    /**
     * Méthode qui teste si l'europe existe bien 
     */
    @Test
    public void testEurope_nonNull() {
        assertNotNull(europe);
    }
 
    /**
     * Méthode qui teste si l'islande existe bien 
     */
    @Test
    public void testIslande_nonNull() {
        assertNotNull(islande);
    }
 
    /**
     * Méthode qui teste si la grande bretagne existe bien 
     */
    @Test
    public void testGrandeBretagne_nonNull() {
        assertNotNull(grandeBretagne);
    }
 
    /**
     * Méthode qui teste si la normandie existe bien 
     */
    @Test
    public void testNormandie_nonNull() {
        assertNotNull(normandie);
    }
 
    /**
     * Méthode qui teste si la scandinavie existe bien 
     */
    @Test
    public void testScandinavie_nonNull() {
        assertNotNull(scandinavie);
    }
 
    /**
     * Méthode qui teste si le vinland existe bien 
     */
    @Test
    public void testVinland_nonNull() {
        assertNotNull(vinland);
    }
   
    // Tests pour vérifier si les contients ont des blocs 
    
    /**
     * Méthode qui teste si lindfarne a au moins un bloc
     */
    @Test
    public void testLindsfarne_aDesBlocs() {
        assertTrue(lindsfarne.getNbBlocs() > 0);
    }
    
    /**
     * Méthode qui teste si l'europe a au moins un bloc
     */
    @Test
    public void testEurope_aDesBlocs() {
        assertTrue(europe.getNbBlocs() > 0);
    }
 
    /**
     * Méthode qui teste si l'islande a au moins un bloc
     */
    @Test
    public void testIslande_aDesBlocs() {
        assertTrue(islande.getNbBlocs() > 0);
    }
 
    /**
     * Méthode qui teste si la grande bretagne a au moins un bloc
     */
    @Test
    public void testGrandeBretagne_aDesBlocs() {
        assertTrue(grandeBretagne.getNbBlocs() > 0);
    }
 
    /**
     * Méthode qui teste si la normandie a au moins un bloc
     */
    @Test
    public void testNormandie_aDesBlocs() {
        assertTrue(normandie.getNbBlocs() > 0);
    }
 
    /**
     * Méthode qui teste si la scandinavie a au moins un bloc
     */
    @Test
    public void testScandinavie_aDesBlocs() {
        assertTrue(scandinavie.getNbBlocs() > 0);
    }
 
    /**
     * Méthode qui teste si le vinland a au moins un bloc
     */
    @Test
    public void testVinland_aDesBlocs() {
        assertTrue(vinland.getNbBlocs() > 0);
    }
    
}
