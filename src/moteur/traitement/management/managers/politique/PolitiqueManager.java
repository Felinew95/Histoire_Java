package moteur.traitement.management.managers.politique;

import moteur.donnees.Evenement;
import moteur.donnees.Politique;

import java.util.Random;

/**
 * Classe utilitaire statique qui gère la politique d'une civilisation.
 *
 * <p>
 * Cette classe permet de mettre à jour la stabilité et les orientations politiques
 * (militaire, économique, diplomatique) en fonction d'événements historiques ou fictifs.
 * </p>
 *
 * <p>
 * La stabilité est comprise entre 0 et 100, et les événements peuvent l'augmenter ou la diminuer.
 * Les orientations politiques sont automatiquement ajustées selon le type d'événement.
 * </p>
 *
 * <p>
 * Cette classe est conçue comme utilitaire et ne peut pas être instanciée.
 * </p>
 *
 * @author Massinissa
 * @version 1.0
 *
 * @see Politique
 * @see Evenement
 */
public class PolitiqueManager {

    /**
     * Générateur aléatoire pour les variations de stabilité
     */
    private static final Random RANDOM = new Random();

    /**
     * Stabilité minimale possible
     */
    private static final float STABILITE_MIN = 0f;

    /**
     * Stabilité maximale possible
     */
    private static final float STABILITE_MAX = 100f;

    /**
     * Constructeur privé pour empêcher l'instanciation
     */
    private PolitiqueManager() {
    }

    /**
     * Met à jour la politique de la civilisation en fonction de l'événement courant.
     *
     * <p>
     * Cette méthode ajuste la stabilité de la civilisation et met à jour les orientations
     * politiques, militaire, économique et diplomatique.
     * </p>
     *
     * @param politique Politique actuelle de la civilisation.
     * @param evenement Événement influençant la politique (peut être {@code null}).
     */
    public static void updatePolitique(Politique politique, Evenement evenement) {
        float ancienneStabilite = politique.getStabilite();
        float delta = calculerDeltaStabilite(ancienneStabilite, evenement);

        float nouvelleStabilite = ancienneStabilite + delta;
        nouvelleStabilite = Math.max(STABILITE_MIN, Math.min(STABILITE_MAX, nouvelleStabilite));
        politique.setStabilite(nouvelleStabilite);

        mettreAJourOrientations(politique, evenement);
    }

    /**
     * Calcule la variation de stabilité politique en fonction de l'événement.
     *
     * <p>
     * Les chocs négatifs sont atténués si la stabilité actuelle est élevée.
     * </p>
     *
     * @param stabiliteActuelle Stabilité politique actuelle.
     * @param evenement         Événement influençant la stabilité (peut être {@code null}).
     * @return Variation à appliquer à la stabilité.
     */
    private static float calculerDeltaStabilite(float stabiliteActuelle, Evenement evenement) {
        float delta = -0.2f - RANDOM.nextFloat() * 0.1f;

        if (evenement == null) {
            return 0.3f + RANDOM.nextFloat() * 0.2f;
        }

        switch (evenement.getType()) {
            case "Guerre":
                delta = -2.0f - RANDOM.nextFloat() * 2.0f;
                break;
            case "Raid":
                delta = 1.0f + RANDOM.nextFloat() * 1.5f;
                break;
            case "Diplomatie":
                delta = 2.5f + RANDOM.nextFloat() * 1.5f;
                break;
            case "Commerce":
                delta = 1.5f + RANDOM.nextFloat() * 1.0f;
                break;
            case "Exploration":
                delta = 0.5f + RANDOM.nextFloat() * 0.8f;
                break;
            case "Colonisation":
                delta = -0.5f + RANDOM.nextFloat() * 1.5f;
                break;
            case "Religion":
                delta = 2.0f + RANDOM.nextFloat() * 1.5f;
                break;
            case "Politique":
                delta = -3.5f - RANDOM.nextFloat() * 3.0f;
                break;
            default:
                delta = 0f;
        }

        // Amortissement : la stabilité élevée résiste mieux aux chocs négatifs
        float facteurResistance = stabiliteActuelle / STABILITE_MAX;
        if (delta < 0) {
            delta = delta * (1 - facteurResistance * 0.4f);
        }

        return delta;
    }

    /**
     * Met à jour les orientations politiques de la civilisation en fonction de l'événement.
     *
     * <p>
     * Les orientations incluent militaire, diplomatique et économique, ainsi que le type de régime
     * si nécessaire.
     * </p>
     *
     * @param politique Politique de la civilisation.
     * @param evenement Événement influençant les orientations (peut être {@code null}).
     */
    private static void mettreAJourOrientations(Politique politique, Evenement evenement) {
        if (evenement == null) {
            return;
        }

        switch (evenement.getType()) {
            case "Guerre":
                politique.setPolitiqueMilitaire("Offensive");
                politique.setPolitiqueDiplomatique("Expansionniste");
                break;
            case "Raid":
                politique.setPolitiqueMilitaire("Offensive");
                break;
            case "Diplomatie":
                politique.setPolitiqueMilitaire("Défensive");
                politique.setPolitiqueDiplomatique("Pacifique");
                break;
            case "Commerce":
                politique.setPolitiqueEconomique("Libre-échange");
                politique.setPolitiqueDiplomatique("Pacifique");
                break;
            case "Colonisation":
                politique.setPolitiqueDiplomatique("Expansionniste");
                politique.setPolitiqueEconomique("Protectionnisme");
                break;
            case "Religion":
                politique.setPolitiqueDiplomatique("Pacifique");
                politique.setPolitiqueEconomique("Mixte");
                if (politique.getStabilite() >= 50f) {
                    politique.setTypeRegime("Monarchie chrétienne");
                }
                break;
            case "Politique":
                if (politique.getStabilite() < 30f) {
                    politique.setTypeRegime("Royaumes divisés");
                    politique.setPolitiqueMilitaire("Neutre");
                    politique.setPolitiqueDiplomatique("Équilibrée");
                }
                break;
            default:
                break;
        }
    }

    /**
     * Retourne une description lisible de l'état politique global de la civilisation.
     *
     * @param politique Politique à évaluer.
     * @return Chaîne décrivant le type de régime, la stabilité et les orientations actuelles.
     */
    public static String getEtatPolitiqueGlobal(Politique politique) {
        String etatStabilite = politique.getEtatStabilite();
        return politique.getTypeRegime() + " — " + etatStabilite
                + " | Militaire : " + politique.getPolitiqueMilitaire()
                + " | Éco : " + politique.getPolitiqueEconomique()
                + " | Diplo : " + politique.getPolitiqueDiplomatique();
    }
}