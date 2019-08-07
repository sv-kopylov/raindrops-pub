package ru.kopylov.raindrops.model;

import java.util.function.Consumer;

public abstract class AbstractLayerIterator {
//    протяженность, высота, ширина
    protected final byte[][][] space;
    protected final int layerNumber;

    public AbstractLayerIterator(byte[][][] space, int layerNumber) {
        if((!isLayerExists())||
                (layerNumber<0)) throw new IllegalArgumentException("Layer number is out of bounds dude");
        this.space = space;
        this.layerNumber = layerNumber;
    }

    protected abstract boolean isLayerExists();
//  Навигация по слою
    public abstract byte get(int x, int y);
// Применяет операцию ко всем элементам слоя
    public abstract void forEach(Consumer consumer);

    public abstract boolean hasNext();

    public abstract byte next();


    public long sum(){
        long result = 0;
        while(hasNext()){
            result +=next();
        }
        return result;
    }


}
