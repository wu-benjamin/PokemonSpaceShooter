import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Timer;

public abstract class Projectile extends GameObject {

    // Velocity of Pokemon (velocity is partly passed to projectile for linear projectiles)
    // Velocity components of attack
    double xComponent;
    double yComponent;
    Timer timer;

    Attack attack;
    private Rectangle2D square;
    private BufferedImage attackImage;
    private ControlPanel control;
    private Type attackType;
    double rawX;
    double rawY;
    Pokemon enemyPokemon = null;

    Projectile(int x, int y, int size, Attack attack, ControlPanel control, Pokemon... enemyPokemon) {
        super(x, y, size, size, ControlPanel.TRANSPARENT);
        this.square = new Rectangle2D.Double(x, y, size, size);
        this.attackType = attack.getType();
        this.attack = attack;
        this.control = control;
        attackImage = attack.getAttackImage();
        this.rawX = x;
        this.rawY = y;
        if (enemyPokemon.length > 0) {
            this.enemyPokemon = enemyPokemon[0];
        }
        this.timer = new Timer();
    }

    public Rectangle2D getObj() {
        return square;
    }

    // Projectiles disappear if too far off screen
    public void update(ControlPanel panel) {
        this.x = (int) rawX;
        this.y = (int) rawY;
        if (this.getX() < -500 || this.getX() > ControlPanel.width + 500 || this.getY() < -this.getHeight() - 500 ||
                this.getY() > ControlPanel.height + 500) {
            if (enemyPokemon == null) {
                ControlPanel.playerProjectilesToRemove.add(this);
            } else {
                ControlPanel.enemyProjectilesToRemove.add(this);
            }
        } else {
            boolean hit = false;
            // Attack enemies
            if (enemyPokemon == null) {
                for (Enemy e : ControlPanel.getEnemies()) {
                    // Checks for hits
                    if (checkCollision(e) && enemyPokemon == null) {
                        if (ControlPanel.rand.nextInt(300) < ControlPanel.player.getPokemon().getCritChance() * ((double) control.getPower() / 5.0)) { // Critical
                            // new HitFlash(0, 0, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT, Type.UNKNOWN, 300, 75);
                            SoundFX.HARD_HIT.play();
                            e.takeDamage((int) (((double) this.getAttack().getAttackDamage()
                                    * (Type.typeEffectiveness(e.getType1(), e.getType2(), this.getAttack().getType())) + 1.0)
                                    * (1.0 + (double) control.getPower() / 5.0)
                                    / 5.0 * ControlPanel.player.getPokemon().getAttackPower() / 70.0 * 2.0), attackType);
                        } else {
                            SoundFX.ROUND_HIT.play();
                            e.takeDamage((int) (((double) this.getAttack().getAttackDamage()
                                    * (Type.typeEffectiveness(e.getType1(), e.getType2(), this.getAttack().getType())) + 1.0)
                                    * (1.0 + (double) control.getPower() / 5.0)
                                    / 5.0 * ControlPanel.player.getPokemon().getAttackPower() / 70.0), attackType);
                        }
                        hit = true;
                    }
                }
            } else {
                // Attack player
                if (checkCollision(Player.getPlayer()) && enemyPokemon != null) {
                    if (ControlPanel.rand.nextInt(300) < enemyPokemon.getCritChance()) { // Critical
                        SoundFX.HARD_HIT.play();
                        Player.getPlayer().takeDamage((int) ((this.getAttack().getAttackDamage() *
                                (Type.typeEffectiveness(Player.getPlayer().getType1(),
                                        Player.getPlayer().getType2(), this.getAttack().getType())) + 1.0) / 5.0
                                * enemyPokemon.getAttackPower() / 70.0 * 2.0));
                    } else {
                        SoundFX.ROUND_HIT.play();
                        Player.getPlayer().takeDamage((int) ((this.getAttack().getAttackDamage() *
                                (Type.typeEffectiveness(Player.getPlayer().getType1(),
                                        Player.getPlayer().getType2(), this.getAttack().getType())) + 1.0) / 5.0
                                * enemyPokemon.getAttackPower() / 70.0));
                    }
                    hit = true;
                }
            }
            if (hit) {
                if (enemyPokemon == null) {
                    ControlPanel.playerProjectilesToRemove.add(this);
                } else {
                    ControlPanel.enemyProjectilesToRemove.add(this);
                }
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        square.setFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(this.getColor());
        g2.fill(square);
        g2.draw(square);
        if (enemyPokemon != null) {
            g2.drawImage(this.attackImage, this.getX(), this.getY()  + this.getHeight(), this.getWidth(), -this.getHeight(), control);
        } else {
            g2.drawImage(this.attackImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
        }
    }

    private boolean checkCollision(GameObject obj) {
        return (square.intersects(obj.getObj()));
    }

    public Timer getTimer() {
        return timer;
    }

    private Attack getAttack() {
        return attack;
    }
}