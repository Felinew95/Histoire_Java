package moteur.traitement.management.managers.armee;

import java.util.Random;

import log.LoggerUtility;
import moteur.donnees.Armee;
import moteur.donnees.Evenement;
import moteur.donnees.Kersir;
import moteur.traitement.management.managers.civilisation.ChefRepository;
import org.apache.log4j.Logger;

import static utilitaire.SimulationUtility.clamp;

/**
 * Gestionnaire de l'armée d'une civilisation.
 *
 * <p>
 * Cette classe est responsable de la mise à jour et de la gestion des forces armées
 * d'une civilisation. Elle applique les effets des événements historiques sur l'armée,
 * calcule les pertes et gains, et coordonne l'évolution des unités militaires au fil du temps.
 * </p>
 *
 * <p>
 * Le gestionnaire peut être utilisé dans le cadre d'une simulation temporelle
 * d'une civilisation pour intégrer les aspects militaires dans le cycle global.
 * </p>
 *
 * @author Tauseef
 * @version 2.0
 *
 * @see Armee
 * @see Evenement
 */
public class ArmeeManager {

    /**
     * Générateur de nombres aléatoires utilisé pour les calculs de combats, pertes,
     * gains et autres événements militaires.
     */
    private static final Random RANDOM = new Random();

    /**
     * Nombre maximal de guerriers qu'une civilisation peut avoir.
     */
    private static final int MAX_GUERRIERS  = 1_000_000;

    /**
     * Nombre maximal de technologies militaires qu'une civilisation peut posséder.
     */
    private static final int MAX_TECHNIQUES = 50;

    /**
     * Nombre de guerriers transportables par navire.
     */
    private static final int GUERRIERS_PAR_NAVIRE = 10;

    /**
     * Référence de calcul pour les guerriers : 10 % de la limite maximale.
     * Utilisé pour normaliser les effets ou calculs proportionnels.
     */
    private static final float REF_GUERRIERS  = MAX_GUERRIERS * 0.1f;

    /**
     * Référence de calcul pour les navires : 10 % des guerriers, ajustée par la capacité de transport.
     */
    private static final float REF_NAVIRES    = (MAX_GUERRIERS * 0.1f) / GUERRIERS_PAR_NAVIRE;

    /**
     * Référence de calcul pour les techniques militaires : 50 % de la limite maximale.
     */
    private static final float REF_TECHNIQUES = MAX_TECHNIQUES * 0.5f;

    /**
     * Instance singleton du référentiel des chefs pour récupérer les dirigeants actuels.
     */
    private static final ChefRepository CHEF_REPOSITORY = ChefRepository.getInstance();

    /**
     * Logger pour suivre le chargement et les erreurs.
     */
    private static final Logger logger =
            LoggerUtility.getLogger(ArmeeManager.class, "html");

    /**
     * Met à jour l'ensemble des attributs de l'armée pour un tour de simulation.
     * @param anneeSim  : L'année de simulation en cours
     * @param armee     : L'armée de la civilisation
     * @param evenement : L'événement en cours (peut être null)
     */
    public static void updateArmee(int anneeSim, Armee armee, Evenement evenement) {
        if (armee == null) {
            return;
        }

        updateGuerriers(armee, evenement);
        updateNavires(armee, evenement);
        updateTechniquesMilitaires(armee, evenement);
        updateEtat(armee);
        updateChefArmee(armee, anneeSim);
    }

    /**
     * Met à jour le chef de l'armée selon l'année de simulation.
     */
    private static void updateChefArmee(Armee armee, int anneeSim) {
        Kersir kersirArmee = CHEF_REPOSITORY.getKersirActuel(anneeSim);
        if (kersirArmee != null) {
            armee.setHersir(kersirArmee);
        }
        logger.debug("Kersir mis à jour : ");
    }

