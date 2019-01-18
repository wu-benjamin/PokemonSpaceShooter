import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public enum Location {

    PALLET      (0, "Pallet Town", 1, Pokemon.EEVEE, "Grass", "Your friend just got their first Pokemon too!\nTime for your first battle!", "Congratulations on your first win!", BackgroundMusic.RIVAL_APPEARS, BackgroundMusic.PALLET_TOWN, BackgroundMusic.TRAINER_VICTORY),
    ROUTE1      (1, "Route 1", 25, Pokemon.SPEAROW, "Grass", "Head north and begin your journey!", "Good job!", BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.WILD_BATTLE, BackgroundMusic.WILD_VICTORY, Pokemon.PIDGEY, Pokemon.RATTATA),
    ROUTE22     (2, "Route 22", 10, Pokemon.MANKEY, "Grass", "Explore Route 22 west of Viridian!", "Excellent findings!", BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.WILD_VICTORY, Pokemon.RATTATA, Pokemon.NIDORANF, Pokemon.NIDORANM, Pokemon.SPEAROW),
    ROUTE2      (3, "Route 2", 25, Pokemon.BUTTERFREE, "Grass", "Head north to Viridian Forest!", "You arrived at the forest!", BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.WILD_BATTLE, BackgroundMusic.WILD_VICTORY, Pokemon.RATTATA, Pokemon.PIDGEY, Pokemon.WEEDLE, Pokemon.CATERPIE, Pokemon.NIDORANF, Pokemon.NIDORANM),
    FOREST      (4, "Viridian Forest", 40, Pokemon.BEEDRILL, "Forest", "Cross through the Viridian Forest!", "You made it to Pewter City!", BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.VIRIDIAN_FOREST, BackgroundMusic.PEWTER_CITY, Pokemon.PIDGEY, Pokemon.PIDGEOTTO, Pokemon.CATERPIE, Pokemon.METAPOD, Pokemon.WEEDLE, Pokemon.KAKUNA, Pokemon.PIKACHU),
    PEWTER      (5, "Pewter City Gym", 25, Pokemon.ONIX, "Sahara", "Earn your first badge\nby defeating Gym Leader Brock!", "You earned the Boulder Badge!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.GEODUDE, Pokemon.SANDSHREW),
    ROUTE3      (6, "Route 3", 25, Pokemon.JIGGLYPUFF, "DryGrass", "Head west to Mount Moon!", "You made it to Mount Moon!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.TRAINER_BATTLE, BackgroundMusic.POKEMON_CENTER, Pokemon.PIDGEY, Pokemon.SPEAROW, Pokemon.RATTATA, Pokemon.SANDSHREW, Pokemon.MANKEY),
    MOON        (7, "Mt. Moon", 50, Pokemon.CLEFABLE, "Cave", "Navigate through Mount Moon!", "You made it through Mount Moon!", BackgroundMusic.MT_MOON, BackgroundMusic.MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, Pokemon.ZUBAT,Pokemon.GEODUDE, Pokemon.PARAS, Pokemon.SANDSHREW, Pokemon.CLEFAIRY),
    ROUTE4      (8, "Route 4", 10, Pokemon.EKANS, "Grass", "Head west to Cerulean City!", "Good job!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.CERULEAN_THEME, Pokemon.RATTATA, Pokemon.SPEAROW, Pokemon.MANKEY, Pokemon.SANDSHREW),
    ROUTE24     (9, "Route 24", 30, Pokemon.CHARMANDER, "Grass", "Cross Nugget Bridge!", "You made it to the other side!", BackgroundMusic.TO_LIGHTHOUSE, BackgroundMusic.TRAINER_BATTLE, BackgroundMusic.TRAINER_VICTORY, Pokemon.WEEDLE, Pokemon.CATERPIE, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.PIDGEY, Pokemon.KAKUNA, Pokemon.METAPOD, Pokemon.ABRA, Pokemon.VENONAT),
    ROUTE25     (10, "Route 25", 30, Pokemon.BULBASAUR, "Grass", "Visit Benjamin Wu and Richard Duan\nat the lighthouse!", "You found out Benjamin Wu made this game!", BackgroundMusic.TO_LIGHTHOUSE, BackgroundMusic.TRAINER_BATTLE, BackgroundMusic.TRAINER_VICTORY, Pokemon.WEEDLE, Pokemon.CATERPIE, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.PIDGEY, Pokemon.KAKUNA, Pokemon.METAPOD, Pokemon.ABRA, Pokemon.VENONAT),
    CERULEAN    (11, "Cerulean City Gym", 30, Pokemon.STARMIE, "Pool", "Fight Gym Leader Misty!", "You earned the Cascade Badge!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.STARYU, Pokemon.GOLDEEN, Pokemon.PSYDUCK, Pokemon.MAGIKARP),
    ROUTE5      (12, "Route 5", 10, Pokemon.MEOWTH, "Grass", "Head south to Vermilion City!", "Good job!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.WILD_VICTORY, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.PIDGEY, Pokemon.RATTATA, Pokemon.MANKEY, Pokemon.ABRA, Pokemon.JIGGLYPUFF, Pokemon.PIDGEOTTO),
    ROUTE6      (13, "Route 6", 25, Pokemon.GOLDUCK, "Grass", "Continue south to Vermilion City!", "You reached Vermilion City!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.VERMILLION_THEME, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.PIDGEY, Pokemon.RATTATA, Pokemon.MANKEY, Pokemon.ABRA, Pokemon.JIGGLYPUFF, Pokemon.PIDGEOTTO, Pokemon.PSYDUCK),
    ROUTE11     (14, "Route 11", 15, Pokemon.HYPNO, "Grass", "Head west to\ncheck out Diglett's Cave!", "Good job!", BackgroundMusic.VERMILLION_THEME, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.WILD_VICTORY, Pokemon.EKANS, Pokemon.SANDSHREW, Pokemon.SPEAROW, Pokemon.DROWZEE, Pokemon.PIDGEY, Pokemon.RATTATA, Pokemon.RATICATE, Pokemon.PIDGEOTTO),
    DIGLETT     (15, "Diglett's Cave", 45, Pokemon.DUGTRIO, "Cave", "Explore Diglett's Cave!", "That was fun!", BackgroundMusic.VIRIDIAN_FOREST, BackgroundMusic.VIRIDIAN_FOREST, BackgroundMusic.WILD_VICTORY, Pokemon.DIGLETT),
    ANNE        (16, "S.S.Anne", 70, Pokemon.SQUIRTLE, "Carpet", "Relax aboard the S.S.Anne!", "That was an awesome ship ride!", BackgroundMusic.VERMILLION_THEME, BackgroundMusic.SS_ANNE, BackgroundMusic.SS_ANNE, Pokemon.RATICATE, Pokemon.MACHOP, Pokemon.MAGIKARP, Pokemon.MANKEY, Pokemon.MAGNETMITE, Pokemon.SHELLDER, Pokemon.KRABBY, Pokemon.TENTACOOL, Pokemon.HORSEA),
    VERMILION   (17, "Vermilion City Gym", 40, Pokemon.RAICHU, "Sahara", "Battle the electrifying\nGym Leader Lieutenant Surge!", "You earned the Thunder Badge!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.PIKACHU, Pokemon.MAGNETMITE, Pokemon.VOLTORB),
    ROUTE9      (18, "Route 9", 45, Pokemon.FEAROW, "DirtPath", "Head east to the Rock Tunnel!", "You reached the Rock Tunnel!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.POKEMON_CENTER, Pokemon.RATTATA, Pokemon.SPEAROW, Pokemon.EKANS, Pokemon.SANDSHREW, Pokemon.RATICATE, Pokemon.NIDORANM, Pokemon.NIDORANF, Pokemon.NIDORINA, Pokemon.NIDORINO),
    TUNNEL      (19, "Rock Tunnel", 75, Pokemon.GOLBAT, "Cave", "Hike through the Rock Tunnel!", "You made it through the Rock Tunnel!", BackgroundMusic.VIRIDIAN_FOREST, BackgroundMusic.VIRIDIAN_FOREST, BackgroundMusic.WILD_VICTORY, Pokemon.ZUBAT, Pokemon.GEODUDE, Pokemon.MACHOP, Pokemon.ONIX),
    POWER       (20, "Power Plant", 45,Pokemon.ZAPDOS, "Metal", "Explore the forgotten power plant!", "You found some electrifying Pokemon!", BackgroundMusic.SILPH_COMPANY, BackgroundMusic.SILPH_COMPANY, BackgroundMusic.WILD_VICTORY, Pokemon.VOLTORB, Pokemon.MAGNETMITE, Pokemon.MAGNETON, Pokemon.ELECTABUZZ, Pokemon.RAICHU, Pokemon.GRIMER, Pokemon.MUK, Pokemon.ELECTRODE),
    ROUTE10     (21, "Route 10", 25, Pokemon.MACHOKE, "DryPath", "Head south towards Lavender Town!", "You've made it to Lavender Town!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.LAVENDER_THEME, Pokemon.VOLTORB, Pokemon.SPEAROW, Pokemon.EKANS, Pokemon.SANDSHREW, Pokemon.MAGNETMITE, Pokemon.RATTATA, Pokemon.NIDORANF, Pokemon.NIDORANM, Pokemon.RATICATE, Pokemon.MACHOP),
    ROUTE8      (22, "Route 8", 50, Pokemon.KADABRA, "Grass", "Head east to Celadon City!", "Good job!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.WILD_VICTORY, Pokemon.PIDGEY, Pokemon.PIDGEOTTO, Pokemon.EKANS, Pokemon.SANDSHREW, Pokemon.VULPIX, Pokemon.JIGGLYPUFF, Pokemon.MANKEY, Pokemon.MEOWTH, Pokemon.GROWLITHE, Pokemon.ABRA),
    ROUTE7      (23, "Route 7", 30, Pokemon.WIGGLYTUFF, "Grass", "Continue east to Celadon City!", "You made it to Celadon City!", BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.WILD_BATTLE, BackgroundMusic.CELADON_THEME, Pokemon.PIDGEY, Pokemon.PIDGEOTTO, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.MEOWTH, Pokemon.GROWLITHE, Pokemon.VULPIX, Pokemon.ABRA),
    HIDEOUT     (24, "Team Rocket's Hideout", 100, Pokemon.KANGASKHAN, "Metal", "Woah! Time to explore\nTeam Rocket's Hideout!", "You made Team Rocket blast off again!", BackgroundMusic.CASINO_THEME, BackgroundMusic.TEAM_ROCKET_BATTLE, BackgroundMusic.TEAM_ROCKET_HIDEOUT, Pokemon.KOFFING, Pokemon.GRIMER, Pokemon.EKANS, Pokemon.NIDORINA, Pokemon.NIDORINO, Pokemon.ZUBAT, Pokemon.PORYGON),
    ROUTE16     (25, "Route 16", 40, Pokemon.SNORLAX, "Grass", "Explore the wilderness!", "Good exploring!", BackgroundMusic.POKEFLUTE, BackgroundMusic.WILD_BATTLE, BackgroundMusic.WILD_VICTORY,  Pokemon.SPEAROW, Pokemon.RATTATA, Pokemon.DODUO, Pokemon.RATICATE, Pokemon.FEAROW),
    CELEDON     (26, "Celadon City Gym", 60, Pokemon.VENUSAUR, "Grass", "Fight the grass Gym Leader Erika!", "You earned the Rainbow Badge!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.VICTREEBEL, Pokemon.VILEPLUME, Pokemon.BELLSPROUT, Pokemon.WEEPINBELL, Pokemon.ODDISH, Pokemon.GLOOM, Pokemon.TANGELA, Pokemon.IVYSAUR, Pokemon.BULBASAUR),
    DOJO        (27, "Dojo", 55, Pokemon.PRIMEAPE, "StoneWall", "Train at the dojo!", "Good job!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.HITMONCHAN, Pokemon.HITMONLEE, Pokemon.MANKEY, Pokemon.MACHOP, Pokemon.MACHOKE, Pokemon.RHYHORN, Pokemon.GRAVELER),
    SAFFRON     (28, "Saffron City Gym", 70, Pokemon.ALAKAZAM, "GoldByzantine", "Defeat Sabrina,\nthe psychic Gym Leader!", "You earned the Soul Badge!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.ABRA, Pokemon.KADABRA, Pokemon.JYNX, Pokemon.MRMIME, Pokemon.PSYDUCK, Pokemon.GOLDUCK, Pokemon.VENOMOTH, Pokemon.VENONAT, Pokemon.SLOWBRO, Pokemon.SLOWPOKE),
    SILPH       (29, "Silph Co.", 130, Pokemon.PERSIAN, "Carpet", "Thwart Team Rocket!\nEarn new Silph Co. tech!", "You made Team Rocket blast off again!", BackgroundMusic.SILPH_COMPANY, BackgroundMusic.TEAM_ROCKET_BATTLE, BackgroundMusic.TRAINER_VICTORY, Pokemon.LAPRAS, Pokemon.NIDOKING, Pokemon.NIDOQUEEN, Pokemon.NIDORINA, Pokemon.NIDORINO, Pokemon.NIDORANF, Pokemon.NIDORANM, Pokemon.ZUBAT, Pokemon.GOLBAT, Pokemon.KANGASKHAN, Pokemon.SANDSHREW, Pokemon.SANDSLASH, Pokemon.KOFFING, Pokemon.GRIMER, Pokemon.VOLTORB, Pokemon.EKANS, Pokemon.ARBOK, Pokemon.RHYHORN),
    TOWER       (30, "Pokemon Tower", 75, Pokemon.MAROWAK, "Tower", "Investigate the spooky Pokemon Tower\nwith your new tech!", "That was spooky!", BackgroundMusic.LAVENDER_THEME, BackgroundMusic.POKEMON_TOWER, BackgroundMusic.JIGGLYPUFF_SONG, Pokemon.HAUNTER, Pokemon.GASTLY, Pokemon.CUBONE),
    ROUTE12     (31, "Route 12", 70, Pokemon.SNORLAX, "Brick", "Wake Snorlax and\nhead to Fuchsia City!", "Good job!", BackgroundMusic.POKEFLUTE, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.TRAINER_VICTORY, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.PIDGEOTTO, Pokemon.PIDGEY, Pokemon.VENONAT, Pokemon.GLOOM, Pokemon.WEEPINBELL, Pokemon.FARFETCHD, Pokemon.TENTACOOL, Pokemon.KRABBY, Pokemon.HORSEA, Pokemon.SEADRA, Pokemon.POLIWAG, Pokemon.GOLDEEN, Pokemon.MAGIKARP),
    ROUTE13     (32, "Route 13", 75, Pokemon.DITTO, "Brick", "Navigate the maze!", "You made it through the maze!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.TRAINER_BATTLE, BackgroundMusic.TRAINER_VICTORY, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.PIDGEOTTO, Pokemon.PIDGEY, Pokemon.VENONAT, Pokemon.GLOOM, Pokemon.WEEPINBELL, Pokemon.FARFETCHD, Pokemon.TENTACOOL, Pokemon.KRABBY, Pokemon.HORSEA, Pokemon.SEADRA, Pokemon.POLIWAG, Pokemon.GOLDEEN, Pokemon.MAGIKARP),
    ROUTE14     (33, "Route 14", 50, Pokemon.VENOMOTH, "Brick", "Head south towards Fuchsia City!", "Good job!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.WILD_VICTORY, Pokemon.DITTO, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.VENONAT, Pokemon.PIDGEY, Pokemon.PIDGEOTTO, Pokemon.GLOOM, Pokemon.WEEPINBELL),
    ROUTE15     (34, "Route 15", 70, Pokemon.PIDGEOT,  "Brick", "Head east to Fuchsia City!", "Let's check out the bridge\nbeside Fuchsia City!", BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.ROAD_CERULEAN_TO_MT_MOON, BackgroundMusic.CERULEAN_THEME, Pokemon.DITTO, Pokemon.ODDISH, Pokemon.BELLSPROUT, Pokemon.VENONAT, Pokemon.PIDGEY, Pokemon.PIDGEOTTO, Pokemon.GLOOM, Pokemon.WEEPINBELL),
    ROUTE17     (35, "Route 17", 80, Pokemon.RAPIDASH, "Brick", "Bike across the bridge beside Fuchsia City!", "That's a cool bridge!", BackgroundMusic.CYCLING, BackgroundMusic.CYCLING, BackgroundMusic.TRAINER_VICTORY, Pokemon.SPEAROW, Pokemon.RATICATE, Pokemon.DODUO, Pokemon.FEAROW, Pokemon.PONYTA, Pokemon.DODRIO),
    ROUTE18     (36, "Route 18", 20, Pokemon.GYARADOS, "LightSand", "Enjoy the view of the sea!", "The sea is majestic!", BackgroundMusic.OCEAN, BackgroundMusic.OCEAN, BackgroundMusic.WILD_VICTORY, Pokemon.POLIWAG, Pokemon.POLIWHIRL, Pokemon.POLIWRATH, Pokemon.MAGIKARP, Pokemon.SPEAROW, Pokemon.DODUO, Pokemon.RATICATE, Pokemon.RATTATA, Pokemon.FEAROW),
    SAFARI      (37, "Safari Zone", 80, Pokemon.CHANSEY, "Grass", "Catch new Pokemon in the Safari!\nFind the warden's dentures!", "You found many new Pokemon\nand the warden's dentures!", BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.WILD_VICTORY, Pokemon.NIDORANM, Pokemon.NIDORANF, Pokemon.EXEGGCUTE, Pokemon.RHYHORN, Pokemon.VENONAT, Pokemon.NIDORINO, Pokemon.NIDORINA, Pokemon.PARASECT, Pokemon.SCYTHER, Pokemon.PINSIR, Pokemon.PARAS, Pokemon.TANGELA, Pokemon.DODUO, Pokemon.KANGASKHAN, Pokemon.CUBONE, Pokemon.TAUROS, Pokemon.MAROWAK, Pokemon.VENOMOTH, Pokemon.MAGIKARP, Pokemon.POLIWAG, Pokemon.GOLDEEN, Pokemon.PSYDUCK, Pokemon.SLOWPOKE, Pokemon.KRABBY, Pokemon.DRATINI, Pokemon.DRAGONAIR),
    FUCHSIA     (38, "Fuchsia City Gym", 90, Pokemon.WEEZING, "StoneWall", "Fight the ninja master\nGym Leader Koga!", "You earned the Marsh Badge!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.VENOMOTH, Pokemon.VENONAT, Pokemon.KOFFING, Pokemon.MUK, Pokemon.GRIMER, Pokemon.EKANS, Pokemon.ARBOK),
    ROUTE19     (39, "Route 19", 100, Pokemon.TENTACRUEL, "Sea", "Head out to the sea!\nAdventure awaits!", "The sea is huge!", BackgroundMusic.OCEAN, BackgroundMusic.OCEAN, BackgroundMusic.WILD_VICTORY, Pokemon.TENTACOOL, Pokemon.MAGIKARP, Pokemon.POLIWAG, Pokemon.GOLDEEN, Pokemon.SHELLDER, Pokemon.HORSEA, Pokemon.STARYU, Pokemon.SQUIRTLE, Pokemon.WARTORTLE),
    SEAFOAM     (40, "Seafoam Islands", 120, Pokemon.ARTICUNO, "Icy", "Explore the depths of\nthe Seafoam Islands!", "You found some cool Pokemon!", BackgroundMusic.OCEAN, BackgroundMusic.VIRIDIAN_FOREST, BackgroundMusic.WILD_VICTORY, Pokemon.SEEL, Pokemon.HORSEA, Pokemon.KRABBY, Pokemon.SHELLDER, Pokemon.STARYU, Pokemon.SLOWBRO, Pokemon.SLOWPOKE, Pokemon.ZUBAT, Pokemon.PSYDUCK, Pokemon.GOLDUCK, Pokemon.GOLBAT, Pokemon.DEWGONG, Pokemon.SEADRA, Pokemon.KINGLER, Pokemon.SQUIRTLE, Pokemon.WARTORTLE, Pokemon.BLASTOISE),
    ROUTE20     (41, "Route 20", 100, Pokemon.MISSINGNO, "Sea", "Head east to Cinnabar!", "You made it to Cinnabar Island!", BackgroundMusic.OCEAN, BackgroundMusic.OCEAN, BackgroundMusic.CINNABAR_THEME, Pokemon.GYARADOS, Pokemon.TENTACOOL, Pokemon.MAGIKARP, Pokemon.POLIWAG, Pokemon.GOLDEEN, Pokemon.SHELLDER, Pokemon.HORSEA, Pokemon.STARYU, Pokemon.TENTACRUEL),
    MANSION     (42, "Pokemon Mansion", 110, Pokemon.MEW, "Carpet", "Investigate the lost experiments\nof the Pokemon Mansion!", "Excellent investigating!", BackgroundMusic.CINNABAR_THEME, BackgroundMusic.POKEMON_MANSION, BackgroundMusic.CINNABAR_THEME, Pokemon.MAGMAR, Pokemon.PONYTA, Pokemon.KOFFING, Pokemon.GROWLITHE, Pokemon.VULPIX, Pokemon.GRIMER, Pokemon.WEEZING, Pokemon.MUK, Pokemon.RATTATA, Pokemon.RATICATE, Pokemon.DITTO, Pokemon.KABUTO, Pokemon.KABUTOPS, Pokemon.OMANYTE, Pokemon.OMASTAR, Pokemon.AERODACTYL),
    CINNABAR    (43, "Cinnabar Island Gym", 100, Pokemon.ARCANINE, "StoneWall", "Battle the master of\nfire type Pokemon: Blaine!", "You earned the Volcano Badge!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY,Pokemon.MAGMAR, Pokemon.RAPIDASH, Pokemon.PONYTA, Pokemon.GROWLITHE, Pokemon.VULPIX, Pokemon.NINETALES, Pokemon.KOFFING, Pokemon.WEEZING, Pokemon.GRIMER, Pokemon.MUK, Pokemon.VOLTORB, Pokemon.ELECTRODE, Pokemon.MAGNETMITE, Pokemon.MAGNETON, Pokemon.CHARIZARD, Pokemon.CHARMANDER, Pokemon.CHARMELEON),
    ROUTE21     (44, "Route 21", 90, Pokemon.SEAKING, "Lake", "Head north towards Pallet Town!\nYour journey is coming full circle!", "Time for your final gym battle!", BackgroundMusic.CINNABAR_THEME, BackgroundMusic.OCEAN, BackgroundMusic.PALLET_TOWN, Pokemon.RATTATA, Pokemon.RATICATE, Pokemon.PIDGEY, Pokemon.PIDGEOTTO, Pokemon.TANGELA, Pokemon.TENTACOOL, Pokemon.MAGIKARP, Pokemon.POLIWAG, Pokemon.GOLDEEN, Pokemon.SHELLDER, Pokemon.HORSEA, Pokemon.STARYU, Pokemon.TENTACRUEL),
    VIRIDIAN    (45, "Viridian City Gym", 120, Pokemon.RHYDON, "DryPath", "Battle Giovanni: the last Gym Leader!", "You earned the Earth Badge!", BackgroundMusic.GYM, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.RHYHORN, Pokemon.DIGLETT, Pokemon.DUGTRIO, Pokemon.NIDOQUEEN, Pokemon.NIDOKING, Pokemon.KANGASKHAN, Pokemon.PERSIAN, Pokemon.MACHAMP, Pokemon.MACHOKE),
    ROUTE23     (46, "Route 23", 60, Pokemon.SLOWBRO, "Brick", "Head north to take your shot\nto earn a shot at entering the\nHall of Fame!", "You made it to Victory Road", BackgroundMusic.ROAD_TO_VIRIDIAN, BackgroundMusic.VIRIDIAN_FOREST, BackgroundMusic.VICTORY_ROAD, Pokemon.DITTO, Pokemon.FEAROW, Pokemon.EKANS, Pokemon.SANDSHREW, Pokemon.SPEAROW, Pokemon.ARBOK, Pokemon.SANDSLASH, Pokemon.NIDORINO, Pokemon.NIDORINA, Pokemon.MANKEY, Pokemon.PRIMEAPE, Pokemon.MAGIKARP, Pokemon.POLIWAG, Pokemon.GOLDEEN, Pokemon.SLOWBRO, Pokemon.KINGLER, Pokemon.SEADRA, Pokemon.SEAKING, Pokemon.POLIWHIRL),
    VICTORY     (47, "Victory Road", 150, Pokemon.MOLTRES, "Cave", "Welcome to Victory Road:\nthe last leg of your journey!", "You made it to the Indigo Plateau!", BackgroundMusic.VICTORY_ROAD, BackgroundMusic.VICTORY_ROAD, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.GOLEM, Pokemon.VENOMOTH, Pokemon.GRAVELER, Pokemon.MAROWAK, Pokemon.MACHOKE, Pokemon.GOLBAT, Pokemon.GEODUDE, Pokemon.ZUBAT, Pokemon.MACHOP, Pokemon.ONIX, Pokemon.CUBONE, Pokemon.MAROWAK),
    LORELEI     (48, "Elite Four Lorelei", 200, Pokemon.LAPRAS, "Glacier", "It's finally time to challenge\nthe Elite Four!\nFirst up is Lorelei!", "Good job!", BackgroundMusic.VICTORY_ROAD, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.DEWGONG, Pokemon.CLOYSTER, Pokemon.SLOWBRO, Pokemon.JYNX),
    BRUNO       (49, "Elite Four Bruno", 210, Pokemon.MACHAMP, "StoneWall", "Up next is Elite Four: Bruno!", "Keep it up champ to be!", BackgroundMusic.VICTORY_ROAD, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.ONIX, Pokemon.HITMONLEE, Pokemon.HITMONCHAN, Pokemon.PRIMEAPE),
    AGATHA      (50, "Elite Four Agatha", 220, Pokemon.GENGAR, "Tower", "Agatha of the Elite Four challenges you!", "You're almost there!", BackgroundMusic.VICTORY_ROAD, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.GYM_LEADER_VICTORY, Pokemon.HAUNTER, Pokemon.ARBOK, Pokemon.GOLBAT),
    LANCE       (51, "Elite Four Lance", 230, Pokemon.DRAGONITE, "GoldByzantine", "The final member of the\nElite Four: Lance", "You did it!", BackgroundMusic.VICTORY_ROAD, BackgroundMusic.GYM_LEADER_BATTLE, BackgroundMusic.ENTERING_HALL_OF_FAME, Pokemon.GYARADOS, Pokemon.DRAGONAIR, Pokemon.AERODACTYL),
    CHAMPION    (52, "Champion", 240, Pokemon.EEVEE, "Palace", "Woah! Your rival defeated the Elite Four first!\nDefeat him to claim the title of Champion!", "Congratulations, Champion!", BackgroundMusic.RIVAL_APPEARS, BackgroundMusic.CHAMPION_BATTLE, BackgroundMusic.ENDING, Pokemon.JOLTEON, Pokemon.VAPOREON, Pokemon.FLAREON, Pokemon.PIDGEOT, Pokemon.ALAKAZAM, Pokemon.RHYDON, Pokemon.GYARADOS, Pokemon.EXEGGUTOR, Pokemon.ARCANINE, Pokemon.BLASTOISE, Pokemon.CHARIZARD, Pokemon.VENUSAUR),
    MEWTWO      (53, "Cerulean Cave", 250, Pokemon.MEWTWO, "Cave", "Explore unknowns as the Pokemon Master!", "Excellent exploring!", BackgroundMusic.CERULEAN_THEME, BackgroundMusic.VIRIDIAN_FOREST, BackgroundMusic.ENDING, Pokemon.GOLBAT, Pokemon.MAGNETON, Pokemon.HYPNO, Pokemon.ARBOK, Pokemon.SANDSLASH, Pokemon.VENOMOTH, Pokemon.DODRIO, Pokemon.PARASECT, Pokemon.KADABRA, Pokemon.RAICHU, Pokemon.DITTO, Pokemon.GRAVELER, Pokemon.GLOOM, Pokemon.WEEPINBELL, Pokemon.ELECTRODE, Pokemon.MAROWAK, Pokemon.RHYDON, Pokemon.WIGGLYTUFF, Pokemon.CHANSEY, Pokemon.RHYHORN, Pokemon.LICKITUNG, Pokemon.MAGIKARP, Pokemon.POLIWAG, Pokemon.GOLDEEN, Pokemon.SLOWBRO, Pokemon.KRABBY, Pokemon.SEADRA, Pokemon.SEAKING);

    private int levelIndex;
    private String levelName;
    private int numberOfEnemies;
    private Pokemon[] enemy;
    private Pokemon boss;
    private BufferedImage background;
    private String intro;
    private String outro;
    private BackgroundMusic introMusic;
    private BackgroundMusic levelMusic;
    private BackgroundMusic outroMusic;

    Location(int levelIndex, String levelName, int numberOfRegEnemies, Pokemon boss, String backgroundName,
            String intro, String outro, BackgroundMusic introMusic, BackgroundMusic levelMusic, BackgroundMusic outroMusic, Pokemon... enemy) {
        this.levelName = levelName;
        URL resource = Pokemon.class.getResource("/Resources/Locations/" + backgroundName + ".png");
        try {
            this.background = ImageIO.read(new File(resource.toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.levelIndex = levelIndex;
        this.numberOfEnemies = numberOfRegEnemies;
        this.enemy = enemy;
        this.boss = boss;
        this.intro = intro;
        this.outro = outro;
        this.introMusic = introMusic;
        this.levelMusic = levelMusic;
        this.outroMusic = outroMusic;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public Pokemon[] getEnemies() {
        return enemy;
    }

    public Pokemon getBoss() {
        return boss;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getLevelIndex() { return levelIndex; }

    public String getIntro() {
        return intro;
    }

    public String getOutro() {
        return outro;
    }

    public BackgroundMusic getIntroMusic() {
        return introMusic;
    }
    public BackgroundMusic getLevelMusic() {
        return levelMusic;
    }
    public BackgroundMusic getOutroMusic() {
        return outroMusic;
    }
}
