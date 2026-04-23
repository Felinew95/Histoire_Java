package tests.unit.managers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import moteur.donnees.Economie;
import moteur.donnees.Evenement;
import moteur.traitement.management.managers.economie.EconomieManager;
import moteur.traitement.management.managers.economie.ProduitRepository;
import moteur.traitement.management.managers.economie.RessourceRepository;

/**
 * Suite de tests unitaires pour la gestion globale de l'économie.
 * 
 * <p>
 * Cette classe valide trois aspects critiques de la simulation :
 * <ul>
 * 		<li>Le chargement et l'unicité des dépôts de données ({@link ProduitRepository} et {@link RessourceRepository}).</li>
 * 		<li>La robustesse du moteur de calcul {@link EconomieManager} face aux limites (plafond de monnaie).</li>
 * 		<li>La génération correcte des flux économiques (productions, exportations, importations) lors des cycles de mise à jour.</li>
 * </ul>
 * </p>
 * 
 * @author Tauseef
 * @author Alexandre 
 * 
 * @version 1.0
 */
public class TestEconomieManager {

    /** 
     * Objet de données représentant l'état économique pour le test. 
     */
    private Economie economie;
    
    /** 
     * Population de test utilisée pour simuler l'échelle économique. 
     */
    private static final int NB_HABITANTS = 10_000;

    /**
     * Prépare un environnement économique neutre avant chaque test.
     * <p>Initialise les stycas à 1000 et remet à zéro tous les compteurs annuels et totaux.</p>
     */
    @Before
    public void prepare() {
        economie = new Economie();
        economie.setStycas(1000f);
        economie.setGainsAnnuel(0f);
        economie.setPertesAnnuel(0f);
        economie.setGainsTotal(0f);
        economie.setPertesTotal(0f);
    }

    /**
     * Vérifie que le dépôt des produits est correctement instancié.
     */
    @Test
    public void testProduitRepository_nonNull() {
        assertNotNull("Le ProduitRepository ne doit pas être nul", ProduitRepository.getInstance());
    }

    /**
     * Vérifie que le dépôt des produits contient des données après chargement.
     */
    @Test
    public void testProduitRepository_aDesProduits() {
        assertTrue("Le ProduitRepository doit contenir au moins un produit",
                ProduitRepository.getInstance().getNombreProduits() > 0);
    }

    /**
     * Vérifie la validité textuelle des noms de produits chargés.
     */
    @Test
    public void testProduitRepository_nomProduitNonVide() {
        ProduitRepository repo = ProduitRepository.getInstance();
        for (int i = 0; i < repo.getNombreProduits(); i++) {
            String nom = repo.getNomProduit(i);
            assertTrue("Le nom du produit ne doit pas être vide", nom != null && !nom.isEmpty());
        }
    }

    /**
     * Vérifie que le dépôt des produits respecte strictement le pattern Singleton.
     */
    @Test
    public void testProduitRepository_singleton_memInstance() {
        assertSame("getInstance doit retourner la même instance",
                ProduitRepository.getInstance(), ProduitRepository.getInstance());
    }

    /**
     * Vérifie que le dépôt des ressources est correctement instancié.
     */
    @Test
    public void testRessourceRepository_nonNull() {
        assertNotNull("Le RessourceRepository ne doit pas être nul", RessourceRepository.getInstance());
    }

    /**
     * Vérifie que le dépôt des ressources contient des données après chargement.
     */
    @Test
    public void testRessourceRepository_aDesRessources() {
        assertTrue("Le RessourceRepository doit contenir au moins une ressource",
                RessourceRepository.getInstance().getAllNomRessources().size() > 0);
    }

    /**
     * Vérifie que chaque ressource chargée possède un type défini.
     */
    @Test
    public void testRessourceRepository_typeRessourceNonNull() {
        RessourceRepository repo = RessourceRepository.getInstance();
        for (String nom : repo.getAllNomRessources()) {
            String type = repo.getTypeRessource(nom);
            assertTrue("Le type de la ressource ne doit pas être vide", type != null && !type.isEmpty());
        }
    }

    /**
     * Vérifie que le dépôt des ressources respecte strictement le pattern Singleton.
     */
    @Test
    public void testRessourceRepository_singleton_memeInstance() {
        assertSame("getInstance doit retourner la même instance",
                RessourceRepository.getInstance(), RessourceRepository.getInstance());
    }

    /**
     * Teste le mécanisme de plafonnement de la monnaie (Stycas).
     * <p>Vérifie qu'une accumulation excessive de richesses est bridée par la constante {@code MAX_STYCAS}.</p>
     */
    @Test
    public void testUpdateEconomie_stycasLimitesAMAX() {
        economie.setStycas(999_999f);
        Evenement commerce = new Evenement("Commerce baltique", 900, 950, null, "Commerce", "");
        for (int i = 0; i < 10; i++) {
            EconomieManager.updateEconomie(economie, NB_HABITANTS, commerce);
        }
        assertTrue("Les stycas ne doivent pas dépasser MAX_STYCAS",
                economie.getStycas() <= config.SimConfig.MAX_STYCAS);
    }

    /**
     * Vérifie l'initialisation des productions lors du premier cycle de mise à jour.
     */
    @Test
    public void testUpdateEconomie_creerProductions_apresPremierUpdate() {
        EconomieManager.updateEconomie(economie, NB_HABITANTS, null);
        assertTrue("Des productions doivent être créées après le premier update",
                economie.getProductions().size() > 0);
    }

    /**
     * Vérifie l'initialisation des exportations lors du premier cycle de mise à jour.
     */
    @Test
    public void testUpdateEconomie_creerExportations_apresPremierUpdate() {
        EconomieManager.updateEconomie(economie, NB_HABITANTS, null);
        assertTrue("Des exportations doivent être créées après le premier update",
                economie.getExportations().size() > 0);
    }

    /**
     * Vérifie l'initialisation des importations lors du premier cycle de mise à jour.
     */
    @Test
    public void testUpdateEconomie_creerRessourcesImportees_apresPremierUpdate() {
        EconomieManager.updateEconomie(economie, NB_HABITANTS, null);
        assertTrue("Des ressources importées doivent être créées après le premier update",
                economie.getRessourcesImportees().size() > 0);
    }

    /**
     * Vérifie la cohérence arithmétique des pertes annuelles après calcul.
     */
    @Test
    public void testUpdateEconomie_pertesAnnuelPositif_apresUpdate() {
        EconomieManager.updateEconomie(economie, NB_HABITANTS, null);
        assertTrue("Les pertes annuelles doivent être positives ou nulles", economie.getPertesAnnuel() >= 0f);
    }

    /**
     * Vérifie que les gains totaux sont cumulatifs sur plusieurs cycles de simulation.
     */
    @Test
    public void testUpdateEconomie_gainsTotalCumule_apresPlusieursCycles() {
        float gainsAvant = economie.getGainsTotal();
        EconomieManager.updateEconomie(economie, NB_HABITANTS, null);
        EconomieManager.updateEconomie(economie, NB_HABITANTS, null);
        assertTrue("Les gains totaux doivent augmenter après plusieurs cycles",
                economie.getGainsTotal() > gainsAvant);
    }
    
}