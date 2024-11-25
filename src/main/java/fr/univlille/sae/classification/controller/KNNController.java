package fr.univlille.sae.classification.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class KNNController {
    @FXML
    ChoiceBox<String> AlgoSelector;

    @FXML
    TextField KEntry;

    @FXML
    Button AutoK;

    @FXML
    Button confirmK;


}
