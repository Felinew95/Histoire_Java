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
 * @author Tauseef
 * 
 * @version 2.0
 *
 * @see Relation
 * @see Politique
 * @see Evenement
 */
public class RelationManager {

	/**
	 * Générateur de nombres aléatoires pour simuler l'incertitude diplomatique 
     * et les variations imprévisibles des relations humaines.
     */
    private static final Random RANDOM = new Random();

    /** 
     * Valeur plancher d'une relation (0.0). Une relation ne peut pas être plus dégradée. 
     */
    private static final float NIVEAU_MIN = 1f;
    
    /** 
     * Valeur plafond d'une relation (100.0). Représente une intégration totale entre peuples. 
     */
    private static final float NIVEAU_MAX = 100f;
    
    /** 
     * Seuil critique d'hostilité. En dessous de cette valeur, les échanges cessent 
     * et les risques de conflit armé augmentent drastiquement.
     */
    private static final float SEUIL_HOSTILE = 25f;
    
    /**
     * Seuil de fraternité. Au-dessus de cette valeur, les civilisations sont considérées 
     * comme alliées (partage de ressources, soutien militaire).
     */
    private static final float SEUIL_ALLIANCE = 75f;
    
    /** 
     * Royaume anglo-saxon du Nord-Est de l'Angleterre, cible du raid de 793. 
     */
    private static final String NORTHUMBRIE = "Northumbrie";
    
    /** 
     * Territoires gaéliques, cibles de raids puis de fondations de cités (comme Dublin). 
     */
    private static final String IRLANDE     = "Irlande";
    
    /** 
     * Empire Carolingien puis Francie Occidentale, cible des raids sur Paris et la Seine. 
     */
    private static final String FRANCS      = "Royaume des Francs";
    
    /** 
     * Empire romain d'Orient, partenaire commercial majeur via la route du Dniepr. 
     */
    private static final String BYZANCE     = "Empire Byzantin";
    
    /** 
     * Puissance califale de Bagdad, source d'argent (dirhams) via la route de la Volga. 
     */
    private static final String CALIFAT     = "Califat Abbasside";

    /**  
     * Logger configuré pour une sortie HTML. 
     * Consigne chaque changement de delta pour permettre un débuggage précis de la simulation.
     */
    private static final Logger logger = LoggerUtility.getLogger(RelationManager.class, "html");

    /** 
     * Constructeur privé pour empêcher l'instanciation de cette classe utilitaire. 
     */
    private RelationManager() {}

    /**
     * Pilote la mise à jour globale du carnet diplomatique.
     * 
     * <p>
     * Pour chaque relation, cette méthode calcule l'influence de l'année en cours, 
     * applique le clampage de sécurité et consigne les changements dans le log HTML.
     * </p>
     * 
     * @param relations La liste des relations de la civilisation.
     * @param politique La politique actuelle (influe sur la réception des événements).
     * @param evenement L'événement historique du tour (peut être {@code null}).
     */
    public static void updateRelations(ArrayList<Relation> relations, Politique politique, Evenement evenement) {
        if (relations == null || relations.isEmpty()) return;

        for (Relation relation : relations) {
            float niveauAvant = relation.getNiveau();
            float delta = calculerDelta(relation, politique, evenement);
            float nouveau = clamp(relation.getNiveau() + delta, NIVEAU_MIN, NIVEAU_MAX);
            relation.setNiveau(nouveau);

            logger.info(String.format("Diplomatie [%s] : %.2f -> %.2f (Delta: %+.2f) %s",
                    relation.getNomCivilisation(),
                    niveauAvant, nouveau, delta,
                    (evenement != null ? "| Event: " + evenement.getNom() : "")));
        }
    }

