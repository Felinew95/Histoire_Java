package tests.unit.repository;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Point;
import java.util.Set;
import gui.management.RegionRepository;

/**
 * Classe de tests unitaires pour {@link RegionRepository}.
 * 
 * <p>
 * Cette classe vérifie l'intégrité du chargement des données géographiques, 
 * l'unicité de l'instance du Singleton et la précision de la récupération 
 * des coordonnées des régions.
 * </p>
 * 
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 2.0
 */
public class TestRegionRepository {

    /** 
     * Instance du dépôt à tester. 
     */
    private RegionRepository repository;
    
    /**
     * Initialise le contexte de test avant chaque exécution.
     * <p>Récupère l'instance globale du Singleton {@link RegionRepository}.</p>
     */
    @Before
    public void setUp() {
        repository = RegionRepository.getInstance();
    }

    /**
     * Vérifie que le Singleton renvoie bien une instance valide.
     */
    @Test
    public void testSingletonInstance() {
        assertNotNull("L'instance du repository ne doit pas être null", repository);
    }

    /**
     * Teste la récupération d'une position pour une région existante.
     * 
     * <p>
     * On vérifie ici qu'une région attendue (ex: "Scandinavie") retourne 
     * bien un {@link Point} non nul.
     * </p>
     */
    @Test
    public void testGetPosition_Success() {
        Point p = repository.getPosition("Scandinavie");
        assertNotNull("Une région existante doit retourner un point", p);
    }

    /**
     * Teste la récupération d'une région inexistante.
     * <p>Vérifie que le système retourne {@code null} sans lever d'exception.</p>
     */
    @Test
    public void testGetPosition_NotFound() {
        assertNull("Une région imaginaire doit retourner null", 
                   repository.getPosition("Atlantide"));
    }

    /**
     * Vérifie que le chargement initial n'est pas vide.
     * <p>S'assure que le fichier CSV a été lu et que des régions ont été stockées.</p>
     */
    @Test
    public void testGetAllNomRegions() {
        Set<String> noms = repository.getAllNomRegions();
        assertNotNull("L'ensemble des noms ne doit pas être null", noms);
        assertFalse("Le repository ne devrait pas être vide après chargement", noms.isEmpty());
    }

    /**
     * Teste la validité des coordonnées récupérées.
     * <p>Vérifie que les valeurs X et Y du point ne sont pas aberrantes (positives).</p>
     */
    @Test
    public void testCoordinateIntegrity() {
        Set<String> noms = repository.getAllNomRegions();
        if (!noms.isEmpty()) {
            String premierNom = noms.iterator().next();
            Point p = repository.getPosition(premierNom);
            assertTrue("La coordonnée X (ligne) doit être positive", p.getX() >= 0);
            assertTrue("La coordonnée Y (colonne) doit être positive", p.getY() >= 0);
        }
    }
    
}