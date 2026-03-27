package moteur.traitement.management.managers;

import moteur.donnees.Economie;
import moteur.donnees.Produit;
import moteur.donnees.Ressource;

import java.util.HashMap;
import java.util.Random;

/**
 * Classe qui gère l'économie de la civilisation
 *
 * @author Alexandre
 * @version 1.0
 */
public class EconomieManager {

    // Attributs
    private static final Random RANDOM = new Random();



    /**
     * Méthode qui permet de mettre à jour l'économie de la civilisation
     *
     * @param economie : Économie de la civilisation
     * @param nvNbHabitants : Nouveau nombre d'habitants de la civilisation
     * @param nbHabitants : Ancien nombre d'habitants de la civilisation
     */
    public static void updateEconomie(Economie economie, int nvNbHabitants, int nbHabitants) {
        float ancienneValeurStycas = economie.getStycas();
        HashMap<String, Produit> productions = economie.getProductions();
        HashMap<String, Produit> exportations = economie.getExportations();
        HashMap<String, Ressource> ressourcesImportees = economie.getRessourcesImportees();

        // Calcul des gains et pertes
        float gains = getGains(productions, economie, exportations);
        float pertes = getPertes(ressourcesImportees);

        float facteurEconomique = economie.getFacteurEconomie();
        float nvValeurStycas =  ancienneValeurStycas + (gains - pertes)*facteurEconomique;

        economie.setStycas(nvValeurStycas);

        float nouveauFacteurEconomique = getNouveauFacteurEconomique(nvNbHabitants, nbHabitants);
        facteurEconomique = 0.7f * facteurEconomique + 0.3f * nouveauFacteurEconomique;
        facteurEconomique = Math.max(0.7f, Math.min(1.3f, facteurEconomique));

        float bonusExport = economie.getBonusExport();
        float nouveauBonusExport = 1.2f + RANDOM.nextFloat() * 0.3f;
        bonusExport = 0.7f * bonusExport + 0.3f * nouveauBonusExport;
        bonusExport = Math.min(1.5f, Math.max(1.2f, bonusExport));

        economie.setFacteurEconomie(facteurEconomique);
        economie.setBonusExport(bonusExport);
    }

    /**
     * Méthode qui calcule le nouveau facteur économique de la civilisation
     *
     * @param nvNbHabitants : Nouveau nombre d'habitants de la civilisation
     * @param nbHabitants : Ancien nombre d'habitants de la civilisation
     *
     * @return Le nouveau facteur économique de la civilisation
     */
    private static float getNouveauFacteurEconomique(int nvNbHabitants, int nbHabitants) {
        float nouveauFacteurEconomique;
        if (nvNbHabitants < nbHabitants) {
            nouveauFacteurEconomique = 0.5f + RANDOM.nextFloat() * 0.4f;
        } else {
            nouveauFacteurEconomique = 1.0f + RANDOM.nextFloat() * 0.2f;
        }
        return nouveauFacteurEconomique;
    }

    /**
     * Méthode qui permet de calculer les pertes économiques
     *
     * @param ressourcesImportees : Les ressources importées
     * @return Les pertes économiques
     */
    private static float getPertes(HashMap<String, Ressource> ressourcesImportees) {
        float pertes = 0f;

        for (Ressource ressource : ressourcesImportees.values()) {
            pertes += ressource.getQuantite()*ressource.getCout();
        }

        return pertes;
    }

    /**
     * Méthode qui permet de calculer les gains économiques
     *
     * @param productions : Les productions de la civilisation
     * @param economie : Économie de la civilisation
     * @param exportations : Les exportations de la civilisation
     *
     * @return Les gains économiques
     */
    private static float getGains(HashMap<String, Produit> productions, Economie economie, HashMap<String, Produit> exportations) {
        float gainsProds = 0f;
        float gainsExports = 0f;
        float bonusExports = economie.getBonusExport();

        for (Produit prod : productions.values()) {
            gainsProds += prod.getPrix()*prod.getQuantite();
        }

        for (Produit prod : exportations.values()) {
            gainsExports += prod.getPrix()*prod.getQuantite()*bonusExports;
        }

        return gainsProds + gainsExports;
    }

}
