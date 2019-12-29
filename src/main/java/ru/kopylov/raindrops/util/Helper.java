package ru.kopylov.raindrops.util;

import ru.kopylov.raindrops.application.RainDensity;

public class Helper {
    public static int DropFallingSpeed(double DropSize){
       return 2999;
    }

    public static int SpaceLenght(int spaceHeight, int dropFallingSpeed, int humanSpeed, int humanDepth) {
        double hLayrsPerMove = (double) dropFallingSpeed/humanSpeed; // количество заполненных слоев на одно горизонтальное смещение
        double movesToCompletteSpace = (double) spaceHeight / hLayrsPerMove ; // количество горизонтальных смещений, за которое все пространство обновляется
        int result = (int)(movesToCompletteSpace+1+humanDepth);
        return result;
    }

    public static double ProbabilityDropInCell() {
        // should be implementation, or not
        return 0.0;
    }

    public static double DropsInLayer(double rainIntensyty, double dropSize) {
        return new RainDensity(rainIntensyty, dropSize).getDropsIn1m2();
    }
}
