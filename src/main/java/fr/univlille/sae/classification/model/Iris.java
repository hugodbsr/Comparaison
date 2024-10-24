package fr.univlille.sae.classification.model;

import com.opencsv.bean.*;
import javafx.scene.paint.Color;

import java.util.Random;

public class Iris extends LoadableData{


    @CsvBindByName(column = "sepal.length")
    private double sepalLength;
    @CsvBindByName(column = "sepal.width")
    private double sepalWidth;
    @CsvBindByName(column = "petal.length")
    private double petalLength;
    @CsvBindByName(column = "petal.width")
    private double petalWidth;
    @CsvBindByName(column = "variety")
    private String variety;

    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        this(sepalLength, sepalWidth, petalLength, petalWidth, "undefined");
    }

    public Iris() {
        //
    }

    @Override
    public String getClassification() {
        return variety;
    }

    @Override
    public void setClassification(String classification) {
        this.variety = classification;
    }

    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String variety) {
        super();
        this.sepalWidth = sepalWidth;
        this.sepalLength = sepalLength;
        this.petalWidth = petalWidth;
        this.petalLength = petalLength;
        this.variety = variety;
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

    public double getDataType(String axes){
        switch (axes){
            case "sepalWidth":
                return sepalWidth;
            case "sepalLength":
                return sepalLength;
            case "petalWidth":
                return petalWidth;
            case "petalLength":
                return petalLength;
            default:
                return sepalLength;
        }
    }

    public Color getColor(){
        switch (this.variety){
            case "Setosa":
                return Color.RED;
            case "Versicolor":
                return Color.BLUE;
            case "Virginica":
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    public String[] getAttributesName() {
        String[] names = new String[]{
                "sepalLength",
                "sepalWidth",
                "petalLength",
                "petalWidth"
        };
        return names;
    }

    @Override
    public String toString() {
        return "Iris{" +
                "sepalLength=" + sepalLength +
                ", sepalWidth=" + sepalWidth +
                ", petalLength=" + petalLength +
                ", petalWidth=" + petalWidth +
                '}';
    }
}
