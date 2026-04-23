package moteur.traitement.builders;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import moteur.traitement.management.factory.MapFactory;
import org.apache.log4j.Logger;

import log.LoggerUtility;

import moteur.carte.Continent;
import moteur.donnees.Region;
import moteur.traitement.management.factory.SimFactory;

/**
 * Classe utilitaire pour la construction des cartes et des îles de la simulation.
 *
 * <p>
 * Cette classe fournit des méthodes pour créer des {@link Continent} à partir
 * de fichiers CSV représentant les blocs de la carte. Chaque île ou région
 * est construite avec un nom, un chef et une position dans la simulation.
 * </p>
 *
 * <p>
 * Les cartes disponibles incluent Lindsfarne, l'Europe, le Vinland, la Normandie,
 * l'Islande, la Grande-Bretagne et la Scandinavie. Les méthodes publiques
 * permettent de récupérer directement ces continents prêts à l'emploi.
 * </p>
 *
 * <p>
 * Un logger {@link Logger} est utilisé pour suivre le chargement des cartes,
 * signaler les erreurs et enregistrer le détail de chaque bloc ajouté.
 * </p>
 *
 * @author Alexandre
 * @author Massinissa
 *
 * @version 2.0
 *
 * @see Continent
 * @see Region
 */
public class CarteBuilder {

    /**
     * Logger HTML pour le suivi du chargement des cartes.
     */
	private static final Logger logger = LoggerUtility.getLogger(CarteBuilder.class, "html");

    /**
     * Construit un continent à partir d'un fichier CSV de blocs.
     *
     * @param fichier       chemin vers le fichier CSV contenant les blocs
     * @param nomRegion     nom de la région d'appartenance
     * @param chef          nom du chef de la région
     * @param nomIle        nom de l'île à construire
     * @param positionIle   position de l'île dans la simulation
     * @return le {@link Continent} construit, ou {@code null} en cas d'erreur
     * @throws IOException si une erreur de lecture du fichier se produit
     */
    private static Continent buildContinent(String fichier, String nomRegion, String chef, String nomIle, Point positionIle) throws IOException {
        logger.info("Début du chargement de l'île \"" + nomIle + "\" depuis : " + fichier);
        Continent continent;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            br.readLine(); // Saute la tête (ligne, colonne, type)
            Region region = SimFactory.buildRegion(nomRegion, chef);
            continent = MapFactory.buildIle(nomIle, positionIle, region);
            ajoutBlocsContinent(continent, br);
        }

        logger.info("Chargement terminé de l'île \"" + nomIle + "\" : " + continent.getNbBlocs() + " blocs chargés");
        return continent;
    }

    /**
     * Ajoute des blocs au continent à partir du lecteur CSV.
     *
     * @param continent le {@link Continent} à compléter
     * @param br        lecteur du fichier CSV
     * @throws IOException si une erreur de lecture se produit
     */
	private static void ajoutBlocsContinent(Continent continent, BufferedReader br) throws IOException {
		String ligne;
		String[] valeurs;
		
		while ((ligne = br.readLine()) != null) {
		    valeurs = ligne.split(",");
		    continent.ajouterBloc(MapFactory.buildBloc(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]), valeurs[2]));
		    logger.debug("Bloc ajouté à l'île : ligne=" + valeurs[0] + ", colonne=" + valeurs[1] + ", type=" + valeurs[2]);
		}
	}

	/**
     * Génère la carte de Lindisfarne.
     * 
     * <p>
     * Initialise le continent avec les données du Wessex, sous l'autorité 
     * historique de {@code Aelred de Rievaulx}.
     * </p>
     * 
     * @return Une instance de {@link Continent} centrée sur Lindisfarne, ou {@code null} si le fichier CSV est inaccessible.
     */
    public static Continent buildLindsfarne() {
        logger.info("Chargement de la carte : Lindsfarne");
        try {
            return buildContinent("src/csv/cartes/carte_lindsfarne.csv", "Angleterre", "Aelred de Rievaulx", "Lindsfarne",
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de Lindsfarne : " + e.getMessage());
        }
        return null;
    }

    /**
     * Génère la carte de l'Europe continentale.
     * <p>Définit le domaine impérial de {@code Charlemagne} pour la région Europe du Nord.</p>
     * @return Le {@link Continent} européen configuré, ou {@code null} en cas d'erreur d'E/S.
     */
    public static Continent buildEurope() {
        logger.info("Chargement de la carte : Europe ");
        try {
            return buildContinent("src/csv/cartes/carte_europe.csv", "Europe", "Charlemagne", "EuropeDuNord",
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de l'Europe  : " + e.getMessage());
        }
        return null;
    }

    /**
     * Génère la carte du Vinland (Terre-Neuve).
     * <p>Associe le territoire aux explorations de {@code Leif Erikson}.</p>
     * @return Le {@link Continent} du Nouveau Monde, ou {@code null} si le CSV est corrompu.
     */
    public static Continent buildVinland() {
        logger.info("Chargement de la carte : Vinland");
        try {
            return buildContinent("src/csv/cartes/carte_vinland.csv", "Vinland", "Leif Erikson", "Vinland",
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement du Vinland : " + e.getMessage());
        }
        return null;
    }

    /**
     * Génère la carte de la Normandie.
     * <p>Établit le duché sous le commandement du chef viking {@code Rollon}.</p>
     * @return Le {@link Continent} normand, ou {@code null} en cas d'échec de lecture.
     */
    public static Continent buildNormandie() {
        logger.info("Chargement de la carte : Normandie");
        try {
            return buildContinent("src/csv/cartes/carte_normandie.csv", "Normandie", "Rollon", "Normandie",
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de la Normandie : " + e.getMessage());
        }
        return null;
    }

    /**
     * Génère la carte de l'Islande.
     * <p>Configure la colonisation par le premier colon permanent, {@code Ingólfr Arnarson}.</p>
     * @return Le {@link Continent} islandais, ou {@code null} en cas d'erreur système.
     */
    public static Continent buildIslande() {
        logger.info("Chargement de la carte : Islande");
        try {
            return buildContinent("src/csv/cartes/carte_islande.csv", "Islande", "Ingólfr Arnarson", "Islande",
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de l'Islande : " + e.getMessage());
        }
        return null;
    }

    /**
     * Génère la carte de la Grande-Bretagne.
     * <p>Initialise le royaume sous l'égide de {@code Alfred le Grand}.</p>
     * @return Le {@link Continent} britannique complet, ou {@code null} si le chargement échoue.
     */
    public static Continent buildGrandeBretagne() {
        logger.info("Chargement de la carte : Grande-Bretagne");
        try {
            return buildContinent("src/csv/cartes/carte_grande_bretagne.csv", "Grande-Bretagne", "Alfred le Grand", "Grande-Bretagne",
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de la Grande-Bretagne : " + e.getMessage());
        }
        return null;
    }

    /**
     * Génère la carte de la Scandinavie.
     * <p>Configure les terres ancestrales unifiées par {@code Harald à la Belle Chevelure}.</p>
     * @return Le {@link Continent} scandinave, ou {@code null} en cas d'exception I/O.
     */
    public static Continent buildScandinave() {
        logger.info("Chargement de la carte : Scandinavie");
        try {
            return buildContinent("src/csv/cartes/carte_scandinavie.csv", "Scandinavie", "Harald à la Belle Chevelure", "Scandinavie",
            		new Point(800, 500));
        } catch (IOException e) {
            logger.error("Erreur lors du chargement de la Scandinavie : " + e.getMessage());
        }
        return null;
    }
    
}