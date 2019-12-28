package ru.kopylov.raindrops;

import ru.kopylov.raindrops.model.InputDataSet;

public class Test {
    public static void main(String[] args) {
        InputDataSet ids = InputDataSet.getInstance();
        System.out.println(ids.getSpaceLenght());
        System.out.println(ids.getSpaceHeight());
        System.out.println(ids.getSpaceWidth());
        System.out.println();

        ids.setDropSize(0.5);
        System.out.println(ids.getSpaceLenght());
        System.out.println(ids.getSpaceHeight());
        System.out.println(ids.getSpaceWidth());
        System.out.println();
        System.out.println(ids.getDropFallingSpeed());

        ids.setDropSize(4.0);
        System.out.println(ids.getDropFallingSpeed());
        System.out.println("din "+ids.getDropsInLayer());

        ids.setDropSize(1.0);
        System.out.println(ids.getDropFallingSpeed());
        System.out.println("din "+ids.getDropsInLayer());



//        int distance = 200;
//        int humanSpeed = 3;
//        int rainSpeed = 4;
//
//        double ticTime = 1.0 / rainSpeed;
//        double humanDistancePerTic = ticTime*humanSpeed;
//        int passedDiscretDistance=1;
//        double passedContiniousDistance = 0;
//
//        while(passedDiscretDistance<distance){
//            passedContiniousDistance+=humanDistancePerTic;
//
//        }
//
//
//
//
//        double a = (double) 1/2;
//        System.out.println(a);
    }
}
