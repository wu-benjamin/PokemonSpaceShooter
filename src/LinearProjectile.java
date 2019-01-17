import java.util.TimerTask;

public class LinearProjectile extends Projectile {
    LinearProjectile(int x, int y, int size, ControlPanel control, int xComponent, int yComponent, Attack attack, Pokemon... enemyPokemon) {
        super(x, y, size, attack, control, enemyPokemon);
        this.yComponent = Math.max(attack.getProjectileSpeed() + Math.abs(yComponent / 3.0), 1);
        this.xComponent = (double) xComponent / 3.0;
        MoveTask moveTask = new MoveTask();
        timer.schedule(moveTask, 0, 17);
    }

    class MoveTask extends TimerTask {
        @Override
        public void run() {
            rawX += xComponent;
            if (enemyPokemon == null) {
                rawY -= yComponent;
            } else {
                rawY += yComponent;
            }
        }
    }
}
