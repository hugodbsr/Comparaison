package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.view.MainStageView;
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

    @FXML
    Button confirmAxes;

    MainStageView mainStageView;

    public void setSelectOrd(String[] fields){
        selectOrd.getItems().clear();
        selectOrd.getItems().addAll(fields);
    }

    public void setSelectAbs(String[] fields){
        selectAbs.getItems().clear();
        selectAbs.getItems().addAll(fields);
    }

    public void setMainStageView(MainStageView mainStageView) {
        this.mainStageView = mainStageView;
    }

    public void validate(){
        mainStageView.setActualX(selectAbs.getValue().toString());
        mainStageView.setActualY(selectOrd.getValue().toString());
        mainStageView.update(mainStageView.getModel());
        stage.close();
    }
}
