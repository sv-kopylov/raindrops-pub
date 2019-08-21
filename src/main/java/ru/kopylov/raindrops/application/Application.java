package ru.kopylov.raindrops.application;


import ru.kopylov.raindrops.model.Constants;
import ru.kopylov.raindrops.model.Human;
import ru.kopylov.raindrops.model.RainSpace;


import org.apache.log4j.Logger;


public class Application implements Constants {
    public static Logger logger = Logger.getLogger(Application.class);

    /**    cкорость падения капель производная величина, поэтому вынесена из Constants
     Скорость падения дождевых капель диаметром 0,5 мм на уровне моря и без ветра составляет от 2(200) до 6,6(660) метров в секунду,
     в то время как капли диаметром 5 мм имеют скорость от 9 (900) до 30 (3000) метров в секунду.
     Все измерения в см/сек
     min = 200 sm/sec
     max = 3000 sm/sec

     пусть будет линейная зависимость от 0.5 до 5
     после 5 скорость опять снижается (допустим с той же интенсивностью)

     */
    public static int DropFallingSpeed;
    static {
        if(DropSize<0.5){
            throw new RuntimeException("incorrect drop size");
        } else if(DropSize>=0.5 && DropSize<5)
            DropFallingSpeed = (int)(DropSize*622 - 111);
    }

    public void launch(){
        long time = 0;
        RainSpace rainSpace = new RainSpace();
        Human human = new Human();

        int speedDifference = DropFallingSpeed / HumanSpeed;

        int distancePassed = 0;
//        Заполняем пространство дождем
        for(int i=0;i<SpaceHeight();i++){
            rainSpace.updateTopLayer();
        }
//          Начинаем эксперимент
        int innerCounter=0;
        while(distancePassed<= Distance){
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
