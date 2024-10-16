package fr.univlille.sae.classification.model;

import java.util.Set;

public class LoadableData {

    private static Set<String> classificationTypes;

    private String classification;


    public LoadableData() {

    }
    public String getClassification() {
        return this.classification;
    }
    Set<String> getClassificationTypes() {
        return classificationTypes;
    }
    void setClassification(String classification) {
        this.classification = classification;
    }

}
