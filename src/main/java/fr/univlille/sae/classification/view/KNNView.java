package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.KNNController;
import fr.univlille.sae.classification.model.ClassificationModel;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Classe représentant la vue de classification des données avec KNN.
 */
public class KNNView {
    /**
     * Modèle de classification utilisé pour gérer les données.
     */
    private ClassificationModel model;

    /**
     * Fenêtre parente de la vue.
     */
    private Stage owner;

    /**
     * Constructeur de la vue de chargement des données.
     * @param model Modèle de classification à utiliser
     * @param owner Fenêtre parente
     */
    public KNNView(ClassificationModel model, Stage owner) {
        this.model = model;
        this.owner = owner;
    }

    /**
     * Charge le fichier FXML et initialise la scène.
     */
    public void show() {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getClassLoader().getResource("stages"+ File.separator+"k-NN-stage.fxml");

        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }

        loader.setLocation(fxmlFileUrl);

        try {
            Stage root = loader.load();

            root.setResizable(false);
            root.initOwner(owner);
            root.initModality(Modality.APPLICATION_MODAL);
            root.setTitle("Configuration de la classification");
            KNNController controller = loader.getController();

            root.showAndWait();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}
