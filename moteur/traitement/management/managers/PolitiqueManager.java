package moteur.traitement.management.managers;

import moteur.donnees.Evenement;
import moteur.donnees.Politique;

import java.util.Random;

/**
 * Classe qui gère la politique de la civilisation
 *
 * @author Massinissa
 * @version 1.0
 */
public class PolitiqueManager {

    
    private static final Random RANDOM = new Random();

    /** Stabilité minimale possible */
    private static final float STABILITE_MIN = 0f;

    /** Stabilité maximale possible */
    private static final float STABILITE_MAX = 100f;

    /**
     * Constructeur privé — classe utilitaire statique
     */
    private PolitiqueManager() {
    }

 
    /**
     * Met à jour la politique de la civilisation en fonction de l'événement en cours.

     *
     * @param politique : Politique actuelle de la civilisation
     * @param evenement : Événement en cours (peut être null)
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
     * Calcule la variation de stabilité politique selon l'événement en cours.
     *
     * @param stabiliteActuelle : Stabilité politique courante
     * @param evenement         : Événement en cours
     * @return La variation à appliquer à la stabilité
     */
    private static float calculerDeltaStabilite(float stabiliteActuelle, Evenement evenement) {
 
        float delta = -0.2f - RANDOM.nextFloat() * 0.1f;

        if (evenement == null) {
        
            return 0.3f + RANDOM.nextFloat() * 0.2f;
        }

        switch (evenement.getType()) {

            case "Guerre":
            
                // guerre longue → tensions internes, ressources épuisées
                delta = -2.0f - RANDOM.nextFloat() * 2.0f;
                break;

            case "Raid":
                
                // victoires rapides → autorité du chef renforcée
                delta = 1.0f + RANDOM.nextFloat() * 1.5f;
                break;

            case "Diplomatie":
                // Traité de Wedmore (878) : négociation → stabilisation
                delta = 2.5f + RANDOM.nextFloat() * 1.5f;
                break;

            case "Commerce":
                // Commerce baltique (900-950) : richesse → soutien populaire
                delta = 1.5f + RANDOM.nextFloat() * 1.0f;
                break;

            case "Exploration":
                // Islande (874), Vinland (1000) : expansion pacifique
                delta = 0.5f + RANDOM.nextFloat() * 0.8f;
                break;

            case "Colonisation":
               
                // administration à structurer → légère tension transitoire
                delta = -0.5f + RANDOM.nextFloat() * 1.5f;
                break;

            case "Religion":
              
                // l'Église légitime le pouvoir royal → stabilité +
                delta = 2.0f + RANDOM.nextFloat() * 1.5f;
                break;

            case "Politique":
                
                // réforme profonde → instabilité de transition
                delta = -3.5f - RANDOM.nextFloat() * 3.0f;
                break;

            default:
                delta = 0f;
                break;
        }

        // Amortissement : un régime très stable résiste mieux aux chocs
        float facteurResistance = stabiliteActuelle / STABILITE_MAX;
        if (delta < 0) {
            // Les chocs négatifs sont atténués par la stabilité existante
            delta = delta * (1 - facteurResistance * 0.4f);
        }

        return delta;
    }

    /**
     * Met à jour les orientations politique (militaire, économique, diplomatique)
     * en fonction du type d'événement en cours.
     * @param politique : Politique de la civilisation
     * @param evenement : Événement en cours
     */
    private static void mettreAJourOrientations(Politique politique, Evenement evenement) {
        if (evenement == null) {
            return;
        }

        switch (evenement.getType()) {

            case "Guerre":
                // Période de guerre totale → tout est subordonné à l'effort militaire
                politique.setPolitiqueMilitaire("Offensive");
                politique.setPolitiqueDiplomatique("Expansionniste");
                break;

            case "Raid":
                // Raids ciblés → posture offensive mais pas de guerre totale
                politique.setPolitiqueMilitaire("Offensive");
                break;

            case "Diplomatie":
                // Traités et alliances → pivot vers la paix
                politique.setPolitiqueMilitaire("Défensive");
                politique.setPolitiqueDiplomatique("Pacifique");
                break;

            case "Commerce":
                // Commerce actif → libéralisation des échanges
                politique.setPolitiqueEconomique("Libre-échange");
                politique.setPolitiqueDiplomatique("Pacifique");
                break;

            case "Colonisation":
                // Nouvelles terres → politique d'expansion structurée
                politique.setPolitiqueDiplomatique("Expansionniste");
                politique.setPolitiqueEconomique("Protectionnisme");
                break;

            case "Religion":
                // Conversion royale → le pouvoir s'appuie sur l'Église
                politique.setPolitiqueDiplomatique("Pacifique");
                politique.setPolitiqueEconomique("Mixte");
                // Le régime se centralise sous l'influence de l'Église
                if (politique.getStabilite() >= 50f) {
                    politique.setType_regime("Monarchie chrétienne");
                }
                break;

            case "Politique":
                // Crise politique majeure (mort de Knut, fragmentation)
                if (politique.getStabilite() < 30f) {
                    politique.setType_regime("Royaumes divisés");
                    politique.setPolitiqueMilitaire("Neutre");
                    politique.setPolitiqueDiplomatique("Équilibrée");
                }
                break;

            default:
                break;
        }
    }

    /**
     * Retourne une description lisible de l'état politique global
     * en combinant stabilité et orientation actuelle.
     *
     * @param politique : Politique à évaluer
     * @return Description de l'état politique
     */
    public static String getEtatPolitiqueGlobal(Politique politique) {
        String etatStabilite = politique.getEtatStabilite();
        return politique.getTypeRegime() + " — " + etatStabilite
                + " | Militaire : " + politique.getPolitiqueMilitaire()
                + " | Éco : " + politique.getPolitiqueEconomique()
                + " | Diplo : " + politique.getPolitiqueDiplomatique();
    }

}