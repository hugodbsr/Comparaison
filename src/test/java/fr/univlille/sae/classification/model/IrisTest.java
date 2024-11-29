package fr.univlille.sae.classification.model;
import javafx.scene.paint.Color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IrisTest {

    Iris iris = new Iris(2.8, 3.0, 1.9, 4.1, "Setosa");

    @Test
    void getSepalWidth() {
        assertEquals(3.0 , iris.getSepalWidth());
    }

    @Test
    void getSepalLength() {
        assertEquals(2.8, iris.getSepalLength());
    }

    @Test
    void getPetalWidth() {
        assertEquals(4.1, iris.getPetalWidth());
    }

    @Test
    void getPetalLength() {
        assertEquals(1.9, iris.getPetalLength());
    }

/*
    @Test
    void getDataType() {
        assertEquals(3.0 , iris.getDataType("sepalWidth"));
        assertEquals(2.8, iris.getDataType("sepalLength"));
        assertEquals(4.1, iris.getDataType("petalWidth"));
        assertEquals(1.9, iris.getDataType("petalLength"));
    }

    @Test
    void getColor() {
        assertEquals(Color.RED, iris.getClassifications().get(iris.getClassification()));
    }

    @Test
    void testToString() {
        assertEquals("Iris{sepalLength=2.8, sepalWidth=3.0, petalLength=1.9, petalWidth=4.1}", iris.toString());
    }

 */
}