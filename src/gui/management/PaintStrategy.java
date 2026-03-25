package gui.management;

import config.SimConfig;
import config.SpritesConfig;

import gui.mobiles.Mobile;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import moteur.carte.Bloc;
import moteur.carte.Carte;
import moteur.traitement.management.managers.MobileManager;

/**
 * Classe qui permet de peindre les différents composants graphiques
 * 
 * @author Alexandre, Tauseef et Massinissa
 * @version 1.0
 */
public class PaintStrategy {

	// Attributs
    private final SpritesConfig sprites = new SpritesConfig("src/images");
    private int tailleBloc = SimConfig.TAILLE_BLOC_EUROPE;
    
    private static final String[] NOMS_REGIONS   = {
            "Vinland", "Islande", "Scandinavie", "Grande-Bretagne", "Lindisfarne", "Normandie"
        };
    private static final int[] COLS_REGIONS = { 3,  9, 95, 52, 62, 70 };
    private static final int[] LIGS_REGIONS = { 3,  3,  8, 35, 30, 43 };
    
    private boolean estEurope = true;
    
    /**
     * Constructeur de la classe PaintStrategy
     * 
     * @param simulation : Le coeur de la simulation
     */
    public PaintStrategy() {
		super();
	}

	/**
     * Méthode qui permet d'afficher la carte
     * 
     * @param carte    : Carte de la simulation 
     * @param graphics : Composant graphique
     */
    public void peindreCarte(Carte carte, Graphics graphics) {
        Bloc[][] blocs = carte.getBlocs();

        int temps = (int) System.currentTimeMillis() / 500;
        BufferedImage imageEau = (temps % 2 == 0) ? this.sprites.getImage("eau1.png"): this.sprites.getImage("eau2.png");
        
        BufferedImage imageTerre    = this.sprites.getImage("terre.png");
        BufferedImage imageHerbe    = this.sprites.getImage("herbe.png");
        BufferedImage imageArbre    = this.sprites.getImage("tree.png"); 
        BufferedImage imagePlage    = this.sprites.getImage("sable.png");
        BufferedImage imageMontagne = this.sprites.getImage("montagne.png");
        
        Graphics2D g = (Graphics2D) graphics; 
        for (int lineIndex = 0; lineIndex < carte.getNbLignes(); lineIndex++) {
            for (int columnIndex = 0; columnIndex < carte.getNbColonnes(); columnIndex++) {
                Bloc bloc = blocs[lineIndex][columnIndex];
                peindreBloc(imageEau, imageTerre, imageHerbe, imageArbre, imagePlage, imageMontagne, g, bloc); 
            }
        } 
        
        if (estEurope) {
            peindreLabelsRegions(g);
        }
    }

    /**
     * Méthode qui permet d'afficher graphiquement un bloc 
     * 
     * @param imageEau      : Image d'un bloc d'eau
     * @param imageTerre    : Image d'un bloc de terre
     * @param imageHerbe    : Image d'un bloc d'herbe
     * @param imageArbre    : Image d'un bloc arbre
     * @param imagePlage    : Image d'un bloc de sable
     * @param imageMontagne : Image d'un bloc de roche
     * @param g  			: Composant graphique 
     * @param bloc			: Le bloc à peindre
     */
	private void peindreBloc(BufferedImage imageEau, BufferedImage imageTerre, BufferedImage imageHerbe,
			BufferedImage imageArbre, BufferedImage imagePlage, BufferedImage imageMontagne, Graphics2D g, Bloc bloc) {
		
		String typeBloc = bloc.getTypeBloc();
		switch (typeBloc) {
			case "EAU":
				g.drawImage(imageEau, bloc.getColonne() * tailleBloc, bloc.getLigne() * tailleBloc,
		                tailleBloc, tailleBloc, null);
				break;
		    case "ARBRE":
			    	g.drawImage(imageHerbe, bloc.getColonne()*tailleBloc, bloc.getLigne()*tailleBloc, tailleBloc,
			                tailleBloc, null);
			    	g.drawImage(imageArbre, bloc.getColonne()*tailleBloc, bloc.getLigne()*tailleBloc, tailleBloc,
			   			 tailleBloc, null);
			    	break;
		    case "HERBE":
			    	g.drawImage(imageHerbe, bloc.getColonne()*tailleBloc, bloc.getLigne()*tailleBloc, tailleBloc,
			                tailleBloc, null);
			    	break;
		    case "PLAGE":
			    	g.drawImage(imagePlage, bloc.getColonne()*tailleBloc, bloc.getLigne()*tailleBloc, tailleBloc,
			                tailleBloc, null);
			    	break;
		    case "MONTAGNE":
			    	g.drawImage(imageMontagne, bloc.getColonne()*tailleBloc, bloc.getLigne()*tailleBloc, tailleBloc,
			                tailleBloc, null);
			    	break;
		}
		
	}
  
