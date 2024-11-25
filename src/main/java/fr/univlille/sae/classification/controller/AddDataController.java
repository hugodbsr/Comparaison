package fr.univlille.sae.classification.controller;

import com.sun.scenario.effect.impl.state.HVSeparableKernel;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.MainStageView;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controlleur pour le FXML add-data-stage, pour ajouter une nouvelle donnée
 */
public class AddDataController {

    @FXML
    private Stage stage;

    @FXML
    private VBox entries;

    /**
     * MainStageView associé au controlleur
     */
    MainStageView mainStageView;

    private List<Spinner<Double>> spinners;

    /**
     * Méthode d'intitialisation du controlleur
     */
    @FXML
    public void initialize() {
        this.spinners = new ArrayList<>();
        ClassificationModel model = ClassificationModel.getClassificationModel();
        if (!model.getDatas().isEmpty()) {
            String[] attributes = model.getDatas().get(0).getAttributesNames().keySet().toArray(new String[0]);
            for (String attribute : attributes) {
                Label label = new Label(attribute);

                Spinner<Double> spinner = new Spinner<>();
                spinner.setEditable(true);
                SpinnerValueFactory<Double> valueFactory =
                        new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                0,
                                Double.POSITIVE_INFINITY,
                                0.0,
                                0.1
                        );
                spinner.setValueFactory(valueFactory);
                HBox hbox = new HBox(10, label, spinner);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(10.0);
                entries.getChildren().addAll(hbox);
                spinners.add(spinner);
            }
        }
    }

    /**
     * Méthode permettante d'attribuer la mainStageView associer à la classe
     * @param mainStageView mainStageView à attribuer
     */
    public void setMainStageView(MainStageView mainStageView) {
        this.mainStageView = mainStageView;
    }
    /**
     * Validation des données à ajouter
     */
    public void validate() {
        System.out.println("validé");
        mainStageView.getController().getClassifyData().setDisable(false);

        try {
            Double[] values = spinners.stream().map(Spinner::getValue).toArray(Double[]::new);
            ClassificationModel.getClassificationModel().ajouterDonnee((Object[]) values);
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        stage.close();
    }

}
