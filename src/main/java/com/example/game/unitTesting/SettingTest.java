package com.example.game.unitTesting;

import com.example.game.GameScene;
import com.example.game.Setting;
import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class SettingTest {
    Setting s = new Setting();
    Object gs = new GameScene();

    @Test
    public void SettingcolorSelect() {
        setcolorSelect();
        assertEquals(Color.rgb(1, 1, 1), Setting.colorSelected);
    }

    @Test
    public void setcolorSelect() {
        Setting.colorSelected = Color.rgb(1, 1, 1);
    }

    @Test
    public void levelSelect() {
        setLevelSelect();
        assertEquals("Easy", Setting.levelSelected);
    }

    @Test
    public void setLevelSelect() {
        Setting.levelSelected = "Easy";
    }

    @Test
    public void tilesSelect() {
        s.determine = "3x3";
        if (Objects.equals(s.determine, "3x3")) {
            GameScene.n = 3;
        } else if (Objects.equals(s.determine, "4x4")) {
            GameScene.n = 4;
        } else if (Objects.equals(s.determine, "5x5")) {
            GameScene.n = 5;
        } else if (Objects.equals(s.determine, "6x6")) {
            GameScene.n = 6;
        }
        assertEquals(3, GameScene.n);
    }

    @Test
    public void gameSceneLength() {
        s.determine = "3x3";
        if (Objects.equals(s.determine, "3x3")) {
            GameScene.LENGTH = (600 - ((4) * 10)) / (double) 3;
        } else if (Objects.equals(s.determine, "4x4")) {
            GameScene.LENGTH = (600 - ((5) * 10)) / (double) 4;
        } else if (Objects.equals(s.determine, "5x5")) {
            GameScene.LENGTH = (600 - ((6) * 10)) / (double) 5;
        } else if (Objects.equals(s.determine, "6x6")) {
            GameScene.LENGTH = (600 - ((7) * 10)) / (double) 6;
        }
        assertEquals(186, GameScene.LENGTH, 1);
    }

}