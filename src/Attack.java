import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public enum Attack {
    HYPERBEAM       (Type.UNKNOWN, "HyperBeam", "Radial", 120, 150, 20, 30),
    QUICKATTACK     (Type.NORMAL, "QuickAttack", "Linear", 40, 150, 15, 30),
    FLAMETHROWER    (Type.FIRE, "Flamethrower", "Linear", 55, 200, 12, 40),
    SEISMICTOSS     (Type.FIGHTING, "SeismicToss", "Oscillate", 45, 175, 10, 40),
    BUBBLEBEAM      (Type.WATER, "BubbleBeam", "Linear", 45, 180, 10, 35),
    WINGATTACK      (Type.FLYING, "WingAttack", "Boomerang", 45, 400, 14, 25),
    RAZORLEAF       (Type.GRASS, "RazorLeaf", "Boomerang", 45, 400, 12, 25),
    POISONGAS       (Type.POISON, "PoisonGas", "Radial", 5, 60, 13, 30),
    THUNDERBOLT     (Type.ELECTRIC, "Thunderbolt", "Linear", 45, 170, 15, 35),
    SANDATTACK      (Type.GROUND, "SandAttack", "Linear", 35, 140, 12, 35),
    PSYCHIC         (Type.PSYCHIC, "Psychic", "Radial", 70, 525, 5, 30),
    ROCKTHROW       (Type.ROCK, "RockThrow", "Linear", 60, 235, 12, 45),
    ICEBEAM         (Type.ICE, "IceBeam", "Linear", 55, 210, 10, 40),
    STRINGSHOT      (Type.BUG, "StringShot", "Linear", 20, 80, 15, 40),
    DRAGONRAGE      (Type.DRAGON, "DragonRage", "Linear", 40, 155, 13, 45),
    NIGHTSHADE      (Type.GHOST, "Nightshade", "Radial", 20, 150, 8, 25),
    BONEMERANG      (Type.GROUND, "Bonemerang", "Boomerang", 45, 400, 12, 25);

    private String attackPath;
    private int attackDamage;
    private int attackDelay;
    private int projectileSpeed;
    private int projectileSize;
    private Type type;
    private BufferedImage attackImage;
    private String attackName;

    Attack(Type type, String attack, String attackPath, int attackDamage, int attackDelay, int projectileSpeed, int projectileSize) {
        this.attackPath = attackPath;
        this.attackDamage = attackDamage;
        this.attackDelay = attackDelay;
        this.projectileSpeed = (int) (projectileSpeed * 1.5);
        this.projectileSize = projectileSize;
        this.type = type;
        this.attackName = attack;
        URL resource = Pokemon.class.getResource("/Resources/Attacks/" + attack + ".png");
        try {
            this.attackImage = ImageIO.read(new File(resource.toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAttackPath(){
        return attackPath;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getAttackDelay() {
        return attackDelay;
    }

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public int getProjectileSize() {
        return projectileSize;
    }

    public Type getType() {
        return type;
    }

    public String getAttackName() {
        return attackName;
    }

    public BufferedImage getAttackImage() {
        return attackImage;
    }
}
