package gui.management;

import config.SimConfig;
import config.SpritesConfig;

import gui.mobiles.Mobile;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import moteur.carte.Bloc;
import moteur.carte.Carte;

/**
 * Stratégie de rendu graphique de la simulation.
 *
 * <p>
 * Cette classe est responsable de l'affichage de tous les éléments visuels :
 * <ul>
 *     <li>La carte (blocs de terrain : eau, herbe, montagne, etc.)</li>
 *     <li>Les entités mobiles (personnages, navires)</li>
 *     <li>Les labels des régions (uniquement pour la carte Europe)</li>
 * </ul>
 *
 * <p>
 * Elle utilise les ressources graphiques fournies par {@link SpritesConfig}
 * ainsi que les positions des régions via {@link RegionRepository}.
 * </p>
 *
 * <p>
 * Le rendu inclut des effets visuels avancés tels que :
 * animations (eau, personnages), halo lumineux, transparence et styles médiévaux.
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 * @author Massinissa
 *
 * @version 1.1
 */
public class PaintStrategy {

	/**
	 * Taille (en pixels) d'un bloc de la carte.
	 */
	private int tailleBloc = SimConfig.TAILLE_BLOC_EUROPE;

	/**
	 * Gestionnaire des sprites (images) utilisés pour le rendu.
	 */
	private final SpritesConfig sprites = SpritesConfig.getInstance();

	/**
	 * Repository contenant les positions des régions.
	 */
	private final RegionRepository regions = RegionRepository.getInstance();

	/**
	 * Indique si la carte affichée est l'Europe.
	 * Permet d'activer ou non l'affichage des labels des régions.
	 */
	private boolean estEurope = true;


	/**
     * Constructeur de la classe PaintStrategy
     */
    public PaintStrategy() {
		super();
	}

	/**
	 * Affiche la carte en dessinant chaque bloc.
	 *
	 * @param carte    carte contenant les blocs
	 * @param graphics contexte graphique
	 */
    public void paint(Carte carte, Graphics graphics) {
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
     * Dessine un bloc selon son type.
     *
     * @param imageEau       Image d'un bloc d'eau
     * @param imageTerre     Image d'un bloc de terre
     * @param imageHerbe     Image d'un bloc d'herbe
     * @param imageArbre     Image d'un bloc arbre
     * @param imagePlage     Image d'un bloc de sable
     * @param imageMontagne  Image d'un bloc de roche
     * @param g  			 Composant graphique
     * @param bloc			 Le bloc à peindre
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
	 * Affiche les mobiles actifs.
	 *
	 * @param mobileManager  Le manager des mobiles
	 * @param graphics       Composant graphique
	 */
    public void paint(MobileManager mobileManager, int anneeSim, Graphics graphics) {
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
     * Dessine un mobile.
     *
     * @param g                Composant graphique
     * @param imagePersonnage  Image d'un personnage
     * @param imageNavire      Image d'un navire
     * @param mobile           Le mobile à peindre
     * @param typeMobile	   Le type du mobile
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
     * Affiche les labels des régions.
     * @param g : Contexte graphique
     */
    private void peindreLabelsRegions(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,     RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(SimConfig.FONT_LABEL_SIM);

        long temps = System.currentTimeMillis();

		int i = 0;
        for (String nom : regions.getAllNomRegions()) {
			Point position = regions.getPosition(nom);
	        peindreLabel(g, nom, (int) position.getY(), (int) position.getX(), i, temps);
			i++;
        }
    }

    /**
     * Dessine un label stylisé.
	 *
     * @param g     Contexte graphique
     * @param nom   Nom de la région
     * @param col   Colonne en blocs
     * @param lig   Ligne en blocs
     * @param idx   Index de la région (décale la phase d'animation)
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
        creerOmbre(g, nom, tx, ty, new Color(10, 5, 0));

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
	 * @param g   : Composant graphique
	 * @param nom : Nom du label
	 * @param tx  : Taille en x
	 * @param ty  : Taille en y
	 */
	private void creerOmbre(Graphics2D g, String nom, int tx, int ty, Color couleur) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.85));

		g.setFont(SimConfig.FONT_LABEL_SIM);
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
	 * Setter de tailleBloc
	 *
	 * @param tailleBloc : Nouvelle taille d'un bloc
	 */
	public void setTailleBloc(int tailleBloc) {
		this.tailleBloc = tailleBloc;
	}

	/**
	 * Setter d'estEurope
	 *
	 * @param estEurope : Nouvel état si la carte est l'Europe ou non
	 */
	public void setEstEurope(boolean estEurope) {
		this.estEurope = estEurope;
	}

}