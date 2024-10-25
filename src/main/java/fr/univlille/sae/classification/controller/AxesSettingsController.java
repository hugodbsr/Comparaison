package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.view.DataVisualizationView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;


public class AxesSettingsController{
    @FXML
    Stage stage;

    @FXML
    ChoiceBox selectOrd;

    @FXML
    ChoiceBox selectAbs;


    DataVisualizationView dataVisualizationView;

    public void setSelectOrd(String[] fields){
        selectOrd.getItems().clear();
        selectOrd.getItems().addAll(fields);
        selectOrd.setValue(dataVisualizationView.getActualY());
    }

    public void setSelectAbs(String[] fields){
        selectAbs.getItems().clear();
        selectAbs.getItems().addAll(fields);
        selectAbs.setValue(dataVisualizationView.getActualX());
    }

    public void setdataVisualizationView(DataVisualizationView dataVisualizationView) {
        this.dataVisualizationView = dataVisualizationView;
    }

    public void validate(){
        dataVisualizationView.setActualX(selectAbs.getValue().toString());
        dataVisualizationView.setActualY(selectOrd.getValue().toString());
        dataVisualizationView.getScatterChart().getXAxis().setLabel(dataVisualizationView.getActualX());
        dataVisualizationView.getScatterChart().getYAxis().setLabel(dataVisualizationView.getActualY());

        dataVisualizationView.reload();
        stage.close();
    }
}
