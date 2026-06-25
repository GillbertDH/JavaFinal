package shape;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {
    private double radius;

    public Circle(GraphicsContext gc) {
        super(gc);
    }

    public void setRadius(double r) {
        radius = r;
    }

    @Override
    public void draw() {
        gc.setFill(getFillColor());
        gc.fillOval(cx - radius, cy - radius, 2 * radius, 2 * radius);

        gc.setStroke(getLineColor());
        gc.setLineWidth(1);
        gc.strokeOval(cx - radius, cy - radius, 2 * radius, 2 * radius);
    }

    @Override
    public boolean isMyRange(double x, double y) {
        double distance = Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
        if (distance <= radius)
            return true;
        else
            return false;
    }

    @Override
    public void move(double dx, double dy) {
        cx += dx;
        cy += dy;
    }
}