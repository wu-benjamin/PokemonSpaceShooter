import java.util.TimerTask;

public class RadialProjectile extends Projectile {
    RadialProjectile(int x, int y, int size, ControlPanel control, int xComponent, int yComponent, Attack attack, Pokemon... enemyPokemon) {
        super(x, y, size, attack, control, enemyPokemon);
        this.xComponent = xComponent;
        this.yComponent = yComponent;
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
