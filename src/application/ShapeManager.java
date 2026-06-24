package application;

import java.util.ArrayList;
import shape.Shape;

public class ShapeManager {
    private ArrayList<Shape> shapeList;

    public ShapeManager() {
        shapeList = new ArrayList<Shape>();
    }

    public void addShape(Shape shape) {
        shapeList.add(shape);
        System.out.println(shape + " is added.");
    }

    public Shape findShape(double x, double y) {
        // (x, y)를 자신의 영역 안에 있는 모양을 찾아서 그 모양을 리턴함
        // 없으면 null 리턴
        for (int i = 0; i < shapeList.size(); i++) {
            Shape shape = shapeList.get(i);
            if (shape.isMyRange(x, y)) {
                return shape;
            }
        }
        return null;
    }

    public void drawAll() {
        for (int i = 0; i < shapeList.size(); i++) {
            shapeList.get(i).draw();
        }
    }

    public void removeShape(Shape shape) {
        shapeList.remove(shape);
    }
}