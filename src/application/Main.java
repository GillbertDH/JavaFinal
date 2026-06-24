package application;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        ToolManager toolManager = new ToolManager();
        ShapeManager shapeManager = new ShapeManager();

        Canvas canvas = new Canvas(900, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        MyMouseHandler mouseHandler = new MyMouseHandler();
        mouseHandler.setGC(gc);
        mouseHandler.setToolManager(toolManager);
        mouseHandler.setShapeManager(shapeManager);
        mouseHandler.setCanvas(canvas);

        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseDragged(mouseHandler);
        canvas.setOnMouseReleased(mouseHandler);

        // --- 상단 UI 구성 ---
        VBox topContainer = new VBox();
        
        // 1. MenuBar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("파일(F)");
        MenuItem saveItem = new MenuItem("저장하기");
        MenuItem loadItem = new MenuItem("불러오기");
        MenuItem exitItem = new MenuItem("종료");
        fileMenu.getItems().addAll(saveItem, loadItem, new SeparatorMenuItem(), exitItem);
        
        Menu editMenu = new Menu("편집(E)");
        MenuItem undoItem = new MenuItem("실행 취소 (Undo)");
        MenuItem redoItem = new MenuItem("다시 실행 (Redo)");
        editMenu.getItems().addAll(undoItem, redoItem);
        
        menuBar.getMenus().addAll(fileMenu, editMenu);

        // 2. ToolBar
        ToolBar toolBar = new ToolBar();
        Button btnRect = new Button("사각형");
        Button btnCircle = new Button("원");
        Button btnLine = new Button("직선");
        Button btnFreeLine = new Button("자유곡선");
        Button btnMove = new Button("이동");
        Button btnClear = new Button("삭제");
        
        ColorPicker fillColorPicker = new ColorPicker(Color.WHITE);
        ColorPicker lineColorPicker = new ColorPicker(Color.BLACK);
        Slider lineWidthSlider = new Slider(1, 10, 1);
        lineWidthSlider.setShowTickMarks(true);
        lineWidthSlider.setShowTickLabels(true);

        toolBar.getItems().addAll(
            btnRect, btnCircle, btnLine, btnFreeLine, new Separator(),
            btnMove, btnClear, new Separator(),
            new Label("채우기:"), fillColorPicker,
            new Label("선:"), lineColorPicker,
            new Label("굵기:"), lineWidthSlider
        );

        topContainer.getChildren().addAll(menuBar, toolBar);

        // --- 이벤트 연결 (람다 사용) ---
        btnRect.setOnAction(e -> toolManager.setCurrentMode(ToolManager.DRAW_RECT));
        btnCircle.setOnAction(e -> toolManager.setCurrentMode(ToolManager.DRAW_CIRCLE));
        btnLine.setOnAction(e -> toolManager.setCurrentMode(ToolManager.DRAW_LINE));
        btnFreeLine.setOnAction(e -> toolManager.setCurrentMode(ToolManager.DRAW_FREELINE));
        btnMove.setOnAction(e -> toolManager.setCurrentMode(ToolManager.MOVE_SHAPE));
        btnClear.setOnAction(e -> toolManager.setCurrentMode(ToolManager.REMOVE_SHAPE));

        fillColorPicker.setOnAction(e -> toolManager.setFillColor(fillColorPicker.getValue()));
        lineColorPicker.setOnAction(e -> toolManager.setLineColor(lineColorPicker.getValue()));
        lineWidthSlider.valueProperty().addListener((obs, oldVal, newVal) -> toolManager.setLineWidth(newVal.doubleValue()));

        // 메뉴 이벤트 연결
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("그림판 파일", "*.dat"));

        saveItem.setOnAction(e -> {
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                shapeManager.saveToFile(file);
            }
        });

        loadItem.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                shapeManager.loadFromFile(file);
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeManager.drawAll(gc);
            }
        });

        undoItem.setOnAction(e -> {
            shapeManager.undo();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            shapeManager.drawAll(gc);
        });

        redoItem.setOnAction(e -> {
            shapeManager.redo();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            shapeManager.drawAll(gc);
        });

        exitItem.setOnAction(e -> primaryStage.close());

        // 3. 레이아웃 배치
        root.setTop(topContainer);
        root.setCenter(canvas);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("자바 그림판 (기말과제 완성본)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}