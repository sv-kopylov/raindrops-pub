package ru.kopylov.raindrops.application;

import ru.kopylov.raindrops.util.Helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Вычисление количества капель в слое в зависимости от объема капли и интенсивности (исходя из предположения что они не связанны)
 */
public class RainDensity {

    double intensity; //    l/sec
    double dropDiameter; //    mm

    double r; //                dm
    double dropVolume;//         l
    double dropSpeed;//         cm/sec
    double timePerLayer;//      sec
    double waterPer1m2;//       l
    double dropsIn1m2;//        items

    public RainDensity(double intensity, double dropDiameter) {
        this.intensity = intensity;
        this.dropDiameter = dropDiameter;

        r = (dropDiameter/2.0)*(1.0/100.0);// dm
        dropVolume = (4.0/3.0)*Math.PI*r*r*r; // liters
        dropSpeed = Helper.DropFallingSpeed(dropDiameter); //1775 см/сек
        timePerLayer = 1.0/dropSpeed;
        waterPer1m2 = intensity*timePerLayer;
        dropsIn1m2 = waterPer1m2/dropVolume;
        if(dropsIn1m2>10000){
            throw new RuntimeException("Too mutch drops in 1 m^2");
        }
    }


    public double getDropsIn1m2() {
        return dropsIn1m2;
    }

    public static void main(String[] args) {
        waterPerVolume();
    }


    public static void dropsPerLayer(){
        double intensityMin = 0.25/3600;
        double intensityMax = 100.0/3600;
        double intensityCurrent = intensityMin;
        double intensityStep = 10.0/3600;
        int columns = (int)((intensityMax - intensityMin)/intensityStep)+2;

        double dropDiameterMin = 0.5;
        double dropDiameterMax = 7.0;
        double dropDiameterCurrent = dropDiameterMin;
        double dropDiameterStep = 0.1;
        int rows = (int)((dropDiameterMax - dropDiameterMin)/dropDiameterStep)+2;

        double [][] results = new double[columns][rows];
        int columnIndex=0;
        int rowIndex=0;
        RainDensity next;

        while (intensityCurrent<intensityMax){
            results[columnIndex][0] = intensityCurrent;
            columnIndex++;

            while (dropDiameterCurrent<dropDiameterMax){
                rowIndex++;
                next = new RainDensity(intensityCurrent, dropDiameterCurrent);
                results[0][rowIndex] = dropDiameterCurrent;
                results[columnIndex][rowIndex] = next.dropsIn1m2;

                dropDiameterCurrent+=dropDiameterStep;

            }
            dropDiameterCurrent = dropDiameterMin;
            rowIndex=0;

            intensityCurrent+=intensityStep;
        }

        File file = new File("results/density.tsv");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(int i=0; i<results.length; i++){
                for(int j=0; j<results[0].length;j++){
                    bw.write(String.format("%.9f\t", results[i][j]));
                }
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static void waterPerVolume(){
        double intensityMin = 0.25/3600;
        double intensityMax = 100.0/3600;
        double intensityCurrent = intensityMin;
        double intensityStep = 10.0/3600;
        int columns = (int)((intensityMax - intensityMin)/intensityStep)+2;

        double dropDiameterMin = 0.5;
        double dropDiameterMax = 7.0;
        double dropDiameterCurrent = dropDiameterMin;
        double dropDiameterStep = 0.1;
        int rows = (int)((dropDiameterMax - dropDiameterMin)/dropDiameterStep)+2;

        double [][] results = new double[columns][rows];
        int columnIndex=0;
        int rowIndex=0;
        RainDensity next;

        while (intensityCurrent<intensityMax){
            results[columnIndex][0] = intensityCurrent;
            columnIndex++;

            while (dropDiameterCurrent<dropDiameterMax){
                rowIndex++;
                next = new RainDensity(intensityCurrent, dropDiameterCurrent);
                results[0][rowIndex] = dropDiameterCurrent;
                results[columnIndex][rowIndex] = next.waterPer1m2*100; // water per 1 m3

                dropDiameterCurrent+=dropDiameterStep;

            }
            dropDiameterCurrent = dropDiameterMin;
            rowIndex=0;

            intensityCurrent+=intensityStep;
        }

        File file = new File("results/densityInVolume.tsv");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(int i=0; i<results.length; i++){
                for(int j=0; j<results[0].length;j++){
                    bw.write(String.format("%.9f\t", results[i][j]));
                }
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


}
