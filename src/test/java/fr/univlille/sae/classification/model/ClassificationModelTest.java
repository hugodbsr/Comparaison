package fr.univlille.sae.classification.model;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ClassificationModelTest {

    private ClassificationModel model;
    private File csvTemp;
    private String csvTest;


    @BeforeEach
    void setUp() throws IOException {
        model = ClassificationModel.getClassificationModel();
        csvTemp = File.createTempFile("test", ".csv");
        csvTest = "\"sepal.length\",\"sepal.width\",\"petal.length\",\"petal.width\",\"variety\"\n" +
                "5.1,3.5,1.4,0.2,\"Setosa\"\n" +
                "4.9,3.0,1.4,0.2,\"Setosa\"\n";
        Files.write(Paths.get(csvTemp.getAbsolutePath()), csvTest.getBytes());

    }

    @Test
    void testSingletonInstance() {
        ClassificationModel anotherModel = ClassificationModel.getClassificationModel();
        assertSame(model, anotherModel);
    }

    @Test
    void testAjouterDonnee() {

        Map<LoadableData, Boolean> dataToClass = model.getDataToClass();
        dataToClass.clear();
        model.ajouterDonnee(5.1, 3.5, 1.4, 0.2);

        assertEquals(1, dataToClass.size());
    }

    @Test
    void testAjouterDonneeInsuffisante() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            model.ajouterDonnee(5.1);
        });

    }

    @Test
    void testLoadData() throws IOException, CsvRequiredFieldEmptyException, IllegalAccessException {

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
    void testClassifierDonnees() throws CsvRequiredFieldEmptyException {
        model.loadData(csvTemp);

        model.ajouterDonnee(5.1, 3.5, 1.4, 0.2);
        
        model.classifierDonnees();

        model.ajouterDonnee(4.9, 3.0, 1.4, 0.2);
        assertEquals(false, model.getDataToClass().get(model.getDataToClass().keySet().toArray()[0]));
        assertEquals(true, model.getDataToClass().get(model.getDataToClass().keySet().toArray()[1]));
    }

    @Test
    void testSetType() {
        model.setType(DataType.IRIS);
        assertEquals(DataType.IRIS, model.getType());
    }
}
