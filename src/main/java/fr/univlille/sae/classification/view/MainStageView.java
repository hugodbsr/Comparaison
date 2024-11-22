package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.Iris;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import fr.univlille.sae.classification.utils.ViewUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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

    private Map<String, XYChart.Series<Double, Double>> serieList;

    private XYChart.Series series1;
    private XYChart.Series series2;
    private XYChart.Series series3;
    private XYChart.Series series4;

    /**
     * Constructeur de la vue principale.
     * @param model modèle de classification à utiliser.
     */
    public MainStageView(ClassificationModel model) {
        super();
        this.serieList = new HashMap<String, XYChart.Series<Double, Double>>();
        this.series1 = new XYChart.Series();
        this.series2 = new XYChart.Series();
        this.series3 = new XYChart.Series();
        this.series4 = new XYChart.Series();
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

    @Override
    public void update(Observable observable) {
        try {
            if (scatterChart == null || !(observable instanceof ClassificationModel)) {
                System.err.println("Erreur de mise à jour.");
                return;
            }

            ObservableList<XYChart.Series> series = scatterChart.getData();
            for (XYChart.Series serie : series) {
                serie.getData().clear();
            }

            if (actualX == null && actualY == null) {
                controller.setAxesSelected("Aucuns axes sélectionnés");
            } else {
                controller.setAxesSelected("");

                List<LoadableData> points = new ArrayList<>(model.getDatas());
                points.addAll(model.getDataToClass().keySet());
                for (LoadableData i : points) {
                    LoadableData data = (LoadableData) i;
                    XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(data.getDataType(actualX), data.getDataType(actualY));

                    if(model.getDataToClass().containsKey(data)) {
                        dataPoint.setNode(ViewUtil.getForm(data, new Rectangle(10, 10), controller));
                    }else {
                        dataPoint.setNode(ViewUtil.getForm(data, new Circle(5), controller));
                    }

                    XYChart.Series<Double, Double> editSerie = serieList.get(data.getClassification());
                    if(editSerie == null){
                        editSerie = new XYChart.Series<>();
                    }
                    editSerie.getData().add(dataPoint);
                    serieList.put(data.getClassification(), editSerie);
                }

                for(String serie : serieList.keySet()) {
                    serieList.get(serie).setName(serie);
                }
                scatterChart.getData().addAll(serieList.values());
            }
        } catch (Exception e) {
            System.err.println("Erreur de mise à jour : " + e.getMessage());
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        try {
            if (scatterChart == null || !(observable instanceof ClassificationModel)) {
                System.err.println("Erreur de mise à jour.");
                return;
            }
            if (data instanceof Iris) {
                Iris iris = (Iris) data;
                if (actualX == null || actualY == null) {
                    controller.setAxesSelected("Aucuns axes sélectionnés");
                    return;
                }
                XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(
                        iris.getDataType(actualX),
                        iris.getDataType(actualY)
                );

                dataPoint.setNode(ViewUtil.getForm(iris, new Rectangle(10, 10), controller));
                if (!scatterChart.getData().isEmpty()) {
                    series4.getData().add(dataPoint);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur de mise à jour : " + e.getMessage());
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
