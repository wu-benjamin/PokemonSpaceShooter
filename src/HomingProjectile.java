import java.util.TimerTask;

public class HomingProjectile extends Projectile {

    Enemy nearestEnemy = null;

    HomingProjectile (int x, int y, int size, Attack attack, ControlPanel control, Pokemon... enemyPokemon) {
        super(x, y, size, attack, control, enemyPokemon);
        MoveTask moveTask = new MoveTask();
        timer.schedule(moveTask, 0, 8);
        this.xComponent = 0;
        this.yComponent = 0;
    }

    class MoveTask extends TimerTask {
        @Override
        public void run() {
            if (enemyPokemon == null) {
                //find nearest enemy
                double nearest = Double.MAX_VALUE;
                double prevNearest;
                // Find nearest enemy
                if (nearestEnemy == null) {
                    for (int i = 0; i < ControlPanel.getEnemies().size(); i++) {
                        if ((double) ControlPanel.getEnemies().get(i).getY() + (double) ControlPanel.getEnemies().get(i).getHeight() / 2.0
                                <= (double) y + (double) height / 2.0) {
                            prevNearest = nearest;
                            nearest = Math.min(nearest, Math.sqrt(Math.pow(((double) ControlPanel.getEnemies().get(i).getY() + (double) ControlPanel.getEnemies().get(i).getWidth() / 2.0)
                                    - ((double) y + (double) width / 2.0), 2)
                                    + Math.pow(((double) ControlPanel.getEnemies().get(i).getX() + (double) ControlPanel.getEnemies().get(i).getWidth() / 2.0)
                                    - ((double) x + (double) width / 2.0), 2)));
                            if (nearest < prevNearest) {
                                nearestEnemy = ControlPanel.getEnemies().get(i);
                            }
                        }
                    }
                }
                if (nearestEnemy != null) {
                    if ((double) nearestEnemy.getY() + (double) nearestEnemy.getHeight() / 2.0
                            <= (double) y + (double) width / 2.0) {
                        xComponent = (((double) nearestEnemy.getX() + (double) nearestEnemy.getWidth() / 2.0) - ((double) x + (double) width / 2.0));
                        yComponent = (((double) nearestEnemy.getY() + (double) nearestEnemy.getHeight() / 2.0) - ((double) y + (double) height / 2.0));
                        xComponent *= (double) attack.getProjectileSpeed() / 2.0 / Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponent, 2));
                        yComponent *= (double) attack.getProjectileSpeed() / 2.0 / Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponent, 2));
                    }
                }
            } else {
                if ((double) ControlPanel.player.getY() + (double) ControlPanel.player.getHeight() / 2.0 >= (double) y + (double) height / 2.0) {
                    xComponent = (((double) ControlPanel.player.getX() + (double) ControlPanel.player.getWidth() / 2.0) - ((double) x + (double) width / 2.0));
                    yComponent = (((double) ControlPanel.player.getY() + (double) ControlPanel.player.getHeight() / 2.0) - ((double) y + (double) height / 2.0));
                    xComponent *= (double) attack.getProjectileSpeed() / 2.0 / Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponent, 2));
                    yComponent *= (double) attack.getProjectileSpeed() / 2.0 / Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponent, 2));
                }
            }
            if ((int) xComponent == 0 && (int) yComponent == 0) {
                if (enemyPokemon == null) {
                    yComponent = -attack.getProjectileSpeed();
                } else {
                    yComponent = attack.getProjectileSpeed();
                }
            }
            rawX += xComponent;
            rawY += yComponent;
        }
    }
}
