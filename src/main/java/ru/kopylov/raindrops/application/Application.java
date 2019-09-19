package ru.kopylov.raindrops.application;


import ru.kopylov.raindrops.model.Human;
import ru.kopylov.raindrops.model.InputDataSet;
import ru.kopylov.raindrops.model.RainSpace;


import org.apache.log4j.Logger;


public class Application {
    public static Logger logger = Logger.getLogger(Application.class);

    public void launch(){
        logger.debug("Start launch");
        long time = 0;
        RainSpace rainSpace = new RainSpace();
        Human human = new Human();
        InputDataSet ds = InputDataSet.getInstance();

        int speedDifference = ds.getDropFallingSpeed() / ds.getHumanSpeed();

        int distancePassed = 0;
//        Заполняем пространство дождем
        logger.debug("Start space filling by rain");
        for(int i=0;i<ds.getSpaceHeight();i++){
            rainSpace.updateTopLayer();
        }
//          Начинаем эксперимент
        int humanMovesCounter=0;
        logger.debug("Start experiment");
        while(distancePassed<= ds.getDistance()){
            time++;
            humanMovesCounter++;
            rainSpace.updateTopLayer();
            human.updateTop(rainSpace.getTopLayer());
            if(humanMovesCounter>=speedDifference){
                distancePassed++;
                humanMovesCounter=0;
                human.updateFront(rainSpace.getFrontVLayer(human.getPosition()), rainSpace.getTopLayerPointer());
                logger.trace(human.getCollectedDrops());
            }
//            эксперимент завершен, сбор данных



        }


    }

    public static void main(String[] args) {
        new Application().launch();
    }
}
