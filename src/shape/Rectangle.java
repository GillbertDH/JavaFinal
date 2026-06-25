package shape;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {
    private double originX, originY;
    private double width, height;

    // ★ 생성자 파라미터 비움
    public Rectangle() {
        width = height = 0;
    }

    public void setOriginXY(double ox, double oy) {
        originX = ox;
        originY = oy;
    }

    public void setWidthHeight(double w, double h) {
        width = w;
        height = h;
    }

    // ★ 파라미터로 gc를 받음
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getFillColor());
        gc.fillRect(originX, originY, width, height);

        // 테두리 추가
        gc.setStroke(getLineColor());
        gc.setLineWidth(1);
        gc.strokeRect(originX, originY, width, height);
    }

    @Override
    public boolean isMyRange(double x, double y) {
        if (originX <= x && x <= originX + width) {
            if (originY <= y && y <= originY + height) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(double dx, double dy) {
        originX += dx;
        originY += dy;
        cx += dx;
        cy += dy;
    }
}