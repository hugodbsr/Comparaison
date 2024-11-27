package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvBindByName;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Pokemon extends LoadableData {

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
        super();
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

    public Pokemon(Object[] list) {
        this((String) list[0], (Integer) list[1], (Integer) list[2], (Double) list[3], (Integer) list[4], (Integer) list[5], (Integer) list[6], (Integer) list[7], (Integer) list[8], (String) list[9], (String) list[10], (Double) list[11], (Boolean) list[12]);
    }

    /**
     * Constructeur par défaut.
     */
    public Pokemon() {
        //
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
    public Map<String, Object> getAttributesNames() {
        Map<String, Object> attrNames = new LinkedHashMap<>();
        attrNames.put("Name", name);
        attrNames.put("Attaque", attack);
        attrNames.put("Base egg steps", baseEggSteps);
        attrNames.put("Capture rate", captureRate);
        attrNames.put("Defense", defense);
        attrNames.put("Experience growth", experienceGrowth);
        attrNames.put("HP", hp);
        attrNames.put("Special attack", spAttack);
        attrNames.put("Special defense", spDefense);
        attrNames.put("Speed", speed);
        attrNames.put("Is legendary", isLegendary);
        return attrNames;
    }



    /**
     * Renvoie la valeur des données en fonction de l'axe spécifié.
     *
     * @param axes nom de l'axe pour lequel la valeur est requise.
     * @return valeur correspondante.
     */
    @Override
    public double getDataType(String axes) {
        switch (axes) {
            case "attack":
                return this.attack;
            case "base_egg_steps":
                return this.baseEggSteps;
            case "capture_rate":
                return this.captureRate;
            case "defense":
                return this.defense;
            case "experience_growth":
                return this.experienceGrowth;
            case "hp":
                return this.hp;
            case "sp_attack":
                return this.spAttack;
            case "sp_defense":
                return this.spDefense;
            case "speed":
                return this.speed;
            case "is_legendary":
                if(this.isLegendary){
                    return 1;
                }
                return 0;
            default:
                return this.attack;
        }
    }

    @Override
    public double[] getAttributes() {
        return new double[]{attack, baseEggSteps, captureRate, defense,
                            experienceGrowth, hp, spAttack, spDefense, speed};
    }

    @Override
    public String[] getStringAttributes() {
        return new String[]{name, type2, String.valueOf(isLegendary)};
    }

    @Override
    public String toString(){
        return(
                "Name: " + this.name + "\n" +
                "Attack: " + this.attack + "\n" +
                "Base egg steps: " + this.baseEggSteps + "\n" +
                "Capture_rate: " + this.captureRate + "\n" +
                "Defense: " + this.defense + "\n" +
                "Experience growth: " + this.experienceGrowth + "\n" +
                "HP: " + this.hp + "\n" +
                "Sp attack: " + this.spAttack + "\n" +
                "Sp defense: " + this.spDefense + "\n" +
                "Type 1: " + this.type1 + "\n" +
                "Type 2: " + this.type2 + "\n" +
                "Speed: " + this.speed + "\n" +
                "Is legendary: " + this.isLegendary
                );
    }
}
