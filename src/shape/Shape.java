package shape;

import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // 색상을 R, G, B, Opacity로 저장 (직렬화 위해)
    protected double fillR, fillG, fillB, fillA;
    protected double lineR, lineG, lineB, lineA;
    
    protected double lineWidth = 1.0;
    protected double cx, cy;

    public Shape() {
        fillR = 1; fillG = 1; fillB = 1; fillA = 1; // Default white fill
        lineR = 0; lineG = 0; lineB = 0; lineA = 1; // Default black line
    }

    public void setColor(Color fColor, Color lColor) {
        if(fColor != null) {
            fillR = fColor.getRed(); fillG = fColor.getGreen(); fillB = fColor.getBlue(); fillA = fColor.getOpacity();
        }
        if(lColor != null) {
            lineR = lColor.getRed(); lineG = lColor.getGreen(); lineB = lColor.getBlue(); lineA = lColor.getOpacity();
        }
    }
    
    public void setLineWidth(double width) {
        this.lineWidth = width;
    }

    protected Color getFillColor() {
        return new Color(fillR, fillG, fillB, fillA);
    }

    protected Color getLineColor() {
        return new Color(lineR, lineG, lineB, lineA);
    }

    public void setCenterXY(double cx, double cy) {
        this.cx = cx;
        this.cy = cy;
    }

    public abstract void draw(GraphicsContext gc);
    public abstract boolean isMyRange(double x, double y);
    public abstract void move(double dx, double dy);
}