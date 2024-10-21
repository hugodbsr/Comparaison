package fr.univlille.sae.classification.model;

import java.util.Set;

public abstract class LoadableData {

    private static Set<String> classificationTypes;

    private String classification;

    protected LoadableData() {

    }

    public void addClassification(String classificationType) {
        this.classificationTypes.add(classificationType);
    }

    public String getClassification() {
        return this.classification;
    }

    public static Set<String> getClassificationTypes() {
        return classificationTypes;
    }

    public static void setClassificationTypes(Set<String> classificationTypes) {
        LoadableData.classificationTypes = classificationTypes;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public abstract String[] getAttributesName();

}
