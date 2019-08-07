package ru.kopylov.raindrops.model;

import java.util.function.Consumer;

/**
 * Вертикальный слой (нормаль совпадает или противоположна направлению движения человека)
 */

public class VLayerIterator extends AbstractLayerIterator{

    private int width=0, height=0;
    private final int maxHeight, maxWidth;



    //    протяженность, высота, ширина.
    public VLayerIterator(byte[][][] space, int layerNumber) {
        super(space, layerNumber);
        maxHeight = space[0].length;
        maxWidth = space[0][0].length;

    }
//    в вертикальном слое постоянное значение протяженности
    @Override
    protected boolean isLayerExists() {
        return layerNumber<space.length;
    }

    @Override
    public byte get(int width, int height) {
        if((width< maxWidth)&&(height< maxHeight)) {
            return space[layerNumber][height][width];
        } else {
            throw new IllegalArgumentException("Index of bound: height - "+height +" width - "+width);
        }
    }

    @Override
    public void forEach(Consumer consumer) {
        for(int i=0; i<maxHeight; i++){
            for(int j=0; j<maxWidth; j++){
                consumer.accept(space[layerNumber][height][width]);
            }
        }
    }

    private boolean hasNext = true;

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public byte next() {
        if(!hasNext()) throw new RuntimeException("Now more bytes");
        byte result = space[layerNumber][height][width];
        if(width<maxWidth-1){
            width++;
        } else  if(height<maxHeight-1) {
            width=0;
            height++;
        } else {
            hasNext=false;
        }

        return result;

    }

    @Override
    public void reset() {
        height=0;
        width=0;
        hasNext=true;
    }
}
