package fr.univlille.sae.classification.model;

import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fr.univlille.sae.classification.knn.DataComparator;
import fr.univlille.sae.classification.knn.distance.DistanceEuclidienne;
import fr.univlille.sae.classification.knn.distance.DistanceManhattan;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ClassificationModelTest {

    private ClassificationModel model;
    private File csvTemp;
    private String csvTest;

    private File errorCsv;
    private String errorCsvTest;

    private String path = System.getProperty("user.dir") + File.separator + "res" + File.separator;


    @BeforeEach
    void setUp() throws IOException {
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

        errorCsv = File.createTempFile("error_test", ".csv");
        errorCsvTest = "\"sepal.length\",\"sepal.width\",\"petal.length\",\"petal.width\",\"variety\"\n" +
                "4.1,sqd,1.4,0.2,\"Setosa\"\n" +
                "4.1,3.1,1.5,\"Setosa\"\n" +
                "3.9,3.6,1.6,0.7,\"Setosa\"\n" +
                "3.7,2.7,qsdsq,0.4,\"Setosa\"\n" +
                "4.7,3.9,1.9,0.3,\"Setosa\"\n" +
                "4.9,3.2,2.1,0.4,\"Setosa\"\n";

        Files.write(Paths.get(errorCsv.getAbsolutePath()), errorCsvTest.getBytes());

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
        assertEquals(6, datas.size());
        assertEquals(0, model.getDataToClass().size());


        csvTemp.delete();
    }

    @Test
    public void test_load_data_with_errors() {

        RuntimeException e = assertThrows(RuntimeException.class, () -> {
            model.loadData(errorCsv);
        });

        assertTrue(e.getCause() instanceof CsvRequiredFieldEmptyException);



    }

    @Test
    void testClassifierDonnees() throws CsvRequiredFieldEmptyException {
        model.loadData(csvTemp);

        model.ajouterDonnee(5.1, 3.5, 1.4, 0.2);
        model.setK(3);
        model.classifierDonnees();

        model.ajouterDonnee(4.9, 3.0, 1.4, 0.2);
        assertEquals(true, model.getDataToClass().get(model.getDataToClass().keySet().toArray()[0]));
        assertEquals(false, model.getDataToClass().get(model.getDataToClass().keySet().toArray()[1]));
    }

    @Test
    void testSetType() {
        model.setType(DataType.IRIS);
        assertEquals(DataType.IRIS, model.getType());
    }

    @Test
    public void test_change_model_datatype() throws CsvRequiredFieldEmptyException {
        model.setType(DataType.POKEMON);
        model.loadData(new File(path + "data/pokemon_train.csv"));
        assertEquals(DataType.POKEMON, model.getType());
        assertFalse(model.getDatas().isEmpty());
    }


    @Test
    public void test_changing_k() {
        // verifie que le k par default est bien 1
        assertEquals(1, model.getK());

        model.setK(3);
        model.setKOptimal(6);

        assertEquals(3, model.getK());
        assertEquals(6, model.getkOptimal());
    }

    @Test
    public void test_chaning_distance() {

        //Verifie que le distance par default n'est pas nul, mais bien Euclidiene
        assertEquals(DistanceEuclidienne.class, model.getDistance().getClass());

        model.setDistance(new DistanceManhattan());

        assertEquals(DistanceManhattan.class, model.getDistance().getClass());

    }

    @Test
    public void test_loadabledata_initialize_all_classifications() throws IllegalAccessException, CsvRequiredFieldEmptyException {

        assertTrue(LoadableData.getClassificationTypes().isEmpty());
        model.loadData(csvTemp);
        LoadableData.setClassificationTypes(model.getDatas());
        assertFalse(LoadableData.getClassificationTypes().isEmpty());
    }

    @Test
    public void test_set_global_classification_attribute_throw_exception() throws CsvRequiredFieldEmptyException {

        //On load des pokemons

        model.setType(DataType.POKEMON);
        model.loadData(new File(path + "data/pokemon_train.csv"));

        LoadableData data = model.getDatas().get(0);
        // on met le type Name (0)
        assertThrows(IllegalArgumentException.class, () -> {
            data.setClassificationType(2);
        });

        // on met le type sur un attribut inexistant
        assertThrows(IllegalArgumentException.class, () -> {
            data.setClassificationType(30);
        });

        // On met sur isLegendary
        assertDoesNotThrow(() -> {
            data.setClassificationType(12);
        });

    }


}
