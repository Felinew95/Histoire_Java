package moteur.donnees;

import java.util.HashMap;

/**
 * Classe qui représente une économie 
 * @author Alexandre et Massinissa
 * @version 1.0
 * 
 * @see Ressource
 */
public class Economie {

    // Attributs 
    private float stycas; 
    private HashMap<String, Integer> productions;
    private HashMap<String, Integer> exportations; 
    private HashMap<String, Ressource> ressourcesImportees;

}
