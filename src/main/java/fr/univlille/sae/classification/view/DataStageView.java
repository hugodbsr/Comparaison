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
import javafx.scene.Node;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsable de l'affichage et de la gestion de la vue des données.
 * Implémente l'interface Observer pour recevoir des notifications de mise à jour.
 */
public class DataStageView extends DataVisualizationView implements Observer {

    private ClassificationModel model;
    private DataStageController controller;

    private Map<String, ScatterChart.Series<Double, Double>> serieList;

    private XYChart.Series series1;
    private XYChart.Series series2;
    private XYChart.Series series3;
    private XYChart.Series series4;

    private Stage root;

    /**
     * Constructeur pour initialiser la vue de données.
     * @param model le modèle de classification utilisé pour gérer les données.
     */
    public DataStageView(ClassificationModel model) {
        super();
        this.serieList = new HashMap<String, ScatterChart.Series<Double, Double>>();
        this.model = model;
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
            controller = loader.getController();
            controller.setDataStageView(this);
            scatterChart = controller.getScatterChart();

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
     * Met à jour l'affichage des données en fonction des changements dans le modèle.
     * @param observable modèle observé.
     */
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
            }
        } catch (Exception e) {
            System.err.println("Erreur de mise à jour : " + e.getMessage());
        }
    }

    /**
     * Met à jour l'affichage en ajoutant un nouveau point de données.
     * @param observable modèle observé.
     * @param data point de données à ajouter.
     */
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
        } catch (Exception e) {
            System.err.println("Erreur de mise à jour : " + e.getMessage());
        }
    }

    /**
     * Renvoie le contrôleur associé à cette vue.
     * @return contrôleur de la vue.
     */
    public DataStageController getController() {
        return controller;
    }

    /**
     * Recharge les données de la vue en fonction des données du modèle.
     */
    @Override
    public void reload() {
        this.update(ClassificationModel.getClassificationModel());
    }
}
