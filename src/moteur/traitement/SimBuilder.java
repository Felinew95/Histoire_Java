package moteur.traitement;

import config.SimConfig;
import moteur.carte.Carte;

public class SimBuilder {
    
    public static Carte buildMap() {
        return new Carte(SimConfig.NOMBRE_LIGNES, SimConfig.NOMBRE_COLONNES);
    }

}
