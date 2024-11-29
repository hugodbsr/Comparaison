package fr.univlille.sae.classification.knn;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fr.univlille.sae.classification.knn.distance.*;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.LoadableData;


import java.io.File;
import java.util.*;

public class MethodKNN {




    public static String path = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    public static double[] amplitude;
    public static double[] minData;
    public static double[] maxData;

   private MethodKNN() {

   }


    /**
     * Permet de mettre a jour les données de l'algorithme. Recalcul les amplitudes et les min/max des données
     * @param datas Les données sur lequel l'algorithme doit travailler
     */
    public static void updateModel(List<LoadableData> datas) {
        if(datas.isEmpty()) return;

        int numAttributes = datas.get(0).getAttributes().length;
        minData = new double[numAttributes];
        maxData = new double[numAttributes];
        amplitude = new double[numAttributes];


        for(LoadableData l :datas) {
            double[] attributes = l.getAttributes();
            for(int i = 0; i<numAttributes; i++) {
                if(attributes[i] < minData[i]) minData[i] = attributes[i];
                if(attributes[i] > maxData[i]) maxData[i] = attributes[i];
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



    public static String estimateClass(List<LoadableData> datas, LoadableData data, int k, Distance distance) throws IllegalAccessException {

        // On recupere les K voisions  de data.
        List<LoadableData> kVoisins = MethodKNN.kVoisins(datas, data, k, distance);

        // On compte le nombre de représentation de chaque class parmis les voisins
        // Et on récupere la plus présente

        Map<String, Integer> classOfNeighbours = new HashMap<>();
        String currentClass = kVoisins.get(0).getClassification();



        for(LoadableData voisin : kVoisins) {
            int newValue =  ((classOfNeighbours.get(voisin.getClassification()) == null) ? 0 : classOfNeighbours.get(voisin.getClassification()) )+ 1;
            classOfNeighbours.put(voisin.getClassification(), newValue);
            // si la classe est plus presente que la classe acutelemnt majoritaire, on change la classe majoritaire.
            // Si il y'a egalité alors on garde la premiere trouvé
            if(classOfNeighbours.get(voisin.getClassification()) > classOfNeighbours.get(currentClass)) {
                currentClass = voisin.getClassification();
            }

        }

        return currentClass;
    }


    public static int bestK(List<LoadableData> datas, Distance distance) throws IllegalAccessException {
        // On borne le K pour eviter de trouver un K trop grand
        int maxK = (int) (Math.sqrt(datas.size()));
        System.out.println("Max k: " + maxK);

        int betK = 1;

        Map<Integer, Double> results = new LinkedHashMap<>();
        // Pour chaque valeur impaire possible de K, on calcul la robustesse (le taux de reussite) de l'algorithme.
        for(int i =1; i<maxK; i = i +2) {
            results.put(i, robustesse(datas, i, distance, 0.2));
            // On modifie le meilleur k si le taux est superieur au K precedent
            // Si egalité, on garde le premier trouvé
            if(results.get(i) > results.get(betK)) betK = i;
        }

        System.out.println("Results: " + results);

        return betK;

    }


    public static double robustesse(List<LoadableData> datas, int k, Distance distance, double testPart) throws IllegalAccessException {



        double taux = 0;

        for(int i = 0; i<1/testPart; i++) {

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
                String baseClass = l.getClassification();
                if(baseClass.equals(MethodKNN.estimateClass(trainingData,l, k, distance))) totalFind++;
            }


            // On affiche le taux de reussite a chaque tour
            System.out.println("total find: " +totalFind + " total try: " + totalTry);
            taux += (totalFind/(double) totalTry);

        }



        return taux/(1/testPart);
    }

    public static void main(String[] args) throws CsvRequiredFieldEmptyException, IllegalAccessException {

        //Test de la robustesse et du meillleur K

        ClassificationModel model = ClassificationModel.getClassificationModel();

        model.setType(DataType.POKEMON);
        model.loadData(new File(path+"data/pokemon_train.csv"));
        MethodKNN.updateModel(model.getDatas());
        System.out.println();

        // Permet de definir l'attribut sur lequel ont souhaite classifier:
        LoadableData.setClassificationTypeGlobal(12);

        List<LoadableData> datas = ClassificationModel.getClassificationModel().getDatas();
        // On mélange les données pour tester sur differentes variétes car le fichier de base est trié.
        Collections.shuffle(datas);

        for(int i = 0; i<1; i++) {
            System.out.println("Search best k");

            // On cherche le meilleure K
            int bestK = MethodKNN.bestK(datas, new DistanceManhattanNormalisee());
            System.out.println(bestK);

            // Puis on clacul la robustesse avec le K trouvé
            System.out.println(MethodKNN.robustesse( datas, bestK, new DistanceManhattanNormalisee(), 0.2));

        }



    }






}
