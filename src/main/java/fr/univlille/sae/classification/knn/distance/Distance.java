package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.model.LoadableData;

public interface Distance {


    double distance(LoadableData l1, LoadableData l2);

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
