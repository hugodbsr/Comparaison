package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvBindByName;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Représente un point Iris.
 * Contient des informations sur les dimensions des sépales et des pétales et sur la variété de la fleur.
 */
public class Iris extends LoadableData {

    @CsvBindByName(column = "sepal.length", required = true)
    private double sepalLength;
    @CsvBindByName(column = "sepal.width", required = true)
    private double sepalWidth;
    @CsvBindByName(column = "petal.length", required = true)
    private double petalLength;
    @CsvBindByName(column = "petal.width", required = true)
    private double petalWidth;
    @CsvBindByName(column = "variety")
    private String variety;

    /**
     * Constructeur pour créer une instance d'Iris avec tous les attributs.
     * @param sepalLength longueur du sépale.
     * @param sepalWidth largeur du sépale.
     * @param petalLength longueur du pétale.
     * @param petalWidth largeur du pétale.
     * @param variety variété de l'Iris.
     */
    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String variety) {
        super();
        classificationType = 4;
        this.sepalWidth = sepalWidth;
        this.sepalLength = sepalLength;
        this.petalWidth = petalWidth;
        this.petalLength = petalLength;
        this.variety = variety;
    }

    /**
     * Constructeur pour créer une instance de Iris avec les dimensions des sépales et des pétales.
     * @param sepalLength longueur du sépale.
     * @param sepalWidth largeur du sépale.
     * @param petalLength longueur du pétale.
     * @param petalWidth largeur du pétale.
     */
    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        this(sepalLength, sepalWidth, petalLength, petalWidth, "undefined");
    }

    /**
     * Constructeur par défaut.
     */
    public Iris() {
        classificationType = 4;
    }

    /**
     * Renvoie la classification de l'objet.
     *
     * @return classification sous forme de chaîne.
     */
    @Override
    public String getClassification() throws IllegalAccessException {
        return (String) this.getClass().getDeclaredFields()[classificationType].get(this).toString();
    }

    /**
     * Permet de modifier l'attribut sur lequelle ont souhaite classifier
     * Ne sont valable que les attributs present dans getClassificationAttributes()
     *
     * Le numéro de l'attribut correspond a sa place dans la liste de tous le attributs.
     * @param classificationType
     */
    @Override
    public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {
        if(classificationType < 0 || classificationType > getAttributesNames().size()) throw new IllegalArgumentException("Cette attribut n'existe pas");
        String keyToVerify = getAttributesNames().keySet().toArray(new String[0])[classificationType];
        if(!getClassifiedAttributes().containsKey(keyToVerify)) throw new IllegalArgumentException("Cette attribut ne peut pas être utiliser pour la classification");
        LoadableData.classificationType = classificationType;
        System.out.println("Set type to : " + classificationType);

        LoadableData.setClassificationTypes(ClassificationModel.getClassificationModel().getDatas());
    }

    /**
     * Définit la classification (variété) de l'Iris.
     */
    @Override
    public Map<String, Object> getClassifiedAttributes() {
        Map<String, Object> attributes = new LinkedHashMap<>();

        attributes.put("Variété", variety);

        return attributes;
    }

    @Override
    public int getClassificationType() {
        return classificationType;
    }

    /**
     * Définit la classification de l'objet.
     *
     * @param classification classification à définir.
     */
    @Override
    public void setClassification(String classification) throws IllegalAccessException {
        this.getClass().getDeclaredFields()[classificationType].set(this, classification);
    }

    /**
     * Renvoie la largeur du sépale.
     * @return largeur du sépale.
     */
    public double getSepalWidth() {
        return sepalWidth;
    }

    /**
     * Renvoie la longueur du sépale.
     * @return longueur du sépale.
     */
    public double getSepalLength() {
        return sepalLength;
    }

    /**
     * Renvoie la largeur du pétale.
     * @return largeur du pétale.
     */
    public double getPetalWidth() {
        return petalWidth;
    }

    /**
     * Renvoie la longueur du pétale.
     * @return longueur du pétale.
     */
    public double getPetalLength() {
        return petalLength;
    }


    @Override
    public double[] getAttributes() {
        return new double[]{sepalLength, sepalWidth, petalLength, petalWidth} ;
    }

    @Override
    public String[] getStringAttributes() {
        return new String[0];
    }

    /**
     * Renvoie les noms des attributs de l'Iris.
     * @return tableau de chaînes contenant les noms des attributs.
     */

    @Override
    public Map<String, Object> getAttributesNames() {
        Map<String, Object> attrNames = new LinkedHashMap<>();
        attrNames.put("Longueur des sépales", sepalLength);
        attrNames.put("Largeur des sépales", sepalWidth);
        attrNames.put("Longueur des pétales", petalLength);
        attrNames.put("Largeur des pétales", petalWidth);
        attrNames.put("Variété", variety);
        return attrNames;
    }

    /**
     * Représentation sous forme de chaîne de l'objet Iris.
     * @return chaîne contenant les dimensions de l'Iris.
     */
    @Override
    public String toString() {
        try {
            return (
                    "Sepal length: " + sepalLength + "\n" +
                    "Sepal width: " + sepalWidth + "\n" +
                    "Petal length: " + petalLength + "\n" +
                    "Petal width: " + petalWidth + "\n" +
                    "Variety: " + getClassification()
            );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
