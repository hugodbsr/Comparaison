package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.AxesSettingsController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Classe responsable de la création et de l'affichage de la vue de configuration des axes.
 */
public class AxesSettingsView {

    /**
     * Modèle de classification utilisé pour gérer les données.
     */
    private ClassificationModel model;

    /**
     * Fenêtre parente de la vue.
     */
    private Stage owner;

    /**
     * Vue de visualisation des données.
     */
    private DataVisualizationView dataVisualizationView;

    /**
     * Constructeur pour initialiser la vue de configuration des axes.
     * @param model Modèle de classification utilisé pour gérer les données
     * @param owner Fenêtre parente de cette vue
     * @param dataVisualizationView Vue de visualisation des données associée
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
        URL fxmlFileUrl = getClass().getClassLoader().getResource("stages"+File.separator+"axes-settings-stage.fxml");


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
            String[] attributes = dataType.getAttributesNames().keySet().toArray(new String[0]);
            ArrayList<String> finalAttrList = new ArrayList<>();
            for (String attribute : attributes) {
                if ((dataType.getAttributesNames().get(attribute) instanceof Integer) || (dataType.getAttributesNames().get(attribute) instanceof Double)) {
                    finalAttrList.add(attribute);
                }
            }
            String[] finalAttributes = finalAttrList.toArray(new String[0]);
            controller.setSelectAbs(finalAttributes);
            controller.setSelectOrd(finalAttributes);

            controller.setAbsSizeUpper(((NumberAxis)dataVisualizationView.getScatterChart().getXAxis()).getUpperBound());
            controller.setAbsSizeLower(((NumberAxis)dataVisualizationView.getScatterChart().getXAxis()).getLowerBound());

            controller.setOrdSizeUpper(((NumberAxis)dataVisualizationView.getScatterChart().getYAxis()).getUpperBound());
            controller.setOrdSizeLower(((NumberAxis)dataVisualizationView.getScatterChart().getYAxis()).getLowerBound());

            root.showAndWait();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}
