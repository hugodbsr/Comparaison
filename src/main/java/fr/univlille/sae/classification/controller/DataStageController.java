package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.AxesSettingsView;
import fr.univlille.sae.classification.view.DataStageView;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlleur pour le FXML data-view-stage, pour gérer la vue supplémentaire
 */
public class DataStageController {
    @FXML
    Stage stage;

    @FXML
    ScatterChart scatterChart;

    @FXML
    Label AxesSelected;

    @FXML
    ListView PointInfo;

    /**
     * DataStageView associé au controlleur
     */
    private DataStageView dataStageView;

    private double initialX;
    private double initialY;
    private double initialLowerBoundX;
    private double initialUpperBoundX;
    private double initialLowerBoundY;
    private double initialUpperBoundY;

    public void initialize() {
        setupZoom();
        setupDrag();
    }

    /**
     * Ouvrir les paramètres des axes de la vue
     */
    public void openAxesSetting(){
        AxesSettingsView axesSettingsView = new AxesSettingsView(ClassificationModel.getClassificationModel(), stage, dataStageView);
        axesSettingsView.show();
    }

    /**
     * Associe la dataStageView associer à la classe
     * @param dataStageView
     */
    public void setDataStageView (DataStageView dataStageView) {
        this.dataStageView = dataStageView;
    }


    /**
     * Renvoie la grille associé à la classe
     * @return grille de la classe
     */
    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }

    /**
     * Attribut une valeur à l'axe de la grille
     * @param texte Valeur de l'axe
     */
    public void setAxesSelected(String texte) {
        this.AxesSelected.setText(texte);
    }

    public void setAxesSelectedDisable(){
        this.AxesSelected.setDisable(true);
    }

    public ListView getPointInfo(){
        return this.PointInfo;
    };

    private void setupZoom() {
        NumberAxis xAxis = (NumberAxis) scatterChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) scatterChart.getYAxis();

        scatterChart.setOnScroll(event -> {
            xAxis.setAutoRanging(false);
            yAxis.setAutoRanging(false);

            double delta = event.getDeltaY();
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            double chartX = xAxis.sceneToLocal(mouseX, mouseY).getX();
            double chartY = yAxis.sceneToLocal(mouseX, mouseY).getY();

            double zoomFactor;
            if (delta > 0) {
                zoomFactor = 0.90;
            } else {
                zoomFactor = 1.05;
            }

            double xLower = xAxis.getLowerBound();
            double xUpper = xAxis.getUpperBound();
            double yLower = yAxis.getLowerBound();
            double yUpper = yAxis.getUpperBound();

            double rangeX = xUpper - xLower;
            double rangeY = yUpper - yLower;

            double newRangeX = rangeX * zoomFactor;
            double newRangeY = rangeY * zoomFactor;

            xAxis.setLowerBound(xLower + (chartX / xAxis.getWidth()) * (rangeX - newRangeX));
            xAxis.setUpperBound(xUpper - ((xAxis.getWidth() - chartX) / xAxis.getWidth()) * (rangeX - newRangeX));

            yAxis.setLowerBound(yLower + ((yAxis.getHeight() - chartY) / yAxis.getHeight()) * (rangeY - newRangeY));
            yAxis.setUpperBound(yUpper - (chartY / yAxis.getHeight()) * (rangeY - newRangeY));
        });

        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
    }

    private void setupDrag() {
        scatterChart.setOnMousePressed(event -> {
            initialX = event.getSceneX();
            initialY = event.getSceneY();
            initialLowerBoundX = ((NumberAxis) scatterChart.getXAxis()).getLowerBound();
            initialUpperBoundX = ((NumberAxis) scatterChart.getXAxis()).getUpperBound();
            initialLowerBoundY = ((NumberAxis) scatterChart.getYAxis()).getLowerBound();
            initialUpperBoundY = ((NumberAxis) scatterChart.getYAxis()).getUpperBound();
        });

        NumberAxis xAxis = (NumberAxis) scatterChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) scatterChart.getYAxis();

        scatterChart.setOnMouseDragged(event -> {
            xAxis.setAutoRanging(false);
            yAxis.setAutoRanging(false);
            double deltaX = event.getSceneX() - initialX;
            double deltaY = event.getSceneY() - initialY;

            double newLowerBoundX = initialLowerBoundX - deltaX * (xAxis.getUpperBound() - xAxis.getLowerBound()) / scatterChart.getWidth();
            double newUpperBoundX = initialUpperBoundX - deltaX * (xAxis.getUpperBound() - xAxis.getLowerBound()) / scatterChart.getWidth();
            double newLowerBoundY = initialLowerBoundY + deltaY * (yAxis.getUpperBound() - yAxis.getLowerBound()) / scatterChart.getHeight();
            double newUpperBoundY = initialUpperBoundY + deltaY * (yAxis.getUpperBound() - yAxis.getLowerBound()) / scatterChart.getHeight();

            xAxis.setLowerBound(newLowerBoundX);
            xAxis.setUpperBound(newUpperBoundX);
            yAxis.setLowerBound(newLowerBoundY);
            yAxis.setUpperBound(newUpperBoundY);
        });
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
    }

}
