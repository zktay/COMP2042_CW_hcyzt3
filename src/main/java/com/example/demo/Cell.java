package com.example.demo;
/**
 *  Cell.java
 *  To set the size, number, and the color of the cells.
 *
 */

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Cell {
    private Rectangle rectangle;
    private Group root;
    private Text textClass;
    private boolean modify = false;
    public long score = 0;

    void setModify(boolean modify) {
        this.modify = modify;
    }

    boolean getModify() {
        return modify;
    }

    //Setting the size, color of the cell and add it into the board.
    Cell(double x, double y, double scale, Group root) {
        rectangle = new Rectangle();
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setHeight(scale);
        rectangle.setWidth(scale);
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        this.root = root;
        rectangle.setFill(Color.rgb(224, 226, 226, 0.5));
        this.textClass = TextMaker.getSingleInstance().madeText("0", x, y, root);
        root.getChildren().add(rectangle);
    }

    void setTextClass(Text textClass) {
        this.textClass = textClass;
    }

    //Changing the cell color
    void changeCell(Cell cell) {
        TextMaker.changeTwoText(textClass, cell.getTextClass());
        root.getChildren().remove(cell.getTextClass());
        root.getChildren().remove(textClass);

        if (!cell.getTextClass().getText().equals("0")) {
            root.getChildren().add(cell.getTextClass());
        }
        if (!textClass.getText().equals("0")) {
            root.getChildren().add(textClass);
        }
        setColorByNumber(getNumber());
        cell.setColorByNumber(cell.getNumber());
    }

    //Changing the number of the cells after getting added
    void adder(Cell cell) {
        cell.getTextClass().setText((cell.getNumber() + this.getNumber()) + "");
        textClass.setText("0");
        root.getChildren().remove(textClass);
        cell.setColorByNumber(cell.getNumber());
        setColorByNumber(getNumber());
    }

    //Switch case of colors of the cells
    void setColorByNumber(int number) {
        switch (number) {
            case 0:
                rectangle.setFill(Color.rgb(224, 226, 226, 0.5));
                break;
            case 2, 3:
                rectangle.setFill(Color.rgb(232, 255, 100, 0.5));
                break;
            case 4, 6:
                rectangle.setFill(Color.rgb(232, 220, 50, 0.5));
                break;
            case 8, 12:
                rectangle.setFill(Color.rgb(232, 200, 44, 0.8));
                break;
            case 16, 24:
                rectangle.setFill(Color.rgb(232, 170, 44, 0.8));
                break;
            case 32, 48:
                rectangle.setFill(Color.rgb(180, 120, 44, 0.7));
                break;
            case 64, 96:
                rectangle.setFill(Color.rgb(180, 100, 44, 0.7));
                break;
            case 128, 192:
                rectangle.setFill(Color.rgb(180, 80, 44, 0.7));
                break;
            case 256, 384:
                rectangle.setFill(Color.rgb(180, 60, 44, 0.8));
                break;
            case 512, 768:
                rectangle.setFill(Color.rgb(180, 30, 44, 0.8));
                break;
            case 1024, 1536:
                rectangle.setFill(Color.rgb(250, 0, 44, 0.8));
                break;
            case 2048, 3072:
                rectangle.setFill(Color.rgb(250,0,0,1));
                break;
            default:
                rectangle.setFill(Color.rgb(64, 60, 52));
                break;

        }

    }

    double getX() {
        return rectangle.getX();
    }

    double getY() {
        return rectangle.getY();
    }

    int getNumber() {
        return Integer.parseInt(textClass.getText());
    }

    private Text getTextClass() {
        return textClass;
    }

}
