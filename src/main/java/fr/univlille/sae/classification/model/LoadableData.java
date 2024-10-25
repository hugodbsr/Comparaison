package fr.univlille.sae.classification.model;

import javafx.scene.paint.Color;

import java.util.Set;

public abstract class LoadableData {

    private static Set<String> classificationTypes;

    protected LoadableData() {

    }

    public void addClassification(String classificationType) {
        this.classificationTypes.add(classificationType);
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

    public abstract Color getColor();

    public abstract double getDataType(String axes);

}
