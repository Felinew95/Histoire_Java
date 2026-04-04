package moteur.donnees;

import java.util.HashMap;

import static utilitaire.SimulationUtility.*;

/**
 * Représente l'économie d'une civilisation.
 *
 * <p>
 * Une économie est composée d'un capital monétaire appelé <em>stycas</em>,
 * de produits en production, de produits destinés à l'exportation,
 * et de ressources importées. Elle conserve également les gains et pertes annuels
 * calculés pour des analyses économiques ou des rapports.
 * </p>
 *
 * <p>
 * Les produits et ressources sont stockés dans des {@link HashMap}, indexés par leur nom.
 * </p>
 *
 * @author Alexandre
 * @author Massinissa
 * @version 1.2
 *
 * @see Ressource
 * @see Produit
 */
public class Economie {

    /**
     * Monnaie disponible (stycas).
     */
    private float stycas;

    /**
     * Gains annuels calculés sur l'année en cours.
     */
    private float gainsAnnuel = 0f;

    /**
     * Pertes annuelles calculées sur l'année en cours.
     */
    private float pertesAnnuel = 0f;

    /**
     * Produits actuellement en production, indexés par nom.
     */
    private final HashMap<String, Produit> productions = new HashMap<>();

    /**
     * Produits destinés à l'exportation, indexés par nom.
     */
    private final HashMap<String, Produit> exportations = new HashMap<>();

    /**
     * Ressources importées depuis d'autres civilisations, indexées par nom.
     */
    private final HashMap<String, Ressource> ressourcesImportees = new HashMap<>();

    /**
     * Construit une économie avec un capital initial.
     *
     * @param stycas montant initial de stycas (doit être >= 0)
     */
    public Economie(float stycas) {
        this.stycas = stycas;
    }

    /**
     * Construit une économie avec un capital initial nul.
     */
    public Economie() {
        this(0);
    }

    /**
     * Retourne le capital disponible en stycas.
     *
     * @return le montant de stycas
     */
    public float getStycas() {
        return stycas;
    }

    /**
     * Définit le capital disponible.
     *
     * @param stycas nouveau montant de stycas
     */
    public void setStycas(float stycas) {
        this.stycas = stycas;
    }

    /**
     * Retourne les produits actuellement en production.
     *
     * @return {@link HashMap} des produits en production, indexés par nom
     */
    public HashMap<String, Produit> getProductions() {
        return productions;
    }

    /**
     * Retourne les produits actuellement destinés à l'exportation.
     *
     * @return {@link HashMap} des produits en exportation, indexés par nom
     */
    public HashMap<String, Produit> getExportations() {
        return exportations;
    }

    /**
     * Retourne les ressources importées depuis d'autres civilisations.
     *
     * @return {@link HashMap} des ressources importées, indexées par nom
     */
    public HashMap<String, Ressource> getRessourcesImportees() {
        return ressourcesImportees;
    }

    /**
     * Vérifie si un produit est présent dans la production.
     *
     * @param cle nom du produit
     * @return {@code true} si le produit est en production, {@code false} sinon
     */
    public boolean contientProduitEnProduction(String cle) {
        return contientCle(productions, cle);
    }

    /**
     * Vérifie si un produit est présent dans l'exportation.
     *
     * @param cle nom du produit
     * @return {@code true} si le produit est en exportation, {@code false} sinon
     */
    public boolean contientProduitEnExportation(String cle) {
        return contientCle(exportations, cle);
    }

    /**
     * Vérifie si une ressource est présente dans les ressources importées.
     *
     * @param cle nom de la ressource
     * @return {@code true} si la ressource est importée, {@code false} sinon
     */
    public boolean contientRessourceEnRessources(String cle) {
        return contientCle(ressourcesImportees, cle);
    }

    /**
     * Retourne un produit présent dans la production à partir de sa clé.
     *
     * @param cle nom du produit
     * @return le {@link Produit} correspondant si trouvé, {@code null} sinon
     */
    public Produit getProduitEnProduction(String cle) {
        return productions.get(cle);
    }

    /**
     * Retourne un produit présent dans l'exportation à partir de sa clé.
     *
     * @param cle nom du produit
     * @return le {@link Produit} correspondant si trouvé, {@code null} sinon
     */
    public Produit getProduitEnExportation(String cle) {
        return exportations.get(cle);
    }

    /**
     * Retourne une ressource importée à partir de sa clé.
     *
     * @param cle nom de la ressource
     * @return la {@link Ressource} importée correspondante si trouvée, {@code null} sinon
     */
    public Ressource getRessourceImportee(String cle) {
        return ressourcesImportees.get(cle);
    }

    /**
     * Ajoute un produit à la production.
     *
     * @param prod produit à ajouter
     */
    public void ajouterProduitEnProduction(Produit prod) {
        addElement(prod.getNom(), prod, productions);
    }

    /**
     * Supprime un produit de la production.
     *
     * @param nomProd nom du produit à supprimer
     */
    public void supprimerProduitEnProduction(String nomProd) {
        suppElement(productions, nomProd);
    }

    /**
     * Ajoute un produit à l'exportation.
     *
     * @param prod produit à ajouter à l'exportation
     */
    public void ajouterProduitEnExportation(Produit prod) {
        addElement(prod.getNom(), prod, exportations);
    }

    /**
     * Supprime un produit de l'exportation.
     *
     * @param nomProd nom du produit à supprimer
     */
    public void supprimerProduitEnExportation(String nomProd) {
        suppElement(exportations, nomProd);
    }

    /**
     * Ajoute une ressource importée.
     *
     * @param res ressource à ajouter
     */
    public void ajouterRessourceImportee(Ressource res) {
        addElement(res.getNom(), res, ressourcesImportees);
    }

    /**
     * Supprime une ressource importée.
     *
     * @param nomRes nom de la ressource à supprimer
     */
    public void supprimerRessourceImportee(String nomRes) {
        suppElement(ressourcesImportees, nomRes);
    }

    /**
     * Retourne les gains annuels de l'économie.
     *
     * @return montant total des gains annuels
     */
    public float getGainsAnnuel() {
        return gainsAnnuel;
    }

    /**
     * Définit les gains annuels de l'économie.
     *
     * @param gainsAnnuel montant total des gains annuels
     */
    public void setGainsAnnuel(float gainsAnnuel) {
        this.gainsAnnuel = gainsAnnuel;
    }

    /**
     * Retourne les pertes annuelles de l'économie.
     *
     * @return montant total des pertes annuelles
     */
    public float getPertesAnnuel() {
        return pertesAnnuel;
    }

    /**
     * Définit les pertes annuelles de l'économie.
     *
     * @param pertesAnnuel montant total des pertes annuelles
     */
    public void setPertesAnnuel(float pertesAnnuel) {
        this.pertesAnnuel = pertesAnnuel;
    }

    /**
     * Retourne une représentation textuelle complète de l'économie.
     *
     * @return une chaîne décrivant le capital, les produits en production,
     *         les produits exportés et les ressources importées
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Stycas : ").append(stycas);

        str.append("\nProduits en production : \n");
        for (Produit p : this.productions.values()) {
            str.append(p).append("\n");
        }

        str.append("\nProduits en exportation : \n");
        for (Produit p : this.exportations.values()) {
            str.append(p).append("\n");
        }

        str.append("\nRessources importées : \n");
        for (Ressource r : this.ressourcesImportees.values()) {
            str.append(r).append("\n");
        }

        return str.toString();
    }

}