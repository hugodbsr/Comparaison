package fr.univlille.sae.classification.model;

public class PointFactory {


    public static LoadableData createPoint(DataType type, double[] coords)  {

        int size = coords.length;
        LoadableData data;
        switch (type) {
            case IRIS:
                if(size != 4) throw new IllegalArgumentException();
                data = new Iris(coords[0],coords[1],coords[2],coords[3]);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return data;
    }
}
