package ru.kopylov.raindrops.persist;

import ru.kopylov.raindrops.model.Human;
import ru.kopylov.raindrops.model.InputDataSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultsDAO extends DAO {


    public ResultsDAO(DataSource repo) {
        super(repo);
    }

     private String query = "INSERT INTO results (" +
            "iteration, " +
            "dataset_id," +
            "cur_distance," +
            "top_drops," +
            "front_drops)" +
            " values (?, ?, ?, ?, ?)";

    public void save(Human human, long iteration){
        PreparedStatement statement = getSttm(query);
        try {
            statement.setLong(1, iteration);
            statement.setLong(2, dataSet.getId());
            statement.setInt(3, human.getPosition());
            statement.setLong(4,human.getTopDrops());
            statement.setLong(5, human.getFrontDrops());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
