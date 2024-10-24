package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.univlille.sae.classification.utils.Observable;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;


public class ClassificationModel extends Observable {


    private List<LoadableData> datas;
    private List<LoadableData> dataToClass;

    private DataType type;

    private static ClassificationModel model;


    /**
     * Renvoie une instance unique du model. Par default le type de ce modele est Iris.
     * Modifier en .setType(DataType).
     * @return l'instance du model
     */
    public static ClassificationModel getClassificationModel() {
        if(model == null) model = new ClassificationModel();
        return model;
    }


    private ClassificationModel() {
        this(DataType.IRIS);
    }

    private ClassificationModel(DataType type) {
        this.datas = new ArrayList<>();
        this.dataToClass = new CopyOnWriteArrayList<>();
        this.type = type;
    }


    /**
     * Ajoute un point au nuage de points avec toutes les données de ce point
     * @param coords toutes les données du points
     * @throws IllegalArgumentException Exception levée si le nombre de parametres est insuffisant pour creer un point du type du model
     */
    public void ajouterDonnee(double... coords) {
        LoadableData newData = PointFactory.createPoint(type, coords);
        this.dataToClass.add(newData);
        notifyObservers(newData);
    }


    /**
     * TODO
     * @param file
     */
    public void loadData(File file) throws IOException {
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


    public void classifierDonnees() {
        dataToClass.forEach(this::classifierDonnee);
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
        dataToClass.remove(data);

    }


    public void setType(DataType type) {
        this.type = type;
    }


    public List<LoadableData> getDatas() {
        return datas;
    }

    public List<LoadableData> getDataToClass() {
        return dataToClass;
    }

    public DataType getType() {
        return type;
    }
}
