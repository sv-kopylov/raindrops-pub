package ru.kopylov.raindrops.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VLayerIteratorTest {

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
    public void next() {
         VLayerIterator iter = new VLayerIterator(space, 1);
        int cnt=0;
        while (iter.hasNext()){
            cnt++;
            System.out.println(iter.next());
        }
        assertEquals(4,cnt);
    }
    @Test
    public void sum() {
        VLayerIterator iter = new VLayerIterator(space, 0);
        long res = iter.sum();
        System.out.println(res);
        assertEquals(6,res);
        System.out.println(iter.toString());
    }

    @Test
    public void representation(){
        AbstractLayerIterator iter = new VLayerIterator(space, 0);
        System.out.println(iter.toString());
        iter = new VLayerIterator(space, 1);
        System.out.println(iter.toString());
        iter = new HLayerIterator(space, 0);
        System.out.println(iter.toString());
        iter = new HLayerIterator(space, 1);
        System.out.println(iter.toString());

    }

    @Test
    public void spotSum() {
        byte[][][] space = new byte[1][5][5];
        VLayerIterator iterator = new VLayerIterator(space, 0);
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


//        for(int i=0; i<5; i++){
//            System.out.print(i+" ");
//            System.out.println(iterator.spotSum(i,3));
//        }

    }
}