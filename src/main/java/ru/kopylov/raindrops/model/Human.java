package ru.kopylov.raindrops.model;

public class Human {
    InputDataSet ds = InputDataSet.getInstance();
//    позиция указывает на слой перед самым носом человека
    private int position = ds.getHumanDepth();
//    количество капель, собранных человеком всего

    private long topDrops = 0;
    private long frontDrops = 0;

//    вычисляется количество капель в пятне слоя аккурат над человеком
    public void updateTop(HLayerIterator iter){
        for(int d = position - ds.getHumanDepth(); d<position; d++){
            for(int w=0; w<ds.getSpaceWidth(); w++){
                topDrops+=iter.get(d, w);
            }
        }

    }

//    вычисляется число капель в пятне слоя с которым человек сталкивается двигаясь вперед
//    один ряд, относящийся к верхнему слою не учитывается (здесь всегда слой с номером HumanHeight
//     это допущение чтобы не усложнять )
//    При переходе от последнего к первому, нарушается непрерывность, мы как бы перескакиваем толщину человека (не собирая капель)
//    Но если принять что пространство уже на глубину человека, то во фронтальном слое все будет непрерывно
//    прерываться будет только верхний слой, но поскольку он и так каждую итерацию новый и вероятность образования
//    капель одинакова во всем верхнем слое, то это допущение не должно как - либо повлиять на результат,
//    упростит код и немного утяжелит вычисления (вспомнить при оптимизации)
    public void updateFront(VLayerIterator iter){
        iter.reset();
//        iter.setLayerNumber(position);
        for(int h=0; h<ds.getHumanHeight(); h++){
            for(int w=0; w<ds.getHumanWidth(); w++){
                frontDrops+=iter.get(h, w);
            }
        }
        updatePosition();

    }
//   Позиция обновляется таким образом, чтобы над человеком всегда было пятно дождя
    private void updatePosition() {
        if(position<ds.getSpaceLenght()){
            position++;
        } else {
            position=ds.getHumanDepth();
        }
    }

//    TODO перевести в литры
    public double getCollectedWater(){
        return (frontDrops + topDrops);
    }

}
