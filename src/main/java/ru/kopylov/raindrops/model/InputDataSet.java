package ru.kopylov.raindrops.model;

import org.apache.log4j.Logger;
import ru.kopylov.raindrops.application.Application;
import ru.kopylov.raindrops.util.Helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * набор переменных для эксперимента,
 * один датасет на программу, сингельтон
 * переменные делятся на три типа
 * 1 - финальная без сеттера - не может меняться на протяжении эксперимента
 * 2 - производная - зависит от других констант, своего сеттера не имеют, обновляется при обновлении констант 3 го типа
 * 3 - изменяемые - могут меняться от итерации к итерации
 */

public class InputDataSet {
    public static Logger logger = Logger.getLogger(InputDataSet.class);
    private static InputDataSet instance = new InputDataSet();

    public static InputDataSet getInstance(){
        return instance;
    }



   /**
    *  размер капли, выражен поперечным сечением от 0.5 до 7 мм
    *  https://ru.wikipedia.org/wiki/%D0%94%D0%BE%D0%B6%D0%B4%D1%8C#%D0%94%D0%BE%D0%B6%D0%B4%D0%B5%D0%B2%D1%8B%D0%B5_%D0%BA%D0%B0%D0%BF%D0%BB%D0%B8
    */
    private double DropSize = 3.0;

    /**
     *     дистанция эксперимента в сантиметрах
     */
    private final int Distance;

    /**
     * cкорость падения капель
     *  Скорость падения дождевых капель диаметром 0,5 мм на уровне моря и без ветра составляет от 2 м/с (200 см/с) до 6,6 м/с (660 см/с),
     *  в то время как капли диаметром 5 мм имеют скорость от 9 м/с (900 см/с) до 30 м/с (3000 смс).
     *  Все измерения в см/сек
     *  добавлена линейная зависимость
     *  после 5 скорость опять снижается
     *  (можно уточнить / добавить )
     */
    private int DropFallingSpeed = Helper.DropFallingSpeed(DropSize); // не финальная но нет сеттера



    /**
     * скорость перемещеня человека (горизонтально)
     *  минимальная - медленный темп 2,196 км/ч  https://ru.wikipedia.org/wiki/%D0%A5%D0%BE%D0%B4%D1%8C%D0%B1%D0%B0_%D1%87%D0%B5%D0%BB%D0%BE%D0%B2%D0%B5%D0%BA%D0%B0
     *  максимальная - 43.9 км/ч - самая быстрая на данный момент зафиксированная скорость бега.
     *  min = 61 см/сек
     *  max = 1219 см/сек

     */
    private int HumanSpeed = 1219;


    /** **** Размерность человека (рост, ширина, глубина) ****
     Горизонтальная проекция человека - эллипс 50х25, используется, например здесь http://base.garant.ru/12169057/5da741911cf9399494368b18de80fbe8/
     площадь S=pi*a*b  S = 3.14*(50/2)*(25/2) ~ 982 кв см
     В нашем случае это прямоугольник с той же площадью, возьмем 48 на 20 (960)
     Вертикальная проекция человека, поверхностное гугление не дало результатов,
     поэтому за высоту беру рост немного меньше своего 160 см ()
     */
    private final int HumanHeight;
    private final int HumanWidth;
    private final int HumanDepth;

//    **** Размерность пространства для эксперимента *****

    /**
     * минимальная высота пространства на одну единицу выше человека (в верхнем горизонтальном слое будут генерироваться капли, которые будут падать через все нижние слои)
     */
    private final int SpaceHeight;

    /**
     * минимальная ширина пространства равна ширине человека
     */
    private final int SpaceWidth;

    /**
     *  длина пространста это тоже измерение, что и глубина человека, его величина должна быть такой,
     *  чтобы к тому моменту, когда человек полностью его пройдет в первом вертикальном слое,
     *  обновились все ячейки - это позволит не просчитывать всю дистанцию, а переиспользовать необольшое пространство,
     *  без ущерба достоверности, но с экономиейй памяти и вычислительных ресурсов.
     *  Величина зависит от соотношения вертикальной скорости дождя и горизонтальной скорости человека.
     *  Минимальное теоретическое значение - глубина человека плюс один
     *  Если эксперимент проводится для диапазона скоростей то выбирается максимальное значение длины, и
     *  на этом значении проводятся все эксперименты
     *  Пример: скорость человека 61 см/сек, скорость дождя 1220 см/сек, высота пространства 161 см.
     *  За одно перемещение человека обновляется 1220/61 = 20 горизонтальных слоев.
     *  Для обновления всех горизонтальных слоев потребуется 161/20 + 1 = 9 перемещений.
     */
    private final int SpaceLenght;



    /**
     * Интенсивность дождя колеблется от 0,25 мм/ч (моросящий дождь) до 100 мм/ч (сильнейший ливень).
     * от нее зависит вероятность образования капель в верхнем слое.
     * В конструкторе преобразуется в мм/сек так дальше и используется
     */
    private double RainIntensyty;


