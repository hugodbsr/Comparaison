package fr.univlille.sae.classification.knn;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fr.univlille.sae.classification.knn.distance.DistanceEuclidienne;
import fr.univlille.sae.classification.knn.distance.DistanceEuclidienneNormalisee;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.Iris;
import fr.univlille.sae.classification.model.LoadableData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodKNNTest {



    private ClassificationModel model;
    private File csvTemp;
    private String csvTest;


    @BeforeEach
    public void initialize() throws IOException {
        model = ClassificationModel.getClassificationModel();
        csvTemp = File.createTempFile("test", ".csv");
        csvTest = "\"sepal.length\",\"sepal.width\",\"petal.length\",\"petal.width\",\"variety\"\n" +
                "4.1,3.2,1.4,0.2,\"Setosa\"\n" +
                "4.1,3.1,1.5,0.2,\"Setosa\"\n" +
                "3.9,3.6,1.6,0.7,\"Setosa\"\n" +
                "3.7,2.7,1.1,0.4,\"Setosa\"\n" +
                "4.7,3.9,1.9,0.3,\"Setosa\"\n" +
                "4.9,3.2,2.1,0.4,\"Setosa\"\n";
        Files.write(Paths.get(csvTemp.getAbsolutePath()), csvTest.getBytes());

    }


    @Test
    public void testKVoisins_distance_euclidienne() throws IOException, CsvRequiredFieldEmptyException {

        model.loadData(csvTemp);
        List<LoadableData> datas = model.getDatas();
        LoadableData data = new Iris(3.5,2.6,1.0,0.5);

        System.out.println(data);
        MethodKNN.updateModel(datas);
        List<LoadableData> kVoisins = MethodKNN.kVoisins(datas, data, 1, new DistanceEuclidienne());

        assertEquals(1, kVoisins.size());

        LoadableData voisin = kVoisins.get(0);
        System.out.println(voisin);
        assertEquals(3.7, voisin.getAttributes()[0], 0.001);
        assertEquals(2.7, voisin.getAttributes()[1], 0.001);
        assertEquals(1.1, voisin.getAttributes()[2], 0.001);
        assertEquals(0.4, voisin.getAttributes()[3], 0.001);
    }



    @Test
    public void testKVoisins_distance_euclidienne_normalise() throws IOException, CsvRequiredFieldEmptyException {

        model.loadData(csvTemp);
        List<LoadableData> datas = model.getDatas();
        LoadableData data = new Iris(3.5,2.6,1.0,0.5);

        System.out.println(data);
        MethodKNN.updateModel(datas);
        List<LoadableData> kVoisins = MethodKNN.kVoisins(datas, data, 1, new DistanceEuclidienneNormalisee());

        assertEquals(1, kVoisins.size());

        LoadableData voisin = kVoisins.get(0);
        System.out.println(voisin);
        assertEquals(3.7, voisin.getAttributes()[0], 0.001);
        assertEquals(2.7, voisin.getAttributes()[1], 0.001);
        assertEquals(1.1, voisin.getAttributes()[2], 0.001);
        assertEquals(0.4, voisin.getAttributes()[3], 0.001);
    }

}
