package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.AddDataController;
import fr.univlille.sae.classification.model.ClassificationModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AddDataView {

    private ClassificationModel model;
    private Stage owner;
    private MainStageView mainStageView;

    public AddDataView(ClassificationModel model, Stage owner, MainStageView mainStageView) {
        this.model = model;
        this.owner = owner;
        this.mainStageView = mainStageView;
    }

    public void show() {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = null;

        try {
            fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "add-data-stage.fxml").toURI().toURL();
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
            AddDataController controller = loader.getController();
            controller.setMainStageView(mainStageView);

            if (model.getDatas().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez d'abord charger les données avant de pouvoir ajouter un point");
                alert.showAndWait();
                return;
            }

            root.setResizable(false);
            root.initOwner(owner);
            root.initModality(Modality.APPLICATION_MODAL);
            root.setTitle("Ajout de donnée");

            root.showAndWait();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}
