package ru.kopylov.raindrops.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HLayerIteratorTest {
    byte[][][] space = new byte[2][2][2];

    @Before
    public void init(){
        space[0][0][0] = 0;
        space[0][0][1] = 1;
        space[0][1][0] = 2;
        space[0][1][1] = 3;
        space[1][0][0] = 4;
        space[1][0][1] = 5;
        space[1][1][0] = 6;
        space[1][1][1] = 7;

    }

    @Test
    public void get() {
        HLayerIterator iter = new HLayerIterator(space, 0);
        assertEquals(0, iter.get(0, 0));
        assertEquals(4, iter.get(1, 0));

    }

    @Test
    public void forEach() {
        HLayerIterator iter = new HLayerIterator(space, 0);
        System.out.println(iter.toString());

        iter.forEach( x->x=(byte)(x+1));
        assertEquals(1, iter.get(0, 0));
        assertEquals(5, iter.get(1, 0));
        System.out.println(iter.toString());

    }

    @Test
    public void hasNext() {
        HLayerIterator iter = new HLayerIterator(space, 0);
        assertTrue(iter.hasNext);
        while (iter.hasNext){
            iter.next();
        }
        assertTrue(!iter.hasNext());

    }

    @Test
    public void sum() {
        byte[][][] space = new byte[5][1][5];
        HLayerIterator iterator = new HLayerIterator(space, 0);
        InputDataSet ds = InputDataSet.getInstance();
        ds.setDropSize(1.2);
        ds.setRainIntensyty(50);
        RandomSeeder rs = new RandomSeeder();
        rs.reSeed(iterator);
    }


    @Test
    public void reset() {
        HLayerIterator iter = new HLayerIterator(space, 0);
        while (iter.hasNext){
            iter.next();
        }
        assertTrue(!iter.hasNext());
        iter.reset();
        assertTrue(iter.hasNext);
    }

    @Test
    public void clean() {
        byte[][][] space = new byte[5][1][5];
        HLayerIterator iterator = new HLayerIterator(space, 0);
        InputDataSet ds = InputDataSet.getInstance();
        ds.setDropSize(1.2);
        ds.setRainIntensyty(50);

        System.out.println(iterator.toString());
        RandomSeeder rs = new RandomSeeder();
        rs.reSeed(iterator);
        System.out.println(iterator.toString());
        iterator.clean();
        System.out.println(iterator.toString());
        long res = iterator.sum();
        assertEquals(0, res);
    }


    @Test
    public void spotSum() {
        byte[][][] space = new byte[5][1][5];
        HLayerIterator iterator = new HLayerIterator(space, 0);
        iterator.set(1, 2, (byte) 1);

        iterator.set(2, 1, (byte) 1);
        iterator.set(2, 3, (byte) 1);

        iterator.set(3, 1, (byte) 1);
        iterator.set(3, 2, (byte) 1);
        iterator.set(3, 3, (byte) 1);

        iterator.set(4, 0, (byte) 1);
        iterator.set(4, 1, (byte) 1);
        iterator.set(4, 3, (byte) 1);
        iterator.set(4, 4, (byte) 1);

        System.out.println(iterator.toString());

        assertEquals(7, iterator.spotSum(0,2));
        assertEquals(4, iterator.spotSum(1,2));
        assertEquals(1, iterator.spotSum(2,2));
        assertEquals(3, iterator.spotSum(3,2));
        assertEquals(5, iterator.spotSum(4,2));

        for(int i=0; i<5; i++){
            System.out.print(i+" ");
            System.out.println(iterator.spotSum(i,2));
        }



    }
}