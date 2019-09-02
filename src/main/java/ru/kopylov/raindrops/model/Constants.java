package ru.kopylov.raindrops.model;

/**
 * Параметры эксперимента
 */
public interface Constants {
//    TODO сделать хелпер для вычисляемых значений
    //    размер капли, выражен поперечным сечением от 0.5 до 7 мм
//    https://ru.wikipedia.org/wiki/%D0%94%D0%BE%D0%B6%D0%B4%D1%8C#%D0%94%D0%BE%D0%B6%D0%B4%D0%B5%D0%B2%D1%8B%D0%B5_%D0%BA%D0%B0%D0%BF%D0%BB%D0%B8
    double DropSize = 3.0;

    /**
     *     дистанция эксперимента
      */
    int Distance = 1000;

/**    cкорость падения капель (вертикально)
        Скорость падения дождевых капель диаметром 0,5 мм на уровне моря и без ветра составляет от 2 (200 см) до 6,6 (660 см) метров в секунду,
        в то время как капли диаметром 5 мм имеют скорость от 9 (900 см) до 30 (3000 см) метров в секунду.
        Все измерения в см/сек
        min = 200 sm/sec
        max = 3000 sm/sec
        пусть будет линейная зависимость
        после 5 скорость опять снижается (допустим с той же интенсивностью)

*/
     default int DropFallingSpeed(){
         if(DropSize>=0.5 && DropSize<5){
             return (int)(DropSize*622 - 111);
         } else if (DropSize>=5 && DropSize<=7){
             return (int)((5 - (DropSize-5))*622 - 111);
         } else {
             throw new RuntimeException("incorrect drop size");
         }
    }


/*  скорость перемещеня человека (горизонтально)
    минимальная - медленный темп 2,196 км/ч  https://ru.wikipedia.org/wiki/%D0%A5%D0%BE%D0%B4%D1%8C%D0%B1%D0%B0_%D1%87%D0%B5%D0%BB%D0%BE%D0%B2%D0%B5%D0%BA%D0%B0
    максимальная - 43.9 км/ч -
    (самая быстрая на данный момент зафиксированная скорость бега.
    Обладателем данного рекорда является Усэйн Болт - в 2009 году он пробежал стометровку за 9,58 секунд
    (средняя 37км/ч, а пиковую скорость он развил к 60-70 метру бега в 43,9км/ч).
     min = 61 sm/sec
     max = 1219 sm/sec

 */
    int HumanSpeed = 1219;


    /* **** Размерность человека (рост, ширина, глубина) ****
     Горизонтальная проекция человека - эллипс 50х25, используется, например здесь http://base.garant.ru/12169057/5da741911cf9399494368b18de80fbe8/
     площадь S=pi*a*b  S = 3.14*(50/2)*(25/2) ~ 982 кв см
     В нашем случае это прямоугольник с той же площадью, возьмем 48 на 20 (960)
     Вертикальная проекция человека, поверхностное гугление не дало результатов,
     поэтому за высоту беру рост немного меньше своего 160 см ()

     */
    int HumanHeight = 160;
    int HumanWidth = 48;
    int HumanDepth = 20;

//    **** Размерность пространства для эксперимента *****

//    минимальная высота пространства на одну единицу выше человека (в верхнем горизонтальном слое будут генерироваться капли, которые будут падать через все нижние слои)
    default int SpaceHeight(){
        return HumanHeight+1;
    }
//    минимальная ширина пространства равна ширине человека
    default int SpaceWidth(){
        return HumanWidth;
    }

    /**
     *  длина пространста это тоже измерение, что и глубина человека, его величина должна быть такой,
     чтобы к тому моменту, когда человек пройдет его полностью в первом вертикальном слое,
     обновились все ячейки - это позволит не просчитывать всю дистанцию, а переиспользовать необольшое пространство,
     без ущерба достоверности, но с экономиейй памяти и вычислительных ресурсов.
     Величина зависит от соотношения вертикальной скорости дождя и горизонтальной скорости человека.
     Минимальное значение - глубина человека
     Если эксперимент проводится для диапазона скоростей дождя или человека выбирается максимальное значение длины, и
     на этом значении должна проводиться вся серия экспериментов
     Например: скорость человека 61 см/сек, а дождя 1220 см/сек, высота 161 см.
     Тогда за одно перемещение человека обновится 1220/61 = 20 горизонтальных слоев.
     А за время совершения человеком 161/20 +1 = 9 движений обновится весь 10 й вертикальный слой (или 1й)
     double hLayrsPerMove = DropFallingSpeed()/HumanSpeed; // количество заполненных слоев на одно горизонтальное смещение
     double vLayrsPerMove = SpaceHeight() / hLayrsPerMove ; // количество горизонтальных смещений, за которое заполняется вертикальный слой
     */
    default int SpaceLenght(){
        return (int)(SpaceHeight()/((double)DropFallingSpeed()/HumanSpeed))+1+HumanDepth;
    }




//    Интенсивность дождя колеблется от 0,25 мм/ч (моросящий дождь) до 100 мм/ч (сильнейший ливень).
//   от нее зависит вероятность образования капель в верхнем слое.


    int RainIntensyty = 50;

//    вероятность появления капли дождя в ячейке пространства
//    TODO вывести эту величину
        /*
   Интенсивность I - 1 мм/ч это пленка тощиной 1 мм которая накапывает на 1 кв. м за 3600 секунд
   оцека объема в литрах = 10 * 10 * 0.01 = 1 литр в час или 1/3600 в секунду.
   Какому отрезку времени соответствует заполнение одного слоя ?
   v скорость падения S - расстояние 1см, время t = s/v
    скорость падения v - это количество вертикальных слоев, которые заполняются за одну секунду
   значит время на заполнение одного слоя (dhtvz nbrf d ghjuhfvvt) t = s/v , где s = 1 см

   Тиким образом объем воды в слое 1 кв.м за один ход это интенсивность умноженная на время V = I*t

   Количество капель в данном объеме - это общий объем разделить на объем капли, который зависит от диаметра капли
   Вероятность появления в 1м кв см  - это количество капель разделить на 100*100.
   (можно не перебирать весь слой, а определить количество капель и раскидать их случайным образом)???

   Пример на тестовых данных:
   I = 50 мм/ч = 5/360 л/сек
   скорость при размере капли 3 мм v = 1775 см/сек
   время заполнения одного слоя (время тика в программе) t = 1/1775 c
   объем воды в слое 1см на 1 кв. м:  V = I*t = (5/360) * (1/1775)
   объем капли в литрах vd = 4/3 * pi * r*r*r
   intensity: 0,01388889 liter per second
        drop volume: 0,00001414  liters
        drop speed: 1755,00000000 cm per sec
        time per layer: 0,00056980 sec
        water in one  layer: 0,00000791 liters
        drops in one  layer: 0,55979369




   Вот.
   TODO написать тест для проверки

     */
    double ProbabilityDropInCell = 0.5;




}
