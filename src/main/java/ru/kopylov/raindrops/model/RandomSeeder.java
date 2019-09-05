package ru.kopylov.raindrops.model;

import java.util.Random;

public class RandomSeeder  {

    InputDataSet ds = InputDataSet.getInstance();

//    могут быть совпадения, какова их вероятность ?
    public void reSeed(HLayerIterator iter){

        Random rnd = new Random(System.currentTimeMillis());
        double drops = ds.getDropsInLayer();
        int constDrops = (int) drops;
        double probabilty = drops - constDrops;
        int singleDrop = rnd.nextDouble()<probabilty?1:0;
        int width;
        int lenght;
        for(int i=0; i<constDrops+singleDrop; i++){
            width = rnd.nextInt(ds.getSpaceWidth());
            lenght = rnd.nextInt(ds.getSpaceLenght());
            iter.set(lenght, width, (byte) 1);
            iter.addPair(lenght, width);
        }
    }

}
