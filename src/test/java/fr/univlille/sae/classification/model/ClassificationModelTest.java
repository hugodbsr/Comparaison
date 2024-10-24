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
        double[] coords = {5.1, 3.5, 1.4, 0.2};
        model.ajouterDonnee(coords);
        
        List<LoadableData> dataToClass = model.getDataToClass();
        assertEquals(1, dataToClass.size());
        assertNotNull(dataToClass.get(0).getClassification());
    }

    @Test
    void testAjouterDonneeInsuffisante() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            model.ajouterDonnee(5.1);
        });
        assertEquals(null, exception.getMessage());
    }

    @Test
    void testLoadData() throws IOException {
        File csvTemp = File.createTempFile("test", ".csv");
        String csvTest = "sepal_length,sepal_width,petal_length,petal_width,class\n" +
                         "5.1,3.5,1.4,0.2,Iris-setosa\n" +
                         "4.9,3.0,1.4,0.2,Iris-setosa\n";
        Files.write(Paths.get(csvTemp.getAbsolutePath()), csvTest.getBytes());

        model.loadData(csvTemp);

        List<LoadableData> datas = model.getDatas();
        assertEquals(2, datas.size());
        
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
