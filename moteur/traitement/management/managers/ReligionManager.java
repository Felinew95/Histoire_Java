package moteur.traitement.management.managers;


import moteur.donnees.Evenement;
import moteur.donnees.Religion;

import java.util.Random;

/**
 * Classe qui gère la religion de la civilisation
 *
 * @author Lomani
 * @version 1.0
 */
public class ReligionManager {

    private static final Random RANDOM = new Random();

   
    private static final float INFLUENCE_MIN = 5f;


    private static final float INFLUENCE_MAX = 100f;

  
    private ReligionManager() {
    }



    /**
     * Met à jour la religion de la civilisation en fonction de l'événement en cours.
     *
     * @param religion  : Religion actuelle de la civilisation
     * @param evenement : Événement en cours (peut être null)
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
     * Calcule la variation d'influence selon l'événement en cours.
     *
     * @param influenceActuelle : Influence courante de la religion
     * @param evenement         : Événement en cours
     * @return La variation à appliquer à l'influence
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
               
                // si croyance nordique → perte d'influence massive
                // si croyance chrétienne → gain d'influence
                if (estNordique(evenement)) {
                    delta = -3.0f - RANDOM.nextFloat() * 2.0f;
                } else {
                    delta = 3.0f + RANDOM.nextFloat() * 2.0f;
                }
                break;

            case "Politique":
                
                // instabilité politique → l'Église renforce son autorité
                delta = -1.0f - RANDOM.nextFloat() * 1.0f;
                break;

            case "Diplomatie":
        
                // syncrétisme progressif, croyances s'entremêlent
                delta = -0.8f + RANDOM.nextFloat() * 0.6f;
                break;

            default:
                delta = 0f;
                break;
        }


        float facteurAmortissement = 1 - (influenceActuelle / (INFLUENCE_MAX * 1.5f));
        facteurAmortissement = Math.max(0.2f, facteurAmortissement);

        return delta * facteurAmortissement;
    }

    /**
     * Met à jour la croyance dominante de la civilisation si un événement
     * de conversion est en cours.
     * @param religion  : Religion de la civilisation
     * @param evenement : Événement en cours
     */
    private static void mettreAJourCroyance(Religion religion, Evenement evenement) {
        if (evenement == null) {
            return;
        }


        if (evenement.getType().equals("Religion")) {
            if (religion.getInfluence() <= 20f && estNordique(religion)) {

                religion.setCroyance("Christianisme");
                religion.setNom("Église catholique romaine");
            }
        }

        if (evenement.getType().equals("Diplomatie") || evenement.getType().equals("Politique")) {
        
            if (RANDOM.nextFloat() < 0.05f && estNordique(religion)) {
                religion.setCroyance("Christianisme syncrétique");
            }
        }
    }

    /**
     * Vérifie si la religion actuelle est de tradition nordique/paganiste.
     *
     * @param religion : Religion à vérifier
     * @return true si la croyance est nordique
     */
    private static boolean estNordique(Religion religion) {
        String croyance = religion.getCroyance().toLowerCase();
        return croyance.contains("nordique") || croyance.contains("paganisme")
                || croyance.contains("odin") || croyance.contains("norse")
                || croyance.contains("viking");
    }

    /**
     * Détermine si l'événement correspond à une période pré-conversion (donc
     * la religion nordique est encore dominante).
     *
     * @param evenement : Événement en cours
     * @return true si l'événement se situe avant la grande christianisation
     */
    private static boolean estNordique(Evenement evenement) {
     
        return evenement.getAnneeDebut() < 960;
    }

    /**
     * Retourne l'état de l'influence de la religion sous forme lisible.
     *
     * @param religion : Religion à évaluer
     * @return Description de l'état d'influence
     */
    public static String getEtatInfluence(Religion religion) {
        float influence = religion.getInfluence();
        if (influence >= 80f) {
            return "Dominante";
        } else if (influence >= 55f) {
            return "Forte";
        } else if (influence >= 30f) {
            return "Modérée";
        } else if (influence >= 10f) {
            return "Faible";
        } else {
            return "Marginale";
        }
    }

}