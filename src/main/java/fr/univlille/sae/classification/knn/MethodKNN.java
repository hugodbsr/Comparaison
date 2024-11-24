package fr.univlille.sae.classification.knn;

import fr.univlille.sae.classification.knn.distance.Distance;
import fr.univlille.sae.classification.knn.distance.DistanceEuclidienne;
import fr.univlille.sae.classification.knn.distance.DistanceEuclidienneNormalisée;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.LoadableData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class MethodKNN {


    private static Random random = new Random();


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



    public static String estimateClass(List<LoadableData> datas, LoadableData data, int k, Distance distance) {

        List<LoadableData> kVoisins = MethodKNN.kVoisins(datas, data, k, distance);

        Map<String, Integer> classOfNeighbours = new HashMap<>();
        for(LoadableData voisin : kVoisins) {
            int newValue =  ((classOfNeighbours.get(voisin.getClassification()) == null) ? 0 : classOfNeighbours.get(voisin.getClassification()) )+ 1;
            classOfNeighbours.put(voisin.getClassification(), newValue);
        }
        String currentClass = kVoisins.get(0).getClassification();
        System.out.println("Classes: " + classOfNeighbours.toString());
        for(String classification : classOfNeighbours.keySet()) {
            if(classOfNeighbours.get(classification) > classOfNeighbours.get(currentClass)) {
                currentClass = classification;
            }else if (classOfNeighbours.get(classification) == classOfNeighbours.get(currentClass)) {
                if(random.nextInt(2) == 1) currentClass = classification;
            }
        }

        return currentClass;
    }


    public static double robustesse(int k, Distance distance) {

        int totalFind = 0;
        int totalTry = 0;
        List<LoadableData> datas = ClassificationModel.getClassificationModel().getDatas();
        int partSize = datas.size()/10;
        datas = datas.subList(datas.size()-partSize, datas.size());

        for(LoadableData l : datas) {
            totalTry++;
            String baseClass = l.getClassification();
            System.out.println("Base Class: " + baseClass);
            if(baseClass.equals(MethodKNN.estimateClass(datas,l, k, distance))) totalFind++;

        }
        System.out.println("total find: " +totalFind + " total try: " + totalTry);
        return (totalFind/(double) totalTry);
    }

    public static void main(String[] args) {


        ClassificationModel model = ClassificationModel.getClassificationModel();
        model.setType(DataType.IRIS);
        model.loadData(new File(path+"data/iris.csv"), "Iris");
        System.out.println();

        System.out.println(MethodKNN.robustesse(12, new DistanceEuclidienneNormalisée()));
        System.out.println(MethodKNN.robustesse(3, new DistanceEuclidienne()));
        System.out.println(MethodKNN.robustesse(3, new DistanceEuclidienneNormalisée()));
    }






}
