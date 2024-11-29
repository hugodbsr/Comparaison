package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseAttributesController {


    @FXML
    Stage stage;


    @FXML
    ChoiceBox<String> choice;


    @FXML
    public void initialize() {
        LoadableData rdmData = ClassificationModel.getClassificationModel().getDatas().get(0);
        choice.getItems().addAll(rdmData.getClassifiedAttributes().keySet());
        String value = rdmData.getAttributesNames().keySet().toArray(new String[0])[rdmData.getClassificationType()];
        if(!rdmData.getClassifiedAttributes().containsKey(value)) {
            value = (String) rdmData.getClassifiedAttributes().keySet().toArray()[0];
        }
        choice.setValue(value);
    }




    public void validate() {
        String selected = choice.getValue();
        LoadableData rdmData = ClassificationModel.getClassificationModel().getDatas().get(0);

        System.out.println(LoadableData.getClassifications());
        List<String> temp = new ArrayList<>(rdmData.getAttributesNames().keySet());

        System.out.println(selected);

        System.out.println("Index: " + temp.indexOf(selected));
        try {
            rdmData.setClassificationType(temp.indexOf(selected));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        stage.close();
    }

}
