package exceptions;

/**
 * Classe d'exception levée lorsqu'un produit en exportation n'existe pas
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Exception
 */
public class ProduitEnExportationNotFoundException extends Exception {

    /**
     * Constructeur de la classe d'exception ProduitEnExportationNotFoundException
     * @param message : Message d'exception levé lorsqu'un produit en exportation n'existe pas
     */
    public ProduitEnExportationNotFoundException(String message) {
        super(message);
    }

}
