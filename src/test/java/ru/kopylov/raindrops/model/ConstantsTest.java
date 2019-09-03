package ru.kopylov.raindrops.model;

import org.junit.Test;
import ru.kopylov.raindrops.application.RainDensity;

import static org.junit.Assert.*;

public class ConstantsTest {

    @Test
    public void dropFallingSpeed() {
        double ds=0.5;
        while(ds<=7){
            System.out.format("%.1f ", ds);
            System.out.println(DropFallingSpeed(ds));
            ds+=0.1;
        }

    }



    static int DropFallingSpeed(double DropSize){
        if(DropSize>=0.5 && DropSize<5){
            return (int)(DropSize*622 - 111);
        } else if (DropSize>=5 && DropSize<=7){
            return (int)((5 - (DropSize-5))*622 - 111);
        } else {
            throw new RuntimeException("incorrect drop size");
        }
    }


    @Test
    public void spaceLenght() {
        Constants cstns = new Constants() {
        };
        System.out.println(cstns.SpaceLenght());
    }

    @Test
    public void probability(){
        double intensity = 0.25/3600.0;
        double dropDiameter = 1.1; // mm
        double r = (dropDiameter/2.0)*(1.0/100.0);
        double dropVolume = (4.0/3.0)*Math.PI*r*r*r;
        double dropSpeed = DropFallingSpeed(dropDiameter); //1775 см/сек
        double timePerLayer = 1.0/dropSpeed;
        double waterPer1m2 = intensity*timePerLayer;
        double dropsIn1m2 = waterPer1m2/dropVolume;
        System.out.printf("intensity: %.8f liter per second\n", intensity);
        System.out.printf("drop volume: %.8f  liters\n",dropVolume);
        System.out.printf("drop diameter: %.8f  milimeter\n",dropDiameter);
        System.out.printf("drop speed: %.8f cm per sec\n", dropSpeed);
        System.out.printf("time per layer: %.8f sec\n", timePerLayer);
        System.out.printf("water in one  layer: %.8f liters \n", waterPer1m2);
        System.out.printf("drops in one  layer: %.8f \n", dropsIn1m2);
    }

    @Test
    public void estimation(){
        System.out.printf("%.8f \n", 0.25/3600);
        System.out.printf("%.8f \n", 100.0/3600);

    }
    @Test
    public void cmp(){
        double intensity = 10.25/3600.0;
        double dropDiameter = 0.5; // mm
        RainDensity next = new RainDensity(intensity, dropDiameter);
        System.out.printf("drops in one  layer: %.8f \n", next.getDropsIn1m2());
    }


}