package fr.univlille.sae.classification.knn;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fr.univlille.sae.classification.knn.distance.*;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.LoadableData;


import javax.xml.crypto.Data;
import java.io.File;
import java.util.*;

public class MethodKNN {

    // name,attack,base_egg_steps,capture_rate,defense,experience_growth,hp,sp_attack,sp_defense,type1,type2,speed,is_legendary
    // Test,45,5800,240.0,50,800000,65,55,65,normal,flying,1.5,False

    private static final Random random = new Random();


    public static String path = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    public static double[] amplitude;
    public static double[] minData;
    public static double[] maxData;

    public MethodKNN(ClassificationModel model) {

        updateModel(model.getDatas());

    }


    /**
     * Permet de mettre a jour les données de l'algorithme. Recalcul les amplitudes et les min/max des données
     * @param datas Les données sur lequel l'algorithme doit travailler
     */
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

    /**
     * Permet de recuperer les K-voisins les plus proches d'une données dans un jeu de données
     * en fonction d'une Distance.
     * @param datas Le jeu de données
     * @param data  La donnée avec laquelle calculer la distance
     * @param k     Le nombre de voisins a recupérer
     * @param distance
     * @return
     */
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
        System.out.println("Neighbours: " + kVoisins);

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
        int maxK = (int) (Math.sqrt(datas.size()));
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



        double taux = 0;

        for(int i = 0; i<(int)1/testPart; i++) {

            int totalFind = 0;
            int totalTry = 0;

            // On calcul la robusstesse en utilisant testPart% du fichier de base comme donnée a tester.
            int partSize = (int) (datas.size() * testPart);
            List<LoadableData> testData = List.copyOf(datas.subList(i*partSize, (i*partSize)+partSize));
            List<LoadableData> trainingData = new ArrayList<>(List.copyOf(datas));
            trainingData.removeAll(testData);

            // On met a jour l'algo avec les nouvelles données (permet de re-calculer l'amplitude ainsi que les val max et min
            updateModel(trainingData);

            // On estime la classe chaque donnée de test, et on verifie si l'algo a bon
            for(LoadableData l : testData) {
                totalTry++;
                System.out.println(l);
                String baseClass = l.getClassification();
                //  System.out.println("Base class : " + baseClass);
                //  System.out.println("Base data: " + l);
                if(baseClass.equals(MethodKNN.estimateClass(trainingData,l, k, distance))) totalFind++;

            }


            // On affiche le taux de reussite a chaque tour
            System.out.println("total find: " +totalFind + " total try: " + totalTry);
            taux += (totalFind/(double) totalTry);

        }



        return taux/(1/testPart);
    }

    public static void main(String[] args) throws CsvRequiredFieldEmptyException {

        //Test de la robustesse et du meillleur K

        ClassificationModel model = ClassificationModel.getClassificationModel();

        model.setType(DataType.POKEMON);
        model.loadData(new File(path+"data/pokemon_train.csv"));
        MethodKNN.updateModel(model.getDatas());
        System.out.println();

        List<LoadableData> datas = ClassificationModel.getClassificationModel().getDatas();

        for(int i = 0; i<1; i++) {
            System.out.println("Search best k");

            // On cherche le meilleure K
            int bestK = MethodKNN.bestK(datas, new DistanceEuclidienneNormalisee());
            System.out.println(bestK);

            // Puis on clacul la robustesse avec le K trouvé
            System.out.println(MethodKNN.robustesse( datas, bestK, new DistanceEuclidienneNormalisee(), 0.2));

        }



        }






}
