package fr.univlille.sae.classification.model;

import com.opencsv.bean.CsvBindByName;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Représente un point Pokémon.
 * Contient des informations sur les statistiques (attaque, défense, PV...) ainsi que les types du monstre.
 */
public class Pokemon extends LoadableData{

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
    private Boolean isLegendary = null ;

    /**
     * Constructeur pour créer une instance de Pokémon avec tous les attributs.
     * @param name Nom du monstre
     * @param attack Statistique d'attaque
     * @param baseEggSteps Pas nécessaires à l'éclosion de son œuf
     * @param captureRate Taux de capture
     * @param defense Statistique de défense
     * @param experienceGrowth Taux de croissance de l'expérience
     * @param hp Points de vie
     * @param spAttack Statistique d'attaque spéciale
     * @param spDefense Statistique de défense spéciale
     * @param type1 Type principal
     * @param type2 Type secondaire (non obligatoire)
     * @param speed Statistique de vitesse
     * @param isLegendary Est-il un Pokémon légendaire
     */
    public Pokemon(String name, int attack, int baseEggSteps, double captureRate, int defense, int experienceGrowth, int hp, int spAttack, int spDefense, String type1, String type2, double speed, boolean isLegendary) {
        super();
        classificationType = 9;
        this.name = name;
        this.attack = attack;
        this.baseEggSteps = baseEggSteps;
        this.captureRate = captureRate;
        this.defense = defense;
        this.experienceGrowth = experienceGrowth;
        this.hp = hp;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        if(type1 == null || type1.isEmpty()) {
            this.type1 = "undefined";
        }else {
            this.type1 = type1;
        }
        this.type2 = type2;
        this.speed = speed;
        this.isLegendary = isLegendary;
    }

    /**
     * Constructeur avec un tableau.
     * @param list Tableau d'élements
     * @throws IllegalAccessException
     */
    public Pokemon(Object[] list) throws IllegalAccessException {

        Field[] fields = getClass().getDeclaredFields();
        for(int i = 0; i<fields.length; i++) {
            if(i != LoadableData.classificationType) {
                System.out.println(" i : " + i + " field " + fields[i].getName());
                if(i < LoadableData.classificationType) {
                    fields[i].set(this, list[i]);
                }else {
                    fields[i].set(this, list[i-1]);
                }

            }else if(fields[i].getType().equals(String.class)) {
                fields[i].set(this, "undefined");
            }
        }

    }

    /**
     * Constructeur par défaut.
     */
    public Pokemon() {
        classificationType = 9;
    }

    /**
     * Renvoie la classification de l'objet.
     *
     * @return Classification sous forme de chaîne
     */
    @Override
    public String getClassification() throws IllegalAccessException {
        return (String) this.getClass().getDeclaredFields()[classificationType].get(this).toString();
    }

    /**
     * Définit la classification de l'objet.
     *
     * @param classification Classification à définir
     */
    @Override
    public void setClassification(String classification) throws IllegalAccessException {
        Field field = this.getClass().getDeclaredFields()[classificationType];
        if(field.getClass().equals(String.class)) {
            field.set(this, classification);
        }else if(field.getType().equals(Boolean.class)) {
            field.set(this, Boolean.valueOf(classification));
        }

    }


    /**
     * Permet de modifier l'attribut sur laquelle l'on souhaite classifier
     * Ne sont valables que les attributs présents dans getClassificationAttributes()
     * Le numéro de l'attribut correspond à sa place dans la liste de tous les attributs.
     * @param classificationType
     */
    public void setClassificationType(int classificationType) throws IllegalArgumentException, IllegalAccessException {
        if(classificationType < 0 || classificationType > getAttributesNames().size()) throw new IllegalArgumentException("Cette attribut n'existe pas");
        String keyToVerify = getAttributesNames().keySet().toArray(new String[0])[classificationType];
        if(!getClassifiedAttributes().containsKey(keyToVerify)) throw new IllegalArgumentException("Cette attribut ne peut pas être utiliser pour la classification");
        LoadableData.classificationType = classificationType;
        System.out.println("Set type to : " + classificationType);

        LoadableData.setClassificationTypes(ClassificationModel.getClassificationModel().getDatas());
    }

    /**
     * Renvoie une Map des attributs valides pour la classification.
     * @return Map des attributs avec leur nom et valeur
     */
    public Map<String, Object> getClassifiedAttributes() {
        Map<String, Object> attributes = new LinkedHashMap<>();

        attributes.put("Experience growth", experienceGrowth);
        attributes.put("Type 1", type1);
        attributes.put("Type 2", type2);
        attributes.put("Is legendary", isLegendary);

        return attributes;
    }

    /**
     *
     * @return
     */
    @Override
    public int getClassificationType() {
        return classificationType;
    }

    /**
     * Renvoie les noms des attributs de l'objet.
     * @return Tableau de chaînes contenant les noms des attributs ainsi que leur variable
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
        attrNames.put("Type 1", type1);
        attrNames.put("Type 2", type2);
        attrNames.put("Speed", speed);
        attrNames.put("Is legendary", isLegendary);
        return attrNames;
    }

    /**
     * Renvoie les attributs numériques sous forme de tableau.
     * @return Tableau des attributs numériques
     */
    @Override
    public double[] getAttributes() {
        return new double[]{attack, baseEggSteps, captureRate, defense,
                            experienceGrowth, hp, spAttack, spDefense, speed};
    }

    /**
     * Renvoie les attributs de type chaînes de caractères.
     * @return Tableau des attributs String
     */
    @Override
    public String[] getStringAttributes() {
        return new String[]{type2, String.valueOf(isLegendary)};
    }

    /**
     * Représentation sous forme de chaîne de l'objet Pokémon.
     * @return Chaîne contenant les informations du Pokémon
     */
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
