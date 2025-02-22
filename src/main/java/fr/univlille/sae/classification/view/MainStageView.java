package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.utils.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Classe représentant la vue principale de l'application de classification.
 */
public class MainStageView extends DataVisualizationView implements Observer {

    /**
     * Fenêtre de la vue principale.
     */
    private Stage root;

    /**
     * Regroupements de points.
     */
    private ScatterChart.Series series1;
    private ScatterChart.Series series2;
    private ScatterChart.Series series3;
    private ScatterChart.Series series4;

    /**
     * Constructeur de la vue principale.
     *
     * @param model modèle de classification à utiliser.
     */
    public MainStageView(ClassificationModel model) {
        super(model);

        this.series1 = new ScatterChart.Series();
        this.series2 = new ScatterChart.Series();
        this.series3 = new ScatterChart.Series();
        this.series4 = new ScatterChart.Series();

        model.attach(this);
    }

    /**
     * Affiche la vue principale.
     */
    public void show() {
        FXMLLoader loader = new FXMLLoader();

        try {
            URL fxmlFileUrl = getClass().getClassLoader().getResource("stages"+File.separator+"main-stage.fxml");

            if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
            }

            loader.setLocation(fxmlFileUrl);

            root = loader.load();
            root.setResizable(false);
            root.setTitle("SAE3.3 - Logiciel de classification");
            root.show();

            root.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Voulez-vous quitter l'application ?");
                alert.setContentText("Aucunes modifications ne seront sauvegardées ! Les points ajoutés ne seront pas sauvegardés.");
                Optional<ButtonType> optionalButtonType = alert.showAndWait();
                if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.OK) {
                    System.exit(0);
                } else {
                    event.consume();
                }
            });


            controller = (MainStageController) controller;
            controller = loader.getController();
            ((MainStageController) controller).setMainStageView(this);
            scatterChart = controller.getScatterChart();
            //scatterChart.getData().addAll(series1, series2, series3, series4);
            controller.setAxesSelected("Aucun fichier sélectionné");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
        }
    }


    /**
     * Retourne le contrôleur principal de la scène.
     *
     * @return le contrôleur principal de la scène en tant qu'instance.
     */
    public MainStageController getController() {
        return (MainStageController) controller;
    }

    /**
     * Recharge les données nécessaires à partir du modèle de classification.
     * Cette méthode met à jour l'état en fonction des données actuelles
     */
    @Override
    public void reload() {
        this.update(ClassificationModel.getClassificationModel());
    }
}

