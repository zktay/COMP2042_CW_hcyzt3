package com.example.game.unitTesting;

import org.junit.Test;
import com.example.game.controller;
import static org.junit.Assert.*;

public class controllerTest {
    controller c = new controller();
    @Test
    public void setN() {
        c.setN(5);
        assertEquals(5, controller.n);
    }

    @Test
    public void testLength() {
        c.setN(5);
        assertEquals(108, controller.LENGTH, 1);
    }


}