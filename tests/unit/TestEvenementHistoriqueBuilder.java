package tests.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import moteur.donnees.Evenement;
import moteur.traitement.builders.EvenementHistoriqueBuilder;
import moteur.traitement.management.EvenementHistoriqueManager;

/**
 * Classe de test de la classe EvenementHistoriqueBuilder
 * 
 * @author Alexandre 
 * @version 1.0
 */
public class TestEvenementHistoriqueBuilder {

	// Attributs 
	private EvenementHistoriqueManager evenements;
	
	/**
     * Méthode qui créer les valeurs pour les attributs
     */
	@Before
	public void prepare() {
		evenements = EvenementHistoriqueBuilder.buildEvenementManagerViking();
	}
	
	/**
	 * Méthode qui vérifie si le manager existe bien 
	 */
	@Test
    public void testEvenements_nonNull() {
        assertNotNull(evenements);
    }
 
	/**
	 * Méthode qui vérifie si le manager a des événements
	 */
    @Test
    public void testEvenements_aDesEvenements() {
        assertTrue(evenements.getNbEvenementsHistoriques() > 0);
    }

    /**
     * Méthode qui vérifie si pour chaque événement les noms ne sont pas vide
     */
    @Test
    public void testEvenements_nomNonVide() {
    	for (Evenement evenement : evenements) {
    		String nom = evenement.getNom();
    		assertTrue(nom != null && !nom.isEmpty());
    	}
    }
    
    /**
     * Méthode qui vérifie pour chaque événement si la narration existe
     */
    @Test
    public void testEvenements_narrationNonVide() {
    	for (Evenement evenement : evenements) {
    		String narration = evenement.getNarration();
    		assertTrue(narration != null && !evenement.getNarration().isEmpty());
    	}
    }
    
    /**
     * Méthode qui vérifie pour chaque événement si il a un type d'événement 
     */
    @Test
    public void testEvenements_typeNonVide() {
    	for (Evenement evenement : evenements) {
    		String type = evenement.getType();
    		assertTrue(type != null && !type.isEmpty());
    	}
    }
    
    /**
     * Méthode qui vérifie si les événements sont en lien avec la civilisation Viking (de 793 à 1066 après J.C.)
     */
    @Test
    public void testEvenements_anneeValide() {
    	for (Evenement evenement : evenements) {
    		int anneeDebut = evenement.getAnneeDebut();
    		int anneeFin = evenement.getAnneeFin();
    		
    		assertTrue((anneeDebut >= 793 && anneeDebut <= 1066) && (anneeFin >= 793 && anneeFin <= 1066));
    	}
    }
    
}
