package shape;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class FreeLine extends Shape {
    private ArrayList<Double> xPoints;
    private ArrayList<Double> yPoints;

    public FreeLine() {
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
    }

    // 마우스가 움직일 때마다 점을 추가하는 함수
    public void addPoint(double x, double y) {
        xPoints.add(x);
        yPoints.add(y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (xPoints.size() < 2) return; // 점이 2개 이상이어야 선을 그림

        gc.setStroke(getLineColor());
        gc.setLineWidth(2);
        
        // 붓을 첫 번째 점으로 이동
        gc.beginPath();
        gc.moveTo(xPoints.get(0), yPoints.get(0));
        
        // 나머지 점들을 쭉 이어서 그리기
        for (int i = 1; i < xPoints.size(); i++) {
            gc.lineTo(xPoints.get(i), yPoints.get(i));
        }
        gc.stroke(); // 화면에 표시!
    }

    @Override
    public boolean isMyRange(double px, double py) {
        // 내가 그렸던 수많은 점들 중 하나라도 마우스 클릭 위치와 5픽셀 이내로 가까우면 선택!
        for (int i = 0; i < xPoints.size(); i++) {
            double dist = Math.hypot(px - xPoints.get(i), py - yPoints.get(i));
            if (dist <= 5.0) return true;
        }
        return false;
    }

    @Override
    public void move(double dx, double dy) {
        // 모든 점들의 위치를 dx, dy만큼 한 번에 이동
        for (int i = 0; i < xPoints.size(); i++) {
            xPoints.set(i, xPoints.get(i) + dx);
            yPoints.set(i, yPoints.get(i) + dy);
        }
    }
}