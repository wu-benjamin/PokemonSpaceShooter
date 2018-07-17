public enum Type {
    NONE(0, 0, 0, 0, ""),
    NORMAL(167, 170, 116, 1, "Normal"),
    FIRE(242, 126, 51, 2, "Fire"),
    FIGHTING(197, 45, 41, 3, "Fighting"),
    WATER(105, 143, 241, 4, "Water"),
    FLYING(168, 144, 240, 5, "Flying"),
    GRASS(121, 199, 79, 6, "Grass"),
    POISON(159, 67, 156, 7, "Poison"),
    ELECTRIC(246, 209, 51, 8, "Electric"),
    GROUND(223, 193, 105, 9, "Ground"),
    PSYCHIC(250, 86, 138, 10, "Psychic"),
    ROCK(183, 160, 58, 11, "Rock"),
    ICE(148, 218, 220, 12, "Ice"),
    BUG(164, 185, 32, 13, "Bug"),
    DRAGON(112, 57, 247, 14, "Dragon"),
    GHOST(111, 87, 150, 15, "Ghost"),
    UNKNOWN(255, 255, 255, 16, "Unknown");

    // Table of what type of move gains a boost against others
    private static double[][] typeChart = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 0, 1},
            {1, 1, 0.5, 1, 0.5, 1, 2, 1, 1, 1, 1, 0.5, 2, 2, 0.5, 1, 1, 1},
            {1, 2, 1, 1, 1, 0.5, 1, 0.5, 1, 1, 0.5, 2, 2, 0.5, 1, 0, 1},
            {1, 1, 2, 1, 0.5, 1, 0.5, 1, 1, 2, 1, 2, 1, 1, 0.5, 1, 1},
            {1, 1, 1, 2, 1, 1, 2, 1, 0.5, 1, 1, 0.5, 1, 1, 1, 1, 1},
            {1, 1, 0.5, 1, 2, 0.5, 0.5, 0.5, 1, 2, 1, 2, 1, 0.5, 0.5, 1, 1},
            {1, 1, 1, 1, 1, 1, 2, 0.5, 1, 0.5, 1, 0.5, 1, 2, 1, 0.5, 1},
            {1, 1, 1, 1, 2, 2, 0.5, 1, 0.5, 0, 1, 1, 1, 1, 0.5, 1, 1},
            {1, 1, 2, 1, 1, 0, 0.5, 2, 2, 1, 1, 2, 1, 0.5, 1, 1, 1},
            {1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 0.5, 1, 1, 1, 1, 1, 1},
            {1, 1, 2, 0.5, 1, 2, 1, 1, 1, 0.5, 1, 1, 2, 2, 1, 1, 1},
            {1, 1, 1, 1, 0.5, 2, 2, 1, 1, 2, 1, 1, 0.5, 1, 2, 1, 1},
            {1, 1, 0.5, 0.5, 1, 0.5, 2, 2, 1, 1, 2, 1, 1, 1, 1, 0.5, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    Type(int red, int green, int blue, int index, String name) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.index = index;
        this.name = name;
    }

    private int red;
    private int green;
    private int blue;
    private int index;
    private String name;

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    // Returns coefficient that damage is to be multiplied by
    public static double typeEffectiveness(Type targetType1, Type targetType2, Type attack) {
        double typeCoefficient = typeChart[attack.getIndex()][targetType1.getIndex()]
                * typeChart[attack.getIndex()][targetType2.getIndex()];
        // Coefficient will not be less than an eighth as the game will be painful to play
        return Math.max(typeCoefficient, 0.125);
    }
}
