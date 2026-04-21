package gui.panels;

import config.SimConfig;
import moteur.donnees.*;
import moteur.traitement.management.managers.armee.ArmeeManager;
import moteur.traitement.management.managers.politique.RelationManager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Panel de fin de simulation affiché lorsque l'année de fin est atteinte.
 *
 * <p>
 * Ce composant fournit un tableau de bord récapitulatif présentant l'état final 
 * de la civilisation à travers plusieurs indicateurs : population, économie, 
 * puissance militaire, religion, politique, diplomatie et historique des événements.
 * </p>
 *
 * @author Tauseef
 * @version 2.0
 */
public class PanelFinSimulation extends JPanel {

    /** 
     * Identifiant de version pour la sérialisation. 
     */
    private static final long serialVersionUID = 15616113216161618L;

    /** 
     * Couleur d'arrière-plan principale. 
     */
    private static final Color BG_MAIN    = SimConfig.COLOR_BG_MAIN;
    
    /** 
     * Couleur d'arrière-plan des cartes de section. 
     */
    private static final Color BG_CARD    = SimConfig.COLOR_BG_CARD;
    
    /** 
     * Couleur d'arrière-plan des zones de données internes. 
     */
    private static final Color BG_INNER   = SimConfig.COLOR_BG_INNER;
    
    /** 
     * Couleur des bordures et séparateurs. 
     */
    private static final Color BORDER_COL = SimConfig.COLOR_BORDER;
    
    /** 
     * Couleur par défaut du texte. 
     */
    private static final Color TEXT_COL   = SimConfig.COLOR_TEXT;
    
    /** 
     * Couleur d'accentuation (titres de sections). 
     */
    private static final Color ACCENT     = SimConfig.COLOR_ACCENT;
    
    /** 
     * Couleur pour les indicateurs positifs ou réussis. 
     */
    private static final Color SUCCESS    = SimConfig.COLOR_SUCCESS;
    
    /** 
     * Couleur pour les alertes ou états intermédiaires. 
     */
    private static final Color WARNING    = SimConfig.COLOR_WARNING;
    
    /** 
     * Couleur pour les états critiques ou dangereux. 
     */
    private static final Color DANGER     = SimConfig.COLOR_ACCENT_RED;

    /** 
     * Police utilisée pour le titre principal de la saga. 
     */
    private static final Font FONT_TITRE   = new Font("Serif", Font.BOLD, 30);
    
    /** 
     * Police utilisée pour le sous-titre (nom du dirigeant). 
     */
    private static final Font FONT_SOUS    = new Font("Serif", Font.BOLD | Font.ITALIC, 14);
    
    /** 
     * Police utilisée pour les titres de sections (cartes). 
     */
    private static final Font FONT_SECTION = new Font("Serif", Font.BOLD, 13);
    
    /** 
     * Police utilisée pour l'affichage des valeurs numériques. 
     */
    private static final Font FONT_VAL     = new Font("Serif", Font.PLAIN, 12);
    
    /** 
     * Police utilisée pour les libellés et descriptions courtes. 
     */
    private static final Font FONT_LABEL   = new Font("Serif", Font.ITALIC, 11);

    /**
     * Construit le panel de fin de simulation.
     * Initialise la mise en page et assemble les différentes sections de l'interface.
     *
     * @param civilisation La civilisation simulée dont on affiche le bilan.
     * @param anneeDebut   L'année où la simulation a commencé.
     * @param anneeFin     L'année où la simulation s'est terminée.
     */
    public PanelFinSimulation(Civilisation civilisation, int anneeDebut, int anneeFin) {
        setLayout(new BorderLayout());
        setBackground(BG_MAIN);

        add(buildBandeau(civilisation, anneeDebut, anneeFin), BorderLayout.NORTH);
        add(buildCorps(civilisation), BorderLayout.CENTER);
    }

