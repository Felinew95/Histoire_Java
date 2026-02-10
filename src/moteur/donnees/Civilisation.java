package moteur.donnees;

import java.util.ArrayList;
import java.util.Iterator;
import javax.management.relation.Relation;
import moteur.donnees.economie.Economie;

public class Civilisation {
    
    // Attributs 
    
    private String konungr; // Roi de la civilisation
    private Economie economie;
    private Armee armee;
    private Population population;
    private ArrayList<Evenement> evenements= new ArrayList<>();
    private Religion religion;
    private ArrayList<Relation> relations= new ArrayList<>();
    private Politique politique;
    private String nom;
    private ArrayList<Region> regions= new ArrayList<>();
    
    /**
     * Constructeur de la classe Civilisation
     * 
     * @param konungr : Nom du roi/chef de la civilisation
     * @param economie : Économie de la civilisation
     * @param armee : Armée de la civilisation
     * @param population : Population de la civilisation
     * @param religion : Religion de la civilisation
     * @param politique : Politique de la civilisation
     * @param nom : Nom de la civilisation
     */

    public Civilisation(String konungr, Economie economie, Armee armee, Population population, 
                       Religion religion, Politique politique, String nom) {
        this.konungr = konungr;
        this.economie = economie;
        this.armee = armee;
        this.population = population;
        this.religion = religion;
        this.politique = politique;
        this.nom = nom;
        
    }
    
    /**
     * Getter de konungr
     * @return Le nom du konungr
     */
    public String getKonungr() {
        return konungr;
    }
    
    /**
     * Getter de economie
     * @return L'économie de la civilisation
     */
    public Economie getEconomie() {
        return economie;
    }
    
    /**
     * Getter de armee
     * @return L'armée de la civilisation
     */
    public Armee getArmee() {
        return armee;
    }
    
    /**
     * Getter de population
     * @return La population de la civilisation
     */
    public Population getPopulation() {
        return population;
    }
    
    /**
     * Getter de evenements
     * @return La liste des événements
     */
    public ArrayList<Evenement> getEvenements() {
        return evenements;
    }
    
    /**
     * Getter de religion
     * @return La religion de la civilisation
     */
    public Religion getReligion() {
        return religion;
    }
    
    /**
     * Getter de relations
     * @return La liste des relations
     */
    public ArrayList<Relation> getRelations() {
        return relations;
    }
    
    /**
     * Getter de politique
     * @return La politique de la civilisation
     */
    public Politique getPolitique() {
        return politique;
    }
    
    /**
     * Getter de nom
     * @return Le nom de la civilisation
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Getter de regions
     * @return La liste des régions
     */
    public ArrayList<Region> getRegions() {
        return regions;
    }
    
    /**
     * Setter de konungr
     * @param konungr : Nouveau konungr
     */
    public void setKonungr(String konungr) {
        this.konungr = konungr;
    }
    
    /**
     * Setter de economie
     * @param economie : Nouvelle économie
     */
    public void setEconomie(Economie economie) {
        this.economie = economie;
    }
    
    /**
     * Setter de armee
     * @param armee : Nouvelle armée
     */
    public void setArmee(Armee armee) {
        this.armee = armee;
    }
    
    /**
     * Setter de population
     * @param population : Nouvelle population
     */
    public void setPopulation(Population population) {
        this.population = population;
    }
    
    /**
     * Setter de religion
     * @param religion : Nouvelle religion
     */
    public void setReligion(Religion religion) {
        this.religion = religion;
    }
    
    /**
     * Setter de politique
     * @param politique : Nouvelle politique
     */
    public void setPolitique(Politique politique) {
        this.politique = politique;
    }
    
    /**
     * Setter de nom
     * @param nom : Nouveau nom de la civilisation
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Ajoute un événement à la civilisation
     * @param even : Événement à ajouter
     */
    public void ajouterEvenement(Evenement even) {
        if (!this.evenements.contains(even)) {
            this.evenements.add(even);
        }
    }
    
    /**
     * Supprime un événement de la civilisation
     * @param even : Événement à supprimer
     */
    public void supprimerEvenement(Evenement even) {
        this.evenements.remove(even);
    }
    
    /**
     * Ajoute une relation avec une autre civilisation
     * @param rel : Relation à ajouter
     */
    public void ajouterRelation(Relation rel) {
        if (!this.relations.contains(rel)) {
            this.relations.add(rel);
        }
    }
    
    /**
     * Supprime une relation avec une autre civilisation
     * @param rel : Relation à supprimer
     */
    public void supprimerRelation(Relation rel) {
        this.relations.remove(rel);
    }
    
    /**
     * Ajoute une région à la civilisation
     * @param reg : Région à ajouter
     */
    public void ajouterRegion(Region reg) {
        if (!this.regions.contains(reg)) {
            this.regions.add(reg);
        }
    }
    
    /**
     * Supprime une région de la civilisation
     * @param reg : Région à supprimer
     */
    public void supprimerRegion(Region reg) {
        this.regions.remove(reg);
    }
    
    /**
     * Affiche les informations de la civilisation
     * @return L'affichage des informations
     */
    @Override
   

public String toString() {
    StringBuilder str = new StringBuilder();

    str.append(" Civilisation : ").append(this.getNom()).append(" \n");
    str.append("Konungr : ").append(this.getKonungr()).append("\n");

    str.append("\n Économie \n").append(this.economie.toString());
    str.append("\n Armée ---\n").append(this.armee.toString());
    str.append("\n--- Population ---\n").append(this.population.toString());
    str.append("\n--- Religion ---\n").append(this.religion.toString());
    str.append("\n--- Politique ---\n").append(this.politique.toString());

    // Régions
    str.append("\n--- Régions (").append(this.regions.size()).append(") ---\n");
    Iterator<Region> itRegion = this.regions.iterator();
    while (itRegion.hasNext()) {
        Region r = itRegion.next();
        str.append(r.toString()).append("\n");
    }

    // Relations
    str.append("\n--- Relations (").append(this.relations.size()).append(") ---\n");
    Iterator<Relation> itRelation = this.relations.iterator();
    while (itRelation.hasNext()) {
        Relation r = itRelation.next();
        str.append(r.toString()).append("\n");
    }

    // Événements
    str.append("\n--- Événements (").append(this.evenements.size()).append(") ---\n");
    Iterator<Evenement> itEvenement = this.evenements.iterator();
    while (itEvenement.hasNext()) {
        Evenement e = itEvenement.next();
        str.append(e.toString()).append("\n");
    }

    return str.toString();
}

}
    

