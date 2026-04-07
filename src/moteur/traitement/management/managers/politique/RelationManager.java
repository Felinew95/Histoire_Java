package moteur.traitement.management.managers.politique;

import log.LoggerUtility;
import moteur.donnees.Evenement;
import moteur.donnees.Politique;
import moteur.donnees.Relation;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;

import static utilitaire.SimulationUtility.clamp;

/**
 * Classe utilitaire statique qui gère les relations diplomatiques d'une civilisation viking.
 *
 * <p>
 * Les noms des civilisations sont historiquement corrects (793 — début de l'âge viking) :
 * <ul>
 *   <li>Northumbrie — première cible (Lindisfarne 793)</li>
 *   <li>Irlande — raids dès 795</li>
 *   <li>Royaume des Francs — raids Seine/Loire (830-850), Normandie (911)</li>
 *   <li>Empire Byzantin — commerce via Dniepr (900-950)</li>
 *   <li>Califat Abbasside — commerce via Volga (900-950)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Chaque événement historique n'impacte QUE les civilisations concernées :
 * Lindisfarne → Northumbrie uniquement, Clontarf → Irlande uniquement, etc.
 * </p>
 *
 * @author Massinissa
 * @version 2.0
 *
 * @see Relation
 * @see Politique
 * @see Evenement
 */
public class RelationManager {

    private static final Random RANDOM = new Random();

    private static final float NIVEAU_MIN = 0f;
    private static final float NIVEAU_MAX = 100f;
    private static final float SEUIL_HOSTILE   = 25f;
    private static final float SEUIL_ALLIANCE  = 75f;

    // Noms des civilisations historiques
    private static final String NORTHUMBRIE     = "Northumbrie";
    private static final String IRLANDE         = "Irlande";
    private static final String FRANCS          = "Royaume des Francs";
    private static final String BYZANCE         = "Empire Byzantin";
    private static final String CALIFAT         = "Califat Abbasside";

    /**
     * Logger pour consigner les événements déclenchés
     */
    private static final Logger logger = LoggerUtility.getLogger(RelationManager.class, "html");

    private RelationManager() {}

    /**
     * Met à jour toutes les relations en fonction de l'événement historique.
     * Chaque événement cible précisément les civilisations concernées.
     */
    public static void updateRelations(ArrayList<Relation> relations, Politique politique, Evenement evenement) {
        if (relations == null || relations.isEmpty()) {
            return;
        }

        for (Relation relation : relations) {
            float niveauAvant = relation.getNiveau();
            float delta = calculerDelta(relation, politique, evenement);
            float nouveau = clamp(relation.getNiveau() + delta, NIVEAU_MIN, NIVEAU_MAX);
            relation.setNiveau(nouveau);

            logger.info("Relation mise à jour : " + relation.getNomCivilisation() +
                    " | Avant = " + String.format("%.2f", niveauAvant) +
                    " | Delta = " + String.format("%.2f", delta) +
                    " | Après = " + String.format("%.2f", nouveau) +
                    (evenement != null ? " | Événement = " + evenement.getNom() : ""));
        }
    }

