package fr.univlille.sae.classification.knn.distance;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import fr.univlille.sae.classification.model.LoadableData;

import java.util.Map;

public class DistanceManhattanTest {

    @Test
    public void testDistanceMatchingAttributes() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{2.0, 3.0, 6.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        LoadableData data2 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{5.0, 8.0, 3.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        DistanceManhattan distance = new DistanceManhattan();
        double result = distance.distance(data1, data2);
        assertEquals(11.0, result, 0.0001);
    }

    @Test
    public void testDistanceWithZeros() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{0.0, 0.0, 0.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        LoadableData data2 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{0.0, 0.0, 0.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        DistanceManhattan distance = new DistanceManhattan();
        double result = distance.distance(data1, data2);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    public void testDistanceWithNegativeValues() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{-2.0, -3.0, -6.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        LoadableData data2 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{2.0, 3.0, 6.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        DistanceManhattan distance = new DistanceManhattan();
        double result = distance.distance(data1, data2);
        assertEquals(22.0, result, 0.0001);
    }

    @Test
    public void testDistanceWithDifferentAttributeLengths() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }
            @Override
            public double[] getAttributes() {
                return new double[]{1.0, 2.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A"};
            }
        };

        LoadableData data2 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{3.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A"};
            }
        };

        DistanceManhattan distance = new DistanceManhattan();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> distance.distance(data1, data2));
    }

    @Test
    public void testDistanceEmptyAttributes() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{};
            }
        };

        LoadableData data2 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
            }

            @Override
            public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {

            }

            @Override
            public Map<String, Object> getClassifiedAttributes() {
                return Map.of();
            }

            @Override
            public int getClassificationType() {
                return 0;
            }

            @Override
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{};
            }
        };

        DistanceManhattan distance = new DistanceManhattan();
        double result = distance.distance(data1, data2);
        assertEquals(0.0, result, 0.0001);
    }
}