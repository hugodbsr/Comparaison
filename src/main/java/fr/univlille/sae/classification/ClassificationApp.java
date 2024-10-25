package fr.univlille.sae.classification;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.MainStageView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassificationApp extends Application {


    public void start(Stage stage) throws IOException {
        ClassificationModel model = ClassificationModel.getClassificationModel();
        MainStageView view = new MainStageView(model);

        view.show();
    }

    // Ouvre l'application
    public static void main(String[] args) {
        Application.launch(args);
    }




}
