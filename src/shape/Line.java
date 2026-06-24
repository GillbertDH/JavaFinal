package shape;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {
    private static final long serialVersionUID = 1L;
    private double startX, startY;
    private double endX, endY;

    public Line() {
        super();
    }

    public void setPoints(double x1, double y1, double x2, double y2) {
        this.startX = x1;
        this.startY = y1;
        this.endX = x2;
        this.endY = y2;
        this.cx = (x1 + x2) / 2;
        this.cy = (y1 + y2) / 2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(getLineColor());
        gc.setLineWidth(lineWidth);
        gc.strokeLine(startX, startY, endX, endY);
    }

    @Override
    public boolean isMyRange(double x, double y) {
        double minX = Math.min(startX, endX) - 5;
        double maxX = Math.max(startX, endX) + 5;
        double minY = Math.min(startY, endY) - 5;
        double maxY = Math.max(startY, endY) + 5;
        
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    @Override
    public void move(double dx, double dy) {
        startX += dx;
        startY += dy;
        endX += dx;
        endY += dy;
        cx += dx;
        cy += dy;
    }
}
