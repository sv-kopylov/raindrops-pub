package ru.kopylov.raindrops.application;


import ru.kopylov.raindrops.model.Human;
import ru.kopylov.raindrops.model.InputDataSet;
import ru.kopylov.raindrops.model.RainSpace;


import org.apache.log4j.Logger;
import ru.kopylov.raindrops.persist.DataSource;
import ru.kopylov.raindrops.persist.InputDataSetDAO;
import ru.kopylov.raindrops.persist.ResultsDAO;


public class Experimentarium {
    public static Logger logger = Logger.getLogger(Experimentarium.class);

    private int[] humanSpeeds = {
            61,  // прогулочный шаг
            640, // 23 км/ч очень энергичный бег
            1219 // рекорд скорости
    };

    private double intensities[] = {
//            1.0, // моросящий дождь
            50.0 // ливень
//            100.0 // катастрофа

    };

    private double dropSizes[] = {
            0.6, // очень мелкий, моросящий
            1.4, // затяжной
            5.0 // ливень, каких мало
//            7.0 // чудо природы

    };



    private RainSpace rainSpace;
    private Human human;
    private InputDataSet dataSet;
    private DataSource dataSource;
    private InputDataSetDAO inputDataSetDAO;
    private ResultsDAO resultsDAO;

    /**
     * Top level complette all experiments
     *
     */
    public void launch(){
        init();
        completeDifferentIntensityesAndDropsizes();
        end();

    }

    /**
     * Prepairing envinronment
     */
    public void init(){
        rainSpace = new RainSpace();
        human = new Human();
        dataSet = InputDataSet.getInstance();
        dataSource = new DataSource();
        inputDataSetDAO = new InputDataSetDAO(dataSource);
        resultsDAO = new ResultsDAO(dataSource);
    }

    /**
     * Realizing resources
     */
    public void end(){
        inputDataSetDAO.shutDown();
        resultsDAO.shutDown();
        dataSource.closeConnection();
    }

    /********************************   вся серия экспериментов    **************************************
     * 1)	Интенсивность:  При постоянном размере капли проводится несколько сетов с различной интенсивностью.
     * Цель – нужно ли менять стратегию при разной интенсивности.
     */
    private void completeDifferentIntensityesAndDropsizes(){

        for(double dropSize: dropSizes) {
            logger.debug("START SET WITN DROPSIZE: " + dropSize);
            dataSet.setDropSize(dropSize);
            for (double inten : intensities) {
                long start = System.currentTimeMillis();
                logger.debug("START SUBSET WITN INTENSITY: " + inten+" ,dropsize"+ dropSize);
                dataSet.setRainIntensyty(inten); // моросящий дождь
                fillSpaceByRain();
                completeDifferentSpeedRuns();
                logger.debug("elapsed time: "+ (System.currentTimeMillis()-start));
                logger.debug(dataSet.toString());
            }
        }
    }

     /**
     * Заполняем пространство дождем
     */
    void fillSpaceByRain(){

        logger.debug("Start space filling by rain");
        for(int i=0;i<InputDataSet.getInstance().getSpaceHeight();i++){
            rainSpace.updateTopLayer();
        }
    }


    /**
     * Серия забегов на разных скоростях, но с константными параметрами среды
     */

    private  void completeDifferentSpeedRuns() {
        for(int i=0; i<humanSpeeds.length; i++){
            dataSet.setHumanSpeed(humanSpeeds[i]);
            human.reset();
            runDistance();
        }
    }

    /**
     * Прохождение дистанции
     */
    private void runDistance(){
        inputDataSetDAO.save();
        double ticTime = 1.0 / dataSet.getDropFallingSpeed();
        double movePerTic = ticTime*dataSet.getHumanSpeed();
        double distancePassed = 0.0;
        double milestones = 1.0;
        long time = 0;
        logger.debug("Start running, speed: " + dataSet.getHumanSpeed());

        while(milestones<= dataSet.getDistance()){
            time++;
            distancePassed+=movePerTic;
            rainSpace.updateTopLayer();
            human.updateTop(rainSpace.getTopLayer());
            if(distancePassed>=milestones){
                int dif = (int)(distancePassed - milestones)+1;
                for(int i=0; i<dif; i++){
                    human.updateFront(rainSpace.getFrontVLayer(human.getPosition()), rainSpace.getTopLayerPointer());
                    milestones+=dif;
                }
            }
            resultsDAO.save(human, time);
        }
        logger.debug("Running completed");
    }



    private void completeDifferentIntensityesAndDropsizes1(){
        logger.debug("START SET");
        dataSet.setDropSize(dropSizes[3]);
        dataSet.setRainIntensyty(intensities[0]);
        long start = System.currentTimeMillis();
        fillSpaceByRain();
        completeDifferentSpeedRuns();
        logger.debug("elapsed time: "+ (System.currentTimeMillis()-start));
        logger.debug(dataSet.toString());

    }

    public static void main(String[] args) {
        new Experimentarium().launch();
    }
}
