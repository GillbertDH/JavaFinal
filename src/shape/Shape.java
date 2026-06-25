package shape;

import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// ★ Serializable 인터페이스 구현
public abstract class Shape implements Serializable {
    private static final long serialVersionUID = 1L;

    // ★ Color 대신 RGB와 투명도(Opacity) 값을 실수로 저장
    protected double fR, fG, fB, fA;
    protected double lR, lG, lB, lA;

    protected double cx, cy;

    // ★ 생성자에서 gc를 더 이상 받지 않음
    public Shape() {
    }

    public void setColor(Color fColor, Color lColor) {
        fR = fColor.getRed(); fG = fColor.getGreen(); fB = fColor.getBlue(); fA = fColor.getOpacity();
        lR = lColor.getRed(); lG = lColor.getGreen(); lB = lColor.getBlue(); lA = lColor.getOpacity();
    }

    protected Color getFillColor() { 
        return Color.color(fR, fG, fB, fA); 
    }
    
    protected Color getLineColor() { 
        return Color.color(lR, lG, lB, lA); 
    }

    public void setCenterXY(double cx, double cy) {
        this.cx = cx;
        this.cy = cy;
    }

    // ★ 그릴 때만 붓(gc)을 파라미터로 넘겨받도록 수정
    public abstract void draw(GraphicsContext gc);
    public abstract boolean isMyRange(double x, double y);
    public abstract void move(double dx, double dy);
}