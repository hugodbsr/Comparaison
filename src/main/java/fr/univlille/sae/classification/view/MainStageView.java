package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.LoadDataController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.Iris;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainStageView implements Observer {

    private ClassificationModel model;
    private ScatterChart scatterChart;
    private MainStageController controller;

    private String actualX;
    private String actualY;

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
        loader.getController();
        controller = loader.getController();
        controller.setMainStageView(this);
        scatterChart = controller.getScatterChart();
        controller.setAxesSelected("Aucun fichier sélectionné");
    }


    @Override
    public void update(Observable observable) {
        if(scatterChart == null) throw new IllegalStateException();
        scatterChart.getData().clear();
        if(!(observable instanceof ClassificationModel)) throw new IllegalStateException();
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Iris");
        if(model.getType() == DataType.IRIS) {
            controller.setAxesSelected("");
            if(actualX==null && actualY==null){
                controller.setAxesSelected("Aucuns axes sélectionnés");
            }
            else{
                scatterChart.getData().add(series1);
                for(LoadableData i : model.getDatas()) {
                    Iris iris = (Iris)i;
                    XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(iris.getDataType(actualX),
                            iris.getDataType(actualY));
                    Circle circle = new Circle(5);
                    circle.setFill(iris.getColor());
                    dataPoint.setNode(circle);
                    series1.getData().add(dataPoint);
                }
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if(scatterChart == null) throw new IllegalStateException();
        if(!(observable instanceof ClassificationModel)) throw new IllegalStateException();
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
            Circle circle = new Circle(5);
            circle.setFill(iris.getColor());
            dataPoint.setNode(circle);
            if (!scatterChart.getData().isEmpty()) {
                XYChart.Series series = (XYChart.Series) scatterChart.getData().get(0);
                series.getData().add(dataPoint);
            }
        }
    }

    public void setActualX(String actualX) {
        this.actualX = actualX;
    }

    public void setActualY(String actualY) {
        this.actualY = actualY;
    }

    public String getActualX() {
        return actualX;
    }

    public String getActualY() {
        return actualY;
    }

    public Observable getModel() {
        return this.model;
    }
}
