package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.LoadDataController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.Iris;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import fr.univlille.sae.classification.utils.ViewUtil;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainStageView extends DataVisualizationView implements Observer {

    private ClassificationModel model;
    private MainStageController controller;

    private Stage root;

    public MainStageView(ClassificationModel model) {
        this.model = model;
        model.attach(this);
    }


    public void show() {
        FXMLLoader loader = new FXMLLoader();

        try {
            URL fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "main-stage.fxml").toURI().toURL();

            if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
            }
            loader.setLocation(fxmlFileUrl);
            root = loader.load();
            root.setResizable(false);
            root.setTitle("SAE3.3 - Logiciel de classification");
            root.show();
            loader.getController();
            controller = loader.getController();
            controller.setMainStageView(this);
            scatterChart = controller.getScatterChart();
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
            scatterChart.getData().clear();

            //Jalon 1: on verifie que le type de donnée est bien IRIS
            if (model.getType() == DataType.IRIS) {
                XYChart.Series series1 = new XYChart.Series();
                XYChart.Series series2 = new XYChart.Series();
                XYChart.Series series3 = new XYChart.Series();
                if (actualX == null && actualY == null) {
                    controller.setAxesSelected("Aucuns axes sélectionnés");
                } else {
                    controller.setAxesSelected("");

                    List<LoadableData> points = new ArrayList<>(model.getDatas());
                    points.addAll(model.getDataToClass());
                    for (LoadableData i : points) {

                        Iris iris = (Iris) i;
                        XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(iris.getDataType(actualX),
                                iris.getDataType(actualY));

                        dataPoint.setNode(ViewUtil.getForm(iris, new Circle(5), root));

                        if (iris.getClassification().equals("Setosa")) {
                            series1.getData().add(dataPoint);
                        } else if (iris.getClassification().equals("Versicolor")) {
                            series2.getData().add(dataPoint);
                        } else if (iris.getClassification().equals("Virginica")) {
                            series3.getData().add(dataPoint);
                        }
                    }

                    series1.setName("Setosa");
                    series2.setName("Versicolor");
                    series3.setName("Virginica");

                    scatterChart.getData().addAll(series1, series2, series3);
                }
            }
        }catch (Exception e) {
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
            if(data instanceof Iris) {
                Iris iris = (Iris) data;
                if(actualX == null || actualY == null) {
                    controller.setAxesSelected("Aucuns axes sélectionnés");
                    return;
                }
                XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(
                        iris.getDataType(actualX),
                        iris.getDataType(actualY)
                );

                dataPoint.setNode(ViewUtil.getForm(iris, new Rectangle(10, 10), root));
                if (!scatterChart.getData().isEmpty()) {
                    XYChart.Series series = (XYChart.Series) scatterChart.getData().get(0);
                    series.getData().add(dataPoint);
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
