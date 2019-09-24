package ru.kopylov.raindrops.persist;

import org.junit.Test;
import ru.kopylov.raindrops.model.InputDataSet;

public class InputDataSetDAOTest {

    @Test
    public void save() throws ClassNotFoundException {
        InputDataSet dataSet = InputDataSet.getInstance();

        DataSource ds = new DataSource();
//        ds.create();

        InputDataSetDAO inputDataSetDAO = new InputDataSetDAO(ds);
        inputDataSetDAO.save();

        dataSet.setDropSize(dataSet.getDropSize()+0.1);
        inputDataSetDAO.save();

        dataSet.setDropSize(dataSet.getDropSize()+0.1);
        inputDataSetDAO.save();
        dataSet.setHumanSpeed(dataSet.getHumanSpeed()+10);
        inputDataSetDAO.save();
        dataSet.setRainIntensyty(dataSet.getRainIntensyty()+51);
        inputDataSetDAO.save();

        inputDataSetDAO.shutDown();
//        ds.shutDown();

    }

    @Test
    public void speed(){
        InputDataSet dataSet = InputDataSet.getInstance();
        double drop = 0.5;
        System.out.println("drop: " + drop + " speed: "+ dataSet.getDropFallingSpeed());

       while(drop < 7){
           dataSet.setDropSize(drop);
           System.out.println("drop: " + drop + " speed: "+ dataSet.getDropFallingSpeed());
           drop+=0.1;

       }

    }
}