package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fr.univlille.sae.classification.knn.MethodKNN;
import fr.univlille.sae.classification.knn.distance.Distance;
import fr.univlille.sae.classification.knn.distance.DistanceEuclidienne;
import fr.univlille.sae.classification.knn.distance.DistanceManhattan;
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
    /**
     * Données du modèle.
     */
    private List<LoadableData> datas;
    private final Map<LoadableData, Boolean> dataToClass;

    /**
     * Type de données.
     */
    private DataType type;
    private static ClassificationModel model;
    private Distance distance;
    /**
     * Valeurs de k liées à l'algorithme KNN.
     */
    private int kOptimal;
    private int k;

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
        this.dataToClass = new ConcurrentHashMap<>();
        this.type = type;
        this.kOptimal = 0;
        this.k = 1;
        this.distance =  new DistanceEuclidienne();
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
    public void loadData(File file) throws CsvRequiredFieldEmptyException, CsvBadConverterException {
        try {
            this.dataToClass.clear();

            this.datas = new CsvToBeanBuilder<LoadableData>(Files.newBufferedReader(file.toPath()))
                    .withSeparator(',')
                    .withType(type.getClazz())
                    .build().parse();


            Collections.shuffle(datas);


            LoadableData.setClassificationTypes(getDatas());
            notifyObservers();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des données : " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
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
        this.dataToClass.remove(data);
        try {
            data.setClassification(MethodKNN.estimateClass(datas, data, k, distance));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
        this.kOptimal = 0;
    }

    public Distance getDistance() {
        return distance;
    }

    public int getkOptimal() {
        return kOptimal;
    }

    public void setKOptimal(int kOptimal) {
        this.kOptimal = kOptimal;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
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
