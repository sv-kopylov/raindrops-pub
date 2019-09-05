package ru.kopylov.raindrops.model;

public class RainSpace {
    InputDataSet ds = InputDataSet.getInstance();

//    Пространство разбито на кубики в каждом из которых может быть ноль или одна капля дождя
//    Измерения: протяженность, высота, ширина
    private byte[][][] space = new byte[ds.getSpaceLenght()][ds.getSpaceHeight()][ds.getSpaceWidth()];

//    представление пространства в горизонтальных  слоях
    private HLayerIterator[] hLayers = new HLayerIterator[ds.getSpaceHeight()];

//    представление пространства в вертикальных  слоях
    private VLayerIterator[] vLayers = new VLayerIterator[ds.getSpaceLenght()];

// указатель на верхний слой
    private int topLayerPointer=0;

    private RandomSeeder randomSeeder = new RandomSeeder();

    public RainSpace() {
        for(int i = 0; i<ds.getSpaceHeight(); i++){
            hLayers[i] = new HLayerIterator(space, i);
        }
        for(int i=0; i<ds.getSpaceLenght(); i++){
            vLayers[i]=new VLayerIterator(space, i);
        }
    }

    // обновление верхнего слоя
    public void updateTopLayer(){
        incrementTopLayerPointer();
        getTopLayer().resetToZero();
        randomSeeder.reSeed(getTopLayer());

    }

    private void incrementTopLayerPointer(){
        if(topLayerPointer<ds.getSpaceHeight()-1){
            topLayerPointer++;
        } else {
            topLayerPointer=0;
        }
    }

    public HLayerIterator getTopLayer() {
        return hLayers[topLayerPointer];
    }

    public VLayerIterator getFrontVLayer(int pointer) {
        if(pointer>=0&&pointer<ds.getSpaceLenght()){
            return vLayers[pointer];
        } else {
            throw new IllegalArgumentException("vLayer index of bound");
        }

    }
}
