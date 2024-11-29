package fr.univlille.sae.classification.model;

public enum DataType {

    IRIS(4, Iris.class),
    POKEMON(12, Pokemon.class);

    private final int argumentSize;
    private final Class<? extends LoadableData> clazz;

    DataType(int argumentSize, Class<? extends LoadableData> clazz) {
        this.argumentSize = argumentSize;
        this.clazz = clazz;
    }

    public int getArgumentSize() {
        return argumentSize;
    }

    public Class<? extends LoadableData> getClazz() {
        return clazz;
    }


}
