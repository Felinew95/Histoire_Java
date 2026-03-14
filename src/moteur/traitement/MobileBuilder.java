package moteur.traitement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import gui.UIFactory;
import gui.mobiles.Action;
import gui.mobiles.Mobile;

/**
 * Classe qui permet de créer la liste des mobiles pour chaque région
 * 
 * @author Alexandre
 * @version 1.0
 */
public class MobileBuilder {
	
	/**
	 * Méthode qui construit un manager de mobiles 
	 * 
	 * @param fichier : Fichier texte 
	 * @return Le manager 
	 * 
	 * @throws IOException
	 */
	private static MobileManager buildMobileManager(String fichier) throws IOException {
		MobileManager manager = new MobileManager();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			String[] valeurs;
			
			while ((ligne = br.readLine()) != null) {
				valeurs = ligne.split(";");
				ajoutMobilesActions(manager, valeurs);
			}
           
		}
		
		return manager;
	}

	/**
	 * Méthode qui ajoute des mobiles et des actions au manager
	 * 
	 * @param manager : Manager des mobiles 
	 * @param valeurs : Valeurs 
	 */
	private static void ajoutMobilesActions(MobileManager manager, String[] valeurs) {
		switch (valeurs[0]) {
			case "MOBILE":
				ajouterMobile(manager, valeurs);
				break;
			case "ACTION":
				ajouterAction(manager, valeurs);
				break;
		}
	}

	/**
	 * Méthode qui ajoute une action à un mobile du manager
	 * 
	 * @param manager : Manager des mobiles 
	 * @param valeurs : Valeurs
	 */
	private static void ajouterAction(MobileManager manager, String[] valeurs) {
		Action a = UIFactory.buildAction(valeurs[2], Integer.parseInt(valeurs[3]), Integer.parseInt(valeurs[4]));
		manager.ajouterActionAMobile(Integer.parseInt(valeurs[1]), a);
	}

	/**
	 * Méthode qui ajoute un mobile au manager 
	 * 
	 * @param manager : Manager des mobiles 
	 * @param valeurs : Valeurs 
	 */
	private static void ajouterMobile(MobileManager manager, String[] valeurs) {
		int id = Integer.parseInt(valeurs[1]);
		String type = valeurs[2];
		int ligneM = Integer.parseInt(valeurs[3]);
		int colonneM = Integer.parseInt(valeurs[4]);
		
		Mobile m = UIFactory.buildMobile(id, type, ligneM, colonneM);
		manager.ajouterMobileEtActions(m, UIFactory.buildActions());
	}
	
	/**
	 * Méthode qui crée le manager de lindsfarne 
	 * 
	 * @return Le manager de lindsfarne
	 */
	public static MobileManager buildMobileManagerLindsfarne() {
		try {
			return buildMobileManager("src/csv/mobilesLindsfarne.csv");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
}
