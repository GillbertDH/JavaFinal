package shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    private Color fillColor;
    private Color lineColor;
    protected GraphicsContext gc;
    protected double cx, cy;

    public Shape(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setColor(Color fColor, Color lColor) {
        fillColor = fColor;
        lineColor = lColor;
    }

    protected Color getFillColor() {
        return fillColor;
    }

    protected Color getLineColor() {
        return lineColor;
    }

    public void setCenterXY(double cx, double cy) {
        this.cx = cx;
        this.cy = cy;
    }

    public abstract void draw();
    public abstract boolean isMyRange(double x, double y);
    public abstract void move(double dx, double dy);
}