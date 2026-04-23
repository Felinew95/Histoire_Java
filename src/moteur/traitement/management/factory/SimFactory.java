package moteur.traitement.management.factory;

import moteur.donnees.*;

import java.awt.Color;

import org.apache.log4j.Logger;

import log.LoggerUtility;

/**
 * Classe utilitaire pour la construction des objets utilisés dans la simulation.
 *
 * <p>
 * Cette classe centralise la création des entités de la simulation.
 * Chaque méthode de construction enregistre ses opérations via un {@link Logger} HTML
 * pour permettre de suivre la création des entités pendant l’exécution.
 * </p>
 *
 * @author Alexandre
 * @version 1.1
 *
 * @see Civilisation
 * @see Armee
 * @see Economie
 * @see Population
 * @see Religion
 * @see Politique
 * @see Produit
 * @see Region
 * @see Ressource
 * @see Relation
 * @see Evenement
 * @see Konungr
 * @see Kersir
 */
public class SimFactory {

	/**
	 * Logger HTML pour suivre la construction des entités
	 */
	private static final Logger logger = LoggerUtility.getLogger(SimFactory.class, "html");

	/**
     * Construit une civilisation complète avec couleur et icône.
     *
     * @param konungr    Chef de la civilisation
     * @param economie   Économie de la civilisation
     * @param armee      Armée de la civilisation
     * @param population Population de la civilisation
     * @param religion   Religion de la civilisation
     * @param politique  Politique de la civilisation
     * @param nom        Nom de la civilisation
     * @param couleur    Couleur représentative sur la carte
     * @param icone      Icône ou emoji de la civilisation
     * @return La civilisation construite
     */
    public static Civilisation buildCivilisation(Konungr konungr, Economie economie, Armee armee,
                                                  Population population, Religion religion,
                                                  Politique politique, String nom,
                                                  Color couleur, String icone) {
        logger.info("Construction de la civilisation : nom=" + nom + ", konungr=" + konungr
                + ", couleur=" + couleur + ", icone=" + icone);
        return new Civilisation(konungr, economie, armee, population, religion, politique, nom,
                couleur, icone);
    }
 
    /**
     * Construit une civilisation sans couleur ni icône — compatibilité avec l'existant.
     *
     * @param konungr    Chef de la civilisation
     * @param economie   Économie de la civilisation
     * @param armee      Armée de la civilisation
     * @param population Population de la civilisation
     * @param religion   Religion de la civilisation
     * @param politique  Politique de la civilisation
     * @param nom        Nom de la civilisation
     * @return La civilisation construite
     */
    public static Civilisation buildCivilisation(Konungr konungr, Economie economie, Armee armee,
                                                  Population population, Religion religion,
                                                  Politique politique, String nom) {
        logger.info("Construction de la civilisation : nom=" + nom + ", konungr=" + konungr);
        return new Civilisation(konungr, economie, armee, population, religion, politique, nom);
    }
    
    
	/**
	 * Construit un objet {@link Konungr} avec les informations fournies.
	 *
	 * <p>Cette méthode enregistre également un message dans le logger pour indiquer
	 * la création du Konungr avec son nom et ses années de règne.</p>
	 *
	 * @param nom        le nom du Konungr
	 * @param anneeDebut l'année de début du règne
	 * @param anneeFin   l'année de fin du règne
	 * @return un nouvel objet {@link Konungr} correspondant aux paramètres donnés
	 */
	public static Konungr buildKonungr(String nom, int anneeDebut, int anneeFin) {
		logger.info("Construction du konungr : nom=" + nom + ", anneeDebut=" + anneeDebut + ", anneeFin=" + anneeFin);
		return new Konungr(nom, anneeDebut, anneeFin);
	}

	/**
	 * Construit un objet {@link Kersir} avec les informations fournies.
	 *
	 * <p>Cette méthode enregistre également un message dans le logger pour indiquer
	 * la création du Kersir avec son nom et ses années d'activité.</p>
	 *
	 * @param nom        le nom du Kersir
	 * @param anneeDebut l'année de début d'activité
	 * @param anneeFin   l'année de fin d'activité
	 * @return un nouvel objet {@link Kersir} correspondant aux paramètres donnés
	 */
	public static Kersir buildKersir(String nom, int anneeDebut, int anneeFin) {
		logger.info("Construction du kersir : nom=" + nom + ", anneeDebut=" + anneeDebut + ", anneeFin=" + anneeFin);
		return new Kersir(nom, anneeDebut, anneeFin);
	}

    /**
     * Méthode qui construit une politique pour la civilisation 
     * 
     * @param typeRegime  Type de régime politique
     * @param stabilite Stabilité politique
     * 
     * @return La politique de civilisation 
     */
	public static Politique buildPolitique(String typeRegime, float stabilite) {
		logger.info("Construction de la politique : typeRegime=" + typeRegime + ", stabilite=" + stabilite);
		return new Politique(typeRegime, stabilite);
	}

