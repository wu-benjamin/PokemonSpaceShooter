public class Bomb extends PowerUp {

    Bomb(int x, int y, int width, int height, ControlPanel control) {
        super(x, y, width, height, control, bombImage);
    }

    public void performEffect(ControlPanel control) {
        control.incrementBombs();
        SoundFX.BOMB.play();
    }
}
