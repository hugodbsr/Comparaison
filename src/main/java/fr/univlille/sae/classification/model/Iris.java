package fr.univlille.sae.classification.model;

import java.util.Set;

public class Iris extends LoadableData{


    private double sepalWidth;
    private double sepalLength;
    private double petalWidth;
    private double petalLength;




    public Iris(double sepalWidth, double sepalLength, double petalWidth, double petalLength) {
        this(sepalWidth, sepalLength, petalWidth, petalLength, "undefined");
    }

    public Iris(double sepalWidth, double sepalLength, double petalWidth, double petalLength, String classification) {
        super();
        this.sepalWidth = sepalWidth;
        this.sepalLength = sepalLength;
        this.petalWidth = petalWidth;
        this.petalLength = petalLength;
    }




    public double getSepalWidth() {
        return sepalWidth;
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }
}
