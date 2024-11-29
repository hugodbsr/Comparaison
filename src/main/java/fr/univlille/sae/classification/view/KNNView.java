package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.KNNController;
import fr.univlille.sae.classification.controller.LoadDataController;
import fr.univlille.sae.classification.model.ClassificationModel;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class KNNView {

    private ClassificationModel model;
    private Stage owner;

    /**
     * Constructeur de la vue de chargement des données.
     * @param model modèle de classification à utiliser.
     * @param owner fenêtre parente.
     */
    public KNNView(ClassificationModel model, Stage owner) {
        this.model = model;
        this.owner = owner;
    }

    /**
     * Affiche la fenêtre de chargement des données.
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
