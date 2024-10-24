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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainStageView implements Observer {

    private ClassificationModel model;
    private ScatterChart scatterChart;
    private MainStageController controller;

    private String actualX;
    private String actualY;
    private Stage root;

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
        root = loader.load();
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
        if(!(observable instanceof ClassificationModel)) throw new IllegalStateException();
        //on vide le nuage pour s'assurer que celui-ci est bien vide
        scatterChart.getData().clear();

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Iris");

        //Jalon 1: on verifie que le type de donnée est bien IRIS
        if(model.getType() == DataType.IRIS) {


            if(actualX==null && actualY==null){
                controller.setAxesSelected("Aucuns axes sélectionnés");
            }
            else{
                controller.setAxesSelected("");
                // On ajoute la serie au nuage
                scatterChart.getData().add(series1);

                //On recupere les données du model
                List<LoadableData> points = new ArrayList<>(model.getDatas());
                points.addAll(model.getDataToClass());
                // on ajoute chaque point a la serie
                for(LoadableData i : points) {

                    Iris iris = (Iris)i;
                    XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(iris.getDataType(actualX),
                            iris.getDataType(actualY));

                    dataPoint.setNode(getCircle(iris));

                    series1.getData().add(dataPoint);

                }
            }
        }
    }


    private Circle getCircle(Iris iris) {
        Circle circle = new Circle(5);
        circle.setFill(iris.getColor());
        circle.setOnMouseClicked(e -> {
            ContextMenu contextMenu = new ContextMenu();
            for(String attributes : iris.getAttributesName()) {
                contextMenu.getItems().add(new MenuItem(attributes + " : " + iris.getDataType(attributes)));
            }
            contextMenu.show(root, e.getScreenX(), e.getScreenY());
        });

        return  circle;
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

            dataPoint.setNode(getCircle(iris));
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

    public MainStageController getController() {
        return controller;
    }

}
