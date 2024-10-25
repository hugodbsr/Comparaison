package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.view.DataVisualizationView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * Controlleur pour le FXML axes-settings-stage, pour gérer les axes de la vue
 */
public class AxesSettingsController{
    @FXML
    Stage stage;

    @FXML
    ChoiceBox selectOrd;

    @FXML
    ChoiceBox selectAbs;


    /**
     * DataVisualizationView associé au controlleur
     */
    DataVisualizationView dataVisualizationView;

    /**
     * Ajout des éléments à sélectionner pour les ordonnées de la grille
     * @param fields Éléments à ajouter
     */
    public void setSelectOrd(String[] fields){
        selectOrd.getItems().clear();
        selectOrd.getItems().addAll(fields);
        selectOrd.setValue(dataVisualizationView.getActualY());
    }

    /**
     * Ajout des éléments à sélectionner pout les abscisses de la grille
     * @param fields Éléments à ajouter
     */
    public void setSelectAbs(String[] fields){
        selectAbs.getItems().clear();
        selectAbs.getItems().addAll(fields);
        selectAbs.setValue(dataVisualizationView.getActualX());
    }

    /**
     * Méthode permettante d'attribuer la dataVisualizationView associer à la classe
     * @param dataVisualizationView dataVisualizationView à attribuer
     */
    public void setdataVisualizationView(DataVisualizationView dataVisualizationView) {
        this.dataVisualizationView = dataVisualizationView;
    }

    /**
     * Validation des paramètres des axes
     */
    public void validate(){
        dataVisualizationView.setActualX(selectAbs.getValue().toString());
        dataVisualizationView.setActualY(selectOrd.getValue().toString());
        dataVisualizationView.getScatterChart().getXAxis().setLabel(dataVisualizationView.getActualX());
        dataVisualizationView.getScatterChart().getYAxis().setLabel(dataVisualizationView.getActualY());

        dataVisualizationView.reload();
        stage.close();
    }
}
