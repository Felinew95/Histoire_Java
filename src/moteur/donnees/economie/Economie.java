package moteur.donnees.economie;

import exceptions.ProduitEnExportationNotFoundException;
import exceptions.ProduitEnProductionNotFoundException;
import exceptions.RessourceImporteeNotFoundException;

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
    private final HashMap<String, Produit> productions;
    private final HashMap<String, Produit> exportations;
    private final HashMap<String, Ressource> ressourcesImportees;

    /**
     * Constructeur de la classe Economie
     * 
     * @param stycas : Argent total
     */
    public Economie(float stycas) {
        this.stycas = stycas;
        this.productions = new HashMap<>();
        this.exportations = new HashMap<>();
        this.ressourcesImportees = new HashMap<>();
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
     * Ajoute un produit en production
     * 
     * @param prod : Un produit
     */
    public void ajouterProduitEnProduction(Produit prod) {
        String nomProd = prod.getNom();
        if (!this.productions.containsKey(nomProd)) {
            this.productions.put(nomProd, prod);
        } else {
            Produit p = this.productions.get(nomProd);
            p.setQuantite(p.getQuantite() + prod.getQuantite());
        }
    }

    /**
     * Supprime un produit en production
     * 
     * @param prod : Un produit
     * @throws ProduitEnProductionNotFoundException
     */
    public void supprimerProduitEnProduction(String nomProd) throws ProduitEnProductionNotFoundException {
        if (this.productions.containsKey(nomProd)) {
            this.productions.remove(nomProd);
        } else {
            throw new ProduitEnProductionNotFoundException("Le produit en production n'existe pas");
        }
    }

    /**
     * Ajoute un produit en exportation
     * 
     * @param prod : Un produit
     */
    public void ajouterProduitEnExportation(Produit prod) {
        String nomProd = prod.getNom();
        if (!this.exportations.containsKey(nomProd)) {
            this.exportations.put(nomProd, prod);
        } else {
            Produit p = this.exportations.get(nomProd);
            p.setQuantite(p.getQuantite() + prod.getQuantite());
        }
    }

    /**
     * Supprime un produit en exportation
     * 
     * @param prod : Un produit
     * @throws ProduitEnProductionNotFoundException
     */
    public void supprimerProduitEnExportation(String nomProd) throws ProduitEnExportationNotFoundException {
        if (this.productions.containsKey(nomProd)) {
            this.productions.remove(nomProd);
        } else {
            throw new ProduitEnExportationNotFoundException("Le produit en production n'existe pas");
        }
    }

    /**
     * Ajoute une ressource importée
     * 
     * @param res : Ressource importée
     */
    public void ajouterRessourceImportee(Ressource res) {
        String nomRes = res.getNom();
        if (!this.ressourcesImportees.containsKey(nomRes)) {
            this.ressourcesImportees.put(nomRes, res);
        } else {
            Ressource resImp = this.ressourcesImportees.get(nomRes);
            resImp.setQuantite(resImp.getQuantite() + res.getQuantite());
        }
    }

    /**
     * Supprime une ressource importée
     * 
     * @param nomRes : Nom de la ressource importée
     * @throws RessourceImporteeNotFoundException
     */
    public void supprimerRessourceImportee(String nomRes) throws RessourceImporteeNotFoundException {
        if (this.ressourcesImportees.containsKey(nomRes)) {
            this.ressourcesImportees.remove(nomRes);
        } else {
            throw new RessourceImporteeNotFoundException("La ressource importée n'existe pas");
        }
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
