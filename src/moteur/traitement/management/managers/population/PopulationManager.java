package moteur.traitement.management.managers.population;

import log.LoggerUtility;
import moteur.donnees.Evenement;
import moteur.donnees.Population;
import org.apache.log4j.Logger;

import java.util.Random;

import static utilitaire.SimulationUtility.clamp;

/**
 * Classe utilitaire qui gère l'évolution démographique d'une civilisation.
 *
 * <p>
 * Elle permet de mettre à jour le nombre d'habitants, l'âge moyen, le sexe majoritaire,
 * ainsi que les taux de naissance et de décès en fonction des événements et d'une volatilité aléatoire.
 * <p>
 *
 * Cette classe est statique et ne peut pas être instanciée.
 *
 * @author Alexandre
 * @version 2.0
 *
 * @see Population
 * @see Evenement
 */
public class PopulationManager {

    /**
     * Générateur de nombres aléatoires pour introduire de la variabilité réaliste
     */
    private static final Random RANDOM = new Random();

    /**
     * Stocke les fractions de naissance non intégralement appliquées
     */
    private static float resteNaissance = 0f;

    /**
     * Stocke les fractions de décès non intégralement appliquées
     */
    private static float resteDeces = 0f;
    
    /**
     * Taux de naissance de base utilisé pour le calcul de la croissance démographique.
     * Valeur par défaut : {@code 0.0015} (soit 0,15% par unité de temps).
     */
    private static final float BASE_NAISSANCE = 0.0015f;

    /**
     * Taux de décès de base utilisé pour le calcul de la mortalité naturelle.
     * Valeur par défaut : {@code 0.0010} (soit 0,10% par unité de temps).
     */
    private static final float BASE_DECES = 0.0010f;

    /**
     * Logger pour consigner les événements déclenchés
     */
    private static final Logger logger = LoggerUtility.getLogger(PopulationManager.class, "html");

    /**
     * Constructeur privé pour empêcher l'instanciation de cette classe utilitaire.
     */
    private PopulationManager() { }

    /**
     * Met à jour l'état complet de la population pour un cycle de simulation.
     *
     * <p>
     * Cette méthode calcule les naissances et décès, ajuste l'âge moyen et le sexe majoritaire,
     * et prend en compte les effets des événements en cours.
     * </p>
     *
     * @param population La population de la civilisation à mettre à jour
     * @param evenement L'événement en cours affectant la population (peut être null).
     */
    public static void updatePopulation(Population population, Evenement evenement) {
        float tauxNaissance = population.getTauxNaissance();
        float tauxDeces = population.getTauxDeces();

        // Ajuste les taux en fonction de l'événement
        updateTaux(population, evenement, tauxNaissance, tauxDeces);

        tauxNaissance = population.getTauxNaissance();
        tauxDeces = population.getTauxDeces();
        int nbHabitants = population.getNbHabitants();

        // Ajout d'une volatilité aléatoire pour simuler les effets de catastrophes imprévues
        float tauxDecesAjuste = tauxDeces;
        if (RANDOM.nextFloat() < 0.03f) {
            tauxDecesAjuste += RANDOM.nextFloat() * 0.003f;
            logger.info("Volatilité décès appliquée : " + (tauxDecesAjuste - tauxDeces));
        }

        // Calcul des naissances et décès en flottants puis conversion en int
        float naissancesFloat = nbHabitants * tauxNaissance + resteNaissance;
        float decesFloat = nbHabitants * tauxDecesAjuste + resteDeces;

        int naissances = (int) naissancesFloat;
        int deces = (int) decesFloat;

        // Conserver les fractions pour le cycle suivant
        resteNaissance = naissancesFloat - naissances;
        resteDeces = decesFloat - deces;

        // Mise à jour du nombre total d'habitants
        int nvNbHabitants = nbHabitants + naissances - deces;
        population.setNbHabitants(Math.max(0, nvNbHabitants));

        logger.info("Population mise à jour : Ancienne = " + nbHabitants + ", Naissances = " + naissances +
                ", Décès = " + deces + ", Nouvelle = " + nvNbHabitants);

        // Mise à jour des attributs démographiques
        updateAgeMoyen(population, nvNbHabitants, nbHabitants);
        updateSexeMajoritaire(population, evenement);
    }

