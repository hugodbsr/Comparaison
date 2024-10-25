package fr.univlille.sae.classification.model;

/**
 * Usine pour créer des objets LoadableData en fonction du type de données.
 */
public class PointFactory {

    /**
     * Crée un point de données en fonction du type spécifié et des coordonnées fournies.
     * @param type type de données
     * @param coords coordonnées du point à créer.
     * @return instance de LoadableData correspondant aux coordonnées, ou null en cas d'erreur.
     * @throws IllegalArgumentException si le nombre de coordonnées ne correspond pas au type spécifié.
     */
    public static LoadableData createPoint(DataType type, double[] coords) throws IllegalArgumentException {
        int size = coords.length;
        LoadableData data;

            switch (type) {
                case IRIS:
                    if (size != 4) {
                        throw new IllegalArgumentException("Le nombre de coordonnées doit être de 4 pour le type IRIS.");
                    }
                    data = new Iris(coords[0], coords[1], coords[2], coords[3]);
                    break;
                default:
                    throw new IllegalArgumentException("Type de données non supporté : " + type);
            }


        return data;
    }
}
