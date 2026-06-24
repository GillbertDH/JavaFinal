package application;

public class ToolManager {
    public static final int DRAW_RECT = 1;
    public static final int DRAW_CIRCLE = 2;
    public static final int MOVE_SHAPE = 3;
    public static final int REMOVE_SHAPE = 4;

    private int currentMode;

    public ToolManager() {
        currentMode = DRAW_RECT;
    }

    public void setCurrentMode(int cm) {
        currentMode = cm;
    }

    public int getCurrentMode() {
        return currentMode;
    }
}