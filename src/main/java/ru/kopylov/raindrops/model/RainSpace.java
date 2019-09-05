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

//    все пространство инкапсулировано но всякие его может использовать через интерфейс вертикального слоя???
    private  VLayerIterator frontVLayer = new VLayerIterator(space, 0);

//    указатель на слой перед самым носом человека (нужен здесь или в сщности человек?)
    private int frontLayerPointer=0;

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
        randomSeeder.reSeed(getTopLayer());
        incrementTopLayerPointer();
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

    public VLayerIterator getFrontVLayer() {
        return frontVLayer;
    }
}
