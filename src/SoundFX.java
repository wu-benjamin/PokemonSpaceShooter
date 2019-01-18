import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public enum SoundFX {

    ROUND_HIT("Round Hit"),
    HARD_HIT("Hard Hit"),
    BOMB("Bomb"),
    HEAL("Heal"),
    ITEM_FOUND("Item Found"),
    LEVEL_UP("LevelUp"),
    MENU_SELECT("Menu Select");

    private Clip clip;

    SoundFX(String fileName) {
        URL resource = Pokemon.class.getResource("/Resources/Sound/" + fileName + ".wav");
        try {
            // Use URL (instead of File) to read from disk and JAR.
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public Clip getClip() {
        return clip;
    }

    // Play sound effect from the beginning
    public void play() {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }
}
