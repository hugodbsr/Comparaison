package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.DataStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.Iris;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import javafx.collections.ObservableList;
import fr.univlille.sae.classification.utils.ViewUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataStageView extends DataVisualizationView implements Observer {

    private ClassificationModel model;
    private DataStageController controller;


    private XYChart.Series series1 ;
    private XYChart.Series series2 ;
    private XYChart.Series series3 ;
    private XYChart.Series series4 ;

    private Stage root;

    public DataStageView(ClassificationModel model) {
        super();
        this.model = model;
        this.series1 = new XYChart.Series();
        this.series2 = new XYChart.Series();
        this.series3 = new XYChart.Series();
        this.series4 = new XYChart.Series();
        model.attach(this);
    }

    public void show() {
        FXMLLoader loader = new FXMLLoader();

        try {
            URL fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "data-view-stage.fxml").toURI().toURL();

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
        controller.setDataStageView(this);
        scatterChart = controller.getScatterChart();

        scatterChart.getData().addAll(series4, series1, series2, series3 );



        controller.setAxesSelected("Aucun fichier sélectionné");

            if (!model.getDatas().isEmpty()) {
                update(model);
            }
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
            // On vide le nuage pour s'assurer qu'il est bien vide
            ObservableList<XYChart.Series> series = scatterChart.getData();
            for(XYChart.Series serie : series) {serie.getData().clear();}




            //Jalon 1: on verifie que le type de donnée est bien IRIS
        if(model.getType() == DataType.IRIS) {
            if(actualX==null && actualY==null){
                controller.setAxesSelected("Aucuns axes sélectionnés");
            }
            else{
                controller.setAxesSelected("");

                //On recupere les données du model
                List<LoadableData> points = new ArrayList<>(model.getDatas());
                points.addAll(model.getDataToClass());
                // on ajoute chaque point a la serie
                for(LoadableData i : points) {

                    Iris iris = (Iris)i;
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
                            dataPoint.setNode(ViewUtil.getForm(iris, new Rectangle(10, 10), root));
                            series4.getData().add(dataPoint);
                            break;
                    }


                    series1.setName("Setosa");
                    series2.setName("Versicolor");
                    series3.setName("Virginica");
                    series4.setName("undefinied");

                }
            }

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

            dataPoint.setNode(ViewUtil.getForm(iris, new Rectangle(10, 10), root));
            if (!scatterChart.getData().isEmpty()) {
                series4.getData().add(dataPoint);
            }
        }
        } catch (Exception e) {
            System.err.println("Erreur de mise à jour : " + e.getMessage());
        }
    }

    public DataStageController getController() {
        return controller;
    }

    @Override
    public void reload() {
        this.update(ClassificationModel.getClassificationModel());
    }
}
