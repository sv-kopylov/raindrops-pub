package ru.kopylov.raindrops.model;

public class Circle {
    private int max;
    private int position;

    public Circle(int max, int position) {
        if (position >= max) {
            throw new IllegalArgumentException("position mmust not be bigger tham max");
        }
        this.max = max;
        this.position = position;
    }

    public int current(){
        return position;
    }

    public int next() {
        position++;
        if (position >= max) {
            position = 0;
        }
        return position;
    }

    public int previous() {
        position--;
        if (position < 0) {
            position = max - 1;
        }
        return position;
    }
}


