package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.AxesSettingsView;
import fr.univlille.sae.classification.view.DataStageView;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class DataStageController {
    @FXML
    Stage stage;

    @FXML
    ScatterChart scatterChart;

    @FXML
    Label AxesSelected;

    private DataStageView dataStageView;


    public void openAxesSetting()throws IOException {
        AxesSettingsView axesSettingsView = new AxesSettingsView(ClassificationModel.getClassificationModel(), stage, dataStageView);
        axesSettingsView.show();
    }

    public void setDataStageView (DataStageView dataStageView) {
        this.dataStageView = dataStageView;
    }



    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }

    public void setAxesSelected(String texte) {
        this.AxesSelected.setText(texte);
    }

}
