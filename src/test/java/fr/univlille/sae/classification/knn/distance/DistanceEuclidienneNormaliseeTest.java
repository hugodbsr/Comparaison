package fr.univlille.sae.classification.knn.distance;

import static org.junit.jupiter.api.Assertions.*;

import fr.univlille.sae.classification.knn.MethodKNN;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.univlille.sae.classification.model.LoadableData;

import java.util.Map;

public class DistanceEuclidienneNormaliseeTest {

    @BeforeAll
    public static void setupMethodKNN() {
        MethodKNN.minData = new double[]{0.0, 2.0, 4.0};
        MethodKNN.amplitude = new double[]{10.0, 3.0, 2.0}; // Assurez-vous que toutes les amplitudes sont non nulles
    }

    @Test
    public void testDistanceMatchingAttributes() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
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
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }
            @Override
            public double[] getAttributes() {
                return new double[]{8.0, 5.0, 8.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        DistanceEuclidienneNormalisee distance = new DistanceEuclidienneNormalisee();
        double result = distance.distance(data1, data2);
        assertEquals(1.3432961119739923, result, 0.0001);
    }

    @Test
    public void testDistanceDifferentStringAttributes() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
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
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }

            @Override
            public double[] getAttributes() {
                return new double[]{8.0, 5.0, 8.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "C"};
            }
        };

        DistanceEuclidienneNormalisee distance = new DistanceEuclidienneNormalisee();
        double result = distance.distance(data1, data2);
        assertEquals(1.674647558277396, result, 0.0001);
    }

    @Test
    public void testDistanceDifferentAttributeLengths() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
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
                return new double[]{2.0, 3.0};
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
            public void setClassification(String classification) {

            }

            @Override
            public Map<String, Object> getAttributesNames() {
                return null;
            }
            @Override
            public double[] getAttributes() {
                return new double[]{8.0, 5.0, 8.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A"};
            }
        };

        DistanceEuclidienneNormalisee distance = new DistanceEuclidienneNormalisee();
        assertThrows(IllegalArgumentException.class, () -> distance.distance(data1, data2));
    }

    @Test
    public void testDistanceDifferentStringAttributeLengths() {
        LoadableData data1 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
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
                return new String[]{"A"};
            }
        };

        LoadableData data2 = new LoadableData() {
            @Override
            public String getClassification() {
                return "";
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
                return new double[]{8.0, 5.0, 8.0};
            }

            @Override
            public String[] getStringAttributes() {
                return new String[]{"A", "B"};
            }
        };

        DistanceEuclidienneNormalisee distance = new DistanceEuclidienneNormalisee();
        assertThrows(IllegalArgumentException.class, () -> distance.distance(data1, data2));
    }
}

