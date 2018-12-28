import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Projectile extends GameObject {

    private int xComponentPokemon;
    private int yComponentPokemon;
    // Velocity components of attack
    private double xComponent;
    private double yComponent;
    private int boomerangNum;
    private Attack attack;
    private Rectangle2D square;
    public boolean isOfEnemy;
    private BufferedImage attackImage;
    private ControlPanel control;
    static ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();
    static ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
    private String attackPath;
    private Timer timer = new Timer();
    private Projectile p;
    private int time;
    private int damage;
    private Type attackType;
    private int rep;
    private double rawX;
    private double rawY;

    // Projectile constructor for Characters to fire directly
    public Projectile(int x, int y, int size, Color color, Attack attack, ControlPanel control, boolean isOfEnemy, int xComponent, int yComponent) {
        super(x, y, size, size, color);
        this.isOfEnemy = isOfEnemy;
        square = new Rectangle2D.Double(x, y, size, size);
        this.attackType = attack.getType();
        this.attack = attack;
        this.xComponentPokemon = xComponent / 3;
        this.yComponentPokemon = yComponent / 3;
        this.attackPath = attack.getAttackPath();
        this.control = control;
        this.p = this;
        if (!isOfEnemy) {
            playerProjectiles.add(this);
        } else {
            enemyProjectiles.add(this);
        }
        attackImage = attack.getAttackImage();
        this.time = 0;
        this.rawX = x;
        this.rawY = y;
        timer();
    }

    // Individual radial bullets
    private Projectile(int x, int y, int size, Color color, Attack attack, ControlPanel control, int xComponent, int yComponent, boolean isOfEnemy) {
        super(x, y, size, size, color);
        this.isOfEnemy = isOfEnemy;
        square = new Rectangle2D.Double(x, y, size, size);
        this.attack = attack;
        this.xComponent = xComponent;
        this.yComponent = yComponent;
        this.attackType = attack.getType();
        this.attackPath = "default";
        this.control = control;
        this.p = this;
        if (!isOfEnemy) {
            playerProjectiles.add(this);
        } else {
            enemyProjectiles.add(this);
        }
        attackImage = attack.getAttackImage();
        this.rawX = x;
        this.rawY = y;
        timer();
    }

    // Individual boomerang bullets
    private Projectile(int x, int y, int size, Color color, Attack attack, ControlPanel control, boolean isOfEnemy, int boomerangNum) {
        super(x, y, size, size, color);
        this.isOfEnemy = isOfEnemy;
        square = new Rectangle2D.Double(x, y, size, size);
        this.attack = attack;
        this.attackType = attack.getType();
        this.attackPath = "Boomerangs";
        this.control = control;
        this.p = this;
        if (!isOfEnemy) {
            playerProjectiles.add(this);
        } else {
            enemyProjectiles.add(this);
        }
        attackImage = attack.getAttackImage();
        this.boomerangNum = boomerangNum;
        this.rawX = x;
        this.rawY = y;
        timer();
    }

    // Special bomb projectile
    public Projectile(int damage, Type type, int rep) {
        super(0, 0, ControlPanel.width + 20, ControlPanel.height + 20, ControlPanel.TRANSPARENT);
        this.isOfEnemy = false;
        square = new Rectangle2D.Double(0, 0, ControlPanel.width + 20, ControlPanel.height + 20);
        this.control = control;
        this.attackType = type;
        this.p = this;
        if (!isOfEnemy) {
            playerProjectiles.add(this);
        }
        this.attackPath = "Bomb";
        // Power is divided for multi-hit so total damage is equivalent to desired
        this.damage = damage / 5;
        this.rep = rep;
        // 5 burst hits prevent enemies from dodging
        if (rep < 5) {
            ControlPanel.toAdd.add(new Projectile(damage, type, rep + 1));
        }
        this.rawX = x;
        this.rawY = y;
        timer();
    }

    public Rectangle2D getObj() {
        return square;
    }

    // Projectiles disappear if too far off screen
    public void update(ControlPanel panel) {
        if (this.getX() < -300 || this.getX() > ControlPanel.width + 300 || this.getY() < -this.getHeight() ||
                this.getY() > ControlPanel.height) {
            this.timer.cancel();
            this.timer.purge();
            if (!this.isOfEnemy) {
                playerProjectiles.remove(this);
            } else {
                enemyProjectiles.remove(this);
            }
            ControlPanel.toRemove.add(this);
            this.p = null;
        }
        boolean hit = false;
        // Attack enemies
        for (Enemy e : Enemy.enemies) {
            // Checks for hits
            if (checkCollision(e) && !this.isOfEnemy) {
                if (this.attackPath.equals("Bomb")) {
                    e.takeDamage((int) (damage * Type.typeEffectiveness(e.getType1(),
                            e.getType2(), attackType) + 1), attackType);
                    hit = true;
                } else {
                    e.takeDamage((int) ((this.getAttack().getAttackDamage() * (Type.typeEffectiveness(e.getType1(),
                            e.getType2(), this.getAttack().getType())) + 1) / 5), attackType);
                    this.p = null;
                    this.timer.cancel();
                    this.timer.purge();
                    ControlPanel.toRemove.add(this);
                    playerProjectiles.remove(this);
                }
            }
        }
        if (hit) {
            this.p = null;
            this.timer.cancel();
            this.timer.purge();
            ControlPanel.toRemove.add(this);
            playerProjectiles.remove(this);
        }
        // Attack player
        if (checkCollision(Player.getPlayer()) && this.isOfEnemy) {
            Player.getPlayer().takeDamage((int) ((this.getAttack().getAttackDamage() *
                    (Type.typeEffectiveness(Player.getPlayer().getType1(),
                    Player.getPlayer().getType2(), this.getAttack().getType())) + 1) / 5));
            this.p = null;
            this.timer.cancel();
            this.timer.purge();
            ControlPanel.toRemove.add(this);
            enemyProjectiles.remove(this);
        }
    }

    public boolean checkCollision(GameObject obj) {
        return (square.intersects((Rectangle2D) obj.getObj()));
    }

    public void paintComponent(Graphics2D g2) {
        square.setFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(this.getColor());
        g2.fill(square);
        g2.draw(square);
        if (!this.attackPath.equals("Bomb")) {
            if (this.isOfEnemy) {
                g2.drawImage(this.attackImage, this.getX(), this.getY()  + this.getHeight(), this.getWidth(), -this.getHeight(), control);
            } else {
                g2.drawImage(this.attackImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
            }
        }
    }

    public void timer() {
        TimerTask task = new MyTask();
        timer.schedule(task, 0, 17);
    }

    public Attack getAttack() {
        return attack;
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            /*   Attack paths can be defined as a parametric function of time where the x and y components are the derivative
                 with respect to x and y of the function whose graph you wish to trace
            */
            time++;
            try {
                switch (attackPath) {
                    case "Linear":
                        yComponent = attack.getProjectileSpeed();
                        break;
                    case "Radial":
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(0) * attack.getProjectileSpeed()), (int) (Math.sin(0) * attack.getProjectileSpeed()), isOfEnemy));
                        ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI / 4) * attack.getProjectileSpeed()), isOfEnemy));
                        ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(Math.PI / 2) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI / 2) * attack.getProjectileSpeed()), isOfEnemy));
                        ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(3 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(3 * Math.PI / 4) * attack.getProjectileSpeed()), isOfEnemy));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(Math.PI) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI) * attack.getProjectileSpeed()), isOfEnemy));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(5 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(5 * Math.PI / 4) * attack.getProjectileSpeed()), isOfEnemy));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(3 * Math.PI / 2) * attack.getProjectileSpeed()), (int) (Math.sin(3 * Math.PI / 2) * attack.getProjectileSpeed()), isOfEnemy));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(7 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(7 * Math.PI / 4) * attack.getProjectileSpeed()), isOfEnemy));
                        ControlPanel.toRemove.add(p);
                        playerProjectiles.remove(p);
                        enemyProjectiles.remove(p);
                        timer.cancel();
                        timer.purge();
                        break;
                    case "Oscillate":
                        yComponent = attack.getProjectileSpeed();
                        xComponent = (int) (25 * Math.cos(time / 3));
                        break;
                    case "Boomerang":
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 0));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 1));
                        ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 2));
                        ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 3));
                        ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 4));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 5));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 6));
                        ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 7));
                        ControlPanel.toRemove.add(p);
                        playerProjectiles.remove(p);
                        enemyProjectiles.remove(p);
                        timer.cancel();
                        timer.purge();
                        break;
                    case "Boomerangs":
                        double timeTemp = time / 10 + 1;
                        switch (boomerangNum) {
                            case 0:
                                xComponent = (- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                                yComponent = (-2 * timeTemp + 10);
                                break;
                            case 1:
                                xComponent = -(- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                                yComponent = (-2 * timeTemp + 10);
                                break;
                            case 2:
                                xComponent = -(- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                                yComponent = -(-2 * timeTemp + 10);
                                break;
                            case 3:
                                xComponent = (- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                                yComponent = -(-2 * timeTemp + 10);
                                break;
                            case 4:
                                yComponent = (- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                                xComponent = (-2 * timeTemp + 10);
                                break;
                            case 5:
                                yComponent = -(- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                                xComponent = (-2 * timeTemp + 10);
                                break;
                            case 6:
                                yComponent = -(- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                                xComponent = -(-2 * timeTemp + 10);
                                break;
                            default:
                                yComponent = (- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                                xComponent = -(-2 * timeTemp + 10);
                                break;
                        }
                        break;
                    case "Bomb":
                        xComponent = -100;
                        break;
                    default:
                        break;
                }
                if (Math.sqrt(Math.pow(xComponent + xComponentPokemon, 2) + Math.pow(yComponent + yComponentPokemon, 2)) == 0) {
                    p.setY(p.getY() + 1);
                }
                if (!isOfEnemy) {
                    /*
                        Keeping a rawX and rawY double ensures small changes in x and y do not get rounded to 0
                        small changes will eventually have an effect
                    */
                    rawX = rawX + xComponent + xComponentPokemon;
                    rawY = rawY - yComponent + yComponentPokemon;
                    p.setX((int) rawX);
                    p.setY((int) rawY);
                } else {
                    rawX = rawX + xComponent + xComponentPokemon;
                    rawY = rawY + yComponent + yComponentPokemon;
                    p.setX((int) rawX);
                    p.setY((int) rawY);
                }
            } catch (NullPointerException e) {

            }
        }
    }
}