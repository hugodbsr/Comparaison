package fr.univlille.sae.classification.knn.distance;

import fr.univlille.sae.classification.model.LoadableData;

public interface Distance {


    double distance(LoadableData l1, LoadableData l2);

}
