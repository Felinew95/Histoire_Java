package moteur.traitement.management.managers.economie;

import moteur.donnees.Economie;
import moteur.donnees.Evenement;
import moteur.donnees.Produit;
import moteur.donnees.Ressource;

import moteur.traitement.management.factory.SimFactory;

import java.util.Random;

import static config.SimConfig.MAX_STYCAS;
import static utilitaire.SimulationUtility.clamp;

/**
 * Classe qui gère l'économie d'une civilisation de manière stable et réaliste.
 *
 * <p>
 * Cette classe s'occupe de la gestion des productions, exportations, et ressources importées,
 * ainsi que du calcul des gains et pertes en monnaie (stycas) selon la population et les événements.
 * Elle inclut des mécanismes pour éviter la croissance explosive et limiter les stocks.
 * </p>
 *
 * @author Alexandre
 * @version 2.0
 */
public class EconomieManager {

    /**
     * Générateur aléatoire partagé pour toute la classe.
     */
    private static final Random RANDOM = new Random();

    /**
     * Référence au repository des ressources.
     */
    private static final RessourceRepository ressourceRepository = RessourceRepository.getInstance();

    /**
     * Référence au repository des produits.
     */
    private static final ProduitRepository produitRepository = ProduitRepository.getInstance();

    /**
     * Met à jour l'économie de la civilisation.
     *
     * <p>
     * Cette méthode ajuste la monnaie disponible (stycas) en fonction des gains issus des productions et
     * exportations, des pertes liées à la consommation des ressources importées, et des événements
     * affectant la population. Elle utilise un facteur de saturation pour ralentir la croissance proche du
     * maximum et un facteur aléatoire léger pour ajouter de la variabilité.
     * </p>
     *
     * @param economie     l'objet {@link Economie} représentant l'état économique de la civilisation
     * @param nbHabitants  le nombre d'habitants de la civilisation, utilisé pour calculer la production et la consommation
     * @param evenement    un {@link Evenement} pouvant influencer la croissance ou la perte (ex. guerre, commerce)
     */
    public static void updateEconomie(Economie economie, int nbHabitants, Evenement evenement) {
        float ancienneValeurStycas = economie.getStycas();

        // Mise à jour des productions, exportations et ressources importées
        updateProductions(nbHabitants, economie, evenement);
        updateExportations(nbHabitants, economie, evenement);
        updateRessourcesImportees(nbHabitants, economie, evenement);

        // Gains : basés sur production et exportation, limités pour éviter croissance explosive
        float gains = getGains(economie) * 0.275f;

        // Pertes : proportionnelles à la consommation réelle
        float pertes = getPertes(economie, nbHabitants) * getFacteurPertes(evenement);

        // Facteur de saturation ajusté pour ralentir la croissance proche du max
        float facteurSaturation = 1 - (ancienneValeurStycas / MAX_STYCAS);
        facteurSaturation = clamp(facteurSaturation, 0.1f, 0.5f);

        // Facteur aléatoire léger
        float facteurAleatoire = 0.95f + RANDOM.nextFloat() * 0.1f; // 0.95 à 1.05

        // Nouveau stock de monnaie
        float nvValeurStycas = ancienneValeurStycas + (gains - pertes) * facteurSaturation * facteurAleatoire;

        // On limite strictement à MAX_STYCAS
        economie.setStycas(Math.min(nvValeurStycas, MAX_STYCAS));
        economie.setGainsAnnuel(gains);
        economie.setPertesAnnuel(pertes);
    }

