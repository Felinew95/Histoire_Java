package utilitaire;

import java.io.File;
import org.apache.log4j.Logger;
import log.LoggerUtility;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Classe utilitaire gérant la lecture et le contrôle des flux audio du jeu.
 *
 * <p>
 * Ce gestionnaire permet de manipuler facilement les fichiers sonores (.wav).
 * Il distingue deux types de flux :
 * 
 * <ul>
 * 		<li>La musique de fond (gérée de manière globale et unique).</li>
 * 		<li>Les effets sonores (superposables, joués ponctuellement).</li>
 * </ul>
 * 
 * Il intègre également des fonctionnalités de contrôle du volume via une conversion
 * en décibels pour un rendu naturel de l'atténuation sonore.
 * </p>
 *
 * @author Tauseef
 * @version 2.0
 */
public class MusicManager {
	
	/**
	 * Logger pour suivre l'évolution 
	 */
	private static final Logger logger = LoggerUtility.getLogger(MusicManager.class, "html");
	
    /**
     * Lecteur audio dédié à la musique de fond principale.
     * Ce clip est généralement exécuté dans un Thread séparé par le système audio de Java.
     */
    private static Clip clipMusique;
    
    /**
     * Ajuste le volume d'un flux audio spécifique en appliquant une échelle logarithmique.
     * * <p>
     * La conversion en décibels est nécessaire car l'oreille humaine perçoit le son de 
     * manière logarithmique et non linéaire.
     * </p>
     *
     * @param clip        Le lecteur audio ({@link Clip}) dont on souhaite modifier le volume.
     * @param pourcentage Le volume désiré (compris entre 0 et 100). Les valeurs hors de
     * cette plage seront contraintes par {@code SimulationUtility.clamp}.
     */
    public static void setVolume(Clip clip, int pourcentage) {
        if (clip != null && clip.isOpen() && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl fluxControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            pourcentage = SimulationUtility.clamp(pourcentage, 0, 100);
            
            if (pourcentage == 0) {
                fluxControl.setValue(fluxControl.getMinimum());
            } else {
                float decibels = 20f * (float) Math.log10(pourcentage / 100f);
                fluxControl.setValue(decibels);
            }
        }
    }

    /**
     * Ajuste uniquement le volume de la musique de fond principale actuellement en cours de lecture.
     *
     * @param pourcentage Le volume désiré (de 0 à 100).
     */
    public static void setVolumeMusiqueDeFond(int pourcentage) {
        setVolume(clipMusique, pourcentage);
    }

    /**
     * Méthode universelle pour charger et jouer un fichier audio au format .wav.
     *
     * @param cheminFichier    Le chemin absolu ou relatif vers le fichier audio .wav.
     * @param estMusiqueDeFond Si {@code true}, arrête la musique actuelle et attribue ce son
     * au canal principal ({@link #clipMusique}). Si {@code false}, 
     * le son est joué en tant qu'effet sonore indépendant (superposé).
     * @param boucle           Si {@code true}, le fichier audio tournera en boucle de manière infinie.
     * Si {@code false}, il sera lu une seule fois.
     * @return {@code true} si la lecture du fichier a démarré avec succès, {@code false} en
     * cas d'erreur (fichier introuvable, format non supporté, etc.).
     */
    public static boolean jouerAudio(String cheminFichier, boolean estMusiqueDeFond, boolean boucle) {
        try {
            File fichierAudio = new File(cheminFichier);
            if (!fichierAudio.exists()) {
                return false;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(fichierAudio);

            if (estMusiqueDeFond) {
                arreterMusique();
                clipMusique = AudioSystem.getClip();
                clipMusique.open(audioInput);
                
                if (boucle) clipMusique.loop(Clip.LOOP_CONTINUOUSLY);
                clipMusique.start();
          
                return true;
                
            } else {
                Clip clipEffet = AudioSystem.getClip();
                clipEffet.open(audioInput);
                
                if (boucle) clipEffet.loop(Clip.LOOP_CONTINUOUSLY);
                clipEffet.start();
                return true;
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        	logger.error("Le fichier : " + cheminFichier + " n'a pas pu être lu", e);
            return false;
        }
    }

    /**
     * Arrête et libère les ressources de la musique de fond principale en cours de lecture.
     * N'affecte pas les effets sonores ponctuels.
     */
    public static void arreterMusique() {
        if (clipMusique != null) {
            clipMusique.stop();
            clipMusique.close();
        }
    }
    
    /**
     * Indique si une musique est actuellement en cours de lecture.
     *
     * @return {@code true} si un clip audio existe et est en train de jouer,
     *         {@code false} sinon
     */
    public static boolean estEnCours() {
        return clipMusique != null && clipMusique.isRunning();
    }
    
}