package fr.univlille.sae.classification.model;

public enum DataType {
    IRIS(4, Iris.class),
    POKEMON(12, Pokemon.class);

    /**
     * Nombre d'arguments nécessaires.
     */
    private final int argumentSize;

    /**
     * Classe liée à l'élément.
     */
    private final Class<? extends LoadableData> clazz;

    /**
     * Constructeur de l'enum.
     * @param argumentSize Nombre d'arguments nécessaires
     * @param clazz Classe liée
     */
    DataType(int argumentSize, Class<? extends LoadableData> clazz) {
        this.argumentSize = argumentSize;
        this.clazz = clazz;
    }

    /**
     * Renvoie le nombre d'arguments.
     * @return Nombre d'arguments
     */
    public int getArgumentSize() {
        return argumentSize;
    }

    /**
     * Renvoie la classe liée à l'élément de l'enum.
     * @return Classe liée
     */
    public Class<? extends LoadableData> getClazz() {
        return clazz;
    }


}
