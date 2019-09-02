package ru.kopylov.raindrops.util;

public class Helper {
    public static int DropFallingSpeed(double DropSize){
        if(DropSize>=0.5 && DropSize<5){
            return (int)(DropSize*622 - 111);
        } else if (DropSize>=5 && DropSize<=7){
            return (int)((5 - (DropSize-5))*622 - 111);
        } else {
            throw new RuntimeException("incorrect drop size");
        }
    }
}
