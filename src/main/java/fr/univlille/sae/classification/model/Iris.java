package fr.univlille.sae.classification.model;

import com.opencsv.bean.*;
import javafx.scene.paint.Color;

public class Iris extends LoadableData{

    @CsvBindByName(column = "sepal.width")
    private double sepalWidth;
    @CsvBindByName(column = "sepal.length")
    private double sepalLength;
    @CsvBindByName(column = "petal.width")
    private double petalWidth;
    @CsvBindByName(column = "petal.length")
    private double petalLength;
    @CsvBindByName(column = "variety")
    private String variety;

    public Iris(double sepalWidth, double sepalLength, double petalWidth, double petalLength) {
        this(sepalWidth, sepalLength, petalWidth, petalLength, "undefined");
    }

    public Iris() {
        //
    }

    public Iris(double sepalWidth, double sepalLength, double petalWidth, double petalLength, String variety) {
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

                        public String getVariety() {
        return variety;
    }

    public double getDataType(String axes){
        if(axes==null){
            return sepalWidth;
        }
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
                "sepalWidth",
                "sepalLength",
                "petalWidth",
                "petalLength"
        };
        return names;
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
