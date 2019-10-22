package ru.kopylov.raindrops.persist;

import org.junit.Test;
import ru.kopylov.raindrops.model.Total;

import static org.junit.Assert.*;

public class TotalDAOTest {

    @Test
    public void save() {
        DataSource ds = new DataSource();
        TotalDAO totalDAO = new TotalDAO(ds);
        Total total = new Total();

        total.setDropVolume(0.2);
        total.setDatasetId(1570726691025l);

        total.setTotalTop(100);
        total.setTotalFront(3000);
        total.setTotalDrops(3100);

        total.setTotalTicks(5000);

        total.setTotalVolume(total.getDropVolume()*3100);

        total.setDeltaPerStep(201);
        total.setDeltaPerStepTop(202);
        total.setDeltaPerStepFront(203);

        total.setDeltaPerTicTop(21);
        total.setDeltaPerTicTotal(22);


        totalDAO.save(total);
        totalDAO.shutDown();
        ds.closeConnection();
    }
}