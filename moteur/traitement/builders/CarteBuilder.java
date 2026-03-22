package moteur.traitement.builders;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import log.LoggerUtility;

import moteur.carte.Continent;
import moteur.donnees.Region;
import moteur.traitement.management.SimFactory;

/**
 * Classe qui construit les différentes iles
 * 
 * @author Alexandre et Massinissa
 * @version 1.0
 * 
 * @see Continent
 */
public class CarteBuilder {

	// Attributs
	private static Logger logger = LoggerUtility.getLogger(CarteBuilder.class, "html");

    /**
     * Méthode qui construit une ile
     * 
     * @param fichier      : Nom du fichier csv contenant les blocs
     * @param nomRegion    : Nom de la région d'appartenance
     * @param chef         : Chef de la région
     * @param nomIle       : Nom de l'ile
     * @param positionIle  : Position de l'ile
     * @return La représentation de l'ile
     * 
     * @throws IOException
     */
    private static Continent buildIle(String fichier, String nomRegion, String chef, String nomIle, Point positionIle) throws IOException {
        logger.info("Début du chargement de l'île \"" + nomIle + "\" depuis : " + fichier);
        Continent continent;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            br.readLine(); // Saute la tête (ligne, colonne, type)
            Region region = SimFactory.buildRegion(nomRegion, chef);
            continent = SimFactory.buildIle(nomIle, positionIle, region);
            ajoutBlocsIle(continent, br);
        }

        logger.info("Chargement terminé de l'île \"" + nomIle + "\" : " + continent.getNbBlocs() + " blocs chargés");
        return continent;
    }

    /**
     * Méthode qui ajoute des blocs à l'ile 
     * 
     * @param continent : Une ile 
     * @param br  : Lit le fichier texte
     * 
     * @throws IOException
     */
	private static void ajoutBlocsIle(Continent continent, BufferedReader br) throws IOException {
		String ligne;
		String[] valeurs;
		
		while ((ligne = br.readLine()) != null) {
		    valeurs = ligne.split(",");
		    continent.ajouterBloc(SimFactory.buildBloc(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]), valeurs[2]));
		    logger.debug("Bloc ajouté à l'île : ligne=" + valeurs[0] + ", colonne=" + valeurs[1] + ", type=" + valeurs[2]);
		}
	}
    	
    /**
     * Méthode qui construit l'ile de Lindsfarne
     * 
     * @return L'ile de Lindsfarne
     */
    public static Continent buildLindsfarne() {
        logger.info("Chargement de la carte : Lindsfarne");
        try {
            return buildIle("src/csv/cartes/carte_lindsfarne.csv", "Angleterre", "Aelred de Rievaulx", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de Lindsfarne : " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit l'Europe 
     * 
     * @return L'Europe 
     */
    public static Continent buildEurope() {
        logger.info("Chargement de la carte : Europe ");
        try {
            return buildIle("src/csv/cartes/carte_europe.csv", "Europe", "Charlemagne", "EuropeDuNord", 
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de l'Europe  : " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit le Vinland
     * 
     * @return Le Vinland
     */
    public static Continent buildVinland() {
        logger.info("Chargement de la carte : Vinland");
        try {
            return buildIle("src/csv/cartes/carte_vinland.csv", "Vinland", "Leif Erikson", "Vinland", 
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement du Vinland : " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de la Normandie
     * 
     * @return La Normandie
     */
    public static Continent buildNormandie() {
        logger.info("Chargement de la carte : Normandie");
        try {
            return buildIle("src/csv/cartes/carte_normandie.csv", "Normandie", "Rollon", "Normandie", 
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de la Normandie : " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de l'Islande
     * 
     * @return La carte de l'Islande
     */
    public static Continent buildIslande() {
        logger.info("Chargement de la carte : Islande");
        try {
            return buildIle("src/csv/cartes/carte_islande.csv", "Islande", "Ingólfr Arnarson", "Islande", 
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de l'Islande : " + e.getMessage());
        }
        return null;
    } 
    
    /**
     * Méthode qui construit la carte de la Grande-Bretagne
     * 
     * @return La carte de la Grande-Bretagne
     */
    public static Continent buildGrandeBretagne() {
        logger.info("Chargement de la carte : Grande-Bretagne");
        try {
            return buildIle("src/csv/cartes/carte_grande_bretagne.csv", "Grande-Bretagne", "Alfred le Grand", "Grande-Bretagne", 
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de la Grande-Bretagne : " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de la Scandinavie
     * 
     * @return La carte de la Scandinavie
     */
    public static Continent buildScandinave() {
        logger.info("Chargement de la carte : Scandinavie");
        try {
            return buildIle("src/csv/cartes/carte_scandinavie.csv", "Scandinavie", "Harald à la Belle Chevelure", "Scandinavie", 
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de la Scandinavie : " + e.getMessage());
        }
        return null;
    }

}