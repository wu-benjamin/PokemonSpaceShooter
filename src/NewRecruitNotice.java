import java.awt.*;
import java.util.ArrayList;

public class NewRecruitNotice extends Flash {

    private static ArrayList<String> newRecruitNames = new ArrayList<>();
    private static Font font;
    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(50f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    NewRecruitNotice() {
        super(0, 0, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT, 2000);
    }

    void addNewRecruit(String newRecruitName) {
        time = 0;
        newRecruitNames.add(newRecruitName);
    }

    int size() {
        return newRecruitNames.size();
    }

    public void update(ControlPanel panel) {
        if (time > duration) {
            if (newRecruitNames.size() > 0) {
                newRecruitNames.remove(0);
                time = 0;
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        if (newRecruitNames.size() > 0) {
            HUD.drawCenteredString(g2, new Rectangle(0, 0, ControlPanel.width, ControlPanel.height), "You Befriended\n" + newRecruitNames.get(0) + "!", font);
        }
    }
}
