package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.Iris;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import fr.univlille.sae.classification.utils.ViewUtil;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainStageView extends DataVisualizationView implements Observer {

    private ClassificationModel model;
    private MainStageController controller;

    private Stage root;

    private XYChart.Series series1 ;
    private XYChart.Series series2 ;
    private XYChart.Series series3;
    private XYChart.Series series4 ;

    public MainStageView(ClassificationModel model) {
        super();
        this.series1 = new XYChart.Series();
        this.series2 = new XYChart.Series();
        this.series3 = new XYChart.Series();
        this.series4 = new XYChart.Series();

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
        root.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez vous quitter l'application ?");
            alert.setContentText("Aucunes modifications ne sera sauvegardé ! Les points qui ont été ajoutés ne seront pas sauvegardés");
            Optional<ButtonType> optionalButtonType = alert.showAndWait();
            if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.OK) {
                System.exit(0);
            }else {
                event.consume();
            }

        });
        loader.getController();
        controller = loader.getController();
        controller.setMainStageView(this);

        scatterChart = controller.getScatterChart();
        scatterChart.getData().addAll(series1, series2, series3);

            System.out.println("DataStageView scatter chart: " +scatterChart );
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
            for(XYChart.Series serie : series) {serie.getData().clear();}



        //Jalon 1: on verifie que le type de donnée est bien IRIS
        if(model.getType() == DataType.IRIS) {


            if(actualX==null && actualY==null){
                controller.setAxesSelected("Aucuns axes sélectionnés");
            }
            else{
                controller.setAxesSelected("");

                    List<LoadableData> points = new ArrayList<>(model.getDatas());
                    points.addAll(model.getDataToClass());
                    for (LoadableData i : points) {

                        Iris iris = (Iris) i;
                        XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(iris.getDataType(actualX),
                                iris.getDataType(actualY));

                        dataPoint.setNode(ViewUtil.getForm(iris, new Circle(5), root));

                    switch (iris.getClassification()) {
                        case "Setosa":
                            series1.getData().add(dataPoint);
                            break;
                        case "Versicolor":
                            series2.getData().add(dataPoint);
                            break;
                        case "Virginica":
                            series3.getData().add(dataPoint);
                            break;
                        default:
                            series4.getData().add(dataPoint);
                            break;
                    }

                }

                series1.setName("Setosa");
                series2.setName("Versicolor");
                series3.setName("Virginica");
                series4.setName("undefinied");

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
