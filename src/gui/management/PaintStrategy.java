package gui.management;

import config.SimConfig;
import config.SpritesRepository;
import gui.mobiles.Mobile;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import moteur.carte.Bloc;
import moteur.carte.Carte;
import utilitaire.DessinUtilitaire;

/**
 * Stratégie de rendu graphique de la simulation.
 * 
 * <p>
 * Cette classe centralise la logique d'affichage de la simulation, gérant le rendu
 * des tuiles du terrain, des entités mobiles et des indicateurs textuels (labels).
 * Elle implémente des effets visuels tels que l'animation de l'eau et des effets
 * de surbrillance (glow) sur les textes.
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 * @author Massinissa
 * 
 * @version 3.0
 */
public class PaintStrategy {
    
    /**
     * Taille d'un côté d'un bloc (tuile) en pixels. 
     */
    private int tailleBloc = SimConfig.TAILLE_BLOC_EUROPE;

    /** 
     * Instance du dépôt de sprites pour la récupération des textures. 
     */
    private final SpritesRepository sprites = SpritesRepository.getInstance();
    
    /** 
     * Instance du dépôt des régions pour localiser les labels. 
     */
    private final RegionRepository regions = RegionRepository.getInstance();
    
    /**
     * Flag déterminant si les noms des régions doivent être dessinés. 
     */
    private boolean estEurope = true;

    /**
     * Effectue le rendu de la carte de la simulation.
     * 
     * <p>
     * Calcule l'animation de l'eau en temps réel et itère sur la matrice de blocs
     * pour dessiner chaque tuile de terrain. Dessine également les labels si activés.
     * </p>
     *
     * @param carte    La structure de données contenant les blocs de la carte.
     * @param graphics Le contexte graphique {@link Graphics} utilisé pour le dessin.
     */
    public void paint(Carte carte, Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Bloc[][] blocs = carte.getBlocs();

        // Animation de l'eau : switch toutes les 500ms
        int temps = (int) System.currentTimeMillis() / 500;
        BufferedImage imageEau = (temps % 2 == 0) ? sprites.getImage("eau1.png") : sprites.getImage("eau2.png");
        
        BufferedImage imageTerre    = sprites.getImage("terre.png");
        BufferedImage imageHerbe    = sprites.getImage("herbe.png");
        BufferedImage imageArbre    = sprites.getImage("tree.png");
        BufferedImage imagePlage    = sprites.getImage("sable.png");
        BufferedImage imageMontagne = sprites.getImage("montagne.png");

        for (int lineIndex = 0; lineIndex < carte.getNbLignes(); lineIndex++) {
            for (int columnIndex = 0; columnIndex < carte.getNbColonnes(); columnIndex++) {
                Bloc blocActuel = blocs[lineIndex][columnIndex];
                peindreBloc(imageEau, imageTerre, imageHerbe, imageArbre,
                        imagePlage, imageMontagne, g, blocActuel);
            }
        }

        if (estEurope) {
            peindreLabelsRegions(g);
        }
    }

    /**
     * Dessine un bloc spécifique sur le contexte graphique selon son type.
     *
     * @param imageEau      Image de l'eau (animée).
     * @param imageTerre    Image de la terre brute.
     * @param imageHerbe    Image de l'herbe.
     * @param imageArbre    Image de l'arbre (superposée à l'herbe).
     * @param imagePlage    Image du sable.
     * @param imageMontagne Image de la montagne.
     * @param g             Le contexte {@link Graphics2D}.
     * @param bloc          L'instance du {@link Bloc} à dessiner.
     */
    private void peindreBloc(BufferedImage imageEau, BufferedImage imageTerre,
                              BufferedImage imageHerbe, BufferedImage imageArbre,
                              BufferedImage imagePlage, BufferedImage imageMontagne,
                              Graphics2D g, Bloc bloc) {
        
        int x = bloc.getColonne() * tailleBloc;
        int y = bloc.getLigne()   * tailleBloc;
        String typeDuBloc = bloc.getTypeBloc();

        switch (typeDuBloc) {
            case "EAU":
                g.drawImage(imageEau, x, y, tailleBloc, tailleBloc, null);
                break;
            case "ARBRE":
                g.drawImage(imageHerbe, x, y, tailleBloc, tailleBloc, null);
                g.drawImage(imageArbre, x, y, tailleBloc, tailleBloc, null);
                break;
            case "HERBE":
                g.drawImage(imageHerbe, x, y, tailleBloc, tailleBloc, null);
                break;
            case "PLAGE":
                g.drawImage(imagePlage, x, y, tailleBloc, tailleBloc, null);
                break;
            case "MONTAGNE":
                g.drawImage(imageMontagne, x, y, tailleBloc, tailleBloc, null);
                break;
            default:
                break;
        }
    }

