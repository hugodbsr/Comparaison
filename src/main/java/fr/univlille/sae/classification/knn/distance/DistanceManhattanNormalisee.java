package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.knn.MethodKNN;
import fr.univlille.sae.classification.model.LoadableData;

public class DistanceManhattanNormalisee implements Distance{

    /**
     * Calcul de la distance.
     * @param l1 Point 1
     * @param l2 Point 2
     * @return Distance calculée entre les 2 points
     */
    @Override
    public double distance(LoadableData l1, LoadableData l2) {
        double distance = 0;

        for(int i = 0 ;i<l1.getAttributes().length; i++){
            double dPoids = (Math.abs(l1.getAttributes()[i]- l2.getAttributes()[i])- MethodKNN.minData[i])/MethodKNN.amplitude[i];
            distance = distance + dPoids;

        }
        return distance;
    }


}