    /**
     * Construit le bandeau supérieur (Header) contenant le titre, le règne et la période.
     * 
     * @param civ La civilisation concernée.
     * @param anneeDebut Année de départ.
     * @param anneeFin Année d'arrivée.
     * @return Un JPanel stylisé avec un dégradé de fond.
     */
    private JPanel buildBandeau(Civilisation civ, int anneeDebut, int anneeFin) {
        String nomKonungr = (civ.getKonungr() != null) ? civ.getKonungr().getNom() : "Inconnu";

        JLabel titre = new JLabel("⚔  Saga Terminée — " + civ.getNom());
        titre.setFont(FONT_TITRE);
        titre.setForeground(Color.WHITE);

        JLabel sousTitre = new JLabel("Sous le règne de " + nomKonungr);
        sousTitre.setFont(FONT_SOUS);
        sousTitre.setForeground(new Color(0xE9DDC7));

        JLabel periode = new JLabel(anneeDebut + " — " + anneeFin + " ap. J.-C.");
        periode.setFont(FONT_LABEL);
        periode.setForeground(new Color(0xC2A57E));

        JLabel duree = new JLabel((anneeFin - anneeDebut) + " ans simulés", SwingConstants.RIGHT);
        duree.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        duree.setForeground(new Color(0xE9DDC7));

        JPanel gauche = new JPanel(new GridLayout(3, 1));
        gauche.setOpaque(false);
        gauche.add(titre);
        gauche.add(sousTitre);
        gauche.add(periode);

       
        JPanel bandeau = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, BORDER_COL, getWidth(), 0, ACCENT));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(0xC2A57E));
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
            }
        };
        
        	bandeau.setPreferredSize(new Dimension(0, 100));
        	bandeau.setBorder(new EmptyBorder(10, 25, 10, 25));
        	bandeau.add(gauche, BorderLayout.CENTER);
        bandeau.add(duree,  BorderLayout.EAST);
        
        return bandeau;
    
    }
    
    /**
     * Construit le bandeau supérieur (Header) contenant le titre, le règne et la période.
     * 
     * @param civ La civilisation concernée.
     * @param anneeDebut Année de départ.
     * @param anneeFin Année d'arrivée.
     * @return Un JPanel stylisé avec un dégradé de fond.
     */
    private JScrollPane buildCorps(Civilisation civ) {
        JPanel corps = new JPanel(new GridBagLayout());
        corps.setBackground(BG_MAIN);
        corps.setBorder(new EmptyBorder(10, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.insets  = new Insets(6, 6, 6, 6);

        // Ligne 1
        gbc.gridy = 0;
        gbc.gridx = 0; corps.add(buildCartePopulation(civ.getPopulation()), gbc);
        gbc.gridx = 1; corps.add(buildCarteEconomie(civ.getEconomie()), gbc);
        gbc.gridx = 2; corps.add(buildCarteArmee(civ.getArmee()), gbc);

        // Ligne 2
        gbc.gridy = 1;
        gbc.gridx = 0; corps.add(buildCarteReligion(civ.getReligion()), gbc);
        gbc.gridx = 1; corps.add(buildCartePolitique(civ.getPolitique()), gbc);
        gbc.gridx = 2; corps.add(buildCarteRelations(civ.getRelations()), gbc);

        // Ligne 3 — événements pleine largeur
        gbc.gridy     = 2;
        gbc.gridx     = 0;
        gbc.gridwidth = 3;
        gbc.weighty   = 1.5;
        corps.add(buildCarteEvenements(civ.getEvenements()), gbc);

        JScrollPane scroll = new JScrollPane(corps,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_MAIN);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    /**
     * Crée une carte (conteneur) avec un fond arrondi et un titre de section. 
     * Sert de base visuelle pour chaque catégorie de statistiques.
     * 
     * @param titre Le titre à afficher en haut de la carte.
     * @return Un JPanel personnalisé avec rendu graphique avancé (Antialiasing).
     */
    private JPanel buildCarte(String titre) {
        JPanel carte = new JPanel(new BorderLayout(0, 6)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(BORDER_COL);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 12, 12));
                g2.dispose();
            }
        };
        carte.setOpaque(false);
        carte.setBorder(new EmptyBorder(10, 12, 12, 12));

        JLabel entete = new JLabel(titre);
        entete.setFont(FONT_SECTION);
        entete.setForeground(ACCENT);
        entete.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COL));
        carte.add(entete, BorderLayout.NORTH);
        return carte;
    }

    /**
     * Crée un panel intérieur stylisé pour accueillir des lignes de données.
     * 
     * @param rows Le nombre de lignes de la grille (GridLayout).
     * @return Un JPanel configuré avec une bordure et un fond spécifique.
     */
    private JPanel buildInner(int rows) {
        JPanel p = new JPanel(new GridLayout(rows, 2, 4, 4));
        p.setBackground(BG_INNER);
        p.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(0xB8956A)),
                new EmptyBorder(6, 8, 6, 8)));
        return p;
    }

    /**
     * Ajoute une paire Libellé/Valeur à un panel donné.
     * 
     * @param p Le panel de destination.
     * @param label Le nom de la donnée (ex: "Habitants").
     * @param valeur La valeur textuelle à afficher.
     */
    private void addLigne(JPanel p, String label, String valeur) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(new Color(0x5C3D1E));

        JLabel val = new JLabel(valeur);
        val.setFont(FONT_VAL);
        val.setForeground(TEXT_COL);

        p.add(lbl);
        p.add(val);
    }

    /**
     * Crée une barre de progression stylisée pour visualiser un pourcentage ou une quantité.
     * 
     * @param value Valeur actuelle.
     * @param max Valeur maximale possible.
     * @param couleur Couleur de la barre.
     * @param texte Texte à afficher sur la barre.
     * @return Une JProgressBar configurée.
     */
    private JProgressBar buildBar(int value, int max, Color couleur, String texte) {
        JProgressBar bar = new JProgressBar(0, max);
        bar.setValue(value);
        bar.setStringPainted(true);
        bar.setString(texte);
        bar.setForeground(couleur);
        bar.setBackground(BG_INNER);
        bar.setBorderPainted(false);
        bar.setFont(FONT_LABEL);
        bar.setPreferredSize(new Dimension(0, 16));
        return bar;
    }

    /**
     * Génère la carte des données démographiques.
     * 
     * @param pop Objet Population contenant les statistiques.
     * @return Le panel de la population.
     */
    private JPanel buildCartePopulation(Population pop) {
        JPanel carte = buildCarte("👥  Population");
        if (pop == null) {
            carte.add(new JLabel("Données indisponibles"), BorderLayout.CENTER);
            return carte;
        }
        JPanel inner = buildInner(3);
        addLigne(inner, "Habitants :",        String.format("%,d", pop.getNbHabitants()));
        addLigne(inner, "Âge moyen :",        String.format("%.1f ans", pop.getAgeMoyen()));
        addLigne(inner, "Sexe majoritaire :", pop.getSexeMajoritaire());
        carte.add(inner, BorderLayout.CENTER);
        return carte;
    }

    /**
     * Génère la carte des données financières (Stycas, gains, pertes).
     * 
     * @param eco Objet Economie contenant les finances.
     * @return Le panel de l'économie.
     */
    private JPanel buildCarteEconomie(Economie eco) {
        JPanel carte = buildCarte("💰  Économie");
        if (eco == null) {
            carte.add(new JLabel("Données indisponibles"), BorderLayout.CENTER);
            return carte;
        }
        float bilan = eco.getGainsTotal() - eco.getPertesTotal();

        JPanel inner = buildInner(4);
        addLigne(inner, "Stycas actuels :", String.format("%,.0f S", eco.getStycas()));
        addLigne(inner, "Gains totaux :",   String.format("%,.0f S", eco.getGainsTotal()));
        addLigne(inner, "Pertes totales :", String.format("%,.0f S", eco.getPertesTotal()));
        addLigne(inner, "Bilan final :",    (bilan >= 0 ? "+" : "") + String.format("%,.0f S", bilan));

        JProgressBar bar = buildBar(
                Math.min((int) eco.getStycas(), (int) SimConfig.MAX_STYCAS),
                (int) SimConfig.MAX_STYCAS,
                SUCCESS,
                String.format("%,.0f S", eco.getStycas()));

        JPanel contenu = new JPanel(new BorderLayout(0, 6));
        contenu.setOpaque(false);
        contenu.add(inner, BorderLayout.CENTER);
        contenu.add(bar,   BorderLayout.SOUTH);
        carte.add(contenu, BorderLayout.CENTER);
        return carte;
    }

    /**
     * Génère la carte des données militaires (Guerriers, navires, état de l'armée).
     * 
     * @param armee Objet Armee contenant les forces militaires.
     * @return Le panel de l'armée.
     */
    private JPanel buildCarteArmee(Armee armee) {
        JPanel carte = buildCarte("⚔  Armée");
        if (armee == null) {
            carte.add(new JLabel("Données indisponibles"), BorderLayout.CENTER);
            return carte;
        }
        String desc      = ArmeeManager.getDescriptionEtat(armee);
        int    etat      = (int) armee.getEtat();
        Color  couleur   = etat >= 75 ? SUCCESS : etat >= 50 ? WARNING : DANGER;

        JPanel inner = buildInner(4);
        addLigne(inner, "Guerriers :",  String.format("%,d", armee.getNombreGuerriers()));
        addLigne(inner, "Navires :",    String.format("%,d", armee.getNombreNavires()));
        addLigne(inner, "Techniques :", String.valueOf(armee.getTechniquesMilitaire()));
        addLigne(inner, "Condition :",  desc);

        JProgressBar bar = buildBar(etat, 100, couleur, "État : " + etat + "% — " + desc);

        JPanel contenu = new JPanel(new BorderLayout(0, 6));
        contenu.setOpaque(false);
        contenu.add(inner, BorderLayout.CENTER);
        contenu.add(bar,   BorderLayout.SOUTH);
        carte.add(contenu, BorderLayout.CENTER);
        return carte;
    }

    /**
     * Génère la carte des données religieuses (Nom, influence).
     * 
     * @param religion Objet Religion.
     * @return Le panel de la religion.
     */
    private JPanel buildCarteReligion(Religion religion) {
        JPanel carte = buildCarte("✝  Religion");
        if (religion == null) {
            carte.add(new JLabel("Données indisponibles"), BorderLayout.CENTER);
            return carte;
        }
        JPanel inner = buildInner(3);
        addLigne(inner, "Religion :",  religion.getNom());
        addLigne(inner, "Croyance :",  religion.getCroyance());
        addLigne(inner, "Influence :", String.format("%.1f%%", religion.getInfluence()));

        JProgressBar bar = buildBar((int) religion.getInfluence(), 100,
                new Color(0x8B6914),
                "Influence : " + (int) religion.getInfluence() + "%");

        JPanel contenu = new JPanel(new BorderLayout(0, 6));
        contenu.setOpaque(false);
        contenu.add(inner, BorderLayout.CENTER);
        contenu.add(bar,   BorderLayout.SOUTH);
        carte.add(contenu, BorderLayout.CENTER);
        return carte;
    }

    /**
     * Génère la carte des données politiques (Régime, stabilité).
     * 
     * @param politique Objet Politique.
     * @return Le panel de la politique.
     */
    private JPanel buildCartePolitique(Politique politique) {
        JPanel carte = buildCarte("🏛  Politique");
        if (politique == null) {
            carte.add(new JLabel("Données indisponibles"), BorderLayout.CENTER);
            return carte;
        }
        Color couleur = politique.getStabilite() >= 50 ? SUCCESS : WARNING;

        JPanel inner = buildInner(5);
        addLigne(inner, "Régime :",       politique.getTypeRegime());
        addLigne(inner, "Stabilité :",    String.format("%.1f%% (%s)",
                politique.getStabilite(), politique.getEtatStabilite()));
        addLigne(inner, "Militaire :",    politique.getPolitiqueMilitaire());
        addLigne(inner, "Économique :",   politique.getPolitiqueEconomique());
        addLigne(inner, "Diplomatique :", politique.getPolitiqueDiplomatique());

        JProgressBar bar = buildBar((int) politique.getStabilite(), 100, couleur,
                "Stabilité : " + (int) politique.getStabilite() + "%");

        JPanel contenu = new JPanel(new BorderLayout(0, 6));
        contenu.setOpaque(false);
        contenu.add(inner, BorderLayout.CENTER);
        contenu.add(bar,   BorderLayout.SOUTH);
        carte.add(contenu, BorderLayout.CENTER);
        return carte;
    }

    /**
     * Génère la carte des relations diplomatiques avec les autres nations.
     * 
     * @param relations Liste des relations enregistrées.
     * @return Le panel de la diplomatie.
     */
    private JPanel buildCarteRelations(ArrayList<Relation> relations) {
        JPanel carte = buildCarte("🤝  Relations diplomatiques");

        if (relations == null || relations.isEmpty()) {
            JLabel vide = new JLabel("Aucune relation établie");
            vide.setFont(FONT_LABEL);
            vide.setForeground(TEXT_COL);
            carte.add(vide, BorderLayout.CENTER);
            return carte;
        }

        JPanel liste = new JPanel(new GridLayout(relations.size(), 1, 0, 3));
        liste.setBackground(BG_INNER);
        liste.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(0xB8956A)),
                new EmptyBorder(4, 6, 4, 6)));

        for (Relation rel : relations) {
            int    niv    = (int) rel.getNiveau();
            String statut = RelationManager.getDescriptionRelation(rel);
            Color  couleur = niv >= 75 ? SUCCESS : niv >= 50 ? WARNING : DANGER;

            JLabel nomCiv = new JLabel(rel.getNomCivilisation());
            nomCiv.setFont(FONT_VAL);
            nomCiv.setForeground(TEXT_COL);

            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(niv);
            bar.setString(statut + " (" + niv + "%)");
            bar.setStringPainted(true);
            bar.setForeground(couleur);
            bar.setBackground(BG_MAIN);
            bar.setBorderPainted(false);
            bar.setFont(FONT_LABEL);
            bar.setPreferredSize(new Dimension(130, 14));

            JPanel ligne = new JPanel(new BorderLayout(6, 0));
            ligne.setOpaque(false);
            ligne.add(nomCiv, BorderLayout.CENTER);
            ligne.add(bar,    BorderLayout.EAST);
            liste.add(ligne);
        }
        carte.add(liste, BorderLayout.CENTER);
        return carte;
    }

    /**
     * Génère la carte listant l'historique des événements majeurs de la saga.
     * Utilise une {@link JTable} pour présenter les données de manière structurée.
     * 
     * @param evenements Liste des événements survenus pendant la simulation.
     * @return Le panel des événements.
     */
    private JPanel buildCarteEvenements(ArrayList<Evenement> evenements) {
        int nb = (evenements != null) ? evenements.size() : 0;
        JPanel carte = buildCarte("📜  Événements de la saga (" + nb + ")");

        if (evenements == null || evenements.isEmpty()) {
            JLabel vide = new JLabel("Aucun événement enregistré");
            vide.setFont(FONT_LABEL);
            carte.add(vide, BorderLayout.CENTER);
            return carte;
        }

        String[]   colonnes = {"Événement", "Type", "Période", "Région"};
        Object[][] data     = new Object[evenements.size()][4];
        for (int i = 0; i < evenements.size(); i++) {
            Evenement e = evenements.get(i);
            data[i][0]  = e.getNom();
            data[i][1]  = e.getType();
            data[i][2]  = e.getAnneeDebut() + " — " + e.getAnneeFin();
            data[i][3]  = (e.getRegion() != null) ? e.getRegion().getNom() : "—";
        }

        JTable table = new JTable(data, colonnes) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setBackground(BG_INNER);
        table.setForeground(TEXT_COL);
        table.setFont(FONT_VAL);
        table.setGridColor(new Color(0xB8956A));
        table.setRowHeight(20);
        table.setSelectionBackground(new Color(0xC2A57E));
        table.setSelectionForeground(SimConfig.COLOR_TITLE);
        table.getTableHeader().setBackground(BG_CARD);
        table.getTableHeader().setForeground(ACCENT);
        table.getTableHeader().setFont(FONT_SECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(220);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(BG_INNER);
        scroll.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0xB8956A)));
        scroll.setPreferredSize(new Dimension(0, 140));
        carte.add(scroll, BorderLayout.CENTER);
        return carte;
    }
    
}