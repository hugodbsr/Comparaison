package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.univlille.sae.classification.utils.Observable;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ClassificationModel extends Observable {


    private List<LoadableData> datas;
    private List<LoadableData> dataToClass;

    private DataType type;

    public ClassificationModel() {
        this(DataType.IRIS);
    }

    public ClassificationModel(DataType type) {
        this.datas = new ArrayList<>();
        this.dataToClass = new ArrayList<>();
        this.type = type;
    }


    /**
     * Ajoute un point au nuage de points avec toutes les données de ce point
     * @param coords toutes les données du points
     * @throws IllegalArgumentException Exception levée si le nombre de parametres est insuffisant pour creer un point du type du model
     */
    private void ajouterDonnee(double... coords) {
        LoadableData newData = PointFactory.createPoint(type, coords);
        this.dataToClass.add(newData);
        notifyObservers(newData);
    }


    /**
     * TODO
     * @param file
     */
    private void loadData(File file) throws IOException {
        this.datas = new CsvToBeanBuilder<LoadableData>(Files.newBufferedReader(file.toPath()))
                .withSeparator(',')
                .withType(Iris.class)
                .build().parse();
        Set<String> types = new HashSet<>();
        for (LoadableData d : datas) {
            types.add(d.getClassification());
        }

        LoadableData.setClassificationTypes(types);
        notifyObservers();
    }

    /**
     * TODO
     * @param data
     */
    private void classifierDonnee(LoadableData data) {

        List<String> classes = new ArrayList<>(data.getClassificationTypes());
        Random rdm = new Random();
        data.setClassification(classes.get(rdm.nextInt(classes.size())));
        notifyObservers(data);

    }
}
