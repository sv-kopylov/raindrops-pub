package ru.kopylov.raindrops.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelperTest {

    @Test
    public void dropsInLayer() {
        double intensity = 0.25/3600;
        double diameter = 0.5;
        double a = Helper.DropsInLayer(intensity, diameter);
        assertEquals(5.30516477, a, 0.0001);
        System.out.println(a);
    }
}