package application;

import java.io.*; // ★ 직렬화를 위한 패키지
import java.util.ArrayList;
import java.util.Stack; // ★ 스택 자료구조

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import shape.Shape;

public class ShapeManager {
    private ArrayList<Shape> shapeList;
    
    // ★ 과거와 미래를 기억할 타임머신 스택 2개 생성
    private Stack<byte[]> undoStack;
    private Stack<byte[]> redoStack;

    public ShapeManager() {
        shapeList = new ArrayList<Shape>();
        undoStack = new Stack<byte[]>();
        redoStack = new Stack<byte[]>();
    }

    // ==========================================
    // [핵심 기술] 현재 도화지 상태를 바이트로 구워 스택에 저장
    // ==========================================
    public void saveState() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(shapeList); // 리스트 전체를 직렬화하여 바이트 배열로 변환
            oos.close();
            
            undoStack.push(baos.toByteArray()); // 스택에 저장
            redoStack.clear(); // 새로운 행동을 하면 미래(redo)는 사라짐
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void undo() {
        if (!undoStack.isEmpty()) {
            try {
                // 1. 현재 상태를 Redo 스택에 백업 (다시 실행을 위해)
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(shapeList);
                oos.close();
                redoStack.push(baos.toByteArray());

                // 2. Undo 스택에서 과거 상태 꺼내와서 복원(역직렬화)
                byte[] prevData = undoStack.pop();
                ByteArrayInputStream bais = new ByteArrayInputStream(prevData);
                ObjectInputStream ois = new ObjectInputStream(bais);
                shapeList = (ArrayList<Shape>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("더 이상 실행 취소할 작업이 없습니다.");
        }
    }

    @SuppressWarnings("unchecked")
    public void redo() {
        if (!redoStack.isEmpty()) {
            try {
                // 1. 현재 상태를 Undo 스택에 백업
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(shapeList);
                oos.close();
                undoStack.push(baos.toByteArray());

                // 2. Redo 스택에서 미래 상태 꺼내와서 복원
                byte[] nextData = redoStack.pop();
                ByteArrayInputStream bais = new ByteArrayInputStream(nextData);
                ObjectInputStream ois = new ObjectInputStream(bais);
                shapeList = (ArrayList<Shape>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ==========================================
    // 기존 기능들 (무언가 변하기 직전에 꼭 saveState() 호출!)
    // ==========================================
    public void addShape(Shape shape) {
        saveState(); // ★ 추가되기 직전의 상태 백업
        shapeList.add(shape);
        System.out.println(shape + " is added.");
    }

    public void removeShape(Shape shape) {
        saveState(); // ★ 삭제되기 직전의 상태 백업
        shapeList.remove(shape);
    }
    
    public void clearAll() {
        saveState(); // ★ 싹 지워지기 직전의 상태 백업
        shapeList.clear();
        System.out.println("도형 전부 삭제");
    }

    public Shape findShape(double x, double y) {
        // 나중에 그려진(위에 덮인) 도형이 먼저 잡히도록 역순으로 탐색하면 더 좋습니다.
        for (int i = shapeList.size() - 1; i >= 0; i--) {
            Shape shape = shapeList.get(i);
            if (shape.isMyRange(x, y)) {
                return shape;
            }
        }
        return null;
    }

    public void drawAll(GraphicsContext gc) {
        for (int i = 0; i < shapeList.size(); i++) {
            shapeList.get(i).draw(gc);
        }
    }
    
 // ==========================================
    // [Level 4] 파일 입출력 (Save & Load)
    // ==========================================
    public void saveToFile(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(shapeList); // 리스트 전체를 파일로 굽기!
            oos.close();
            System.out.println("파일 저장 성공: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(File file, GraphicsContext gc, Canvas canvas) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            // 1. 파일에서 리스트 읽어오기
            shapeList = (ArrayList<Shape>) ois.readObject();
            ois.close();
            
            // 2. 다른 그림을 불러왔으니 기존 타임머신은 초기화
            undoStack.clear(); 
            redoStack.clear();
            
            // 3. 화면 하얗게 지우고 불러온 그림으로 다시 그리기
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawAll(gc);
            
            System.out.println("파일 불러오기 성공: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}