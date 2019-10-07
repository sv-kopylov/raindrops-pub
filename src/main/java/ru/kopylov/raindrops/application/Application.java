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

    public void launch(){
        logger.debug("Start launch");

        RainSpace rainSpace = new RainSpace();
        Human human = new Human();
        InputDataSet dataSet = InputDataSet.getInstance();

        DataSource dataSource = new DataSource();
        InputDataSetDAO inputDataSetDAO = new InputDataSetDAO(dataSource);
        ResultsDAO resultsDAO = new ResultsDAO(dataSource);

//        Заполняем пространство дождем
        logger.debug("Start space filling by rain");
        for(int i=0;i<dataSet.getSpaceHeight();i++){
            rainSpace.updateTopLayer();
        }

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
