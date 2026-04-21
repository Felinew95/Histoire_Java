package moteur.donnees;

/**
 * Classe représentant un Kersir dans une civilisation.
 * <p>
 * Le Kersir est un chef local ou un notable, disposant d'une autorité
 * sur un territoire ou un groupe restreint. Il hérite des caractéristiques
 * communes définies dans la classe {@link Chef}, notamment son nom et sa
 * période de règne.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 *
 * @see Chef
 */
public class Kersir extends Chef {

    /**
     * Constructeur d'un Kersir.
     *
     * @param nom nom du chef
     * @param anneeDebut année de début du règne
     * @param anneeFin année de fin du règne
     */
    public Kersir(String nom, int anneeDebut, int anneeFin) {
        super(nom, anneeDebut, anneeFin);
    }

}