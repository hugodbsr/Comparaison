package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.*;
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
     */
    public void openLoadData() throws IOException {
        LoadDataView loadDataView = new LoadDataView(ClassificationModel.getClassificationModel(), stage);
        loadDataView.show();
    }

    /**
     * Ouvre l'interface d'une nouvelle vue.
     */
    public void openDataView() throws IOException {
        DataStageView dataStageView = new DataStageView(ClassificationModel.getClassificationModel());
        dataStageView.show();
    }

    /**
     * Ouvre l'interface de la configuration des axes.
     */
    public void openAxesSetting()throws IOException {
        AxesSettingsView axesSettingsView = new AxesSettingsView(ClassificationModel.getClassificationModel(), stage, mainStageView);
        axesSettingsView.show();
    }

    /**
     * Associe la mainStageView associer à la classe
     * @param mainStageView
     */
    public void setMainStageView(MainStageView mainStageView) {
        this.mainStageView = mainStageView;
    }

    /**
     * Ouvre l'interface d'ajout de donnée.
     */
    public void openAddData() throws IOException {
        AddDataView addDataView = new AddDataView(ClassificationModel.getClassificationModel(), stage, mainStageView);
        addDataView.show();
    }

    /**
     * Appelle de la méthode de la classe ClassificationModel afin de classifier les nouvelles données
     */
    public void classifyDatas() {
        ClassificationModel.getClassificationModel().classifierDonnees();
        classifyData.setDisable(true);
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

    /**
     * Renvoie le bouton de classification de données
     * @return Bouton de classification
     */
    public Button getClassifyData() {
        return this.classifyData;
    }
}
