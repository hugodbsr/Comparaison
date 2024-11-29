package fr.univlille.sae.classification.knn;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fr.univlille.sae.classification.knn.distance.Distance;
import fr.univlille.sae.classification.knn.distance.DistanceManhattanNormalisee;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.LoadableData;

import java.io.File;
import java.util.*;

public class MethodKNN {

    /**
     * Chemin du fichier de données.
     */
    public static String path = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    public static double[] amplitude;
    public static double[] minData;
    public static double[] maxData;

   private MethodKNN() {

   }

    /**
     * Permet de mettre à jour les données de l'algorithme. Recalcule les amplitudes et les min/max des données.
     * @param datas Les données sur lesquelles l'algorithme doit travailler
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

    /**
     * Renvoie une liste des voisins.
     * @param datas Liste des données
     * @param data Donnée cible
     * @param k Nombre de voisins
     * @param distance Algorithme de distance utilisée pour calculer la proximité entre les points
     * @return Liste des voisins
     */
    public static List<LoadableData> kVoisins(List<LoadableData> datas, LoadableData data, int k, Distance distance) {

        // On récupère toutes les données.
        List<LoadableData> voisins = new ArrayList<>(datas);
        // On retire la valeur dont on cherche les voisins.
        voisins.remove(data);
        // On trie la liste en fonction de la distance entre chaque point et data.
        voisins.sort(new DataComparator(distance, data));

        // On renvoie les k premiers qui sont les k avec la plus petite distance à data.
        return voisins.subList(0, k);
    }

    /**
     * Estime la classe d'une donnée cible en se basant sur ses k plus proches voisins.
     * @param datas Liste de données
     * @param data Donnée cible
     * @param k Nombre de voisins
     * @param distance Algorithme de distance utilisée pour calculer la proximité entre les points
     * @return Classe estimée pour la donnée cible
     * @throws IllegalAccessException
     */
    public static String estimateClass(List<LoadableData> datas, LoadableData data, int k, Distance distance) throws IllegalAccessException {

        // On récupère les K voisins de data.
        List<LoadableData> kVoisins = MethodKNN.kVoisins(datas, data, k, distance);

        // On compte le nombre de représentations de chaque classe parmi les voisins
        // Et on récupère la plus présente.

        Map<String, Integer> classOfNeighbours = new HashMap<>();
        String currentClass = kVoisins.get(0).getClassification();

        for(LoadableData voisin : kVoisins) {
            int newValue =  ((classOfNeighbours.get(voisin.getClassification()) == null) ? 0 : classOfNeighbours.get(voisin.getClassification()) )+ 1;
            classOfNeighbours.put(voisin.getClassification(), newValue);
            // Si la classe est plus présente que la classe actuellement majoritaire, on change la classe majoritaire.
            // S'il y a égalité, alors on garde la première trouvée.
            if(classOfNeighbours.get(voisin.getClassification()) > classOfNeighbours.get(currentClass)) {
                currentClass = voisin.getClassification();
            }
        }
        return currentClass;
    }

    /**
     * Estimation du meilleur K.
     * @param datas Liste des données
     * @param distance Algorithme de distance utilisée pour calculer la proximité entre les points
     * @return Meilleur k déterminé
     * @throws IllegalAccessException
     */
    public static int bestK(List<LoadableData> datas, Distance distance) throws IllegalAccessException {
        // On borne le K pour éviter de trouver un K trop grand
        int maxK = (int) (Math.sqrt(datas.size()));
        System.out.println("Max k: " + maxK);

        int betK = 1;

        Map<Integer, Double> results = new LinkedHashMap<>();
        // Pour chaque valeur impaire possible de K, on calcule la robustesse (le taux de réussite) de l'algorithme.
        for(int i =1; i<maxK; i = i +2) {
            results.put(i, robustesse(datas, i, distance, 0.2));
            // On modifie le meilleur k si le taux est supérieur au K precedent
            // Si égalité, on garde le premier trouvé
            if(results.get(i) > results.get(betK)) betK = i;
        }

        System.out.println("Results: " + results);

        return betK;
    }

    /**
     * Évaluation de la robustesse de l'algorithme KNN.
     * @param datas Liste des données
     * @param k Nombre de voisins
     * @param distance Algorithme de distance utilisée pour calculer la proximité entre les points
     * @param testPart Fraction des données utilisée pour le test
     * @return Taux de réussite de l'algorithme pour un k spécifié
     * @throws IllegalAccessException
     */
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

    /**
     * Fonction principale.
     * @param args Arguments donnés
     * @throws CsvRequiredFieldEmptyException
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws CsvRequiredFieldEmptyException, IllegalAccessException {

        //Test de la robustesse et du meillleur K

        ClassificationModel model = ClassificationModel.getClassificationModel();

        model.setType(DataType.POKEMON);
        model.loadData(new File(path+"data/pokemon_train.csv"));
        MethodKNN.updateModel(model.getDatas());
        System.out.println();

        // Permet de définir l'attribut sur lequel l'on souhaite classifier :
        LoadableData.setClassificationTypeGlobal(12);

        List<LoadableData> datas = ClassificationModel.getClassificationModel().getDatas();
        // On mélange les données pour tester sur différentes variétés car le fichier de base est trié.
        Collections.shuffle(datas);

        for(int i = 0; i<1; i++) {
            System.out.println("Search best k");

            // On cherche le meilleur K
            int bestK = MethodKNN.bestK(datas, new DistanceManhattanNormalisee());
            System.out.println(bestK);

            // Puis on calcule la robustesse avec le K trouvé
            System.out.println(MethodKNN.robustesse( datas, bestK, new DistanceManhattanNormalisee(), 0.2));

        }
    }
}
