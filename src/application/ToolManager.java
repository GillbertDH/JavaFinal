package application;

import javafx.scene.paint.Color;

public class ToolManager {
    public static final int DRAW_RECT = 1;
    public static final int DRAW_CIRCLE = 2;
    public static final int MOVE_SHAPE = 3;
    public static final int REMOVE_SHAPE = 4;
    public static final int DRAW_LINE = 5;
    public static final int DRAW_FREELINE = 6;

    private int currentMode;
    
    private Color fillColor = Color.WHITE;
    private Color lineColor = Color.BLACK;
    private double lineWidth = 1.0;

    public ToolManager() {
        currentMode = DRAW_RECT;
    }

    public void setCurrentMode(int cm) {
        currentMode = cm;
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }
}