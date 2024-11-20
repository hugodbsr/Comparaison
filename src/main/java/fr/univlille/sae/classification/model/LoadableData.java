package fr.univlille.sae.classification.model;

import javafx.scene.paint.Color;

import java.util.Set;

/**
 * Classe abstraite représentant des données pouvant être chargées.
 */
public abstract class LoadableData {

    private static Set<String> classificationTypes;

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

    /**
     * Renvoie les noms des attributs de l'objet.
     * @return tableau de chaînes contenant les noms des attributs.
     */
    public abstract String[] getAttributesName();

    /**
     * Renvoie la couleur associée à l'objet.
     * @return couleur correspondant à la classification de l'objet.
     */
    public abstract Color getColor();

    /**
     * Renvoie la valeur des données en fonction de l'axe spécifié.
     * @param axes nom de l'axe pour lequel la valeur est requise.
     * @return valeur correspondante.
     */
    public abstract double getDataType(String axes);


    public abstract double[] getAttributes();

    public abstract String[] getStringAttributes();

}
