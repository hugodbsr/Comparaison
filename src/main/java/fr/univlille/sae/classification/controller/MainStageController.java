package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.*;
import javafx.fxml.FXML;
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

    private MainStageView mainStageView;

    /**
     * Ouvre l'interface de chargement des données.
     * Permet à l'utilisateur de sélectionner des données à charger pour la classification.
     */
    public void openLoadData() {
        LoadDataView loadDataView = new LoadDataView(ClassificationModel.getClassificationModel(), stage);
        loadDataView.show();
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
}
