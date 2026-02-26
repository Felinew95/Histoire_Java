package moteur.traitement;

import config.SimConfig;

import moteur.carte.Carte;

import moteur.donnees.simulation.Armee;
import moteur.donnees.simulation.Civilisation;
import moteur.donnees.simulation.Economie;
import moteur.donnees.simulation.Politique;
import moteur.donnees.simulation.Population;
import moteur.donnees.simulation.Religion;

public class SimBuilder {
    
    public static Carte buildMap() {
        return new Carte(SimConfig.NOMBRE_LIGNES, SimConfig.NOMBRE_COLONNES);
    }

    public static Civilisation buildCivilisation() {
    	return new Civilisation("", new Economie(), new Armee("", 0, 0, 0, 0), 
    			new Population(0, 0, null), new Religion(null, null, 0), new Politique(null, 0), "Viking");
    }
    
}
