package ru.kopylov.raindrops.model;

import java.util.Iterator;

public class RainSpace implements Constants{
//    Пространство разбито на кубики в каждом из которых может быть ноль или одна капля дождя
//    Измерения: протяженность, высота, ширина
    private byte[][][] space = new byte[SpaceLenght][SpaceHeight][SpaceWidth];

// указатель на верхний слой
    private int topLayerPointer=0;
//    указатель на слой перед самым носом человека
    private int frontLayerPointer=0;

// обновление верхнего слоя
    public void update(){

    }

    private void incrementTopLayerPointer(){
        if(topLayerPointer<SpaceHeight-1){
            topLayerPointer++;
        } else {
            topLayerPointer=0;
        }
    }


}
