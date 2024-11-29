package fr.univlille.sae.classification.model;

import java.util.Arrays;

/**
 * Usine pour créer des objets LoadableData en fonction du type de données.
 */
public class PointFactory {

    /**
     * Crée un point de données en fonction du type spécifié et des coordonnées fournies.
     * @param type Type de données
     * @param coords Coordonnées du point à créer
     * @return Instance de LoadableData correspondant aux coordonnées, ou null en cas d'erreur
     * @throws IllegalArgumentException si le nombre de coordonnées ne correspond pas au type spécifié.
     */
    public static LoadableData createPoint(DataType type, Object[] coords) throws IllegalArgumentException {
        int size = coords.length;
        LoadableData data;
        System.out.println("Arrays : " + Arrays.toString(coords) + "   " + size);
            switch (type) {
                case IRIS:
                    if (size != DataType.IRIS.getArgumentSize()) {
                        throw new IllegalArgumentException("Le nombre de coordonnées doit être de 4 pour le type IRIS.");
                    }
                    data = new Iris((Double)coords[0], (Double)coords[1], (Double)coords[2], (Double)coords[3]);
                    break;
                case POKEMON:
                    if(size != DataType.POKEMON.getArgumentSize()) {
                        throw new IllegalArgumentException("Le nombre de coordonnées doit être de 12 pour le type POKEMON.");
                    }
                    data = null;
                    if (coords.length == 13) {
                        try {
                            data = new Pokemon(coords);
                        } catch (IllegalAccessException e) {
                            throw new IllegalArgumentException("Erreur lors de la création du Pokemon");
                        }
                    }
                    else if (coords.length == 12) {
                        try {
                            data = new Pokemon(coords);
                        } catch (IllegalAccessException e) {
                            throw new IllegalArgumentException("Une erreur est survenue lors de la création du point");
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Type de données non supporté : " + type);
            }


        return data;
    }
}
