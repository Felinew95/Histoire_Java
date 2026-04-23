package moteur.donnees;

import java.awt.Color;
import java.util.ArrayList;

import moteur.traitement.management.factory.SimFactory;

/**
 * Entité centrale représentant une Civilisation dans la simulation.
 * 
 * <p>
 * La classe {@code Civilisation} agit comme un agrégateur pour tous les domaines 
 * fonctionnels : démographie, force militaire, économie, foi et diplomatie. 
 * Elle maintient également l'historique des événements et la liste des régions contrôlées.
 * </p>
 * 
 * @author Massinissa
 * @version 1.2
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

    /** 
     * Le souverain actuel (Roi/Chef) dirigeant la civilisation. 
     */
    private Konungr konungr;

    /** 
     * Données liées à la gestion des ressources et du commerce. 
     */
    private Economie economie;

    /** 
     * Données liées à la puissance militaire et à la défense. 
     */
    private Armee armee;

    /** 
     * Statistiques démographiques (nombre d'habitants, âge moyen, etc.). 
     */
    private Population population;

    /** 
     * Liste chronologique des événements historiques ayant impacté la civilisation. 
     */
    private final ArrayList<Evenement> evenements = new ArrayList<>();

    /** 
     * État des croyances et influence religieuse actuelle. 
     */
    private Religion religion;

    /** 
     * Carnet diplomatique listant les rapports avec les autres puissances. 
     */
    private final ArrayList<Relation> relations = new ArrayList<>();

    /** 
     * Régime et orientations stratégiques de la civilisation. 
     */
    private Politique politique;

    /** 
     * Nom de la civilisation (ex: "Vikings", "Northumbrie"). 
     */
    private String nom;

    /** 
     * Liste des territoires géographiques administrés par cette civilisation. 
     */
    private final ArrayList<Region> regions = new ArrayList<>();

    /**
     * Couleur distinctive pour l'affichage graphique sur la carte. 
     * Utile pour différencier les zones de contrôle.
     */
    private Color couleur;

    /** 
     * Représentation visuelle (icône ou emoji) associée au nom. 
     * Ex : "⚔️" pour symboliser l'aspect guerrier des Vikings.
     */
    private String icone;

    /**
     * Constructeur complet permettant d'initialiser l'identité visuelle et les modules.
     *
     * @param konungr    Le souverain initial
     * @param economie   Le module économique initial
     * @param armee      L'armée de départ
     * @param population La population de départ
     * @param religion   La religion pratiquée
     * @param politique  Le régime politique
     * @param nom        Le nom officiel
     * @param couleur    La couleur d'identification
     * @param icone      Le symbole représentatif
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
     * Constructeur de compatibilité sans identité visuelle.
     * <p>Initialise la couleur en gris et l'icône comme une chaîne vide.</p>
     *
     * @param konungr    Le souverain initial
     * @param economie   Le module économique initial
     * @param armee      L'armée de départ
     * @param population La population de départ
     * @param religion   La religion pratiquée
     * @param politique  Le régime politique
     * @param nom        Le nom officiel
     */
    public Civilisation(Konungr konungr, Economie economie, Armee armee, Population population,
                        Religion religion, Politique politique, String nom) {
        this(konungr, economie, armee, population, religion, politique, nom, Color.GRAY, "");
    }

    /**
     * Récupère le souverain actuel de la civilisation.
     * @return Le {@link Konungr} (roi ou chef) en exercice.
     */
    public Konungr getKonungr() { 
        return konungr; 
    }

    /**
     * Accède aux données financières et commerciales.
     * @return L'objet {@link Economie} contenant les ressources et revenus.
     */
    public Economie getEconomie() { 
        return economie; 
    }

    /**
     * Récupère l'état des forces militaires.
     * @return L'instance {@link Armee} gérant les soldats et la puissance de frappe.
     */
    public Armee getArmee() { 
        return armee; 
    }

    /**
     * Accède aux statistiques de la population.
     * @return L'objet {@link Population} (nombre d'habitants, âge moyen, taux).
     */
    public Population getPopulation() { 
        return population; 
    }

    /**
     * Récupère l'historique complet des événements vécus par la civilisation.
     * @return Une {@link ArrayList} contenant tous les {@link Evenement} passés.
     */
    public ArrayList<Evenement> getEvenements() { 
        return evenements; 
    }

    /**
     * Récupère la religion d'État et son niveau d'influence.
     * @return L'objet {@link Religion} actuel.
     */
    public Religion getReligion() { 
        return religion; 
    }

    /**
     * Accède au carnet d'adresses diplomatiques.
     * @return Une {@link ArrayList} des {@link Relation} avec les autres civilisations.
     */
    public ArrayList<Relation> getRelations() { 
        return relations; 
    }

    /**
     * Récupère l'orientation politique et diplomatique actuelle.
     * @return L'objet {@link Politique} de la nation.
     */
    public Politique getPolitique() { 
        return politique; 
    }

    /**
     * Récupère le nom brut de la civilisation.
     * @return Le nom sous forme de {@link String} (ex: "Vikings").
     */
    public String getNom() { 
        return nom; 
    }

    /**
     * Récupère la liste des territoires sous contrôle.
     * @return Une {@link ArrayList} contenant les instances de {@link Region}.
     */
    public ArrayList<Region> getRegions() { 
        return regions; 
    }

    /**
     * Récupère la couleur associée à la civilisation pour l'interface graphique.
     * @return L'objet {@link Color} utilisé sur la carte.
     */
    public Color getCouleur() { 
        return couleur; 
    }

    /**
     * Récupère l'identifiant visuel symbolique.
     * @return L'icône ou l'émoji représentatif sous forme de {@link String}.
     */
    public String getIcone() { 
        return icone; 
    }

    /**
     * Formate le nom de la civilisation pour l'interface utilisateur.
     * <p>Exemple de retour : "⚔️ Vikings" ou "Northumbrie" (si pas d'icône).</p>
     *
     * @return Une chaîne combinant l'icône et le nom.
     */
    public String getNomAffiche() {
        return (icone != null && !icone.isEmpty()) ? icone + " " + nom : nom;
    }

    /**
     * Modifie le souverain actuel de la civilisation.
     * @param chef Le nouveau {@link Konungr} à placer à la tête de la nation.
     */
    public void setKonungr(Konungr chef) { 
        this.konungr = chef; 
    }

    /**
     * Met à jour le module économique. 
     * À utiliser lors d'une restructuration majeure des finances ou du commerce.
     * @param economie Le nouveau module {@link Economie}.
     */
    public void setEconomie(Economie economie) { 
        this.economie = economie; 
    }

    /**
     * Remplace l'armée actuelle. 
     * Permet de mettre à jour globalement les effectifs et la puissance militaire.
     * @param armee La nouvelle instance d'{@link Armee}.
     */
    public void setArmee(Armee armee) { 
        this.armee = armee; 
    }

    /**
     * Met à jour les données démographiques globales.
     * @param population La nouvelle instance de {@link Population}.
     */
    public void setPopulation(Population population) { 
        this.population = population; 
    }

    /**
     * Modifie la religion d'État de la civilisation. 
     * Peut impacter la ferveur et les relations avec les voisins.
     * @param religion La nouvelle {@link Religion}.
     */
    public void setReligion(Religion religion) { 
        this.religion = religion; 
    }

    /**
     * Change l'orientation politique ou le régime de la civilisation.
     * @param politique La nouvelle {@link Politique}.
     */
    public void setPolitique(Politique politique) { 
        this.politique = politique; 
    }

    /**
     * Définit ou modifie le nom officiel de la civilisation.
     * @param nom Le nouveau nom (ex: "Empire de la Mer du Nord").
     */
    public void setNom(String nom) { 
        this.nom = nom; 
    }

    /**
     * Modifie la couleur représentative sur les interfaces graphiques.
     * @param couleur La nouvelle {@link Color}.
     */
    public void setCouleur(Color couleur) { 
        this.couleur = couleur; 
    }

    /**
     * Change l'icône représentative (emoji ou symbole).
     * @param icone La nouvelle chaîne de caractères représentant l'icône.
     */
    public void setIcone(String icone) { 
        this.icone = icone; 
    }

    /** 
     * @return Le nombre de territoires possédés. 
     */
    public int getNombreRegions()   { return regions.size(); }

    /** 
     * @return Le nombre de puissances étrangères connues dans le carnet diplomatique. 
     */
    public int getNombreRelations() { return relations.size(); }

    /** 
     * @return Le nombre total d'événements enregistrés dans l'historique. 
     */
    public int getNbEvenements()    { return evenements.size(); }

    /**
     * Archive un nouvel événement dans l'historique de la civilisation.
     * @param even L'événement à ajouter.
     */
    public void ajouterEvenement(Evenement even) {
        if (!this.evenements.contains(even)) this.evenements.add(even);
    }

    /**
     * Ajoute un partenaire diplomatique.
     * @param rel La relation à établir.
     */
    public void ajouterRelation(Relation rel) {
        if (!this.relations.contains(rel)) this.relations.add(rel);
    }

    /**
     * Supprime un partenaire du carnet diplomatique.
     * @param rel La relation à retirer.
     */
    public void supprimerRelation(Relation rel) { this.relations.remove(rel); }

    /**
     * Intègre une nouvelle région sous l'autorité de la civilisation.
     * @param reg La région conquise ou colonisée.
     */
    public void ajouterRegion(Region reg) {
        if (!this.regions.contains(reg)) this.regions.add(reg);
    }

    /**
     * Retire une région (perte de territoire).
     * @param reg La région à retirer.
     */
    public void supprimerRegion(Region reg) { this.regions.remove(reg); }

    /**
     * Récupère un événement spécifique par son index.
     * <p>Sécurité : Retourne un événement vide via {@link SimFactory} si l'index est invalide.</p>
     *
     * @param indice L'index dans la liste.
     * @return L'événement correspondant ou un objet neutre.
     */
    public Evenement getEvenement(int indice) {
        return (indice < 0 || indice >= getNbEvenements()) ?
                SimFactory.buildEvenement("", 0, 0, null, "", "") :
                evenements.get(indice);
    }

    /**
     * Génère un rapport textuel complet de l'état de la civilisation.
     * @return Un récapitulatif détaillé incluant tous les modules et listes.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(" === Civilisation : ").append(getNomAffiche()).append(" ===\n");
        str.append("Souverain (Konungr) : ").append(this.getKonungr()).append("\n");
        str.append("\n[Économie]\n").append(this.economie.toString());
        str.append("\n[Armée]\n").append(this.armee.toString());
        str.append("\n[Population]\n").append(this.population.toString());
        str.append("\n[Religion]\n").append(this.religion.toString());
        str.append("\n[Politique]\n").append(this.politique.toString());
        str.append("\n--- Régions contrôlées (").append(this.regions.size()).append(") ---\n");
        for (Region r : this.regions) str.append("- ").append(r.toString()).append("\n");
        str.append("\n--- Relations diplomatiques (").append(this.relations.size()).append(") ---\n");
        for (Relation r : this.relations) str.append("- ").append(r.toString()).append("\n");
        str.append("\n--- Historique des événements (").append(this.evenements.size()).append(") ---\n");
        for (Evenement e : this.evenements) str.append("- ").append(e.toString()).append("\n");
        return str.toString();
    }
    
}