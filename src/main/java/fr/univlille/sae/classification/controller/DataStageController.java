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
public class DataStageController extends DataVisualizationController{
    @FXML
    Stage stage;




    @FXML
    ListView PointInfo;




    public void initialize() {
        setupZoom();
        setupDrag();
    }



    /**
     * Associe la dataStageView associer à la classe
     * @param dataStageView
     */
    public void setDataStageView (DataStageView dataStageView) {
        this.view = dataStageView;
    }






    public ListView getPointInfo(){
        return this.PointInfo;
    };



}