	/**
	 * Méthode qui permet de peindre les mobiles 
	 * 
	 * @param mobileManager : Le manager des mobiles 
	 * @param graphics      : Composant graphique
	 */
    public void peindreMobiles(MobileManager mobileManager, Graphics graphics, int anneeSim) {
    	Graphics2D g = (Graphics2D) graphics;
	    	 
    	int temps = (int) System.currentTimeMillis() / 200;
	    BufferedImage imageNavire     = this.sprites.getImage("navire.png");
	    BufferedImage imagePersonnage = (temps % 2 == 0) ? this.sprites.getImage("perso1.png"): this.sprites.getImage("perso2.png");
	 	
	    for (Mobile mobile : mobileManager) {
	    	if (mobile.estActif(anneeSim)) {
				peindreMobile(g, imagePersonnage, imageNavire, mobile, mobile.getType());
	    	}
	    }
    }

    /**
     * Méthode qui permet de peindre un mobiile 
     * 
     * @param g               : Composant graphique 
     * @param imagePersonnage : Image d'un personnage
     * @param imageNavire     : Image d'un navire 
     * @param mobile          : Le mobile à peindre 
     * @param typeMobile	  : Le type du mobile 
     */
	private void peindreMobile(Graphics2D g, BufferedImage imagePersonnage, BufferedImage imageNavire, Mobile mobile, String typeMobile) {
		switch (typeMobile) {
			case "NAVIRE":
		 		g.drawImage(imageNavire, mobile.getColonne()*tailleBloc, mobile.getLigne()*tailleBloc, tailleBloc,
		                tailleBloc, null);
		 		break;
		 	case "PERSONNAGE":
		 			g.drawImage(imagePersonnage, mobile.getColonne()*tailleBloc, mobile.getLigne()*tailleBloc, tailleBloc,
		                    tailleBloc, null);
		 		break;
		 }
	}

    /**
     * Données des régions navigables : nom affiché, colonne et ligne (en blocs)
     * correspondant à un point de terre sur la carte Europe (TAILLE_BLOC = 10).
     *
     * Coordonnées vérifiées sur carte_europe.csv :
     *   Vinland        — masse col 1-7,  lig 1-9  → label col 3, lig 3
     *   Islande        — masse col 5-29, lig 1-10 → label col 9, lig 3
     *   Scandinavie    — masse col 86-134,lig 0-39→ label col 95, lig 8
     *   Grande-Bretagne— masse col 48-72,lig 22-48→ label col 52, lig 35
     *   Lindisfarne    — côte est GB col 62-70,
     *                    lig 27-35               → label col 62, lig 30
     *   Normandie      — masse col 65-95,lig 35-55→ label col 70, lig 43
     */

    /**
     * Peint tous les labels de navigation sur la carte Europe.
     * Style : parchemin doré avec bordure enluminée, police serif italique,
     * ombre portée sombre, animation de pulsation lumineuse.
     *
     * @param g : Contexte graphique
     */
    private void peindreLabelsRegions(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,     RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(SimConfig.FONT_LABEL_SIM);
        
        long temps = System.currentTimeMillis();
        for (int i = 0; i < NOMS_REGIONS.length; i++) {
            peindreLabel(g, NOMS_REGIONS[i], COLS_REGIONS[i], LIGS_REGIONS[i], i, temps);
        }
    }

