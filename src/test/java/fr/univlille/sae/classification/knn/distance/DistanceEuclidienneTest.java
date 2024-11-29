package fr.univlille.sae.classification.knn.distance;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import fr.univlille.sae.classification.model.LoadableData;

import java.util.Map;

public class DistanceEuclidienneTest {

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
                return new double[]{1.0, 2.0, 3.0};
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
                return new double[]{4.0, 6.0, 3.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        DistanceEuclidienne distance = new DistanceEuclidienne();
        double result = distance.distance(data1, data2);

        assertEquals(5.0, result, 0.0001); // Distance euclidienne entre les deux points
    }

    @Test
    public void testDistanceDifferentStringAttributes() {
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
                return new double[]{1.0, 2.0, 3.0};
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
                return new double[]{1.0, 2.0, 3.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "C"};
            }
        };

        DistanceEuclidienne distance = new DistanceEuclidienne();
        double result = distance.distance(data1, data2);

        assertEquals(1.0, result, 0.0001); // Une différence dans les attributs string
    }

    @Test
    public void testDistanceDifferentAttributeLengths() {
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
                return new double[]{1.0, 2.0, 3.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A"};
            }
        };

        DistanceEuclidienne distance = new DistanceEuclidienne();

        assertThrows(IllegalArgumentException.class, () -> {
            distance.distance(data1, data2);
        });
    }

    @Test
    public void testDistanceDifferentStringAttributeLengths() {
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
                return new double[]{1.0, 2.0, 3.0};
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
                return new double[]{1.0, 2.0, 3.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        DistanceEuclidienne distance = new DistanceEuclidienne();

        assertThrows(IllegalArgumentException.class, () -> {
            distance.distance(data1, data2);
        });
    }
}
