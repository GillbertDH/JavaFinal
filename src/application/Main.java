package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 1. 전체 레이아웃을 위한 BorderPane 생성
        BorderPane root = new BorderPane();
        ToolManager toolManager = new ToolManager();
        ShapeManager shapeManager = new ShapeManager();

        // 2. 오른쪽: Canvas 생성 및 초기화
        Canvas canvas = new Canvas(500, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        MyMouseHandler mouseHandler = new MyMouseHandler();
        mouseHandler.setGC(gc);
        mouseHandler.setToolManager(toolManager);
        mouseHandler.setShapeManager(shapeManager);
        mouseHandler.setCanvas(canvas);

        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseReleased(mouseHandler);

        // 3. 왼쪽: 버튼 4개를 담을 VBox 생성
        VBox sideMenu = new VBox(15); // 버튼 사이 간격 15
        sideMenu.setPadding(new Insets(15)); // 안쪽 여백
        sideMenu.setAlignment(Pos.TOP_CENTER);
        sideMenu.setStyle("-fx-background-color: #eeeeee;"); // 배경색 구분

        Button btnRect = new Button("사각형");
        Button btnCircle = new Button("원");
        Button btnMove = new Button("이동");
        Button btnClear = new Button("삭제");

        // 모든 버튼의 너비를 동일하게 설정
        btnRect.setMaxWidth(Double.MAX_VALUE);
        btnCircle.setMaxWidth(Double.MAX_VALUE);
        btnMove.setMaxWidth(Double.MAX_VALUE);
        btnClear.setMaxWidth(Double.MAX_VALUE);

        MyButtonHandler btnHandler = new MyButtonHandler();
        btnHandler.setToolManager(toolManager);

        btnRect.setOnAction(btnHandler);
        btnCircle.setOnAction(btnHandler);
        btnMove.setOnAction(btnHandler);
        btnClear.setOnAction(btnHandler);

        // 5. 레이아웃에 구성 요소 배치
        sideMenu.getChildren().addAll(btnRect, btnCircle, btnMove, btnClear);
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