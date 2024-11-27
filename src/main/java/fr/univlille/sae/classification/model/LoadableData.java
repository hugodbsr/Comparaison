package fr.univlille.sae.classification.model;

import javafx.scene.paint.Color;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe abstraite représentant des données pouvant être chargées.
 */
public abstract class LoadableData {

    private static Set<String> classificationTypes;

    private static Map<String, Color> classification = new HashMap<>() ;

    /**
     * Constructeur par défaut.
     */
    protected LoadableData() {
    }

    /**
     * Renvoie la classification de l'objet.
     * @return classification sous forme de chaîne.
     */
    public abstract String getClassification();

    /**
     * Renvoie les types de classification définis.
     * @return ensemble de types de classification.
     */
    public static Set<String> getClassificationTypes() {
        return classificationTypes;
    }

    public static Map<String, Color> getClassifications() {
        return classification;
    }

    /**
     * Définit les types de classification disponibles.
     * @param classificationTypes ensemble de types de classification à définir.
     */
    public static void setClassificationTypes(Set<String> classificationTypes) {
        LoadableData.classificationTypes = classificationTypes;
    }

    /**
     * Définit la classification de l'objet.
     * @param classification classification à définir.
     */
    public abstract void setClassification(String classification);

    public abstract Map<String, Object> getAttributesNames();







    /**
     * Renvoie la valeur des données en fonction de l'axe spécifié.
     * @param axes nom de l'axe pour lequel la valeur est requise.
     * @return valeur correspondante.
     */
    public abstract double getDataType(String axes);


    public abstract double[] getAttributes();

    public abstract String[] getStringAttributes();

}
