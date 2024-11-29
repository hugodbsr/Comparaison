# Importation d'un fichier CSV

### Préambule

Avant toute chose, sachez que cette importation nécessite quelques connaissances en Java ou en langage orienté objet. Cependant, si vous ne les avez pas, ce tutoriel devrait vous fournir toutes les étapes nécessaires à l'importation de nouvelles données.

Il est important que vous disposiez d'une base de données sous la forme d'un fichier CSV, avec les noms des colonnes situés sur la première ligne. Il est également essentiel que vous ayez en tête les données que vous souhaitez étudier. Cela ne dépend pas du logiciel, mais bien de la manière dont vous allez importer les données.

___

### Procédure

Ce modèle de classification permet d'importer une base de données. Pour cela, il vous suffit d'ajouter au modèle, dans le dossier `src/main/java/fr.univlille.sae.classification/model`, une nouvelle classe avec le nom que vous souhaitez attribuer à votre jeu de données.

Une fois cela fait, il vous faudra implémenter les différentes méthodes présentes dans l'interface `LoadableData`. Dans votre nouvelle classe, vous devez tout d'abord créer les attributs correspondant aux différentes colonnes de votre CSV. Par exemple :

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

Faites cela pour toutes les colonnes de votre CSV. Vous aurez probablement remarqué qu'il faut aussi leur attribuer des types. À vous de les définir, en veillant à différencier les nombres et les chaînes de caractères.

Il faudra ensuite créer les différents constructeurs pour votre classe. Commencez par en créer un vide, sans lequel l'importation échouera :

```java
public [NomDeLaClasse]() {
    // Constructeur vide
}
```

Ensuite, créez un constructeur qui prend en paramètre tous les attributs que vous avez définis précédemment :

```java
public [NomDeLaClasse](String column1, int column2, boolean column3, double column4) {
    super();
    this.column1 = column1;
    this.column2 = column2;
    this.column3 = column3;
    this.column4 = column4;
}
```

Enfin, créez un autre constructeur qui prend en paramètre une liste d'objets. Chaque objet de cette liste doit être "casté" avec le type correspondant à chaque attribut, en commençant par le premier élément de la liste (indice 0) :

```java
public [NomDeLaClasse](Object[] list) {
    this((String) list[0], (int) list[1], (boolean) list[2], (double) list[3]);
}
```

Ensuite, vous devez ajouter ce nouveau type à l'énumération `DataType`, où vous devrez spécifier le nombre d'arguments à exploiter. Cela correspond au nombre de colonnes du tableau, moins 1.

___

Rendez-vous ensuite dans la classe `PointFactory` et ajoutez le code suivant dans le `switch case` de la méthode `createPoint()` :

```java
case [Nom des données]:
    if (size != DataType.[Nom des données].getArgumentSize()) {
        throw new IllegalArgumentException("Le nombre de coordonnées doit être de " + DataType.[Nom des données].getArgumentSize() + " pour le type [Nom des données].");
    }
    data = new [Nom des données].getArgumentSize()(coords);
    break;
```