package fr.univlille.sae.classification.model;

import javafx.scene.paint.Color;

import java.util.Map;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
        LoadableData.classification.clear();

        int nb = 0;
        for(String s : classificationTypes) {
            // Génération de couleurs avec une plage évitant le blanc

            LoadableData.classification.put(s, getColor(nb++));
        }

        LoadableData.classification.put("undefined", getColor(nb));
    }

    private static Color getColor(int i) {
        double ratio = (double) i / classificationTypes.size();

        // Réduire les composantes pour éviter les tons clairs
        double red = 0.2 + 0.6 * ratio; // Entre 0.2 et 0.8
        double green = 0.8 - 0.6 * ratio; // Entre 0.8 et 0.2
        double blue = 0.5 + 0.3 * Math.sin(ratio * Math.PI); // Entre 0.5 et 0.8

        return Color.color(red, green, blue);
    }


    /**
     * Définit la classification de l'objet.
     * @param classification classification à définir.
     */
    public abstract void setClassification(String classification);

    public abstract Map<String, Object> getAttributesNames();



    public abstract double[] getAttributes();

    public abstract String[] getStringAttributes();

}