    /**
     * Effectue le rendu des entités mobiles (personnages, navires).
     * 
     * <p>
     * Gère l'animation de marche des personnages (alternance de sprites)
     * et filtre les mobiles inactifs selon l'année de simulation.
     * </p>
     *
     * @param mobileManager Le gestionnaire contenant les entités à dessiner.
     * @param anneeSim      L'année courante de la simulation.
     * @param graphics      Le contexte graphique.
     */
    public void paint(MobileManager mobileManager, int anneeSim, Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        
        int temps = (int) System.currentTimeMillis() / 200;
        BufferedImage imagePersonnage = (temps % 2 == 0) ? sprites.getImage("perso1.png") : sprites.getImage("perso2.png");
        BufferedImage imageNavire = sprites.getImage("navire.png");

        for (Mobile mobile : mobileManager) {
            if (mobile.estActif(anneeSim)) {
                peindreMobile(g, imagePersonnage, imageNavire, mobile, mobile.getType());
            }
        }
    }

    /**
     * Dessine une entité mobile individuelle.
     *
     * @param g               Le contexte graphique.
     * @param imagePersonnage Sprite du personnage (frame actuelle).
     * @param imageNavire     Sprite du navire.
     * @param mobile          L'objet {@link Mobile} contenant les coordonnées.
     * @param typeMobile      Le type d'entité (NAVIRE ou PERSONNAGE).
     */
    private void peindreMobile(Graphics2D g, BufferedImage imagePersonnage,
                                BufferedImage imageNavire, Mobile mobile, String typeMobile) {
        
        int x = mobile.getColonne() * tailleBloc;
        int y = mobile.getLigne()   * tailleBloc;

        switch (typeMobile) {
            case "NAVIRE":
                g.drawImage(imageNavire, x, y, tailleBloc, tailleBloc, null);
                break;
            case "PERSONNAGE":
                g.drawImage(imagePersonnage, x, y, tailleBloc, tailleBloc, null);
                break;
        }
    }

    /**
     * Configure les options de rendu et dessine les noms de toutes les régions.
     *
     * @param g Le contexte graphique.
     */
    private void peindreLabelsRegions(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        DessinUtilitaire.definirPolice(g, SimConfig.FONT_LABEL_SIM);

        long temps = System.currentTimeMillis();
        int idx = 0;
        
        for (String nom : regions.getAllNomRegions()) {
            Point position = regions.getPosition(nom);
            if (position != null) {
                peindreLabel(g, nom, (int) position.getY(), (int) position.getX(), idx, temps);
                idx++;
            }
        }
    }

