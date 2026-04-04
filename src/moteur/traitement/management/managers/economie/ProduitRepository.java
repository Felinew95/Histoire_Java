package moteur.traitement.management.managers.economie;

import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static utilitaire.SimulationUtility.addElement;

/**
 * Repository pour gérer la liste des produits disponibles dans la simulation.
 *
 * <p>
 * Cette classe permet de centraliser l'accès aux noms des produits et d'assurer
 * qu'il n'y ait qu'une seule instance (singleton).
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public class ProduitRepository {

    /**
     * Liste contenant les noms des produits.
     */
    private final ArrayList<String> nomProduits = new ArrayList<>();

    /**
     * Logger pour enregistrer les messages d'erreur ou d'information.
     */
    private static final Logger logger = LoggerUtility.getLogger(RessourceRepository.class, "html");

    /**
     * Instance unique du repository (singleton).
     */
    private static final ProduitRepository instance = new ProduitRepository("src/csv/civilisation/produits.csv");

    /**
     * Constructeur privé qui charge les noms de produits depuis un fichier CSV.
     *
     * @param fichier Chemin vers le fichier CSV contenant les noms de produits
     */
    private ProduitRepository(String fichier) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                for (String nom: data) {
                    addElement(nomProduits, nom.trim());
                }
            }
        } catch (IOException e) {
            logger.error("Erreur dans le chargement des ressources depuis " + fichier, e);
        }
    }

    /**
     * Retourne l'instance unique du repository.
     *
     * @return l'instance de {@link ProduitRepository}
     */
    public static ProduitRepository getInstance() {
        return instance;
    }

    /**
     * Retourne le nombre total de produits disponibles dans le repository.
     *
     * @return le nombre de produits
     */
    public int getNombreProduits() {
        return nomProduits.size();
    }

    /**
     * Retourne le nom du produit à un indice donné.
     *
     * @param indice position du produit dans la liste
     * @return nom du produit
     */
    public String getNomProduit(int indice) {
        return nomProduits.get(indice);
    }

}