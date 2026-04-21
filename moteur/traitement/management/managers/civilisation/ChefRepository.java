package moteur.traitement.management.managers.civilisation;

import log.LoggerUtility;
import moteur.donnees.Chef;
import org.apache.log4j.Logger;

import moteur.donnees.Kersir;
import moteur.donnees.Konungr;
import moteur.traitement.management.factory.SimFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import static utilitaire.SimulationUtility.addElement;

/**
 * Gestionnaire des chefs de la civilisation, séparant les {@link Konungr} et {@link Kersir}.
 *
 * <p>
 * Cette classe charge automatiquement les chefs depuis un fichier CSV lors de son instanciation
 * et fournit des méthodes pour obtenir le chef actuel en fonction de l'année simulée.
 * </p>
 *
 * <p>
 * Les chefs sont stockés dans des listes distinctes pour chaque type et la récupération
 * se fait via des méthodes spécifiques.
 * </p>
 *
 * @author Alexandre
 * @version 2.0
 *
 * @see Konungr
 * @see Kersir
 */
public class ChefRepository {

    /**
     * Logger pour suivre le chargement et les erreurs.
     */
    private static final Logger logger =
            LoggerUtility.getLogger(ChefRepository.class, "html");

    /**
     * Liste des Konungr chargés depuis le CSV.
     */
    private final List<Konungr> konungrs = new ArrayList<>();

    /**
     * Liste des Kersir chargés depuis le CSV.
     */
    private final List<Kersir> kersirs = new ArrayList<>();

    /**
     * Instance unique du repository (Singleton).
     */
    private static final ChefRepository instance = new ChefRepository("src/csv/civilisation/chefs.csv");

    /**
     * Constructeur de ChefRepository.
     *
     * <p>
     * Charge les chefs depuis le fichier CSV {@code src/csv/civilisation/chefs.csv}.
     * Chaque ligne est parsée et ajoutée dans la liste correspondante via {@link #ajouterChef(String, String, int, int)}.
     * </p>
     */
    private ChefRepository(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                String nom = data[1];
                int anneeDebut = Integer.parseInt(data[2]);
                int anneeFin = Integer.parseInt(data[3]);

                ajouterChef(data[0], nom, anneeDebut, anneeFin);
            }
        } catch (IOException e) {
            logger.error("Erreur dans le chargement des ressources depuis " + fichier, e);
        }
    }

    /**
     * Ajoute un chef dans la liste appropriée en fonction de son type.
     *
     * @param type       le type de chef ("KONUNGR" ou "KERSIR")
     * @param nom        le nom du chef
     * @param anneeDebut l'année de début du règne ou de l'activité
     * @param anneeFin   l'année de fin du règne ou de l'activité
     */
    private void ajouterChef(String type, String nom, int anneeDebut, int anneeFin) {
        switch (type) {
            case "KONUNGR":
                Konungr konungr = SimFactory.buildKonungr(nom, anneeDebut, anneeFin);
                addElement(konungrs,  konungr);
                break;
            case "KERSIR":
                Kersir kersir = SimFactory.buildKersir(nom, anneeDebut, anneeFin);
                addElement(kersirs, kersir);
                break;
        }

        logger.info("Chef ajouté : nom=" + nom + ", anneeDebut=" + anneeDebut + ", anneeFin=" + anneeFin);
    }

    /**
     * Retourne l'instance unique du repository.
     *
     * @return l'instance unique de {@link ChefRepository}
     */
    public static ChefRepository getInstance() {
        return instance;
    }

    /**
     * Retourne le {@link Konungr} actif pour l'année donnée.
     *
     * @param anneeSim l'année de la simulation
     * @return le Konungr actif pour cette année, ou {@code null} si aucun n'est trouvé
     */
    public Konungr getKonungrActuel(String nomCivilisation, int anneeSim) {
    	if (!nomCivilisation.equals("Viking")) {
    		return null;
    	}
    	
        return getChefActuel(konungrs,  anneeSim);
    }

    /**
     * Retourne le {@link Kersir} actif pour l'année donnée.
     *
     * @param anneeSim l'année de la simulation
     * @return le Kersir actif pour cette année, ou {@code null} si aucun n'est trouvé.
     */
    public Kersir getKersirActuel(String nomCivilisation, int anneeSim) {
    	if (!nomCivilisation.equals("Viking")) {
    		return null;
    	}
    	
        return getChefActuel(kersirs,  anneeSim);
    }

    /**
     * Retourne le chef actif pour une année donnée parmi une liste de chefs.
     *
     * @param chefs    la liste des chefs à parcourir
     * @param anneeSim l'année de la simulation
     * @param <T>      type de chef (Konungr ou Kersir)
     * @return le chef actif pour cette année, ou {@code null} si aucun n'est actif
     */
    private <T extends Chef> T getChefActuel(List<T> chefs, int anneeSim) {
        for (T chef : chefs) {
            if (anneeSim >= chef.getAnneeDebut() && anneeSim <= chef.getAnneeFin()) {
                return chef;
            }
        }
        return null;
    }

}
