package ru.kopylov.raindrops.persist;

import ru.kopylov.raindrops.model.InputDataSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InputDataSetDAO {
    private DataSource repo;
    private PreparedStatement sttm;
    InputDataSet dataSet = InputDataSet.getInstance();

    public InputDataSetDAO(DataSource repo) {
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

    private String query = "INSERT INTO dataset (" +
            "id, " +
            "dropsize," +
            "distance," +
            "drop_falling_speed," +
            "human_speed," +
            "human_height," +
            "human_width," +
            "human_depth," +
            "space_width," +
            "space_height," +
            "space_lenght," +
            "rain_intensyty," +
            "probability_drop_in_cell," +
            "drops_in_layer ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";



    public void save(){
        PreparedStatement statement = getSttm();
        try {
            statement.setLong(1, dataSet.getId());
            statement.setDouble(2, dataSet.getDropSize());
            statement.setInt(3, dataSet.getDistance());
            statement.setInt(4, dataSet.getDropFallingSpeed());
            statement.setInt(5, dataSet.getHumanSpeed());
            statement.setInt(6, dataSet.getHumanHeight());
            statement.setInt(7, dataSet.getHumanWidth());
            statement.setInt(8, dataSet.getHumanDepth());
            statement.setInt(9, dataSet.getSpaceWidth());
            statement.setInt(10, dataSet.getSpaceHeight());
            statement.setInt(11, dataSet.getSpaceLenght());
            statement.setDouble(12, dataSet.getRainIntensyty());
            statement.setDouble(13, dataSet.getProbabilityDropInCell());
            statement.setDouble(14, dataSet.getDropsInLayer());

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