    /**
     * Détermine le changement de score pour une nation spécifique.
     * 
     * @return Le delta de score calculé.
     */
    private static float calculerDelta(Relation relation, Politique politique, Evenement evenement) {
        // Dérive naturelle douce vers la neutralité (coefficient 0.5%)
        float niveauActuel = relation.getNiveau();
        float delta = (50f - niveauActuel) * 0.005f + (RANDOM.nextFloat() - 0.5f) * 0.3f;

        if (evenement == null) return delta;

        String nom = evenement.getNom();
        String civ = relation.getNomCivilisation();

        // Arbre de décision historique basé sur les archives scandinaves
        switch (nom) {
            // VIIIe siècle : L'éveil de la fureur
            case "Raid de Lindisfarne":
                if (NORTHUMBRIE.equals(civ))      delta -= 30f + RANDOM.nextFloat() * 3f;
                else if (IRLANDE.equals(civ))     delta -= 10f + RANDOM.nextFloat() * 2f; // Solidarité chrétienne
                else if (FRANCS.equals(civ))      delta -=  8f + RANDOM.nextFloat() * 2f;
                break;

            case "Premiers raids en Irlande et Écosse":
                if (IRLANDE.equals(civ))          delta -= 15f + RANDOM.nextFloat() * 3f;
                else if (NORTHUMBRIE.equals(civ)) delta -= 5f + RANDOM.nextFloat() * 2f;
                break;

            // IXe siècle : L'âge des conquêtes
            case "Raids fréquents en Europe de l'Ouest":
                if (FRANCS.equals(civ))           delta -= 20f + RANDOM.nextFloat() * 3f;
                else if (NORTHUMBRIE.equals(civ)) delta -= 8f + RANDOM.nextFloat() * 2f;
                else if (IRLANDE.equals(civ))     delta -=  5f + RANDOM.nextFloat() * 2f;
                break;

            case "Grande Armée païenne":
                if (NORTHUMBRIE.equals(civ))      delta -= 40f + RANDOM.nextFloat() * 3f;
                else if (IRLANDE.equals(civ))     delta -=  5f + RANDOM.nextFloat() * 2f;
                break;

            case "Victoires d'Alfred le Grand":
                if (NORTHUMBRIE.equals(civ))      delta += 10f + RANDOM.nextFloat() * 2f;
                break;

            case "Création du Danelaw":
                if (NORTHUMBRIE.equals(civ))      delta += 15f + RANDOM.nextFloat() * 2f;
                break;

            // Xe siècle : Commerce et Sédentarisation
            case "Commerce baltique":
                if (BYZANCE.equals(civ))          delta += 25f + RANDOM.nextFloat() * 3f;
                else if (CALIFAT.equals(civ))     delta += 25f + RANDOM.nextFloat() * 3f;
                break;

            case "Fondation de la Normandie":
                if (FRANCS.equals(civ))           delta += 30f + RANDOM.nextFloat() * 2f;
                break;

            case "Conversion au christianisme":
                if (NORTHUMBRIE.equals(civ))      delta += 40f + RANDOM.nextFloat() * 3f;
                else if (IRLANDE.equals(civ))     delta += 35f + RANDOM.nextFloat() * 3f;
                else if (FRANCS.equals(civ))      delta += 40f + RANDOM.nextFloat() * 3f;
                break;

            // XIe siècle : Le déclin et la fragmentation
            case "Bataille de Clontarf":
                if (IRLANDE.equals(civ))          delta -= 25f + RANDOM.nextFloat() * 3f;
                break;

            case "Mort de Knut le Grand":
                delta -= 15f + RANDOM.nextFloat() * 5f; // Effondrement de l'Empire de la Mer du Nord
                break;

            case "Bataille de Stamford Bridge":
                if (NORTHUMBRIE.equals(civ))      delta -= 40f + RANDOM.nextFloat() * 3f;
                else                              delta -= 20f + RANDOM.nextFloat() * 3f;
                break;

            default:
                delta += deltaParType(evenement.getType());
                break;
        }

        // Modulation par orientation diplomatique (Soft Power vs Hard Power)
        if (politique != null) {
            String diplo = politique.getPolitiqueDiplomatique();
            if ("Pacifique".equals(diplo)) {
                delta = delta > 0 ? delta * 1.3f : delta * 0.7f; // Favorise la paix, amortit la haine
            } else if ("Expansionniste".equals(diplo)) {
                delta = delta > 0 ? delta * 0.7f : delta * 1.3f; // Amortit les alliances, aggrave les conflits
            }
        }

        return delta;
    }

    /**
     * Calcule un delta générique basé sur la nature de l'événement.
     * <p>Utilisé uniquement si le nom de l'événement n'est pas répertorié dans la chronologie historique.</p>
     * 
     * @param type Le type d'événement (Guerre, Raid, Commerce, etc.).
     * @return Un delta de relation pondéré.
     */
    private static float deltaParType(String type) {
        switch (type) {
            case "Guerre":       return -(8f + RANDOM.nextFloat() * 5f);
            case "Raid":         return -(4f + RANDOM.nextFloat() * 3f);
            case "Diplomatie":   return  8f + RANDOM.nextFloat() * 3f;
            case "Commerce":     return  5f + RANDOM.nextFloat() * 3f;
            case "Religion":     return  8f + RANDOM.nextFloat() * 3f;
            case "Politique":    return -(2f + RANDOM.nextFloat() * 2f);
            case "Colonisation": return -(2f + RANDOM.nextFloat() * 1.5f);
            case "Exploration":  return  1f + RANDOM.nextFloat();
            default:             return  0f;
        }
    }

    /**
     * Analyse le niveau numérique pour fournir une étiquette textuelle compréhensible.
     * 
     * @param relation La relation à analyser.
     * @return Une chaîne parmi : "Alliance", "Neutre", "Tendue", "Hostile".
     */
    public static String getDescriptionRelation(Relation relation) {
        float n = relation.getNiveau();
        if (n >= SEUIL_ALLIANCE) return "Alliance";
        if (n >= 50f)            return "Neutre";
        if (n >= SEUIL_HOSTILE)  return "Tendue";
        return "Hostile";
    }

    /**
     * Identifie le partenaire diplomatique le plus fidèle.
     * @return La {@link Relation} avec le score le plus élevé ou {@code null}.
     */
    public static Relation getMeilleureRelation(ArrayList<Relation> relations) {
        if (relations == null || relations.isEmpty()) return null;
        Relation best = relations.get(0);
        for (Relation r : relations) if (r.getNiveau() > best.getNiveau()) best = r;
        return best;
    }

    /**
     * Identifie l'ennemi le plus acharné de la civilisation.
     * 
     * @param relations Liste des relations 
     * @return La {@link Relation} avec le score le plus bas ou {@code null}.
     */
    public static Relation getPireRelation(ArrayList<Relation> relations) {
        if (relations == null || relations.isEmpty()) return null;
        Relation worst = relations.get(0);
        for (Relation r : relations) if (r.getNiveau() < worst.getNiveau()) worst = r;
        return worst;
    }
    
}