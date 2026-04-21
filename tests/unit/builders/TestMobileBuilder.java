package tests.unit.builders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gui.mobiles.Actions;
import gui.mobiles.Mobile;
import moteur.traitement.builders.MobileBuilder;
import gui.management.MobileManager;

/**
 * Tests unitaires pour {@link MobileBuilder}.
 *
 * <p>
 * Vérifie :
 * <ul>
 *     <li>Que chaque manager de mobiles existe</li>
 *     <li>Que chaque manager contient au moins un mobile</li>
 *     <li>Que chaque mobile possède au moins une action</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public class TestMobileBuilder {

    private MobileManager mobilesLindsfarne;
    private MobileManager mobilesEurope;
    private MobileManager mobilesIslande;
    private MobileManager mobilesGrandeBretagne;
    private MobileManager mobilesNormandie;
    private MobileManager mobilesScandinavie;
    private MobileManager mobilesVinland;

    /**
     * Initialise les managers avant chaque test.
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

    @Test
    public void testManagers_nonNull() {
        assertNotNull("Le manager de Lindsfarne ne doit pas être nul", mobilesLindsfarne);
        assertNotNull("Le manager de l'Europe ne doit pas être nul", mobilesEurope);
        assertNotNull("Le manager de l'Islande ne doit pas être nul", mobilesIslande);
        assertNotNull("Le manager de la Grande-Bretagne ne doit pas être nul", mobilesGrandeBretagne);
        assertNotNull("Le manager de la Normandie ne doit pas être nul", mobilesNormandie);
        assertNotNull("Le manager de la Scandinavie ne doit pas être nul", mobilesScandinavie);
        assertNotNull("Le manager du Vinland ne doit pas être nul", mobilesVinland);
    }


    @Test
    public void testManagers_aDesMobiles() {
        assertTrue("Lindsfarne doit contenir au moins un mobile", mobilesLindsfarne.getNbMobiles() > 0);
        assertTrue("Europe doit contenir au moins un mobile", mobilesEurope.getNbMobiles() > 0);
        assertTrue("Islande doit contenir au moins un mobile", mobilesIslande.getNbMobiles() > 0);
        assertTrue("Grande-Bretagne doit contenir au moins un mobile", mobilesGrandeBretagne.getNbMobiles() > 0);
        assertTrue("Normandie doit contenir au moins un mobile", mobilesNormandie.getNbMobiles() > 0);
        assertTrue("Scandinavie doit contenir au moins un mobile", mobilesScandinavie.getNbMobiles() > 0);
        assertTrue("Vinland doit contenir au moins un mobile", mobilesVinland.getNbMobiles() > 0);
    }

    private void testMobilesActions(MobileManager manager, String nomManager) {
        for (Mobile mobile : manager) {
            Actions actions = manager.getActions(mobile);
            assertTrue(String.format("Le mobile %s dans %s doit avoir au moins une action", mobile.getId(), nomManager),
                    actions.getNbActions() > 0);
        }
    }

    @Test
    public void testMobiles_ontDesActions() {
        testMobilesActions(mobilesLindsfarne, "Lindsfarne");
        testMobilesActions(mobilesEurope, "Europe");
        testMobilesActions(mobilesIslande, "Islande");
        testMobilesActions(mobilesGrandeBretagne, "Grande-Bretagne");
        testMobilesActions(mobilesNormandie, "Normandie");
        testMobilesActions(mobilesScandinavie, "Scandinavie");
        testMobilesActions(mobilesVinland, "Vinland");
    }

}