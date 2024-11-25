package fr.univlille.sae.classification.knn;

import fr.univlille.sae.classification.knn.distance.DistanceEuclidienne;
import fr.univlille.sae.classification.knn.distance.DistanceEuclidienneNormalisee;
import fr.univlille.sae.classification.model.ClassificationModel;
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
    public void testKVoisins_distance_euclidienne() throws IOException {

        model.loadData(csvTemp);
        List<LoadableData> datas = model.getDatas();
        LoadableData first = datas.get(0);
        LoadableData second = datas.get(1);

        System.out.println(first);
        System.out.println(second);

        List<LoadableData> kVoisins = MethodKNN.kVoisins(datas, first, 1, new DistanceEuclidienne());

        assertEquals(second, kVoisins.get(0));
    }



    @Test
    public void testKVoisins_distance_euclidienne_normalise() throws IOException {

        model.loadData(csvTemp);
        List<LoadableData> datas = model.getDatas();
        LoadableData first = datas.get(0);
        LoadableData second = datas.get(1);

        System.out.println(first);
        System.out.println(second);

        List<LoadableData> kVoisins = MethodKNN.kVoisins(datas, first, 1, new DistanceEuclidienneNormalisee());

        assertEquals(second, kVoisins.get(0));
    }

}
