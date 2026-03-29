package moteur.traitement.management.managers;

import moteur.donnees.Evenement;
import moteur.donnees.Population;

import java.util.Random;

/**
 * Classe qui gère la population de la civilisation
 *
 * @author Alexandre
 * @version 1.0
 */
public class PopulationManager {

    // Attributs
    private static final Random RANDOM = new Random();

    private static float resteNaissance = 0f;
    private static float resteDeces = 0f;

    /**
     * Constructeur de la classe PopulationManager
     */
    private PopulationManager() {

    }

    /**
     * Méthode qui permet de mettre à jour l'état de la population
     *
     * @param population : La population de la civilisation
     * @param evenement : Evenement en cours
     */
    public static void updatePopulation(Population population, Evenement evenement) {
        float tauxNaissance = population.getTauxNaissance();
        float tauxDeces = population.getTauxDeces();

        // Met à jour les taux
        updateTaux(population, evenement, tauxNaissance, tauxDeces);

        // Reprendre les taux après update
        tauxNaissance = population.getTauxNaissance();
        tauxDeces = population.getTauxDeces();
        int nbHabitants = population.getNbHabitants();

        // Ajouter une volatilité aléatoire pour réalisme (famine, épidémie implicite)
        float tauxDecesAjuste = tauxDeces;
        if (RANDOM.nextFloat() < 0.03f) {
            tauxDecesAjuste += RANDOM.nextFloat() * 0.003f;
        }

        // Calcul des naissances et décès
        float naissancesFloat = nbHabitants * tauxNaissance + resteNaissance;
        float decesFloat = nbHabitants * tauxDecesAjuste + resteDeces;

        int naissances = (int) naissancesFloat;
        int deces = (int) decesFloat;

        resteNaissance = naissancesFloat - naissances;
        resteDeces = decesFloat - deces;

        int nvNbHabitants = nbHabitants + naissances - deces;
        population.setNbHabitants(Math.max(0, nvNbHabitants));

        updateAgeMoyen(population, nvNbHabitants, nbHabitants);
        updateSexeMajoritaire(population, evenement);
    }

    /**
     * Méthode qui permet de mettre à jour le sexe majoritaire de la population
     *
     * @param population : La population de la civilisation
     * @param evenement : Evenement en cours
     */
    private static void updateSexeMajoritaire(Population population, Evenement evenement) {
        if (evenement != null) {
            String type = evenement.getType();
            String sexeMajoritaire = population.getSexeMajoritaire();

            if (type.equals("Guerre") || type.equals("Raid")) {
                sexeMajoritaire = (RANDOM.nextBoolean() ? "HOMME" : "FEMME");
            } else {
                sexeMajoritaire = "HOMME";
            }

            population.setSexeMajoritaire(sexeMajoritaire);
        }
    }

    /**
     * Méthode qui permet de mettre à jour l'age moyen de la population
     *
     * @param population : La population de la civilisation
     * @param nvNbHabitants : Le nouveau nombre d'habitants
     * @param nbHabitants : L'ancien nombre d'habitants
     */
    private static void updateAgeMoyen(Population population, int nvNbHabitants, int nbHabitants) {
        float ageMoyen = population.getAgeMoyen();
        float delta = RANDOM.nextFloat() * 1.2f;

        if (nvNbHabitants < nbHabitants) {
            population.setAgeMoyen(Math.max(0, ageMoyen - delta));
        } else {
            population.setAgeMoyen(Math.max(0, ageMoyen + delta));
        }
    }

    /**
     * Méthode qui permet de mettre à jour les taux d'évolution de la population
     *
     * @param population : La population de la civilisation
     * @param evenement : Evenement en cours
     * @param tauxNaissance : Taux de naissance
     * @param tauxDeces : Taux de décès
     */
    private static void updateTaux(Population population, Evenement evenement, float tauxNaissance, float tauxDeces) {
        float deltaNaissance = 0f;
        float deltaDeces = 0f;

        if (evenement != null) {
            String type = evenement.getType();

            switch (type) {
                case "Guerre":
                case "Raid":
                    // Événements négatifs : impact fort sur natalité, décès significatifs
                    deltaNaissance = -0.006f - RANDOM.nextFloat() * 0.004f;
                    deltaDeces = 0.004f + RANDOM.nextFloat() * 0.004f;
                    break;
                case "Commerce":
                case "Diplomatie":
                case "Exploration":
                case "Colonisation":
                    // Événements positifs : boom des naissances, baisse légère des décès
                    deltaNaissance = 0.005f + RANDOM.nextFloat() * 0.005f;
                    deltaDeces = -0.0015f - RANDOM.nextFloat() * 0.0005f;
                    break;
                case "Politique":
                    // Événement unique : stabilise les taux (équilibre démographique)
                    deltaNaissance = -tauxNaissance * 0.15f;
                    deltaDeces = tauxDeces * 0.25f;
                    break;
                default:
                    deltaNaissance = 0f;
                    deltaDeces = 0f;
                    break;
            }
        } else {
            // Temps normal : croissance démographique stable
            deltaNaissance = 0.004f + RANDOM.nextFloat() * 0.003f;
            deltaDeces = 0.0005f + RANDOM.nextFloat() * 0.0005f;
        }

        tauxNaissance += deltaNaissance;
        tauxDeces += deltaDeces;

        // Bornes réalistes et cohérentes
        tauxNaissance = Math.max(0.002f, Math.min(0.025f, tauxNaissance));
        tauxDeces = Math.max(0.0005f, Math.min(0.008f, tauxDeces));

        population.setTauxNaissance(tauxNaissance);
        population.setTauxDeces(tauxDeces);
    }


}
