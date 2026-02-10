package moteur.donnees;
/**
 * Classe qui représente une relation entre deux civilisations
 * 
 * @author Alexandre et Massinissa
 * @version 1.0
 */
public class Relation {
    
    // Attributs
    private String nom_civilisation; // Nom de la civilisation avec laquelle on a une relation
    private float niveau; // Niveau de la relation (peut être de -100 à 100)
    
    /**
     * Constructeur de la classe Relation
     * 
     * @param nom_civilisation : Nom de la civilisation
     * @param niveau : Niveau de la relation
     */
    public Relation(String nom_civilisation, float niveau) {
        this.nom_civilisation = nom_civilisation;
        this.niveau = niveau;
    }
    
    /**
     * Getter de nom_civilisation
     * @return Le nom de la civilisation
     */
    public String getNom_civilisation() {
        return nom_civilisation;
    }
    
    /**
     * Getter de niveau
     * @return Le niveau de la relation
     */
    public float getNiveau() {
        return niveau;
    }
    
    /**
     * Setter de nom_civilisation
     * @param nom_civilisation : Nouveau nom de civilisation
     */
    public void setNom_civilisation(String nom_civilisation) {
        this.nom_civilisation = nom_civilisation;
    }
    
    /**
     * Setter de niveau
     * @param niveau : Nouveau niveau de relation
     */
    public void setNiveau(float niveau) {
        this.niveau = niveau;
    }
    
    /**
     * Retourne le type de relation selon le niveau
     * @return Le type de relation
     */
    public String getTypeRelation() {
        if (niveau >= 75) {
            return "Alliance";
        } else if (niveau >= 25) {
            return "Amitié";
        } else if (niveau >= -25) {
            return "Neutre";
        } else if (niveau >= -75) {
            return "Tension";
        } else {
            return "Guerre";
        }
    }
    
    /**
     * Améliore la relation
     * @param valeur : Valeur à ajouter au niveau
     */
    public void ameliorerRelation(float valeur) {
        this.niveau += valeur;
        if (this.niveau > 100) {
            this.niveau = 100;
        }
    }
    
    /**
     * Dégrade la relation
     * @param valeur : Valeur à retirer au niveau
     */
    public void degraderRelation(float valeur) {
        this.niveau -= valeur;
        if (this.niveau < -100) {
            this.niveau = -100;
        }
    }
    
    /**
     * Affiche les informations de la relation
     * @return L'affichage des informations
     */
    @Override
    public String toString() {
        return "Relation avec " + this.getNom_civilisation() + " : " + 
               this.getTypeRelation() + " (Niveau : " + this.getNiveau() + ")";
    }
}