    /**
     * Met à jour le nombre de guerriers selon l'événement en cours.
     *
     * <p>
     * Sans événement : croissance naturelle lente (+1–2%), pertes quasi nulles (0–0.3%).
     * Avec événement : variations plus importantes, mais toujours proportionnelles aux effectifs.
     * Plafond logistique : la croissance ralentit quand on approche de MAX_GUERRIERS.
     * </p>
     *
     * @param armee     : L'armée de la civilisation
     * @param evenement : L'événement en cours (peut être null)
     */
    private static void updateGuerriers(Armee armee, Evenement evenement) {
        int nombreActuel = armee.getNombreGuerriers();

        float facteurSaturation = 1f - ((float) nombreActuel / MAX_GUERRIERS);
        float tauxCroissance    = (0.01f + RANDOM.nextFloat() * 0.01f) * facteurSaturation;
        int   renforts          = (int) (nombreActuel * tauxCroissance);


        int pertes = (int) (nombreActuel * RANDOM.nextFloat() * 0.003f);

        if (evenement != null) {
            switch (evenement.getType()) {
                case "Guerre":
                case "Raid":
                    // Pertes importantes en combat : 5% à 10%
                    pertes   = (int) (nombreActuel * (0.05f + RANDOM.nextFloat() * 0.05f));
                    renforts = (int) (nombreActuel * 0.01f);
                    break;
                case "Politique":
                    // Légères pertes liées aux tensions internes : 1% à 3%
                    pertes   = (int) (nombreActuel * (0.01f + RANDOM.nextFloat() * 0.02f));
                    renforts = (int) (nombreActuel * 0.01f);
                    break;
                case "Colonisation":
                    // Recrutement pour accompagner l'expansion : +2% à +5%
                    renforts = (int) (nombreActuel * (0.02f + RANDOM.nextFloat() * 0.03f));
                    break;
                default:
                    // Événement mineur : évolution de base
                    break;
            }
        }

        int nouveauNombre = Math.max(0, nombreActuel - pertes + renforts);
        nouveauNombre     = Math.min(nouveauNombre, MAX_GUERRIERS);

        armee.setNombreGuerriers(nouveauNombre);
        logger.debug("Guerriers mis à jour : " + nombreActuel + " -> " + nouveauNombre);
    }

    /**
     * Met à jour le nombre de navires selon l'événement en cours.
     *
     * Sans événement : construction et usure proportionnelles à la flotte actuelle.
     * Avec événement : variations proportionnelles également (plus de valeurs fixes).
     * Contrainte : les navires ne peuvent pas dépasser guerriers / GUERRIERS_PAR_NAVIRE.
     * Si l'armée rétrécit, les navires excédentaires sont abandonnés progressivement (20%/tour).
     *
     * @param armee     : L'armée de la civilisation
     * @param evenement : L'événement en cours (peut être null)
     */
    private static void updateNavires(Armee armee, Evenement evenement) {
        int nombreActuel = armee.getNombreNavires();

        // Sans événement : légère construction (+1–2%) et usure naturelle (0.3–0.5%)
        int naviresConstruit = (int) (nombreActuel * (0.01f + RANDOM.nextFloat() * 0.01f));
        int naviresPerdu     = (int) (nombreActuel * (0.003f + RANDOM.nextFloat() * 0.002f));

        if (evenement != null) {
            switch (evenement.getType()) {
                case "Guerre":
                case "Raid":
                    // Pertes en mer : 5% à 10%
                    naviresPerdu     = (int) (nombreActuel * (0.05f + RANDOM.nextFloat() * 0.05f));
                    naviresConstruit = (int) (nombreActuel * 0.01f);
                    break;
                case "Exploration":
                case "Colonisation":
                    // Expansion maritime : construction intensive +5% à +10%
                    naviresConstruit = (int) (nombreActuel * (0.05f + RANDOM.nextFloat() * 0.05f));
                    break;
                case "Commerce":
                    // Investissement commercial : +3% à +7%
                    naviresConstruit = (int) (nombreActuel * (0.03f + RANDOM.nextFloat() * 0.04f));
                    break;
                default:
                    break;
            }
        }

        int nouveauNombre = Math.max(0, nombreActuel - naviresPerdu + naviresConstruit);

        // Contrainte logique : 1 navire nécessite GUERRIERS_PAR_NAVIRE guerriers
        int naviresMax = armee.getNombreGuerriers() / GUERRIERS_PAR_NAVIRE;
        if (nouveauNombre > naviresMax) {
            // Réduction progressive : on abandonne 20% de l'excédent par tour
            int excedent   = nouveauNombre - naviresMax;
            nouveauNombre -= (int) (excedent * 0.2f);
        }

        armee.setNombreNavires(Math.max(0, nouveauNombre));
        logger.debug("Navires mis à jour : " + nombreActuel + " -> " + nouveauNombre);
    }

