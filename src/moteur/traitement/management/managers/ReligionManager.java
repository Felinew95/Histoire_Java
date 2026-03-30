package moteur.traitement.management.managers;

import moteur.donnees.Evenement;
import moteur.donnees.Religion;

import java.util.Random;

/**
 * Classe utilitaire statique qui gère la religion d'une civilisation.
 *
 * <p>
 * Cette classe permet de mettre à jour l'influence et la croyance dominante d'une religion
 * en fonction d'événements historiques ou fictifs.
 * </p>
 *
 * <p>
 * L'influence est comprise entre 5 et 100 et peut varier selon le type d'événement.
 * La croyance dominante peut changer en cas de conversion ou de syncrétisme.
 * </p>
 *
 * <p>
 * Cette classe est conçue comme utilitaire et ne peut pas être instanciée.
 * </p>
 *
 * @author Massinissa
 * @version 1.0
 *
 * @see Religion
 * @see Evenement
 */
public class ReligionManager {

    /**
     * Générateur aléatoire pour les variations d'influence
     */
    private static final Random RANDOM = new Random();

    /**
     * Influence minimale possible
     */
    private static final float INFLUENCE_MIN = 5f;

    /**
     * Influence maximale possible
     */
    private static final float INFLUENCE_MAX = 100f;

    /**
     * Constructeur privé pour empêcher l'instanciation
     */
    private ReligionManager() {
    }

    /**
     * Met à jour la religion d'une civilisation en fonction de l'événement en cours.
     *
     *  <p>
     * Cette méthode ajuste l'influence et peut modifier la croyance dominante si un événement
     * de conversion est en cours.
     * </p>
     *
     * @param religion  Religion actuelle de la civilisation.
     * @param evenement Événement influençant la religion (peut être {@code null}).
     */
    public static void updateReligion(Religion religion, Evenement evenement) {
        float ancienneInfluence = religion.getInfluence();
        float delta = calculerDeltaInfluence(ancienneInfluence, evenement);

        float nouvelleInfluence = ancienneInfluence + delta;
        nouvelleInfluence = Math.max(INFLUENCE_MIN, Math.min(INFLUENCE_MAX, nouvelleInfluence));
        religion.setInfluence(nouvelleInfluence);

        mettreAJourCroyance(religion, evenement);
    }

    /**
     * Calcule la variation d'influence religieuse selon l'événement courant.
     *
     * <p>
     * L'amortissement limite l'effet des variations lorsque l'influence est déjà élevée.
     * </p>
     *
     * @param influenceActuelle Influence actuelle de la religion.
     * @param evenement         Événement influençant l'influence (peut être {@code null}).
     * @return Variation à appliquer à l'influence.
     */
    private static float calculerDeltaInfluence(float influenceActuelle, Evenement evenement) {
        float delta = -0.3f - RANDOM.nextFloat() * 0.2f;

        if (evenement == null) {
            return (RANDOM.nextFloat() < 0.5f ? 0.2f : -0.2f);
        }

        switch (evenement.getType()) {
            case "Guerre":
            case "Raid":
                delta = 1.5f + RANDOM.nextFloat() * 1.0f;
                break;
            case "Commerce":
                delta = -0.5f + RANDOM.nextFloat() * 0.4f;
                break;
            case "Exploration":
                delta = 0.3f + RANDOM.nextFloat() * 0.5f;
                break;
            case "Colonisation":
                delta = 0.5f + RANDOM.nextFloat() * 0.5f;
                break;
            case "Religion":
                delta = estNordique(evenement)
                        ? -3.0f - RANDOM.nextFloat() * 2.0f
                        : 3.0f + RANDOM.nextFloat() * 2.0f;
                break;
            case "Politique":
                delta = -1.0f - RANDOM.nextFloat() * 1.0f;
                break;
            case "Diplomatie":
                delta = -0.8f + RANDOM.nextFloat() * 0.6f;
                break;
            default:
                delta = 0f;
        }

        float facteurAmortissement = 1 - (influenceActuelle / (INFLUENCE_MAX * 1.5f));
        facteurAmortissement = Math.max(0.2f, facteurAmortissement);

        return delta * facteurAmortissement;
    }

    /**
     * Met à jour la croyance dominante si un événement de conversion est en cours.
     *
     * <p>
     * Peut transformer une croyance nordique en christianisme ou en syncrétisme selon
     * le contexte historique de l'événement.
     * </p>
     *
     * @param religion  Religion de la civilisation.
     * @param evenement Événement influençant la croyance (peut être {@code null}).
     */
    private static void mettreAJourCroyance(Religion religion, Evenement evenement) {
        if (evenement == null) {
            return;
        }

        if ("Religion".equals(evenement.getType())) {
            if (religion.getInfluence() <= 20f && estNordique(religion)) {
                religion.setCroyance("Christianisme");
                religion.setNom("Église catholique romaine");
            }
        }

        if ("Diplomatie".equals(evenement.getType()) || "Politique".equals(evenement.getType())) {
            if (RANDOM.nextFloat() < 0.05f && estNordique(religion)) {
                religion.setCroyance("Christianisme syncrétique");
            }
        }
    }

    /**
     * Vérifie si la religion est de tradition nordique/paganiste.
     *
     * @param religion Religion à vérifier.
     * @return {@code true} si la croyance est nordique ou viking.
     */
    private static boolean estNordique(Religion religion) {
        String croyance = religion.getCroyance().toLowerCase();
        return croyance.contains("nordique") || croyance.contains("paganisme")
                || croyance.contains("odin") || croyance.contains("norse")
                || croyance.contains("viking");
    }

    /**
     * Détermine si l'événement correspond à une période pré-conversion
     * où la religion nordique est encore dominante.
     *
     * @param evenement Événement à évaluer.
     * @return {@code true} si l'événement est avant la christianisation (année < 960).
     */
    private static boolean estNordique(Evenement evenement) {
        return evenement.getAnneeDebut() < 960;
    }

    /**
     * Retourne une description lisible de l'état d'influence de la religion.
     *
     * @param religion Religion à évaluer.
     * @return Chaîne décrivant si l'influence est Dominante, Forte, Modérée, Faible ou Marginale.
     */
    public static String getEtatInfluence(Religion religion) {
        float influence = religion.getInfluence();
        if (influence >= 80f) return "Dominante";
        if (influence >= 55f) return "Forte";
        if (influence >= 30f) return "Modérée";
        if (influence >= 10f) return "Faible";
        return "Marginale";
    }

}