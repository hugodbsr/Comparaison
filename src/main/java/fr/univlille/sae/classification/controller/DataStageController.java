package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.*;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class DataStageController {
    @FXML
    Stage stage;

    @FXML
    NumberAxis absAxe;

    @FXML
    NumberAxis ordAxe;

    @FXML
    Button settings;

    @FXML
    Button loadData;

    @FXML
    Button addData;

    @FXML
    Button classifyData;

    @FXML
    ScatterChart scatterChart;

    @FXML
    Label AxesSelected;

    Stage loadStage;
    private DataStageView dataStageView;

    /**
     * Ouvre l'interface de chargement des données.
     * @throws IOException
     */
    public void openLoadData() throws IOException {
        LoadDataView loadDataView = new LoadDataView(ClassificationModel.getClassificationModel(), stage);
        loadDataView.show();
    }

    public void openAxesSetting()throws IOException {
        AxesSettingsView axesSettingsView = new AxesSettingsView(ClassificationModel.getClassificationModel(), stage, dataStageView);
        axesSettingsView.show();
    }

    public void setDataStageView (DataStageView dataStageView) {
        this.dataStageView = dataStageView;
    }

    /**
     * Ouvre l'interface d'ajout de donnée.
     * @throws IOException
     */
    public void openAddData() throws IOException {
        AddDataView addDataView = new AddDataView(ClassificationModel.getClassificationModel(), stage, dataStageView);
        addDataView.show();
    }



    public void classifyDatas() {
        ClassificationModel.getClassificationModel().classifierDonnees();
        classifyData.setDisable(true);
    }


    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }

    public void setAxesSelected(String texte) {
        this.AxesSelected.setText(texte);
    }

    public Button getClassifyData() {
        return this.classifyData;
    }
}
