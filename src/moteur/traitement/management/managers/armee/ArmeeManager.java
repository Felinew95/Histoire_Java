package moteur.traitement.management.managers.armee;

import java.util.Random;

import moteur.donnees.Armee;
import moteur.donnees.Evenement;
import moteur.donnees.Kersir;
import moteur.traitement.management.managers.civilisation.ChefRepository;

import static utilitaire.SimulationUtility.clamp;

/**
 * Classe qui gère l'armée de la civilisation.
 *
 * @author Tauseef
 * @version 2.0
 *
 * @see Armee
 * @see Evenement
 */
public class ArmeeManager {

    private static final Random RANDOM = new Random();
    private static float REF_GUERRIERS = 500f;
    private static float REF_NAVIRES = 20f;
    private static float REF_TECHNIQUES = 10f;

    private static final ChefRepository CHEF_REPOSITORY = ChefRepository.getInstance();

    /**
     * Met à jour l'ensemble des attributs de l'armée pour un tour de simulation.
     * @param armee: L'armée de la civilisation
     * @param evenement: L'événement en cours
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

    private static void updateChefArmee(Armee armee, int anneeSim) {
        Kersir kersirArmee = CHEF_REPOSITORY.getKersirActuel(anneeSim);
        if (kersirArmee != null) {
            armee.setHersir(kersirArmee);
        }
    }

    /**
     * Met à jour le nombre de guerriers selon l'événement en cours.
     * @param armee: L'armée de la civilisation
     * @param evenement: L'événement en cours
     */
    private static void updateGuerriers(Armee armee, Evenement evenement) {
        int nombreActuel = armee.getNombreGuerriers();

        int croissance = (int) (nombreActuel * 0.02f);

        int pertes= RANDOM.nextInt(2);
        int renforts = croissance + RANDOM.nextInt(2);

        if (evenement != null) {
            switch (evenement.getType()) {
                case "Guerre":
                case "Politique":
                case"Raid":
                    pertes = (int) (nombreActuel * (0.02f + RANDOM.nextFloat() * 0.04f));
                    renforts = croissance;
                    break;
                case "Colonisation":
                    renforts = (int) (nombreActuel * (0.01f + RANDOM.nextFloat() * 0.04f));
                    break;
                default:
                    renforts = (int) (nombreActuel * RANDOM.nextFloat() *0.02f);
                    break;
            }
        }

        int nouveauNombre = Math.max(0, nombreActuel - pertes + renforts);
        armee.setNombreGuerriers(nouveauNombre);
    }


    /**
     * Met à jour le nombre de navires selon l'événement en cours.
     * @param armee : L'armée de la civilisation
     * @param evenement : L'événement en cours (peut être null)
     */
    private static void updateNavires(Armee armee, Evenement evenement) {
        int nombreActuel  = armee.getNombreNavires();

        int naviresConstruit = (int) (nombreActuel * 0.05f); // +5% par tour
        int naviresPerdu = (int) (nombreActuel * 0.01f);     // -1% usure naturelle

        if (evenement != null) {
            switch (evenement.getType()) {
                case "Guerre":
                case "Raid":
                    naviresPerdu = RANDOM.nextInt(10);
                    break;
                case "Exploration":
                case "Colonisation":
                    naviresConstruit = 10 + RANDOM.nextInt(20);
                    break;
                case "Commerce":
                    naviresConstruit = RANDOM.nextInt(30);
                    break;
                default:
                    naviresConstruit = 10 + RANDOM.nextInt(10);
                    naviresPerdu = RANDOM.nextInt(4);
                    break;
            }
        }

        int nouveauNombre = Math.max(0, nombreActuel - naviresPerdu + naviresConstruit);
        armee.setNombreNavires(nouveauNombre);
    }


    /**
     * Met à jour le nombre de techniques militaires selon l'événement en cours.
     * @param armee : L'armée de la civilisation
     * @param evenement : L'événement en cours (peut être null)
     */
    private static void updateTechniquesMilitaires(Armee armee, Evenement evenement) {
        int nombreActuel    = armee.getTechniquesMilitaire();
        int techniquesGagne = 0;

        if (evenement != null) {
            switch (evenement.getType()) {
                case "Commerce":
                    techniquesGagne = 1 + (RANDOM.nextFloat() < 0.40f ? 1 : 0);
                    break;
                case "Diplomatie":
                    techniquesGagne = 1 + RANDOM.nextInt(2);
                    break;
                case "Exploration":
                    techniquesGagne = (RANDOM.nextFloat() < 0.50f) ? 1 : 0;
                    break;
                case "Guerre":
                    techniquesGagne = (RANDOM.nextFloat() < 0.25f ? 1 : 0) + (RANDOM.nextFloat() < 0.10f ? 1 : 0);
                    break;
                case "Raid":
                    techniquesGagne = (RANDOM.nextFloat() < 0.20f) ? 1 : 0;
                    break;
                default:
                    break;
            }
        }

        int nouveauNombre = Math.max(0, nombreActuel + techniquesGagne);
        armee.setTechniquesMilitaire(nouveauNombre);
    }

    /**
     * Recalcule l'état général de l'armée (entre 0 % et 100 %) à partir
     * d'une combinaison pondérée des effectifs, des navires et des techniques.
     * @param armee : L'armée de la civilisation
     */
    private static void updateEtat(Armee armee) {
        float scoreGuerriers  = Math.min(100f, (armee.getNombreGuerriers()    / REF_GUERRIERS)  * 50f);
        float scoreNavires    = Math.min(100f, (armee.getNombreNavires()       / REF_NAVIRES)    * 50f);
        float scoreTechniques = Math.min(100f, (armee.getTechniquesMilitaire() / REF_TECHNIQUES) * 50f);

        float scoreCalcule = scoreGuerriers  * 0.5f + scoreNavires* 0.2f + scoreTechniques * 0.3f;

        float ancienEtat  = armee.getEtat();
        float nouvelEtat  = 0.7f * ancienEtat + 0.3f * scoreCalcule;

        REF_GUERRIERS = armee.getNombreGuerriers();
        REF_NAVIRES = armee.getNombreNavires();
        REF_TECHNIQUES = armee.getTechniquesMilitaire();

        nouvelEtat= clamp(nouvelEtat, 0, 100);
        armee.setEtat(nouvelEtat);
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