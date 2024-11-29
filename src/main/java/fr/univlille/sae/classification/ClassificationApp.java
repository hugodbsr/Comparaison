package fr.univlille.sae.classification;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.MainStageView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale pour l'application de classification.
 * Cette classe initialise et lance l'interface graphique de l'application.
 */
public class ClassificationApp extends Application {


    /**
     * Point d'entrée principal pour l'initialisation de l'interface utilisateur.
     * Cette méthode configure la vue principale en utilisant une instance du modèle
     * de classification, puis affiche la fenêtre principale.
     * @param stage la fenêtre principale de l'application.
     */
    public void start(Stage stage) throws IOException {
        ClassificationModel model = ClassificationModel.getClassificationModel();
        MainStageView view = new MainStageView(model);

        view.show();
    }

    /**
     * Point d'entrée principal de l'application.
     * Cette méthode lance l'application JavaFX.
     * @param args les arguments de ligne de commande.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }




}
