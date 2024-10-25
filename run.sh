#!/bin/bash

# Noms des fichiers à vérifier
fichier1="ClassificationApp-1.0-shaded.jar"
fichier2="target/ClassificationApp-1.0-shaded.jar"

# Vérifie si le premier fichier existe
if [ -f "$fichier1" ]; then
    echo "Execution de $fichier1 ..."
    java -jar "$fichier1"
# Sinon, vérifie si le deuxième fichier existe
elif [ -f "$fichier2" ]; then
    echo "Execution de $fichier1 ..."
    java -jar "$fichier2"
# Si aucun des deux fichiers n'existe, affiche un message d'erreur
else
    echo "Erreur : Aucun des fichiers $fichier1 ou $fichier2 n'existe. Veuillez compiler le projet avec le script fournis"
fi