#!/bin/bash

# Vérifie si le répertoire contient un projet Maven (pom.xml)
if [ ! -f "pom.xml" ]; then
    echo "Erreur : Aucun fichier pom.xml trouvé dans le répertoire actuel."
    echo "Veuillez exécuter ce script dans un répertoire Maven valide."
    exit 1
fi

# Exécute mvn clean
echo "Exécution de 'mvn clean'..."
mvn clean
if [ $? -ne 0 ]; then
    echo "Erreur : 'mvn clean' a échoué. Veuillez verifier que maven est bien installé"
    exit 1
fi

# Exécute mvn install
echo "Exécution de 'mvn install'..."
mvn install
if [ $? -ne 0 ]; then
    echo "Erreur : 'mvn install' a échoué. Veuillez verifier que maven est bien installé"
    exit 1
fi

echo "Build Maven terminé avec succès."