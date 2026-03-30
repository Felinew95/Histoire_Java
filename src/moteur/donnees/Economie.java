package moteur.donnees;

import java.util.HashMap;

/**
 * Classe qui représente une économie
 * 
 * @author Alexandre et Massinissa
 * @version 1.0
 * 
 * @see Ressource
 * @see Produit
 */
public class Economie {

    // Attributs
    private float stycas; // Argent
    private final HashMap<String, Produit> productions = new HashMap<>();
    private final HashMap<String, Produit> exportations = new HashMap<>();
    private final HashMap<String, Ressource> ressourcesImportees = new HashMap<>();

    /**
     * Constructeur de la classe Economie
     * 
     * @param stycas : Argent total
     */
    public Economie(float stycas) {
        this.stycas = stycas;    
    }

    /**
     * Deuxième constructeur de la classe Economie. Stycas est initialisé à 0
     */
    public Economie() {
        this(0);
    }

    /**
     * Getter de stycas
     * 
     * @return Les stycas
     */
    public float getStycas() {
        return stycas;
    }

    /**
     * Getter de productions
     * 
     * @return La liste des produits en production
     */
    public HashMap<String, Produit> getProductions() {
        return productions;
    }

    /**
     * Getter de exportations
     * 
     * @return La liste des produits exportées
     */
    public HashMap<String, Produit> getExportations() {
        return exportations;
    }

    /**
     * Getter de ressourcesImportees
     * 
     * @return La liste des ressources importées
     */
    public HashMap<String, Ressource> getRessourcesImportees() {
        return ressourcesImportees;
    }

    /**
     * Setter de stycas
     * 
     * @param stycas : Nouvelle valeurs des stycas
     */
    public void setStycas(float stycas) {
        this.stycas = stycas;
    }

    /**
     * Méthode qui vérifie si un produit est bien en production
     *
     * @param nomProd : Nom du produit
     * @return true si un produit est bien en production, false sinon
     */
    public boolean contientProduitEnProduction(String nomProd) {
        return this.productions.containsKey(nomProd);
    }

    /**
     * Méthode qui vérifie si un produit est bien en exportation
     *
     * @param nomProd : Nom du produit
     * @return true si un produit est bien en exportation, false sinon
     */
    public boolean contientProduitEnExportation(String nomProd) {
        return this.exportations.containsKey(nomProd);
    }

    /**
     * Méthode qui vérifie si une ressource est bien importée
     *
     * @param nomRessource : Nom de la ressource
     * @return true si une ressource est bien importée, false sinon
     */
    public boolean contientRessourceImportee(String nomRessource) {
        return this.ressourcesImportees.containsKey(nomRessource);
    }

    /**
     * Ajoute un produit en production
     * 
     * @param prod : Un produit
     */
    public void ajouterProduitEnProduction(Produit prod) {
        if (prod != null && !this.productions.containsKey(prod.getNom())) {
            String nomProd = prod.getNom();
            this.productions.put(nomProd, prod);
        } 
    }

    /**
     * Supprime un produit en production
     * 
     * @param nomProd : Un produit
     */
    public void supprimerProduitEnProduction(String nomProd) {
        this.productions.remove(nomProd);
    }

    /**
     * Ajoute un produit en exportation
     * 
     * @param prod : Un produit
     */
    public void ajouterProduitEnExportation(Produit prod) {
        if (prod != null && !this.exportations.containsKey(prod.getNom())) {
            String nomProd = prod.getNom();
            this.exportations.put(nomProd, prod);
        } 
    }

    /**
     * Supprime un produit en exportation
     * 
     * @param nomProd : Un produit
     */
    public void supprimerProduitEnExportation(String nomProd) {
        this.exportations.remove(nomProd);
    }

    /**
     * Ajoute une ressource importée
     * 
     * @param res : Ressource importée
     */
    public void ajouterRessourceImportee(Ressource res) {
        if (res != null && !this.ressourcesImportees.containsKey(res.getNom())) {
            String nomRes = res.getNom();
            this.ressourcesImportees.put(nomRes, res);
        } 
    }

    /**
     * Supprime une ressource importée
     * 
     * @param nomRes : Nom de la ressource importée
     */
    public void supprimerRessourceImportee(String nomRes) {
        this.ressourcesImportees.remove(nomRes);
    }

    /**
     * Affiche les informations de l'économie
     * 
     * @return L'affichage des informations
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Stycas : ").append(stycas);

        // Produits en productions
        str.append("\nProduits en production : \n");
        for (Produit p : this.productions.values()) {
            str.append(p.toString()).append("\n");
        }

        // Produits en exportation
        str.append("\nProduits en exportation : \n");
        for (Produit p : this.exportations.values()) {
            str.append(p.toString()).append("\n");
        }

        // Ressources importées
        str.append("\nRessources importées : \n");
        for (Ressource r : this.ressourcesImportees.values()) {
            str.append(r.toString()).append("\n");
        }

        return str.toString();
    }

}
