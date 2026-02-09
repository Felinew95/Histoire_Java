package exceptions;

/**
 * Classe d'exception levée lorsqu'un produit en production n'existe pas
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Exception
 */
public class ProduitEnProductionNotFoundException extends Exception {

    /**
     * Constructeur de la classe d'exception ProduitEnProductionNotFoundException
     * @param message : Message d'exception levé lorsqu'un produit en production n'existe pas
     */
    public ProduitEnProductionNotFoundException(String message) {
        super(message);
    }

}
