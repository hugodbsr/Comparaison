package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.knn.MethodKNN;

import fr.univlille.sae.classification.model.LoadableData;

public class DistanceManhattanNormalisée implements Distance{

    @Override
    public double distance(LoadableData l1, LoadableData l2) {
        double distance = 0;



        for(int i = 0 ;i<l1.getAttributes().length; i++){
            double dPoids = (Math.abs(l1.getAttributes()[i]- l2.getAttributes()[i])- MethodeKnn.minData[i])/MethodeKnn.amplitude[i];
            distance = distance + dPoids;

        }
        return distance;
    }


}