	/**
	 * Méthode qui construit une religion pour la civilisation 
	 * 
	 * @param nom  Nom de la religion
     * @param croyance  Croyance de la religion
     * @param influence  Influence de la religion
	 * 
	 * @return la religion de la civilisation
	 */
	public static Religion buildReligion(String nom, String croyance, float influence) {
		logger.info("Construction de la religion : nom=" + nom + ", croyance=" + croyance + ", influence=" + influence);
		return new Religion(nom, croyance, influence);
	}

	/**
	 * Méthode qui construit la population de la civilisation 
	 * 
	 * @param nbHabitants      Nombre d'habitant
     * @param ageMoyen         Age moyen de la population
     * @param sexeMajoritaire  Sexe majoritaire de la population
     * 
	 * @return La population de la civilisation 
	 */
	public static Population buildPopulation(int nbHabitants, float ageMoyen, String sexeMajoritaire) {
		logger.info("Construction de la population : nbHabitants=" + nbHabitants + ", ageMoyen=" + ageMoyen + ", sexeMajoritaire=" + sexeMajoritaire);
		return new Population(nbHabitants, ageMoyen, sexeMajoritaire);
	}

	/**
	 * Méthode qui construit l'armee pour la civilisation 
	 * 
	 * @param hersir  Chef principal de l'armée
	 * @param nombreGuerriers  Nombre de guerriers
     * @param nombreNavires Nombre de navires
     * @param techniquesMilitaire  Nombre de techniques militaires acquises
     * @param etat  État de l'armée
     * 
	 * @return L'armee de la civilisation
	 */
	public static Armee buildArmee(Kersir hersir, int nombreGuerriers, int nombreNavires, int techniquesMilitaire, float etat) {
		logger.info("Construction de l'armée : hersir=" + hersir + ", guerriers=" + nombreGuerriers + ", navires=" + nombreNavires + ", techniquesMilitaire=" + techniquesMilitaire + ", etat=" + etat);
		return new Armee(hersir, nombreGuerriers, nombreNavires, techniquesMilitaire, etat);
	}

	/**
	 * Méthode qui construit une économie pour la civilisation
	 * 
	 * @return L'économie de la civilisation
	 */
	public static Economie buildEconomie() {
		logger.info("Construction d'une nouvelle économie");
		return new Economie();
	}
	
	/**
	 * Méthode qui construit un produit
	 * 
	 * @param nom       Nom du produit
     * @param quantite  Quantité du produit
     * @param prix      Prix du produit
     * 
	 * @return Un produit 
	 */
	public static Produit buildProduit(String nom, int quantite, float prix) {
		logger.debug("Construction d'un produit : nom=" + nom + ", quantite=" + quantite + ", prix=" + prix);
		return new Produit(nom, quantite, prix);
	}
	
	/**
	 * Méthode qui construit une région 
	 * 
	 * @param nom   Nom de la région
     * @param chef  Chef de la région
	 * 
	 * @return Une région 
	 */
	public static Region buildRegion(String nom, String chef) {
		logger.info("Construction d'une région : nom=" + nom + ", chef=" + chef);
		return new Region(nom, chef);
	}
	
	/**
	 * Méthode qui construit une ressource 
	 * 
	 * @param nom     Nom de la ressource
     * @param type    Type de la ressource
     * @param quantite  La quantité de cette ressource
	 * 
	 * @return Une ressource
	 */
	public static Ressource buildRessource(String nom, String type, int quantite, float cout) {
		logger.debug("Construction d'une ressource : nom=" + nom + ", type=" + type + ", cout=" + cout +  ", quantite=" + quantite);
		return new Ressource(nom, type, cout, quantite);
	}
	
	/**
	 * Méthode qui créer une relation 
	 * 
	 * @param nomCivilisation  Nom de la civilisation
	 * @param niveau  Niveau de relation
	 * 
	 * @return Une relation
	 */
	public static Relation buildRelation(String nomCivilisation, float niveau) {
		logger.info("Construction d'une relation : civilisation=" + nomCivilisation + ", niveau=" + niveau);
		return new Relation(nomCivilisation, niveau);
	}
	
	/**
	 * Méthode qui crée un événement 
	 * 
	 * @param nom             Nom de l'événement
     * @param anneeDebut      Année du début de l'événement
     * @param anneeFin 		  Année de fin de l'événement
     * @param region          Région de l'évenement
     * @param narration       Narration de l'événement
     * @param type            Type d'événement
	 * 
	 * @return Un événement 
	 */
	public static Evenement buildEvenement(String nom, int anneeDebut, int anneeFin, Region region, String narration, String type) {
		logger.info("Construction d'un événement : nom=" + nom + ", anneeDebut=" + anneeDebut + ", anneeFin="+ anneeFin + ", region=" + region.getNom() + ", type=" + type);
		return new Evenement(nom, anneeDebut, anneeFin, region, narration, type);
	}

}