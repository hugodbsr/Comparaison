package fr.univlille.sae.classification.model;

public enum DataType {

    IRIS(4),
    POKEMON(12);

    private int argumentSize;

    DataType(int argumentSize) {
        this.argumentSize = argumentSize;
    }

    public int getArgumentSize() {
        return argumentSize;
    }


}
