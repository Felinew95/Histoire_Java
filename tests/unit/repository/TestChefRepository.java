package tests.unit.repository;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import moteur.donnees.Konungr;
import moteur.traitement.management.managers.civilisation.ChefRepository;

/**
 * Classe de tests unitaires pour le dépôt {@link ChefRepository}.
 * 
 * <p>
 * Ces tests vérifient la bonne récupération des chefs de civilisation selon 
 * des critères temporels et de validité de civilisation.
 * </p>
 * 
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 2.0
 */
public class TestChefRepository {

    /** 
     * Instance du dépôt à tester. 
     */
    private ChefRepository repository;
    
    /**
     * Initialisation du contexte de test.
     * <p>Récupère l'instance unique du Singleton {@link ChefRepository}.</p>
     */
    @Before
    public void setUp() {
        repository = ChefRepository.getInstance();
    }

    /**
     * Teste la récupération réussie d'un Konungr.
     * 
     * <p>
     * Vérifie qu'un chef est bien retourné pour la civilisation "Viking" 
     * à une date historiquement couverte par le fichier de données (an 879).
     * </p>
     */
    @Test
    public void testGetKonungrActuel_Success() {
        Konungr chef = repository.getKonungrActuel("Viking", 879);
        assertNotNull("Un chef devrait exister pour l'an 879", chef);
    }

    /**
     * Teste le comportement face à une civilisation non gérée.
     * 
     * <p>
     * Vérifie que la méthode retourne {@code null} si le nom de la 
     * civilisation demandée ne correspond pas à "Viking", même si l'année est valide.
     * </p>
     */
    @Test
    public void testWrongCiv() {
        assertNull("La méthode doit retourner null pour une civilisation inconnue (ex: Romain)", 
                   repository.getKonungrActuel("Romain", 800));
    }

    /**
     * Teste la récupération avec des dates hors limites.
     * 
     * <p>
     * Vérifie que le système gère correctement les années situées 
     * bien avant ou bien après les données enregistrées dans le CSV.
     * </p>
     */
    @Test
    public void testYearOutOfBounds() {
        assertNull("Aucun chef ne devrait être trouvé pour l'an -500", 
                   repository.getKonungrActuel("Viking", -500));
        assertNull("Aucun chef ne devrait être trouvé pour l'an 5000", 
                   repository.getKonungrActuel("Viking", 5000));
    }
    
}
