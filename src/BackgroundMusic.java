import javax.sound.sampled.*;
import java.net.URL;

public enum BackgroundMusic {
    WELCOME("Welcome"),
    PALLET_TOWN("Pallet Town"),
    OAK_LAB("Oak Lab"),
    RIVAL_APPEARS("Rival Appears"),
    ROAD_TO_VIRIDIAN("Road to Viridian"),
    WILD_BATTLE("Wild Battle"),
    WILD_VICTORY("Wild Victory"),
    PEWTER_CITY("Pewter City"),
    POKEMON_CENTER("Pokemon Center"),
    VIRIDIAN_FOREST("Viridian Forest"),
    TRAINER_BATTLE("Trainer Battle"),
    TRAINER_VICTORY("Trainer Victory"),
    MT_MOON("Mt Moon"),
    ROAD_CERULEAN_TO_MT_MOON("Road Cerulean to Mt Moon"),
    CERULEAN_THEME("Cerulean Theme"),
    GYM("Gym"),
    TO_LIGHTHOUSE("To Lighthouse"),
    VERMILLION_THEME("Vermillion Theme"),
    SS_ANNE("SS Anne"),
    GYM_LEADER_BATTLE("Gym Leader Battle"),
    GYM_LEADER_VICTORY("Gym Leader Victory"),
    POKEFLUTE("PokeFlute"),
    CYCLING("Cycling"),
    LAVENDER_THEME("Lavender Theme"),
    POKEMON_TOWER("Pokemon Tower"),
    CELADON_THEME("Celadon Theme"),
    CASINO_THEME("Casino Theme"),
    TEAM_ROCKET_BATTLE("Team Rocket Battle"),
    TEAM_ROCKET_HIDEOUT("Team Rocket Hideout"),
    SILPH_COMPANY("Silph Company"),
    OCEAN("Ocean"),
    CINNABAR_THEME("Cinnabar Theme"),
    POKEMON_MANSION("Pokemon Mansion"),
    VICTORY_ROAD("Victory Road"),
    CHAMPION_BATTLE("Champion Battle"),
    ENTERING_HALL_OF_FAME("Entering Hall of Fame"),
    ENDING("Ending"),
    JIGGLYPUFF_SONG("Jigglypuff Song");

    private Clip clip;
    private static Clip currentMusic;

    BackgroundMusic(String fileName) {
        URL resource = Pokemon.class.getResource("/Resources/Sound/" + fileName + ".wav");
        try {
            // Use URL (instead of File) to read from disk and JAR.
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Clip getClip() {
        return clip;
    }

    // Play sound effect from the beginning
    public void play() {
        if (this.getClip() != currentMusic) { // Only restarts music if different
            for (int i = 0; i < BackgroundMusic.values().length; i++) {
                if (BackgroundMusic.values()[i].getClip().isRunning()) {
                    BackgroundMusic.values()[i].getClip().stop();
                }
            }
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            currentMusic = clip;
        }
    }

    public static void stop() {
        for (int i = 0; i < BackgroundMusic.values().length; i++) {
            if (BackgroundMusic.values()[i].getClip().isRunning()) {
                BackgroundMusic.values()[i].getClip().stop();
            }
        }
    }
}
