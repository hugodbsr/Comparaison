package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import fr.univlille.sae.classification.utils.ViewUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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

    @Override
    public void update(Observable observable) {
        try {
            if (scatterChart == null || !(observable instanceof ClassificationModel)) {
                System.err.println("Erreur de mise à jour.");
                return;
            }

            scatterChart.getData().clear();
            serieList.clear();

            if (actualX == null && actualY == null) {
                controller.setAxesSelected("Aucuns axes sélectionnés");
            } else {
                controller.setAxesSelected("");
                controller.setAxesSelectedDisable();

                List<LoadableData> points = new ArrayList<>(model.getDatas());
                points.addAll(model.getDataToClass().keySet());
                for (LoadableData data : points) {
                    Object xValue = data.getAttributesNames().get(actualX);
                    Object yValue = data.getAttributesNames().get(actualY);



                    double x = 0;
                    if(xValue instanceof Number) {
                        x = ((Number) xValue).doubleValue();
                    }
                    double y = 0;
                    if(yValue instanceof Number) {
                        y = ((Number) yValue).doubleValue();
                    }
                    /**
                    Double x = 0.0;
                    if (xValue instanceof Integer) {
                        x = ((Integer) xValue).doubleValue();
                    } else if (xValue instanceof Double) {
                        x = (Double) xValue;
                    }

                    Double y = 0.0;
                    if (yValue instanceof Integer) {
                        y = ((Integer) yValue).doubleValue();
                    } else if (yValue instanceof Double) {
                        y = (Double) yValue;
                    }
                    **/
                    ScatterChart.Data<Double, Double> dataPoint = new ScatterChart.Data<>(x, y);

                    Node nodePoint = ViewUtil.getForm(data, new Circle(5), controller);

                    ScatterChart.Series<Double, Double> editSerie = serieList.get(data.getClassification());
                    if(editSerie == null){
                        editSerie = new ScatterChart.Series<Double, Double>();
                    }
                    if(data.getClassification().equals("undefined") || model.getDataToClass().containsKey(data)) {
                        nodePoint = ViewUtil.getForm(data, new Rectangle(10,10), controller);
                    }

                    dataPoint.setNode(nodePoint);
                    editSerie.getData().add(dataPoint);
                    serieList.put(data.getClassification(), editSerie);
                }

                for(String serie : serieList.keySet()) {
                    serieList.get(serie).setName(serie);
                }
                scatterChart.getData().addAll(serieList.values());


                HBox hBox = ViewUtil.loadLegend();
                controller.loadLegend(hBox);
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



            LoadableData newData = (LoadableData) data;
            if (actualX == null || actualY == null) {
                controller.setAxesSelected("Aucuns axes sélectionnés");
                return;
            }
            Object attrX = newData.getAttributesNames().get(actualX);
            Object attrY = newData.getAttributesNames().get(actualY);
            if (attrX instanceof Integer) {
                attrX = ((Integer) attrX).doubleValue();
            }
            if (attrY instanceof Integer) {
                attrY = ((Integer) attrY).doubleValue();
            }
            XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(
                    (Double) attrX,
                    (Double) attrY
            );

            dataPoint.setNode(ViewUtil.getForm(newData, new Rectangle(10, 10), controller));
            if (!scatterChart.getData().isEmpty()) {
                series4.getData().add(dataPoint);
                series4.setName("indéfini");
                scatterChart.getData().add(series4);
            }


            controller.loadLegend(ViewUtil.loadLegend());
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
