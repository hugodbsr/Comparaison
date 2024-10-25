package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.MainStageView;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AddDataController {

    @FXML
    private Stage stage;

    @FXML
    private Spinner<Double> sepalLengthSpinner;

    @FXML
    private Spinner<Double> sepalWidthSpinner;

    @FXML
    private Spinner<Double> petalLengthSpinner;

    @FXML
    private Spinner<Double> petalWidthSpinner;

    MainStageView mainStageView;

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

    }

    public void setMainStageView(MainStageView mainStageView) {
        this.mainStageView = mainStageView;
    }

    public void validate() {
        System.out.println("validé");
        mainStageView.getController().getClassifyData().setDisable(false);


        ClassificationModel.getClassificationModel().ajouterDonnee(sepalLengthSpinner.getValue(), sepalWidthSpinner.getValue(), petalLengthSpinner.getValue(), petalWidthSpinner.getValue());
        stage.close();
    }

}