    /**
     * Calcule le delta pour une relation donnée selon l'événement historique.
     * Les événements n'impactent QUE les civilisations directement concernées.
     */
    private static float calculerDelta(Relation relation, Politique politique, Evenement evenement) {
        // Dérive naturelle douce vers la neutralité
        float niveauActuel = relation.getNiveau();
        float delta = (50f - niveauActuel) * 0.005f + (RANDOM.nextFloat() - 0.5f) * 0.3f;

        if (evenement == null) {
            return delta;
        }

        String nom  = evenement.getNom();
        String civ  = relation.getNomCivilisation();

        switch (nom) {

            // --- VIIIe siècle ---

            case "Raid de Lindisfarne":
                // PDF : Relations royaumes chrétiens -30% → impacte Northumbrie en priorité
                if (NORTHUMBRIE.equals(civ))    delta -= 30f + RANDOM.nextFloat() * 3f;
                else if (IRLANDE.equals(civ))   delta -= 10f + RANDOM.nextFloat() * 2f; // par solidarité chrétienne
                else if (FRANCS.equals(civ))    delta -=  8f + RANDOM.nextFloat() * 2f;
                break;

            case "Premiers raids en Irlande et Écosse":
                // PDF : raids répétés côtes irlandaises et écossaises
                if (IRLANDE.equals(civ))        delta -= 15f + RANDOM.nextFloat() * 3f;
                else if (NORTHUMBRIE.equals(civ)) delta -= 5f + RANDOM.nextFloat() * 2f;
                break;

            // --- IXe siècle ---

            case "Raids fréquents en Europe de l'Ouest":
                // PDF : campagnes sur Seine, Loire, Rhin → Francs surtout
                if (FRANCS.equals(civ))         delta -= 20f + RANDOM.nextFloat() * 3f;
                else if (NORTHUMBRIE.equals(civ)) delta -= 8f + RANDOM.nextFloat() * 2f;
                else if (IRLANDE.equals(civ))   delta -=  5f + RANDOM.nextFloat() * 2f;
                break;

            case "Grande Armée païenne":
                // PDF : invasion totale Angleterre → Northumbrie -40%
                if (NORTHUMBRIE.equals(civ))    delta -= 40f + RANDOM.nextFloat() * 3f;
                else if (IRLANDE.equals(civ))   delta -=  5f + RANDOM.nextFloat() * 2f;
                break;

            case "Victoires d'Alfred le Grand":
                // PDF : début diplomatie +10% → Northumbrie/Angleterre
                if (NORTHUMBRIE.equals(civ))    delta += 10f + RANDOM.nextFloat() * 2f;
                break;

            case "Création du Danelaw":
                // PDF : coexistence +15% → Northumbrie (accord territorial)
                if (NORTHUMBRIE.equals(civ))    delta += 15f + RANDOM.nextFloat() * 2f;
                break;

            case "Colonisation de l'Islande":
                // Colonisation interne, légère tension
                delta -= 2f + RANDOM.nextFloat();
                break;

            // --- Xe siècle ---

            case "Commerce baltique":
                // PDF : Relations Byzance +25%, Califat +25%
                if (BYZANCE.equals(civ))        delta += 25f + RANDOM.nextFloat() * 3f;
                else if (CALIFAT.equals(civ))   delta += 25f + RANDOM.nextFloat() * 3f;
                break;

            case "Fondation de la Normandie":
                // PDF : alliance avec les Francs +30%
                if (FRANCS.equals(civ))         delta += 30f + RANDOM.nextFloat() * 2f;
                break;

            case "Fondation de l'Althing":
                // Politique interne, légère hausse
                delta += 3f + RANDOM.nextFloat();
                break;

            case "Conversion au christianisme":
                // PDF : fin du statut "païens" → toute l'Europe chrétienne +40%
                if (NORTHUMBRIE.equals(civ))    delta += 40f + RANDOM.nextFloat() * 3f;
                else if (IRLANDE.equals(civ))   delta += 35f + RANDOM.nextFloat() * 3f;
                else if (FRANCS.equals(civ))    delta += 40f + RANDOM.nextFloat() * 3f;
                break;

            case "Colonisation du Groenland":
                delta += 1f + RANDOM.nextFloat();
                break;

            // --- XIe siècle ---

            case "Voyages vers le Vinland":
                // Prestige, impact léger sur tous
                delta += 2f + RANDOM.nextFloat();
                break;

            case "Bataille de Clontarf":
                // PDF : déclin influence Irlande -25%
                if (IRLANDE.equals(civ))        delta -= 25f + RANDOM.nextFloat() * 3f;
                break;

            case "Mort de Knut le Grand":
                // PDF : fragmentation totale → toutes les relations se dégradent
                delta -= 15f + RANDOM.nextFloat() * 5f;
                break;

            case "Bataille de Stamford Bridge":
                // PDF : fin de l'âge viking → toutes les relations chutent
                if (NORTHUMBRIE.equals(civ))    delta -= 40f + RANDOM.nextFloat() * 3f;
                else                            delta -= 20f + RANDOM.nextFloat() * 3f;
                break;

            default:
                // Fallback sur le type d'événement
                delta += deltaParType(evenement.getType());
                break;
        }

        // Modulation par orientation diplomatique
        if (politique != null) {
            String diplo = politique.getPolitiqueDiplomatique();
            if ("Pacifique".equals(diplo)) {
                delta = delta > 0 ? delta * 1.3f : delta * 0.7f;
            } else if ("Expansionniste".equals(diplo)) {
                delta = delta > 0 ? delta * 0.7f : delta * 1.3f;
            }
        }

        return delta;
    }

    /**
     * Delta de secours basé sur le type d'événement quand le nom n'est pas reconnu.
     */
    private static float deltaParType(String type) {
        switch (type) {
            case "Guerre":       return -(8f + RANDOM.nextFloat() * 5f);
            case "Raid":         return -(4f + RANDOM.nextFloat() * 3f);
            case "Diplomatie":   return   8f + RANDOM.nextFloat() * 3f;
            case "Commerce":     return   5f + RANDOM.nextFloat() * 3f;
            case "Religion":     return   8f + RANDOM.nextFloat() * 3f;
            case "Politique":    return -(2f + RANDOM.nextFloat() * 2f);
            case "Colonisation": return -(2f + RANDOM.nextFloat() * 1.5f);
            case "Exploration":  return   1f + RANDOM.nextFloat();
            default:             return   0f;
        }
    }

    /**
     * Retourne le statut qualitatif d'une relation.
     * @return "Alliance", "Neutre", "Tendue" ou "Hostile"
     */
    public static String getDescriptionRelation(Relation relation) {
        float n = relation.getNiveau();
        if (n >= SEUIL_ALLIANCE) return "Alliance";
        if (n >= 50f)            return "Neutre";
        if (n >= SEUIL_HOSTILE)  return "Tendue";
        return "Hostile";
    }

    /**
     * Retourne la meilleure relation (niveau le plus élevé).
     */
    public static Relation getMeilleureRelation(ArrayList<Relation> relations) {
        if (relations == null || relations.isEmpty()) return null;
        Relation best = relations.get(0);
        for (Relation r : relations) if (r.getNiveau() > best.getNiveau()) best = r;
        return best;
    }

    /**
     * Retourne la relation la plus hostile (niveau le plus bas).
     */
    public static Relation getPireRelation(ArrayList<Relation> relations) {
        if (relations == null || relations.isEmpty()) return null;
        Relation worst = relations.get(0);
        for (Relation r : relations) if (r.getNiveau() < worst.getNiveau()) worst = r;
        return worst;
    }

}