package fr.univlille.sae.classification.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassificationModelTest {

    private ClassificationModel model;

    @BeforeEach
    void setUp() {
        model = ClassificationModel.getClassificationModel();
    }

    @Test
    void testSingletonInstance() {
        ClassificationModel anotherModel = ClassificationModel.getClassificationModel();
        assertSame(model, anotherModel);
    }

    @Test
    void testAjouterDonnee() {

        model.ajouterDonnee(5.1, 3.5, 1.4, 0.2);
        
        List<LoadableData> dataToClass = model.getDataToClass();
        assertEquals(1, dataToClass.size());
        assertNotNull(dataToClass.get(0).getClassification());
    }

    @Test
    void testAjouterDonneeInsuffisante() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            model.ajouterDonnee(5.1);
        });

    }

    @Test
    void testLoadData() throws IOException {
        File csvTemp = File.createTempFile("test", ".csv");
        String csvTest = "\"sepal.length\",\"sepal.width\",\"petal.length\",\"petal.width\",\"variety\"\n" +
                         "5.1,3.5,1.4,0.2,\"Setosa\"\n" +
                         "4.9,3.0,1.4,0.2,\"Setosa\"\n";
        Files.write(Paths.get(csvTemp.getAbsolutePath()), csvTest.getBytes());

        model.loadData(csvTemp);

        List<LoadableData> datas = model.getDatas();
        assertEquals(2, datas.size());

        Iris i1 = (Iris) datas.get(0);
        assertEquals(5.1, i1.getSepalLength());
        assertEquals(3.5, i1.getSepalWidth());
        assertEquals(1.4, i1.getPetalLength());
        assertEquals(0.2, i1.getPetalWidth());
        assertEquals("Setosa", i1.getClassification());

        Iris i2 = (Iris) datas.get(1);
        assertEquals(4.9, i2.getSepalLength());
        assertEquals(3.0, i2.getSepalWidth());
        assertEquals(1.4, i2.getPetalLength());
        assertEquals(0.2, i2.getPetalWidth());
        assertEquals("Setosa", i1.getClassification());
        
        csvTemp.delete();
    }

    @Test
    void testClassifierDonnees() {
        double[] coords1 = {5.1, 3.5, 1.4, 0.2};
        double[] coords2 = {4.9, 3.0, 1.4, 0.2};
        model.ajouterDonnee(coords1);
        model.ajouterDonnee(coords2);
        
        model.classifierDonnees();

        assertEquals(0, model.getDataToClass().size());
    }

    @Test
    void testSetType() {
        model.setType(DataType.IRIS);
        assertEquals(DataType.IRIS, model.getType());
    }
}
