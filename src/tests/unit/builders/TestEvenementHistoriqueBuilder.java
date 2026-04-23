package tests.unit.builders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import moteur.donnees.Evenement;
import moteur.traitement.builders.EvenementHistoriqueBuilder;
import moteur.traitement.management.managers.evenement.EvenementHistoriqueManager;

/**
 * Classe de test unitaire pour {@link EvenementHistoriqueBuilder}.
 *
 * <p>
 * Elle vérifie :
 * <ul>
 *     <li>Que le manager d'événements existe</li>
 *     <li>Que le manager contient des événements</li>
 *     <li>Que chaque événement a un nom, une narration et un type non vide</li>
 *     <li>Que chaque événement appartient à la période viking (793-1066 après J.C.)</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public class TestEvenementHistoriqueBuilder {

	// Manager contenant les événements historiques
	private EvenementHistoriqueManager evenements;

	/**
	 * Initialise le manager avant chaque test.
	 */
	@Before
	public void prepare() {
		evenements = EvenementHistoriqueBuilder.buildEvenementManagerViking();
	}

	@Test
	public void testEvenements_nonNull() {
		assertNotNull("Le manager d'événements ne doit pas être nul", evenements);
	}

	@Test
	public void testEvenements_aDesEvenements() {
		assertTrue("Le manager doit contenir au moins un événement", evenements.getNbEvenementsHistoriques() > 0);
	}

	@Test
	public void testEvenements_nomNonVide() {
		for (Evenement evenement : evenements) {
			String nom = evenement.getNom();
			assertTrue("Le nom d'un événement ne doit pas être vide", nom != null && !nom.isEmpty());
		}
	}

	@Test
	public void testEvenements_narrationNonVide() {
		for (Evenement evenement : evenements) {
			String narration = evenement.getNarration();
			assertTrue("La narration d'un événement ne doit pas être vide", narration != null && !narration.isEmpty());
		}
	}

	@Test
	public void testEvenements_typeNonVide() {
		for (Evenement evenement : evenements) {
			String type = evenement.getType();
			assertTrue("Le type d'un événement ne doit pas être vide", type != null && !type.isEmpty());
		}
	}

	@Test
	public void testEvenements_anneeValide() {
		for (Evenement evenement : evenements) {
			int anneeDebut = evenement.getAnneeDebut();
			int anneeFin = evenement.getAnneeFin();

			assertTrue(
					String.format("L'événement %s doit être compris entre 793 et 1066, mais a début=%d et fin=%d",
							evenement.getNom(), anneeDebut, anneeFin),
					(anneeDebut >= 793 && anneeDebut <= 1066) &&
							(anneeFin >= 793 && anneeFin <= 1066)
			);
		}
	}
	
}