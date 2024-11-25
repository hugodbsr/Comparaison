package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.univlille.sae.classification.knn.MethodKNN;
import fr.univlille.sae.classification.knn.distance.Distance;
import fr.univlille.sae.classification.utils.Observable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Modèle de classification des données.
 * Gère le chargement, l'ajout et la classification des données.
 */
public class ClassificationModel extends Observable {

    private List<LoadableData> datas;
    private final Map<LoadableData, Boolean> dataToClass;

    private DataType type;

    private static ClassificationModel model;

    private Distance distance;
    private int kOptimal;

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
        this(DataType.POKEMON);
    }

    /**
     * Constructeur privé avec type de données.
     * @param type type de données à utiliser pour la classification.
     */
    private ClassificationModel(DataType type) {
        this.datas = new ArrayList<>();
        this.dataToClass = new ConcurrentHashMap<>();
        this.type = type;
    }
    /**
     * Ajoute un point au nuage de points avec toutes les données de ce point.
     * @param coords toutes les données du point.
     * @throws IllegalArgumentException si le nombre de coordonnées ne correspond pas au type spécifié.
     */
    public void ajouterDonnee(Object... coords) throws IllegalArgumentException {
        LoadableData newData = PointFactory.createPoint(type, coords);
        this.dataToClass.put(newData, false);
        notifyObservers(newData);
    }


    public void ajouterDonnee(LoadableData loadableData)  {
        this.dataToClass.put(loadableData, false);
    }

    /**
     * Charge les données à partir d'un fichier CSV.
     * @param file fichier contenant les données à charger.
     */
    public void loadData(File file) {
        try {
            this.datas = new CsvToBeanBuilder<LoadableData>(Files.newBufferedReader(file.toPath()))
                    .withSeparator(',')
                    .withType(type.getClazz())
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
        dataToClass.keySet().forEach(this::classifierDonnee);
    }

    /**
     * Classifie une donnée spécifique.
     * Attribue une classification aléatoire à la donnée fournie et notifie les observateurs.
     * @param data donnée à classifier.
     */
    public void classifierDonnee(LoadableData data) {
        if(dataToClass.get(data) != null && dataToClass.get(data)) return;
        List<String> classes = new ArrayList<>(LoadableData.getClassificationTypes());



        data.setClassification(MethodKNN.estimateClass(datas, data, 1, distance));
        notifyObservers(data);
        dataToClass.put(data, true);
    }

    /**
     * Définit le type de données à classifier.
     * @param type type de données.
     */
    public void setType(DataType type) {
        this.type = type;
    }


    public void setDatas(List<LoadableData> datas) {
        this.datas = datas;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Distance getDistance() {
        return distance;
    }

    public int getkOptimal() {
        return kOptimal;
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
    public Map<LoadableData, Boolean> getDataToClass() {
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
