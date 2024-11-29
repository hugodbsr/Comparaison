package fr.univlille.sae.classification.knn.distance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.knn.MethodKNN;
import fr.univlille.sae.classification.knn.distance.DistanceManhattanNormalisée;
import org.junit.jupiter.api.BeforeEach;

public class DistanceManhattanNormaliséeTest {

    private DistanceManhattanNormalisée distanceManhattan;

    @BeforeEach
    public void setUp() {
        // Initialisation de l'objet DistanceManhattanNormalisée avant chaque test
        distanceManhattan = new DistanceManhattanNormalisée();

        // Définition des valeurs de normalisation dans MethodKNN (à ajuster selon votre logique)
        MethodKNN.minData = new double[] {0.0, 0.0};  // Valeurs minimales pour les attributs
        MethodKNN.amplitude = new double[] {1.0, 1.0};  // Amplitude pour la normalisation
    }

    @Test
    public void testDistanceSimple() {
        // Création de deux objets LoadableData avec des attributs simples
        LoadableData data1 = new LoadableData(new double[] {1.0, 2.0});
        LoadableData data2 = new LoadableData(new double[] {3.0, 5.0});

        // Calcul de la distance manhattan normalisée
        double result = distanceManhattan.distance(data1, data2);

        // Calcul attendu : (|1.0 - 3.0| / 1.0) + (|2.0 - 5.0| / 1.0)
        double expected = (Math.abs(1.0 - 3.0) / 1.0) + (Math.abs(2.0 - 5.0) / 1.0);

        // Vérification du résultat
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void testDistanceZero() {
        // Deux objets avec les mêmes attributs (la distance devrait être 0)
        LoadableData data1 = new LoadableData(new double[] {1.0, 2.0});
        LoadableData data2 = new LoadableData(new double[] {1.0, 2.0});

        // Vérification que la distance est bien 0
        double result = distanceManhattan.distance(data1, data2);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testDistanceAvecValeursExtremes() {
        // Test avec des valeurs maximales ou minimales (hypothétiques dans ce cas)
        LoadableData data1 = new LoadableData(new double[] {Double.MAX_VALUE, Double.MIN_VALUE});
        LoadableData data2 = new LoadableData(new double[] {Double.MIN_VALUE, Double.MAX_VALUE});

        double result = distanceManhattan.distance(data1, data2);

        // Calcul de la distance, ici l'effet de normalisation devrait être pris en compte
        double expected = (Math.abs(Double.MAX_VALUE - Double.MIN_VALUE) / 1.0) +
                (Math.abs(Double.MIN_VALUE - Double.MAX_VALUE) / 1.0);

        assertEquals(expected, result, 0.001);
    }

    @Test
    public void testDistanceAvecDonneesNulles() {
        // Test avec des données nulles
        LoadableData data1 = new LoadableData(new double[] {1.0, 2.0});
        LoadableData data2 = null;

        try {
            distanceManhattan.distance(data1, data2);
            fail("La méthode devrait lever une exception quand l'un des objets est nul");
        } catch (NullPointerException e) {
            // Vérification qu'une exception est lancée
        }
    }

    @Test
    public void testDistanceAvecDonneesVides() {
        // Test avec des données vides (tous les attributs sont 0 ou absents)
        LoadableData data1 = new LoadableData(new double[] {});
        LoadableData data2 = new LoadableData(new double[] {});

        // La distance devrait être 0 car il n'y a aucune différence d'attribut
        double result = distanceManhattan.distance(data1, data2);
        assertEquals(0.0, result, 0.001);
    }
}
