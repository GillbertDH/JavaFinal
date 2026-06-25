package application;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 1. 전체 레이아웃을 위한 BorderPane 생성
        BorderPane root = new BorderPane();
        ToolManager toolManager = new ToolManager();
        ShapeManager shapeManager = new ShapeManager();

        // 2. 오른쪽: Canvas 생성 및 초기화
        Canvas canvas = new Canvas(1600, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        MyMouseHandler mouseHandler = new MyMouseHandler();
        mouseHandler.setGC(gc);
        mouseHandler.setToolManager(toolManager);
        mouseHandler.setShapeManager(shapeManager);
        mouseHandler.setCanvas(canvas);
        
        
        

        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseReleased(mouseHandler);
        canvas.setOnMouseDragged(mouseHandler);

        // 3. 왼쪽: 버튼 4개를 담을 VBox 생성
        VBox sideMenu = new VBox(15); // 버튼 사이 간격 15
        sideMenu.setPadding(new Insets(15)); // 안쪽 여백
        sideMenu.setAlignment(Pos.TOP_CENTER);
        sideMenu.setStyle("-fx-background-color: #eeeeee;"); // 배경색 구분

        Button btnRect = new Button("사각형");
        Button btnCircle = new Button("원");
        Button btnMove = new Button("이동");
        Button btnClear = new Button("삭제");
        Button btnClearAll = new Button("전체삭제");
        Button btnLine = new Button("선");
        Button btnFreeLine = new Button("자유곡선");
        Button btnUndo = new Button("실행취소"); 
        Button btnRedo = new Button("다시실행"); 
        Button btnSave = new Button("저장");
        Button btnLoad = new Button("불러오기");
        
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setMaxWidth(Double.MAX_VALUE);
        
        colorPicker.setOnAction(event -> {
            toolManager.setCurrentColor(colorPicker.getValue());
        });
        
        // 모든 버튼의 너비를 동일하게 설정
        btnRect.setMaxWidth(Double.MAX_VALUE);
        btnCircle.setMaxWidth(Double.MAX_VALUE);
        btnMove.setMaxWidth(Double.MAX_VALUE);
        btnClear.setMaxWidth(Double.MAX_VALUE);
        btnClearAll.setMaxWidth(Double.MAX_VALUE);
        btnLine.setMaxWidth(Double.MAX_VALUE);
        btnUndo.setMaxWidth(Double.MAX_VALUE); 
        btnRedo.setMaxWidth(Double.MAX_VALUE); 
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnLoad.setMaxWidth(Double.MAX_VALUE);
        
        MyButtonHandler btnHandler = new MyButtonHandler();
        btnHandler.setToolManager(toolManager);
        btnHandler.setShapeManager(shapeManager);  
        btnHandler.setCanvas(canvas);
        
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("그림판 파일 선택");
        // 우리 프로그램만의 전용 확장자(.dat) 설정
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java 그림 파일 (*.dat)", "*.dat"));

        // ★ 저장 버튼 눌렀을 때의 동작
        btnSave.setOnAction(e -> {
            File file = fileChooser.showSaveDialog(primaryStage); // 윈도우 저장 창 띄우기
            if (file != null) {
                shapeManager.saveToFile(file);
            }
        });

        // ★ 불러오기 버튼 눌렀을 때의 동작
        btnLoad.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage); // 윈도우 열기 창 띄우기
            if (file != null) {
                shapeManager.loadFromFile(file, gc, canvas);
            }
        });
        
        btnRect.setOnAction(btnHandler);
        btnCircle.setOnAction(btnHandler);
        btnMove.setOnAction(btnHandler);
        btnClear.setOnAction(btnHandler);
        btnClearAll.setOnAction(btnHandler);
        btnLine.setOnAction(btnHandler);
        btnUndo.setOnAction(btnHandler); 
        btnRedo.setOnAction(btnHandler);
        
        // 5. 레이아웃에 구성 요소 배치
        sideMenu.getChildren().addAll(btnUndo, btnRedo, btnRect, btnCircle, btnLine, btnFreeLine, btnMove, btnClear, btnClearAll, colorPicker, btnSave, btnLoad);
        root.setLeft(sideMenu); // 왼쪽에 메뉴 배치
        root.setCenter(canvas); // 중앙(오른쪽)에 캔버스 배치

        // 6. 씬 및 스테이지 설정
        Scene scene = new Scene(root, 650, 400);
        primaryStage.setTitle("JavaFX Layout & Canvas 예제");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}