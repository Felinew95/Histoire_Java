package tests.unit.repository;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import moteur.traitement.management.managers.economie.ProduitRepository;

/**
 * Suite de tests unitaires pour la classe {@link ProduitRepository}.
 * 
 * <p>
 * Cette classe vérifie le bon chargement des produits depuis le fichier CSV,
 * la gestion du Singleton et la validité des accès par indice aux noms des produits.
 * </p>
 *  
 * @author Alexandre
 * @version 2.0
 */
public class TestProduitRepository {

    /** 
     * Instance du repository à tester. 
     */
    private ProduitRepository repository;

    /**
     * Initialisation du contexte de test.
     * <p>Récupère l'unique instance de {@link ProduitRepository} avant chaque test.</p>
     */
    @Before
    public void setUp() {
        repository = ProduitRepository.getInstance();
    }

    /**
     * Vérifie que le Singleton fonctionne correctement.
     * <p>S'assure que l'instance n'est pas nulle et qu'elle est unique en mémoire.</p>
     */
    @Test
    public void testSingletonInstance() {
        assertNotNull("L'instance ne doit pas être nulle", repository);
        ProduitRepository autreInstance = ProduitRepository.getInstance();
        assertSame("Le repository doit être un Singleton (même référence)", repository, autreInstance);
    }

    /**
     * Vérifie que le chargement initial a bien eu lieu.
     * <p>Le test échoue si la liste de produits est vide (ce qui signifierait un échec de lecture CSV).</p>
     */
    @Test
    public void testGetNombreProduits() {
        int nbProduits = repository.getNombreProduits();
        assertTrue("Le repository devrait contenir au moins un produit chargé depuis le CSV", nbProduits > 0);
    }

    /**
     * Teste la récupération du nom d'un produit par son index.
     * <p>Vérifie que le premier élément de la liste est bien une chaîne de caractères valide.</p>
     */
    @Test
    public void testGetNomProduit_ValidIndex() {
        if (repository.getNombreProduits() > 0) {
            String nom = repository.getNomProduit(0);
            assertNotNull("Le nom du premier produit ne doit pas être nul", nom);
            assertFalse("Le nom du produit ne doit pas être vide", nom.isEmpty());
        }
    }

    /**
     * Teste le comportement face à un index invalide.
     * <p>S'attend à ce qu'une exception {@link IndexOutOfBoundsException} soit levée.</p>
     */
    @Test
    public void testGetNomProduit_InvalidIndex() {
        assertNull(repository.getNomProduit(-1));
    }
    
}