    /**
     * Вероятность появления капли в кубсеском сантиметре верхнего слоя
     *  Интенсивность I - 1 мм/ч это пленка тощиной 1 мм которая накапывает на 1 кв. м за 3600 секунд
     *  оцека объема в литрах = 10 * 10 * 0.01 = 1 литр в час или 1/3600 в секунду.
     *  Какому отрезку времени соответствует заполнение одного слоя ?
     *  v скорость падения S - расстояние 1см, время t = s/v
     *   скорость падения v - это количество вертикальных слоев, которые заполняются за одну секунду
     *  значит время на заполнение одного слоя (dhtvz nbrf d ghjuhfvvt) t = s/v , где s = 1 см

     *  Тиким образом объем воды в слое 1 кв.м за один ход это интенсивность умноженная на время V = I*t

     *  Количество капель в данном объеме - это общий объем разделить на объем капли, который зависит от диаметра капли
     *  Вероятность появления в 1м кв см  - это количество капель разделить на 100*100.
     *  (можно не перебирать весь слой, а определить количество капель и раскидать их случайным образом)???

     *  Пример на тестовых данных:
     *  I = 50 мм/ч = 5/360 л/сек
     *  скорость при размере капли 3 мм v = 1775 см/сек
     *  время заполнения одного слоя (время тика в программе) t = 1/1775 c
     *  объем воды в слое 1см на 1 кв. м:  V = I*t = (5/360) * (1/1775)
     *  объем капли в литрах vd = 4/3 * pi * r*r*r
     *  intensity: 0,01388889 liter per second
     *       drop volume: 0,00001414  liters
     *       drop speed: 1755,00000000 cm per sec
     *       time per layer: 0,00056980 sec
     *       water in one  layer: 0,00000791 liters
     *       drops in one  layer: 0,55979369
     */
    private double ProbabilityDropInCell = Helper.ProbabilityDropInCell();

    /**
     * Каждую итерацию переберать весь слой неэкономично, как показывают графики капель в слое не очень много
     * (даже при минимальном объеме и максимальной интенсивности, поэтому разумнее не перебирать весь слой,
     * а вычислить количество капель и разбросать их равномерно по слою, будет ли это натяжкой ? - нет)
     */

    private double DropsInLayer; // не финальная но нет сеттера

//    сеттеры

    public void setDropSize(double dropSize) {
        DropSize = dropSize;
        updateDerivatives();
    }

    public void setHumanSpeed(int humanSpeed) {
        HumanSpeed = humanSpeed;
        updateDerivatives();
    }

    public void setRainIntensyty(double rainIntensyty) {
        RainIntensyty = rainIntensyty;
        updateDerivatives();
    }

    private void updateDerivatives(){
        DropFallingSpeed = Helper.DropFallingSpeed(DropSize);
        DropsInLayer = SpaceLenght*(1.0/100)*SpaceWidth*(1.0/100)*Helper.DropsInLayer(RainIntensyty, DropSize);
        ProbabilityDropInCell = Helper.ProbabilityDropInCell();
    }


//    геттеры


    public double getDropSize() {
        return DropSize;
    }

    public int getDistance() {
        return Distance;
    }

    public int getDropFallingSpeed() {
        return DropFallingSpeed;
    }

    public int getHumanSpeed() {
        return HumanSpeed;
    }

    public int getHumanHeight() {
        return HumanHeight;
    }

    public int getHumanWidth() {
        return HumanWidth;
    }

    public int getHumanDepth() {
        return HumanDepth;
    }

    public int getSpaceHeight() {
        return SpaceHeight;
    }

    public int getSpaceWidth() {
        return SpaceWidth;
    }

    public int getSpaceLenght() {
        return SpaceLenght;
    }

    public double getRainIntensyty() {
        return RainIntensyty;
    }

    public double getProbabilityDropInCell() {
        return ProbabilityDropInCell;
    }

    public double getDropsInLayer() {
        return DropsInLayer;
    }

    private InputDataSet(){
        Properties ppts = new Properties();
        try(FileInputStream fis = new FileInputStream("model.properties")){
            ppts.load(fis);
        } catch (FileNotFoundException e) {
            logger.error("model properties file not found");
        } catch (IOException e) {
            logger.error(e.getMessage() );
        }
        for(Object key: ppts.keySet()){
            logger.debug((String) key+" = " +ppts.get(key));
        }

        this.Distance = getIntProperty(ppts, "Distance");
        this.DropSize = getDoubleProperty(ppts, "DropSize");
        this.HumanWidth = getIntProperty(ppts, "HumanWidth");
        this.HumanDepth = getIntProperty(ppts, "HumanDepth");
        this.HumanHeight = getIntProperty(ppts, "HumanHeight");
        this.RainIntensyty = getDoubleProperty(ppts, "RainIntensyty")/3600;
        this.HumanSpeed = getIntProperty(ppts, "HumanSpeed");


        this.SpaceHeight = HumanHeight+1;
        this.SpaceWidth = HumanWidth;
        this.SpaceLenght = Helper.SpaceLenght(SpaceHeight, DropFallingSpeed, HumanSpeed, HumanDepth);
        this.DropsInLayer = SpaceLenght*(1.0/100)
                            *SpaceWidth*(1.0/100)
                            *Helper.DropsInLayer(RainIntensyty, DropSize);

    }

    private int getIntProperty(Properties ppts, String key){
        String value = ppts.getProperty(key);
        if(value!=null){
            return Integer.parseInt(value);
        } else {
            throw new RuntimeException("Property not found, key: "+ key);
        }
    }

    private double getDoubleProperty(Properties ppts, String key){
        String value = ppts.getProperty(key);
        if(value!=null){
            return Double.parseDouble(value);
        } else {
            throw new RuntimeException("Property not found, key: "+ key);
        }
    }

}
