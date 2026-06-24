package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import shape.Shape;

public class ShapeManager {
    private ArrayList<Shape> shapeList;
    private Stack<byte[]> undoStack;
    private Stack<byte[]> redoStack;

    public ShapeManager() {
        shapeList = new ArrayList<Shape>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        saveState(); // Initial state
    }

    public void addShape(Shape shape) {
        shapeList.add(shape);
        System.out.println(shape + " is added.");
        saveState();
    }

    public Shape findShape(double x, double y) {
        // 맨 위에 그려진 도형(리스트의 마지막)부터 찾도록 역순 검색
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

    public void removeShape(Shape shape) {
        shapeList.remove(shape);
        saveState();
    }
    
    // 이동 완료 후 상태 저장용
    public void onMoveFinished() {
        saveState();
    }

    // --- 상태 저장 (Undo / Redo 용 직렬화) ---
    private byte[] serializeShapes(ArrayList<Shape> list) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(list);
            oos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Shape> deserializeShapes(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (ArrayList<Shape>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Shape>();
        }
    }

    private void saveState() {
        byte[] data = serializeShapes(shapeList);
        if (data != null) {
            undoStack.push(data);
            redoStack.clear();
        }
    }

    public void undo() {
        if (undoStack.size() > 1) { // 초기 상태는 남겨둠
            redoStack.push(undoStack.pop()); // 현재 상태를 redo로
            byte[] previousData = undoStack.peek();
            shapeList = deserializeShapes(previousData);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            byte[] nextData = redoStack.pop();
            undoStack.push(nextData);
            shapeList = deserializeShapes(nextData);
        }
    }

    // --- 파일 입출력 ---
    public void saveToFile(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(shapeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            shapeList = (ArrayList<Shape>) ois.readObject();
            undoStack.clear();
            redoStack.clear();
            saveState(); // 불러온 상태를 초기 상태로
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}