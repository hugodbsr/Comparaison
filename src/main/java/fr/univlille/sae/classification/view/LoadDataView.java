package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.AxesSettingsController;
import fr.univlille.sae.classification.controller.LoadDataController;
import fr.univlille.sae.classification.model.ClassificationModel;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Classe responsable de l'affichage de la vue de chargement des données.
 */
public class LoadDataView {

    private ClassificationModel model;
    private Stage owner;

    /**
     * Constructeur de la vue de chargement des données.
     * @param model modèle de classification à utiliser.
     * @param owner fenêtre parente.
     */
    public LoadDataView(ClassificationModel model, Stage owner) {
        this.model = model;
        this.owner = owner;
    }

    /**
     * Affiche la fenêtre de chargement des données.
     */
    public void show() {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getClassLoader().getResource("stages"+File.separator+"load-data-stage.fxml");

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
            root.setTitle("Chargement des données");

            root.showAndWait();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}
