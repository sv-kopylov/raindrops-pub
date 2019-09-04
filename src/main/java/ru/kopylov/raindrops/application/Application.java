package ru.kopylov.raindrops.application;


import ru.kopylov.raindrops.model.Human;
import ru.kopylov.raindrops.model.InputDataSet;
import ru.kopylov.raindrops.model.RainSpace;


import org.apache.log4j.Logger;


public class Application {
    public static Logger logger = Logger.getLogger(Application.class);

    public void launch(){
        long time = 0;
        RainSpace rainSpace = new RainSpace();
        Human human = new Human();
        InputDataSet ds = InputDataSet.getInstance();

        int speedDifference = ds.getDropFallingSpeed() / ds.getHumanSpeed();

        int distancePassed = 0;
//        Заполняем пространство дождем
        for(int i=0;i<ds.getSpaceHeight();i++){
            rainSpace.updateTopLayer();
        }
//          Начинаем эксперимент
        int innerCounter=0;
        while(distancePassed<= ds.getDistance()){
            time++;
            innerCounter++;
            rainSpace.updateTopLayer();
            human.updateTop(rainSpace.getTopLayer());
            if(innerCounter>=speedDifference){
                distancePassed++;
                innerCounter=0;
                human.updateFront(rainSpace.getFrontVLayer());
                logger.trace(human.getCollectedWater());
            }
//            эксперимент завершен, сбор данных



        }






    }
}
