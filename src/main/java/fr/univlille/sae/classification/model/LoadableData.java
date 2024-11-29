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
    /**
     * Ensemble des types de classification actuellement définis.
     */
    private static Set<String> classificationTypes;

    /**
     * Map contenant les classifications associées à leur couleur représentative.
     */
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

    /**
     * Renvoie une Map des classifications avec leur couleur réprésentative.
     * @return Map des classifications avec leur couleur
     */
    public static Map<String, Color> getClassifications() {
        return classification;
    }

    /**
     * Définit le type de classification global à utiliser.
     * @param classificationType Index du type de classification
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setClassificationTypeGlobal(int classificationType) throws IllegalArgumentException, IllegalAccessException {
        LoadableData.classificationType = classificationType;
    }

    /**
     * Définit le type de classification pour un objet spécifique.
     * @param classificationType Index du type de classification
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public abstract void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException;

    /**
     * Définit les types de classification disponibles.
     * @param datas Liste des objets
     * @throws IllegalAccessException
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

    /**
     * Génère une couleur unique pour chaque type de classification.
     * @param nb Numéro de la couleur
     * @param totalColors Nombre total de couleurs nécessaires
     * @return La couleur générée
     */
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

    /**
     * Renvoie une Map des attributs valides pour la classification.
     * @return Map des attributs avec leur nom et valeur
     */
    public abstract Map<String, Object> getClassifiedAttributes();

    /**
     *
     * @return Renvoie l'index du type de la classification.
     */
    public abstract int getClassificationType() ;

    /**
     * Définit la classification de l'objet.
     * @param classification classification à définir.
     */
    public abstract void setClassification(String classification) throws IllegalAccessException;

    /**
     * Renvoie les noms des attributs de l'objet.
     * @return Tableau de chaînes contenant les noms des attributs ainsi que leur variable
     */
    public abstract Map<String, Object> getAttributesNames();

    /**
     * Renvoie les attributs numériques.
     * @return Tableau des attributs numériques
     */
    public abstract double[] getAttributes();

    /**
     * Renvoie les attributs de type chaînes de caractères.
     * @return Tableau des attributs String
     */
    public abstract String[] getStringAttributes();

}
