import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Enemy extends Character {

    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage toShow;
    Attack attack;
    protected Enemy e;
    private final int X_COMPONENT = 0;
    private final int Y_COMPONENT = 3;
    Timer timer = new Timer();
    HealthBar health;
    int time;
    Pokemon p;

    Enemy(int x, int y, int width, int height, Color color, Pokemon p, ControlPanel control) {
        super(x, y, width, height, color, p, control);
        this.color = color;
        this.control = control;
        this.attack = p.getAttack();
        this.image1 = p.getFront1();
        this.image2 = p.getFront2();
        this.type1 = p.getType1();
        this.type2 = p.getType2();
        this.p = p;
        this.setHitPoints(p.getHitPoints());
        this.setMaxHitPoints(this.getHitPoints());
        this.square = new Rectangle2D.Double(x, y, this.width, this.height);
        toShow = image1;
        timer();
        ControlPanel.enemiesToAdd.add(this);
        this.e = this;
        this.health = new HealthBar(x, y + this.getHeight(), this.getWidth(), 15, color, this/*, control*/);
        ControlPanel.toAdd.add(health);
    }

    public Rectangle2D getObj() {
        return square;
    }

    public void update(ControlPanel panel) {
        // Despawns enemies when they are off screen
        if (this.getX() < 0 || this.getX() > ControlPanel.width || this.getY() > ControlPanel.height) {
            ControlPanel.toRemove.add(this);
            ControlPanel.enemiesToRemove.add(this);
            timer.cancel();
            timer.purge();
        }
    }

    private void setToShow(BufferedImage toShow) {
        this.toShow = toShow;
    }

    @Override
    public BufferedImage getToShow() {
        return toShow;
    }

    @Override
    public BufferedImage getImage1() {
        return image1;
    }

    @Override
    public BufferedImage getImage2() {
        return image2;
    }

    /*
    public boolean checkCollision(GameObject obj) {
        return (square.intersects((Rectangle2D) obj.getObj()));
    }
    */

    // Creates death animation and removes enemy when killed
    public void enemyDeath(Enemy e, Type type) {
        new Flash(0, 0, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT, 20, false, type);
        // Handles recruiting new Pokemon -- not yet fully implemented
        if (ControlPanel.rand.nextInt(1000) < ControlPanel.RECRUIT_RATE) {
            ControlPanel.unlockedPokemon[e.p.getIndex()] = true;
        }
        control.incrementScore(ControlPanel.SCORE_FOR_ENEMY_KILL);
        ControlPanel.toRemove.add(e.health);
        e.health = null;
        this.health = null;
        e.timer.cancel();
        e.timer.purge();
        ControlPanel.toRemove.add(e);
        ControlPanel.enemiesToRemove.add(e);
        // Drops powerups
        if (ControlPanel.rand.nextInt(1000) < ControlPanel.POWER_UP_DROP_RATE) {
            int random = ControlPanel.rand.nextInt(100);
            if (random < 25) {
                new PowerUp(e.getX() + e.getWidth() / 2 - PowerUp.getSize() / 2, e.getY() + e.getHeight() / 2 - PowerUp.getSize() / 2,
                        PowerUp.getSize(), PowerUp.getSize(), ControlPanel.TRANSPARENT, "Bomb", control);
            } else if (random < 50 && control.getPower() < 5) {
                new PowerUp(e.getX() + e.getWidth() / 2 - PowerUp.getSize() / 2, e.getY() + e.getHeight() / 2 - PowerUp.getSize() / 2,
                        PowerUp.getSize(), PowerUp.getSize(), ControlPanel.TRANSPARENT, "RareCandy", control);
            } else if (random < 100) {
                new PowerUp(e.getX() + e.getWidth() / 2 - PowerUp.getSize() / 2, e.getY() + e.getHeight() / 2 - PowerUp.getSize() / 2,
                        PowerUp.getSize(), PowerUp.getSize(), ControlPanel.TRANSPARENT, "OranBerry", control);
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        square.setFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(color);
        g2.fill(square);
        g2.draw(square);
        g2.drawImage(toShow, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void timer() {
        TimerTask attackTask = new AttackTask();
        TimerTask moveTask = new MoveTask();
        TimerTask imageTask = new ImageTask();
        timer.schedule(attackTask, 0, (int) (1.5 * attack.getAttackDelay() * (ControlPanel.rand.nextInt(200) + 300) / 100));
        timer.schedule(moveTask, 0, 50);
        timer.schedule(imageTask, 0, 300);
    }


    // Ensures players can at least damage the opponent regardless of type effectiveness
    void takeDamage(int damage, Type type) {
        this.setHitPoints(this.getHitPoints() - Math.max(5, damage));
        if (e.getHitPoints() <= 0) {
            e.enemyDeath(e, type);
        }
    }

    // Moves enemies down and moves boss side to side using a parametric function of time
    class MoveTask extends TimerTask {
        @Override
        public void run() {
            try {
                e.setX(e.getX() + X_COMPONENT);
                e.setY(e.getY() + Y_COMPONENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Enemies fires an attack based on their species
    class AttackTask extends TimerTask {
        @Override
        public void run() {
            if (ControlPanel.enemyProjectiles.size() <= 15) {
                try {
                    ControlPanel.enemyProjectilesToAdd.add(new Projectile(e.getX() + e.getWidth() / 2 - attack.getProjectileSize() / 2,
                            e.getY() + e.getHeight(), attack.getProjectileSize(), color, attack, control, true, X_COMPONENT, Y_COMPONENT));
                } catch (NullPointerException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    // Animates enemy sprites
    class ImageTask extends TimerTask {
        @Override
        public void run() {
            double scale = e.getHeight() / e.getToShow().getHeight();
            int orgHeight = e.getHeight();
            int orgWidth = e.getWidth();
            if (e.getToShow().equals(e.getImage1())) {
                e.setToShow(e.getImage2());
            } else {
                e.setToShow(e.getImage1());
            }
            e.setWidth((int) (e.getToShow().getWidth() * scale));
            e.setHeight((int) (e.getToShow().getHeight() * scale));
            e.setX(e.getX() + (orgWidth - e.getWidth()) / 2);
            e.setY(e.getY() + (orgHeight - e.getHeight()) / 2);
        }
    }
}