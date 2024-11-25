package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.*;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStageController {

    @FXML
    Stage stage;

    @FXML
    Button classifyData;

    @FXML
    ScatterChart scatterChart;

    @FXML
    Label AxesSelected;

    @FXML
    ListView PointInfo;

    @FXML
    Button ZoomIn;

    @FXML
    Button ZoomOut;

    private MainStageView mainStageView;

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
     * Ouvre l'interface de chargement des données.
     * Permet à l'utilisateur de sélectionner des données à charger pour la classification.
     */
    public void openLoadData() {
        LoadDataView loadDataView = new LoadDataView(ClassificationModel.getClassificationModel(), stage);
        loadDataView.show();
    }

    public void openClassification(){
        KNNView knnView = new KNNView(ClassificationModel.getClassificationModel(), stage);
        knnView.show();
    }

    /**
     * Ouvre l'interface d'une nouvelle vue.
     * Affiche une nouvelle fenêtre pour visualiser les données après classification.
     */
    public void openDataView() {
        DataStageView dataStageView = new DataStageView(ClassificationModel.getClassificationModel());
        dataStageView.show();
    }

    /**
     * Ouvre l'interface de la configuration des axes.
     * Permet à l'utilisateur de définir les axes du graphique.
     */
    public void openAxesSetting() {
        AxesSettingsView axesSettingsView = new AxesSettingsView(ClassificationModel.getClassificationModel(), stage, mainStageView);
        axesSettingsView.show();
    }

    /**
     * Associe la mainStageView à la classe.
     * @param mainStageView Instance de MainStageView à associer.
     */
    public void setMainStageView(MainStageView mainStageView) {
        this.mainStageView = mainStageView;
    }

    /**
     * Ouvre l'interface d'ajout de donnée.
     * Permet à l'utilisateur d'ajouter de nouvelles données à classifier.
     */
    public void openAddData() {
        AddDataView addDataView = new AddDataView(ClassificationModel.getClassificationModel(), stage, mainStageView);
        addDataView.show();
    }

    /**
     * Appelle la méthode de la classe ClassificationModel afin de classifier les nouvelles données.
     * Désactive le bouton de classification après l'appel de la méthode.
     */
    public void classifyDatas() {
        ClassificationModel.getClassificationModel().classifierDonnees();
        classifyData.setDisable(true);
    }

    /**
     * Renvoie la grille associée à la classe.
     * @return grille de type ScatterChart utilisée pour la visualisation des données.
     */
    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }

    /**
     * Attribue une valeur à l'axe de la grille.
     * @param texte Valeur de l'axe à afficher sur l'interface.
     */
    public void setAxesSelected(String texte) {
        this.AxesSelected.setText(texte);
    }

    public void setAxesSelectedDisable(){
        this.AxesSelected.setDisable(true);
    }

    /**
     * Renvoie le bouton de classification de données.
     * @return Bouton utilisé pour déclencher la classification des données.
     */
    public Button getClassifyData() {
        return this.classifyData;
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
