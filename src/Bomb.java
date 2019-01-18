public class Bomb extends PowerUp {

    Bomb(int x, int y, int width, int height, ControlPanel control) {
        super(x, y, width, height, control, bombImage);
    }

    public void performEffect(ControlPanel control) {
        control.incrementBombs();
        AudioPlayer.playSoundFX(Audio.ITEM_FOUND.getAudioIn());
    }
}
