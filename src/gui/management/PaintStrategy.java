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
 * Version propre, synchronisée avec les vraies données de la simulation
 * et utilisant une syntaxe classique.
 *
 * @author Alexandre, Tauseef, Massinissa
 * @version 3.2
 */
public class PaintStrategy {
    private int tailleBloc = SimConfig.TAILLE_BLOC_EUROPE;

    private final SpritesConfig sprites = SpritesConfig.getInstance();
    private final RegionRepository regions = RegionRepository.getInstance();
    private boolean estEurope = true;

  
    public void paint(Carte carte, Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Bloc[][] blocs = carte.getBlocs();

        // Animation de l'eau
        int temps = (int) System.currentTimeMillis() / 500;
        BufferedImage imageEau;
        if (temps % 2 == 0) {
            imageEau = sprites.getImage("eau1.png");
        } else {
            imageEau = sprites.getImage("eau2.png");
        }
        
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



    public void paint(MobileManager mobileManager, int anneeSim, Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        
        int temps = (int) System.currentTimeMillis() / 200;
        BufferedImage imagePersonnage;
        if (temps % 2 == 0) {
            imagePersonnage = sprites.getImage("perso1.png");
        } else {
            imagePersonnage = sprites.getImage("perso2.png");
        }
        
        BufferedImage imageNavire = sprites.getImage("navire.png");

        for (Mobile mobile : mobileManager) {
            if (mobile.estActif(anneeSim)) {
                peindreMobile(g, imagePersonnage, imageNavire, mobile, mobile.getType());
            }
        }
    }

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

    private void peindreLabelsRegions(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(SimConfig.FONT_LABEL_SIM);

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

    private void creerTexte(Graphics2D g, String nom, int tx, int ty, Color c) {
        g.setColor(c);
        g.drawString(nom, tx, ty);
    }

    private void creerOmbre(Graphics2D g, String nom, int tx, int ty, Color c) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f));
        g.setFont(SimConfig.FONT_LABEL_SIM);
        g.setColor(c);
        g.drawString(nom, tx + 1, ty + 1);
    }

    private void creerBordure(Graphics2D g, int boxW, int boxH, int bx, int by) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.90f));
        g.setStroke(new BasicStroke(2.0f));
        g.setColor(new Color(20, 10, 0));
        g.draw(new RoundRectangle2D.Float(bx, by, boxW, boxH, 6, 6));
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(new Color(200, 160, 40));
        g.draw(new RoundRectangle2D.Float(bx + 2, by + 2, boxW - 4, boxH - 4, 4, 4));
    }

    private void creerFond(Graphics2D g, int boxW, int boxH, int bx, int by, float alpha) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha + 0.35f));
        g.setColor(new Color(40, 20, 5));
        g.fill(new RoundRectangle2D.Float(bx, by, boxW, boxH, 6, 6));
    }

    private void creerHalo(Graphics2D g, int boxW, int boxH, int bx, int by, float glow) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glow));
        g.setColor(new Color(255, 210, 60));
        g.fill(new RoundRectangle2D.Float(bx - 4, by - 4, boxW + 8, boxH + 8, 10, 10));
    }

    public void setTailleBloc(int tailleBloc)   { this.tailleBloc = tailleBloc; }
    public void setEstEurope(boolean estEurope) { this.estEurope = estEurope; }
}