    /**
     * Met à jour les ressources importées de la civilisation.
     *
     * <p>
     * Si une ressource n'existe pas encore dans l'économie, elle est créée avec une quantité et un prix
     * proportionnels à la population. Sinon, sa quantité est ajustée selon la variation calculée en
     * fonction de la population et des événements.
     * </p>
     *
     * @param nbHabitants  le nombre d'habitants pour ajuster les quantités
     * @param economie     l'économie de la civilisation
     * @param evenement    l'événement pouvant influencer la variation
     */
    private static void updateRessourcesImportees(int nbHabitants, Economie economie, Evenement evenement) {
        int maxQuantiteParRessource = Math.max(50, nbHabitants / 10);

        for (String nomRessource : ressourceRepository.getAllNomRessources()) {
            if (!economie.contientRessourceEnRessources(nomRessource)) {
                int quantite = Math.max(1, nbHabitants / 20);
                float prix = 10f + RANDOM.nextFloat() * 5f;
                economie.ajouterRessourceImportee(SimFactory.buildRessource(
                        nomRessource,
                        ressourceRepository.getTypeRessource(nomRessource),
                        quantite,
                        prix
                ));
            } else {
                Ressource res = economie.getRessourceImportee(nomRessource);
                int variation = calculerVariation(res.getQuantite(), nbHabitants, evenement);
                int nouvelleQuantite = res.getQuantite() + variation;

                // Limitation stricte entre 10% et 100% du max pour éviter effondrement
                nouvelleQuantite = clamp(nouvelleQuantite, quantiteMin(nbHabitants), maxQuantiteParRessource);
                res.setQuantite(nouvelleQuantite);
            }
        }
    }

    /**
     * Met à jour les produits exportés.
     *
     * <p>
     * Crée les produits exportables si inexistants et ajuste leur quantité selon la population et les événements.
     * Les quantités sont limitées pour éviter des variations trop extrêmes.
     * </p>
     *
     * @param nbHabitants  le nombre d'habitants pour ajuster les quantités
     * @param economie     l'économie de la civilisation
     * @param evenement    l'événement pouvant influencer la variation
     */
    private static void updateExportations(int nbHabitants, Economie economie, Evenement evenement) {
        int maxQuantiteExportation = Math.max(100, nbHabitants / 15);

        for (Produit produit : economie.getProductions().values()) {
            if (!economie.contientProduitEnExportation(produit.getNom())) {
                int quantite = Math.max(1, nbHabitants / 40);
                economie.ajouterProduitEnExportation(SimFactory.buildProduit(produit.getNom(), quantite, produit.getPrix()));
            } else {
                Produit exp = economie.getProduitEnExportation(produit.getNom());
                int variation = calculerVariation(exp.getQuantite(), nbHabitants, evenement) / 2;

                int nouvelleQuantite = exp.getQuantite() + variation;
                nouvelleQuantite = clamp(nouvelleQuantite, nbHabitants / 50, maxQuantiteExportation);
                exp.setQuantite(nouvelleQuantite);
            }
        }
    }

    /**
     * Met à jour les produits en production.
     *
     * <p>
     * Chaque produit est créé si absent, puis sa quantité est ajustée selon les règles de variation
     * basées sur la population et les événements. Une limitation maximale est appliquée.
     * </p>
     *
     * @param nbHabitants  le nombre d'habitants pour ajuster les quantités
     * @param economie     l'économie de la civilisation
     * @param evenement    l'événement pouvant influencer la variation
     */
    private static void updateProductions(int nbHabitants, Economie economie, Evenement evenement) {
        int maxQuantiteProduction = Math.max(200, nbHabitants / 8);
        int nbProduits = produitRepository.getNombreProduits();

        for (int i = 0; i < nbProduits; i++) {
            String nomProduit = produitRepository.getNomProduit(i);
            if (!economie.contientProduitEnProduction(nomProduit)) {
                int quantite = Math.max(1, nbHabitants / 30);
                float prix = 5f + RANDOM.nextFloat() * 2f;
                economie.ajouterProduitEnProduction(SimFactory.buildProduit(nomProduit, quantite, prix));
            } else {
                Produit prod = economie.getProduitEnProduction(nomProduit);
                int variation = calculerVariation(prod.getQuantite(), nbHabitants, evenement) / 2;

                int nouvelleQuantite = prod.getQuantite() + variation;
                nouvelleQuantite = clamp(nouvelleQuantite, nbHabitants / 40, maxQuantiteProduction);
                prod.setQuantite(nouvelleQuantite);
            }
        }
    }

