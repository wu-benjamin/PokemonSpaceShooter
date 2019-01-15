import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Projectile extends GameObject {

    // Velocity of Pokemon (velocity is partly passed to projectile for linear projectiles)
    private int xComponentPokemon;
    private int yComponentPokemon;
    // Velocity components of attack
    private double xComponent;
    private double yComponent;
    private int boomerangNum;
    private Attack attack;
    private Rectangle2D square;
    private boolean isOfEnemy;
    private BufferedImage attackImage;
    private ControlPanel control;
    private String attackPath;
    private Timer timer = new Timer();
    private Projectile p;
    private int time;
    private Type attackType;
    private double rawX;
    private double rawY;

    // Projectile constructor for Characters to fire directly
    Projectile(int x, int y, int size, Color color, Attack attack, ControlPanel control, boolean isOfEnemy, int xComponent, int yComponent) {
        super(x, y, size, size, color);
        this.isOfEnemy = isOfEnemy;
        this.square = new Rectangle2D.Double(x, y, size, size);
        this.attackType = attack.getType();
        this.attack = attack;
        this.xComponentPokemon = xComponent / 3;
        this.yComponentPokemon = yComponent / 3;
        this.attackPath = attack.getAttackPath();
        this.control = control;
        this.p = this;
        if (!isOfEnemy) {
            ControlPanel.playerProjectilesToAdd.add(this);
        } else {
            ControlPanel.enemyProjectilesToAdd.add(this);
        }
        attackImage = attack.getAttackImage();
        this.time = 0;
        this.rawX = x;
        this.rawY = y;
        timer(17);
    }

    // Individual radial bullets
    private Projectile(int x, int y, int size, Color color, Attack attack, ControlPanel control, int xComponent, int yComponent, boolean isOfEnemy) {
        super(x, y, size, size, color);
        this.isOfEnemy = isOfEnemy;
        this.square = new Rectangle2D.Double(x, y, size, size);
        this.attack = attack;
        this.xComponent = xComponent;
        this.yComponent = yComponent;
        this.attackType = attack.getType();
        this.attackPath = "default";
        this.control = control;
        this.p = this;
        if (!isOfEnemy) {
            ControlPanel.playerProjectilesToAdd.add(this);
        } else {
            ControlPanel.enemyProjectilesToAdd.add(this);
        }
        attackImage = attack.getAttackImage();
        this.rawX = x;
        this.rawY = y;
        timer(17);
    }

    // Individual boomerang bullets
    private Projectile(int x, int y, int size, Color color, Attack attack, ControlPanel control, boolean isOfEnemy, int boomerangNum) {
        super(x, y, size, size, color);
        this.isOfEnemy = isOfEnemy;
        this.square = new Rectangle2D.Double(x, y, size, size);
        this.attack = attack;
        this.attackType = attack.getType();
        this.attackPath = "Boomerangs";
        this.control = control;
        this.p = this;
        if (!isOfEnemy) {
            ControlPanel.playerProjectilesToAdd.add(this);
        } else {
            ControlPanel.enemyProjectilesToAdd.add(this);
        }
        attackImage = attack.getAttackImage();
        this.boomerangNum = boomerangNum;
        this.rawX = x;
        this.rawY = y;
        timer(10);
    }

    public Rectangle2D getObj() {
        return square;
    }

    // Projectiles disappear if too far off screen
    public void update(ControlPanel panel) {
        if (this.getX() < -500 || this.getX() > ControlPanel.width + 500 || this.getY() < -this.getHeight() - 500 ||
                this.getY() > ControlPanel.height + 500) {
            this.timer.cancel();
            this.timer.purge();
            if (!this.isOfEnemy) {
                ControlPanel.playerProjectilesToRemove.add(this);
            } else {
                ControlPanel.enemyProjectilesToRemove.add(this);
            }
        } else {
            boolean hit = false;
            // Attack enemies
            for (Enemy e : ControlPanel.getEnemies()) {
                // Checks for hits
                if (checkCollision(e) && !this.isOfEnemy) {
                    e.takeDamage((int) (((double) this.getAttack().getAttackDamage() * (Type.typeEffectiveness(e.getType1(),
                            e.getType2(), this.getAttack().getType())) + 1.0) * (1.0 + (double) control.getPower() / 10.0) / 5.0), attackType);
                    this.timer.cancel();
                    this.timer.purge();
                    ControlPanel.playerProjectilesToRemove.add(this);
                    hit = true;
                }
            }
            if (hit) {
                this.timer.cancel();
                this.timer.purge();
                ControlPanel.playerProjectilesToRemove.add(this);
            }
            // Attack player
            if (checkCollision(Player.getPlayer()) && this.isOfEnemy) {
                Player.getPlayer().takeDamage((int) ((this.getAttack().getAttackDamage() *
                        (Type.typeEffectiveness(Player.getPlayer().getType1(),
                                Player.getPlayer().getType2(), this.getAttack().getType())) + 1) / 5));
                this.timer.cancel();
                this.timer.purge();
                ControlPanel.enemyProjectilesToRemove.add(this);
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        square.setFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(this.getColor());
        g2.fill(square);
        g2.draw(square);
        if (this.isOfEnemy) {
            g2.drawImage(this.attackImage, this.getX(), this.getY()  + this.getHeight(), this.getWidth(), -this.getHeight(), control);
        } else {
            g2.drawImage(this.attackImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
        }
    }

    private boolean checkCollision(GameObject obj) {
        return (square.intersects(obj.getObj()));
    }


    public void timer(int speed) {
        TimerTask task = new MyTask();
        timer.schedule(task, 0, speed);
    }

    private Attack getAttack() {
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
                        new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI / 4) * attack.getProjectileSpeed()), isOfEnemy);
                        new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(Math.PI / 2) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI / 2) * attack.getProjectileSpeed()), isOfEnemy);
                        new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(3 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(3 * Math.PI / 4) * attack.getProjectileSpeed()), isOfEnemy);
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(Math.PI) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI) * attack.getProjectileSpeed()), isOfEnemy));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(5 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(5 * Math.PI / 4) * attack.getProjectileSpeed()), isOfEnemy));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(3 * Math.PI / 2) * attack.getProjectileSpeed()), (int) (Math.sin(3 * Math.PI / 2) * attack.getProjectileSpeed()), isOfEnemy));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, (int) (Math.cos(7 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(7 * Math.PI / 4) * attack.getProjectileSpeed()), isOfEnemy));
                        ControlPanel.playerProjectilesToRemove.add(p);
                        ControlPanel.enemyProjectilesToRemove.add(p);
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
                        new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 2);
                        new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 3);
                        new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 4);
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 5));
                        //ControlPanel.toAdd.add(new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 6));
                        new Projectile(p.getX(), p.getY(), p.getWidth(), p.getColor() , attack, control, isOfEnemy, 7);
                        ControlPanel.playerProjectilesToRemove.add(p);
                        ControlPanel.enemyProjectilesToRemove.add(p);
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
                e.printStackTrace();
            }
        }
    }
}