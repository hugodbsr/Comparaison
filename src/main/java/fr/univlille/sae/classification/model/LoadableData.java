package fr.univlille.sae.classification.model;

import java.util.Set;

public abstract class LoadableData {

    private static Set<String> classificationTypes;




    protected LoadableData() {

    }

    public abstract String getClassification() ;

    public static Set<String> getClassificationTypes() {
        return classificationTypes;
    }

    public static void setClassificationTypes(Set<String> classificationTypes) {
        LoadableData.classificationTypes = classificationTypes;
    }

    public abstract void setClassification(String classification);

    public abstract String[] getAttributesName();

}
