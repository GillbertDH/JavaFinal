package application;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shape.Circle;
import shape.FreeLine;
import shape.Line;
import shape.Rectangle;
import shape.Shape;

public class MyMouseHandler implements EventHandler<MouseEvent> {
    private GraphicsContext gc;
    private ToolManager toolManager;
    private ShapeManager shapeManager;
    private Canvas canvas;
    private double clickedX, clickedY;
    private FreeLine currentFreeLine;
    
    public void setToolManager(ToolManager tm) { toolManager = tm; }
    public void setShapeManager(ShapeManager sm) { shapeManager = sm; }
    public void setCanvas(Canvas cv) { canvas = cv; }
    public void setGC(GraphicsContext gc) { this.gc = gc; }

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
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    shapeManager.drawAll(gc); // ★ 수정됨
                }
            }
            else if (toolManager.getCurrentMode() == ToolManager.DRAW_FREELINE) {
                currentFreeLine = new FreeLine();
                currentFreeLine.setColor(Color.TRANSPARENT, toolManager.getCurrentColor());
                currentFreeLine.addPoint(x, y);
            }
        }
        
        else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (toolManager.getCurrentMode() == ToolManager.DRAW_FREELINE && currentFreeLine != null) {
                currentFreeLine.addPoint(x, y); // 움직일 때마다 점 추가
                
                // 화면 초기화 후 기존 그림 + 지금 그리고 있는 선 실시간 렌더링
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeManager.drawAll(gc);
                currentFreeLine.draw(gc); 
            }
        }
        
        else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            if (toolManager.getCurrentMode() == ToolManager.DRAW_RECT) {
                double originX, originY, width, height;

                if (clickedX < x) { originX = clickedX; width = x - clickedX; } 
                else { originX = x; width = clickedX - x; }

                if (clickedY < y) { originY = clickedY; height = y - clickedY; } 
                else { originY = y; height = clickedY - y; }

                Rectangle rect = new Rectangle(); // ★ 수정됨
                rect.setOriginXY(originX, originY);
                rect.setWidthHeight(width, height);
                rect.setColor(toolManager.getCurrentColor(), Color.BLACK);

                rect.draw(gc); // ★ 수정됨
                shapeManager.addShape(rect);

            } else if (toolManager.getCurrentMode() == ToolManager.DRAW_CIRCLE) {
                double radius = Math.sqrt((x - clickedX) * (x - clickedX) + (y - clickedY) * (y - clickedY));
                
                Circle cir = new Circle(); // ★ 수정됨
                cir.setColor(toolManager.getCurrentColor(), Color.BLACK);
                cir.setCenterXY(clickedX, clickedY);
                cir.setRadius(radius);
                
                cir.draw(gc); // ★ 수정됨
                shapeManager.addShape(cir);

            } else if (toolManager.getCurrentMode() == ToolManager.DRAW_LINE) {
                Line line = new Line(); // ★ 수정됨
                line.setStartEndXY(clickedX, clickedY, x, y);
                line.setColor(Color.TRANSPARENT, toolManager.getCurrentColor());
                
                line.draw(gc); // ★ 수정됨
                shapeManager.addShape(line);
                
            }
            
            else if (toolManager.getCurrentMode() == ToolManager.DRAW_FREELINE) {
                if (currentFreeLine != null) {
                    shapeManager.addShape(currentFreeLine); // 창고에 넣으면서 백업(saveState) 됨
                    currentFreeLine = null; // 초기화
                }
            }
            
            else if (toolManager.getCurrentMode() == ToolManager.MOVE_SHAPE) {
                Shape selectedShape = shapeManager.findShape(clickedX, clickedY);
                if (selectedShape != null) {
                	shapeManager.saveState();
                    selectedShape.move(x - clickedX, y - clickedY);
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    shapeManager.drawAll(gc); // ★ 수정됨
                }
            }
            
            
        }
    }
}