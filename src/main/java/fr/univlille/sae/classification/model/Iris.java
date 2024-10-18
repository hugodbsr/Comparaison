package fr.univlille.sae.classification.model;

import java.util.Set;
import com.opencsv.bean.*;

public class Iris extends LoadableData{

    @CsvBindByName(column = "sepal.width")
    private double sepalWidth;
    @CsvBindByName(column = "sepal.length")
    private double sepalLength;
    @CsvBindByName(column = "petal.width")
    private double petalWidth;
    @CsvBindByName(column = "petal.length")
    private double petalLength;

    public Iris(double sepalWidth, double sepalLength, double petalWidth, double petalLength) {
        this(sepalWidth, sepalLength, petalWidth, petalLength, "undefined");
    }

    public Iris() {
        //
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

    @Override
    public String toString() {
        return "Iris{" +
                "sepalWidth=" + sepalWidth +
                ", sepalLength=" + sepalLength +
                ", petalWidth=" + petalWidth +
                ", petalLength=" + petalLength +
                '}';
    }
}
