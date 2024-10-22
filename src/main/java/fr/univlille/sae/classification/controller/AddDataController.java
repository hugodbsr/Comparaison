package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class AddDataController {

    @FXML
    private Stage stage;

    @FXML
    private Button confirmAdd;

    @FXML
    private Spinner<Double> sepalLengthSpinner;

    @FXML
    private Spinner<Double> sepalWidthSpinner;

    @FXML
    private Spinner<Double> petalLengthSpinner;

    @FXML
    private Spinner<Double> petalWidthSpinner;

    @FXML
    public void initialize() {
        sepalLengthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 200.0, 3.0,0.1));
        sepalWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 200.0, 3.0, 0.1));
        petalLengthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 200.0, 3.0, 0.1));
        petalWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 200.0, 3.0, 0.1));

        // Permet d'écrire dans la case du spinners
        sepalLengthSpinner.setEditable(true);
        sepalWidthSpinner.setEditable(true);
        petalLengthSpinner.setEditable(true);
        petalWidthSpinner.setEditable(true);


        confirmAdd.setOnAction(event -> handleConfirmAdd());
    }

    private void handleConfirmAdd() {
        double sepalLength = sepalLengthSpinner.getValue();
        double sepalWidth = sepalWidthSpinner.getValue();
        double petalLength = petalLengthSpinner.getValue();
        double petalWidth = petalWidthSpinner.getValue();

        stage.close();
    }

    public void validate() throws IOException {


        ClassificationModel.getClassificationModel().ajouterDonnee(sepalLengthSpinner.getValue(), sepalWidthSpinner.getValue(), petalLengthSpinner.getValue(), petalWidthSpinner.getValue());
        stage.close();
    }

}
