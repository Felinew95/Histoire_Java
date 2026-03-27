package tests.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gui.mobiles.Actions;
import gui.mobiles.Mobile;
import moteur.traitement.builders.MobileBuilder;
import moteur.traitement.management.managers.MobileManager;

/**
 * Classe qui test la classe MobileBuilder
 * 
 * @author Alexandre
 * @version 1.0
 */
public class TestMobileBuilder {

	// Attributs
	private MobileManager mobilesLindsfarne;
    private MobileManager mobilesEurope;
    private MobileManager mobilesIslande;
    private MobileManager mobilesGrandeBretagne;
    private MobileManager mobilesNormandie;
    private MobileManager mobilesScandinavie;
    private MobileManager mobilesVinland;
	
    /**
     * Méthode qui créer les valeurs pour les attributs
     */
	@Before
	public void prepare() {
		mobilesLindsfarne = MobileBuilder.buildMobileManagerLindsfarne();
        mobilesEurope = MobileBuilder.buildMobileManagerEurope();
        mobilesIslande = MobileBuilder.buildMobileManagerIslande();
        mobilesGrandeBretagne = MobileBuilder.buildMobileManagerGrandeBretagne();
        mobilesNormandie = MobileBuilder.buildMobileManagerNormandie();
        mobilesScandinavie = MobileBuilder.buildMobileManagerScandinavie();
        mobilesVinland = MobileBuilder.buildMobileManagerVinland();
	}
	
	 /**
     * Méthode qui vérifie si le manager de Lindsfarne existe bien
     */
    @Test
    public void testMobilesLindsfarne_nonNull() {
        assertNotNull(mobilesLindsfarne);
    }
 
    /**
     * Méthode qui vérifie si le manager de l'Europe existe bien
     */
    @Test
    public void testMobilesEurope_nonNull() {
        assertNotNull(mobilesEurope);
    }
 
    /**
     * Méthode qui vérifie si le manager de l'Islande existe bien
     */
    @Test
    public void testMobilesIslande_nonNull() {
        assertNotNull(mobilesIslande);
    }
 
    /**
     * Méthode qui vérifie si le manager de la Grande-Bretagne existe bien
     */
    @Test
    public void testMobilesGrandeBretagne_nonNull() {
        assertNotNull(mobilesGrandeBretagne);
    }
 
    /**
     * Méthode qui vérifie si le manager de la Normandie existe bien
     */
    @Test
    public void testMobilesNormandie_nonNull() {
        assertNotNull(mobilesNormandie);
    }
 
    /**
     * Méthode qui vérifie si le manager de la Scandinavie existe bien
     */
    @Test
    public void testMobilesScandinavie_nonNull() {
        assertNotNull(mobilesScandinavie);
    }
 
    /**
     * Méthode qui vérifie si le manager du Vinland existe bien
     */
    @Test
    public void testMobilesVinland_nonNull() {
        assertNotNull(mobilesVinland);
    }
    
    /**
     * Méthode qui vérifie si le manager de Lindsfarne a des mobiles
     */
    @Test
    public void testMobilesLindsfarne_aMobiles() {
        assertTrue(mobilesLindsfarne.getNbMobiles() > 0);
    }
 
    /**
     * Méthode qui vérifie si le manager de l'Europe a des mobiles
     */
    @Test
    public void testMobilesEurope_aMobiles() {
        assertTrue(mobilesEurope.getNbMobiles() > 0);
    }
 
    /**
     * Méthode qui vérifie si le manager de l'Islande a des mobiles
     */
    @Test
    public void testMobilesIslande_aMobiles() {
        assertTrue(mobilesIslande.getNbMobiles() > 0);
    }
 
    /**
     * Méthode qui vérifie si le manager de la Grande-Bretagne a des mobiles
     */
    @Test
    public void testMobilesGrandeBretagne_aMobiles() {
        assertTrue(mobilesGrandeBretagne.getNbMobiles() > 0);
    }
 
    /**
     * Méthode qui vérifie si le manager de la Normandie a des mobiles
     */
    @Test
    public void testMobilesNormandie_aMobiles() {
        assertTrue(mobilesNormandie.getNbMobiles() > 0);
    }
 
    /**
     * Méthode qui vérifie si le manager de la Scandinavie a des mobiles
     */
    @Test
    public void testMobilesScandinavie_aMobiles() {
        assertTrue(mobilesScandinavie.getNbMobiles() > 0);
    }
 
    /**
     * Méthode qui vérifie si le manager du Vinland a des mobiles
     */
    @Test
    public void testMobilesVinland_aMobiles() {
        assertTrue(mobilesVinland.getNbMobiles() > 0);
    }
    
    @Test
    public void testMobilesLindsfarne_ontDesActions() {
    	for (Mobile mobile : mobilesLindsfarne) {
    		Actions actions = mobilesLindsfarne.getActions(mobile);
    		assertTrue(actions.getNbActions() > 0);
    	}
    }
    
    /**
     * Méthode qui vérifie si les mobiles de l'Europe ont des actions
     */
    @Test
    public void testMobilesEurope_ontDesActions() {
        for (Mobile mobile : mobilesEurope) {
            Actions actions = mobilesEurope.getActions(mobile);
            assertTrue(actions.getNbActions() > 0);
        }
    }
 
    /**
     * Méthode qui vérifie si les mobiles de l'Islande ont des actions
     */
    @Test
    public void testMobilesIslande_ontDesActions() {
        for (Mobile mobile : mobilesIslande) {
            Actions actions = mobilesIslande.getActions(mobile);
            assertTrue(actions.getNbActions() > 0);
        }
    }
 
    /**
     * Méthode qui vérifie si les mobiles de la Grande-Bretagne ont des actions
     */
    @Test
    public void testMobilesGrandeBretagne_ontDesActions() {
        for (Mobile mobile : mobilesGrandeBretagne) {
            Actions actions = mobilesGrandeBretagne.getActions(mobile);
            assertTrue(actions.getNbActions() > 0);
        }
    }
 
    /**
     * Méthode qui vérifie si les mobiles de la Normandie ont des actions
     */
    @Test
    public void testMobilesNormandie_ontDesActions() {
        for (Mobile mobile : mobilesNormandie) {
            Actions actions = mobilesNormandie.getActions(mobile);
            assertTrue(actions.getNbActions() > 0);
        }
    }
 
    /**
     * Méthode qui vérifie si les mobiles de la Scandinavie ont des actions
     */
    @Test
    public void testMobilesScandinavie_ontDesActions() {
        for (Mobile mobile : mobilesScandinavie) {
            Actions actions = mobilesScandinavie.getActions(mobile);
            assertTrue(actions.getNbActions() > 0);
        }
    }
 
    /**
     * Méthode qui vérifie si les mobiles du Vinland ont des actions
     */
    @Test
    public void testMobilesVinland_ontDesActions() {
        for (Mobile mobile : mobilesVinland) {
            Actions actions = mobilesVinland.getActions(mobile);
            assertTrue(actions.getNbActions() > 0);
        }
    }

}
