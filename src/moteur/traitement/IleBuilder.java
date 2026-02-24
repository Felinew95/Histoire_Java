package moteur.traitement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import moteur.carte.Bloc;
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
    private static Ile buildIle(String fichier, String nomRegion, String chef, String nomIle) throws IOException {
        Ile ile;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            br.readLine(); // Saute la tête (ligne, colonne, type)
            
            Region region = new Region(nomRegion, chef);
            ile = new Ile(nomIle, region);
            
            String ligne;
            String[] valeurs;
            
            while ((ligne = br.readLine()) != null) {
                valeurs = ligne.split(",");
                ile.ajouterBloc(new Bloc(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]), valeurs[2]));
            }
        }

        return ile;
    }

    /**
     * Méthode qui construit l'ile de lindsfarne
     * 
     * @return L'ile de lindsfarne
     */
    public static Ile buildIleLindsfarne() throws IOException {
        try {
            return buildIle("src/csv/lindsfarne_blocs.csv", "Angleterre", "Liu", "Lindsfarne");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
