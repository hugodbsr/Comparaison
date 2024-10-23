package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.AddDataController;
import fr.univlille.sae.classification.controller.AxesSettingsController;
import fr.univlille.sae.classification.model.ClassificationModel;
import javafx.fxml.FXMLLoader;
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



    public void show() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "add-data-stage.fxml").toURI().toURL();

        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Stage root = loader.load();

        AddDataController controller = loader.getController();

        controller.setMainStageView(mainStageView);

        root.setResizable(false);
        root.initOwner(owner);
        root.initModality(Modality.APPLICATION_MODAL);
        root.setTitle("Ajout de donée");

        root.showAndWait();

    }

}
