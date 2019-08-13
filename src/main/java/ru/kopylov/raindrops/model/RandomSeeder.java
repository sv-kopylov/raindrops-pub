package ru.kopylov.raindrops.model;

import java.util.Random;

public class RandomSeeder implements Constants {

    public void reSeed(AbstractLayerIterator iter){
        Random rnd = new Random(System.currentTimeMillis());
//        мало ли что
        iter.reset();
        iter.forEach((x)-> (byte)0);
        iter.reset();
        iter.forEach((x)->(byte)(rnd.nextDouble()<ProbabilityDropInCell?1:0));
//        подчищаем за собой
        iter.reset();

    }

}
