package common.data;

public class GameData {
    private int displayWidth  = 1440;
    private int displayHeight = 810;

    public boolean debug = true;


    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

}
