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

/**
 * Controlleur pour le FXML data-view-stage, pour gérer la vue supplémentaire
 */
public class DataStageController {
    @FXML
    Stage stage;

    @FXML
    Button classifyData;

    @FXML
    ScatterChart scatterChart;

    @FXML
    Label AxesSelected;

    /**
     * DataStageView associé au controlleur
     */
    private DataStageView dataStageView;

    /**
     * Ouvrir les paramètres des axes de la vue
     */
    public void openAxesSetting()throws IOException {
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

}
