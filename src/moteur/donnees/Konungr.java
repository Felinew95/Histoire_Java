package moteur.donnees;

/**
 * Classe représentant un Konungr (roi) dans une civilisation.
 *
 * <p>
 * Le Konungr est un chef suprême, généralement à la tête du pouvoir
 * politique et militaire. Il hérite des caractéristiques communes
 * définies dans la classe {@link Chef}, notamment son nom et sa
 * période de règne.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 *
 * @see Chef
 */
public class Konungr extends Chef {

    /**
     * Constructeur d'un Konungr.
     *
     * @param nom nom du roi
     * @param anneeDebut année de début du règne
     * @param anneeFin année de fin du règne
     */
    public Konungr(String nom, int anneeDebut, int anneeFin) {
        super(nom, anneeDebut, anneeFin);
    }

}