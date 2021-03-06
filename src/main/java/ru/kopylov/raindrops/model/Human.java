package ru.kopylov.raindrops.model;

public class Human {
    InputDataSet ds = InputDataSet.getInstance();
//    позиция на протяженности(длине пространства), указывает на слой перед самым носом человека
    private  Circle  position =  new Circle(ds.getSpaceLenght(), 0);

//    количество капель, собранных человеком сверху
    private long topDrops = 0;

//    количество капель, собранных человеком спереди
    private long frontDrops = 0;

//    вычисляется количество капель в пятне слоя аккурат над человеком
    public void updateTop(HLayerIterator iter){
        topDrops+=iter.spotSum(position.current(), ds.getHumanDepth());
    }

//    вычисляется число капель в пятне слоя с которым человек сталкивается двигаясь вперед
    public void updateFront(VLayerIterator iter, int topLayerPointer){
        frontDrops+=iter.spotSum(topLayerPointer, ds.getHumanHeight());
        position.next();
    }
    public double getCollectedDrops(){
        return (frontDrops + topDrops);
    }

    public int getPosition(){
        return position.current();
    }

    public long getTopDrops() {
        return topDrops;
    }

    public long getFrontDrops() {
        return frontDrops;
    }

    public void reset(){
        topDrops=0;
        frontDrops=0;
        position =  new Circle(ds.getSpaceLenght(), 0);

    }
}
