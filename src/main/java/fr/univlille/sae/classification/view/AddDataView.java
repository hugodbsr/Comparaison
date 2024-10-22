package fr.univlille.sae.classification.view;

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

    public AddDataView(ClassificationModel model, Stage owner) {
        this.model = model;
        this.owner = owner;
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

        root.setResizable(false);
        root.initOwner(owner);
        root.initModality(Modality.APPLICATION_MODAL);
        root.setTitle("Ajout de donée");

        root.showAndWait();

    }

}
