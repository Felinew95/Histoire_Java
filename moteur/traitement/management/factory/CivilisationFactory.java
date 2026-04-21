package moteur.traitement.management.factory;

import config.SimConfig;
import gui.management.ChartManager;
import gui.management.MobileManager;
import moteur.carte.Carte;
import moteur.donnees.*;
import moteur.traitement.builders.EvenementHistoriqueBuilder;
import moteur.traitement.management.managers.civilisation.ChefRepository;
import moteur.traitement.management.managers.civilisation.CivilisationManager;
import org.apache.log4j.Logger;
import log.LoggerUtility;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory qui construit toutes les civilisations ET leurs SimulationManager.
 *
 * <p>
 * Chaque civilisation adverse reçoit son propre {@link CivilisationManager}
 * avec les événements qui la concernent. Ainsi ArmeeManager, EconomieManager,
 * PopulationManager etc. s'appliquent identiquement sur toutes les civs —
 * c'est l'événement reçu qui fait la différence.
 * </p>
 *
 * @author Tauseef
 * @version 2.0
 */
public class CivilisationFactory {

	/** 
	 * Logger pour le suivi de la construction des entités en format HTML. 
	 */
    private static final Logger logger = LoggerUtility.getLogger(CivilisationFactory.class, "html");

    /** 
     * Couleur représentative des Vikings (Brun/Terre). 
     */
    private static final Color COULEUR_VIKING = new Color(0x7A3E1D);
    
    /** 
     * Couleur représentative des Anglo-Saxons (Rouge). 
     */
    private static final Color COULEUR_ANGLO_SAXONS = new Color(0xC0392B);
    
    /** 
     * Couleur représentative des Francs (Bleu). 
     */
    private static final Color COULEUR_FRANCS = new Color(0x2471A3);
    
    /** 
     * Couleur représentative des Irlandais (Vert). 
     */
    private static final Color COULEUR_IRLANDAIS = new Color(0x27AE60);

    /** 
     * Constructeur privé pour empêcher l'instanciation de cette classe utilitaire. 
     */
    private CivilisationFactory() {}

    /**
     * Organise la création des gestionnaires pour toutes les civilisations adverses.
     *
     * @param anneeDebut Année de début de la simulation.
     * @param carte La carte du monde partagée entre toutes les simulations.
     * @return Une liste contenant les {@link CivilisationManager} des Anglo-Saxons, Francs et Irlandais.
     */
    public static Civilisation buildVikings(int anneeDebut) {
        logger.info("Construction civilisation Viking");
        ChefRepository chefs = ChefRepository.getInstance();

        Civilisation vikings = SimFactory.buildCivilisation(
                chefs.getKonungrActuel("", anneeDebut),
                SimFactory.buildEconomie(),
                SimFactory.buildArmee(chefs.getKersirActuel(SimConfig.NOM_CIVILISATION, anneeDebut), 10000, 300, 10, 80f),
                SimFactory.buildPopulation(SimConfig.NB_HABITANT_DEBUT_SIM,
                        SimConfig.AGE_MOYEN_DEBUT_SIM, SimConfig.SEXE_MAJORITAIRE_DEBUT_SIM),
                SimFactory.buildReligion(SimConfig.NOM_RELIGION_DEBUT,
                        SimConfig.CROYANCE_RELIGION_DEBUT, SimConfig.INFLUENCE_RELIGION_DEBUT),
                SimFactory.buildPolitique(SimConfig.TYPE_REGIME_DEBUT,
                        SimConfig.STABILITE_POLITIQUE_DEBUT),
                SimConfig.NOM_CIVILISATION,
                COULEUR_VIKING, "⚔️"
        );


        vikings.ajouterRelation(SimFactory.buildRelation("Anglo-Saxons", 10f));
        vikings.ajouterRelation(SimFactory.buildRelation("Francs", 20f));
        vikings.ajouterRelation(SimFactory.buildRelation("Irlandais", 15f));

        return vikings;
    }

    /**
     * Construit les 3 civilisations adverses avec leur SimulationManager.
     *
     * @param anneeDebut année de départ
     * @param carte carte partagée (peut être une carte vide)
     * @return liste des SimulationManager des civs adverses
     */
    public static List<CivilisationManager> buildSimulationManagersAdverses(
            int anneeDebut, Carte carte) {

        List<CivilisationManager> managers = new ArrayList<>();
        managers.add(buildSimManagerAngloSaxons(anneeDebut, carte));
        managers.add(buildSimManagerFrancs(anneeDebut, carte));
        managers.add(buildSimManagerIrlandais(anneeDebut, carte));
        return managers;
    }

