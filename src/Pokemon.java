import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public enum Pokemon {
    MISSINGNO   (0, "MissingNo", Type.UNKNOWN, Type.NONE, Attack.HYPERBEAM, 100, 100, 100, 100, 100),
    BULBASAUR   (1, "Bulbasaur", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 45, 49, 49, 45, 65),
    IVYSAUR     (2, "Ivysaur", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 60, 62, 63, 60, 80),
    VENUSAUR    (3, "Venusaur", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 80, 82, 83, 80, 100),
    CHARMANDER  (4, "Charmander", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 39, 52, 43, 65, 50),
    CHARMELEON  (5, "Charmeleon", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 58, 64, 58, 80, 65),
    CHARIZARD   (6, "Charizard", Type.FIRE, Type.FLYING, Attack.FLAMETHROWER, 78, 84, 78, 100, 85),
    SQUIRTLE    (7, "Squirtle", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 44, 48, 65, 43, 50),
    WARTORTLE   (8, "Wartortle", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 59, 63, 80, 58, 65),
    BLASTOISE   (9, "Blastoise", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 79, 83, 100, 78, 85),
    CATERPIE    (10, "Caterpie", Type.BUG, Type.NONE, Attack.STRINGSHOT, 45, 30, 35, 45, 20),
    METAPOD     (11, "Metapod", Type.BUG, Type.NONE, Attack.STRINGSHOT, 50, 20,55, 30, 25),
    BUTTERFREE  (12, "Butterfree", Type.BUG, Type.FLYING, Attack.STRINGSHOT, 60, 45, 50, 70, 80),
    WEEDLE      (13, "Weedle", Type.BUG, Type.POISON, Attack.STRINGSHOT, 40, 35, 30, 50 ,20),
    KAKUNA      (14, "Kakuna", Type.BUG, Type.POISON, Attack.STRINGSHOT, 45, 25, 50, 35, 25),
    BEEDRILL    (15, "Beedrill", Type.BUG, Type.POISON, Attack.STRINGSHOT, 65, 80, 40, 75, 45),
    PIDGEY      (16, "Pidgey", Type.NORMAL, Type.FLYING, Attack.WINGATTACK, 40,45, 40, 56, 35),
    PIDGEOTTO   (17, "Pidgeotto", Type.NORMAL, Type.FLYING, Attack.WINGATTACK, 63, 60, 55, 71, 50),
    PIDGEOT     (18, "Pidgeot", Type.NORMAL, Type.FLYING, Attack.WINGATTACK, 83, 80, 75, 91, 70),
    RATTATA     (19, "Rattata", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 30, 56, 35, 72, 25),
    RATICATE    (20, "Raticate", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 55, 81, 60, 97, 50),
    SPEAROW     (21, "Spearow", Type.NORMAL, Type.FLYING, Attack.WINGATTACK, 40,60, 30, 70, 31),
    FEAROW      (22, "Fearow", Type.NORMAL, Type.FLYING, Attack.WINGATTACK, 65, 90, 65, 100, 61),
    EKANS       (23, "Ekans", Type.POISON, Type.NONE, Attack.POISONGAS, 35, 60, 44, 55, 40),
    ARBOK       (24, "Arbok", Type.POISON, Type.NONE, Attack.POISONGAS, 60, 85, 69, 80, 65),
    PIKACHU     (25, "Pikachu", Type.ELECTRIC, Type.NONE, Attack.THUNDERBOLT, 35, 55, 30, 90, 50),
    RAICHU      (26, "Raichu", Type.ELECTRIC, Type.NONE, Attack.THUNDERBOLT, 60, 90, 55, 100, 90),
    SANDSHREW   (27, "Sandshrew", Type.GROUND, Type.NONE, Attack.SANDATTACK, 50, 75, 85, 40, 30),
    SANDSLASH   (28, "Sandslash", Type.GROUND, Type.NONE, Attack.SANDATTACK, 75, 100, 110, 65, 55),
    NIDORANF    (29, "NidoranF", Type.POISON, Type.NONE, Attack.POISONGAS, 55, 47, 52, 41, 40),
    NIDORINA    (30, "Nidorina", Type.POISON, Type.NONE, Attack.POISONGAS, 70, 62, 67, 56, 55),
    NIDOQUEEN   (31, "Nidoqueen", Type.POISON, Type.GROUND, Attack.POISONGAS, 90, 82, 87, 76, 75),
    NIDORANM    (32, "NidoranM", Type.POISON, Type.NONE, Attack.POISONGAS, 46, 57, 40, 50, 40),
    NIDORINO    (33, "Nidorino", Type.POISON, Type.NONE, Attack.POISONGAS, 61, 72, 57, 65, 55),
    NIDOKING    (34, "Nidoking", Type.POISON, Type.GROUND, Attack.POISONGAS, 81, 92, 77, 85, 75),
    CLEFAIRY    (35, "Clefairy", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 70, 45, 48, 35, 60),
    CLEFABLE    (36, "Clefable", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 95, 70, 73, 60, 85),
    VULPIX      (37, "Vulpix", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 38, 41, 40, 65, 65),
    NINETALES   (38, "Ninetales", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 73, 76, 75, 100, 100),
    JIGGLYPUFF  (39, "Jigglypuff", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 115, 45, 20, 20, 25),
    WIGGLYTUFF  (40, "Wigglytuff", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 140, 70, 45, 45, 50),
    ZUBAT       (41, "Zubat", Type.POISON, Type.FLYING, Attack.WINGATTACK, 40, 45, 35, 55, 40),
    GOLBAT      (42, "Golbat", Type.POISON, Type.FLYING, Attack.WINGATTACK, 75, 80, 70, 90, 75),
    ODDISH      (43, "Oddish", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 45, 50, 55, 30, 75),
    GLOOM       (44, "Gloom", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 60, 65, 70, 40, 85),
    VILEPLUME   (45, "Vileplume", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 75, 80, 85, 50, 100),
    PARAS       (46, "Paras", Type.BUG, Type.GRASS, Attack.RAZORLEAF, 35, 70, 55, 25, 55),
    PARASECT    (47, "Parasect", Type.BUG, Type.GRASS, Attack.RAZORLEAF, 60, 95, 80, 30, 80),
    VENONAT     (48, "Venonat", Type.BUG, Type.POISON, Attack.POISONGAS, 60, 55, 50, 45, 40),
    VENOMOTH    (49, "Venomoth", Type.BUG, Type.POISON, Attack.POISONGAS, 70, 65, 60, 90, 90),
    DIGLETT     (50, "Diglett", Type.GROUND, Type.NONE, Attack.SANDATTACK, 10, 55, 25, 95, 45),
    DUGTRIO     (51, "Dugtrio", Type.GROUND, Type.NONE, Attack.SANDATTACK, 35, 80, 50, 120, 70),
    MEOWTH      (52, "Meowth", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 50, 45, 35, 90, 40),
    PERSIAN     (53, "Persian", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 65, 70, 60, 115, 65),
    PSYDUCK     (54, "Psyduck", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 50,52, 48, 55, 50),
    GOLDUCK     (55, "Golduck", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 80, 82, 78, 85, 80),
    MANKEY      (56, "Mankey", Type.FIGHTING, Type.NONE, Attack.SEISMICTOSS, 40, 80, 35, 70, 35),
    PRIMEAPE    (57, "Primeape", Type.FIGHTING, Type.NONE, Attack.SEISMICTOSS, 65, 105, 60, 95, 60),
    GROWLITHE   (58, "Growlithe", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 55, 70, 45, 60, 50),
    ARCANINE    (59, "Arcanine", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 90, 110, 80, 95, 80),
    POLIWAG     (60, "Poliwag", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 40, 50, 40, 90, 40),
    POLIWHIRL   (61, "Poliwhirl", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 65, 65, 65, 90, 50),
    POLIWRATH   (62, "Poliwrath", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 90, 85, 95, 70, 70),
    ABRA        (63, "Abra", Type.PSYCHIC, Type.NONE, Attack.PSYCHIC, 25, 20, 15, 90, 105),
    KADABRA     (64, "Kadabra", Type.PSYCHIC, Type.NONE, Attack.PSYCHIC, 40, 35, 30, 105, 120),
    ALAKAZAM    (65, "Alakazam", Type.PSYCHIC, Type.NONE, Attack.PSYCHIC, 55, 50, 45, 120, 135),
    MACHOP      (66, "Machop", Type.FIGHTING, Type.NONE, Attack.SEISMICTOSS, 70, 80, 50, 35, 35),
    MACHOKE     (67, "Machoke", Type.FIGHTING, Type.NONE, Attack.SEISMICTOSS, 80, 100, 70, 45, 50),
    MACHAMP     (68, "Machamp", Type.FIGHTING, Type.NONE, Attack.SEISMICTOSS, 90, 130, 80, 55, 65),
    BELLSPROUT  (69, "Bellsprout", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 50, 75, 35, 40, 70),
    WEEPINBELL  (70, "Weepinbell", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 65, 90, 50, 55, 85),
    VICTREEBEL  (71, "Victreebel", Type.GRASS, Type.POISON, Attack.RAZORLEAF, 80, 105, 65, 70, 100),
    TENTACOOL   (72, "Tentacool", Type.WATER, Type.POISON, Attack.BUBBLEBEAM, 40, 40, 35, 70, 100),
    TENTACRUEL  (73, "Tentacruel", Type.WATER, Type.POISON, Attack.BUBBLEBEAM, 80, 70, 65, 100, 120),
    GEODUDE     (74, "Geodude", Type.ROCK, Type.GROUND, Attack.ROCKTHROW, 40, 80, 100, 20, 30),
    GRAVELER    (75, "Graveler", Type.ROCK, Type.GROUND, Attack.ROCKTHROW, 55, 95, 115, 35, 45),
    GOLEM       (76, "Golem", Type.ROCK, Type.GROUND, Attack.ROCKTHROW, 80, 110, 130, 45, 55),
    PONYTA      (77, "Ponyta", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 50, 85, 55, 90, 65),
    RAPIDASH    (78, "Rapidash", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 65, 100, 70, 105, 80),
    SLOWPOKE    (79, "Slowpoke", Type.WATER, Type.PSYCHIC, Attack.BUBBLEBEAM, 90, 65, 65, 15, 40),
    SLOWBRO     (80, "Slowbro", Type.WATER, Type.PSYCHIC, Attack.PSYCHIC, 95, 75, 110, 30, 80),
    MAGNETMITE  (81, "Magnemite", Type.ELECTRIC, Type.NONE, Attack.THUNDERBOLT, 25, 35, 70, 45, 95),
    MAGNETON    (82, "Magneton", Type.ELECTRIC, Type.NONE, Attack.THUNDERBOLT, 50, 60, 95, 70, 120),
    FARFETCHD   (83, "Farfetchd", Type.NORMAL, Type.FLYING, Attack.WINGATTACK, 52, 65, 55, 60, 58),
    DODUO       (84, "Doduo", Type.NORMAL, Type.FLYING, Attack.QUICKATTACK, 35, 85, 45, 75, 35),
    DODRIO      (85, "Dodrio", Type.NORMAL, Type.FLYING, Attack.QUICKATTACK, 60, 110, 70, 100, 60),
    SEEL        (86, "Seel", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 65, 45, 55, 45, 70),
    DEWGONG     (87, "Dewgong", Type.WATER, Type.ICE, Attack.ICEBEAM, 90, 70, 80, 70, 95),
    GRIMER      (88, "Grimer", Type.POISON, Type.NONE, Attack.POISONGAS, 80, 80, 50,25, 40),
    MUK         (89, "Muk", Type.POISON, Type.NONE, Attack.POISONGAS, 105, 105, 75, 50, 65),
    SHELLDER     (90, "Shellder", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 30, 65, 100, 40, 45),
    CLOYSTER    (91, "Cloyster", Type.WATER, Type.ICE, Attack.ICEBEAM, 50, 95, 180, 70, 85),
    GASTLY      (92, "Gastly", Type.GHOST, Type.POISON, Attack.NIGHTSHADE, 30, 35, 30, 80, 100),
    HAUNTER     (93, "Haunter", Type.GHOST, Type.POISON, Attack.NIGHTSHADE, 45, 50, 45, 95, 115),
    GENGAR      (94, "Gengar", Type.GHOST, Type.POISON, Attack.NIGHTSHADE, 60, 65, 60, 110, 130),
    ONIX        (95, "Onix", Type.ROCK, Type.GROUND, Attack.ROCKTHROW, 35, 45, 160, 70, 30),
    DROWZEE     (96, "Drowzee", Type.PSYCHIC, Type.NONE, Attack.PSYCHIC, 60, 48, 45, 52, 90),
    HYPNO       (97, "Hypno", Type.PSYCHIC, Type.NONE, Attack.PSYCHIC, 85, 73, 70, 67, 115),
    KRABBY      (98, "Krabby", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 30, 105, 90, 50, 25),
    KINGLER     (99, "Kingler", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 55, 130, 115, 75, 50),
    VOLTORB     (100, "Voltorb", Type.ELECTRIC, Type.NONE, Attack.THUNDERBOLT, 40, 30, 50, 100, 55),
    ELECTRODE   (101, "Electrode", Type.ELECTRIC, Type.NONE, Attack.THUNDERBOLT, 60, 50, 70, 140, 80),
    EXEGGCUTE   (102, "Exeggcute", Type.GRASS, Type.PSYCHIC, Attack.PSYCHIC, 60, 40 , 80, 40, 60),
    EXEGGUTOR   (103, "Exeggutor", Type.GRASS, Type.PSYCHIC, Attack.PSYCHIC, 95, 95, 85, 55, 125),
    CUBONE      (104, "Cubone", Type.GROUND, Type.NONE, Attack.BONEMERANG, 50, 50, 95, 35, 40),
    MAROWAK     (105, "Marowak", Type.GROUND, Type.NONE, Attack.BONEMERANG, 60, 80, 110, 45, 50),
    HITMONLEE   (106, "Hitmonlee", Type.FIGHTING, Type.NONE, Attack.SEISMICTOSS, 50, 120, 53, 87, 35),
    HITMONCHAN  (107, "Hitmonchan", Type.FIGHTING, Type.NONE, Attack.SEISMICTOSS, 50, 105, 79, 76, 35),
    LICKITUNG   (108, "Lickitung", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 90, 55, 75, 30, 60),
    KOFFING     (109, "Koffing", Type.POISON, Type.NONE, Attack.POISONGAS, 40, 65, 95, 35, 60),
    WEEZING     (110, "Weezing", Type.POISON, Type.NONE, Attack.POISONGAS, 65, 90, 120, 60, 85),
    RHYHORN     (111, "Rhyhorn", Type.GROUND, Type.ROCK, Attack.ROCKTHROW, 80, 85, 95, 25, 30),
    RHYDON      (112, "Rhydon", Type.GROUND, Type.ROCK, Attack.ROCKTHROW, 105, 130, 120, 40, 45),
    CHANSEY     (113, "Chansey", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 250, 5, 5, 50, 105),
    TANGELA     (114, "Tangela", Type.GRASS, Type.NONE, Attack.RAZORLEAF, 65, 55, 115, 60, 100),
    KANGASKHAN  (115, "Kangaskhan", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 105, 95, 80, 90, 40),
    HORSEA      (116, "Horsea", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 30, 40, 70, 60, 70),
    SEADRA      (117, "Seadra", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 55, 65, 95, 85, 95),
    GOLDEEN     (118, "Goldeen", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 45, 67, 60, 63, 50),
    SEAKING     (119, "Seaking", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 80, 92, 65, 68, 80),
    STARYU      (120, "Staryu", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 30, 45, 55, 85, 70),
    STARMIE     (121, "Starmie", Type.NORMAL, Type.PSYCHIC, Attack.BUBBLEBEAM, 60, 75, 85, 115, 100),
    MRMIME      (122, "MrMime", Type.PSYCHIC, Type.NONE, Attack.PSYCHIC, 40, 45, 65, 90, 100),
    SCYTHER     (123, "Scyther", Type.BUG, Type.FLYING, Attack.WINGATTACK, 70, 110, 80, 105, 55),
    JYNX        (124, "Jynx", Type.ICE, Type.PSYCHIC, Attack.PSYCHIC, 65, 50, 35, 95, 95),
    ELECTABUZZ  (125, "Electabuzz", Type.ELECTRIC, Type.NONE, Attack.THUNDERBOLT, 65, 83, 57, 105, 85),
    MAGMAR      (126, "Magmar", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 65, 95, 57, 93, 85),
    PINSIR      (127, "Pinsir", Type.BUG, Type.NONE, Attack.SEISMICTOSS, 65, 135, 100, 85, 55),
    TAUROS      (128, "Tauros", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 75, 100, 95, 110, 70),
    MAGIKARP    (129, "Magikarp", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 20, 10, 55, 80, 20),
    GYARADOS    (130, "Gyarados", Type.WATER, Type.FLYING, Attack.BUBBLEBEAM, 95, 125, 79, 81, 100),
    LAPRAS      (131, "Lapras", Type.WATER, Type.ICE, Attack.BUBBLEBEAM, 130, 85, 80, 60, 95),
    DITTO       (132, "Ditto", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 48, 48, 48, 48, 48),
    EEVEE       (133, "Eevee", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 55, 55, 50, 55, 65),
    VAPOREON    (134, "Vaporeon", Type.WATER, Type.NONE, Attack.BUBBLEBEAM, 130, 65, 60, 65, 110),
    JOLTEON     (135, "Jolteon", Type.ELECTRIC, Type.NONE, Attack.THUNDERBOLT, 65, 65, 60, 130, 110),
    FLAREON     (136, "Flareon", Type.FIRE, Type.NONE, Attack.FLAMETHROWER, 65, 130, 60, 65, 110),
    PORYGON     (137, "Porygon", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 65, 60, 70, 40, 75),
    OMANYTE     (138, "Omanyte", Type.ROCK, Type.WATER, Attack.BUBBLEBEAM, 35, 40, 100, 35, 90),
    OMASTAR     (139, "Omastar", Type.ROCK, Type.WATER, Attack.BUBBLEBEAM, 70, 60, 125, 55, 115),
    KABUTO      (140, "Kabuto", Type.ROCK, Type.WATER, Attack.BUBBLEBEAM, 30, 80, 90, 55, 45),
    KABUTOPS    (141, "Kabutops", Type.ROCK, Type.WATER, Attack.BUBBLEBEAM, 60, 115, 105, 80, 70),
    AERODACTYL  (142, "Aerodactyl", Type.ROCK, Type.FLYING, Attack.WINGATTACK, 80, 105, 65, 130, 60),
    SNORLAX     (143, "Snorlax", Type.NORMAL, Type.NONE, Attack.QUICKATTACK, 160, 110, 65, 30, 65),
    ARTICUNO    (144, "Articuno", Type.ICE, Type.FLYING, Attack.ICEBEAM, 90, 85, 100, 85, 125),
    ZAPDOS      (145, "Zapdos", Type.ELECTRIC, Type.FLYING, Attack.THUNDERBOLT, 90, 90, 85, 100, 125),
    MOLTRES     (146, "Moltres", Type.FIRE, Type.FLYING, Attack.FLAMETHROWER, 90, 100, 90, 90, 125),
    DRATINI     (147, "Dratini", Type.DRAGON, Type.NONE, Attack.DRAGONRAGE, 41, 64, 45, 50, 50),
    DRAGONAIR   (148, "Dragonair", Type.DRAGON, Type.NONE, Attack.DRAGONRAGE, 61, 84, 65, 70, 70),
    DRAGONITE   (149, "Dragonite", Type.DRAGON, Type.FLYING, Attack.DRAGONRAGE, 91, 134, 95, 80, 100),
    MEWTWO      (150, "Mewtwo", Type.PSYCHIC, Type.NONE, Attack.PSYCHIC, 106, 110, 90, 130, 154),
    MEW         (151, "Mew", Type.PSYCHIC, Type.NONE, Attack.PSYCHIC, 100, 100, 100, 100, 100);

    private int index;
    private String name;
    private int width;
    private int height;
    private Type type1;
    private Type type2;
    private Attack attack;
    private double attackSpeed;
    private int hitPoints;
    private double attackPower;
    private double movementSpeed;
    private double baseSpeed;
    private double critChance;
    private BufferedImage front1;
    private BufferedImage front2;
    private BufferedImage back1;
    private BufferedImage back2;

    Pokemon(int index, String name, Type type1, Type type2, Attack attack, int hitPoints,
            double attackPower, double defense, double speed, double special) {
        this.name = name;
        URL resource1 = Pokemon.class.getResource("/Resources/Pokemon/" + name + "Front1.png");
        URL resource2 = Pokemon.class.getResource("/Resources/Pokemon/" + name + "Front2.png");
        URL resource3 = Pokemon.class.getResource("/Resources/Pokemon/" + name + "Back1.png");
        URL resource4 = Pokemon.class.getResource("/Resources/Pokemon/" + name + "Back2.png");
        try {
            this.front1 = ImageIO.read(new File(resource1.toURI()));
            this.front2 = ImageIO.read(new File(resource2.toURI()));
            this.back1 = ImageIO.read(new File(resource3.toURI()));
            this.back2 = ImageIO.read(new File(resource4.toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.width = (back1.getWidth() + back2.getWidth() + front1.getWidth() + front2.getWidth()) / 4;
        this.height = (back1.getHeight() + back2.getHeight() + front1.getHeight() + front2.getHeight()) / 4;
        this.type1 = type1;
        this.type2 = type2;
        this.attack = attack;
        this.index = index;
        this.hitPoints = (int) Math.ceil((hitPoints + defense + special) / 3);
        this.attackPower = (attackPower + special) / 2;
        this.movementSpeed = speed / 4;
        this.attackSpeed = speed;
        this.baseSpeed = speed;
        // To use critChance, generate a number from 1 to 300 (inclusive)
        // and crit if it is less than critChance
        this.critChance = speed;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double getCritChance() {
        return critChance;
    }

    public Type getType1() {
        return type1;
    }

    public Type getType2() {
        return type2;
    }

    public Attack getAttack() {
        return attack;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public double getAttackPower(){
        return attackPower;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public BufferedImage getFront1() {
        return front1;
    }

    public BufferedImage getBack1() {
        return back1;
    }

    public BufferedImage getFront2() {
        return front2;
    }

    public BufferedImage getBack2() {
        return back2;
    }

    public static String appearsIn(Pokemon p) {
        for (int i = 0; i < Location.values().length; i++) {
            if (ControlPanel.unlockedLocation[i]) {
                if (p.equals(Location.values()[i].getBoss())) {
                    return Location.values()[i].getLevelName();
                }
                for (int j = 0; j < Location.values()[i].getEnemies().length; j++) {
                    if (p.equals(Location.values()[i].getEnemies()[j])) {
                        return Location.values()[i].getLevelName();
                    }
                }
            }
        }
        return "Unknown Location";
    }
}
