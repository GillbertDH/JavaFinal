package application;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import shape.Circle;
import shape.Rectangle;
import shape.Shape;
import shape.Line;
import shape.FreeLine;

public class MyMouseHandler implements EventHandler<MouseEvent> {
    private GraphicsContext gc;
    private ToolManager toolManager;
    private ShapeManager shapeManager;
    private Canvas canvas;
    private double clickedX, clickedY;
    
    private FreeLine currentFreeLine;

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
        double x = event.getX();
        double y = event.getY();

        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            clickedX = x;
            clickedY = y;

            if (toolManager.getCurrentMode() == ToolManager.REMOVE_SHAPE) {
                Shape selectedShape = shapeManager.findShape(clickedX, clickedY);
                if (selectedShape != null) {
                    shapeManager.removeShape(selectedShape);
                    redraw();
                }
            } else if (toolManager.getCurrentMode() == ToolManager.DRAW_FREELINE) {
                currentFreeLine = new FreeLine();
                currentFreeLine.setColor(toolManager.getFillColor(), toolManager.getLineColor());
                currentFreeLine.setLineWidth(toolManager.getLineWidth());
                currentFreeLine.addPoint(x, y);
            }
        } 
        else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (toolManager.getCurrentMode() == ToolManager.DRAW_FREELINE && currentFreeLine != null) {
                currentFreeLine.addPoint(x, y);
                // 실시간으로 선을 그리기 위해 현재까지의 선을 그립니다.
                currentFreeLine.draw(gc); 
            }
        }
        else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            if (toolManager.getCurrentMode() == ToolManager.DRAW_RECT) {
                double originX = Math.min(clickedX, x);
                double width = Math.abs(x - clickedX);
                double originY = Math.min(clickedY, y);
                double height = Math.abs(y - clickedY);

                Rectangle rect = new Rectangle();
                rect.setOriginXY(originX, originY);
                rect.setWidthHeight(width, height);
                rect.setColor(toolManager.getFillColor(), toolManager.getLineColor());
                rect.setLineWidth(toolManager.getLineWidth());
                rect.setCenterXY(originX + width / 2, originY + height / 2);
                
                shapeManager.addShape(rect);
                redraw();
            } else if (toolManager.getCurrentMode() == ToolManager.DRAW_CIRCLE) {
                double radius = Math.sqrt((x - clickedX) * (x - clickedX) + (y - clickedY) * (y - clickedY));
                Circle cir = new Circle();
                cir.setColor(toolManager.getFillColor(), toolManager.getLineColor());
                cir.setLineWidth(toolManager.getLineWidth());
                cir.setCenterXY(clickedX, clickedY);
                cir.setRadius(radius);
                
                shapeManager.addShape(cir);
                redraw();
            } else if (toolManager.getCurrentMode() == ToolManager.DRAW_LINE) {
                Line line = new Line();
                line.setColor(toolManager.getFillColor(), toolManager.getLineColor());
                line.setLineWidth(toolManager.getLineWidth());
                line.setPoints(clickedX, clickedY, x, y);
                
                shapeManager.addShape(line);
                redraw();
            } else if (toolManager.getCurrentMode() == ToolManager.DRAW_FREELINE) {
                if (currentFreeLine != null) {
                    shapeManager.addShape(currentFreeLine);
                    currentFreeLine = null;
                    redraw();
                }
            } else if (toolManager.getCurrentMode() == ToolManager.MOVE_SHAPE) {
                Shape selectedShape = shapeManager.findShape(clickedX, clickedY);
                if (selectedShape != null) {
                    selectedShape.move(x - clickedX, y - clickedY);
                    shapeManager.onMoveFinished(); // 변경 후 상태 저장
                    redraw();
                }
            }
        }
    }
    
    private void redraw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        shapeManager.drawAll(gc);
    }
}