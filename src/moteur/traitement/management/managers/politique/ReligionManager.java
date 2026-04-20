package moteur.traitement.management.managers.politique;

import log.LoggerUtility;
import moteur.donnees.Evenement;
import moteur.donnees.Religion;
import org.apache.log4j.Logger;

import java.util.Random;

import static utilitaire.SimulationUtility.clamp;

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
 * @version 2.0
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
     * Logger pour consigner les événements déclenchés
     */
    private static final Logger logger = LoggerUtility.getLogger(PolitiqueManager.class, "html");

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
        nouvelleInfluence = clamp(nouvelleInfluence, INFLUENCE_MIN, INFLUENCE_MAX);
        religion.setInfluence(nouvelleInfluence);

        String ancienneCroyance = religion.getCroyance();
        mettreAJourCroyance(religion, evenement);
        String nouvelleCroyance = religion.getCroyance();

        logger.info("Religion mise à jour : " + religion.getNom() +
                " | Influence : " + String.format("%.2f", ancienneInfluence) +
                " -> " + String.format("%.2f", nouvelleInfluence) +
                " | Delta = " + String.format("%.2f", delta) +
                (evenement != null ? " | Événement = " + evenement.getNom() : "") +
                " | Croyance : " + ancienneCroyance + " -> " + nouvelleCroyance);
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
        float delta;

        if (evenement == null) {
            return (RANDOM.nextFloat() < 0.5f ? 0.2f : -0.2f);
        }

        switch (evenement.getType()) {
            case "Guerre":
            case "Raid":
                delta = 1.5f + RANDOM.nextFloat();
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
                delta = -1.0f - RANDOM.nextFloat();
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

        String ancienneCroyance = religion.getCroyance();

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

        if (!ancienneCroyance.equals(religion.getCroyance())) {
            logger.info("Croyance modifiée : " + ancienneCroyance + " -> " + religion.getCroyance() +
                    (evenement != null ? " | Événement = " + evenement.getNom() : ""));
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

}