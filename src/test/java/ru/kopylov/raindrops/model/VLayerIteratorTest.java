package ru.kopylov.raindrops.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class VLayerIteratorTest {

    @Test
    public void next() {
        byte[][][] space = new byte[2][2][2];
        space[0][0][0] = 0;
        space[0][0][1] = 1;
        space[0][1][0] = 2;
        space[0][1][1] = 3;
        space[1][0][0] = 4;
        space[1][0][1] = 5;
        space[1][1][0] = 6;
        space[1][1][1] = 7;

        VLayerIterator iter = new VLayerIterator(space, 1);
        int cnt=0;
        while (iter.hasNext()){
            cnt++;
//            System.out.println(iter.next());
        }
        assertEquals(4,cnt);


    }
}