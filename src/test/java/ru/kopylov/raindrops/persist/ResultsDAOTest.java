package ru.kopylov.raindrops.persist;

import org.junit.Test;
import ru.kopylov.raindrops.model.Human;
import ru.kopylov.raindrops.model.InputDataSet;

import static org.junit.Assert.*;

public class ResultsDAOTest {

    @Test
    public void save() {
        InputDataSet dataSet = InputDataSet.getInstance();
        DataSource dataSource = new DataSource();

        InputDataSetDAO inputDataSetDAO = new InputDataSetDAO(dataSource);
        inputDataSetDAO.save();

        Human human = new Human();
        ResultsDAO resultsDAO = new ResultsDAO(dataSource);

        resultsDAO.save(human,1);
        resultsDAO.save(human,2);


        dataSet.setDropSize(dataSet.getDropSize()+0.1);
        inputDataSetDAO.save();
        resultsDAO.save(human,3);
        resultsDAO.save(human,4);



        inputDataSetDAO.shutDown();
    }
}