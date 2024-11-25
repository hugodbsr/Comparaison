package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.model.LoadableData;

public class DistanceManhattan implements Distance{

    public double distance(LoadableData l1, LoadableData l2){
        double distance = 0;


        for(int i = 0 ;i<l1.getAttributes().length; i++){
            distance = distance + Math.abs(l1.getAttributes()[i]- l2.getAttributes()[i]);
        }

        return distance;
    }
}