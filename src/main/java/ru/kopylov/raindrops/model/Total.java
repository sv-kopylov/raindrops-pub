package ru.kopylov.raindrops.model;

/**
 * -- Показатели:
 -- забег
 -- - объем полученной воды;
 -- - количество капель всего;
 -- - количество (объем) капель сверху всего;
 -- - количество (объем) капель спереди всего;
 -- - средний прирост за один тик;
 -- - средний прирост за один тик (не включая фронтальные);
 -- - средний приросn за одно горизонтальное смещение (всего / только горизонтальные/ только фронтальные)
 -- сет
 -- - разница полученного объема на разных скоростях.
 -- - другие разницы
 */
// TODO delta -> avg
public class Total {
    private long id;
    private long datasetId;
    private double dropVolume;
    private double totalVolume;
    private long totalDrops;
    private long totalTop;
    private long totalFront;
    // количество капель за тик
    private double deltaPerTicTotal;
    // количество капель сверху за тик
    private double deltaPerTicTop;
    // количество капель за одно горизонтальное смещение
    private double deltaPerStep;
    // количество капель сверху за одно горизонтальное смещение
    private double deltaPerStepTop;
    // количество капель cgthtlb за одно горизонтальное смещение
    private double deltaPerStepFront;


    private int totalTicks;

    public double getDeltaPerStepTop() {
        return deltaPerStepTop;
    }

    public void setDeltaPerStepTop(double deltaPerStepTop) {
        this.deltaPerStepTop = deltaPerStepTop;
    }

    public double getDeltaPerStepFront() {
        return deltaPerStepFront;
    }

    public void setDeltaPerStepFront(double deltaPerStepFront) {
        this.deltaPerStepFront = deltaPerStepFront;
    }

    public int getTotalTicks() {
        return totalTicks;
    }

    public void setTotalTicks(int totalTicks) {
        this.totalTicks = totalTicks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(long datasetId) {
        this.datasetId = datasetId;
    }

    public double getDropVolume() {
        return dropVolume;
    }

    public void setDropVolume(double dropVolume) {
        this.dropVolume = dropVolume;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public long getTotalDrops() {
        return totalDrops;
    }

    public void setTotalDrops(long totalDrops) {
        this.totalDrops = totalDrops;
    }

    public long getTotalTop() {
        return totalTop;
    }

    public void setTotalTop(long totalTop) {
        this.totalTop = totalTop;
    }

    public long getTotalFront() {
        return totalFront;
    }

    public void setTotalFront(long totalFront) {
        this.totalFront = totalFront;
    }

    public void setDeltaPerTicTotal(double deltaPerTicTotal) {
        this.deltaPerTicTotal = deltaPerTicTotal;
    }

   public void setDeltaPerTicTop(double deltaPerTicTop) {
        this.deltaPerTicTop = deltaPerTicTop;
    }

    public void setDeltaPerStep(double deltaPerStep) {
        this.deltaPerStep = deltaPerStep;
    }

    public double getDeltaPerTicTotal() {
        return deltaPerTicTotal;
    }

    public double getDeltaPerTicTop() {
        return deltaPerTicTop;
    }

    public double getDeltaPerStep() {
        return deltaPerStep;
    }
}
