package moteur.traitement;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import moteur.carte.Ile;
import moteur.donnees.Region;

/**
 * Classe qui construit l'ile de lindsfarne
 * 
 * @author Alexandre et Massinissa
 * @version 1.0
 */
public class IleBuilder {

    /**
     * Méthode qui construit une ile
     * 
     * @param fichier   : Nom du fichier csv contenant les blocs
     * @param nomRegion : Nom de la région d'appartenance
     * @param chef      : Chef de la région
     * @param nomIle    : Nom de l'ile
     * @return La représentation de l'ile
     * 
     * @throws IOException
     */
    private static Ile buildIle(String fichier, String nomRegion, String chef, String nomIle, Point positionIle) throws IOException {
        Ile ile;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            br.readLine(); // Saute la tête (ligne, colonne, type)
            Region region = SimFactory.buildRegion(nomIle, chef);
            ile = SimFactory.buildIle(nomIle, positionIle, region);
            ajoutBlocsIle(ile, br);
        }

        return ile;
    }

    /**
     * Méthode qui ajoute des bloc à l'ile 
     * 
     * @param ile : Une ile 
     * @param br  : Lit le fichier texte
     * 
     * @throws IOException
     */
	private static void ajoutBlocsIle(Ile ile, BufferedReader br) throws IOException {
		String ligne;
		String[] valeurs;
		
		while ((ligne = br.readLine()) != null) {
		    valeurs = ligne.split(",");
		    ile.ajouterBloc(SimFactory.buildBloc(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]), valeurs[2]));
		}
	}
    	
    /**
     * Méthode qui construit l'ile de lindsfarne
     * 
     * @return L'ile de lindsfarne
     */
    public static Ile buildLindsfarne() {
        try {
            return buildIle("src/csv/cartes/carte_lindsfarne.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit l'europe du nord 
     * 
     * @return L'europe du nord
     */
    public static Ile buildEuropeduNord() {
        try {
            return buildIle("src/csv/cartes/europe_nord.csv", "EuropeDuNord", "Liu", "EuropeDuNord", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit le vinland
     * 
     * @return Le vinland
     */
    public static Ile buildVinland() {
        try {
            return buildIle("src/csv/cartes/carte_vinland.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de StamfordBridge
     * 
     * @return L'ile de lindsfarne
     */
    public static Ile buildStamfordBridge() {
        try {
            return buildIle("src/csv/cartes/carte_stamford_bridge.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de l'europe de l'ouest
     * 
     * @return L'europe de l'ouest
     */
    public static Ile buildEuropeOuest() {
        try {
            return buildIle("src/csv/cartes/carte_raids_europe_ouest.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de la normandie
     * 
     * @return La normandie
     */
    public static Ile buildNormandie() {
        try {
            return buildIle("src/csv/cartes/carte_normandie.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de mort_knut
     * 
     * @return La carte de mort knut
     */
    public static Ile buildMortKnut() {
        try {
            return buildIle("src/csv/cartes/carte_mort_knut.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de l'islande
     * 
     * @return La carte de l'islande
     */
    public static Ile buildIslande() {
        try {
            return buildIle("src/csv/cartes/carte_islande.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    } 
    
    /**
     * Méthode qui construit la carte l'irlande et de l'écosse
     * 
     * @return La carte de l'irlande et de l'écosse
     */
    public static Ile buildIrlandeEcosse() {
        try {
            return buildIle("src/csv/cartes/carte_irlande_ecosse.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte du groenland
     * 
     * @return La carte du groenland
     */
    public static Ile buildGroenland() {
        try {
            return buildIle("src/csv/cartes/carte_groenland.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de l'angleterre
     * 
     * @return La carte de l'angleterre
     */
    public static Ile buildEngland() {
        try {
            return buildIle("src/csv/cartes/carte_grande_armee_england.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte du Danelaw York
     * 
     * @return La carte du Danelaw York
     */
    public static Ile buildDanelawYork() {
        try {
            return buildIle("src/csv/cartes/carte_danelaw_york", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de la scandinavie
     * 
     * @return La carte de l'alfred wessex
     */
    public static Ile buildScandinave() {
        try {
            return buildIle("src/csv/cartes/carte_conversion_scandinave.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte baltique
     * 
     * @return La carte baltique
     */
    public static Ile buildBaltique() {
        try {
            return buildIle("src/csv/cartes/carte_commerce_baltique.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de dublin
     * 
     * @return La carte de dublin
     */
    public static Ile buildClontarfDublin() {
        try {
            return buildIle("src/csv/cartes/carte_clontarf_dublin.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Méthode qui construit la carte de l'alfred wessex
     * 
     * @return La carte de l'alfred wessex
     */
    public static Ile buildAlfredWessex() {
        try {
            return buildIle("src/csv/cartes/carte_alfred_wessex.csv", "Angleterre", "Liu", "Lindsfarne", 
            		new Point(800, 500));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
