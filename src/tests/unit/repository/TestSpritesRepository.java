package tests.unit.repository;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.image.BufferedImage;
import config.SpritesRepository;

/**
 * Suite de tests unitaires pour la classe {@link SpritesRepository}.
 * 
 * <p>
 * Ces tests valident le mécanisme de chargement des ressources graphiques (sprites),
 * le respect du pattern Singleton pour l'accès aux images, ainsi que la 
 * validité des objets {@link BufferedImage} récupérés.
 * </p>
 * 
 * @author Tauseef
 * @version 1.0
 */
public class TestSpritesRepository {

    /** 
     * Instance du dépôt de sprites à tester. 
     */
    private SpritesRepository repository;
    
    /**
     * Initialisation du contexte de test.
     * <p>Récupère l'unique instance de {@link SpritesRepository} avant chaque test.</p>
     */
    @Before
    public void setUp() {
        repository = SpritesRepository.getInstance();
    }

    /**
     * Vérifie que le Singleton est correctement implémenté.
     * <p>S'assure que plusieurs appels à {@code getInstance()} renvoient la même référence.</p>
     */
    @Test
    public void testSingletonUnicity() {
        assertNotNull("L'instance ne doit pas être null", repository);
        SpritesRepository secondeInstance = SpritesRepository.getInstance();
        assertSame("Le repository doit retourner la même instance (unicité mémoire)", 
                   repository, secondeInstance);
    }

    /**
     * Teste la récupération d'une image existante.
     * 
     * <p>
     * Vérifie que les images essentielles au rendu (ex: eau, herbe) sont bien 
     * chargées dans la map.
     * </p>
     */
    @Test
    public void testGetImage_Success() {
        // "eau1.png" est utilisé dans PaintStrategy, il doit être présent
        BufferedImage image = repository.getImage("eau1.png");
        assertNotNull("L'image 'eau1.png' devrait être chargée dans le dépôt", image);
    }

    /**
     * Teste le comportement lors de la demande d'une image inexistante.
     * <p>Vérifie que la méthode retourne {@code null} sans lever d'exception.</p>
     */
    @Test
    public void testGetImage_NotFound() {
        assertNull("Une image inexistante doit retourner null", 
                   repository.getImage("image_qui_n_existe_pas.png"));
    }

    /**
     * Vérifie l'intégrité des données chargées.
     * 
     * <p>
     * S'assure que les objets récupérés sont bien des instances valides de 
     * {@link BufferedImage} avec des dimensions cohérentes.
     * </p>
     */
    @Test
    public void testImageIntegrity() {
        BufferedImage image = repository.getImage("herbe.png");
        if (image != null) {
            assertTrue("La largeur de l'image doit être supérieure à 0", image.getWidth() > 0);
            assertTrue("La hauteur de l'image doit être supérieure à 0", image.getHeight() > 0);
        }
    }
}