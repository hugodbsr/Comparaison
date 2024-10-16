package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainStageView implements Observer {


    private ClassificationModel model;

    public MainStageView(ClassificationModel model) {
        this.model = model;
        model.attach(this);
    }


    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        URL fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "main-stage.fxml").toURI().toURL();

        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Stage root = loader.load();
        root.setResizable(false);
        root.setTitle("SAE3.3 - Logiciel de classification");
        root.show();
    }


    @Override
    public void update(Observable observable) {

    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
