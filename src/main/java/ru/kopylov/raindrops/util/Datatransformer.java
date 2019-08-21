package ru.kopylov.raindrops.util;

public class Datatransformer {
    static int kmphTosmpsec(double value){
        int result = (int)(value*1000*100)/3600;
        return result;
    }

    static int mpsecTosmpsec(double value){
        int result = (int)(value*100);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(kmphTosmpsec(43.9));
    }

}
