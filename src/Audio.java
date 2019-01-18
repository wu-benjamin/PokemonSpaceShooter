import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.net.URL;

public enum Audio {
    WELCOME("Welcome"),
    PALLET_TOWN("Pallet Town"),
    OAK_LAB("Oak Lab"),
    RIVAL_APPEARS("Rival Appears"),
    ROAD_TO_VIRIDIAN("Road to Viridian"),
    WILD_BATTLE("Wild Battle"),
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
    JIGGLYPUFF_SONG("Jigglypuff Song"),
    VERMILLION_THEME("Vermillion Theme"),
    SS_ANNE("SS Anne"),
    POKEFLUTE("PokeFlute"),
    GYM_LEADER_BATTLE("Gym Leader Battle"),
    GYM_LEADER_VICTORY("Gym Leader Victory"),
    CYCLING("Cycling"),
    LAVENDER_THEME("Lavender Theme"),
    POKEMON_TOWER("Pokemon Tower"),
    CELADON_THEME("Celadon Theme"),
    CASINO_THEME("Casino Theme"),
    TEAM_ROCKET_BATTLE("Team Rocket Battle"),
    TEAM_ROCKET_HIDEOUT("Team Rocket Hideout"),
    SYLPH_COMPANY("Sylph Company"),
    OCEAN("Ocean"),
    CINNABAR_THEME("Cinnabar Theme"),
    POKEMON_MANSION("Pokemon Mansion"),
    VICTORY_ROAD("Victory Road"),
    CHAMPION_BATTLE("Champion Battle"),
    ENTERING_HALL_OF_FAME("Entering Hall of Fame"),
    ENDING("Ending"),
    ROUND_HIT("Round Hit"),
    HARD_HIT("Hard Hit"),
    BOMB("Bomb"),
    HEAL("Heal"),
    ITEM_FOUND("Item Found"),
    LEVEL_UP("LevelUp"),
    MENU_SELECT("Menu Select");


    private AudioInputStream audioIn;

    Audio(String fileName) {
        URL resource = Pokemon.class.getResource("/Resources/Sound/" + fileName + ".wav");
        try {
            this.audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(new File(resource.toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AudioInputStream getAudioIn() {
        return audioIn;
    }
}
