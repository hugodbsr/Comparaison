package fr.univlille.sae.classification.model;

import fr.univlille.sae.classification.utils.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ClassificationModel extends Observable {


    private List<LoadableData> datas;
    private List<LoadableData> dataToClass;

    public ClassificationModel() {
        this.datas = new ArrayList<>();

    }

    /**
     * TODO
     * @param x
     * @param y
     */
    private void ajouterDonnee(double x, double y) {

    }


    /**
     * TODO
     * @param file
     */
    private void loadData(File file) {

    }

    /**
     * TODO
     * @param data
     */
    private void classifierDonnee(LoadableData data) {

        List<String> classes = new ArrayList<>(data.getClassificationTypes());
        Random rdm = new Random();
        data.setClassification(classes.get(rdm.nextInt(classes.size())));

    }


}
