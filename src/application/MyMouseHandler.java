package application;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shape.Circle;
import shape.Rectangle;
import shape.Shape;

public class MyMouseHandler implements EventHandler<MouseEvent> {
    private GraphicsContext gc;
    private ToolManager toolManager;
    private ShapeManager shapeManager;
    private Canvas canvas;
    private double clickedX, clickedY;

    public void setToolManager(ToolManager tm) {
        toolManager = tm;
    }

    public void setShapeManager(ShapeManager sm) {
        shapeManager = sm;
    }

    public void setCanvas(Canvas cv) {
        canvas = cv;
    }

    public void setGC(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void handle(MouseEvent event) {
        // 클릭한 지점의 x, y 좌표 가져오기
        double x = event.getX();
        double y = event.getY();

        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("Mouse Pressed.");
            clickedX = x;
            clickedY = y;

            if (toolManager.getCurrentMode() == ToolManager.REMOVE_SHAPE) {
                Shape selectedShape = shapeManager.findShape(clickedX, clickedY);
                if (selectedShape != null) {
                    shapeManager.removeShape(selectedShape);
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    shapeManager.drawAll();
                }
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            System.out.println("Mouse Released.");

            if (toolManager.getCurrentMode() == ToolManager.DRAW_RECT) {
                double originX, originY;
                double width, height;

                if (clickedX < x) {
                    originX = clickedX;
                    width = x - clickedX;
                } else {
                    originX = x;
                    width = clickedX - x;
                }

                if (clickedY < y) {
                    originY = clickedY;
                    height = y - clickedY;
                } else {
                    originY = y;
                    height = clickedY - y;
                }

                Rectangle rect = new Rectangle(gc);
                rect.setOriginXY(originX, originY);
                rect.setWidthHeight(width, height);

                Color fColor = toolManager.getCurrentColor();
                Color lColor = Color.BLACK;
                rect.setColor(fColor, lColor);

                rect.setCenterXY(originX + width / 2, originY + height / 2);
                rect.draw();
                shapeManager.addShape(rect);

            } else if (toolManager.getCurrentMode() == ToolManager.DRAW_CIRCLE) {
                double radius = Math.sqrt((x - clickedX) * (x - clickedX) + (y - clickedY) * (y - clickedY));
                Circle cir = new Circle(gc);

                Color fColor = toolManager.getCurrentColor();
                Color lColor = Color.BLACK;
                cir.setColor(fColor, lColor);

                cir.setCenterXY(clickedX, clickedY);
                cir.setRadius(radius);
                cir.draw();
                shapeManager.addShape(cir);

            } else if (toolManager.getCurrentMode() == ToolManager.MOVE_SHAPE) {
                Shape selectedShape = shapeManager.findShape(clickedX, clickedY);
                if (selectedShape != null) {
                    selectedShape.move(x - clickedX, y - clickedY);
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    shapeManager.drawAll();
                }
            }
        }
    }
}