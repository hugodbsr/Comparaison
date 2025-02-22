package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.AxesSettingsView;
import fr.univlille.sae.classification.view.DataVisualizationView;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe abstraite de contrôleur pour les fenêtres de visualisation de données.
 */
public abstract class DataVisualizationController {

    /**
     * Fenêtre associée à cette vue.
     */
    @FXML
    Stage stage;

    /**
     * Label affichant les axes.
     */
    @FXML
    Label AxesSelected;

    /**
     * Légende de la visualisation.
     */
    @FXML
    VBox legend;

    /**
     * ScatterChart pour l'affichage des données.
     */
    @FXML
    ScatterChart scatterChart;

    /**
     * Positions x et y du curseur de la souris.
     */
    protected double initialX;
    protected double initialY;

    /**
     * Limites initiales des axes.
     */
    protected double initialLowerBoundX;
    protected double initialUpperBoundX;
    protected double initialLowerBoundY;
    protected double initialUpperBoundY;

    /**
     * Vue de visualisation des données associé à ce contrôleur.
     */
    protected DataVisualizationView view;

    /**
     * Configure les fonctionnalités de zoom à l'aide de la molette de la souris.
     */
    protected void setupZoom() {
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

    /**
     * Configure les fonctionnalités de déplacement à l'aide de la souris.
     */
    protected void setupDrag() {
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

    /**
     * Ouvrir les paramètres des axes de la vue.
     */
    public void openAxesSetting(){
        AxesSettingsView axesSettingsView = new AxesSettingsView(ClassificationModel.getClassificationModel(), stage, view);
        axesSettingsView.show();
    }

    /**
     * Renvoie la grille associée à la classe.
     * @return Grille de la classe
     */
    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }


    /**
     * Attribue une valeur à l'axe de la grille.
     * @param texte Valeur de l'axe
     */
    public void setAxesSelected(String texte) {
        this.AxesSelected.setText(texte);
    }

    /**
     * Désactive ou active la zone de texte des axes sélectionnés.
     */
    public void setAxesSelectedDisability(boolean disability){
        this.AxesSelected.setDisable(disability);
    }

    /**
     * Charge une légende dans le conteneur dédié.
     * @param vBox Conteneur contenant les éléments de la légende
     */
    public void loadLegend(VBox vBox) {
        this.legend.getChildren().clear();
        this.legend.getChildren().addAll(vBox.getChildren());
    }
}
