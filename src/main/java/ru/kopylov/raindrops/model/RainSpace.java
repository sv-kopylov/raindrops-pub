package ru.kopylov.raindrops.model;

public class RainSpace implements Constants{
//    Пространство разбито на кубики в каждом из которых может быть ноль или одна капля дождя
//    Измерения: протяженность, высота, ширина
    private byte[][][] space = new byte[SpaceLenght][SpaceHeight][SpaceWidth];

// указатель на верхний слой
    private int topLayerPointer=0;
//    сам верхний слой
    private HLayerIterator topLayer= new HLayerIterator(space,topLayerPointer);

//    указатель на слой перед самым носом человека (нужен здесь или в сщности человек?)
    private int frontLayerPointer=0;

    private RandomSeeder randomSeeder = new RandomSeeder();

// обновление верхнего слоя
    public void updateTopLayer(){
        randomSeeder.reSeed(topLayer);
        incrementTopLayerPointer();
        topLayer.setLayerNumber(topLayerPointer);
    }

    private void incrementTopLayerPointer(){
        if(topLayerPointer<SpaceHeight-1){
            topLayerPointer++;
        } else {
            topLayerPointer=0;
        }
    }
}
