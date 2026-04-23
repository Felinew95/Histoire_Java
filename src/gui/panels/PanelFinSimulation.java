package gui.panels;

import config.SimConfig;
import moteur.donnees.*;
import moteur.traitement.management.managers.armee.ArmeeManager;
import moteur.traitement.management.managers.politique.RelationManager;
import utilitaire.DessinUtilitaire;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Panneau de fin de simulation affiché lorsque l'année de fin est atteinte.
 *
 * <p>
 * Ce composant fournit un tableau de bord récapitulatif présentant l'état final
 * de la civilisation à travers plusieurs indicateurs :
 * population, économie, puissance militaire, religion, politique,
 * diplomatie et historique des événements.
 * </p>
 *
 * @author Tauseef
 * @version 2.1
 */
public class PanelFinSimulation extends JPanel {

    /** Identifiant de sérialisation. */
    private static final long serialVersionUID = 15616113216161618L;

    /** Couleur d'arrière-plan principale. */
    private static final Color COULEUR_FOND= SimConfig.COLOR_BG_MAIN;

    /** Couleur d'arrière-plan des cartes de section. */
    private static final Color COULEUR_CARTE= SimConfig.COLOR_BG_CARD;

    /** Couleur d'arrière-plan des zones de données internes. */
    private static final Color COULEUR_INNER= SimConfig.COLOR_BG_INNER;

    /** Couleur des bordures et séparateurs. */
    private static final Color COULEUR_BORDURE= SimConfig.COLOR_BORDER;

    /** Couleur par défaut du texte. */
    private static final Color COULEUR_TEXTE= SimConfig.COLOR_TEXT;

    /** Couleur d'accentuation (titres de sections). */
    private static final Color COULEUR_ACCENT= SimConfig.COLOR_ACCENT;

    /** Couleur pour les indicateurs positifs. */
    private static final Color COULEUR_SUCCES= SimConfig.COLOR_SUCCESS;

    /** Couleur pour les états intermédiaires ou alertes. */
    private static final Color COULEUR_ALERTE= SimConfig.COLOR_WARNING;

    /** Couleur pour les états critiques. */
    private static final Color COULEUR_DANGER= SimConfig.COLOR_ACCENT_RED;

    /** Police du titre principal. */
    private static final Font POLICE_TITRE    = new Font("Serif", Font.BOLD, 30);

    /** Police du sous-titre (nom du dirigeant). */
    private static final Font POLICE_SOUS     = new Font("Serif", Font.BOLD | Font.ITALIC, 14);

    /** Police des titres de sections. */
    private static final Font POLICE_SECTION  = new Font("Serif", Font.BOLD, 13);

    /** Police des valeurs numériques. */
    private static final Font POLICE_VALEUR   = new Font("Serif", Font.PLAIN, 12);

    /** Police des libellés descriptifs. */
    private static final Font POLICE_LIBELLE  = new Font("Serif", Font.ITALIC, 11);


    /**
     * Construit le panneau de fin de simulation.
     *
     * @param civilisation La civilisation simulée dont on affiche le bilan.
     * @param anneeDebut   L'année de début de la simulation.
     * @param anneeFin     L'année de fin de la simulation.
     */
    public PanelFinSimulation(Civilisation civilisation, int anneeDebut, int anneeFin) {
        setLayout(new BorderLayout());
        setBackground(COULEUR_FOND);

        add(buildBandeau(civilisation, anneeDebut, anneeFin), BorderLayout.NORTH);
        add(buildCorps(civilisation),                         BorderLayout.CENTER);
    }

