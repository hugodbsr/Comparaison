package fr.univlille.sae.classification.knn;

import fr.univlille.sae.classification.knn.distance.Distance;
import fr.univlille.sae.classification.model.LoadableData;

import java.util.Comparator;

public class DataComparator implements Comparator<LoadableData> {

    Distance distance;
    LoadableData data;
    public DataComparator(Distance distance, LoadableData data) {
        this.distance = distance;
        this.data = data;
    }

    @Override
    public int compare(LoadableData o1, LoadableData o2) {
        return Double.compare(distance.distance(data, o1), distance.distance(data, o2));
    }
}
