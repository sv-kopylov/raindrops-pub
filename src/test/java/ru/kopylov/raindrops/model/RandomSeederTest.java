package ru.kopylov.raindrops.model;

import org.junit.Test;

public class RandomSeederTest {



    @Test
    public void reSeed() {
        byte[][][] space = new byte[5][1][5];
        HLayerIterator iterator = new HLayerIterator(space, 0);

        System.out.println(iterator.toString());
        RandomSeeder rs = new RandomSeeder();
        rs.reSeed(iterator);
        System.out.println(iterator.toString());
    }
}