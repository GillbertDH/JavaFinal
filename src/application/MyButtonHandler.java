package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

public class MyButtonHandler implements EventHandler<ActionEvent> {
    private ToolManager toolManager;
    private ShapeManager shapeManager; 
    private Canvas canvas;             

    public void setToolManager(ToolManager tm) {
        this.toolManager = tm;
    }
    public void setShapeManager(ShapeManager sm) { 
        this.shapeManager = sm;
    }
    public void setCanvas(Canvas cv) {             
        this.canvas = cv;
    }

    @Override
    public void handle(ActionEvent arg0) {
        Button btn = (Button)arg0.getSource();
        System.out.println(btn.getText() + " 버튼이 클릭 되었습니다.");

        if (btn.getText().compareTo("사각형") == 0) {
            toolManager.setCurrentMode(ToolManager.DRAW_RECT);
        } else if (btn.getText().compareTo("원") == 0) {
            toolManager.setCurrentMode(ToolManager.DRAW_CIRCLE);
        } else if (btn.getText().compareTo("이동") == 0) {
            toolManager.setCurrentMode(ToolManager.MOVE_SHAPE);
        } else if (btn.getText().compareTo("삭제") == 0) {
            toolManager.setCurrentMode(ToolManager.REMOVE_SHAPE);
        } 
       
        else if (btn.getText().compareTo("전체삭제") == 0) {
            shapeManager.clearAll(); 
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); 
        }
    }
}