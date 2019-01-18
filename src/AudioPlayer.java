import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class AudioPlayer {

    private static Clip backgroundMusic;
    private static Clip soundFX;

    static {
        try {
            backgroundMusic = javax.sound.sampled.AudioSystem.getClip();
            soundFX = javax.sound.sampled.AudioSystem.getClip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setBackgroundMusic(AudioInputStream audioIn) {
        backgroundMusic.stop();
        backgroundMusic.close();
        try {
            backgroundMusic.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void playSoundFX(AudioInputStream audioIn) {
        try {
            //soundFX.open(audioIn);
            //soundFX.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
