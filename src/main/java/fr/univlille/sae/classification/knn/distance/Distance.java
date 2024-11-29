package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.model.LoadableData;

public interface Distance {

    /**
     * Calcul de la distance.
     * @param l1 Point 1
     * @param l2 Point 2
     * @return Distance calculée entre les 2 points
     */
    double distance(LoadableData l1, LoadableData l2);

    /**
     * Renvoie l'algorithme de distance selon le nom.
     * @param name Nom de l'algorithme
     * @return Classe liée à l'algorithme de distance
     */
    static Distance getByName(String name){
        switch (name) {
            case "Euclidienne Normalisée":
                return new DistanceEuclidienneNormalisee();
            case "Manhattan":
                return new DistanceManhattan();
            case "Manhattan Normalisée":
                return new DistanceManhattanNormalisee();
            default:
                return new DistanceEuclidienne();
        }

    }

    /**
     * Renvoie le nom de l'algorithme utilisé.
     * @param distance Algorithme de distance
     * @return Chaîne de caractères correspondante
     */
    static String getDistanceName(Distance distance){
        if (distance instanceof DistanceEuclidienneNormalisee) {
            return "Euclidienne Normalisee";
        }else if (distance instanceof DistanceManhattan){
            return "Manhattan";
        }else if (distance instanceof DistanceManhattanNormalisee){
            return "ManhattanNormalisee";
        }else {
            return "Euclidienne";
        }
    }

}
