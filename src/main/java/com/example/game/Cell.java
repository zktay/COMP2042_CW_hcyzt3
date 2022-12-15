package com.example.game;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Ze Tay-modified
 *  Cell.java
 *  To set the size, number, and the color of the cells.
 *
 */
public class Cell {
    private Rectangle rectangle;
    private Group root;
    private Text textClass;
    private boolean modify = false;

    void setModify(boolean modify) {
        this.modify = modify;
    }

    boolean getModify() {
        return modify;
    }

    //Setting the background cell placement to the board, gray boxes.
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

    /**
     * Changing the cell's value and it's color
     * @param cell cell pass from controller to get its value
     *
     */

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

    /**
     * Changing the number of the cells after additional
     * @param cell Cell pass from controller
     *
     */
    void adder(Cell cell) {
        cell.getTextClass().setText((cell.getNumber() + this.getNumber()) + "");
        cell.getTextClass().setTextAlignment(TextAlignment.CENTER);
        textClass.setText("0");
        root.getChildren().remove(textClass);
        cell.setColorByNumber(cell.getNumber());
        setColorByNumber(getNumber());
    }


    /**
     * Switch case using lambda to set colors of the cells
     * @param number number pass from spawn and use the number to determine the color of the cell
     *
     */
    void setColorByNumber(int number) {
        switch (number) {
            case 0 -> rectangle.setFill(Color.rgb(224, 226, 226, 0.5));
            case 2, 3 -> rectangle.setFill(Color.rgb(232, 255, 100, 0.5));
            case 4, 6 -> rectangle.setFill(Color.rgb(232, 220, 50, 0.5));
            case 8, 12 -> rectangle.setFill(Color.rgb(232, 200, 44, 0.8));
            case 16, 24 -> rectangle.setFill(Color.rgb(232, 170, 44, 0.8));
            case 32, 48 -> rectangle.setFill(Color.rgb(180, 120, 44, 0.7));
            case 64, 96 -> rectangle.setFill(Color.rgb(180, 100, 44, 0.7));
            case 128, 192 -> rectangle.setFill(Color.rgb(180, 80, 44, 0.7));
            case 256, 384 -> rectangle.setFill(Color.rgb(180, 60, 44, 0.8));
            case 512, 768 -> rectangle.setFill(Color.rgb(180, 30, 44, 0.8));
            case 1024, 1536 -> rectangle.setFill(Color.rgb(250, 0, 44, 0.8));
            case 2048, 3072 -> rectangle.setFill(Color.rgb(250, 0, 0, 1));
            //Set default color if the cell value goes over 2048
            default -> rectangle.setFill(Color.rgb(64, 60, 52));
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
