package ru.kopylov.raindrops.model;


import org.junit.Test;


public class RainSpaceTest {

    @Test
    public void testCounter(){
        int topLayerPointer = 0;
        int SpaceHeight = 10;
        for(int i=0; i<SpaceHeight*2.7; i++){
            System.out.println(topLayerPointer);
            if(topLayerPointer<SpaceHeight-1){
                topLayerPointer++;
            } else {
                topLayerPointer=0;
            }
            System.out.println("after "+topLayerPointer);

//            topLayerPointer = topLayerPointer<(SpaceHeight-1)?++topLayerPointer:0;
        }
    }

}