    /**
     * Calcule la variation d'une quantité de ressource ou produit selon la population et un événement.
     *
     * <p>
     * Les événements peuvent diminuer ou augmenter la quantité (ex. guerre réduit, commerce augmente),
     * la population influe via un facteur racine carrée pour lisser la croissance,
     * et un facteur aléatoire ajoute de la variabilité.
     * </p>
     *
     * @param quantite     la quantité actuelle du produit ou ressource
     * @param nbHabitants  le nombre d'habitants
     * @param evenement    l'événement pouvant influencer la variation
     * @return la variation à appliquer (positive ou négative)
     */
    private static int calculerVariation(int quantite, int nbHabitants, Evenement evenement) {
        float pourcentage = 0.05f; // croissance neutre

        if (evenement != null) {
            if (evenement.getType().equals("Guerre") || evenement.getType().equals("Politique") || evenement.getType().equals("Raid")) {
                pourcentage = -0.20f;
            } else if (evenement.getType().equals("Commerce") || evenement.getType().equals("Exploration")) {
                pourcentage = 0.10f;
            }
        }

        float facteurPopulation = 1 + (float) Math.sqrt(nbHabitants) / 700f;
        float facteurAleatoire = 0.9f + RANDOM.nextFloat() * 0.2f;

        return (int) (quantite * pourcentage * facteurPopulation * facteurAleatoire);
    }

    /**
     * Calcule les pertes en stycas liées à la consommation des ressources importées.
     * <p>
     * Seule une fraction du stock est consommée par rapport au nombre d'habitants.
     * </p>
     *
     * @param economie     l'économie de la civilisation
     * @param nbHabitants  le nombre d'habitants
     * @return le montant total des pertes
     */
    private static float getPertes(Economie economie, int nbHabitants) {
        float pertes = 0f;
        for (Ressource res : economie.getRessourcesImportees().values()) {
            // Considérer que seulement une fraction du stock est consommée
            pertes += res.getPrix() * Math.min(res.getQuantite(), nbHabitants / 2);
        }
        return pertes;
    }

    /**
     * Calcule les gains en stycas issus des productions et exportations.
     *
     * <p>
     * Chaque produit et exportation contribue au total en fonction de sa quantité et de son prix.
     * </p>
     *
     * @param economie  l'économie de la civilisation
     * @return le montant total des gains
     */
    private static float getGains(Economie economie) {
        float gains = 0f;
        for (Produit prod : economie.getProductions().values()) {
            gains += prod.getPrix() * prod.getQuantite();
        }
        for (Produit prod : economie.getExportations().values()) {
            gains += prod.getPrix() * prod.getQuantite();
        }
        return gains;
    }

    /**
     * Retourne le facteur à appliquer aux pertes selon l'événement.
     *
     * <p>
     * Les guerres ou raids réduisent fortement les pertes, les autres situations appliquent un facteur par défaut.
     * </p>
     *
     * @param evenement  l'événement pouvant influencer le facteur
     * @return le facteur multiplicatif pour les pertes
     */
    private static float getFacteurPertes(Evenement evenement) {
        if (evenement != null && ("Guerre".equals(evenement.getType()) || "Raid".equals(evenement.getType()))) {
            return 0.3f;
        }
        return 0.15f;
    }

    /**
     * Renvoie la quantité minimale pour un stock afin d'éviter un effondrement complet.
     * <p>
     * Permet de garantir qu'il y a toujours un minimum de ressources disponibles.
     * </p>
     *
     * @param nbHabitants  le nombre d'habitants pour ajuster le minimum
     * @return la quantité minimale autorisée
     */
    private static int quantiteMin(int nbHabitants) {
        return Math.max(5, nbHabitants / 50);
    }

}