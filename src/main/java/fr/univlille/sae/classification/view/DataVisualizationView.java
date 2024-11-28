package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.ViewUtil;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.*;

/**
 * Classe abstraite représentant une vue de visualisation des données.
 * Elle gère les axes actuels et le graphique de dispersion.
 */
public abstract class DataVisualizationView {

    private ScatterChart.Series series1;
    private ScatterChart.Series series2;
    private ScatterChart.Series series3;
    private ScatterChart.Series series4;
    protected String actualX;
    protected String actualY;
    protected ScatterChart scatterChart;
    private MainStageController controller;
    /**
     * Constructeur par défaut.
     */
    protected DataVisualizationView() {}

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
                if (series4 == null) {
                    series4 = new ScatterChart.Series<>();
                }
                series4.getData().add(dataPoint);
                series4.setName("indéfini");
                scatterChart.getData().add(series4);
            }
        } catch (Exception e) {
            System.err.println("Erreur de mise à jour : " + e.getMessage());
        }
    }


}
