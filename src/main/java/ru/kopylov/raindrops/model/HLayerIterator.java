package ru.kopylov.raindrops.model;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class HLayerIterator extends AbstractLayerIterator {
    private int width = 0, lenght = 0;
    private final int maxLenght, maxWidth;

    private ArrayList<Pair> cellsWithDrops = new ArrayList<>();

    //    Измерения пространства: протяженность, высота, ширина
    //    высота - постоянная, изменяются протяженность и ширина.
    public HLayerIterator(byte[][][] space, int layerNumber) {
        super(space, layerNumber);
        maxLenght = space.length;
        maxWidth = space[0][0].length;
    }

    //    в горизонтальном слое постоянное значение высоты
    @Override
    protected boolean isLayerExists() {
        return layerNumber < space[0].length;
    }

    @Override
    public byte get(int lenght, int width) {
        if ((width < maxWidth) && (lenght < maxLenght)) {
            return space[lenght][layerNumber][width];
        } else {
            throw new IllegalArgumentException("Index of bound: lenght - " + lenght + " width - " + width);
        }
    }

    @Override
    public void set(int lenght, int width, byte value) {
        if ((width < maxWidth) && (lenght < maxLenght)) {
            space[lenght][layerNumber][width] = value;
        } else {
            throw new IllegalArgumentException("Index of bound: lenght - " + lenght + " width - " + width);
        }
    }

    @Override
    public void forEach(UnaryOperator<Byte> unaryOperator) {
        for (int i = 0; i < maxLenght; i++) {
            for (int j = 0; j < maxWidth; j++) {
                space[i][layerNumber][j] = unaryOperator.apply(space[i][layerNumber][j]);
            }
        }
    }

    boolean hasNext = true;

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public byte next() {
        if (!hasNext()) throw new RuntimeException("Now more bytes");
        byte result = space[lenght][layerNumber][width];
        if (width < maxWidth - 1) {
            width++;
        } else if (lenght < maxLenght - 1) {
            width = 0;
            lenght++;
        } else {
            hasNext = false;
        }

        return result;
    }

    @Override
    public void reset() {
        lenght = 0;
        width = 0;
        hasNext = true;
    }

    @Override
    public void clean() {
        for (Pair p : cellsWithDrops) {
            set(p.getX(), p.getY(), (byte) 0);
        }
        cellsWithDrops.clear();
    }

    @Override
    public void addPair(int lenght, int width) {
        cellsWithDrops.add(new Pair(lenght, width));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxLenght; i++) {
            for (int j = 0; j < maxWidth; j++) {
                sb.append(space[i][layerNumber][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getMaxLenght() {
        return maxLenght;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    @Override
    public long spotSum(int lPosition, int lbehind) {
        long result = 0;
        Circle circle = new Circle(maxLenght, lPosition);
        for (int i = 0; i < lbehind; i++) {
            result+=wideArraySum(circle.previous());
        }
        return result;
    }

    private long wideArraySum(int positionInLenght) {
        long sum = 0;
        for (int i = 0; i < maxWidth; i++) {
            sum += space[positionInLenght][layerNumber][i];
        }
        return sum;
    }


}