    /**
     * Peint un label cartographique médiéval animé à la position (col, lig) en blocs.
     *
     * Rendu en 4 couches :
     *   1. Halo lumineux pulsé (AlphaComposite, sin du temps)
     *   2. Fond parchemin semi-transparent (RoundRectangle2D)
     *   3. Bordure enluminée dorée sur fond sombre
     *   4. Texte serif italique doré avec ombre portée
     *
     * @param g    : Contexte graphique
     * @param nom  : Nom de la région
     * @param col  : Colonne en blocs
     * @param lig  : Ligne en blocs
     * @param idx  : Index de la région (décale la phase d'animation)
     */
    private void peindreLabel(Graphics2D g, String nom, int col, int lig, int idx, long temps) {
        // --- Police historique ---
    		FontMetrics fm = g.getFontMetrics();

        int txtW  = fm.stringWidth(nom);
        int txtH  = fm.getAscent();
        int padX  = 6;
        int padY  = 4;
        int boxW  = txtW + padX * 2;
        int boxH  = txtH + padY * 2;

        // Pixel top-left du label centré sur la position bloc
        int cx  = col * tailleBloc;
        int cy  = lig * tailleBloc;
        int bx  = cx - boxW / 2;
        int by  = cy - boxH / 2;
        
        int tx = bx + padX;
        int ty = by + padY + txtH - 1;

        // --- Animation : pulsation dorée (phase décalée par idx) ---
        double phase   =  temps / 600.0 + idx * 1.1;
        float  alpha   = 0.35f + 0.20f * (float) Math.sin(phase);
        float  glow    = 0.18f + 0.12f * (float) Math.sin(phase * 1.3);

        // 1. Halo extérieur pulsé
        creerHalo(g, boxW, boxH, bx, by, glow);

        // 2. Fond parchemin semi-transparent
        creerFond(g, boxW, boxH, bx, by, alpha);

        // 3. Bordure enluminée : contour sombre épais + filet doré intérieur
        creerBordure(g, boxW, boxH, bx, by);

        // 4a. Ombre portée du texte
        creerOmbre(g, nom,  SimConfig.FONT_LABEL_SIM, tx, ty, new Color(10, 5, 0), 0.85f);

        // 4b. Texte doré principal
        creerTexte(g, nom, tx, ty, new Color(255, 215, 60));

        // Reset composite
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    /**
     * Méthode qui permet de créer le texte 
     * 
     * @param g   : Composant graphique 
     * @param nom : Nom du label 
     * @param tx  : Taille en x 
     * @param ty  : Taille en y 
     */
	private void creerTexte(Graphics2D g, String nom, int tx, int ty, Color couleur) {
		g.setColor(couleur);
        g.drawString(nom, tx, ty);
	}
	
	/**
	 * Méthode qui permet de créer une ombre 
	 * 
	 * @param g         : Composant graphique
	 * @param nom       : Nom du label 
	 * @param fontTitre : Font du titre 
	 * @param tx        : Taille en x 
	 * @param ty		: Taille en y
	 * @param alpha 	: alpha compris entre 0 et 1
	 */
	private void creerOmbre(Graphics2D g, String nom, Font fontTitre, int tx, int ty, Color couleur, float alpha) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
		g.setFont(fontTitre);
        g.setColor(couleur);
        
        g.drawString(nom, tx + 1, ty + 1);
	}

	/**
	 * Méthode qui permet de créer les bordures 
	 * 
	 * @param g    : Composant graphique 
	 * @param boxW : Longueur du rectangle  
	 * @param boxH : Hauteur du rectangle
	 * @param bx   : Position en x du rectangle
	 * @param by   : Position en y du rectangle
	 */
	private void creerBordure(Graphics2D g, int boxW, int boxH, int bx, int by) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.90f));
        
		g.setStroke(new BasicStroke(2.0f));
        g.setColor(new Color(20, 10, 0));
        g.draw(new RoundRectangle2D.Float(bx, by, boxW, boxH, 6, 6));
        
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(new Color(200, 160, 40));
        g.draw(new RoundRectangle2D.Float(bx + 2, by + 2, boxW - 4, boxH - 4, 4, 4));
	}

	/**
	 * Méthode qui permet de créer le fond 
	 * 
	 * @param g    : Composant graphique 
	 * @param boxW : Longueur du rectangle  
	 * @param boxH : Hauteur du rectangle
	 * @param bx   : Position en x du rectangle
	 * @param by   : Position en y du rectangle
	 * @param alpha : alpha compris entre 0 et 1
	 */
	private void creerFond(Graphics2D g, int boxW, int boxH, int bx, int by, float alpha) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha + 0.35f));
        g.setColor(new Color(40, 20, 5));
        g.fill(new RoundRectangle2D.Float(bx, by, boxW, boxH, 6, 6));
	}

	/**
	 * Méthode qui permet de créer un halo 
	 * 
	 * @param g    : Composant graphique 
	 * @param boxW : Longueur du rectangle  
	 * @param boxH : Hauteur du rectangle
	 * @param bx   : Position en x du rectangle
	 * @param by   : Position en y du rectangle
	 * @param glow : Effet de brillance
	 */
	private void creerHalo(Graphics2D g, int boxW, int boxH, int bx, int by, float glow) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glow));
        g.setColor(new Color(255, 210, 60));
        g.fill(new RoundRectangle2D.Float(bx - 4, by - 4, boxW + 8, boxH + 8, 10, 10));
	}

    /**
     * Getter de tailleBloc
     * 
     * @return La taille d'un bloc 
     */
	public int getTailleBloc() {
		return tailleBloc;
	}

	/**
	 * Setter de tailleBloc
	 * 
	 * @param tailleBloc : Nouvelle taille d'un bloc
	 */
	public void setTailleBloc(int tailleBloc) {
		this.tailleBloc = tailleBloc;
	}

	/**
	 * Getter de estEurope
	 * 
	 * @return Etat si la carte est l'europe ou non
	 */
	public boolean estEurope() {
		return estEurope;
	}

	/**
	 * Setter de estEurope
	 * 
	 * @param estEurope : Nouvel état si la carte est l'europe ou non
	 */
	public void setEstEurope(boolean estEurope) {
		this.estEurope = estEurope;
	}
    
}