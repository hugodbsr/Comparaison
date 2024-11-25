package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.model.LoadableData;

public class DistanceEuclidienne implements Distance {


    @Override
    public double distance(LoadableData l1, LoadableData l2) {
        if(l1.getAttributes().length != l2.getAttributes().length) throw new IllegalArgumentException("Error while trying to get Distance : Attributes do not match");
        if(l1.getStringAttributes().length != l2.getStringAttributes().length) throw new IllegalArgumentException("Error while trying to get Distance : Attributes String do not match");

        double total = 0;

        for(int i = 0;i<l1.getAttributes().length;i++) {
            total += Math.pow(l2.getAttributes()[i] - l1.getAttributes()[i], 2);
        }
        //A Check
        for(int i = 0;i<l2.getStringAttributes().length;i++) {
            if(!l1.getStringAttributes()[i].equals(l2.getStringAttributes()[i])) total++;
        }

        return Math.sqrt(total);
    }
}