    /**
     * Met à jour le sexe majoritaire de la population.
     *
     * <p>
     * Pour les événements violents comme la guerre ou le raid, le sexe majoritaire est aléatoire.
     * Sinon, il est fixé à "HOMME" par défaut.
     * </p>
     *
     * @param population La population à mettre à jour
     * @param evenement L'événement en cours (peut être null).
     */
    private static void updateSexeMajoritaire(Population population, Evenement evenement) {
        if (evenement != null) {
            String type = evenement.getType();
            String sexeMajoritaire;

            if (type.equals("Guerre") || type.equals("Raid")) {
                sexeMajoritaire = (RANDOM.nextBoolean() ? "HOMME" : "FEMME");
            } else {
                sexeMajoritaire = "HOMME";
            }

            population.setSexeMajoritaire(sexeMajoritaire);
            logger.info("Sexe majoritaire mis à jour : " + sexeMajoritaire + " pour l'événement " + evenement.getType());
        }
    }

    /**
     * Met à jour l'âge moyen de la population.
     *
     * <p>
     * L'âge moyen augmente si la population diminue (survivants plus âgés)
     * et diminue si la population augmente (nouvelles naissances).
     * La valeur reste bornée entre 15 et 60 ans pour plus de réalisme.
     * </p>
     *
     * @param population La population à mettre à jour
     * @param nvNbHabitants Le nouveau nombre d'habitants après calcul
     * @param nbHabitants L'ancien nombre d'habitants
     */
    private static void updateAgeMoyen(Population population, int nvNbHabitants, int nbHabitants) {
        float ageMoyen = population.getAgeMoyen();
        float delta = RANDOM.nextFloat() * 0.2f;

        float ageMoyenNouveau = (nvNbHabitants < nbHabitants)
                ? clamp(ageMoyen - delta, 15, 60)
                : clamp(ageMoyen + delta, 15, 60);

        population.setAgeMoyen(ageMoyenNouveau);
        logger.info("Âge moyen mis à jour : Ancien = " + ageMoyen + ", Nouveau = " + ageMoyenNouveau);
    }

    /**
     * Met à jour les taux de naissance et de décès de la population.
     *
     * <p>
     * Les ajustements dépendent de l'événement en cours et introduisent
     * une variabilité aléatoire pour simuler des fluctuations réalistes.
     * Les valeurs sont ensuite limitées à des bornes cohérentes.
     * </p>
     *
     * @param population La population à mettre à jour
     * @param evenement L'événement influençant les taux (peut être null).
     * @param tauxNaissance Le taux de naissance avant ajustement
     * @param tauxDeces Le taux de décès avant ajustement
     */
    private static void updateTaux(Population population, Evenement evenement, float tauxNaissance, float tauxDeces) {
        float deltaNaissance;
        float deltaDeces;

        if (evenement != null) {
            String type = evenement.getType();

            switch (type) {
                case "Guerre":
                case "Raid":
                case "Politique":
                    deltaNaissance = -0.007f - RANDOM.nextFloat() * 0.004f;
                    deltaDeces = 0.004f + RANDOM.nextFloat() * 0.004f;
                    break;
                case "Commerce":
                case "Diplomatie":
                case "Exploration":
                case "Colonisation":
                    deltaNaissance = 0.005f + RANDOM.nextFloat() * 0.005f;
                    deltaDeces = -0.0015f - RANDOM.nextFloat() * 0.0005f;
                    break;
                default:
                    deltaNaissance = 0f;
                    deltaDeces = 0f;
                    break;
            }
        } else {
            deltaNaissance = 0.004f + RANDOM.nextFloat() * 0.003f;
            deltaDeces = 0.0005f + RANDOM.nextFloat() * 0.0005f;
        }

        tauxNaissance = BASE_NAISSANCE + deltaNaissance;
        tauxDeces = BASE_DECES + deltaDeces;

        // Bornes réalistes
        tauxNaissance = clamp(tauxNaissance, 0.002f, 0.025f);
        tauxDeces = clamp(tauxDeces, 0.0005f, 0.008f);

        population.setTauxNaissance(tauxNaissance);
        population.setTauxDeces(tauxDeces);

        logger.info("Taux mis à jour : Naissance = " + tauxNaissance + ", Décès = " + tauxDeces +
                (evenement != null ? ", Événement = " + evenement.getType() : ", Aucun événement"));
    }

}