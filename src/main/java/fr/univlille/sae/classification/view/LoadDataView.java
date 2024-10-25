package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.model.ClassificationModel;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoadDataView {

    private ClassificationModel model;
    private Stage owner;

    public LoadDataView(ClassificationModel model, Stage owner) {
        this.model = model;
        this.owner = owner;
    }

    public void show() {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = null;

        try {
            fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "load-data-stage.fxml").toURI().toURL();
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
            root.setTitle("Chargement des données");

            root.showAndWait();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}