    /**
     * Crée le gestionnaire complet pour la civilisation Anglo-Saxonne.
     * 
     * <p>
     * Inclut la configuration spécifique de l'Angleterre et de Lindisfarne, 
     * ainsi que le moteur d'événements historiques dédié à cette région.
     * </p>
     *
     * @param anneeDebut Année de départ.
     * @param carte Carte globale.
     * @return Un {@link CivilisationManager} prêt à l'emploi.
     */
    public static CivilisationManager buildSimManagerAngloSaxons(int anneeDebut, Carte carte) {
        logger.info("Construction SimulationManager Anglo-Saxons");

        Civilisation civ = SimFactory.buildCivilisation(
                SimFactory.buildKonungr("Alfred le Grand", 878, 883),
                SimFactory.buildEconomie(),
                SimFactory.buildArmee(SimFactory.buildKersir("Æthelred", 878, 899),
                        15000, 50, 8, 72f),
                SimFactory.buildPopulation(20000, 28f, "HOMME"),
                SimFactory.buildReligion("Christianisme", "Foi catholique romaine", 95f),
                SimFactory.buildPolitique("Monarchie saxonne", 55f),
                "Anglo-Saxons",
                COULEUR_ANGLO_SAXONS, "🏰"
        );

        civ.ajouterRegion(SimFactory.buildRegion("Angleterre", "Alfred le Grand"));
        civ.ajouterRegion(SimFactory.buildRegion("Lindisfarne", "Æthelred"));
        civ.ajouterRelation(SimFactory.buildRelation("Viking", 10f));
        civ.ajouterRelation(SimFactory.buildRelation("Francs", 60f));

        // SimulationManager avec les événements Anglo-Saxons uniquement
        return new CivilisationManager(
                anneeDebut, carte, civ,
                new MobileManager(),
                EvenementHistoriqueBuilder.buildEvenementManagerAngloSaxons(),
                new ChartManager()
        );
    }

    /**
     * Crée le gestionnaire complet pour la civilisation Franque.
     * 
     * <p>
     * Configure l'Empire Carolingien et la Normandie, avec une gestion 
     * d'événements centrée sur l'Europe du Nord.
     * </p>
     *
     * @param anneeDebut Année de départ.
     * @param carte Carte globale.
     * @return Un {@link CivilisationManager} prêt à l'emploi.
     */
    public static CivilisationManager buildSimManagerFrancs(int anneeDebut, Carte carte) {
        logger.info("Construction SimulationManager Francs");

        Civilisation civ = SimFactory.buildCivilisation(
                SimFactory.buildKonungr("Rollon", 911, 959),
                SimFactory.buildEconomie(),
                SimFactory.buildArmee(SimFactory.buildKersir("Charles le Simple", 911, 929),
                        25000, 20, 12, 80f),
                SimFactory.buildPopulation(20000, 30f, "HOMME"),
                SimFactory.buildReligion("Christianisme franc", "Catholicisme carolingien", 98f),
                SimFactory.buildPolitique("Empire carolingien", 68f),
                "Francs",
                COULEUR_FRANCS, "⚜️"
        );

        civ.ajouterRegion(SimFactory.buildRegion("Normandie", "Rollon"));
        civ.ajouterRegion(SimFactory.buildRegion("EuropeDuNord", "Charlemagne"));
        civ.ajouterRelation(SimFactory.buildRelation("Viking", 20f));
        civ.ajouterRelation(SimFactory.buildRelation("Anglo-Saxons", 60f));

        return new CivilisationManager(
                anneeDebut, carte, civ,
                new MobileManager(),
                EvenementHistoriqueBuilder.buildEvenementManagerFrancs(),
                new ChartManager()
        );
    }

    /**
     * Crée le gestionnaire complet pour la civilisation Irlandaise.
     * 
     * <p>
     * Définit le système politique du Haut-Roi (Ard Rí) 
     * et les événements spécifiques à l'île d'Irlande.
     * </p>
     *
     * @param anneeDebut Année de départ.
     * @param carte Carte globale.
     * @return Un {@link CivilisationManager} prêt à l'emploi.
     */
    public static CivilisationManager buildSimManagerIrlandais(int anneeDebut, Carte carte) {
        logger.info("Construction SimulationManager Irlandais");

        Civilisation civ = SimFactory.buildCivilisation(
                SimFactory.buildKonungr("Brian Boru", 1014, 1034),
                SimFactory.buildEconomie(),
                SimFactory.buildArmee(SimFactory.buildKersir("Malachy II", 980, 1022),
                        8000, 30, 5, 65f),
                SimFactory.buildPopulation(20000, 25f, "HOMME"),
                SimFactory.buildReligion("Christianisme celtique", "Foi monastique irlandaise", 92f),
                SimFactory.buildPolitique("Ard Rí (Haut-Roi)", 45f),
                "Irlandais",
                COULEUR_IRLANDAIS, "☘️"
        );

        civ.ajouterRegion(SimFactory.buildRegion("Irlande", "Brian Boru"));
        civ.ajouterRelation(SimFactory.buildRelation("Viking", 15f));
        civ.ajouterRelation(SimFactory.buildRelation("Anglo-Saxons", 50f));

        return new CivilisationManager(
                anneeDebut, carte, civ,
                new MobileManager(),
                EvenementHistoriqueBuilder.buildEvenementManagerIrlandais(),
                new ChartManager()
        );
    }

    /**
     * Utilitaire pour isoler les objets Civilisation des gestionnaires.
     * 
     * <p>
     * Utile pour les composants d'interface graphique (comme les JComboBox) 
     * qui n'ont besoin que des données de la civilisation sans la logique de simulation.
     * </p>
     *
     * @param managers Liste des managers dont on veut extraire les données.
     * @return Une liste d'objets {@link Civilisation}.
     */
    public static List<Civilisation> getCivilisationsAdverses(
            List<CivilisationManager> managers) {
        List<Civilisation> civs = new ArrayList<>();
        for (CivilisationManager m : managers) {
            civs.add(m.getCivilisation());
        }
        return civs;
    }
    
}