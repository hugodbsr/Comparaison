# SAÉ 3.01 - Logiciel de classification

## Membres - Groupe H4

-  [ANTOINE Maxence](mailto:maxence.antoine.etu@univ-lille.fr)
-  [DEBUYSER Hugo](mailto:hugo.debuyser.etu@univ-lille.fr)
-  [DEKEISER Matisse](mailto:matisse.dekeiser.etu@univ-lille.fr)
-  [DESMONS Hugo](mailto:hugo.desmons.etu@univ-lille.fr)
-  [MENNECART Matias](mailto:matias.mennecart.etu@univ-lille.fr)


## Présentation du projet
L'objectif de cette SAÉ est le développement d'une application de classification de données. L'utilisateur pourra charger un jeu de données en format CSV, puis choisir les axes d'abscisse et d'ordonnée selon 2 attributs, et le logiciel proposera une visualisation des données en 2 dimensions sous forme d'un nuage de points. Par la suite, l'utilisateur pourra ajouter une donnée en spécifiant tous les attributs nécessaires à celle-ci, et le logiciel se chargera de déterminer la possible catégorie à laquelle elle appartient, tout en la rendant visible sur le nuage de points.

## Pour compiler l'application

```
sh compile.sh
```

## Pour lancer l'application

```
sh run.sh
```

# Participation des membres

---

## Jalon 1:

-  [ANTOINE Maxence](mailto:maxence.antoine.etu@univ-lille.fr) :

Figma basse fidélité, Tests du model (Iris, Model), Merge request

-  [DEBUYSER Hugo](mailto:hugo.debuyser.etu@univ-lille.fr) : 

SceneBuilder FXML, Modifications des axes, Couleur des points, javadoc, merge request

- [DEKEISER Matisse](mailto:matisse.dekeiser.etu@univ-lille.fr) :

Chargement fichiers CSV, deuxieme vue, merge request
 
- [DESMONS Hugo](mailto:hugo.desmons.etu@univ-lille.fr) :

Modifs XML, Ajouts des données (view, controller)
 
- [MENNECART Matias](mailto:matias.mennecart.etu@univ-lille.fr)

Structure des classes, packages, ClassificationModel, Ajouts des fenetres d'exceptions,
Selection des points, Optimisations et fix de bugs, script lancement et compilation, merge request et gestion des conflits 

## Jalon 2:


• La possibilité de visualiser un autre nuage de points que Iris,
et de choisir les axes de projection de ce nuage

• Les catégories visibles sur le nuage (couleur, forme). La
catégorie qu’on souhaite visualiser sera choisie lors du
chargement des données, parmi les catégories énumérables.

• La possibilité d’ajouter un point dont la catégorie est
inconnue, et de décider cette catégorie en utilisant l’algorithme
k-NN. Ce point doit être ajouté au nuage, de façon visible.

• Une évaluation de la qualité de votre classification : calcul de
la robustesse

• Des fonctionnalités supplémentaires: info bulle au survol d’un
point, possibilité de modifier l’échelle des axes pour ne
visualiser qu’une partie des points du nuage

-  [ANTOINE Maxence](mailto:maxence.antoine.etu@univ-lille.fr) :



-  [DEBUYSER Hugo](mailto:hugo.debuyser.etu@univ-lille.fr) :



- [DEKEISER Matisse](mailto:matisse.dekeiser.etu@univ-lille.fr) :



- [DESMONS Hugo](mailto:hugo.desmons.etu@univ-lille.fr) :



- [MENNECART Matias](mailto:matias.mennecart.etu@univ-lille.fr)





---