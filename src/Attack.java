import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public enum Attack {
    HYPERBEAM(Type.UNKNOWN, "HyperBeam", "Radial", 120, 150, 20, 25),
    QUICKATTACK(Type.NORMAL, "QuickAttack", "Linear", 40, 150, 15, 20),
    FLAMETHROWER(Type.FIRE, "Flamethrower", "Linear", 55, 200, 12, 40),
    SEISMICTOSS(Type.FIGHTING, "SeismicToss", "Oscillate", 45, 175, 10, 40),
    BUBBLEBEAM(Type.WATER, "BubbleBeam", "Linear", 40, 180, 10, 35),
    WINGATTACK(Type.FLYING, "WingAttack", "Boomerang", 45, 400, 14, 25),
    RAZORLEAF(Type.GRASS, "RazorLeaf", "Boomerang", 55, 400, 12, 25),
    POISONGAS(Type.POISON, "PoisonGas", "Radial", 5, 80, 13, 30),
    THUNDERBOLT(Type.ELECTRIC, "Thunderbolt", "Linear", 50, 170, 15, 35),
    SANDATTACK(Type.GROUND, "SandAttack", "Linear", 35, 200, 12, 35),
    PSYCHIC(Type.PSYCHIC, "Psychic", "Radial", 70, 300, 5, 25),
    ROCKTHROW(Type.ROCK, "RockThrow", "Linear", 60, 250, 12, 50),
    ICEBEAM(Type.ICE, "IceBeam", "Linear", 55, 200, 10, 40),
    STRINGSHOT(Type.BUG, "StringShot", "Linear", 20, 150, 15, 40),
    DRAGONRAGE(Type.DRAGON, "DragonRage", "Linear", 40, 165, 13, 50),
    NIGHTSHADE(Type.GHOST, "Nightshade", "Radial", 20, 150, 8, 25),
    BONEMERANG(Type.GROUND, "Bonemerang", "Boomerang", 55, 400, 12, 25);

    private String attackPath;
    private int attackDamage;
    private int attackDelay;
    private int projectileSpeed;
    private int projectileSize;
    private Type type;
    private BufferedImage attackImage;

    Attack(Type type, String attack, String attackPath, int attackDamage, int attackDelay, int projectileSpeed, int projectileSize) {
        this.attackPath = attackPath;
        this.attackDamage = attackDamage;
        this.attackDelay = attackDelay;
        this.projectileSpeed = (int) (projectileSpeed * 1.5);
        this.projectileSize = projectileSize;
        this.type = type;
        URL resource = Pokemon.class.getResource("/Resources/Attacks/" + attack + ".png");
        try {
            this.attackImage = ImageIO.read(new File(resource.toURI()));
        } catch (Exception e) {
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

    public BufferedImage getAttackImage() {
        return attackImage;
    }
}
