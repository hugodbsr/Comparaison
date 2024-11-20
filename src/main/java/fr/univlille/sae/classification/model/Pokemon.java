package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvBindByName;
import javafx.scene.paint.Color;

public class Pokemon extends LoadableData{



    // name,attack,base_egg_steps,capture_rate,defense,experience_growth,hp,sp_attack,sp_defense,type1,type2,speed,is_legendary
    // Swablu,40,5120,255.0,60,600000,45,75,50,normal,flying,1.2,False

    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "attack")
    private int attack;
    @CsvBindByName(column = "base_egg_steps")
    private int baseEggSteps;
    @CsvBindByName(column = "capture_rate")
    private double captureRate;
    @CsvBindByName(column = "defense")
    private int defense;
    @CsvBindByName(column = "experience_growth")
    private int experienceGrowth;
    @CsvBindByName(column = "hp")
    private int hp;
    @CsvBindByName(column = "sp_attack")
    private int spAttack;
    @CsvBindByName(column = "sp_defense")
    private int spDefense;
    @CsvBindByName(column = "type1")
    private String type1;
    @CsvBindByName(column = "type2")
    private String type2;
    @CsvBindByName(column = "speed")
    private double speed;
    @CsvBindByName(column = "is_legendary")
    private boolean isLegendary;


    public Pokemon(String name, int attack, int baseEggSteps, double captureRate, int defense, int experienceGrowth, int hp, int spAttack, int spDefense, String type1, String type2, double speed, boolean isLegendary) {
        this.name = name;
        this.attack = attack;
        this.baseEggSteps = baseEggSteps;
        this.captureRate = captureRate;
        this.defense = defense;
        this.experienceGrowth = experienceGrowth;
        this.hp = hp;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.type1 = type1;
        this.type2 = type2;
        this.speed = speed;
        this.isLegendary = isLegendary;
    }

    public Pokemon() {

    }

    /**
     * Renvoie la classification de l'objet.
     *
     * @return classification sous forme de chaîne.
     */
    @Override
    public String getClassification() {
        return type1;
    }

    /**
     * Définit la classification de l'objet.
     *
     * @param classification classification à définir.
     */
    @Override
    public void setClassification(String classification) {
        this.type1 = classification;
    }

    /**
     * Renvoie les noms des attributs de l'objet.
     *
     * @return tableau de chaînes contenant les noms des attributs.
     */
    @Override
    public String[] getAttributesName() {
        //TODO
        return new String[0];
    }

    /**
     * Renvoie la couleur associée à l'objet.
     *
     * @return couleur correspondant à la classification de l'objet.
     */
    @Override
    public Color getColor() {
        return null;
    }

    /**
     * Renvoie la valeur des données en fonction de l'axe spécifié.
     *
     * @param axes nom de l'axe pour lequel la valeur est requise.
     * @return valeur correspondante.
     */
    @Override
    public double getDataType(String axes) {
        // todo
        return 0;
    }

    @Override
    public double[] getAttributes() {
        return new double[0];
    }

    @Override
    public String[] getStringAttributes() {
        return new String[0];
    }
}
