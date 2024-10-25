package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.AxesSettingsController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Classe responsable de la création et de l'affichage de la vue de configuration des axes.
 */
public class AxesSettingsView {

    private ClassificationModel model;
    private Stage owner;
    private DataVisualizationView dataVisualizationView;

    /**
     * Constructeur pour initialiser la vue de configuration des axes.
     * @param model modèle de classification utilisé pour gérer les données.
     * @param owner fenêtre parente de cette vue.
     * @param dataVisualizationView vue de visualisation des données associée.
     */
    public AxesSettingsView(ClassificationModel model, Stage owner, DataVisualizationView dataVisualizationView) {
        this.model = model;
        this.owner = owner;
        this.dataVisualizationView = dataVisualizationView;
    }

    /**
     * Affiche la vue de configuration des axes.
     */
    public void show() {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = null;

        try {
            fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "axes-settings-stage.fxml").toURI().toURL();
        } catch (IOException e) {
            System.out.println("Erreur lors de la création de l'URL du fichier FXML : " + e.getMessage());
            return;
        }

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
            root.setTitle("Configuration des axes");
            AxesSettingsController controller = loader.getController();
            controller.setdataVisualizationView(dataVisualizationView);

            if (model.getDatas().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez d'abord charger les données avant de modifier les paramètres");
                alert.showAndWait();
                return;
            }

            LoadableData dataType = model.getDatas().get(0);
            controller.setSelectAbs(dataType.getAttributesName());
            controller.setSelectOrd(dataType.getAttributesName());

            root.showAndWait();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}
