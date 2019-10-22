package ru.kopylov.raindrops.persist;

import ru.kopylov.raindrops.model.InputDataSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DAO {
    protected DataSource repo;
    private PreparedStatement sttm;
    protected InputDataSet dataSet = InputDataSet.getInstance();

    public DAO(DataSource repo) {
        this.repo = repo;
    }

    protected PreparedStatement getSttm(String query){
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

    public void shutDown(){
        try {
            sttm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
