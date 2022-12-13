package com.example.demo;
/**
 *  TextMaker.java
 *  To control how text are showed in respective scene, including the color and the font size
 *
 */
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class TextMaker {
    private static TextMaker singleInstance = null;

    private TextMaker() {

    }

    static TextMaker getSingleInstance() {
        if (singleInstance == null)
            singleInstance = new TextMaker();
        return singleInstance;
    }

    Text madeText(String input, double xCell, double yCell, Group root) {
        double length = GameScene.getLENGTH();
        Text text = new Text(input);
        double fontSize;
        if (length <= 4){
            fontSize = (2.5 * length) / 7.0;
            text.setFont(Font.font(fontSize));
            text.relocate((xCell + (1.2)* length / 7.0), (yCell + 2 * length / 7.0));
        }else if (length >= 5){
            fontSize = (2.3 * length) / 9.0;
            text.setFont(Font.font(fontSize));
            text.relocate((xCell + (1.2)* length / 11.0), (yCell + 2 * length / 7.0));;
        }else if (length  >= 7 ){
            fontSize = (1.1 * length) / 7.0;
            text.setFont(Font.font(fontSize));
            text.relocate((xCell + (1)* length / 15.0), (yCell + 2 * length / 7.0));;
        }



        if (Setting.levelSelected.equals("Color")){
            text.setFill(Color.TRANSPARENT);
        }else {
            text.setFill(Color.WHITE);
        }


        return text;
    }

    static void changeTwoText(Text first, Text second) {
        String temp;
        temp = first.getText();
        first.setText(second.getText());
        second.setText(temp);

        double tempNumber;
        tempNumber = first.getX();
        first.setX(second.getX());
        second.setX(tempNumber);

        tempNumber = first.getY();
        first.setY(second.getY());
        second.setY(tempNumber);

    }

}
