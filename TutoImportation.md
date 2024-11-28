# Importation d'un fichier CSV

Ce modèle de classification offre la possibilité d'importer une base de données, pour cela il vous suffit d'ajouter au modèle 
dans le dossier `src/main/java/fr.univlille.sae.classification/model` une nouvelle classe avec le nom que vous souhaitez donner à votre jeu de données.

Une fois cela fait, il vous suffit d'implémenter les différentes méthodes présentes dans l'interface `LoadableData`. Vous devez ensuite ajouter ce nouveau type
à l'énumération `DataType` où vous devez spécifier le nombre d'arguments devant être exploiter, cela correpond au nombre de colones du tableau - 1.

Rendez-vous ensuite dans la classe `PointFactory` où devez y ajouter dans le switch case de la méthode `createPoint()`:

```java
case [Nom des données]:
    if (size != DataType.[Nom des données].getArgumentSize()) {
        throw new IllegalArgumentException("Le nombre de coordonnées doit être de " + [Nom des données].getArgumentSize() + " pour le type [Nom des données].");
    }
    data = new [Nom des données].getArgumentSize()(coords);
    break;
```