    /**
     * Dessine un label textuel complet avec ses effets visuels complexes.
     *
     * @param g     Le contexte graphique.
     * @param nom   Le texte à afficher.
     * @param col   La colonne cible (coordonnée logique).
     * @param lig   La ligne cible (coordonnée logique).
     * @param idx   Index de la région (utilisé pour déphaser l'animation).
     * @param temps Temps actuel en millisecondes.
     */
    private void peindreLabel(Graphics2D g, String nom, int col, int lig, int idx, long temps) {
        FontMetrics fm = g.getFontMetrics();
        int txtW = fm.stringWidth(nom);
        int txtH = fm.getAscent();
        int padX = 6, padY = 4;
        int boxW = txtW + padX * 2;
        int boxH = txtH + padY * 2;
        
        int cx = col * tailleBloc;
        int cy = lig * tailleBloc;
        int bx = cx - boxW / 2;
        int by = cy - boxH / 2;
        int tx = bx + padX;
        int ty = by + padY + txtH - 1;

        double phase = temps / 600.0 + idx * 1.1;
        float  alpha = 0.35f + 0.20f * (float) Math.sin(phase);
        float  glow  = 0.18f + 0.12f * (float) Math.sin(phase * 1.3);

        creerHalo(g, boxW, boxH, bx, by, glow);
        creerFond(g, boxW, boxH, bx, by, alpha);
        creerBordure(g, boxW, boxH, bx, by);
        creerOmbre(g, nom, tx, ty, new Color(10, 5, 0));
        creerTexte(g, nom, tx, ty, new Color(255, 215, 60));
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    /**
     * Dessine le texte principal du label.
     *
     * @param g   Le contexte graphique.
     * @param nom Le texte à dessiner.
     * @param tx  Position X du texte.
     * @param ty  Position Y du texte.
     * @param c   Couleur du texte.
     */
    private void creerTexte(Graphics2D g, String nom, int tx, int ty, Color c) {
        DessinUtilitaire.definirCouleur(g, c);
        g.drawString(nom, tx, ty);
    }

    /**
     * Dessine une ombre légère sous le texte pour améliorer la lisibilité.
     *
     * @param g   Le contexte graphique.
     * @param nom Le texte.
     * @param tx  Position X.
     * @param ty  Position Y.
     * @param c   Couleur de l'ombre.
     */
    private void creerOmbre(Graphics2D g, String nom, int tx, int ty, Color c) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f));
        DessinUtilitaire.definirPolice(g, SimConfig.FONT_LABEL_SIM);
        DessinUtilitaire.definirCouleur(g, c);
        g.drawString(nom, tx + 1, ty + 1);
    }

    /**
     * Dessine la double bordure stylisée (extérieure sombre et intérieure dorée) du label.
     *
     * @param g    Le contexte graphique.
     * @param boxW Largeur de la boîte.
     * @param boxH Hauteur de la boîte.
     * @param bx   Coordonnée X du coin supérieur gauche.
     * @param by   Coordonnée Y du coin supérieur gauche.
     */
    private void creerBordure(Graphics2D g, int boxW, int boxH, int bx, int by) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.90f));
        g.setStroke(new BasicStroke(2.0f));
        DessinUtilitaire.definirCouleur(g, new Color(20, 10, 0));
        DessinUtilitaire.dessinerForme(g, new RoundRectangle2D.Float(bx, by, boxW, boxH, 6, 6));
        g.setStroke(new BasicStroke(1.0f));
        DessinUtilitaire.definirCouleur(g, new Color(200, 160, 40));
        DessinUtilitaire.dessinerForme(g, new RoundRectangle2D.Float(bx + 2, by + 2, boxW - 4, boxH - 4, 4, 4));
    }

    /**
     * Dessine le fond plein du label avec une transparence variable.
     *
     * @param g     Le contexte graphique.
     * @param boxW  Largeur de la boîte.
     * @param boxH  Hauteur de la boîte.
     * @param bx    Position X.
     * @param by    Position Y.
     * @param alpha Valeur de transparence pour l'animation.
     */
    private void creerFond(Graphics2D g, int boxW, int boxH, int bx, int by, float alpha) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha + 0.35f));
        DessinUtilitaire.definirCouleur(g, new Color(40, 20, 5));
        DessinUtilitaire.remplirForme(g, new RoundRectangle2D.Float(bx, by, boxW, boxH, 6, 6));
    }

    /**
     * Dessine un halo lumineux derrière le label pour l'effet de brillance.
     *
     * @param g    Le contexte graphique.
     * @param boxW Largeur de la boîte.
     * @param boxH Hauteur de la boîte.
     * @param bx   Position X.
     * @param by   Position Y.
     * @param glow Intensité de la transparence du halo.
     */
    private void creerHalo(Graphics2D g, int boxW, int boxH, int bx, int by, float glow) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glow));
        DessinUtilitaire.definirCouleur(g, new Color(255, 210, 60));
        DessinUtilitaire.remplirForme(g, new RoundRectangle2D.Float(bx - 4, by - 4, boxW + 8, boxH + 8, 10, 10));
    }

    /**
     * Définit la taille des blocs pour le rendu.
     * @param tailleBloc Taille en pixels.
     */
    public void setTailleBloc(int tailleBloc) { 
    	this.tailleBloc = tailleBloc; 
    }
    
    /**
     * Active ou désactive l'affichage des labels de régions.
     * @param estEurope True pour afficher les labels, False sinon.
     */
    public void setEstEurope(boolean estEurope) { 
    	this.estEurope = estEurope; 
    }
    
}