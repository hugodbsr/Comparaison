package fr.univlille.sae.classification.knn.distance;

import static org.junit.jupiter.api.Assertions.*;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fr.univlille.sae.classification.knn.MethodKNN;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.Iris;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.univlille.sae.classification.model.LoadableData;

import java.io.File;
import java.util.Map;

public class DistanceManhattanTest {

    private ClassificationModel model;
    private DistanceManhattan distance;
    private String path = System.getProperty("user.dir") + File.separator + "res" + File.separator;

    @BeforeEach
    public void initialize() throws CsvRequiredFieldEmptyException {

        model = ClassificationModel.getClassificationModel();
        distance = new DistanceManhattan();

        model.setDistance(distance);
        model.loadData(new File(path + "data/iris.csv"));



        MethodKNN.updateModel(model.getDatas());
    }

    @Test
    public void test_distance_est_correcte() {

        Iris iris = new Iris(1.0, 1.0, 1.0,1.0);
        Iris iris2 = new Iris(1.0, 1.0, 1.0,1.0);
        assertEquals(0, distance.distance(iris, iris2), 0.001);

        Iris iris3 = new Iris(2.0, 2.0, 2.0, 2.0);
        assertEquals(4.0, distance.distance(iris, iris3), 0.01);

    }
}