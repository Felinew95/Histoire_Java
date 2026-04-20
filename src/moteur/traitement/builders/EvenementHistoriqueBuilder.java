package moteur.traitement.builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import log.LoggerUtility;
import moteur.donnees.Evenement;
import moteur.donnees.Region;
import moteur.traitement.management.managers.evenement.EvenementHistoriqueManager;
import moteur.traitement.management.factory.SimFactory;

/**
 * Builder responsable de la création et du filtrage des managers
 * d'événements historiques à partir d'un fichier CSV.
 *
 * <p>
 * Ce builder centralise :
 * <ul>
 *   <li>Le chargement des événements depuis un fichier CSV</li>
 *   <li>La mise en cache des données pour éviter les relectures disque</li>
 *   <li>La création de managers filtrés par régions historiques</li>
 * </ul>
 * </p>
 *
 * <p>
 * Les événements sont regroupés par civilisation (Vikings, Anglo-Saxons,
 * Francs, Irlandais) selon la région associée.
 * </p>
 * 
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 2.0
 */
public class EvenementHistoriqueBuilder {

	/**
     * Logger HTML pour le suivi du chargement des managers.
     */
    private static final Logger logger =
            LoggerUtility.getLogger(EvenementHistoriqueBuilder.class, "html");

    /** 
     * Chemin du fichier CSV contenant les événements historiques 
     */
    private static final String FICHIER_CSV =
            "src/csv/civilisation/evenements.csv";

    /** 
     * Régions associées aux Anglo-Saxons 
     */
    private static final List<String> REGIONS_ANGLO_SAXONS =
            Arrays.asList("Angleterre", "Lindisfarne");

    /** 
     * Régions associées aux Francs 
     */
    private static final List<String> REGIONS_FRANCS =
            Arrays.asList("EuropeDuNord", "Normandie");

    /** 
     * Régions associées aux Irlandais 
     */
    private static final List<String> REGIONS_IRLANDAIS =
            Arrays.asList("Irlande");

    /**
     * Cache mémoire des événements chargés depuis le CSV.
     * Permet d'éviter une relecture du fichier à chaque appel.
     */
    private static List<Evenement> cacheEvenements;

    /**
     * Charge tous les événements historiques depuis le fichier CSV.
     *
     * <p>
     * Cette méthode utilise un cache mémoire afin d'éviter de relire
     * le fichier disque plusieurs fois (optimisation performance).
     * </p>
     *
     * @return liste complète des événements historiques
     */
    private static List<Evenement> chargerTousLesEvenements() {

        if (cacheEvenements != null) {
            return cacheEvenements;
        }

        List<Evenement> evenements = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FICHIER_CSV))) {

            // Ignorer l'entête CSV
            br.readLine();

            String ligne;
            while ((ligne = br.readLine()) != null) {

                String[] v = ligne.split(";");
                if (v.length < 7) continue;

                Region region = SimFactory.buildRegion(
                        v[0].trim(),
                        v[1].trim()
                );

                Evenement ev = SimFactory.buildEvenement(
                        v[2].trim(),
                        Integer.parseInt(v[5].trim()),
                        Integer.parseInt(v[6].trim()),
                        region,
                        v[4].trim(),
                        v[3].trim()
                );

                evenements.add(ev);
            }

        } catch (IOException e) {
            logger.error("Erreur lors du chargement du fichier CSV : " + e.getMessage(), e);
        }

        cacheEvenements = evenements;

        logger.info("Nombre total d'événements chargés : " + evenements.size());
        return evenements;
    }

    /**
     * Construit une instance de gestionnaire d'événements affinée par zone géographique.
     * 
     * <p>
     * Cette méthode filtre la base de données globale des événements historiques pour 
     * ne conserver que ceux dont la localisation correspond à la liste fournie. 
     * Cela permet d'isoler les chronologies propres à chaque civilisation.
     * </p>
     *
     * @param regionsAutorisees liste des régions à inclure
     * @return manager contenant uniquement les événements filtrés
     */
    private static EvenementHistoriqueManager buildManagerFiltre(List<String> regionsAutorisees) {
        EvenementHistoriqueManager manager = new EvenementHistoriqueManager();

        List<Evenement> all = chargerTousLesEvenements();
        for (Evenement ev : all) {
            String regionName = ev.getRegion().getNom();
            if (regionsAutorisees.contains(regionName)) {
                manager.ajouterEvenementHistorique(ev);
            }
        }

        logger.info("Manager créé avec "
                + manager.getNbEvenementsHistoriques()
                + " événements pour les régions : "
                + regionsAutorisees);

        return manager;
    }

    /**
     * Construit un manager contenant tous les événements Vikings (sans filtre).
     *
     * @return manager complet des événements historiques
     */
    public static EvenementHistoriqueManager buildEvenementManagerViking() {
        logger.info("Chargement des événements Vikings (sans filtre)");
        
        EvenementHistoriqueManager manager = new EvenementHistoriqueManager();
        for (Evenement ev : chargerTousLesEvenements()) {
            manager.ajouterEvenementHistorique(ev);
        }
        
        return manager;
    }

    /**
     * Initialise un gestionnaire d'événements restreint au contexte géographique anglo-saxon.
     * 
     * <p>
     * Ce manager ne chargera que les événements historiques liés aux régions définies 
     * par {@code REGIONS_ANGLO_SAXONS} (ex: Wessex, Mercie, Lindisfarne).
     * </p>
     * 
     * @return Un {@link EvenementHistoriqueManager} configuré pour le théâtre d'opération britannique.
     */
    public static EvenementHistoriqueManager buildEvenementManagerAngloSaxons() {
        logger.info("Chargement des événements Anglo-Saxons");
        return buildManagerFiltre(REGIONS_ANGLO_SAXONS);
    }

    /**
     * Initialise un gestionnaire d'événements restreint au contexte géographique des Francs.
     * 
     * <p>
     * Filtre la base de données globale pour n'extraire que les événements survenant 
     * dans les territoires de la Francie Occidentale et de la Normandie.
     * </p>
     * 
     * @return Un {@link EvenementHistoriqueManager} configuré pour le théâtre d'opération continental.
     */
    public static EvenementHistoriqueManager buildEvenementManagerFrancs() {
        logger.info("Chargement des événements Francs");
        return buildManagerFiltre(REGIONS_FRANCS);
    }

    /**
     * Initialise un gestionnaire d'événements restreint au contexte géographique de l'Irlande.
     * 
     * <p>
     * Se concentre exclusivement sur les événements gaëliques et les raids côtiers 
     * spécifiques aux régions définies par {@code REGIONS_IRLANDAIS}.
     * </p>
     * 
     * @return Un {@link EvenementHistoriqueManager} configuré pour le théâtre d'opération irlandais.
     */
    public static EvenementHistoriqueManager buildEvenementManagerIrlandais() {
        logger.info("Chargement des événements Irlandais");
        return buildManagerFiltre(REGIONS_IRLANDAIS);
    }
    
}