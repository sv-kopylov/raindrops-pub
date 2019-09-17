package ru.kopylov.raindrops.model;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class AbstractLayerIterator {
//    протяженность, высота, ширина
    protected final byte[][][] space;
    protected final int layerNumber;

    public AbstractLayerIterator(byte[][][] space, int layerNumber) {
        this.space = space;
        this.layerNumber = layerNumber;
        if((!isLayerExists())||
                (layerNumber<0)) throw new IllegalArgumentException("Layer number is out of bounds");
    }

    protected abstract boolean isLayerExists();
//  Навигация по слою
    public abstract byte get(int x, int y);
// Применяет операцию ко всем элементам слоя
    public abstract void forEach(UnaryOperator<Byte> consumer);

    public abstract boolean hasNext();

    public abstract byte next();

// сумма всех элементов
    public long sum(){
        long result = 0;
        while(hasNext()){
            result +=next();
        }
        reset();
        return result;
    }


//    сброс итератора к началу
    public abstract void reset();


    public abstract void clean();
    public abstract void set(int x, int y, byte value);
    public abstract void addPair(int x, int y);

//    Вычисление суммы части слоя за позицией
    public abstract long spotSum(int зosition, int behind);

// imutable
    public static class Pair{
        private int x;
        private int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

}
