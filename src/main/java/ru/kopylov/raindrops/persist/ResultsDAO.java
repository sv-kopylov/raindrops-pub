package ru.kopylov.raindrops.persist;

import ru.kopylov.raindrops.model.Human;
import ru.kopylov.raindrops.model.InputDataSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultsDAO {
    private DataSource repo;
    private PreparedStatement sttm;
    InputDataSet dataSet = InputDataSet.getInstance();

    public ResultsDAO(DataSource repo) {
        this.repo = repo;
    }

    private PreparedStatement getSttm(){
        try {
            if(sttm==null){
                sttm = repo.getConnection().prepareStatement(query);
            }  else if(sttm.isClosed()){
                sttm = repo.getConnection().prepareStatement(query);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return sttm;
    }


    private String query = "INSERT INTO results (" +
            "iteration, " +
            "dataset_id," +
            "cur_distance," +
            "top_drops," +
            "front_drops)" +
            " values (?, ?, ?, ?, ?)";

    public void save(Human human, long iteration){
        PreparedStatement statement = getSttm();
        try {
            statement.setLong(1, iteration);
            statement.setLong(2, dataSet.getId());
            statement.setInt(3, human.getPosition());
            statement.setLong(4,human.getTopDrops());
            statement.setLong(5, human.getFrontDrops());

            sttm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void shutDown(){
        try {
            sttm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
