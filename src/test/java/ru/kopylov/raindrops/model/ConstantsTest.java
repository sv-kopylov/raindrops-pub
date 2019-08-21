package ru.kopylov.raindrops.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConstantsTest {

    @Test
    public void dropFallingSpeed() {
        double ds=0.5;
        while(ds<=7){
            System.out.format("%.1f ", ds);
            System.out.println(DropFallingSpeed(ds));
            ds+=0.1;
        }

    }

    static int DropFallingSpeed(double DropSize){
        if(DropSize>=0.5 && DropSize<5){
            return (int)(DropSize*622 - 111);
        } else if (DropSize>=5 && DropSize<=7){
            return (int)((5 - (DropSize-5))*622 - 111);
        } else {
            throw new RuntimeException("incorrect drop size");
        }
    }


}