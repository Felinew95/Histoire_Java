package moteur.donnees;

import java.awt.Color;
import java.util.ArrayList;

import moteur.traitement.management.factory.SimFactory;

/**
 * Classe qui représente une civilisation.
 *
 * @author Massinissa
 * @version 1.1
 *
 * @see Economie
 * @see Armee
 * @see Population
 * @see Religion
 * @see Politique
 * @see Evenement
 * @see Relation
 * @see Region
 * @see Konungr
 */
public class Civilisation {

    // Attributs
    private Konungr konungr;   // Roi de la civilisation
    private Economie economie;
    private Armee armee;
    private Population population;
    private final ArrayList<Evenement> evenements = new ArrayList<>();
    private Religion religion;
    private final ArrayList<Relation> relations = new ArrayList<>();
    private Politique politique;
    private String nom;
    private final ArrayList<Region> regions = new ArrayList<>();

    /**
     * Couleur représentative de la civilisation sur la carte.
     * Ex : marron pour Vikings, rouge pour Anglo-Saxons, bleu pour Francs.
     */
    private Color couleur;

    /**
     * Icône ou emoji représentant la civilisation.
     * Ex : "⚔️" pour Vikings, "🏰" pour Anglo-Saxons.
     */
    private String icone;

    /**
     * Constructeur complet avec couleur et icône.
     *
     * @param konungr    Chef de la civilisation
     * @param economie   Économie de la civilisation
     * @param armee      Armée de la civilisation
     * @param population Population de la civilisation
     * @param religion   Religion de la civilisation
     * @param politique  Politique de la civilisation
     * @param nom        Nom de la civilisation
     * @param couleur    Couleur représentative sur la carte
     * @param icone      Icône ou emoji de la civilisation
     */
    public Civilisation(Konungr konungr, Economie economie, Armee armee, Population population,
                        Religion religion, Politique politique, String nom,
                        Color couleur, String icone) {
        this.konungr = konungr;
        this.economie = economie;
        this.armee = armee;
        this.population = population;
        this.religion = religion;
        this.politique = politique;
        this.nom = nom;
        this.couleur = couleur;
        this.icone = icone;
    }

    /**
     * Constructeur sans couleur ni icône — compatibilité avec le code existant.
     * Utilise gris par défaut et icône vide.
     *
     * @param konungr    Chef de la civilisation
     * @param economie   Économie de la civilisation
     * @param armee      Armée de la civilisation
     * @param population Population de la civilisation
     * @param religion   Religion de la civilisation
     * @param politique  Politique de la civilisation
     * @param nom        Nom de la civilisation
     */
    public Civilisation(Konungr konungr, Economie economie, Armee armee, Population population,
                        Religion religion, Politique politique, String nom) {
        this(konungr, economie, armee, population, religion, politique, nom, Color.GRAY, "");
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public Konungr getKonungr()                  { return konungr; }
    public Economie getEconomie()                { return economie; }
    public Armee getArmee()                      { return armee; }
    public Population getPopulation()            { return population; }
    public ArrayList<Evenement> getEvenements()  { return evenements; }
    public Religion getReligion()                { return religion; }
    public ArrayList<Relation> getRelations()    { return relations; }
    public Politique getPolitique()              { return politique; }
    public String getNom()                       { return nom; }
    public ArrayList<Region> getRegions()        { return regions; }
    public Color getCouleur()                    { return couleur; }
    public String getIcone()                     { return icone; }

    /**
     * Retourne le nom affiché avec l'icône.
     * Ex : "⚔️ Vikings", "🏰 Anglo-Saxons"
     *
     * @return nom affiché avec icône
     */
    public String getNomAffiche() {
        return (icone != null && !icone.isEmpty()) ? icone + " " + nom : nom;
    }

    // ── Setters ──────────────────────────────────────────────────────────

    public void setKonungr(Konungr chef)              { this.konungr = chef; }
    public void setEconomie(Economie economie)        { this.economie = economie; }
    public void setArmee(Armee armee)                 { this.armee = armee; }
    public void setPopulation(Population population)  { this.population = population; }
    public void setReligion(Religion religion)        { this.religion = religion; }
    public void setPolitique(Politique politique)     { this.politique = politique; }
    public void setNom(String nom)                    { this.nom = nom; }
    public void setCouleur(Color couleur)             { this.couleur = couleur; }
    public void setIcone(String icone)                { this.icone = icone; }

    // ── Méthodes utilitaires ──────────────────────────────────────────────

    public int getNombreRegions()   { return regions.size(); }
    public int getNombreRelations() { return relations.size(); }
    public int getNbEvenements()    { return evenements.size(); }

    public void ajouterEvenement(Evenement even) {
        if (!this.evenements.contains(even)) this.evenements.add(even);
    }

    public void ajouterRelation(Relation rel) {
        if (!this.relations.contains(rel)) this.relations.add(rel);
    }

    public void supprimerRelation(Relation rel) { this.relations.remove(rel); }

    public void ajouterRegion(Region reg) {
        if (!this.regions.contains(reg)) this.regions.add(reg);
    }

    public void supprimerRegion(Region reg) { this.regions.remove(reg); }

    public Evenement getEvenement(int indice) {
        return (indice < 0 || indice >= getNbEvenements()) ?
                SimFactory.buildEvenement("", 0, 0, null, "", "") :
                evenements.get(indice);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(" Civilisation : ").append(getNomAffiche()).append(" \n");
        str.append("Konungr : ").append(this.getKonungr()).append("\n");
        str.append("\n Économie \n").append(this.economie.toString());
        str.append("\n Armée ---\n").append(this.armee.toString());
        str.append("\n--- Population ---\n").append(this.population.toString());
        str.append("\n--- Religion ---\n").append(this.religion.toString());
        str.append("\n--- Politique ---\n").append(this.politique.toString());
        str.append("\n--- Régions (").append(this.regions.size()).append(") ---\n");
        for (Region r : this.regions) str.append(r.toString()).append("\n");
        str.append("\n--- Relations (").append(this.relations.size()).append(") ---\n");
        for (Relation r : this.relations) str.append(r.toString()).append("\n");
        str.append("\n--- Événements (").append(this.evenements.size()).append(") ---\n");
        for (Evenement e : this.evenements) str.append(e.toString()).append("\n");
        return str.toString();
    }
}