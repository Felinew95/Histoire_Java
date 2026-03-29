package moteur.traitement.management.managers;

import moteur.donnees.Economie;
import moteur.donnees.Evenement;
import moteur.donnees.Produit;
import moteur.donnees.Ressource;
import moteur.traitement.management.factory.SimFactory;
import moteur.traitement.management.repository.RessourceRepository;

import java.util.HashMap;
import java.util.Random;

import static config.SimConfig.MAX_STYCAS;
import static config.SimConfig.NOM_PRODUITS;

/**
 * Classe qui gère l'économie de la civilisation de façon stable
 *
 * @author Alexandre
 * @version 1.5
 */
public class EconomieManager {

    private static final Random RANDOM = new Random();
    private static final RessourceRepository ressourceRepository = RessourceRepository.getInstance();

    /**
     * Méthode qui met à jour l'économie de la civilisation
     *
     * @param economie    : Economie de la civilisation
     * @param nbHabitants : Nombre d'habitants
     * @param evenement   : Evenement en cours
     */
    public static void updateEconomie(Economie economie, int nbHabitants, Evenement evenement) {
        float ancienneValeurStycas = economie.getStycas();

        HashMap<String, Produit> productions = economie.getProductions();
        HashMap<String, Produit> exportations = economie.getExportations();
        HashMap<String, Ressource> ressourcesImportees = economie.getRessourcesImportees();

        updateProdEnProduction(nbHabitants, productions, evenement);
        updateProdEnExportation(nbHabitants, productions, exportations, evenement);
        updateRessourceImportees(nbHabitants, ressourcesImportees, evenement);

        float gains = getGains(productions, exportations) * 0.4f;

        float facteurPertes = 0.15f;
        if (evenement != null && (evenement.getType().equals("Guerre") || evenement.getType().equals("Raid"))) {
            facteurPertes = 0.3f;
        }
        float pertes = getPertes(ressourcesImportees) * facteurPertes;

        float facteurSaturation = 1 - (ancienneValeurStycas / MAX_STYCAS);
        facteurSaturation = Math.max(0.1f, facteurSaturation);

        float amortissement = 0.1f;

        float nvValeurStycas = ancienneValeurStycas + (gains - pertes) * facteurSaturation * amortissement;
        economie.setStycas(Math.min(nvValeurStycas, MAX_STYCAS));
    }

    /**
     * Méthode qui met à jour les ressources importées
     *
     * @param ressourcesImportees : Les ressources importées
     * @param nbHabitants : Nombre d'habitants
     * @param evenement   : Evenement en cours
     */
    private static void updateRessourceImportees(int nbHabitants, HashMap<String, Ressource> ressourcesImportees, Evenement evenement) {
        int maxQuantiteParRessource = Math.max(50, nbHabitants / 10);

        for (String nomRessource : ressourceRepository.getAllNomRessources()) {
            if (!ressourcesImportees.containsKey(nomRessource)) {
                int quantite = Math.max(1, nbHabitants / 20);
                float prix = 10f + RANDOM.nextFloat() * 5f;
                ressourcesImportees.put(nomRessource, SimFactory.buildRessource(nomRessource, ressourceRepository.getTypeRessource(nomRessource), quantite, prix));
            } else {
                Ressource res = ressourcesImportees.get(nomRessource);
                int variation = calculerVariation(res.getQuantite(), nbHabitants, evenement);
                int nouvelleQuantite = res.getQuantite() + variation;
                res.setQuantite(Math.max(0, Math.min(nouvelleQuantite, maxQuantiteParRessource)));
            }
        }
    }

    /**
     * Méthode qui met à jour les produits en exportations
     *
     * @param productions : Produits en production
     * @param exportations : Produits en exportations
     * @param nbHabitants : Nombre d'habitants
     * @param evenement   : Evenement en cours
     */
    private static void updateProdEnExportation(int nbHabitants, HashMap<String, Produit> productions, HashMap<String, Produit> exportations, Evenement evenement) {
        int maxQuantiteExportation = Math.max(100, nbHabitants / 15);

        for (Produit produit : productions.values()) {
            if (!exportations.containsKey(produit.getNom())) {
                int quantite = Math.max(1, nbHabitants / 40);
                exportations.put(produit.getNom(), SimFactory.buildProduit(produit.getNom(), quantite, produit.getPrix()));
            } else {
                Produit exp = exportations.get(produit.getNom());
                int variation = calculerVariation(exp.getQuantite(), nbHabitants, evenement) / 2;
                int nouvelleQuantite = exp.getQuantite() + variation;
                exp.setQuantite(Math.max(0, Math.min(nouvelleQuantite, maxQuantiteExportation)));
            }
        }
    }

    /**
     * Méthode qui met à jour les produits en production
     *
     * @param productions : Produits en production
     * @param nbHabitants : Nombre d'habitants
     * @param evenement   : Evenement en cours
     */
    private static void updateProdEnProduction(int nbHabitants, HashMap<String, Produit> productions, Evenement evenement) {
        int maxQuantiteProduction = Math.max(200, nbHabitants / 8);

        for (String nomProduit : NOM_PRODUITS) {
            if (!productions.containsKey(nomProduit)) {
                int quantite = Math.max(1, nbHabitants / 30);
                float prix = 5f + RANDOM.nextFloat() * 2f;
                productions.put(nomProduit, SimFactory.buildProduit(nomProduit, quantite, prix));
            } else {
                Produit prod = productions.get(nomProduit);
                int variation = calculerVariation(prod.getQuantite(), nbHabitants, evenement) / 2;
                int nouvelleQuantite = prod.getQuantite() + variation;
                prod.setQuantite(Math.max(0, Math.min(nouvelleQuantite, maxQuantiteProduction)));
            }
        }
    }

    /**
     * Méthode qui calcule la variation de la quantité de produit selon un événement
     *
     * @param quantite : Quantité d'un produit
     * @param nbHabitants : Nombre d'habitants
     * @param evenement   : Evenement en cours
     * @return La variation de la quantité
     */
    private static int calculerVariation(int quantite, int nbHabitants, Evenement evenement) {
        float pourcentageVariation = 0.05f;

        if (evenement != null) {
            switch (evenement.getType()) {
                case "Guerre":
                case "Politique":
                case "Raid":
                    pourcentageVariation = -0.15f;
                    break;
                case "Commerce":
                case "Exploration":
                    pourcentageVariation = 0.05f;
                    break;
            }
        }

        float facteurPopulation = 1 + (float) Math.sqrt(nbHabitants) / 100f;
        float facteurAleatoire = 0.8f + RANDOM.nextFloat() * 0.4f;

        int variation = (int) (quantite * pourcentageVariation * facteurPopulation * facteurAleatoire);

        return variation;
    }

    /**
     * Méthode qui calcule les pertes
     *
     * @param ressourcesImportees : Les ressources importées
     * @return Les pertes
     */
    private static float getPertes(HashMap<String, Ressource> ressourcesImportees) {
        float pertes = 0f;
        for (Ressource res : ressourcesImportees.values()) {
            pertes += res.getQuantite() * res.getPrix();
        }
        return pertes;
    }

    /**
     * Méthode qui calcule les gains
     *
     * @param productions : Produits en production
     * @param exportations : Produits en exportations
     * @return Les gains
     */
    private static float getGains(HashMap<String, Produit> productions, HashMap<String, Produit> exportations) {
        float gains = 0f;
        for (Produit prod : productions.values()) {
            gains += prod.getPrix() * prod.getQuantite();
        }
        for (Produit prod : exportations.values()) {
            gains += prod.getPrix() * prod.getQuantite();
        }
        return gains;
    }

}