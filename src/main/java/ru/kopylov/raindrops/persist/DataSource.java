package ru.kopylov.raindrops.persist;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DataSource {
    public static Logger logger = Logger.getLogger(DataSource.class);
    private String dbname = "raindrops2";
    private Path dbDir = Paths.get("results\\db");
//    private String url = "jdbc:hsqldb:file:" + dbDir.toAbsolutePath().toString() + "/" + dbname;
    private String url = "jdbc:hsqldb:hsql://localhost/" + dbname;
    private Connection connection;


    public Connection getConnection(){
        try {
        if(connection==null){
                connection=DriverManager.getConnection(url, "SA", "");
        } else if(connection.isClosed()){
                connection=DriverManager.getConnection(url, "SA", "");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return connection;

    }
    public void shutDown(){
        Statement sttm = null;
        try {
            if (connection != null) {
                if (!connection.isClosed())
                    sttm = connection.createStatement();
                    sttm.execute("SHUTDOWN");
                    sttm.close();
                    connection.close();
            }
        } catch (SQLException e) {
            logger.error("I can't close connection " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    public void closeConnection(){
        try {
            if (connection != null) {
                if (!connection.isClosed())
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("I can't close connection " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DataSource ds = new DataSource();
        System.out.println(ds.dbDir.toAbsolutePath().toString());
        Files.createDirectories(ds.dbDir);
        ds.create();
    }
@Deprecated
    public void create() throws ClassNotFoundException {
        String createDataSetTableQuery =
                "CREATE CACHED TABLE dataset " +
                        "(" +
                        "id BIGINT PRIMARY KEY," +
                        "dropsize DOUBLE," +
                        "distance INTEGER," +
                        "drop_falling_speed INTEGER," +
                        "human_speed INTEGER," +
                        "human_height INTEGER," +
                        "human_width INTEGER," +
                        "human_depth INTEGER," +
                        "space_width INTEGER," +
                        "space_height INTEGER," +
                        "space_lenght INTEGER," +
                        "rain_intensyty DOUBLE," +
                        "probability_drop_in_cell DOUBLE," +
                        "drops_in_layer DOUBLE" +
                        ")";
        String createResultsTableQuery =
                "CREATE CACHED TABLE results " +
                        "(" +
                        "iteration BIGINT PRIMARY KEY,"+
                        "dataset_id BIGINT," +
                        "top_drops BIGINT," +
                        "front_drops BIGINT," +
                        "FOREIGN KEY(dataset_id) REFERENCES dataset(id)" +

                        ")";   try {
            Files.createDirectories(dbDir);
        } catch (IOException e) {
            logger.error("Cannot create directory for db: " + e.getMessage());
        }
        try (Connection con = DriverManager.getConnection(url, "SA", "");
            Statement sttm = con.createStatement();) {
            sttm.executeUpdate(createDataSetTableQuery);
            sttm.executeUpdate(createResultsTableQuery);
            String shutdoun = "SHUTDOWN";
            sttm.execute(shutdoun);
            sttm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
