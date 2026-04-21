# Histoire Java — Simulation de Civilisations Vikings

Simulation de civilisations inspirée de l'univers viking, développée en Java avec interface graphique Swing. Chaque civilisation gère son économie, son armée, sa population, sa religion et ses relations diplomatiques sur une carte en 2D.

> Projet académique réalisé en trinôme — CY Cergy Paris Université (L2 Informatique)

---

## Fonctionnalités

- Génération d'une carte en blocs (1080×720)
- Gestion complète d'une civilisation : économie, armée, population, religion, politique
- Système économique : productions, exportations, ressources importées (stycas)
- Relations diplomatiques entre civilisations
- Système d'événements historiques
- Interface graphique avec rendu de la carte (Swing)
- Gestion des exceptions métier personnalisées

## Technologies

- Java 17
- Swing (interface graphique)
- Programmation Orientée Objet (héritage, encapsulation, design patterns)
- Gestion d'exceptions personnalisées

## Structure du projet

```
src/
├── config/            # Configuration de la simulation (taille carte, blocs)
├── gui/               # Interface graphique (JFrame, panels, rendu carte)
├── log/               # Pour les logs
├── moteur/
│   ├── carte/         # Carte et blocs
│   ├── donnees/       # Civilisation, Armée, Population, Religion, Politique...
│   │   └── economie/  # Économie, Produits, Ressources
│   └── traitement/    # Construction des régions, polygones
│       ├── builders   # Builders
│       └── management # Managers 
└── utilitaires/       # Utilitaires
```

## Lancer le projet

```bash
# Compiler depuis le dossier src/
javac -d out $(find . -name "*.java")

# Lancer
java -cp out gui.MainGUI
```

## Auteurs

- Massinissa Lomani
- Alexandre Burin
- Tauseef Ahmed
