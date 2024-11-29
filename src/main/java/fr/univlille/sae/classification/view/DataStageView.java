package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.DataStageController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.utils.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Classe responsable de l'affichage et de la gestion de la vue des données.
 * Implémente l'interface Observer pour recevoir des notifications de mise à jour.
 */
public class DataStageView extends DataVisualizationView implements Observer {
    /**
     * Regroupements de points.
     */
    private XYChart.Series series1;
    private XYChart.Series series2;
    private XYChart.Series series3;
    private XYChart.Series series4;

    /**
     * Fenêtre de la vue secondaire.
     */
    private Stage root;

    /**
     * Constructeur pour initialiser la vue de données.
     * @param model le modèle de classification utilisé pour gérer les données.
     */
    public DataStageView(ClassificationModel model) {
        super(model);
        this.series1 = new XYChart.Series();
        this.series2 = new XYChart.Series();
        this.series3 = new XYChart.Series();
        this.series4 = new XYChart.Series();
        model.attach(this);
    }

    /**
     * Affiche la vue des données en chargeant le fichier FXML et en initialisant la scène.
     */
    public void show() {
        FXMLLoader loader = new FXMLLoader();

        try {
            URL fxmlFileUrl = getClass().getClassLoader().getResource("stages"+File.separator+"data-view-stage.fxml");

            if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
            }
            loader.setLocation(fxmlFileUrl);
            root = loader.load();
            root.setResizable(false);
            root.setTitle("SAE3.3 - Logiciel de classification");
            root.show();
            controller = (MainStageController) controller;
            controller = loader.getController();
            ((DataStageController) controller).setDataStageView(this);
            scatterChart = controller.getScatterChart();
            scatterChart.setLegendVisible(false);
            scatterChart.getData().addAll(series4, series1, series2, series3);

            controller.setAxesSelected("Aucun fichier sélectionné");

            if (!model.getDatas().isEmpty()) {
                update(model);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
        }
    }


    /**
     * Renvoie le contrôleur associé à cette vue.
     * @return contrôleur de la vue.
     */
    public DataStageController getController() {
        return (DataStageController) controller;
    }

    /**
     * Recharge les données de la vue en fonction des données du modèle.
     */
    @Override
    public void reload() {
        this.update(ClassificationModel.getClassificationModel());
    }
}
