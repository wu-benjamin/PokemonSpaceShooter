import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Enemy extends Character {

    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage toShow;
    private final int X_COMPONENT = 0;
    private final int Y_COMPONENT = 3;
    Attack attack;
    Timer timer;
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
        this.setMaxHitPoints(p.getHitPoints());
        this.setHitPoints(p.getHitPoints());
        this.square = new Rectangle2D.Double(x, y, this.width, this.height);
        this.toShow = image1;
        this.timer = new Timer();
        timer();
        new HealthBar(x, y + this.getHeight(), this.getWidth(), 15, color, this/*, control*/);
    }

    public Rectangle2D getObj() {
        return square;
    }

    public void update(ControlPanel panel) {
        // De-spawns enemies when they are off screen
        if (x < 0 - width || x > ControlPanel.width || y > ControlPanel.height) {
            ControlPanel.enemiesToRemove.add(this);
        }
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

    // Ensures players can at least damage the opponent regardless of type effectiveness
    void takeDamage(int damage, Type type) {
        if (!ControlPanel.win && !ControlPanel.dead && this.getY() > -this.getHeight() && this.getY() < ControlPanel.height) {
            int hitPointsBefore = this.getHitPoints();
            this.setHitPoints(this.getHitPoints() - Math.max(ControlPanel.MIN_DAMAGE, damage));
            if (this.getHitPoints() <= 0 && hitPointsBefore > 0) {
                this.enemyDeath(this, type);
            }
        }
    }

    // Creates death animation and removes enemy when killed
    public void enemyDeath(Enemy e, Type type) {
        // new HitFlash(0, 0, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT, type, 100, 70);
        // Handles recruiting new Pokemon -- not yet fully implemented
        if (ControlPanel.rand.nextInt(1000) < ControlPanel.RECRUIT_RATE) {
            if (!ControlPanel.unlockedPokemon[e.p.getIndex()]) {
                ControlPanel.unlockedPokemon[e.p.getIndex()] = true;
                ControlPanel.recruitNotice.addNewRecruit(e.getPokemon().getName());
            }
        }
        control.incrementScore(ControlPanel.SCORE_FOR_ENEMY_KILL);
        ControlPanel.enemiesToRemove.add(e);
        // Drops power-ups
        if (ControlPanel.rand.nextInt(1000) < ControlPanel.POWER_UP_DROP_RATE) {
            int random = ControlPanel.rand.nextInt(PowerUp.BERRY_CHANCE + PowerUp.BOMB_CHANCE + PowerUp.CANDY_CHANCE);
            if (random < PowerUp.BOMB_CHANCE && control.getBombs() < PowerUp.MAX_BOMB) {
                new Bomb(e.getX() + e.getWidth() / 2 - PowerUp.getSize() / 2, e.getY() + e.getHeight() / 2 - PowerUp.getSize() / 2,
                        PowerUp.getSize(), PowerUp.getSize(), control);
            } else if (random < PowerUp.BOMB_CHANCE + PowerUp.CANDY_CHANCE && control.getPower() < PowerUp.MAX_LEVEL) {
                new RareCandy(e.getX() + e.getWidth() / 2 - PowerUp.getSize() / 2, e.getY() + e.getHeight() / 2 - PowerUp.getSize() / 2,
                        PowerUp.getSize(), PowerUp.getSize(), control);
            } else if (random < PowerUp.BOMB_CHANCE + PowerUp.CANDY_CHANCE + PowerUp.BERRY_CHANCE) {
                new OranBerry(e.getX() + e.getWidth() / 2 - PowerUp.getSize() / 2, e.getY() + e.getHeight() / 2 - PowerUp.getSize() / 2,
                        PowerUp.getSize(), PowerUp.getSize(), control);
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

    public Timer getTimer() {
        return timer;
    }

    public void timer() {
        TimerTask attackTask = new AttackTask();
        TimerTask moveTask = new MoveTask();
        TimerTask imageTask = new ImageTask();
        timer.schedule(attackTask, 0, (int) (1.5 * attack.getAttackDelay() / p.getAttackSpeed() * 60 * (ControlPanel.rand.nextInt(200) + 300) / 100));
        timer.schedule(moveTask, 0, 50);
        timer.schedule(imageTask, 0, 300);
    }

    // Moves enemies down and moves boss side to side using a parametric function of time
    class MoveTask extends TimerTask {
        @Override
        public void run() {
            try {
                x += X_COMPONENT;
                y += Y_COMPONENT;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Enemies fires an attack based on their species
    class AttackTask extends TimerTask {
        @Override
        public void run() {
            if (y >= -height) {
                try {
                    new ProjectileLauncher(x + width / 2 - attack.getProjectileSize() / 2,
                            y + height, attack.getProjectileSize(), control, X_COMPONENT, Y_COMPONENT, attack, p);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Animates enemy sprites
    class ImageTask extends TimerTask {
        @Override
        public void run() {
            double scale = height / toShow.getHeight();
            int orgHeight = height;
            int orgWidth = width;
            if (toShow.equals(image1)) {
                toShow = image2;
            } else {
                toShow = image1;
            }
            width = (int) (toShow.getWidth() * scale);
            height = (int) (toShow.getHeight() * scale);
            x += (orgWidth - width) / 2;
            y += (orgHeight - height) / 2;
        }
    }
}