package fr.univlille.sae.classification.model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe abstraite représentant des données pouvant être chargées.
 */
public abstract class LoadableData {

    private static Set<String> classificationTypes;

    private static Map<String, Color> classification = new HashMap<>() ;

    protected static int classificationType = 1;

    /**
     * Constructeur par défaut.
     */
    protected LoadableData() {

    }

    /**
     * Renvoie la classification de l'objet.
     * @return classification sous forme de chaîne.
     */
    public abstract String getClassification() throws IllegalAccessException;

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

    public static void setClassificationTypeGlobal(int classificationType) throws IllegalArgumentException, IllegalAccessException {
        LoadableData.classificationType = classificationType;
    }

    public abstract void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException;

    /**
     * Définit les types de classification disponibles.
      */
    public static void setClassificationTypes(List<LoadableData> datas) throws IllegalAccessException {

        Set<String> types = new HashSet<>();
            for (LoadableData d : datas) {
                types.add(d.getClassification());
            }


        classificationTypes = types;

        LoadableData.classification.clear();
        int nbOfColors = classificationTypes.size() + 1;
        int nb = 0;
        for(String s : classificationTypes) {
            // Génération de couleurs avec une plage évitant le blanc

            LoadableData.classification.put(s, getColor(nb++, nbOfColors));
        }

        LoadableData.classification.put("undefined", getColor(nb,nbOfColors));
    }


    private static Color getColor(int nb, int totalColors) {
        // Ratio pour répartir les couleurs uniformément
        double ratio = (double) nb / (double) totalColors;

        // Utilisation de fonctions trigonométriques pour des transitions douces
        double red = 0.5 + 0.4 * Math.sin(2 * Math.PI * ratio); // Oscille entre 0.1 et 0.9
        double green = 0.5 + 0.4 * Math.sin(2 * Math.PI * ratio + Math.PI / 3); // Décalage de phase
        double blue = 0.5 + 0.4 * Math.sin(2 * Math.PI * ratio + 2 * Math.PI / 3); // Décalage de phase

        // Réduction de la luminosité pour éviter le blanc et gris clair
        double maxComponent = Math.max(red, Math.max(green, blue));
        if (maxComponent > 0.8) {
            red *= 0.8 / maxComponent;
            green *= 0.8 / maxComponent;
            blue *= 0.8 / maxComponent;
        }

        // Conversion en objet Color
        return Color.color(red, green, blue);
    }

   /* private static Color getColor(int i) {
        double ratio = (double) i / classificationTypes.size();

        // Réduire les composantes pour éviter les tons clairs
        double red = 0.2 + 0.6 * ratio; // Entre 0.2 et 0.8
        double green = 0.8 - 0.6 * ratio; // Entre 0.8 et 0.2
        double blue = 0.5 + 0.3 * Math.sin(ratio * Math.PI); // Entre 0.5 et 0.8

        return Color.color(red, green, blue);
    }


    */

    public abstract Map<String, Object> getClassifiedAttributes();

    public abstract int getClassificationType() ;

    /**
     * Définit la classification de l'objet.
     * @param classification classification à définir.
     */
    public abstract void setClassification(String classification) throws IllegalAccessException;

    public abstract Map<String, Object> getAttributesNames();



    public abstract double[] getAttributes();

    public abstract String[] getStringAttributes();

}
