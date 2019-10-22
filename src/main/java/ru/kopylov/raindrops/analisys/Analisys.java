package ru.kopylov.raindrops.analisys;

/*
 * Показатели:
 забег
 - объем полученной воды;
 - количество капель всего;
 - количество (объем) капель сверху всего;
 - количество (объем) капель спереди всего;
 - средний прирост за один тик;
 - средний прирост за один тик (не включая фронтальные);
 - средний прирос за одно горизонтальное смещение (всего / только горизонтальные/ только фронтальные)
 сет
 - разница полученного объема на разных скоростях.
 - другие разницы

 */

import ru.kopylov.raindrops.model.Total;
import ru.kopylov.raindrops.persist.DataSource;
import ru.kopylov.raindrops.persist.TotalDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Analisys {
    DataSource ds = new DataSource();
    TotalDAO totalDAO = new TotalDAO(ds);

    /**
     * Заполняет поля датасет и размер капли в литрах
     * @return
     */
    private  ArrayList<Total> predefineTotals(){
        String query = "select id, dropsize from dataset";
        ArrayList<Total> totals = new ArrayList<>();
        Total next;
        long nextDatasetId;
        double dropsize;
        try(Statement statement = ds.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()){
                next = new Total();
                next.setDatasetId(resultSet.getLong("id"));
                next.setDropVolume(dropsize(resultSet.getDouble("dropsize")));
                totals.add(next);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return totals;
    }
    public void completeTotal(Total predefinedTotal){
        String whereClause = " where dataset_id="+predefinedTotal.getDatasetId();
        String totalTicksQuery = "select count(id) from results"+whereClause;
        String totalTopFrontDropsQuery = "select max(top_drops), max(front_drops) from results"+whereClause;
        String totalStepsQuery = "select distance from dataset where id =" +predefinedTotal.getDatasetId();

        int totalTics;
        int totalSteps;
        long totalTopDrops;
        long totalFrontDrops;
        long totalDrops;

        ResultSet resultSet;
        try(Statement statement = ds.getConnection().createStatement()){
            resultSet = statement.executeQuery(totalTicksQuery);
            resultSet.next();
            totalTics = resultSet.getInt(1);

            resultSet = statement.executeQuery(totalStepsQuery);
            resultSet.next();
            totalSteps = resultSet.getInt(1);

            resultSet = statement.executeQuery(totalTopFrontDropsQuery);
            resultSet.next();
            totalTopDrops = resultSet.getLong(1);
            totalFrontDrops = resultSet.getLong(2);
            totalDrops = totalTopDrops+totalFrontDrops;

            predefinedTotal.setTotalTop(totalTopDrops);
            predefinedTotal.setTotalFront(totalFrontDrops);
            predefinedTotal.setTotalDrops(totalDrops);

            predefinedTotal.setTotalTicks(totalTics);

            predefinedTotal.setTotalVolume(predefinedTotal.getDropVolume()*totalDrops);

            predefinedTotal.setDeltaPerStep((double) totalDrops/(double)totalSteps);
            predefinedTotal.setDeltaPerStepTop((double)totalTopDrops/(double)totalSteps);
            predefinedTotal.setDeltaPerStepFront((double)totalFrontDrops/(double)totalSteps);

            predefinedTotal.setDeltaPerTicTop((double)totalTopDrops/(double)totalTics);
            predefinedTotal.setDeltaPerTicTotal((double)totalDrops/(double)totalTics);

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    private double dropsize(double dropDiameter){
       double r = (dropDiameter/2.0)*(1.0/100.0);// dm
       double dropVolume = (4.0/3.0)*Math.PI*r*r*r; // liters
        return dropVolume;
    }

    public static void main(String[] args) {
        Analisys a = new Analisys();
        ArrayList<Total> list = a.predefineTotals();
        TotalDAO totalDAO = new TotalDAO(a.ds);
        for(Total total: list){

            a.completeTotal(total);
            totalDAO.save(total);
        }
        a.ds.closeConnection();
    }

}
