package exceptions;

/**
 * Classe d'exception levée lorsqu'une une ressource importée n'existe pas
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Exception
 */
public class RessourceImporteeNotFoundException extends Exception {

    /**
     * Constructeur de la classe d'exception RessourceImporteeNotFoundException
     * 
     * @param message : Message d'exception levé lorsqu'une une ressource importée
     *                n'existe pas
     */
    public RessourceImporteeNotFoundException(String message) {
        super(message);
    }

}