    /**
     * Met à jour le nombre de techniques militaires selon l'événement en cours.
     * Sans événement : aucun gain (la paix ne favorise pas l'innovation militaire).
     * Plafond à MAX_TECHNIQUES.
     *
     * @param armee     : L'armée de la civilisation
     * @param evenement : L'événement en cours (peut être null)
     */
    private static void updateTechniquesMilitaires(Armee armee, Evenement evenement) {
        int nombreActuel    = armee.getTechniquesMilitaire();
        int techniquesGagne = 0;

        if (RANDOM.nextFloat() < 0.05f) {
            techniquesGagne = 1;
        }

        if (evenement != null) {
            switch (evenement.getType()) {
                case "Commerce":
                    techniquesGagne += 1 + (RANDOM.nextFloat() < 0.40f ? 1 : 0);
                    break;
                case "Diplomatie":
                    techniquesGagne += 1 + RANDOM.nextInt(2);
                    break;
                case "Exploration":
                    techniquesGagne += (RANDOM.nextFloat() < 0.50f) ? 1 : 0;
                    break;
                case "Guerre":
                    techniquesGagne += (RANDOM.nextFloat() < 0.25f ? 1 : 0)
                            + (RANDOM.nextFloat() < 0.10f ? 1 : 0);
                    break;
                case "Raid":
                    techniquesGagne += (RANDOM.nextFloat() < 0.20f) ? 1 : 0;
                    break;
                case "Colonisation":
                    techniquesGagne += (RANDOM.nextFloat() < 0.15f) ? 1 : 0;
                    break;
                case "Religion":
                    techniquesGagne += (RANDOM.nextFloat() < 0.10f) ? 1 : 0;
                    break;
                default:
                    break;
            }
        }

        armee.setTechniquesMilitaire(clamp(nombreActuel + techniquesGagne, 0, MAX_TECHNIQUES));
        logger.debug("Techniques militaires mis à jour : " + nombreActuel + " -> " + clamp(nombreActuel + techniquesGagne, 0, MAX_TECHNIQUES));
    }

    /**
     * Recalcule l'état général de l'armée (entre 0% et 100%).
     *
     * Les références sont relatives au maximum → le score ne sature plus à 100 trop tôt.
     * L'inertie est forte (85%) pour éviter les sauts brutaux d'un tour à l'autre.
     *
     * @param armee : L'armée de la civilisation
     */
    private static void updateEtat(Armee armee) {
        float scoreGuerriers  = clamp((armee.getNombreGuerriers()    / REF_GUERRIERS)  * 100f, 0, 100);
        float scoreNavires    = clamp((armee.getNombreNavires()       / REF_NAVIRES)    * 100f, 0, 100);
        float scoreTechniques = clamp((armee.getTechniquesMilitaire() / REF_TECHNIQUES) * 100f, 0, 100);

        float scoreCalcule = scoreGuerriers  * 0.5f
                + scoreNavires    * 0.2f
                + scoreTechniques * 0.3f;


        float ancienEtat = armee.getEtat();
        float nouvelEtat = clamp(0.85f * ancienEtat + 0.15f * scoreCalcule, 0, 100);

        armee.setEtat(nouvelEtat);
        logger.debug("État de l'armée mis à jour : ");
    }

    /**
     * Retourne une description textuelle de l'état de l'armée.
     *
     * @param armee : L'armée de la civilisation
     * @return La description de l'état
     */
    public static String getDescriptionEtat(Armee armee) {
        float etatActuel = armee.getEtat();
        if (etatActuel >= 75f) {
            return "En pleine forme";
        } else if (etatActuel >= 50f) {
            return "Opérationnelle";
        } else if (etatActuel >= 25f) {
            return "Affaiblie";
        } else {
            return "Démoralisée";
        }
    }

}