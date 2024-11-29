package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.DataStageController;
import fr.univlille.sae.classification.controller.DataVisualizationController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.ViewUtil;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;

import java.util.*;

/**
 * Classe abstraite représentant une vue de visualisation des données.
 * Elle gère les axes actuels et le graphique de dispersion.
 */
public abstract class DataVisualizationView {
    private static Set<DataVisualizationView> views = new HashSet<>();
    public DataVisualizationController controller;
    private ScatterChart.Series series1;
    private ScatterChart.Series series2;
    private ScatterChart.Series series3;
    private ScatterChart.Series series4;
    protected String actualX;
    protected String actualY;
    protected ScatterChart scatterChart;


    private Map<String, ScatterChart.Series<Double, Double>> serieList;
    public ClassificationModel model;
    /**
     * Constructeur pour initialiser la vue de données.
     * @param model le modèle de classification utilisé pour gérer les données.
     */
    protected DataVisualizationView(ClassificationModel model) {
        this.serieList = new HashMap<String, ScatterChart.Series<Double, Double>>();
        this.model = model;
        this.series4 = new XYChart.Series();
        views.add(this);
    }

    /**
     * Réinitialisation des axes.
     */
    public static void resetEachAxis() {
        // call method resetAxis for each instance of DataVisualizationView (views)
        for(DataVisualizationView view : views) {
            view.resetAxis();
        }
    }

    /**
     * Réinitialisation des axes.
     */
    public void resetAxis(){
        setActualY("");
        setActualX("");
        ((NumberAxis) scatterChart.getXAxis()).setLabel("");
        ((NumberAxis) scatterChart.getYAxis()).setLabel("");
    }

    /**
     * Renvoie le nom de l'axe X actuel.
     * @return nom de l'axe X.
     */
    public String getActualX() {
        return actualX;
    }

    /**
     * Définit le nom de l'axe X actuel.
     * @param actualX nom de l'axe X à définir.
     */
    public void setActualX(String actualX) {
        this.actualX = actualX;
    }

    /**
     * Renvoie le nom de l'axe Y actuel.
     * @return nom de l'axe Y.
     */
    public String getActualY() {
        return actualY;
    }

    /**
     * Définit le nom de l'axe Y actuel.
     * @param actualY nom de l'axe Y à définir.
     */
    public void setActualY(String actualY) {
        this.actualY = actualY;
    }

    /**
     * Renvoie le graphique de dispersion associé à cette vue.
     * @return graphique de dispersion.
     */
    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }

    /**
     * Méthode abstraite à implémenter pour recharger les données de la vue.
     */
    public abstract void reload();


    /**
     * Met à jour l'affichage des données en fonction des changements dans le modèle.
     * @param observable modèle observé.
     */
    public void update(Observable observable) {
        try {
            if (scatterChart == null || !(observable instanceof ClassificationModel)) {
                System.err.println("Erreur de mise à jour.");
                return;
            }

            scatterChart.getData().clear();
            serieList.clear();

            if (actualX == null && actualY == null || actualY.isEmpty() || actualX.isEmpty()) {
                controller.setAxesSelected("Aucuns axes sélectionnés");
                controller.setAxesSelectedDisability(false);
            } else {
                controller.setAxesSelected("");
                controller.setAxesSelectedDisability(true);

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
                    if(data.getClassification().equals("undefined") || data.getClassification().equals("null") || model.getDataToClass().containsKey(data)) {
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


                VBox vBox = ViewUtil.loadLegend();
                controller.loadLegend(vBox);
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
    public void update(Observable observable, Object data) {
        try {
            if (scatterChart == null || !(observable instanceof ClassificationModel)) {
                System.err.println("Erreur de mise à jour.");
                return;
            }

            LoadableData newData = (LoadableData) data;
            if (actualX == null || actualY == null || actualY.isEmpty() || actualX.isEmpty()) {
                controller.setAxesSelected("Aucuns axes sélectionnés");
                controller.setAxesSelectedDisability(false);
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


}
