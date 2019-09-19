package ru.kopylov.raindrops.model;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Вертикальный слой (нормаль совпадает или противоположна направлению движения человека)
 */

public class VLayerIterator extends AbstractLayerIterator{

    private int width=0, height=0;
    private final int maxHeight, maxWidth;



    //    протяженность - постоянная, изменяются высота и ширина.
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
    public byte get(int height, int width) {
        if((width< maxWidth)&&(height< maxHeight)) {
            return space[layerNumber][height][width];
        } else {
            throw new IllegalArgumentException("Index of bound: height - "+height +" width - "+width);
        }
    }

    @Override
    public void forEach(UnaryOperator<Byte> unaryOperator) {
        for(int i=0; i<maxHeight; i++){
            for(int j=0; j<maxWidth; j++){
                space[layerNumber][i][j] = unaryOperator.apply(space[layerNumber][i][j]);
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

    @Override
    public void clean() {
//        empty implementation
    }

    @Override
    public void set(int height, int width, byte value) {
        if ((width < maxWidth) && (height < maxHeight)) {
            space[layerNumber][height][width] = value;
        } else {
            throw new IllegalArgumentException("Index of bound: height - " + height + " width - " + width);
        }
    }

    @Override
    public void addPair(int x, int y) {
//        empty implementation
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<maxHeight; i++){
            for(int j=0; j<maxWidth; j++){
                sb.append(space[layerNumber][i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public long spotSum(int hPosition, int hLower) {
        long result = 0;
        Circle circle = new Circle(maxHeight, hPosition);
        for (int i = 0; i < hLower; i++) {
            result+=wideArraySum(circle.previous());
        }
        return result;
    }

    private long wideArraySum(int positionInHeight) {
        long sum = 0;
        for (int i = 0; i < maxWidth; i++) {
            sum += space[layerNumber][positionInHeight][i];
        }
        return sum;
    }
}
