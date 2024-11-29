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
        assertEquals(3.0 , iris.getSepalWidth());
        assertEquals(2.8, iris.getSepalLength());
        assertEquals(4.1, iris.getPetalWidth());
        assertEquals(1.9, iris.getPetalLength());
    }

//    @Test
//    void getColor() throws IllegalAccessException {
//        assertEquals(Color.RED, iris.getClassifications().get(iris.getClassification()));
//    }

    @Test
    void testToString() {
        assertEquals("Sepal length: 2.8\nSepal width: 3.0\nPetal length: 1.9\nPetal width: 4.1\nVariety: Setosa", iris.toString());
    }

 */
}