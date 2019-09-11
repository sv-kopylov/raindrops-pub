package ru.kopylov.raindrops.model;

import org.apache.log4j.Logger;

import java.util.Random;

public class RandomSeeder  {
    private int maxAttempts = 0;

//    совпадений нет, но при большом значении плотности дождя эфективнее будет простой перебор
    public void reSeed(HLayerIterator iter){
        maxAttempts = iter.getMaxLenght()*iter.getMaxWidth();
        Random rnd = new Random(System.currentTimeMillis());
        double drops = InputDataSet.getInstance().getDropsInLayer();
        int constDrops = (int) drops;
        double probabilty = drops - constDrops;
        int singleDrop = rnd.nextDouble()<probabilty?1:0;
        int width;
        int lenght;

        for(int i=0; i<constDrops+singleDrop; i++){
            generateAndSet(rnd, iter, 0);
        }
    }

    private boolean set(HLayerIterator iter, int lenght, int width){
        if (iter.get(lenght, width)==0){
            iter.set(lenght, width, (byte)1);
            iter.addPair(lenght, width);
            return true;
        } else {
            return false;
        }
    }

    private boolean generateAndSet(Random rnd, HLayerIterator iter, int attmpts){
        if(attmpts<maxAttempts){
            int width = rnd.nextInt(iter.getMaxWidth());
            int lenght = rnd.nextInt(iter.getMaxLenght());
            if(!set(iter, lenght, width)){
                attmpts++;
                generateAndSet(rnd, iter, attmpts);
            }
            return true;
        }else {
            return false;
        }
    }

}
