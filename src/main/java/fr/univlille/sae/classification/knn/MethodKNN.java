package fr.univlille.sae.classification.knn;

import fr.univlille.sae.classification.knn.distance.Distance;
import fr.univlille.sae.classification.knn.distance.DistanceEuclidienneNormalisee;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.LoadableData;


import java.io.File;
import java.util.*;

public class MethodKNN {


    private static final Random random = new Random();


    public static String path = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    public static double[] amplitude;
    public static double[] minData;
    public static double[] maxData;

    public MethodKNN(ClassificationModel model) {

        updateModel(model.getDatas());

    }


    public static void updateModel(List<LoadableData> datas) {
        if(datas.isEmpty()) return;
        minData = new double[datas.get(0).getAttributes().length];
        maxData = new double[datas.get(0).getAttributes().length];
        amplitude = new double[datas.get(0).getAttributes().length];
        for(LoadableData l :datas) {
            for(int i = 0; i<l.getAttributes().length; i++) {
                if(l.getAttributes()[i] < minData[i]) minData[i] = l.getAttributes()[i];
                if(l.getAttributes()[i] > maxData[i]) maxData[i] = l.getAttributes()[i];
            }
        }

        for(int i = 0; i<minData.length; i++) {
            amplitude[i] = maxData[i] - minData[i];
        }
    }

    public static List<LoadableData> kVoisins(List<LoadableData> datas, LoadableData data, int k, Distance distance) {

        // On recupere toutes les données
        List<LoadableData> voisins = new ArrayList<>(datas);
        // on retire la valeur dont on cherche les voisins
        voisins.remove(data);
        // On tri la liste en fonction de la distance entre chaque point et data
        voisins.sort(new DataComparator(distance, data));


        // On renvoie les k premier qui sont les k avec la plus petite distance a data
        return voisins.subList(0, k);

    }



    public static String estimateClass(List<LoadableData> datas, LoadableData data, int k, Distance distance) {

        // On recupere les K voisions  de data.
        List<LoadableData> kVoisins = MethodKNN.kVoisins(datas, data, k, distance);

       // System.out.println("Neighbours found :  " + kVoisins);

        // On compte le nombre de représentation de chaque class parmis les voisins
        Map<String, Integer> classOfNeighbours = new HashMap<>();
        for(LoadableData voisin : kVoisins) {
            int newValue =  ((classOfNeighbours.get(voisin.getClassification()) == null) ? 0 : classOfNeighbours.get(voisin.getClassification()) )+ 1;
            classOfNeighbours.put(voisin.getClassification(), newValue);
        }
        // On recupere la classe la plus repésenté parmis les voisins (au hasard si egalité entre 2)
        String currentClass = kVoisins.get(0).getClassification();
        for(String classification : classOfNeighbours.keySet()) {
            if(classOfNeighbours.get(classification) > classOfNeighbours.get(currentClass)) {
                currentClass = classification;
            }else if (classOfNeighbours.get(classification).equals(classOfNeighbours.get(currentClass))) {
                if(random.nextInt(2) == 1) currentClass = classification;
            }
        }

      //  System.out.println("Estimate class = " + currentClass);
        return currentClass;
    }


    public static int bestK(List<LoadableData> datas, Distance distance) {
        //ToDO Juste pour eviter d'avoir k = 35 je limite la taille max de K. Je vais chercher si y'a une methode particuliere pour limiter sa taille
        int maxK = (int) (Math.sqrt(datas.size())/2 *2);
        System.out.println("Max k: " + maxK);

        Map<Integer, Double> results = new HashMap<>();
        // Pour chaque valeur impaire possible de K, on calcul la robustesse (le taux de reussite) de l'algorithme.
        for(int i =1; i<maxK; i = i +2) {
            results.put(i, robustesse(datas, i, distance, 0.2));
        }

        System.out.println(results);

        // On return le K ayant le meilleur taux de reussite ( ou l'un des K si egalités).
        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();

    }


    public static double robustesse(List<LoadableData> datas, int k, Distance distance, double testPart) {

        int totalFind = 0;
        int totalTry = 0;

        // On calcul la robusstesse en utilisant testPart% du fichier de base comme donnée a tester.
        int partSize = (int) (datas.size() * testPart);

        List<LoadableData> trainingData = new ArrayList<>(List.copyOf(datas.subList(0, datas.size()-partSize)));
        List<LoadableData> testData = List.copyOf(datas.subList(datas.size()-partSize, datas.size()));

        // On met a jour l'algo avec les nouvelles données (permet de re-calculer l'amplitude ainsi que les val max et min
        updateModel(trainingData);

        // On estime la classe chaque donnée de test, et on verifie si l'algo a bon
        for(LoadableData l : testData) {
            totalTry++;
            String baseClass = l.getClassification();
          //  System.out.println("Base class : " + baseClass);
          //  System.out.println("Base data: " + l);
            if(baseClass.equals(MethodKNN.estimateClass(trainingData,l, k, distance))) totalFind++;

        }

        // On return le taux de reussite
        System.out.println("total find: " +totalFind + " total try: " + totalTry);
        return (totalFind/(double) totalTry);
    }

    public static void main(String[] args) {

        //Test de la robustesse et du meillleur K

        ClassificationModel model = ClassificationModel.getClassificationModel();

        model.setType(DataType.POKEMON);
        model.loadData(new File(path+"data/pokemon_train.csv"));
        MethodKNN.updateModel(model.getDatas());
        System.out.println();

        List<LoadableData> datas = ClassificationModel.getClassificationModel().getDatas();
        // On mélange les données pour tester sur differentes variétes car le fichier de base est trié.
        Collections.shuffle(datas);

        System.out.println("Search best k");

        // On cherche le meilleure K
        int bestK = MethodKNN.bestK(datas, new DistanceEuclidienneNormalisee());
        System.out.println(bestK);

        // Puis on clacul la robustesse avec le K trouvé
        System.out.println(MethodKNN.robustesse( datas, bestK, new DistanceEuclidienneNormalisee(), 0.2));


        }






}
