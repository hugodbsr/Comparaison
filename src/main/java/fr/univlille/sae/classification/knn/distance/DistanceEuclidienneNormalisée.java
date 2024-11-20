package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.knn.MethodKNN;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;

public class DistanceEuclidienneNormalisée implements Distance{
    @Override
    public double distance(LoadableData l1, LoadableData l2) {
        if(l1.getAttributes().length != l2.getAttributes().length) throw new IllegalArgumentException("Error while trying to get Distance : Attributes do not match");
        if(l1.getStringAttributes().length != l2.getStringAttributes().length) throw new IllegalArgumentException("Error while trying to get Distance : Attributes String do not match");

        double total = 0;

        double[] normaliseL1 = normalise(l1);
        double[] normaliseL2 = normalise(l2);
        for(int i = 0;i<normaliseL1.length;i++) {
            total += Math.pow(normaliseL2[i] - normaliseL1[i], 2);
        }
        //A Check
        for(int i = 0;i<l2.getStringAttributes().length;i++) {
            if(!l1.getStringAttributes()[i].equals(l2.getStringAttributes()[i])) total++;
        }

        return Math.sqrt(total);
    }

    private double[] normalise(LoadableData l) {
        double[] dataNormalise = new double[l.getAttributes().length];
        for(int i = 0;i<dataNormalise.length;i++) {
            dataNormalise[i] = (l.getAttributes()[i]-MethodKNN.minData[i])/ MethodKNN.amplitude[i];
        }

        return dataNormalise;
    }
}
