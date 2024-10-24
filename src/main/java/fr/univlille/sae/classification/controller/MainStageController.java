package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.AxesSettingsView;
import fr.univlille.sae.classification.view.LoadDataView;
import fr.univlille.sae.classification.view.AddDataView;
import fr.univlille.sae.classification.view.MainStageView;
import javafx.fxml.FXML;

import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;


import java.io.IOException;


public class MainStageController {


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
    private MainStageView mainStageView;

    /**
     * Ouvre l'interface de chargement des données.
     * @throws IOException
     */
    public void openLoadData() throws IOException {
        LoadDataView loadDataView = new LoadDataView(ClassificationModel.getClassificationModel(), stage);
        loadDataView.show();
    }

    public void openAxesSetting()throws IOException {
        AxesSettingsView axesSettingsView = new AxesSettingsView(ClassificationModel.getClassificationModel(), stage, mainStageView);
        axesSettingsView.show();
    }

    public void setMainStageView(MainStageView mainStageView) {
        this.mainStageView = mainStageView;
    }

    /**
     * Ouvre l'interface d'ajout de donnée.
     * @throws IOException
     */
    public void openAddData() throws IOException {

        AddDataView addDataView = new AddDataView(ClassificationModel.getClassificationModel(), stage, mainStageView);
        addDataView.show();


    }



    public void classifyDatas() {
        ClassificationModel.getClassificationModel().classifierDonnees();
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
