package ru.kopylov.raindrops.application;


import ru.kopylov.raindrops.model.Human;
import ru.kopylov.raindrops.model.InputDataSet;
import ru.kopylov.raindrops.model.RainSpace;


import org.apache.log4j.Logger;
import ru.kopylov.raindrops.persist.DataSource;
import ru.kopylov.raindrops.persist.InputDataSetDAO;
import ru.kopylov.raindrops.persist.ResultsDAO;


public class Application {
    public static Logger logger = Logger.getLogger(Application.class);

    public static int LOW_HUMAN_SPEED  = 61; // прогулочный шаг
    public static int MIDDLE_HUMAN_SPEED  = 640; // 23 км/ч довольно энергичный бег
    public static int HI_HUMAN_SPEED  = 1219; // рекорд скорости

    public void launch(){

        RainSpace rainSpace = new RainSpace();
        Human human = new Human();
        InputDataSet dataSet = InputDataSet.getInstance();

        DataSource dataSource = new DataSource();

        InputDataSetDAO inputDataSetDAO = new InputDataSetDAO(dataSource);
        ResultsDAO resultsDAO = new ResultsDAO(dataSource);



/********************************   1 сет экспериментов    **************************************
 * 1)	Интенсивность:  При постоянном размере капли проводится несколько сетов с различной интенсивностью.
 * Цель – нужно ли менять стратегию при разной интенсивности.
 */

//        Заполняем пространство дождем
        fillSpaceByRain(rainSpace);

        dataSet.setHumanSpeed(LOW_HUMAN_SPEED);
        runDistance(rainSpace, human, inputDataSetDAO, resultsDAO);
        human.reset();



//        начало эксперимента
        dataSet.setHumanSpeed(70);
        runDistance(rainSpace, human, inputDataSetDAO, resultsDAO);

        human.reset();

        dataSet.setHumanSpeed(1200);
        runDistance(rainSpace, human, inputDataSetDAO, resultsDAO);

        inputDataSetDAO.shutDown();
        resultsDAO.shutDown();
        dataSource.closeConnection();

    }

    void fillSpaceByRain(RainSpace rainSpace){
        //        Заполняем пространство дождем
        logger.debug("Start space filling by rain");
        for(int i=0;i<InputDataSet.getInstance().getSpaceHeight();i++){
            rainSpace.updateTopLayer();
        }
    }

    void runDistance(RainSpace rainSpace,  Human human, InputDataSetDAO inputDataSetDAO, ResultsDAO resultsDAO){
        inputDataSetDAO.save();
        long time = 0;
        InputDataSet ds = InputDataSet.getInstance();
        int speedDifference = ds.getDropFallingSpeed() / ds.getHumanSpeed();
        int distancePassed = 0;

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
            resultsDAO.save(human, time);


        }
    }

    public static void main(String[] args) {
        new Application().launch();
    }
}
