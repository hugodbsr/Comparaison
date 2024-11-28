package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import fr.univlille.sae.classification.utils.ViewUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Classe représentant la vue principale de l'application de classification.
 */
public class MainStageView extends DataVisualizationView implements Observer {

    private ClassificationModel model;
    private MainStageController controller;

    private Stage root;

    private Map<String, ScatterChart.Series<Double, Double>> serieList;

    private ScatterChart.Series series1;
    private ScatterChart.Series series2;
    private ScatterChart.Series series3;
    private ScatterChart.Series series4;

    /**
     * Constructeur de la vue principale.
     * @param model modèle de classification à utiliser.
     */
    public MainStageView(ClassificationModel model) {
        super();
        this.serieList = new HashMap<String, ScatterChart.Series<Double, Double>>();
        this.series1 = new ScatterChart.Series();
        this.series2 = new ScatterChart.Series();
        this.series3 = new ScatterChart.Series();
        this.series4 = new ScatterChart.Series();
        this.model = model;
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

            controller = loader.getController();
            controller.setMainStageView(this);
            scatterChart = controller.getScatterChart();
            //scatterChart.getData().addAll(series1, series2, series3, series4);
            controller.setAxesSelected("Aucun fichier sélectionné");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
        }
    }





    public MainStageController getController() {
        return controller;
    }

    @Override
    public void reload() {
        this.update(ClassificationModel.getClassificationModel());
    }
}
