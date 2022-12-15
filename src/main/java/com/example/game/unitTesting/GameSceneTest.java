package com.example.game.unitTesting;

import com.example.game.GameScene;
import com.example.game.controller;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameSceneTest {
    @Test
    public void getLENGTH() {
        double length = controller.LENGTH = 100;
        assertEquals(length, GameScene.getLENGTH(), 1);
    }
}