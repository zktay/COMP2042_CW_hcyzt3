package com.example.game.unitTesting;

import com.example.game.TextMaker;
import javafx.scene.text.Text;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextMakerTest {
    TextMaker t = new TextMaker();

    @Test
    public void changeTwoText() {
        Text first = new Text();
        Text second = new Text();
        first.setText("FIRST");
        second.setText("SECOND");
        t.changeTwoText(first, second);
        assertEquals("SECOND", first.getText());
    }
}