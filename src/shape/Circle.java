package shape;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {
    private double radius;

    // ★ 생성자 파라미터 비움
    public Circle() {
    }

    public void setRadius(double r) {
        radius = r;
    }

    // ★ 파라미터로 gc를 받음
    @Override
    public void draw(GraphicsContext gc) {
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