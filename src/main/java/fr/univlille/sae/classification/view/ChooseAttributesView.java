package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.AddDataController;
import fr.univlille.sae.classification.controller.ChooseAttributesController;
import fr.univlille.sae.classification.model.ClassificationModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Classe responsable du choix des attributs.
 */
public class ChooseAttributesView {

    /**
     * Modèle de classification utilisé pour gérer les données.
     */
    private ClassificationModel model;

    /**
     * Fenêtre parente de la vue.
     */
    private Stage owner;

    /**
     * Constructeur pour initialiser la vue de choix des attributs.
     * @param model Le modèle de classification
     * @param owner La fenêtre parente de cette vue
     */
    public ChooseAttributesView(ClassificationModel model, Stage owner) {
        this.model = model;
        this.owner = owner;
    }

    /**
     * Affiche la vue du choix des attributs.
     */
    public void show() {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getClassLoader().getResource("stages"+ File.separator+"choose-attributes.fxml");

        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }

        loader.setLocation(fxmlFileUrl);

        try {
            Stage root = loader.load();
           // ChooseAttributesController controller = loader.getController();

            if (model.getDatas().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez d'abord charger les données avant de pouvoir choisir l'attribut a classifier");
                alert.showAndWait();
                return;
            }

            root.setResizable(false);
            root.initOwner(owner);
            root.initModality(Modality.APPLICATION_MODAL);
            root.setTitle("Choix d'attribut");

            root.showAndWait();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