    /**
     * Construit le bandeau supérieur avec le titre, le nom du dirigeant et la période.
     *
     * @param civ        La civilisation concernée.
     * @param anneeDebut Année de départ.
     * @param anneeFin   Année d'arrivée.
     * @return Un {@link JPanel} avec un dégradé de fond.
     */
    private JPanel buildBandeau(Civilisation civ, int anneeDebut, int anneeFin) {
        String nomDirigeant = (civ.getKonungr() != null) ? civ.getKonungr().getNom() : "Inconnu";

        JLabel labelTitre    = creerLabel("⚔  Saga Terminée — " + civ.getNom(), POLICE_TITRE,   Color.WHITE);
        JLabel labelDirigeant = creerLabel("Sous le règne de " + nomDirigeant,   POLICE_SOUS,    new Color(0xE9DDC7));
        JLabel labelPeriode  = creerLabel(anneeDebut + " — " + anneeFin + " ap. J.-C.", POLICE_LIBELLE, new Color(0xC2A57E));
        JLabel labelDuree    = creerLabel((anneeFin - anneeDebut) + " ans simulés",
                new Font("Serif", Font.BOLD | Font.ITALIC, 20), new Color(0xE9DDC7));
        labelDuree.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel colGauche = new JPanel(new GridLayout(3, 1));
        colGauche.setOpaque(false);
        colGauche.add(labelTitre);
        colGauche.add(labelDirigeant);
        colGauche.add(labelPeriode);

        JPanel bandeau = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                // Utilisation du dégradé spécifique
                g2.setPaint(new GradientPaint(0, 0, COULEUR_BORDURE, getWidth(), 0, COULEUR_ACCENT));
                
                // Utilisation de DessinUtilitaire pour remplir le fond
                DessinUtilitaire.remplirForme(g2, new Rectangle(0, 0, getWidth(), getHeight()));
                
                DessinUtilitaire.definirCouleur(g2, new Color(0xC2A57E));
                g2.setStroke(new BasicStroke(2f));
                // Dessin d'une ligne de séparation
                g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
            }
        };
        bandeau.setPreferredSize(new Dimension(0, 100));
        bandeau.setBorder(new EmptyBorder(10, 25, 10, 25));
        bandeau.add(colGauche, BorderLayout.CENTER);
        bandeau.add(labelDuree, BorderLayout.EAST);

        return bandeau;
    }

    /**
     * Construit le corps principal contenant les six cartes de statistiques
     * et la carte des événements en pleine largeur.
     *
     * @param civ La civilisation à afficher.
     * @return Un {@link JScrollPane} encapsulant toutes les cartes.
     */
    private JScrollPane buildCorps(Civilisation civ) {
        JPanel corps = new JPanel(new GridBagLayout());
        corps.setBackground(COULEUR_FOND);
        corps.setBorder(new EmptyBorder(10, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.insets  = new Insets(6, 6, 6, 6);

        // Ligne 1 : population, économie, armée
        gbc.gridy = 0;
        gbc.gridx = 0; corps.add(buildCartePopulation(civ.getPopulation()), gbc);
        gbc.gridx = 1; corps.add(buildCarteEconomie(civ.getEconomie()),     gbc);
        gbc.gridx = 2; corps.add(buildCarteArmee(civ.getArmee()),           gbc);

        gbc.gridy = 1;
        gbc.gridx = 0; corps.add(buildCarteReligion(civ.getReligion()),     gbc);
        gbc.gridx = 1; corps.add(buildCartePolitique(civ.getPolitique()),   gbc);
        gbc.gridx = 2; corps.add(buildCarteRelations(civ.getRelations()),   gbc);

        // Ligne 3 : événements en pleine largeur
        gbc.gridy     = 2;
        gbc.gridx     = 0;
        gbc.gridwidth = 3;
        gbc.weighty   = 1.5;
        corps.add(buildCarteEvenements(civ.getEvenements()), gbc);

        JScrollPane scroll = new JScrollPane(corps,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COULEUR_FOND);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    /**
     * Crée un {@link JLabel} avec la police et la couleur spécifiées.
     */
    private JLabel creerLabel(String texte, Font police, Color couleur) {
        JLabel label = new JLabel(texte);
        label.setFont(police);
        label.setForeground(couleur);
        return label;
    }

    /**
     * Crée une carte (conteneur visuel) avec un fond arrondi et un titre de section.
     */
    private JPanel buildCarte(String titre) {
        JPanel carte = new JPanel(new BorderLayout(0, 6)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                RoundRectangle2D fond    = new RoundRectangle2D.Float(0, 0, getWidth(),     getHeight(),     12, 12);
                RoundRectangle2D bordure = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

                // Utilisation de DessinUtilitaire pour les couleurs et les formes
                DessinUtilitaire.definirCouleur(g2, COULEUR_CARTE);
                DessinUtilitaire.remplirForme(g2, fond);

                DessinUtilitaire.definirCouleur(g2, COULEUR_BORDURE);
                g2.setStroke(new BasicStroke(1.5f));
                DessinUtilitaire.dessinerForme(g2, bordure);

                g2.dispose();
            }
        };
        carte.setOpaque(false);
        carte.setBorder(new EmptyBorder(10, 12, 12, 12));

        JLabel entete = creerLabel(titre, POLICE_SECTION, COULEUR_ACCENT);
        entete.setBorder(new MatteBorder(0, 0, 1, 0, COULEUR_BORDURE));
        carte.add(entete, BorderLayout.NORTH);
        return carte;
    }

    /**
     * Crée un panneau intérieur en grille pour afficher des paires libellé/valeur.
     */
    private JPanel buildInner(int nbLignes) {
        JPanel inner = new JPanel(new GridLayout(nbLignes, 2, 4, 4));
        inner.setBackground(COULEUR_INNER);
        inner.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, new Color(0xB8956A)),new EmptyBorder(6, 8, 6, 8)));
        return inner;
    }

    /**
     * Ajoute une ligne libellé/valeur à un panneau intérieur.
     */
    private void addLigne(JPanel inner, String libelle, String valeur) {
        inner.add(creerLabel(libelle, POLICE_LIBELLE, new Color(0x5C3D1E)));
        inner.add(creerLabel(valeur,  POLICE_VALEUR,  COULEUR_TEXTE));
    }

    /**
     * Crée une barre de progression stylisée.
     */
    private JProgressBar buildBarre(int valeur, int maximum, Color couleur, String texte) {
        JProgressBar barre = new JProgressBar(0, maximum);
        barre.setValue(valeur);
        barre.setStringPainted(true);
        barre.setString(texte);
        barre.setForeground(couleur);
        barre.setBackground(COULEUR_INNER);
        barre.setBorderPainted(false);
        barre.setFont(POLICE_LIBELLE);
        barre.setPreferredSize(new Dimension(0, 16));
        return barre;
    }

    /**
     * Assemble une carte avec un panneau intérieur et une barre de progression en bas.
     */
    private void assemblerCarteAvecBarre(JPanel carte, JPanel inner, JProgressBar barre) {
        JPanel contenu = new JPanel(new BorderLayout(0, 6));
        contenu.setOpaque(false);
        contenu.add(inner, BorderLayout.CENTER);
        contenu.add(barre, BorderLayout.SOUTH);
        carte.add(contenu, BorderLayout.CENTER);
    }

    // [Méthodes buildCartePopulation, buildCarteEconomie, etc. restent identiques à ta logique]

    private JPanel buildCartePopulation(Population pop) {
        JPanel carte = buildCarte("👥  Population");
        if (pop == null) {
            carte.add(creerLabel("Données indisponibles", POLICE_LIBELLE, COULEUR_TEXTE), BorderLayout.CENTER);
            return carte;
        }
        JPanel inner = buildInner(3);
        addLigne(inner, "Habitants :",        String.format("%,d",   pop.getNbHabitants()));
        addLigne(inner, "Âge moyen :",        String.format("%.1f ans", pop.getAgeMoyen()));
        addLigne(inner, "Sexe majoritaire :", pop.getSexeMajoritaire());
        carte.add(inner, BorderLayout.CENTER);
        return carte;
    }

    private JPanel buildCarteEconomie(Economie eco) {
        JPanel carte = buildCarte("💰  Économie");
        if (eco == null) {
            carte.add(creerLabel("Données indisponibles", POLICE_LIBELLE, COULEUR_TEXTE), BorderLayout.CENTER);
            return carte;
        }
        float bilan = eco.getGainsTotal() - eco.getPertesTotal();

        JPanel inner = buildInner(4);
        addLigne(inner, "Stycas actuels :", String.format("%,.0f S", eco.getStycas()));
        addLigne(inner, "Gains totaux :",   String.format("%,.0f S", eco.getGainsTotal()));
        addLigne(inner, "Pertes totales :", String.format("%,.0f S", eco.getPertesTotal()));
        addLigne(inner, "Bilan final :",    (bilan >= 0 ? "+" : "") + String.format("%,.0f S", bilan));

        JProgressBar barre = buildBarre(
                Math.min((int) eco.getStycas(), (int) SimConfig.MAX_STYCAS),
                (int) SimConfig.MAX_STYCAS,
                COULEUR_SUCCES,
                String.format("%,.0f S", eco.getStycas()));

        assemblerCarteAvecBarre(carte, inner, barre);
        return carte;
    }

    private JPanel buildCarteArmee(Armee armee) {
        JPanel carte = buildCarte("⚔  Armée");
        if (armee == null) {
            carte.add(creerLabel("Données indisponibles", POLICE_LIBELLE, COULEUR_TEXTE), BorderLayout.CENTER);
            return carte;
        }
        String description = ArmeeManager.getDescriptionEtat(armee);
        int    etat        = (int) armee.getEtat();
        Color  couleur     = etat >= 75 ? COULEUR_SUCCES : etat >= 50 ? COULEUR_ALERTE : COULEUR_DANGER;

        JPanel inner = buildInner(4);
        addLigne(inner, "Guerriers :",  String.format("%,d", armee.getNombreGuerriers()));
        addLigne(inner, "Navires :",    String.format("%,d", armee.getNombreNavires()));
        addLigne(inner, "Techniques :", String.valueOf(armee.getTechniquesMilitaire()));
        addLigne(inner, "Condition :",  description);

        assemblerCarteAvecBarre(carte, inner,
                buildBarre(etat, 100, couleur, "État : " + etat + "% — " + description));
        return carte;
    }

    private JPanel buildCarteReligion(Religion religion) {
        JPanel carte = buildCarte("✝  Religion");
        if (religion == null) {
            carte.add(creerLabel("Données indisponibles", POLICE_LIBELLE, COULEUR_TEXTE), BorderLayout.CENTER);
            return carte;
        }
        JPanel inner = buildInner(3);
        addLigne(inner, "Religion :",  religion.getNom());
        addLigne(inner, "Croyance :",  religion.getCroyance());
        addLigne(inner, "Influence :", String.format("%.1f%%", religion.getInfluence()));

        assemblerCarteAvecBarre(carte, inner,buildBarre((int) religion.getInfluence(), 100,
                        new Color(0x8B6914),
                        "Influence : " + (int) religion.getInfluence() + "%"));
        return carte;
    }

    private JPanel buildCartePolitique(Politique politique) {
        JPanel carte = buildCarte("🏛  Politique");
        if (politique == null) {
            carte.add(creerLabel("Données indisponibles", POLICE_LIBELLE, COULEUR_TEXTE), BorderLayout.CENTER);
            return carte;
        }
        Color couleur = politique.getStabilite() >= 50 ? COULEUR_SUCCES : COULEUR_ALERTE;

        JPanel inner = buildInner(5);
        addLigne(inner, "Régime :",       politique.getTypeRegime());
        addLigne(inner, "Stabilité :",    String.format("%.1f%% (%s)",
                politique.getStabilite(), politique.getEtatStabilite()));
        addLigne(inner, "Militaire :",    politique.getPolitiqueMilitaire());
        addLigne(inner, "Économique :",   politique.getPolitiqueEconomique());
        addLigne(inner, "Diplomatique :", politique.getPolitiqueDiplomatique());

        assemblerCarteAvecBarre(carte, inner,buildBarre((int) politique.getStabilite(), 100, couleur,"Stabilité : " + (int) politique.getStabilite() + "%"));
        return carte;
    }

    private JPanel buildCarteRelations(ArrayList<Relation> relations) {
        JPanel carte = buildCarte("🤝  Relations diplomatiques");

        if (relations == null || relations.isEmpty()) {
            carte.add(creerLabel("Aucune relation établie", POLICE_LIBELLE, COULEUR_TEXTE), BorderLayout.CENTER);
            return carte;
        }

        JPanel liste = new JPanel(new GridLayout(relations.size(), 1, 0, 3));
        liste.setBackground(COULEUR_INNER);
        liste.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, new Color(0xB8956A)),new EmptyBorder(4, 6, 4, 6)));

        for (Relation rel : relations) {
            int    niveau  = (int) rel.getNiveau();
            String statut  = RelationManager.getDescriptionRelation(rel);
            Color  couleur = niveau >= 75 ? COULEUR_SUCCES : niveau >= 50 ? COULEUR_ALERTE : COULEUR_DANGER;

            JProgressBar barre = new JProgressBar(0, 100);
            barre.setValue(niveau);
            barre.setString(statut + " (" + niveau + "%)");
            barre.setStringPainted(true);
            barre.setForeground(couleur);
            barre.setBackground(COULEUR_FOND);
            barre.setBorderPainted(false);
            barre.setFont(POLICE_LIBELLE);
            barre.setPreferredSize(new Dimension(130, 14));

            JPanel ligne = new JPanel(new BorderLayout(6, 0));
            ligne.setOpaque(false);
            ligne.add(creerLabel(rel.getNomCivilisation(), POLICE_VALEUR, COULEUR_TEXTE), BorderLayout.CENTER);
            ligne.add(barre, BorderLayout.EAST);
            liste.add(ligne);
        }
        carte.add(liste, BorderLayout.CENTER);
        return carte;
    }

    private JPanel buildCarteEvenements(ArrayList<Evenement> evenements) {
        int nb = (evenements != null) ? evenements.size() : 0;
        JPanel carte = buildCarte("📜  Événements de la saga (" + nb + ")");

        if (evenements == null || evenements.isEmpty()) {
            carte.add(creerLabel("Aucun événement enregistré", POLICE_LIBELLE, COULEUR_TEXTE), BorderLayout.CENTER);
            return carte;
        }

        String[]   colonnes = {"Événement", "Type", "Période", "Région"};
        Object[][] donnees  = new Object[evenements.size()][4];
        
        for (int i = 0; i < evenements.size(); i++) {
            Evenement e  = evenements.get(i);
            donnees[i][0] = e.getNom();
            donnees[i][1] = e.getType();
            donnees[i][2] = e.getAnneeDebut() + " — " + e.getAnneeFin();
            donnees[i][3] = (e.getRegion() != null) ? e.getRegion().getNom() : "—";
        }

        JTable tableau = new JTable(donnees, colonnes) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        tableau.setBackground(COULEUR_INNER);
        tableau.setForeground(COULEUR_TEXTE);
        tableau.setFont(POLICE_VALEUR);
        tableau.setGridColor(new Color(0xB8956A));
        tableau.setRowHeight(20);
        tableau.setSelectionBackground(new Color(0xC2A57E));
        tableau.setSelectionForeground(SimConfig.COLOR_TITLE);
        tableau.getTableHeader().setBackground(COULEUR_CARTE);
        tableau.getTableHeader().setForeground(COULEUR_ACCENT);
        tableau.getTableHeader().setFont(POLICE_SECTION);

        JScrollPane scroll = new JScrollPane(tableau);
        scroll.getViewport().setBackground(COULEUR_INNER);
        scroll.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0xB8956A)));
        scroll.setPreferredSize(new Dimension(0, 140));
        carte.add(scroll, BorderLayout.CENTER);
        return carte;
    }
}