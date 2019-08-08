package ru.kopylov.raindrops.model;

import java.util.function.Consumer;

public class HLayerIterator extends AbstractLayerIterator {
    public HLayerIterator(byte[][][] space, int layerNumber) {
        super(space, layerNumber);
    }

    @Override
    protected boolean isLayerExists() {
        return false;
    }

    @Override
    public byte get(int x, int y) {
        return 0;
    }

    @Override
    public void forEach(Consumer consumer) {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public byte next() {
        return 0;
    }

    @Override
    public void reset() {

    }
}
