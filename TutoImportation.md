## Importation d'un fichier CSV

#### Préambule

Avant de commencer, sachez que cette importation nécessite certaines connaissances en Java ou en programmation orientée objet. Toutefois, si vous n'êtes pas familier avec ces concepts, ce tutoriel vous guidera à travers toutes les étapes nécessaires pour importer de nouvelles données.

Assurez-vous que votre base de données est sous forme de fichier CSV, avec les noms des colonnes sur la première ligne. Il est également essentiel de savoir quelles données vous souhaitez analyser. Cette étape est indépendante du logiciel que vous utilisez, et dépend plutôt de la manière dont vous allez importer vos données.

---

### Procédure

Ce modèle de classification permet d’importer une base de données. Pour ce faire, il vous suffit d'ajouter au modèle, dans le dossier `src/main/java/fr.univlille.sae.classification/model`, une nouvelle classe portant le nom de votre choix, correspondant à votre jeu de données.

Une fois cela fait, vous devrez implémenter les différentes méthodes présentes dans l'interface `LoadableData`. Dans votre nouvelle classe, commencez par définir les attributs correspondant aux colonnes de votre fichier CSV. Par exemple :

```java
@CsvBindByName(column = "column1")
private String column1;
@CsvBindByName(column = "column2")
private int column2;
@CsvBindByName(column = "column3")
private boolean column3;
@CsvBindByName(column = "column4")
private double column4;
```

Réitérez cette opération pour toutes les colonnes de votre CSV. Vous remarquerez qu’il est nécessaire d’attribuer un type à chaque attribut. Assurez-vous de bien distinguer les types numériques (entiers, réels) des chaînes de caractères.

Ensuite, créez les différents constructeurs pour votre classe. Commencez par un constructeur vide, indispensable au bon fonctionnement de l’importation :

```java
public [NomDeLaClasse]() {
    // Constructeur vide
}
```

Puis, créez un constructeur prenant en paramètre tous les attributs définis précédemment :

```java
public [NomDeLaClasse](String column1, int column2, boolean column3, double column4) {
    super();
    this.column1 = column1;
    this.column2 = column2;
    this.column3 = column3;
    this.column4 = column4;
}
```

Enfin, créez un autre constructeur qui accepte une liste d'objets. Chaque élément de cette liste doit être converti au type correspondant à chaque attribut, en commençant par l'élément à l'indice 0 :

```java
public [NomDeLaClasse](Object[] list) {
    this((String) list[0], (int) list[1], (boolean) list[2], (double) list[3]);
}
```

Ensuite, modifiez les méthodes `getClassification()` et `setClassification()` pour leur attribuer la valeur que vous souhaitez étudier. C’est à vous de définir cette valeur :

```java
@Override
public String getClassification() {
    return [DonnéeÀClassifier];
}

@Override
public void setClassification(String classification) {
    this.[DonnéeÀClassifier] = classification;
}
```

La méthode `getAttributesNames()` permet de renvoyer les différents attributs étudiés dans le graphe, c'est-à-dire tous les attributs sauf celui que vous souhaitez classifier. Vous pouvez rendre les noms des attributs plus lisibles que leur nom de variable, comme dans l’exemple ci-dessous :

```java
@Override
public Map<String, Object> getAttributesNames() {
    Map<String, Object> attrNames = new LinkedHashMap<>();
    attrNames.put("Column 1", column1);
    attrNames.put("Column 2", column2);
    attrNames.put("Column 3", column3);
    attrNames.put("Column 4", column4);
    return attrNames;
}
```

La méthode `getAttributes()` permet de renvoyer les différents attributs numériques sous forme de tableau de doubles. Cela correspond aux données qui pourront être affichées dans le graphe :

```java
@Override
public double[] getAttributes() {
    return new double[]{column1, column2, column3, column4};
}
```

La méthode `getStringAttributes()` renvoie un tableau d'attributs de type chaîne de caractères, y compris les valeurs booléennes :

```java
@Override
public String[] getStringAttributes() {
    return new String[]{column1, String.valueOf(column2)};
}
```

Il ne vous reste plus qu'à personnaliser la méthode `toString()`, qui permet d’afficher les informations d’un point. Vous pouvez entièrement personnaliser l’affichage, mais il est recommandé de respecter cette structure pour une présentation claire :

```java
@Override
public String toString() {
    return "Column 1: " + this.column1 + "\n" +
           "Column 2: " + this.column2 + "\n" +
           "Column 3: " + this.column3 + "\n" +
           "Column 4: " + this.column4 + "\n";
}
```

Votre classe pour le nouveau jeu de données est maintenant prête.

---

Ensuite, vous devez ajouter ce nouveau type à l'énumération `DataType`, en spécifiant le nombre d'arguments à exploiter, soit le nombre de colonnes dans le fichier CSV, moins un (puisque vous avez une colonne pour la classification).

---

Rendez-vous ensuite dans la classe `PointFactory` et ajoutez le code suivant dans le `switch case` de la méthode `createPoint()` :

```java
case [Nom des données]:
    if (size != DataType.[Nom des données].getArgumentSize()) {
        throw new IllegalArgumentException("Le nombre de coordonnées doit être de " + DataType.[Nom des données].getArgumentSize() + " pour le type [Nom des données].");
    }
    data = new [Nom des données](coords);
    break;
```

---

Ces explications devraient vous permettre d’ajouter des données à la classification. Si vous rencontrez des difficultés lors de votre implémentation, n'hésitez pas à relire ce tutoriel ou à consulter les implémentations existantes.