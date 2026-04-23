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
├── config/             # Configuration de la simulation (taille carte, blocs)
├── gui/                # Interface graphique (JFrame, panels, rendu carte)
├── log/                # Pour les logs
├── moteur/
│   ├── carte/          # Carte et blocs
│   ├── donnees/        # Civilisation, Armée, Population, Religion, Politique...
│   │   └── economie/   # Économie, Produits, Ressources
│   └── traitement/     # Construction des régions, polygones
│       ├── builders/   # Builders
│       └── management/ # Managers 
└── utilitaires/        # Utilitaires
```

## Lancer le projet

### Option 1 : Via l'IDE Eclipse

Pour exécuter le projet dans l'IDE Eclipse, suivez ces étapes :
1. Importer le projet :
  - Créez un nouveau projet Java dans Eclipse en sélectionnant le JRE 1.8.
  - Copiez le contenu du dossier src (téléchargé précédemment) et collez-le directement dans le dossier src de votre nouveau projet dans Eclipse.
  - Configurer les librairies : Faites un clic droit sur le projet > Build Path > Add Libraries > User Library.
  - Ajoutez les fichiers .jar requis (situés dans le dossier lib du projet).

2.Vérifier le JDK :
  - Assurez-vous que le projet utilise bien le JRE 1.8 (Clic droit sur le projet > Properties > Java Build Path > Libraries).

3. Exécuter :
  - Cherchez le fichier TestMainGUI.java situé dans le package tests.manuel.
  - Faites un clic droit sur le fichier > Run As > Java Application.

### Option 2 : Via le Terminal

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
