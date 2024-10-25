package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.univlille.sae.classification.utils.Observable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Modèle de classification des données.
 * Gère le chargement, l'ajout et la classification des données.
 */
public class ClassificationModel extends Observable {

    private List<LoadableData> datas;
    private List<LoadableData> dataToClass;

    private DataType type;

    private static ClassificationModel model;

    /**
     * Renvoie une instance unique du modèle. Par défaut, le type de ce modèle est Iris.
     * @return l'instance du modèle
     */
    public static ClassificationModel getClassificationModel() {
        if(model == null) model = new ClassificationModel();
        return model;
    }

    /**
     * Constructeur privé par défaut.
     * Initialise le modèle avec le type de données Iris.
     */
    private ClassificationModel() {
        this(DataType.IRIS);
    }

    /**
     * Constructeur privé avec type de données.
     * @param type type de données à utiliser pour la classification.
     */
    private ClassificationModel(DataType type) {
        this.datas = new ArrayList<>();
        this.dataToClass = new CopyOnWriteArrayList<>();
        this.type = type;
    }

    /**
     * Ajoute un point au nuage de points avec toutes les données de ce point.
     * @param coords toutes les données du point.
     */
    public void ajouterDonnee(double... coords) {
        LoadableData newData = PointFactory.createPoint(type, coords);
        this.dataToClass.add(newData);
        notifyObservers(newData);
    }

    /**
     * Charge les données à partir d'un fichier CSV.
     * @param file fichier contenant les données à charger.
     */
    public void loadData(File file) {
        try {
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
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des données : " + e.getMessage());
        }
    }

    /**
     * Classifie toutes les données à classifier.
     * Parcourt la liste des données à classifier et appelle la méthode pour chaque donnée.
     */
    public void classifierDonnees() {
        dataToClass.forEach(this::classifierDonnee);
    }

    /**
     * Classifie une donnée spécifique.
     * Attribue une classification aléatoire à la donnée fournie et notifie les observateurs.
     * @param data donnée à classifier.
     */
    private void classifierDonnee(LoadableData data) {
        List<String> classes = new ArrayList<>(LoadableData.getClassificationTypes());
        Random rdm = new Random();
        data.setClassification(classes.get(rdm.nextInt(classes.size())));
        notifyObservers(data);
        dataToClass.remove(data);
    }

    /**
     * Définit le type de données à classifier.
     * @param type type de données.
     */
    public void setType(DataType type) {
        this.type = type;
    }

    /**
     * Renvoie la liste des données chargées.
     * @return liste des données chargées.
     */
    public List<LoadableData> getDatas() {
        return datas;
    }

    /**
     * Renvoie la liste des données à classifier.
     * @return liste des données à classifier.
     */
    public List<LoadableData> getDataToClass() {
        return dataToClass;
    }

    /**
     * Renvoie le type de données actuellement défini.
     * @return type de données.
     */
    public DataType getType() {
        return type;
    }
}
