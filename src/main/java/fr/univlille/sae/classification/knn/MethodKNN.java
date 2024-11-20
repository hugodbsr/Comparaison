package fr.univlille.sae.classification.knn;

import fr.univlille.sae.classification.knn.distance.Distance;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodKNN {



    public static String path = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    public static double[] amplitude;
    public static double[] minData;
    public static double[] maxData;

    public MethodKNN(ClassificationModel model) {

        double[] minData = new double[model.getType().getArgumentSize()];
        double[] maxData = new double[model.getType().getArgumentSize()];
        amplitude = new double[model.getType().getArgumentSize()];
        for(LoadableData l : model.getDatas()) {
            for(int i = 0; i<l.getAttributes().length; i++) {
                    if(l.getAttributes()[i] < minData[i]) minData[i] = l.getAttributes()[i];
                    if(l.getAttributes()[i] > maxData[i]) maxData[i] = l.getAttributes()[i];
            }
        }

        for(int i = 0; i<model.getType().getArgumentSize(); i++) {
            amplitude[i] = maxData[i] - minData[i];
        }

    }


    public static List<LoadableData> kVoisins(List<LoadableData> datas, LoadableData data, int k, Distance distance) {

        List<LoadableData> voisins = new ArrayList<>(datas);
        voisins.remove(data);
        voisins.sort(new DataComparator(distance, data));

        return voisins.subList(0, k);

    }



    public static String estimateClass(LoadableData joueur) {


        return "";
    }






}
