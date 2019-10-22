package ru.kopylov.raindrops.persist;

import ru.kopylov.raindrops.model.InputDataSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InputDataSetDAO extends DAO {

    public InputDataSetDAO(DataSource repo) {
        super(repo);
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
        PreparedStatement statement = getSttm(query);
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

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }





}
