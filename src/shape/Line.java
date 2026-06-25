package shape;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {
    private double startX, startY;
    private double endX, endY;

    // ★ 생성자 파라미터 비움
    public Line() {
    }

    public void setStartEndXY(double sx, double sy, double ex, double ey) {
        this.startX = sx;
        this.startY = sy;
        this.endX = ex;
        this.endY = ey;
    }

    // ★ 파라미터로 gc를 받음
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(getLineColor());
        gc.setLineWidth(2);
        gc.strokeLine(startX, startY, endX, endY);
    }

    @Override
    public boolean isMyRange(double px, double py) {
        double l2 = Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2);
        if (l2 == 0) return Math.hypot(px - startX, py - startY) <= 5.0;

        double t = ((px - startX) * (endX - startX) + (py - startY) * (endY - startY)) / l2;
        t = Math.max(0, Math.min(1, t));

        double projX = startX + t * (endX - startX);
        double projY = startY + t * (endY - startY);

        double distance = Math.hypot(px - projX, py - projY);
        return distance <= 5.0;
    }

    @Override
    public void move(double dx, double dy) {
        startX += dx;
        startY += dy;
        endX += dx;
        endY += dy;
    }
}