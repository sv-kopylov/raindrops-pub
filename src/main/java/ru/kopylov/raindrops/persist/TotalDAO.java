package ru.kopylov.raindrops.persist;

import ru.kopylov.raindrops.model.Total;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TotalDAO extends DAO {

    public TotalDAO(DataSource repo) {
        super(repo);
    }


    private String query = "INSERT INTO total (" +
            "dataset_id," +
            "drop_volume," +
            "total_volume," +

            "total_drops," +
            "total_top," +
            "total_front," +

            "delta_per_tic_total," +
            "delta_per_tic_top," +
            "delta_per_step," +

            "total_tics," +
            "delta_per_step_top," +
            "delta_per_step_front" +
            ")" +
            " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void save(Total total){
        PreparedStatement statement = getSttm(query);
        try {
            statement.setLong(1, total.getDatasetId());
            statement.setDouble(2, total.getDropVolume());
            statement.setDouble(3, total.getTotalVolume());

            statement.setLong(4, total.getTotalDrops());
            statement.setLong(5, total.getTotalTop());
            statement.setLong(6, total.getTotalFront());

            statement.setDouble(7, total.getDeltaPerTicTotal());
            statement.setDouble(8, total.getDeltaPerTicTop());
            statement.setDouble(9, total.getDeltaPerStep());

            statement.setInt(10, total.getTotalTicks());
            statement.setDouble(11, total.getDeltaPerStepTop());
            statement.setDouble(12, total.getDeltaPerStepFront());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
