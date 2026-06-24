package shape;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class FreeLine extends Shape {
    private static final long serialVersionUID = 1L;
    private ArrayList<Double> xPoints;
    private ArrayList<Double> yPoints;

    public FreeLine() {
        super();
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
    }

    public void addPoint(double x, double y) {
        xPoints.add(x);
        yPoints.add(y);
        
        if (xPoints.size() == 1) {
            cx = x; cy = y;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (xPoints.size() < 2) return;
        
        gc.setStroke(getLineColor());
        gc.setLineWidth(lineWidth);
        
        gc.beginPath();
        gc.moveTo(xPoints.get(0), yPoints.get(0));
        for (int i = 1; i < xPoints.size(); i++) {
            gc.lineTo(xPoints.get(i), yPoints.get(i));
        }
        gc.stroke();
    }

    @Override
    public boolean isMyRange(double x, double y) {
        for (int i = 0; i < xPoints.size(); i++) {
            double px = xPoints.get(i);
            double py = yPoints.get(i);
            if (Math.abs(px - x) < 5 && Math.abs(py - y) < 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(double dx, double dy) {
        for (int i = 0; i < xPoints.size(); i++) {
            xPoints.set(i, xPoints.get(i) + dx);
            yPoints.set(i, yPoints.get(i) + dy);
        }
        cx += dx;
        cy += dy;
    }
}
