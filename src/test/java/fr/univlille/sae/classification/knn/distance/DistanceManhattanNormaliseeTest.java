package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.knn.MethodKNN;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DistanceManhattanNormaliseeTest {

    private DistanceManhattanNormalisee distanceManhattanNormalisee;

    @BeforeEach
    void setUp() {
        distanceManhattanNormalisee = new DistanceManhattanNormalisee();

        // Configurer les valeurs statiques pour la normalisation
        MethodKNN.minData = new double[]{1.0, 2.0, 0.5};
        MethodKNN.amplitude = new double[]{4.0, 3.0, 2.5};
    }

    @Test
    void testDistance_Calculation() {
        LoadableData l1 = new LoadableData() {
            @Override
            public double[] getAttributes() {
                return new double[]{2.0, 5.0, 1.0};
            }

            @Override
            public String getClassification() {
                return "Class1";
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
            public String[] getStringAttributes() {
                return new String[0];
            }
        };

        LoadableData l2 = new LoadableData() {
            @Override
            public double[] getAttributes() {
                return new double[]{4.0, 4.0, 2.0};
            }

            @Override
            public String getClassification() {
                return "Class2";
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
            public String[] getStringAttributes() {
                return new String[0];
            }
        };

        double expectedDistance =
                (Math.abs(2.0 - 4.0) - 1.0) / 4.0 +
                        (Math.abs(5.0 - 4.0) - 2.0) / 3.0 +
                        (Math.abs(1.0 - 2.0) - 0.5) / 2.5;

        assertEquals(expectedDistance, distanceManhattanNormalisee.distance(l1, l2), 1e-6);
    }

    @Test
    void testDistance_ZeroDistance() {
        MethodKNN.minData = new double[]{1.0, 1.0, 1.0};
        LoadableData l1 = new LoadableData() {
            @Override
            public double[] getAttributes() {
                return new double[]{2.0, 2.0, 2.0};
            }

            @Override
            public String getClassification() {
                return "Class1";
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
            public String[] getStringAttributes() {
                return new String[0];
            }
        };

        LoadableData l2 = new LoadableData() {
            @Override
            public double[] getAttributes() {
                return new double[]{1.0, 1.0, 1.0};
            }

            @Override
            public String getClassification() {
                return "Class1";
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
            public String[] getStringAttributes() {
                return new String[0];
            }
        };

        assertEquals(0.0, distanceManhattanNormalisee.distance(l1, l2), 1e-6);
    }


    @Test
    void testDistance_EmptyAttributes() {
        LoadableData l1 = new LoadableData() {
            @Override
            public double[] getAttributes() {
                return new double[]{};
            }

            @Override
            public String getClassification() {
                return "Class1";
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
            public String[] getStringAttributes() {
                return new String[0];
            }
        };

        LoadableData l2 = new LoadableData() {
            @Override
            public double[] getAttributes() {
                return new double[]{};
            }

            @Override
            public String getClassification() {
                return "Class2";
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
            public String[] getStringAttributes() {
                return new String[0];
            }
        };

        assertEquals(0.0, distanceManhattanNormalisee.distance(l1, l2), 1e-6);
    }

    @Test
    void testDistance_DifferentLengthAttributes() {
        LoadableData l1 = new LoadableData() {
            @Override
            public double[] getAttributes() {
                return new double[]{2.0, 3.0};
            }

            @Override
            public String getClassification() {
                return "Class1";
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
            public String[] getStringAttributes() {
                return new String[0];
            }
        };

        LoadableData l2 = new LoadableData() {
            @Override
            public double[] getAttributes() {
                return new double[]{2.0};
            }

            @Override
            public String getClassification() {
                return "Class2";
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
            public String[] getStringAttributes() {
                return new String[0];
            }
        };

        Exception exception = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> distanceManhattanNormalisee.distance(l1, l2)
        );

        assertEquals("Index 1 out of bounds for length 1", exception.getMessage());
    }
}
